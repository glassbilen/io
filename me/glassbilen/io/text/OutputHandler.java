package me.glassbilen.io.text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputHandler {
	private boolean showTime;

	public OutputHandler(boolean showTime) {
		this.showTime = showTime;
	}

	public void printRaw(String string) {
		System.out.print(string);
	}
	
	public void printRawLine(String string) {
		System.out.println(string);
	}
	
	public void println(String string) {
		printRawLine(TextColor.RESET + (showTime ? getTimePrefix() : "") + string + TextColor.RESET);
	}

	public void print(String string) {
		printRaw(TextColor.RESET + (showTime ? getTimePrefix() : "") + string);
	}
	
	protected String getTimePrefix() {
		return "[" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + "] ";
	}
}
