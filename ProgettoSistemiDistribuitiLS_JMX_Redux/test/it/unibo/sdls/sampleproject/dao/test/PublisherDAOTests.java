package it.unibo.sdls.sampleproject.dao.test;

import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class PublisherDAOTests 
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
		Publisher publisher1 = new Publisher(publisherName1);
		publisherDAO.insertPublisher(publisher1);
		Publisher publisher2 = new Publisher(publisherName2);
		publisherDAO.insertPublisher(publisher2);
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
	public void findPublisherByName() {
		Publisher publisher1 = publisherDAO.findPublisherByName(publisherName1);
		Assert.assertEquals(publisherName1, publisher1.getName());
		Publisher publisher2 = publisherDAO.findPublisherByName(publisherName2);
		Assert.assertEquals(publisherName2, publisher2.getName());
	}
	
	@Test
	public void findAllPublishers() {
		List<Publisher> publishersList = publisherDAO.findAllPublishers();
		Assert.assertEquals(2, publishersList.size());
	}

}
