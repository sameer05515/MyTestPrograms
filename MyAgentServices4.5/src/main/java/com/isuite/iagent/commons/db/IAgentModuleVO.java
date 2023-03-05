package com.isuite.iagent.commons.db;

public class IAgentModuleVO {
	@Override
	public String toString() {
		return "IAgentModuleVO [moduleName=" + moduleName + ", isEnabled="
				+ isEnabled + ", moduleClassName=" + moduleClassName + "]";
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getModuleClassName() {
		return moduleClassName;
	}
	public void setModuleClassName(String moduleClassName) {
		this.moduleClassName = moduleClassName;
	}
	private String moduleName;
	private boolean isEnabled;
	private String moduleClassName;
}
