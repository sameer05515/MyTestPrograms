package com.isuite.iagent.commons.db.util;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

public class ISTMySqlDialect extends MySQL5Dialect {
	
	public ISTMySqlDialect() {
		super();
		registerColumnType(Types.VARCHAR, "varchar");
		registerColumnType(Types.CHAR, "char");
	}

}
