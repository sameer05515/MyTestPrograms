package com.isuite.iagent.commons.db.util;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

public class ISTKBMySqlDialect extends MySQL5Dialect {
	
	public ISTKBMySqlDialect() {
		super();
		registerColumnType(Types.VARCHAR, 1000, "mediumtext");
		registerColumnType(Types.VARCHAR, 2000, "text");
		registerColumnType(Types.VARCHAR, 5000, "longtext");
		
		registerColumnType(Types.BLOB, 1000, "mediumblob");
		registerColumnType(Types.BLOB, 2000, "blob");
		registerColumnType(Types.BLOB, 5000, "longblob");
		
	}

}
