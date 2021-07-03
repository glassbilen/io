package me.glassbilen.file.types;

import java.util.regex.Pattern;

import me.glassbilen.file.ConfigVarData;

public class DoubleConfigVarData implements ConfigVarData {
	private double defaultValue;
	private double value;
	private double minValue;
	private double maxValue;
	private String genericDoc;

	public DoubleConfigVarData(double defaultValue, double minValue, double maxValue, String genericDoc) {
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

	public double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		if (getValue() < minValue) {
			setValue(minValue);
		}

		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
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
		double val = Double.valueOf(obj.toString());

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
			Double.valueOf(data);
			String[] split = ("" + data).split(Pattern.quote("."));

			if (split.length < 2) {
				return false;
			}

			if (split[1].length() <= 8) {
				return false;
			}

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
