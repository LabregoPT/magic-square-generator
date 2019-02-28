 	package model;

import java.util.*;
import customExceptions.*;

public class MagicSquare {
	//Constants
	/**To be used when the initial position is the first row of the square*/
	public static final String UP = "UP";
	/**To be used when the initial position is the last row of the square*/
	public static final String DOWN = "DOWN";
	/**To be used when the initial position is the first column of the square*/
	public static final String LEFT = "LEFT";
	/**To be used when the initial position is the last column of the square*/
	public static final String RIGHT = "RIGHT";
	/**To be used when the filling direction is North left*/
	public static final char NL = 'A';
	/**To be used when the filling direction is North right*/
	public static final char NR = 'B';
	/**To be used when the filling direction is South left*/
	public static final char SL = 'C';
	/**To be used when the filling direction is South right*/
	public static final char SR = 'D';
	
	//Attributes
	/**Size of the square*/
	private int size;
	/**Magic constant of the magic square to be created.*/
	private int magicConstant;
	/**Matrix that contains a magic square.*/
	private int[][] matrix;
	/**Filling direction of this square.*/
	private char dir;
	/**Starting filling direction of this square.*/
	private String initPos;
	
	//Methods.
	
	/**
	 * Constructor method, first generates a matrix using <code>s</code> as the size, then assigns <code>d</code> as the filling direction and <code>init</code> as the initial filling position; validating each of the aforementioned parameters, and finally proceeds to fill the square using the method developed by Simon de la Loubère.
	 * @param s The size of the square to be created.
	 * @param d The filling direction of the square to be created.
	 * @param init The initial position of the filling process used in the square to be created.
	 * @throws InvalidSizeException Thrown whenever is attempted to create a square using an invalid size.
	 * @throws InputMismatchException Thrown whenever is attempted to create a square using either an undefined filling direction or initial filling position.
	 */
	public MagicSquare(int s, char d, String init) throws InvalidSizeException, InputMismatchException {
		generateMatrix(s);
		assignInitPos(init);
		assignDirection(d);
		magicConstant = (s*((s*s)+1))/2;
		fillSquare();
	}
	
	/**
	 * Initializes a new Matrix to contain the magic square.
	 * @param n The size of the container matrix.
	 * @throws InvalidSizeException Thrown whenever an invalid number is set as size.
	 */
	private void generateMatrix(int n) throws InvalidSizeException{
		if(n%2 == 0 || n == 0 || n < 0) {
			throw new InvalidSizeException(n);
		}else {
			matrix = new int[n][n];
			size = n;
		}
	}
	
	/**
	 * Assigns the initial filling position of the square.
	 * @param init String containing the initial filling position of the square.
	 * @throws InputMismatchException Thrown when the filling direction inputed as <code>init</code> does not match as any of the possible inital filling positions.
	 */
	private void assignInitPos(String init) throws InputMismatchException {
		if(init.equals(UP)) {
			initPos = UP;
		}else if(init.equals(DOWN)) {
			initPos = DOWN;
		}else if(init.equals(LEFT)) {
			initPos = LEFT;
		}else if(init.equals(RIGHT)) {
			initPos = RIGHT;
		}else {
			throw new InputMismatchException("There is no such initial filling position.");
		}
	}

	/**
	 * Assigns the filling direction of the square.
	 * @param d Filling direction to be assigned.
	 * @throws InputMismatchException Thrown when the character to be assigned as the direction does not match as any of the possible filling directions.
	 */
	private void assignDirection(char d) throws InputMismatchException{
		if(d == NL) {
			dir = NL;
		}else if(d == NR) {
			dir = NR;
		}else if(d == SL) {
			dir = SL;
		}else if(d == SR){
			dir = SR;
		}else {
			throw new InputMismatchException("There is no such filling direction.");
		}
	}
	
	/**
	 * Fills the square using the method published by Simon de la Loubère.
	 */
	private void fillSquare() {
		int vGrowth = defineGrowths()[0];
		int hGrowth = defineGrowths()[1];
		int currentV = defineStartingPos()[0];
		int currentH = defineStartingPos()[1];
		int encounterV = defineStartingPos()[2];
		int encounterH = defineStartingPos()[3];
		int prevV = 0;
		int prevH = 0;
		for(int currentNumber = 0; currentNumber<(size*size); currentNumber++) {
			if(matrix[currentV][currentH] == 0) {
				matrix[currentV][currentH] = currentNumber+1;
				prevV = currentV;
				prevH = currentH;
			}else {
				currentV = prevV + encounterV;
				currentH = prevH + encounterH;
				matrix[currentV][currentH] = currentNumber +1;
			}
			currentV+=vGrowth;
			currentH+=hGrowth;
			if(currentV == -1) { 
				currentV = size-1;}
			if(currentV == size) {
				currentV = 0;}
			if(currentH == -1) {
				currentH = size-1;}
			if(currentH == size) {
				currentH = 0;}
		}
	}

	/**
	 * Defines the starting position to be used in the filling process.
	 * @return A 4 positions integer array, with the starting vertical position in its first slot, the starting horizontal position in its second, the vertical growth in case the filling process encounters a number in its third and the horizontal growth in case the filling process encounters a number in its fourth slot.
	 */
	private int[] defineStartingPos() {
		int[] positions = new int[4];
		if(initPos.equals(UP)) {
			positions[0] = 0;
			positions[1] = (size-1)/2;
			positions[2] = 1;
		}else if(initPos.equals(DOWN)) {
			positions[0] = size-1;
			positions[1] = (size-1)/2;
			positions[2] = -1;
		}else if(initPos.equals(LEFT)) {
			positions[0] = (size-1)/2;
			positions[1] = 0;
			positions[3] = 1;
		}else if(initPos.equals(RIGHT)) {
			positions[0] = (size-1)/2;
			positions[1] = size-1;
			positions[3] = -1;
		}
		return positions;
	}
	
	/**
	 * Assigns the increment vertically and horizontally in the magic square's filling process.
	 * @return A two positions array, containing the vertical growth in the first position and the horizontal growth in the second.
	 */
	private int[] defineGrowths() {
		int[] growth = new int[2];
		if(dir == NL) {
			growth[0] = -1;
			growth[1] = -1;
		}else if(dir == NR) {
			growth[0] = -1;
			growth[1] = 1;
		}else if(dir == SL) {
			growth[0] = 1;
			growth[1] = -1;
		}else if(dir == SR){
			growth[0] = 1;
			growth[1] = 1;
		}
		return growth;
	}

	/**
	 * Returns a matrix containing a magic square.
	 * @return A matrix containing a magic square.
	 */
	public int[][] getMatrix(){
		return matrix;
	}
	
	/**
	 * Returns the value of the magic constant in this magic square.
	 * @return The magic constant.
	 */
	public int getConstant() {
		return magicConstant;
	}

	/**
	 * Returns the character that represents the Filling Direction of this square.
	 * @return 'A' if filling on the North Left, 'B' if filling on the north right, 'C' if filling on the south left and 'D' if filling on the south right.
	 */
	public char getFillingDirection() {
		return dir;
	}
	
	/**
	 * Returns the String that represents the Initial filling direction in this square.
	 * @return "UP" if filling started on the first row, "LEFT" if filling started on the first column, "RIGHT" if filling started on the last column and "DOWN" if filling started on the last row.
	 */
	public String getInitialPosition() {
		return initPos;
	}

	/**
	 * Returns the size of the magic square.
	 * @return An odd positive integer number representing the size of this square.
	 */
	public int getSize() {
		return size;
	}
}
