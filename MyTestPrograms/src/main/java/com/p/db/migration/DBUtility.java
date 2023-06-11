package com.p.db.migration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.annotations.common.util.StringHelper;

public class DBUtility {
    private static final String[] TYPES = {"TABLE", "VIEW"};
    public static void getTableMetadata(Connection jdbcConnection, String tableNamePattern, String schema, String catalog, boolean isQuoted) throws HibernateException {
            try {
                DatabaseMetaData meta = jdbcConnection.getMetaData();
                ResultSet rs = null;
                try {
                    if ( (isQuoted && meta.storesMixedCaseQuotedIdentifiers())) {
                        rs = meta.getTables(catalog, schema, tableNamePattern, TYPES);
                    } else if ( (isQuoted && meta.storesUpperCaseQuotedIdentifiers())
                        || (!isQuoted && meta.storesUpperCaseIdentifiers() )) {
                        rs = meta.getTables(
                                StringHelper.toUpperCase(catalog),
                                StringHelper.toUpperCase(schema),
                                StringHelper.toUpperCase(tableNamePattern),
                                TYPES
                            );
                    }
                    else if ( (isQuoted && meta.storesLowerCaseQuotedIdentifiers())
                            || (!isQuoted && meta.storesLowerCaseIdentifiers() )) {
                        rs = meta.getTables( 
                                StringHelper.toLowerCase( catalog ),
                                StringHelper.toLowerCase(schema), 
                                StringHelper.toLowerCase(tableNamePattern), 
                                TYPES 
                            );
                    }
                    else {
                        rs = meta.getTables(catalog, schema, tableNamePattern, TYPES);
                    }

                    while ( rs.next() ) {
                        String tableName = rs.getString("TABLE_NAME");
                        System.out.println("table = " + tableName);
                    }



                }
                finally {
                    if (rs!=null) rs.close();
                }
            }
            catch (Exception sqlException) {
                // TODO 
                sqlException.printStackTrace();
            }

    }

    public static void main(String[] args) {
        Connection jdbcConnection;
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/topic-mgmt?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            getTableMetadata(jdbcConnection, "tbl%", null, null, false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}