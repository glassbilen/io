package me.glassbilen.samples;

import java.io.File;
import java.io.IOException;

import me.glassbilen.file.ConfigManager;
import me.glassbilen.file.exceptions.ConfigOutdatedException;

public class ExampleConfigManager {
	private ConfigManager configManager;

	public ExampleConfigManager() {
		configManager = new ConfigManager(new File("config.cfg"));

		configManager.prepareString("username", "placeholder username", "Enter your username.");
		configManager.prepareString("password", "placeholder password", "Enter your password.");

		configManager.prepareInteger("age", 10, 1, Integer.MAX_VALUE, "Enter your age.");

		configManager.prepareBoolean("tall", true, "You are not tall", "You are tall.", "Weither you're tall or not.");

		try {
			configManager.init();

			if (configManager.isFresh()) {
				System.out.println("Config was just created for the first time.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigOutdatedException e) {
			e.printStackTrace();
		}

		String username = configManager.getString("username");
		String password = configManager.getString("password");
		int age = configManager.getInteger("age");
		boolean tall = configManager.getBoolean("age");

		System.out.println("Hi " + username + ", your password is: " + password + " and you're " + age
				+ " years old and " + (tall ? "tall" : "short") + ".");
	}

	public static void main(String[] args) {
		new ExampleConfigManager();
	}
}
