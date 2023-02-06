package com.isuite.iagent.commons.db.util;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;

public class ISTOracle10gDialect extends Oracle10gDialect {
	
	public ISTOracle10gDialect() {
		super();
		registerColumnType(Types.CHAR, "nchar($l)");
		registerColumnType(Types.NCHAR, "nchar($l)");
		registerColumnType(Types.VARCHAR, 1000, "nvarchar2($l)");
	}
	
	
}
