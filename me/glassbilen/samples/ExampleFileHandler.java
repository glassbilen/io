package me.glassbilen.samples;

import java.io.File;
import java.io.IOException;

import me.glassbilen.file.FileHandler;

public class ExampleFileHandler {
	private FileHandler fileHandler;

	public ExampleFileHandler() {
		fileHandler = new FileHandler(new File("test.cfg"));

		try {
			fileHandler.writeToFile("Hey guys. This is written to the provided file.", false);

			String[] lines = fileHandler.getLinesByPrefix("Hey guys.", true);

			for (String line : lines) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ExampleFileHandler();
	}
}
