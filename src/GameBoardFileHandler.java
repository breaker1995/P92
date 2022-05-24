
public interface GameBoardFileHandler {
	boolean isFileExists(String fileName);
	void saveToFile(GameBoard gameBoard, String fileName);
	GameBoard loadFromFile(String fileName);
}
