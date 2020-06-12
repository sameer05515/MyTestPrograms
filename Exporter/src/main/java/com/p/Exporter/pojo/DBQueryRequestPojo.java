package com.p.Exporter.pojo;

public class DBQueryRequestPojo {
	
	protected DataSourcePojo dataSourcePojo;
	protected String query;
	public DataSourcePojo getDataSourcePojo() {
		return dataSourcePojo;
	}
	public void setDataSourcePojo(DataSourcePojo dataSourcePojo) {
		this.dataSourcePojo = dataSourcePojo;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	@Override
	public String toString() {
		return "DBQueryRequestPojo [dataSourcePojo=" + dataSourcePojo + ", query=" + query + "]";
	}

}
