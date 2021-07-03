package me.glassbilen.samples;

import java.io.IOException;

import me.glassbilen.io.text.InputHandler;
import me.glassbilen.io.text.OutputHandler;
import me.glassbilen.io.text.TextColor;

public class ExampleIOLogger {
	private OutputHandler output;
	private InputHandler input;

	public ExampleIOLogger() {
		output = new OutputHandler(true); // This boolean lets you include a timestamp in each log message.
		input = new InputHandler(output);

		try {
			String username = input.prepareReadLine("Username: ");
			String password = input.prepareReadPassword("Password: ");

			output.println(TextColor.RED.setBold(true) + "Your username: " + username + ", password: " + password + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ExampleIOLogger();
	}
}
