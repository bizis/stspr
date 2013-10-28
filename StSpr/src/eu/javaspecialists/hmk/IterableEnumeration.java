package eu.javaspecialists.hmk;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Making enumerations iterable
 * @author Dr. Heinz M. Kabultz
 * http://www.javaspecialists.eu/archive/Issue107.html
 *
 * @param <T>
 */
public class IterableEnumeration<T> implements Iterable<T> {
	  private final Enumeration<T> en;
	  public IterableEnumeration(Enumeration<T> en) {
	    this.en = en;
	  }
	  // return an adaptor for the Enumeration
	  public Iterator<T> iterator() {
	    return new Iterator<T>() {
	      public boolean hasNext() {
	        return en.hasMoreElements();
	      }
	      public T next() {
	        return en.nextElement();
	      }
	      public void remove() {
	        throw new UnsupportedOperationException();
	      }
	    };
	  }
	  public static <T> Iterable<T> make(Enumeration<T> en) {
	    return new IterableEnumeration<T>(en);
	  }
	}