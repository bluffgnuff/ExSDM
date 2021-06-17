package it.unibo.sdls.sampleproject.dao;

import it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb.PooledHsqldbJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql.PooledMySqlJDBCDAOFactory;

import java.util.HashSet;

public class InitializeTables {

	public static void main(String[] args) {

		// Scelta db
		String dao = null;
		dao = PooledMySqlJDBCDAOFactory.class.getCanonicalName();
		if ( args.length > 0 )
			dao = args[0];
		
		// recupero dei dao
		BookDAO bookDAO = DAOFactory.getDAOFactory(dao).getBookDAO();
		AuthorDAO authorDAO = DAOFactory.getDAOFactory(dao).getAuthorDAO();
		PublisherDAO publisherDAO = DAOFactory.getDAOFactory(dao).getPublisherDAO();
		
		// tuple di prova
		Publisher p1 = new Publisher();
		p1.setName("publisher1");
		int p1_id = publisherDAO.insertPublisher(p1);
		p1.setId(p1_id);

		Publisher p2 = new Publisher();
		p2.setName("publisher2");
		int p2_id = publisherDAO.insertPublisher(p2);
		p2.setId(p2_id);

		Author a1 = new Author();
		a1.setName("author1");
		int a1_id = authorDAO.insertAuthor(a1);
		a1.setId(a1_id);

		Author a2 = new Author();
		a2.setName("author2");
		int a2_id = authorDAO.insertAuthor(a2);
		a2.setId(a2_id);

		Author a3 = new Author();
		a3.setName("author3");
		int a3_id = authorDAO.insertAuthor(a3);
		a3.setId(a3_id);

		Book b1 = new Book();
		b1.setTitle("book1");
		b1.setIsbn10("b1_10");
		b1.setIsbn13("b1_13");
		b1.setPublisher(p1);
		if ( b1.getAuthors() == null ) b1.setAuthors( new HashSet<Author>() );
		b1.getAuthors().add(a1);
		b1.getAuthors().add(a2);
		int b1_id = bookDAO.addBook(b1);
		b1.setId(b1_id);
		
		Book b2 = new Book();
		b2.setTitle("book2");
		b2.setIsbn10("b2_10");
		b2.setIsbn13("b2_13");
		b2.setPublisher(p2);
		if ( b2.getAuthors() == null ) b2.setAuthors( new HashSet<Author>() );
		b2.getAuthors().add(a2);
		b2.getAuthors().add(a3);
		int b2_id = bookDAO.addBook(b2);
		b2.setId(b2_id);
		
		// riletture
		Utilities.out( Utilities.print( publisherDAO.findPublisherById( p1.getId() ) ) );
		Utilities.out( Utilities.print( publisherDAO.findPublisherByName( p2.getName() ) ) );
		Utilities.out( Utilities.print( authorDAO.findAuthorById( a1.getId() ) ) );
		Utilities.out( Utilities.print( authorDAO.findAuthorByName( a2.getName() ) ) );
		Utilities.out( Utilities.print( authorDAO.findAuthorByName( a3.getName() ) ) );
		Utilities.out( Utilities.print( bookDAO.getBookById( b1.getId() ) ) );
		Utilities.out( Utilities.print( bookDAO.getBookById( b2.getId() ) ) );

	}
	
}
