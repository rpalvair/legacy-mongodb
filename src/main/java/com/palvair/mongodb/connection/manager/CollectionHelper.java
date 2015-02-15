package com.palvair.mongodb.connection.manager;

import com.mongodb.DBCollection;

import javax.naming.NamingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Created by ruddy on 15/02/15.
 */
public class CollectionHelper {

    private final ConnectionManager connectionManager;

    public CollectionHelper(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public long count(final String dbName, final String dbCollectionName) throws NamingException, UnknownHostException, SQLException {
        final DBCollection dbCollection = connectionManager.getDbCollection(dbName, dbCollectionName);
        return dbCollection.count();
    }
}
