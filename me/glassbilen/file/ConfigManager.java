package me.glassbilen.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.glassbilen.file.exceptions.ConfigOutdatedException;
import me.glassbilen.file.types.BooleanConfigVarData;
import me.glassbilen.file.types.CharacterConfigVarData;
import me.glassbilen.file.types.DoubleConfigVarData;
import me.glassbilen.file.types.FloatConfigVarData;
import me.glassbilen.file.types.IntegerConfigVarData;
import me.glassbilen.file.types.LongConfigVarData;
import me.glassbilen.file.types.StringConfigVarData;

public class ConfigManager {
	private String separator = System.getProperty("line.separator");

	private List<ConfigVarData> types;

	private Map<String, ConfigVarData> configValues;
	private File file;
	private boolean fresh;

	public ConfigManager(File file) {
		configValues = new LinkedHashMap<>();
		types = new ArrayList<>();

		registerConfigVarDataType(new BooleanConfigVarData(true, null, null, null));
		registerConfigVarDataType(new DoubleConfigVarData(0, 0, 0, null));
		registerConfigVarDataType(new FloatConfigVarData(0, 0, 0, null));
		registerConfigVarDataType(new LongConfigVarData(0, 0, 0, null));
		registerConfigVarDataType(new IntegerConfigVarData(0, 0, 0, null));
		registerConfigVarDataType(new CharacterConfigVarData(' ', null));
		registerConfigVarDataType(new StringConfigVarData(null, null));

		this.file = file;
	}

	public void registerConfigVarDataType(ConfigVarData type) {
		types.add(type);
	}

	public void prepareBoolean(String key, boolean state, String genericDoc, String trueDoc, String falseDoc) {
		prepareVar(key, new BooleanConfigVarData(state, genericDoc, trueDoc, falseDoc));
	}

	public void prepareCharacter(String key, char value, String genericDoc) {
		prepareVar(key, new CharacterConfigVarData(value, genericDoc));
	}

	public void prepareDouble(String key, double defaultValue, double minValue, double maxValue, String genericDoc) {
		prepareVar(key, new DoubleConfigVarData(defaultValue, minValue, maxValue, genericDoc));
	}

	public void prepareFloat(String key, float defaultValue, float minValue, float maxValue, String genericDoc) {
		prepareVar(key, new FloatConfigVarData(defaultValue, minValue, maxValue, genericDoc));
	}

	public void prepareInteger(String key, int defaultValue, int minValue, int maxValue, String genericDoc) {
		prepareVar(key, new IntegerConfigVarData(defaultValue, minValue, maxValue, genericDoc));
	}

	public void prepareLong(String key, long defaultValue, long minValue, long maxValue, String genericDoc) {
		prepareVar(key, new LongConfigVarData(defaultValue, minValue, maxValue, genericDoc));
	}

	public void prepareString(String key, String value, String genericDoc) {
		prepareVar(key, new StringConfigVarData(value, genericDoc));
	}

	public void prepareVar(String key, ConfigVarData data) {
		configValues.put(key, data);
	}

	public void init() throws ConfigOutdatedException, IOException {
		if (!file.exists()) {
			fresh = true;
			file.createNewFile();
		}

		Map<String, Class<? extends ConfigVarData>> tempData = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line;

			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.startsWith("//")) {
					continue;
				}

				if (!line.contains(":")) {
					continue;
				}

				String[] parts = line.split(":", 2);

				if (parts.length != 2) {
					continue;
				}

				parts[0] = parts[0].trim();
				parts[1] = parts[1].trim();

				Class<? extends ConfigVarData> dataTypeFound = null;

				for (ConfigVarData dataType : types) {
					if (dataType.isDataType(parts[1])) {
						dataTypeFound = dataType.getClass();
						break;
					}
				}

