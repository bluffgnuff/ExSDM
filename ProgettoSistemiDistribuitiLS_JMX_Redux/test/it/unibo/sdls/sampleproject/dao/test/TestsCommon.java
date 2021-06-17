package it.unibo.sdls.sampleproject.dao.test;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.AuthorDAO;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.Publisher;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public abstract class TestsCommon {

	protected static final String bookTitle1 = "Spring in Action";
	protected static final String bookTitle2 = "XDoclet in Action";
	protected static final String authorName1 = "Craig Walls";
	protected static final String authorName2 = "Ryan Breidenbach";
	protected static final String authorName3 = "Norman Richards";
	protected static final String[] book1_authorsNames = {authorName1, authorName2};
	protected static final String[] book2_authorsNames = {authorName1, authorName3};
	protected static final String publisherName1 = "Manning";
	protected static final String publisherName2 = "Wrox";
	
	protected DAOFactory daoFactory = null;
	protected BookDAO bookDAO = null;
	protected AuthorDAO authorDAO = null;
	protected PublisherDAO publisherDAO = null;

	abstract protected String getDAOFactoryCanonicalClassName();
	
	// -------------------------------------------------
	// --- Initialization ------------------------------
	// -------------------------------------------------

	protected void await() {
		try { Thread.sleep(500); } catch (Exception e) { }
	}

	protected void init() {
		await();
		daoFactory = DAOFactory.getDAOFactory(getDAOFactoryCanonicalClassName());
		bookDAO = daoFactory.getBookDAO();
		authorDAO = daoFactory.getAuthorDAO();
		publisherDAO = daoFactory.getPublisherDAO();
	}

	protected void cleanDatabases() {
		List<Book> books = bookDAO.getAllBooks();
		for ( Book book : books ) bookDAO.deleteBook(book.getId());
		List<Author> authors = authorDAO.findAllAuthors();
		for ( Author author : authors ) authorDAO.removeAuthorById(author.getId());
		List<Publisher> publishers = publisherDAO.findAllPublishers();
		for ( Publisher publisher : publishers ) publisherDAO.removePublisherById(publisher.getId());
	}
	
	//------------------------
	// Tests
	// -----------------------
	
	@Test
	public void retrieval() {
		Assert.assertNotNull(bookDAO);
		Assert.assertNotNull(authorDAO);
		Assert.assertNotNull(publisherDAO);
	}
	
	//------------------------
	// Helpers
	// -----------------------

	protected Book createBook(String title, String[] authors, String publisherName, String isbn10, String isbn13) {
		Book book = new Book();
		book.setTitle(title);
		
		// Authors
		Set<Author> authorsSet = new HashSet<Author>();
		try {
			for(int i=0; ; i++) {
				// checking if author already exists in the DB
				Author author = authorDAO.findAuthorByName(authors[i]);
				if (author == null) {
					author = new Author();
					author.setName(authors[i]);
				}
				authorsSet.add(author);
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			// ignore; this just means that i has reached the end of the array
		}
		book.setAuthors(authorsSet);
		
		Publisher publisher = new Publisher();
		publisher.setName(publisherName);
		book.setPublisher(publisher);
		
		book.setIsbn10(isbn10);
		book.setIsbn13(isbn13);
		
		return book;
	}
	
	protected Book createBook(String bookTitle, Author[] authors, Publisher publisher) {
		Book book = new Book();
		book.setTitle(bookTitle);
		book.setIsbn10(bookTitle+"_ISBN10");
		book.setIsbn13(bookTitle+"_ISBN13");
		book.setPublisher(publisher);
		if ( book.getAuthors() == null )
			book.setAuthors( new HashSet<Author>() );
		for ( Author author : authors ) 
			book.getAuthors().add(author);
		return book;
	}

	protected Author createAuthorWithoutBook(String name) {
		Author author = new Author();
		author.setName(name);
		return author;
	}
	
	protected Publisher createPublisherWithoutBook(String name) {
		Publisher publisher = new Publisher();
		publisher.setName(name);
		return publisher;
	}

}
