import java.util.LinkedHashSet;
import java.util.Set;

/*This code is picked up from AIMA's code base
 * http://code.google.com/p/aima-java
 * http://code.google.com/p/aima-java/downloads/detail?name=aima-java-1.7.0-Chp4-Complete.zip&can=2&q=
 */

public class SetOps {
	
	public static <T> Set<T> union(Set<T> s1, Set<T> s2) {
		Set<T> union = new LinkedHashSet<T>(s1);
		union.addAll(s2);
		return union;
	}
	public static <T> Set<T> intersection(Set<T> s1, Set<T> s2) {
		Set<T> intersection = new LinkedHashSet<T>(s1);
		intersection.retainAll(s2);
		return intersection;
	}
	public static <T> Set<T> difference(Set<T> s1, Set<T> s2) {
		Set<T> difference = new LinkedHashSet<T>(s1);
		difference.removeAll(s2);
		return difference;
	}
}
