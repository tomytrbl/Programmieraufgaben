package pgdp.games;

import static pgdp.PinguLib.generateBoard;
import static pgdp.PinguLib.write;
import static pgdp.PinguLib.writeConsole;

import java.util.Random;

/**
 * You can change this class however you like as long as you do not delete any
 * existing method. However, you can change them.
 */
public class AI {

	private static final String SHOOT = "The AI shoots at ";

	private int[][] board;
	private Random random;

	private boolean strategic;

	public AI(int[][] board) {
		this.board = board;
		random = new Random();
		strategic = false;
	}

	public AI() {
		this(generateBoard());
	}

	/**
	 * Plays random per default. Feel free to implement a strategic variant in the
	 * helper method!
	 * 
	 * @param playerBoard
	 * @return true if the ai hit a ship
	 */
	public boolean play(int[][] playerBoard) {
		if (strategic) {
			return playStrategic(playerBoard);
		}
		return playRandom(playerBoard);
	}

	/**
	 * Generates a random coordinate until it found one that has not been hit yet.
	 * 
	 * @param playerBoard
	 * @return true if the ai hit a ship
	 */
	public boolean playRandom(int[][] playerBoard) {
		int row = random.nextInt(8);
		int col = random.nextInt(8);
		while (playerBoard[row][col] > 1) {
			row = random.nextInt(8);
			col = random.nextInt(8);
		}
		playerBoard[row][col] += 2;
		writeConsole(SHOOT);
		writeConsole(row + 1 + "" + (char) (col + 'a') + " and ");

		if (playerBoard[row][col] == 3) {
			write("hit!");
			return true;
		}
		write("missed!");
		return false;
	}

	/**
	 * You can implement a strategic variant here.
	 * 
	 * @param playerBoard
	 * @return
	 */
	public boolean playStrategic(int[][] playerBoard) {
		return false;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

}
