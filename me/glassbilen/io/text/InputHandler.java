package me.glassbilen.io.text;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
	private boolean debug;
	private BufferedReader bufferedReader;
	private Console console;
	private OutputHandler outputHandler;

	public InputHandler(OutputHandler outputHandler) {
		this.outputHandler = outputHandler;

		console = System.console();
		debug = (console == null);

		if (debug) {
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	public String prepareReadLine(String prefix) throws IOException {
		outputHandler.print(prefix);
		String line = readLine();
		outputHandler.printRaw("" + TextColor.RESET);
		return line;
	}

	public String prepareReadPassword(String prefix) throws IOException {
		outputHandler.print(prefix + (debug ? (TextColor.BLACK + "" + TextColor.BLACK.setBackground(true)) : ""));
		String password = readPassword();
		outputHandler.printRaw("" + TextColor.RESET);
		return password;
	}

	public String readLine() throws IOException {
		return debug ? bufferedReader.readLine() : console.readLine();
	}

	public String readPassword() throws IOException {
		return debug ? bufferedReader.readLine() : String.valueOf(console.readPassword());
	}

	public void close() throws IOException {
		bufferedReader.close();
	}

	public boolean isDebug() {
		return debug;
	}
}