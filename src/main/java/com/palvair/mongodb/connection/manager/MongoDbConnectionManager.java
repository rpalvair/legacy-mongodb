package com.palvair.mongodb.connection.manager;

import com.mongodb.*;
import lombok.extern.log4j.Log4j;

import javax.naming.NamingException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by widdy on 27/08/14.
 */
@Log4j
public class MongoDbConnectionManager implements ConnectionManager {

    public MongoDbConnectionManager() {

    }

    public MongoClient getConnection(final String uri) throws NamingException, SQLException, UnknownHostException {
        final MongoClientURI mongoClientURI = new MongoClientURI(uri);
        return new MongoClient(mongoClientURI);
    }

    public MongoClient getConnection() throws NamingException, SQLException, UnknownHostException {
        return new MongoClient();
    }

    public MongoClient getConnectionWithCredentials(String user, String db, char[] password, String uri) throws NamingException, SQLException, UnknownHostException {
        MongoCredential credential = MongoCredential.createMongoCRCredential(user, db, password);
        return new MongoClient(new ServerAddress(uri), Arrays.asList(credential));
    }

    public DBCollection getDbCollection(final String dbName, final String collection) throws NamingException, UnknownHostException, SQLException {
        return getDB(dbName).getCollection(collection);
    }

    public DB getDB(final String dbName) throws NamingException, UnknownHostException, SQLException {
        final MongoClient mongoClient = getConnection();
        return mongoClient.getDB(dbName);
    }
}
