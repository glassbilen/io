package me.glassbilen.file.types;

import me.glassbilen.file.ConfigVarData;

public class BooleanConfigVarData implements ConfigVarData {
	private boolean defaultValue;
	private boolean state;
	private String genericDoc;
	private String trueDoc;
	private String falseDoc;

	public BooleanConfigVarData(boolean defaultValue, String genericDoc, String trueDoc, String falseDoc) {
		this.defaultValue = defaultValue;
		this.state = defaultValue;
		this.genericDoc = genericDoc;
		this.trueDoc = trueDoc;
		this.falseDoc = falseDoc;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getGenericDoc() {
		return genericDoc;
	}

	public void setGenericDoc(String genericDoc) {
		this.genericDoc = genericDoc;
	}

	public String getTrueDoc() {
		return trueDoc;
	}

	public void setTrueDoc(String trueDoc) {
		this.trueDoc = trueDoc;
	}

	public String getFalseDoc() {
		return falseDoc;
	}

	public void setFalseDoc(String falseDoc) {
		this.falseDoc = falseDoc;
	}

	@Override
	public Object getData() {
		return state;
	}

	@Override
	public void setData(Object obj) {
		state = Boolean.valueOf(obj.toString());
	}

	@Override
	public boolean isDataType(String data) {
		if (data.equals("true") || data.equals("false")) {
			return true;
		}

		return false;
	}

	@Override
	public String getPreDoc() {
		return "//variable description: " + genericDoc + separator + "//default value: " + defaultValue + separator
				+ "//true: " + trueDoc + separator + "//false: " + falseDoc;
	}
}
