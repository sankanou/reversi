package reversi;

public class Board {
	private int[] board;
	private int ply;
	private int turn; // true->black, false->white

	private int moveNum;
	private int[] move;
	private int blank;

	public static final int BLACK = 1;
	public static final int EMPTY = 0;
	public static final int WHITE = -1;
	public static final int WALL = 2;

	public static final int BOARD_SIZE = 8;
	public static final int ARRAY_WIDTH = BOARD_SIZE + 1;
	public static final int ARRAY_HIGHTH = BOARD_SIZE + 2;

	public static final int X = 1;
	public static final int Y = ARRAY_WIDTH;

	public static final int[] DIR = { X, X * -1, Y, Y * -1 };
	public static final int DIR_NUM = 4;

	public Board() {
		board = new int[ARRAY_WIDTH * ARRAY_HIGHTH];
		for (int i = 0; i < ARRAY_WIDTH; i++) {
			board[i] = WALL;
			board[i + ARRAY_WIDTH * (ARRAY_HIGHTH - 1)] = WALL;
		}

		for (int i = 1; i <= BOARD_SIZE; i++)
			board[i * ARRAY_WIDTH] = WALL;

		board[getIndex(4, 5)] = BLACK;
		board[getIndex(5, 4)] = BLACK;
		board[getIndex(4, 4)] = WHITE;
		board[getIndex(5, 5)] = WHITE;

		/*
		 * board = { {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, {2, 0, 0, 0, 0, 0, 0, 0, 0, 2}, {2,
		 * 0, 0, 0, 0, 0, 0, 0, 0, 2}, {2, 0, 0, 0, 0, 0, 0, 0, 0, 2}, {2, 0, 0, 0, -1,
		 * 1, 0, 0, 0, 2}, {2, 0, 0, 0, 1, -1, 0, 0, 0, 2}, {2, 0, 0, 0, 0, 0, 0, 0, 0,
		 * 2}, {2, 0, 0, 0, 0, 0, 0, 0, 0, 2}, {2, 0, 0, 0, 0, 0, 0, 0, 0, 2}, {2, 2, 2,
		 * 2, 2, 2, 2, 2, 2, 2} }
		 */

		turn = 1;
		ply = 1;

		moveNum = 4;
		move[0] = getIndex(4, 4) - X;
		move[1] = getIndex(4, 4) - Y;
		move[2] = getIndex(5, 5) + X;
		move[3] = getIndex(5, 5) + Y;

		blank = 60;
	}

	public void swapTurn() {
		turn *= -1;
	}
	
	public void printBoard() {
		for(int i = 1; i <= BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++)
				System.out.print(board[getIndex(i, j)] + " ");
			System.out.println();
		}
		
		System.out.println("ply = " + ply);
		if(turn == 1) {
			System.out.println("turn = BLACK");
		}else {
			System.out.println("turn = WHITE");
		}
		System.out.println("moveNum = " + moveNum);
		for(int i = 0; i < moveNum; i++)
			System.out.println(move[i] + ", ");
		System.out.println("blank = " + blank);
	}

	public int getIndex(int x, int y) {
		//TODO 例外処理
		if(x < 1 || x > 8 || y < 1 || y > 8) {
			
		}
		return x * Y + y;
	}

	public int getMove() {
		moveNum = 0;
		for (int i = 1; i <= BOARD_SIZE; i++)
			for (int j = 1; j <= BOARD_SIZE; j++)
				if (board[getIndex(i, j)] == EMPTY) {
					for (int k = 0; k < DIR_NUM; k++) {
						int pos = getIndex(i, j) + DIR[k];
						if (board[pos] != turn * -1)
							continue;
						while (board[pos] == turn * -1)
							pos += DIR[k];
						if (board[pos] == turn) {
							move[moveNum] = getIndex(i, j);
							moveNum++;
							break;
						}
					}
				}
		
		if(moveNum == 0)
			return 1;
		
		return 0;
	}

	public void indexMove(int index) {
		// TODO 例外処理
		if (index < 0 || index >= moveNum) {

		}
		makeMove(move[index]);
	}

	public void makeMove(int index) {
		int i;
		for(i = 0; i < moveNum; i++)
			if(move[i] == index)
				break;
		
		// TODO 例外処理
		if (i >= moveNum) {

		}		
		
		board[index] = turn;
		
		for (i = 0; i < DIR_NUM; i++) {
			int pos = index + DIR[i];
			while (board[pos] == turn * -1)
				pos += DIR[i];
			if (board[pos] == turn) {
				for(int pos2 = index + DIR[i]; pos2 != pos; pos2 += DIR[i])
					board[pos2] = turn;
			}
		}
		
		ply++;
		swapTurn();
		blank--;
		
		if(blank <= 0) {
			System.out.println("This Game was Finished");
			return;
		}
		
		if(getMove() == 1) {
			ply++;
			swapTurn();
			if(getMove() == 1) {
				System.out.println("This Game was Finished");
			}
		}
	}

}