				tempData.put(parts[0], dataTypeFound);
			}
		}

		boolean isEmpty = tempData.size() == 0;
		boolean missingKey = false;

		if (!isEmpty) {
			Iterator<Entry<String, ConfigVarData>> iterator = configValues.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, ConfigVarData> entry = iterator.next();
				boolean found = false;

				for (String key : tempData.keySet()) {
					if (key.equals(entry.getKey())) {
						if (entry.getValue().getClass() == tempData.get(key)
								|| ((entry.getValue().getClass() == DoubleConfigVarData.class
										&& tempData.get(key) == FloatConfigVarData.class)
										|| (entry.getValue().getClass() == LongConfigVarData.class
												&& tempData.get(key) == IntegerConfigVarData.class))) {
							tempData.remove(key);
						}
						found = true;
						break;
					}
				}

				if (!found) {
					missingKey = true;
				}
			}
		}

		if (tempData.size() > 0 || fresh || isEmpty || missingKey) {
			saveConfig();

			if (!fresh) {
				loadConfig();
				throw new ConfigOutdatedException("Config structure was updated or changed.");
			}
		} else {
			loadConfig();
		}
	}

	public void saveConfig() throws IOException {
		StringBuilder configContent = new StringBuilder();

		Iterator<Entry<String, ConfigVarData>> iterator = configValues.entrySet().iterator();
		boolean hasNext = iterator.hasNext();

		while (iterator.hasNext()) {
			Entry<String, ConfigVarData> entry = iterator.next();

			boolean addQuotes = entry.getValue().getClass() == StringConfigVarData.class;
			String value = (addQuotes ? "\"" : "") + entry.getValue().getData() + (addQuotes ? "\"" : "");

			configContent.append(entry.getValue().getPreDoc() + separator + entry.getKey() + ": " + value);

			hasNext = iterator.hasNext();

			if (hasNext) {
				configContent.append(separator + separator);
			}
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
			writer.write(configContent.toString());
		}
	}

	public void loadConfig() throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line;

			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.startsWith("//")) {
					continue;
				}

				if (!line.contains(":")) {
					continue;
				}

				String[] parts = line.split(":", 2);

				if (parts.length != 2) {
					continue;
				}

				parts[0] = parts[0].trim();
				parts[1] = parts[1].trim();

				configValues.get(parts[0]).setData(parts[1]);
			}
		}
	}

	public boolean getBoolean(String key) {
		return Boolean.valueOf(get(key).toString());
	}

	public char getChar(String key) {
		return get(key).toString().charAt(0);
	}

	public double getDouble(String key) {
		return Double.valueOf(get(key).toString());
	}

	public float getFloat(String key) {
		return Float.valueOf(get(key).toString());
	}

	public int getInteger(String key) {
		return Integer.valueOf(get(key).toString());
	}

	public long getLong(String key) {
		return Long.valueOf(get(key).toString());
	}

	public short getShort(String key) {
		return Short.valueOf(get(key).toString());
	}

	public String getString(String key) {
		return get(key).toString();
	}

	public Object get(String key) {
		return configValues.get(key).getData();
	}

	public void setBoolean(String key, boolean value) {
		set(key, value);
	}

	public void setChar(String key, char value) {
		set(key, value);
	}

	public void setDouble(String key, double value) {
		set(key, value);
	}

	public void setFloat(String key, float value) {
		set(key, value);
	}

	public void setInteger(String key, int value) {
		set(key, value);
	}

	public void setLong(String key, long value) {
		set(key, value);
	}

	public void setShort(String key, short value) {
		set(key, value);
	}

	public void setString(String key, String value) {
		set(key, "\"" + value + "\"");
	}

	public void set(String key, Object value) {
		configValues.get(key).setData(value);
	}

	public Map<String, ConfigVarData> getConfigValues() {
		return configValues;
	}

	public void setConfigValues(Map<String, ConfigVarData> configValues) {
		this.configValues = configValues;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isFresh() {
		return fresh;
	}

	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}
}
