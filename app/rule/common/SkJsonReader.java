package rule.common;

import java.util.stream.Stream;

/**
 * Various things read in JSON files.
 */
public interface SkJsonReader<C> {

	/**
     * Convert a bean to a JSON String.
	 *
	 * @param inentry
	 * @return String
     */
	String entryToString(final C inentry);

	/**
     * Size of the contents, zero if nothing.
	 *
	 * @return Integer
     */
	Integer size();

    /**
     * Return the this reader thingy as a stream for processing.
     *
     * @return Stream<C>
     */
	Stream<C> stream();
}
