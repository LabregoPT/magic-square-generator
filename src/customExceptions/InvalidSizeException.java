package customExceptions;

/**
 * Exception to be thrown whenever a non valid size is inputed as the size of a new magic square.
 * @author Jhon Edward Mora - Universidad ICESI - A00355710
 * @version 1.0 - 02/2019
 */
@SuppressWarnings("serial")
public class InvalidSizeException extends Exception {
	/**The inputed size*/
	private int size;
	
	/**
	 * Constructor of the class.
	 * @param n The inputed size.
	 */
	public InvalidSizeException(int n) {
		size = n;
	}
	
	@Override
	/**
	 * Returns a String containing information of the occurred exception.
	 */
	public String getMessage() {
		String m = "the size of the square cannot be ";
		if(size <0) {
			m += "a negative number.";
		}else if (size == 0) {
			m += "zero.";
		}else if (size%2 == 0) {
			m += "an even number";
		}else {
			m = "the size of the square is not valid";
		}
		return m;
	}
}
