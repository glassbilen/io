package me.glassbilen.file.types;

import me.glassbilen.file.ConfigVarData;

public class CharacterConfigVarData implements ConfigVarData {
	private char defaultValue;
	private char value;
	private String genericDoc;

	public CharacterConfigVarData(char defaultValue, String genericDoc) {
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.genericDoc = genericDoc;
	}

	public char getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(char defaultValue) {
		this.defaultValue = defaultValue;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
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
		return value;
	}

	@Override
	public void setData(Object obj) {
		value = obj.toString().charAt(0);
	}

	@Override
	public boolean isDataType(String data) {
		if (data.length() == 1) {
			return true;
		}

		return false;
	}

	@Override
	public String getPreDoc() {
		return "//variable description: " + genericDoc + separator + "//default value: " + defaultValue;
	}
}
