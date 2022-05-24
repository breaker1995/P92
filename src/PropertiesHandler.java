import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PropertiesHandler {
	private static int winThreshold;
	private static int rows;
	private static int columns;
	
	private static int minimumSize = 5;
	private static int maximumSize = 99; 
	
	private static ResourceBundle messages;
	
	public static int getWinThreshold() {
		return winThreshold;
	}

	public static int getRows() {
		return rows;
	}

	public static int getColumns() {
		return columns;
	}
	
	public static ResourceBundle getMessages() {
		return messages;
	}

	public static boolean readProperties() {
		Scanner properties = null;		
		try {
			messages = ResourceBundle.getBundle("MessagesBundle", new Locale("hu","HU"));
			File file = new File("resources/tictactoe.properties");
			properties = new Scanner(file);
			winThreshold = Integer.parseInt(properties.nextLine());			
			rows = Integer.parseInt(properties.nextLine());
			columns = Integer.parseInt(properties.nextLine());		
		} catch (Exception e) {
			System.out.println(e.getMessage());			
		} finally {
			if (properties != null) {
				properties.close();
			}
		}
		
		if (rows < minimumSize || rows > maximumSize ||
				columns < minimumSize || columns > maximumSize ||
				winThreshold > rows || winThreshold > columns || winThreshold < 1) {
			return false;
		}
		return true;
	}
}
