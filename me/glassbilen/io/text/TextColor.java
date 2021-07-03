package me.glassbilen.io.text;

// Probably shouldn't been using an enum but this class started out so pure and innocent hehe.
public enum TextColor {
	RESET(-1), BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), WHITE(7);

	private int type;

	private boolean bold;
	private boolean underline;
	private boolean background;
	private boolean bright;
	private int index;

	TextColor(int index) {
		this.index = index;
	}

	public String toString() {
		updateType();

		String type = String.valueOf(this.type);
		String prefix = "\033[";
		String split = ";";
		String suffix = "m";
		int colorCode = (bright ? 60 : 0) + (background ? 10 : 0) + index + 30;

		if (index == -1) {
			colorCode = 0;
			split = "";
			type = "";
		}

		if (background) {
			type = "";
			split = "";
		}

		String result = String.valueOf(prefix) + type + split + colorCode + suffix;

		resetLastColor();
		return result;
	}

	public TextColor setBright(boolean bright) {
		this.bright = bright;
		return this;
	}

	public TextColor setBackground(boolean background) {
		this.background = background;
		return this;
	}

	public TextColor setUnderline(boolean underline) {
		this.underline = underline;
		return this;
	}

	public TextColor setBold(boolean bold) {
		this.bold = bold;
		return this;
	}

	private void resetLastColor() {
		bright = false;
		background = false;
		underline = false;
		bold = false;
		updateType();
	}

	private void updateType() {
		if (bold) {
			type = 1;
		} else if (underline) {
			type = 4;
		} else {
			type = 0;
		}
	}
}