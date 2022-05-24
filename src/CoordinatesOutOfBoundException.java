
public class CoordinatesOutOfBoundException extends Exception {
	public CoordinatesOutOfBoundException(int row, int column) {
		super("Coordinates " + row + " " + column + " are outside of the game board. Enter a valid coordinate pair.");
	}
}
