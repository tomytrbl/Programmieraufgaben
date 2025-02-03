package pgdp.games;

import pgdp.PinguLib;

import static pgdp.PinguLib.*;

public class Battleship {

	private static final String WELCOME_MESSAGE = "Welcome to Battleships";
	private static final String PLAY_MESSAGE = "Let's start the game!";
	private static final String WIN_MESSAGE = "Congrats! You won!";
	private static final String LOSE_MESSAGE = "Too bad, you lost!";

	private static final String PRINT_FIRST_LINE = "Your Board:\t\t\tAI Board:";
	private static final String PRINT_SECOND_LINE = "+ a b c d e f g h\t\t+ a b c d e f g h";
	private static final String TABS = "\t\t";

	private static final String PLAYER_BOAT = " ■";
	private static final String BOAT_MISSED = " o";
	private static final String BOAT_HIT = " x";
	private static final String WATER = "  ";

	private Player player;
	private AI ai;

	public Battleship(Player player, AI ai) {
		this.player = player;
		this.ai = ai;
	}

	public static void main(String[] args) {
		Battleship Test = new Battleship(new Player(), new AI());
		Test.game();
	}

	/**
	 * Runs the whole game.
	 */
	public void game() {

		System.out.println(WELCOME_MESSAGE);

		int[][] leer = new int[8][8];
		printBoard(leer, leer);

		player.generatePlayerBoard();
		System.out.println(PLAY_MESSAGE);

		while (hasShipsLeft(player.getBoard()) && hasShipsLeft(ai.getBoard())) {
			boolean p1Hit = true;

			while (p1Hit) {
				p1Hit = player.play(ai.getBoard());
				printBoard(player.getBoard(), ai.getBoard());
				if (!hasShipsLeft(ai.getBoard())) {
					System.out.println(WIN_MESSAGE);
					return;
				}
			}

			boolean aiHit = true;

			while (aiHit) {
				aiHit = ai.play(player.getBoard());
				printBoard(player.getBoard(), ai.getBoard());
				if (!hasShipsLeft(player.getBoard())) {
					System.out.println(LOSE_MESSAGE);
					return;
				}
			}
		}
	}
		// TODO 7

	/**
	 * Checks if there are any ships left.
	 * 
	 * @param board
	 * @return true if at least one ship exists (partially)
	 */
	public boolean hasShipsLeft(int[][] board) {
		// TODO 2
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j <  8; j++) {
				if (board[i][j] == 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Prints both boards to the console
	 * 
	 * @param playerBoard
	 * @param aiBoard
	 */
	public static void printBoard(int[][] playerBoard, int[][] aiBoard) {
		System.out.println(PRINT_FIRST_LINE);
		System.out.println(PRINT_SECOND_LINE);
		String out = "";
		for (int i = 1; i < 9; i++) {
			out = out+i;
			for (int j = 0; j <  8; j++) {
				if (playerBoard[i - 1][j] == 0) {
					out = out + WATER;
				} else if (playerBoard[i - 1][j] == 1) {
					out = out + PLAYER_BOAT;
				} else if (playerBoard[i - 1][j] == 2) {
					out = out + BOAT_MISSED;}
				else {out = out + BOAT_HIT;}}
			out = out+"\t\t";
			out = out+i;
			for (int j = 0; j <  8; j++) {
				if (aiBoard[i - 1][j] == 0) {
					out = out + WATER;
				} else if (aiBoard[i - 1][j] == 1) {
					out = out + WATER;
				} else if (aiBoard[i - 1][j] == 2) {
					out = out + BOAT_MISSED;
				} else if (aiBoard[i - 1][j] == 3){
					out = out + BOAT_HIT;
				}
			}
			System.out.println(out);
			out = "";
		}
	}
	// TODO 1

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}
}
