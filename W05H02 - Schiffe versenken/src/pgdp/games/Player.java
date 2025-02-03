package pgdp.games;

import static pgdp.PinguLib.*;
import static pgdp.games.Battleship.printBoard;

public class Player {

	private static final String PLACE_LENGTH_4 = "Place a ship of length 4. Enter the coordinates of both ends.";
	private static final String PLACE_LENGTH_3 = "Place a ship of length 3.";
	private static final String PLACE_LENGTH_2 = "Place a ship of length 2.";
	private static final String PLACE_ERROR = "There is a problem with your coordinates. Try again!";

	private static final String ALREADY_HIT = "Field is already hit!";
	private static final String MISSED = "You missed. Better luck next time!";
	private static final String HIT = "You hit it!";

	private static final String ENTER_NUMBER = "Enter number coordinate:";
	private static final String ENTER_LETTER = "Enter letter coordinate:";

	private int[][] board;

	public Player(int[][] board) {
		this.board = board;
	}

	public Player() {
	}

	/**
	 * Generates the player board with 1x ships of length 4, 2x ships of length 3
	 * and 3x ships of length 2.
	 */
	public void generatePlayerBoard() {
		board = new int[8][8];
		placeShip(4, PLACE_LENGTH_4);
		placeShip(3, PLACE_LENGTH_3);
		placeShip(3, PLACE_LENGTH_3);
		placeShip(2, PLACE_LENGTH_2);
		placeShip(2, PLACE_LENGTH_2);
		placeShip(2, PLACE_LENGTH_2);
		// TODO 5
	}

	/**
	 * Places a ship of given length on the player board.
	 * 
	 * @param length
	 * @param message
	 */
	public void placeShip(int length, String message) {
		boolean valid = false;
		int koordinate1z=0;
		int koordinate1s=0;
		int koordinate2z= 0;
		int koordinate2s= 0;
		while (!valid){
			System.out.println(message);
		koordinate1z = readNumber();
		koordinate1s = readCharacter();
		koordinate2z = readNumber();
		koordinate2s = readCharacter();
		boolean fehler = false;
			if ((koordinate1z == koordinate2z || koordinate1s == koordinate2s) &&
					(koordinate1z <= koordinate2z) &&
					(koordinate1s <= koordinate2s) &&
					(koordinate2z - koordinate1z + 1 == length || koordinate2s - koordinate1s + 1 == length))
			{
				for (int i = koordinate1z; i <= koordinate2z; i++) {
					for (int j = koordinate1s; j <= koordinate2s; j++) {
						fehler = !checkCoordinate(i,j);
					}
				}
			}
			else fehler = true;
			if (!fehler){
				valid = true;}
			else {System.out.println(PLACE_ERROR);}
		}
		setShip(koordinate1z, koordinate1s, koordinate2z, koordinate2s);
		printBoard(board, new int[8][8]);
	}
	// TODO 4

	/**
	 * Sets the ship between the two given coordinates.
	 * 
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 */
	public void setShip(int row1, int col1, int row2, int col2) {
		for (int i = row1; i <= row2; i++) {
			for (int j = col1; j <=  col2; j++) {
				board[i][j] = 1;
			}}
	}
	// TODO 3

	/**
	 * Checks the given coordinate if there are no adjacent ships around.
	 * 
	 * @param x
	 * @param y
	 * @return true if it is not adjacent to any existing ship
	 */
	public boolean checkCoordinate(int x, int y) {
		if (board[x][y] != 0 //
				|| x + 1 < 8 && board[x + 1][y] != 0 //
				|| y + 1 < 8 && board[x][y + 1] != 0 //
				|| x - 1 >= 0 && board[x - 1][y] != 0 //
				|| y - 1 >= 0 && board[x][y - 1] != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Plays one move of the player. Repeatedly asks the player to input coordinates
	 * until they are valid
	 * 
	 * @param aiBoard
	 * @return true if the player hit a ship
	 */
	public boolean play(int[][] aiBoard) {
		boolean valid = false;
		int zeile = 0;
		int spalte = 0;
		while (!valid) {
			zeile = readNumber();
			spalte = readCharacter();
			if (aiBoard[zeile][spalte] == 2 || aiBoard[zeile][spalte] == 3) {
				System.out.println(ALREADY_HIT);
			} else {
				valid = true;
			}
		}
		if (aiBoard[zeile][spalte] == 1) {
			System.out.println(HIT);
			aiBoard[zeile][spalte] = 3;
			return true;
		} else {
			System.out.println(MISSED);
			aiBoard[zeile][spalte] = 2;
			return false;
		}
	}
	// TODO 6

	/**
	 * Reads and returns a number between 1 and 8 (inclusive) from std-in.
	 * 
	 * @return the number - 1
	 */
	public static int readNumber() {
		int number = readInt(ENTER_NUMBER);
		while (number < 1 || number > 8) {
			number = readInt(ENTER_NUMBER);
		}
		return number - 1;
	}

	/**
	 * Reads and returns a char between a and h (inclusive) from std-in.
	 * 
	 * @return the char - 'a'
	 */
	public static int readCharacter() {
		char character = readChar(ENTER_LETTER);
		while (character < 'a' || character > 'h') {
			character = readChar(ENTER_LETTER);
		}
		return character - 'a';
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
}
