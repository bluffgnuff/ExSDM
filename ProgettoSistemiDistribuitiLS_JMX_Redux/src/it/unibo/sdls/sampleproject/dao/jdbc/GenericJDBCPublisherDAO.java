package it.unibo.sdls.sampleproject.dao.jdbc;

import it.unibo.sdls.sampleproject.dao.Publisher;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericJDBCPublisherDAO
extends GenericJDBCDAO
implements PublisherDAO {
	
	// ======================================================================================
	
	public static final String publisher_table = "publisher";

	// -------------------------------------------------------------------------------------

	public static final String publisher_id = "id";
	public static final String publisher_name = "name";
	
	// -------------------------------------------------------------------------------------

	protected static final String insert_publisher = 
		"INSERT " +
			"INTO " + publisher_table + " ( " + publisher_name + " ) " +
			"VALUES (?) ;"
		;
	
	protected static final String find_publisher_by_id = 
		"SELECT * " +
			"FROM " + publisher_table + " " +
			"WHERE " + publisher_id + " = ? ;"
		;
	protected static final String find_publisher_by_name = 
		"SELECT * " +
			"FROM " + publisher_table + " " +
			"WHERE " + publisher_name + " = ? ;"
		;

	protected static final String find_all_publishers = 
		"SELECT * " +
			"FROM " + publisher_table + ";"
		;
	
	protected static final String remove_publisher_by_id = 
		"DELETE " +
			"FROM " + publisher_table + " " +
			"WHERE " + publisher_id + " = ? ;"
		;

	protected abstract String getLastInsert();
	
	// -------------------------------------------------------------------------------------

	protected abstract String getCreate();
	
	protected static final String drop = 
		"DROP " +
			"TABLE " + publisher_name + " ;"
		;

	// ======================================================================================

	public int insertPublisher(Publisher publisher) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -3;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( publisher == null )  {
			getCurrentJDBCFactory().getLogger().error("failed to insert a null publisher");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(insert_publisher);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, publisher.getName());
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
			getCurrentJDBCFactory().getLogger().error("failed to insert publisher\n",e);
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
		int result = -1;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getLastInsert());
			if ( rs.next() ) {
				result = rs.getInt(1);
			}		
			rs.close();
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve id of last inserted publisher",e);
			e.printStackTrace();
		}
		return result;
	}

	// --------------------------------------------------------------------------------------------------------

	public Publisher findPublisherById(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Publisher result = null;
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
			PreparedStatement prep_stmt = conn.prepareStatement(find_publisher_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() ) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt(publisher_id));
				publisher.setName(rs.getString(publisher_name));
				// books.. lazy fetch
				result = publisher;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve publisher with id = " + id,e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
	
	// --------------------------------------------------------------------------------------------------------

	public Publisher findPublisherByName(String name) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Publisher result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( name == null || name.equals("") ) {
			getCurrentJDBCFactory().getLogger().error("failed to read a publisher with no name");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(find_publisher_by_name);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, name);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() ) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt(publisher_id));
				publisher.setName(rs.getString(publisher_name));
				// books.. lazy fetch
				result = publisher;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve publisher with name = " + name,e);
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public List<Publisher> findAllPublishers() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<Publisher> result = new ArrayList<Publisher>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(find_all_publishers);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			while ( rs.next() ) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt(publisher_id));
				publisher.setName(rs.getString(publisher_name));
				// books.. lazy fetch
				result.add(publisher);
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve publishers",e);
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

	public int removePublisherByName(String name) {
		Publisher publisher = findPublisherByName(name);
		return removePublisher(publisher);
	}
	
	public int removePublisherById(int id) {
		Publisher publisher = findPublisherById(id);
		return removePublisher(publisher);
	}
	
	protected int removePublisher(Publisher publisher) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -1;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( publisher == null )  {
			getCurrentJDBCFactory().getLogger().error("failed to delete a null publisher");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(remove_publisher_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, publisher.getId());
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
			getCurrentJDBCFactory().getLogger().error("failed to delete publisher with id = " + publisher.getId(),e);
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
			getCurrentJDBCFactory().getLogger().debug("failed to create publisher table",e);
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
			getCurrentJDBCFactory().getLogger().debug("failed to drop publisher table",e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

}