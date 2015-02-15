package com.palvair.mongodb.connection.manager;


import com.mongodb.DBCollection;

import javax.naming.NamingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public interface ConnectionManager {
    public DBCollection getDbCollection(final String dbName, final String collection) throws NamingException, UnknownHostException, SQLException;
}