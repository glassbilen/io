package me.glassbilen.file.types;

import me.glassbilen.file.ConfigVarData;

public class LongConfigVarData implements ConfigVarData {
	private long defaultValue;
	private long value;
	private long minValue;
	private long maxValue;
	private String genericDoc;

	public LongConfigVarData(long defaultValue, long minValue, long maxValue, String genericDoc) {
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

	public long getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(long defaultValue) {
		this.defaultValue = defaultValue;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getMinValue() {
		return minValue;
	}

	public void setMinValue(long minValue) {
		if (getValue() < minValue)
			setValue(minValue);

		this.minValue = minValue;
	}

	public long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(long maxValue) {
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
		long val = Long.valueOf(obj.toString());

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
			Long.valueOf(data);

			if (Math.abs(Long.valueOf(data)) < Integer.MAX_VALUE) {
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
