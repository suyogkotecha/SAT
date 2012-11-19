/*This code is picked up/derived from AIMA's code base
 * http://code.google.com/p/aima-java
 * http://code.google.com/p/aima-java/downloads/detail?name=aima-java-1.7.0-Chp4-Complete.zip&can=2&q=
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Converter<T> {

	/**
	 * Converts a Set into a List
	 * 
	 * @param set
	 *            a collection of unique objects
	 * 
	 * @return a new list containing the elements of the specified set, in the
	 *         order they are returned by the set's iterator.
	 */
	public List<T> setToList(Set<T> set) {
		List<T> retVal = new ArrayList<T>(set);
		return retVal;
	}

	/**
	 * Converts a List into a Set
	 * 
	 * @param l
	 *            a list of objects, possibly containing duplicates
	 * @return a new set containing the unique elements of the specified list.
	 */
	public Set<T> listToSet(List<T> l) {
		Set<T> retVal = new HashSet<T>(l);
		return retVal;
	}
}