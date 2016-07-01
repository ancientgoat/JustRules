package rule.utils;

import controllers.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SkChar {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static final Set<Character> ALLOWED_MACRO_CHARS = new HashSet<Character>() {{
		add('.');
		add('~');
		add('_');
	}};
	public static final Set<Character> BAD_MACRO_CHARS = new HashSet<Character>() {{
		add('\'');
		add(')');
		add('(');
	}};
	public static final Set<String> BAD_MACRO_WORDS = new HashSet<String>() {{
		add("BIGDECIMAL");
		add("ROUND_HALF_UP");
		add("INTEGER.MAX_VALUE");
		add("JAVA.MATH.BIGDECIMAL");
		add("MATH");
		add("NEW");
		add("NULL");
		add("POW");
		add("SETSCALE");
		add("T");
	}};

	private SkChar() {
	}

	public static boolean isAlpha(final char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static boolean isNumber(final char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isMacro(final char c) {
		return isAlpha(c) || isNumber(c) || ALLOWED_MACRO_CHARS.contains(c);
	}

	public static boolean isMacro(final String inWord) {
		if (null != inWord) {
			final String wordUpper = inWord.toUpperCase();
			log.info(String.format("Checking Work : %s ", wordUpper));
			return !BAD_MACRO_WORDS.contains(wordUpper);
		}
		return false;
	}

	public static boolean isSpace(final char c) {
		return c == 32;
	}

	public static boolean isBadMacroChar(final char c) {
		return BAD_MACRO_CHARS.contains(c);
	}

	public static boolean isQuote(final char c) {
		return '\'' == c;
	}
}
