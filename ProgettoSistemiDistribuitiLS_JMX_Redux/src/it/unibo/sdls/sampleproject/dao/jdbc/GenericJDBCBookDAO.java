package it.unibo.sdls.sampleproject.dao.jdbc;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class GenericJDBCBookDAO
extends GenericJDBCDAO
implements BookDAO {
	
	// ======================================================================================
	
	public static final String book_table = "book";

	public static final String authorbook_jointable = "author_book";

	public static final String author_table = GenericJDBCAuthorDAO.author_table;
	public static final String publisher_table = GenericJDBCPublisherDAO.publisher_table;
 	
	// -------------------------------------------------------------------------------------

	public static final String book_id = "id";
	public static final String book_title = "title";
	public static final String book_isbn10 = "isbn10";
	public static final String book_isbn13 = "isbn13";
	public static final String book_publisher_id = "publisher_id";
	
	public static final String join_book_id = "book_id";
	public static final String join_author_id = "author_id";
	
	public static final String author_id = GenericJDBCAuthorDAO.author_id;
	public static final String author_name = GenericJDBCAuthorDAO.author_name;
	public static final String publisher_id = GenericJDBCPublisherDAO.publisher_id;
	public static final String publisher_name = GenericJDBCPublisherDAO.publisher_name;
	
	// -------------------------------------------------------------------------------------

	protected static final String insert_book_with_publisher = 
		"INSERT " +
			"INTO " + book_table + " ( " + book_title + ", " + book_isbn10 + ", " + book_isbn13 + ", " + book_publisher_id + " ) " +
			"VALUES (?,?,?,?) ;"
		;

	protected static final String insert_book_author = 
		"INSERT " +
			"INTO " + authorbook_jointable + " ( " + join_book_id + ", " + join_author_id + " ) " +
			"VALUES (?,?) ;"
		;
	
	protected static final String _get_book = 
		"SELECT * " +
			"FROM " + book_table + " as B " + 
			"LEFT JOIN " + publisher_table + " as P ON B." + book_publisher_id + " = P." + publisher_id + " " +
			"LEFT JOIN " + authorbook_jointable + " as AB ON B." + book_id + " = AB." + join_book_id + " " +
			"LEFT JOIN " + author_table + " as A ON AB." + join_author_id + " = A." + author_id + " "
		;
	
	protected static final String get_book_by_id = 
			_get_book +
			"WHERE B." + book_id + " = ? ;"
		;
	
	protected static final String get_book_by_title = 
			_get_book +
			"WHERE B." + book_title + " = ? ;"
		;

	protected static final String get_book_by_isbn10 = 
			_get_book +
			"WHERE B." + book_isbn10 + " = ? ;"
		;

	protected static final String get_book_by_isbn13 = 
			_get_book +
			"WHERE B." + book_isbn13 + " = ? ;"
		;

	protected static final String get_all_books = 
			_get_book +
			";"
		;

	protected static final String get_books_by_author = 
			_get_book +
			"WHERE B." + book_id + " IN (" +
				"SELECT " + join_book_id + " " +
				"FROM " + authorbook_jointable + " " +
				"WHERE "+ join_author_id + " = ? " +
			") ORDER BY B." + book_id +";"
		;

	protected static final String get_books_by_publisher = 
			_get_book +
			"WHERE B." + book_publisher_id + " = ? ;"
		;

	static final String remove_book_by_id = 
		"DELETE " +
			"FROM " + book_table + " " +
			"WHERE " + book_id + " = ? ;"
		;

	static final String remove_authorjoins_by_id = 
		"DELETE " +
			"FROM " + authorbook_jointable + " " +
			"WHERE " + join_book_id + " = ? ;"
		;

	protected abstract String getLastInsert();

	// -------------------------------------------------------------------------------------

	protected abstract String getCreate();
	protected abstract String getCreateJoin();
	
	protected static final String drop = 
		"DROP " +
			"TABLE " + book_table + " ;"
		;

	protected static final String drop_join = 
		"DROP " +
			"TABLE " + authorbook_jointable + " ;"
		;
	
	// ======================================================================================

	public int addBook(Book book) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -1;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( book == null )  {
			getCurrentJDBCFactory().getLogger().error("failed to insert a null book");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(insert_book_with_publisher);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, book.getTitle());
			prep_stmt.setString(2, book.getIsbn10());
			prep_stmt.setString(3, book.getIsbn13());
			prep_stmt.setInt(4, book.getPublisher().getId());
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
			getCurrentJDBCFactory().getLogger().error("failed to insert book\n",e);
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
		int lastInsertId = getLastInsertId(conn);

		// Aggiungo i join con gli autori, supposti già esistenti!!!
		for ( Author author : book.getAuthors() ) {
			try {
				// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
				PreparedStatement prep_stmt = conn.prepareStatement(insert_book_author);
				// --- b. Pulisci e imposta i parametri (se ve ne sono)
				prep_stmt.clearParameters();
				prep_stmt.setInt(1, lastInsertId);
				prep_stmt.setInt(2, author.getId());
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
				getCurrentJDBCFactory().getLogger().error("failed to insert book author",e);
				e.printStackTrace();
				result = -2;
			}
		}

		// tutto è andato bene
		result = lastInsertId;
		
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
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve id of last inserted publisher",e);
			e.printStackTrace();
		}
		return result;
	}

	// --------------------------------------------------------------------------------------------------------

	public int deleteBook(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -1;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( id < 0 )  {
			getCurrentJDBCFactory().getLogger().error("failed to delete a book with a negative id");
			return result;
		}
		if ( getBookById(id) == null ) {
			getCurrentJDBCFactory().getLogger().error("failed to delete a non-existing book");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(remove_book_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
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
			getCurrentJDBCFactory().getLogger().error("failed to delete book with id = " + id,e);
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return ( result < 1 ) ? result : deleteBookAuthors(id);
	}
	
	protected int deleteBookAuthors(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		int result = -1;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(remove_authorjoins_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
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
			getCurrentJDBCFactory().getLogger().error("failed to delete book with id = " + id,e);
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

	public Book getBookById(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Book result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( id < 0 )  {
			getCurrentJDBCFactory().getLogger().error("failed to read book with negative id");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_book_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() )
				result = extractBook(rs);
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve book with id = " + id,e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	private Book extractBook(ResultSet rs)
	throws Exception {
		if ( rs.isAfterLast() )
			return null;
		int book_id_in_the_result_set = rs.getInt(1);
		Book book = new Book();
		book.setId(-1);
		do {
			if ( rs.getInt(1) != book_id_in_the_result_set )
				// fine del gruppo di tuple relative al book da leggere
				break;
			if (book.getId() == -1) {
				book.setId(rs.getInt(1));							// book_table.id
				book.setTitle(rs.getString(2));						// book_table.title
				book.setIsbn10(rs.getString(3));					// book_table.isbn10
				book.setIsbn13(rs.getString(4));					// book_table.isbn13
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt(6));						// book_table left join publisher_table on publisher_id
				publisher.setName(rs.getString(7));					// publisher_table.name
				book.setPublisher(publisher);
			}
			Author author = new Author();
			author.setId(rs.getInt(10));							// (book_table left join on jointable on book_id) left join on author_table on author_id
			author.setName(rs.getString(11));						// author_table.name
			if ( book.getAuthors() == null ) book.setAuthors( new HashSet<Author>() );
			book.getAuthors().add(author);
		} while ( rs.next() );
		return book;
	}

	public Book getBookByISBN10(String isbn10) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Book result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( isbn10 == null || isbn10.equals("") )  {
			getCurrentJDBCFactory().getLogger().error("failed to read book with no isbn10");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_book_by_isbn10);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, isbn10);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() )
				result = extractBook(rs);
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve book with isbn10 = " + isbn10,e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public Book getBookByISBN13(String isbn13) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Book result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( isbn13 == null || isbn13.equals("") )  {
			getCurrentJDBCFactory().getLogger().error("failed to read book with no isbn13");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_book_by_isbn13);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, isbn13);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() )
				result = extractBook(rs);
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve book with isbn13 = " + isbn13,e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public Book getBookByTitle(String title) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		Book result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( title == null || title.equals("") )  {
			getCurrentJDBCFactory().getLogger().error("failed to read book with no title");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_book_by_title);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setString(1, title);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() )
				result = extractBook(rs);
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve book with title = " + title,e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public List<Book> getAllBooks() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<Book> result = new ArrayList<Book>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_all_books);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			Book aBook = null;
			if ( rs.next() )
				aBook = extractBook(rs);
			while ( aBook != null ) {
				result.add(aBook);
				aBook = extractBook(rs);
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve books",e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public List<Book> getAllBooksByAuthor(Author author) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<Book> result = new ArrayList<Book>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( author == null ) {
			getCurrentJDBCFactory().getLogger().error("failed to read book with no author");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_books_by_author);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1,author.getId());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			Book aBook = null;
			if ( rs.next() )
				aBook = extractBook(rs);
			while ( aBook != null ) {
				result.add(aBook);
				aBook = extractBook(rs);
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve books with author id = " + author.getId(),e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	public List<Book> getAllBooksByPublisher(Publisher publisher) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<Book> result = new ArrayList<Book>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( publisher == null ) {
			getCurrentJDBCFactory().getLogger().error("failed to read book with no publisher");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = getCurrentJDBCFactory().getConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(get_books_by_publisher);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1,publisher.getId());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			Book aBook = null;
			if ( rs.next() )
				aBook = extractBook(rs);
			while ( aBook != null ) {
				result.add(aBook);
				aBook = extractBook(rs);
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			getCurrentJDBCFactory().getLogger().error("failed to retrieve books with publisher id = " + publisher.getId(),e);
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
			getCurrentJDBCFactory().getLogger().debug("failed to create book table",e);
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
			getCurrentJDBCFactory().getLogger().debug("failed to drop book table",e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	/**
	 * Creazione della table
	 */
	protected boolean createJoinTable() {
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
			stmt.execute(getCreateJoin());
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
			getCurrentJDBCFactory().getLogger().debug("failed to create author book join table",e);
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
	protected boolean dropJoinTable() {
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
			stmt.execute(drop_join);
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
			getCurrentJDBCFactory().getLogger().debug("failed to drop author book join table",e);
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			getCurrentJDBCFactory().releaseConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

}
