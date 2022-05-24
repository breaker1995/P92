public class WinChecker {
	
	private GameBoard gameBoard;
	
	public WinChecker(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public boolean isEnded(int row, int column) {
		if ( isColumnMet(row, column) || 
				isRowMet(row, column) ||
				isDiagonalMet(row, column, +1, +1) ||
				isDiagonalMet(row, column, +1, -1) ||
				isDiagonalMet(row, column, -1, +1) /*||
				isDiagonalMet(row, column, -1, -1) */) {
			return true;
		}
		if (isDraw()) {
			return true;
		}
		return false;
	}
	
	private boolean isColumnMet(int rowCoordinate, int columnCoordinate) {
		String last = "";
		int counter = 0;
		for (int row = (Math.max(rowCoordinate - gameBoard.getWinThreshold(), 0));
				 row < (Math.min(rowCoordinate + gameBoard.getWinThreshold(), gameBoard.getRows()));
				 row++) {
			String value = gameBoard.getBoard()[row][columnCoordinate];
			if (last.equals(value)) {
				counter++;
			} else {
				counter = 1;
			}
			if (counter == gameBoard.getWinThreshold()) {
				System.out.println(PropertiesHandler.getMessages().getString("columnWin")+ gameBoard.getTurn() + " " + last);
				return true;
			}
			last = value == null ? "" : value;
		}
		return false;
	}
	
	private boolean isRowMet(int rowCoordinate, int columnCoordinate) {
		String last = "";
		int counter = 0;
		for (int column = (Math.max(columnCoordinate - gameBoard.getWinThreshold(), 0));
				column < (Math.min(columnCoordinate + gameBoard.getWinThreshold(), gameBoard.getColumns()));
				column++) {
			String value = gameBoard.getBoard()[rowCoordinate][column];
			if (last.equals(value)) {
				counter++;
			} else {
				counter = 1;
			}
			if (counter == gameBoard.getWinThreshold()) {
				System.out.println(PropertiesHandler.getMessages().getString("rowWin")+ gameBoard.getTurn() + " " + last);
				return true;
			}
			last = value == null ? "" : value;
		}
		return false;
	}
	
	private boolean isDiagonalMet(int rowCoordinate, int columnCoordinate, int rowDirection, int columnDirection) {
		String last = "";
		int counter = 0;
		int row = rowCoordinate;
		int column = columnCoordinate;
		int thresholdCounter = 0;
		
		while( 0 < row && row < gameBoard.getRows() && 
				0 < column && column < gameBoard.getColumns() && 
				thresholdCounter < gameBoard.getWinThreshold()) {
			row += -rowDirection;
			column += -columnDirection;
			thresholdCounter ++;
		}
		
		while( 0 <= row && row < gameBoard.getRows() && 
				0 <= column && column < gameBoard.getColumns() ) {			
			String value = gameBoard.getBoard()[row][column];
			if (last.equals(value)) {
				counter++;
			} else {
				counter = 1;
			}
			if (counter == gameBoard.getWinThreshold()) {
				System.out.println(PropertiesHandler.getMessages().getString("diagonalWin")+ gameBoard.getTurn() + " " + last);
				return true;
			}
			last = value == null ? "" : value;
			row += rowDirection;
			column += columnDirection;
		}

		return false;
	}
	
	private boolean isDraw() {
		if ((gameBoard.getTurn() - 1) == gameBoard.getRows() * gameBoard.getColumns()) {
			BoardDrawer.drawDraw();
			return true;
		}
		return false;
	}
}
