package it.unibo.sdls.sampleproject.dao.test;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class BookDAOTests
extends TestsCommon {

	// -------------------------------------------------
	// --- Initialization ------------------------------
	// -------------------------------------------------
	
	@BeforeClass
	public static void thisWorksBeforeAllTests() 
	throws Exception {
	}

	@Before
	public void thisIsTheOldSetUp() 
	throws Exception {
		super.init();
		super.cleanDatabases();
		Author author1 = createAuthorWithoutBook(authorName1);
		Author author2 = createAuthorWithoutBook(authorName2);
		author1.setId( authorDAO.insertAuthor(author1) );
		author2.setId( authorDAO.insertAuthor(author2) );
		Publisher publisher = createPublisherWithoutBook(publisherName1);
		publisher.setId( publisherDAO.insertPublisher(publisher) );
		Book book = createBook(bookTitle1, new Author[] { author1, author2 }, publisher);
		bookDAO.addBook(book);	
	}
	
	@After
	public void thisIsTheOldTearDown() 
	throws Exception {
		super.cleanDatabases();
	}

	@AfterClass
	public static void thisWorkAfterAllTests() 
	throws Exception {
	}

	// -------------------------------------------------
	// --- Tests ---------------------------------------
	// -------------------------------------------------

	@Test 
	public void getBookByTitle() {
		Book book = bookDAO.getBookByTitle(bookTitle1);
		
		Set<Author> authors = book.getAuthors();
		String[] actualAuthors = new String[2];
		int index = 0;
		for(Iterator<Author> iterator = authors.iterator(); iterator.hasNext(); ) {
			actualAuthors[index] = iterator.next().getName();
			index++;
		}
		Arrays.sort(actualAuthors);
		
		Publisher publisher = book.getPublisher();
		
		Assert.assertEquals(bookTitle1, book.getTitle());
		Assert.assertArrayEquals(book1_authorsNames, actualAuthors);
		Assert.assertEquals(publisherName1, publisher.getName());
	}
	
	 @Test
	 public void deleteBook() {
		 // since id is auto-incremented by the DBMS we necessarily have to
		 // fetch the only Book object from the DB to know its id and then 
		 // attempt its removal
		 Book book = bookDAO.getBookByTitle(bookTitle1);
		 int id = book.getId();
		 bookDAO.deleteBook(id);
		 Book retrievedBook = bookDAO.getBookById(id);
		 Assert.assertNull(retrievedBook);
	 }

}
