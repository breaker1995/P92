import java.util.Scanner;

public class Program {
	
	private static GameBoardFileHandler handler = new XmlFileHandler();
	private static String saveCommand = "ment";
	private static String commandSplitter = " ";
	
	public static void main(String[] args)  {
		Scanner scanner = new Scanner(System.in);
		
		int menuPoint = 0;
		
		if (!PropertiesHandler.readProperties() ) {
			System.out.println(PropertiesHandler.getMessages().getString("propertiesFileError"));
			menuPoint = 3;
		}
		
		while (menuPoint != 3) {
			BoardDrawer.drawWelcome();
			try {
				menuPoint = Integer.parseInt(scanner.nextLine());
			} catch (Exception e ) {
				
			}
			
			switch (menuPoint) {
				case 1:
					newGame(null);
					break;
				case 2:
					loadGame();
					break;
				case 3:
					BoardDrawer.drawGoodbye();
					break;
				default:
					break;
			}
		}
		
		scanner.close();
	}
	
	private static void loadGame() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(PropertiesHandler.getMessages().getString("enterFileName"));
		String input = scanner.nextLine();
		newGame(handler.loadFromFile(input));
	}
	
	private static void newGame(GameBoard gameBoard) {
		GameBoard board = gameBoard != null ? gameBoard : new GameBoard(PropertiesHandler.getRows(), PropertiesHandler.getColumns(), PropertiesHandler.getWinThreshold());
		Scanner scanner = new Scanner(System.in);
		while (!getInput(board, scanner)) {
			
		}	
	}
	
	private static boolean isValidSaveCommand(String input) {
		String[] line = input.split(commandSplitter);
		return line.length == 2 && saveCommand.equals(line[0]);
	}
	
	private static boolean isExtensionInCommand(String input) {
		String[] line = input.split(commandSplitter);
		return line[1].contains(".");
	}
	
	private static boolean getInput(GameBoard gameBoard, Scanner scanner) {
		boolean ret = false;
		try {
			System.out.println(PropertiesHandler.getMessages().getString("enterCommand"));
			String input = scanner.nextLine();
			if (isValidSaveCommand(input)) {
				if (isExtensionInCommand(input)) {
					System.out.println(PropertiesHandler.getMessages().getString("noExtension"));
				}
				else if (!handler.isFileExists(input.split(commandSplitter)[1])) {
					handler.saveToFile(gameBoard, input.split(commandSplitter)[1]);
				} else {
					System.out.println(PropertiesHandler.getMessages().getString("overwriteQuestion"));
					String confirm = scanner.nextLine();
					if (PropertiesHandler.getMessages().getString("yes").equals(confirm)) {
						handler.saveToFile(gameBoard, input.split(commandSplitter)[1]);
					}
				}
			} else {
				String[] line = input.split(commandSplitter);
				int row = Integer.parseInt(line[0]);
				int column = Integer.parseInt(line[1]);
				ret = gameBoard.place(row-1, column-1);
			}
		}
		catch (CoordinatesOutOfBoundException e) {
			System.out.println(e.getMessage());
		}
		catch (CoordinatesOccupiedException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
		}
		return ret;
	}
}
