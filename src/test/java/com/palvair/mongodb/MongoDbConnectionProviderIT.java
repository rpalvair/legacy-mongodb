package com.palvair.mongodb;

import com.google.common.base.Stopwatch;
import com.mongodb.*;
import com.palvair.mongodb.connection.manager.CollectionHelper;
import com.palvair.mongodb.connection.manager.MongoDbConnectionManager;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NamingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import static org.junit.Assert.assertNotNull;

/**
 * @author rpalvair
 */
@RunWith(MockitoJUnitRunner.class)
@Log4j
public class MongoDbConnectionProviderIT {

    private final MongoDbConnectionManager unitUnderTest = new MongoDbConnectionManager();

    private final CollectionHelper collectionHelper = new CollectionHelper(unitUnderTest);

    private final int NB_RECORDS = 1000;

    @Before
    public void setUp() {
        try {
            final DBCollection dbCollection = unitUnderTest.getDbCollection("mydb", "testData");
            log.info("initial count = "+dbCollection.count());
            log.info("Attempt to insert "+NB_RECORDS+" of records...");
            final Stopwatch stopwatch = Stopwatch.createStarted();
            for (int i = 0; i < NB_RECORDS; i++) {
                final DBObject insert = new BasicDBObject();
                insert.put("name", "Billy");
                insert.put("age", "30");
                insert.put("timestamp",new Date());
                dbCollection.save(insert);
            }
            stopwatch.stop();
            log.info("Terminated in " + stopwatch.toString());
        } catch (Exception e) {
            log.error(e);
        }
    }

    //@After
    public void after() {
        try {
            final DBCollection dbCollection = unitUnderTest.getDbCollection("mydb", "testData");
            dbCollection.drop();
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Test
    public void getConnection_Localhost_ShouldReturnAMongoClient() throws NamingException, UnknownHostException, SQLException {
        final DBCollection dbCollection = unitUnderTest.getDbCollection("mydb", "testData");
        assertNotNull(dbCollection);

        final long count = dbCollection.count();
        //log.info("count = " + count);
        final DBCursor dbCursor = dbCollection.find();
        final Iterator<DBObject> iterator = dbCursor.iterator();
        /*while (iterator.hasNext()) {
            final DBObject dbObject = iterator.next();
            //log.info(dbObject.toString());
        }*/
    }
}
