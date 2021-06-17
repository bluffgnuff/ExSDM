package it.unibo.sdls.sampleproject.dao.jdbc;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.AuthorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericJDBCAuthorDAO
extends GenericJDBCDAO
implements AuthorDAO {
	
	// ======================================================================================
	
	public  static final String author_table = "author";

	// -------------------------------------------------------------------------------------

	public static final String author_id = "id";
	public static final String author_name = "name";
	
	// -------------------------------------------------------------------------------------

	protected static final String insert_author = 
		"INSERT " +
			"INTO " + author_table + " ( " + author_name + " ) " +
			"VALUES (?) ;"
		;
	
	protected static final String find_author_by_id = 
		"SELECT * " +
			"FROM " + author_table + " " +
			"WHERE " + author_id + " = ? ;"
		;
	protected static final String find_author_by_name = 
		"SELECT * " +
			"FROM " + author_table + " " +
			"WHERE " + author_name + " = ? ;"
		;
	
	protected static final String find_all_authors = 
		"SELECT * " +
			"FROM " + author_table + ";"
		;
	
	protected static final String remove_author_by_id = 
		"DELETE " +
			"FROM " + author_table + " " +
			"WHERE " + author_id + " = ? ;"
		;

	protected abstract String getLastInsert();

	// -------------------------------------------------------------------------------------

	protected abstract String getCreate();

	protected static final String drop = 
		"DROP " +
			"TABLE " + author_name + " ;"
		;
	
	// ======================================================================================

	public int insertAuthor(Author author) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -3;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( author == null )  {
			getCurrentJDBCFactory().getLogger().error("failed to insert a null author");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(insert_author);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, author.getName());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d.
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to insert author\n",e);
			e.printStackTrace();
			result = -2;
		}

		// Nel caso della creazione di una nuova tupla eseguo un secondo accesso per sapere che id le è stato assegnato
		// Chiaramente è inutile farlo se già il primo accesso ha prodotto errori
		// --- 6a./7a. Chiusura della connessione in caso di errori e restituizione prematura di un risultato di fallimento
		if ( result == -2 ) {
			getCurrentJDBCFactory().releaseConnection(conn);
			return result;
		}
		
		// Eseguo una seconda query, dipendende da database e tabella in uso, per rileggere l'ultima tupla inserita
		result = getLastInsertId(conn);
		
		// --- 6b. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		getCurrentJDBCFactory().releaseConnection(conn);
		// --- 7b. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
	
	protected int getLastInsertId(Connection conn) {
		int result =  -1;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getLastInsert());
			if ( rs.next() ) {
				result = rs.getInt(1);
			}		
			rs.close();
			stmt.close();
		}
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve id of last inserted author",e);
			e.printStackTrace();
		}
		return result;
	}

	// --------------------------------------------------------------------------------------------------------

	public Author findAuthorById(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Author result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( id < 0 )  {
			getCurrentJDBCFactory().getLogger().error("failed to read author with negative id");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(find_author_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() ) {
				Author author = new Author();
				author.setId(rs.getInt(author_id));
				author.setName(rs.getString(author_name));
				// books.. lazy fetch
				result = author;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve author with id = " + id,e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
	
	// --------------------------------------------------------------------------------------------------------

	public Author findAuthorByName(String name) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Author result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( name == null || name.equals("") ) {
			getCurrentJDBCFactory().getLogger().error("failed to read a author with no name");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(find_author_by_name);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, name);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() ) {
				Author author = new Author();
				author.setId(rs.getInt(author_id));
				author.setName(rs.getString(author_name));
				// books.. lazy fetch
				result = author;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve author with name = " + name+"\n",e);
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public List<Author> findAllAuthors() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<Author> result = new ArrayList<Author>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(find_all_authors);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			while ( rs.next() ) {
				Author author = new Author();
				author.setId(rs.getInt(author_id));
				author.setName(rs.getString(author_name));
				// books.. lazy fetch
				result.add(author);
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve authors",e);
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// --------------------------------------------------------------------------------------------------------

	public int removeAuthorByName(String name) {
		Author author = findAuthorByName(name);
		return removeAuthor(author);
	}
	
	public int removeAuthorById(int id) {
		Author author = findAuthorById(id);
		return removeAuthor(author);
	}

	protected int removeAuthor(Author author) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -1;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( author == null )  {
			getCurrentJDBCFactory().getLogger().error("failed to delete a null author");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(remove_author_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, author.getId());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = 1;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to delete author with id = " + author.getId(),e);
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
	
	// ======================================================================================
	
	/**
	 * Creazione della table
	 */
	protected boolean createTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(getCreate());
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().debug("failed to create author table",e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
	
	// -------------------------------------------------------------------------------------
	
	/**
	 * Rimozione della table
	 */
	protected boolean dropTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(drop);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto a posto.
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().debug("failed to drop author table",e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

}
