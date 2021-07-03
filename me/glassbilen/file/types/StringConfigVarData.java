package me.glassbilen.file.types;

import me.glassbilen.file.ConfigVarData;

public class StringConfigVarData implements ConfigVarData {
	private String defaultValue;
	private String value;
	private String genericDoc;

	public StringConfigVarData(String defaultValue, String genericDoc) {
		if (defaultValue != null) {
			if (!defaultValue.startsWith("\"") || !defaultValue.endsWith("\"")) {
				defaultValue = "\"" + defaultValue + "\"";
			}
		}

		this.defaultValue = defaultValue;
		this.value = this.defaultValue;
		this.genericDoc = genericDoc;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGenericDoc() {
		return genericDoc;
	}

	public void setGenericDoc(String genericDoc) {
		this.genericDoc = genericDoc;
	}

	@Override
	public Object getData() {
		return value.length() <= 2 ? "" : value.substring(1, value.length() - 1);
	}

	@Override
	public void setData(Object obj) {
		value = (String) obj;
	}

	@Override
	public boolean isDataType(String data) {
		if (data.startsWith("\"") && data.endsWith("\"")) {
			return true;
		}

		return false;
	}

	@Override
	public String getPreDoc() {
		return "//variable description: " + genericDoc + separator + "//default value: " + defaultValue;
	}
}
