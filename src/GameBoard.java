
public class GameBoard {
	
	private String[][] board;
	private int rows;
	private int columns;
	private int turn;
	private int winThreshold;
	private WinChecker winChecker;
	private BoardDrawer boardDrawer;
	public static final String player1Mark = "X";
	public static final String player2Mark = "O";
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public String[][] getBoard() {
		return board;
	}
	
	public int getWinThreshold() {
		return winThreshold;
	}		
	
	public int getTurn() {
		return turn;
	}
	
	public GameBoard(int rows, int columns, int winThreshold) {
		this(rows, columns, winThreshold, 1);
	}

	public GameBoard(int rows, int columns, int winThreshold, int turn) {
		this.rows = rows;
		this.columns = columns;
		this.winThreshold = winThreshold;
		this.turn = turn;
		
		board = new String[rows][columns];
		winChecker = new WinChecker(this);
		boardDrawer = new BoardDrawer(this);
	}
	
	public boolean place(int row, int column) throws CoordinatesOccupiedException, CoordinatesOutOfBoundException {
		if (row < 0 || row >= rows || column < 0 || column >= columns) {
			throw new CoordinatesOutOfBoundException(row+1, column+1);
		}
		if (player1Mark.equals(board[row][column]) || player2Mark.equals(board[row][column])) {
			throw new CoordinatesOccupiedException(row+1, column+1);
		}
		board[row][column] = turn % 2 == 0 ? player2Mark : player1Mark;
		turn++;
		
		boardDrawer.drawBoard();
		
		return winChecker.isEnded(row, column);
	}
}
