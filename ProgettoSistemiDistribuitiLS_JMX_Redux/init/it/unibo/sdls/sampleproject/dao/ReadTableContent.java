package it.unibo.sdls.sampleproject.dao;

import it.unibo.sdls.sampleproject.dao.jdbc.pooled.hsqldb.PooledHsqldbJDBCDAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.mysql.PooledMySqlJDBCDAOFactory;

import java.util.List;

public class ReadTableContent {

	public static void main(String[] args) {

		// Scelta dao
		String dao = null;
		dao = PooledMySqlJDBCDAOFactory.class.getCanonicalName();
		if ( args.length > 0 ) {
			dao = args[0];
		}
		
		// recupero dei dao
		BookDAO bookDAO = DAOFactory.getDAOFactory(dao).getBookDAO();
		AuthorDAO authorDAO = DAOFactory.getDAOFactory(dao).getAuthorDAO();
		PublisherDAO publisherDAO = DAOFactory.getDAOFactory(dao).getPublisherDAO();

		// riletture
		List<Book> books = bookDAO.getAllBooks();
		for ( Book book : books ) {
			Utilities.out( Utilities.print( book ) );
		}
		
		List<Author> authors = authorDAO.findAllAuthors();
		for ( Author author : authors ) {
			Utilities.out( Utilities.print( author ) );
		}

		List<Publisher> publishers = publisherDAO.findAllPublishers();
		for ( Publisher publisher : publishers ) {
			Utilities.out( Utilities.print( publisher ) );
		}

	}

}
