package com.isuite.iagent.commons.db.util;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;

public class ISTSqlServerDialect extends SQLServerDialect {
	
	public ISTSqlServerDialect() {
		super();
		registerColumnType(Types.VARCHAR, "nvarchar($l)");
		registerColumnType(Types.CHAR, "nchar($l)");
		registerColumnType(Types.CLOB, "nvarchar(max)");
		
	}

}
