import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlFileHandler implements GameBoardFileHandler {
	
	public boolean isFileExists(String fileName) {
		File file = new File("saves/" + fileName + ".sav");
		return file.exists();
	}
	
	public void saveToFile(GameBoard gameBoard, String fileName) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
	        Element rootElement = doc.createElement(GameBoard.class.getSimpleName());
	        doc.appendChild(rootElement);
	        
	        for (int row = 0; row < gameBoard.getRows(); row++) {
		        Element rowElement = doc.createElement("Row");
		        
		        for (int column = 0; column < gameBoard.getColumns(); column++) {
			        Element cellElement = doc.createElement("Cell");
			        String value = gameBoard.getBoard()[row][column];
			        cellElement.setTextContent(value);
			        rowElement.appendChild(cellElement);
		        }
		        rootElement.appendChild(rowElement);
	        }
	        Element winThresholdElement = doc.createElement("WinThreshold");
	        winThresholdElement.setTextContent(gameBoard.getWinThreshold()+"");
	        rootElement.appendChild(winThresholdElement);
	        
	        Element turnElement = doc.createElement("Turn");
	        turnElement.setTextContent(gameBoard.getTurn()+"");
	        rootElement.appendChild(turnElement);
	        
	        File file = new File("saves/" + fileName + ".sav");
	        file.getParentFile().mkdirs(); // Will create parent directories if not exists
	        file.createNewFile();
	        FileOutputStream output = new FileOutputStream(file,false);
	        writeXml(doc, output);
	       
		} catch (Exception e) {
	           e.printStackTrace();		       
		}
	}
	
	private static void writeXml(Document doc,
            OutputStream output)
	throws TransformerException {
	
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);
		
		transformer.transform(source, result);
	
	}
	
	private void remoteTextNodes(Document doc) throws XPathExpressionException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// XPath to find empty text nodes.
		XPathExpression xpathExp = xpathFactory.newXPath().compile(
		        "//text()[normalize-space(.) = '']");  
		NodeList emptyTextNodes = (NodeList) 
		        xpathExp.evaluate(doc, XPathConstants.NODESET);

		// Remove each empty text node from document.
		for (int i = 0; i < emptyTextNodes.getLength(); i++) {
		    Node emptyTextNode = emptyTextNodes.item(i);
		    emptyTextNode.getParentNode().removeChild(emptyTextNode);
		}
	}
	
	public GameBoard loadFromFile(String fileName) {
		try {
	         File inputFile = new File("saves/" + fileName + ".sav");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         remoteTextNodes(doc);
	         
	         int winThreshold = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("WinThreshold").item(0).getTextContent());
	         int turn = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("Turn").item(0).getTextContent());
	         
	         boolean created = false;
	         GameBoard gameBoard = null;
	         int rows = doc.getDocumentElement().getElementsByTagName("Row").getLength();
	         for (int i = 0; i< rows; i++) {
	        	 Node row = doc.getDocumentElement().getElementsByTagName("Row").item(i);
	        	 int columns = row.getChildNodes().getLength();
	        	 for (int j = 0; j< columns; j++) {
	        		 if (!created) {
	        			 gameBoard = new GameBoard(rows, columns, winThreshold, turn);
	        			 created = true;
	        		 }
	        		 String textContent = row.getChildNodes().item(j).getTextContent();
	        		 if (GameBoard.player1Mark.equals(textContent) || GameBoard.player2Mark.equals(textContent)) {
	        			 gameBoard.getBoard()[i][j] = textContent;
	        		 }
	        	 }
	         }
	         return gameBoard;
		}
		catch (Exception e) {
	           e.printStackTrace();		       
		}
		return null;
	}
}
