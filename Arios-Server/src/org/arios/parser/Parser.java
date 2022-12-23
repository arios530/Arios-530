package org.arios.parser;

/**
 * An interface implemented by parsing class which are used upon runtime.
 * @author Emperor
 */
public interface Parser {

	/**
	 * Parses data from the server store.
	 * @return {@code True} if succesful.
	 * @throws Throwable when an exception occurs.
	 */
	boolean parse() throws Throwable;

}