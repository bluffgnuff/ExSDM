package it.unibo.sdls.sampleproject.dao.test;

import it.unibo.sdls.sampleproject.dao.Author;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class AuthorDAOTests
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
		authorDAO.insertAuthor(author1);
		Author author2 = createAuthorWithoutBook(authorName2);
		authorDAO.insertAuthor(author2);
		Author author3 = createAuthorWithoutBook(authorName3);
		authorDAO.insertAuthor(author3);
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
	public void findAuthorByName() {
		Author author = authorDAO.findAuthorByName(authorName1);
		Assert.assertEquals(authorName1, author.getName());
	}
	
	@Test
	public void findAllAuthors() {
		List<Author> authorsList = authorDAO.findAllAuthors();
		Assert.assertEquals(3, authorsList.size());
	}

}
