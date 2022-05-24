
public class CoordinatesOccupiedException extends Exception {
	public CoordinatesOccupiedException(int row, int column) {
		super("Coordinates " + row + " " + column + " already occupied. Enter an empty coordinate pair.");
	}
}
