package me.glassbilen.file.types;

import java.util.regex.Pattern;

import me.glassbilen.file.ConfigVarData;

public class FloatConfigVarData implements ConfigVarData {
	private float defaultValue;
	private float value;
	private float minValue;
	private float maxValue;
	private String genericDoc;

	public FloatConfigVarData(float defaultValue, float minValue, float maxValue, String genericDoc) {
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

	public float getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(float defaultValue) {
		this.defaultValue = defaultValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		if (getValue() < minValue) {
			setValue(minValue);
		}

		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
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
		float val = Float.valueOf(obj.toString());

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
			Float.valueOf(data);
			String[] split = ("" + data).split(Pattern.quote("."));

			if (split.length < 2) {
				return false;
			}

			if (split[1].length() > 8) {
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
