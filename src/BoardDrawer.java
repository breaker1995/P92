public class BoardDrawer {
	
	private GameBoard gameBoard;
	
	public BoardDrawer(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public void drawBoard() {
		drawColumnNumbers();
		for (int row = 0; row < gameBoard.getRows(); row++) {
			drawLine();
			drawLineNumber(row + 1);
			for (int column = 0; column < gameBoard.getColumns(); column++) {
				String value = gameBoard.getBoard()[row][column];
				System.out.print("│ " + (value == null ? " " : value) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void drawLine() {
		System.out.print("───");
		for (int column = 0; column < gameBoard.getColumns(); column++) {
			System.out.print("┼───");
		}
		System.out.println();
	}
	
	private void drawLineNumber(int row) {
		System.out.print((row < 10 ? " " : "") + row + ".");
	}
	
	private void drawColumnNumbers() {
		System.out.print("   ");
		for (int column = 1; column <= gameBoard.getColumns(); column++) {
			System.out.print("│" +(column < 10 ? " " : "") + column + ".");
		}
		System.out.println();
	}
	
	public static void drawDraw() {
		System.out.println(PropertiesHandler.getMessages().getString("draw"));
	}
	
	public static void drawWelcome() {
		System.out.println(PropertiesHandler.getMessages().getString("welcome"));
		System.out.println(PropertiesHandler.getMessages().getString("menus"));
	}
	
	public static void drawGoodbye() {
		System.out.println(PropertiesHandler.getMessages().getString("goodBye"));
	}
}
