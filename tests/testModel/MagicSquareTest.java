package testModel;

import static org.junit.jupiter.api.Assertions.*;
import java.util.InputMismatchException;
import model.*;
import customExceptions.*;
import org.junit.jupiter.api.Test;
/**
 * JUnit Test Case that handles all of the methods to be tested in MagicSquare.
 * @author Jhon Edward Mora - A00355710 - Universidad ICESI
 * @version 1.0 - 02/2019
 */
class MagicSquareTest {
	
	/**
	 * Relation with class MagicSquare, to be updated constantly as the tests flow in the program.
	 */
	private MagicSquare ms;
	
	/**
	 * Sets up Stage 1, an empty Stage.
	 */
	void setupStage1() {
	
	}
	
	/**
	 * Sets up Stage 2, one with an already generated magic square.
	 */
	void setupStage2() {
		try {
		ms = new MagicSquare(3, MagicSquare.NL, MagicSquare.UP);
		}catch(InvalidSizeException is) {
			assertNull(ms, "Failed to create a Magic Square because of the size. \n" + is.getMessage());
		}catch(InputMismatchException im) {
			assertNull(ms, "Failed to create a Magic Square as the inputted initial direction does not exist." );
		}
	}
	
	/**
	 * Sets up Stage 3, one with an already generated magic square.
	 */
	void setupStage3() {
		try {
		ms = new MagicSquare(5, MagicSquare.SL, MagicSquare.DOWN);
		}catch(InvalidSizeException is) {
			assertNull(ms, "Magic Square generated with invalid size. \n" + is.getMessage());
		}catch(InputMismatchException im) {
			assertNull(ms, "Magic Square generated with non existing initial position." );
		}
	}
	
	/**
	 * Tests different case scenarios while building a new instance of MagicSquare
	 */
	@Test
	void test1() {
		setupStage1();
		try {
			ms = new MagicSquare(5, MagicSquare.NL, MagicSquare.UP);
			//ms = new MagicSquare(0, MagicSquare.NL, MagicSquare.UP);
			//ms = new MagicSquare(4, MagicSquare.SL, MagicSquare.DOWN);
			//ms = new MagicSquare(-1, MagicSquare.SL, MagicSquare.DOWN);
			assertNotNull(ms, "Failed while generatig a Magic Square with inputted size: " + ms.getSize() + ", inputted filling direction: '" + ms.getFillingDirection() + "' and inputted initial direction: "+ms.getFillingDirection());
		}catch(InvalidSizeException is) {
			assertNull(ms, "Magic Square generated with invalid size. \n" + is.getMessage());
		}catch(InputMismatchException im) {
			assertNull(ms, "Magic Square generated with non existing initial position." );
		}
	}
	
	/**
	 * Tests that the program successfully creates a Magic Square and returns the value of the magic constant.
	 */
	@Test
	void test2() {
		setupStage2();
		assertEquals(15, ms.getConstant(), "The magic constant in this square is not valid");
		int[][] expectedMatrix = {{6,1,8},{7,5,3},{2,9,4}};
		for (int i = 0; i < ms.getMatrix().length; i++) {
			for (int j = 0; j < ms.getMatrix()[0].length; j++) {
				if(expectedMatrix[i][j] != ms.getMatrix()[i][j]) {
					assertEquals(ms.getMatrix()[i][j], expectedMatrix[i][j], "Generated square is not a magic square.");
					break;
				}
			}
		}
	}
	
	/**
	 * Tests that the program successfully creates a Magic Square and returns the value of the magic constant.
	 */
	@Test
	void test3() {
		setupStage3();
		assertEquals(65, ms.getConstant(), "The magic constant in this square is not valid");
		int[][] expectedMatrix = {{9,2,25,18,11},{3,21,19,12,10},{22,20,13,6,4},{16,14,7,5,23},{15,8,1,24,17}};
		for (int i = 0; i < ms.getMatrix().length; i++) {
			for (int j = 0; j < ms.getMatrix()[0].length; j++) {
				if(expectedMatrix[i][j] != ms.getMatrix()[i][j]) {
					assertEquals(ms.getMatrix()[i][j], expectedMatrix[i][j], "Generated square is not a magic square.");
					break;
				}
			}
		}
	}
}
