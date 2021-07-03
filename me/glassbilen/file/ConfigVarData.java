package me.glassbilen.file;

public interface ConfigVarData {
	String separator = System.getProperty("line.separator");
	
	Object getData();
	void setData(Object data);
	
	boolean isDataType(String data);
	
	String getPreDoc();
}
