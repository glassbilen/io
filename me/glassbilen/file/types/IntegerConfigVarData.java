package me.glassbilen.file.types;

import me.glassbilen.file.ConfigVarData;

public class IntegerConfigVarData implements ConfigVarData {
	private int defaultValue;
	private int value;
	private int minValue;
	private int maxValue;
	private String genericDoc;

	public IntegerConfigVarData(int defaultValue, int minValue, int maxValue, String genericDoc) {
		if (defaultValue > maxValue) {
			defaultValue = maxValue;
		}

		if (defaultValue < minValue) {
			defaultValue = minValue;
		}

		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.genericDoc = genericDoc;
	}

	public int getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		if (getValue() < minValue) {
			setValue(minValue);
		}

		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		if (getValue() > maxValue) {
			setValue(maxValue);
		}

		this.maxValue = maxValue;
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
		int val = Integer.valueOf(obj.toString());

		if (val > getMaxValue()) {
			val = getMaxValue();
		}
		
		if (val < getMinValue()) {
			val = getMinValue();
		}
		
		setValue(val);
	}

	@Override
	public boolean isDataType(String data) {
		try {
			Integer.valueOf(data);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getPreDoc() {
		return "//variable description: " + genericDoc + separator + "//default value: " + defaultValue + separator
				+ "//minimum value: " + minValue + separator + "//maximum value: " + maxValue;
	}
}
