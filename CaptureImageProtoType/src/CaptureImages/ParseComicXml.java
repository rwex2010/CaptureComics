package CaptureImages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * this is from this url: http://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html
 */
public class ParseComicXml {
    private List<ComicStrip> comicList = new ArrayList<>();
    private File myFile;
    private String strMyXmlFile = "../JavaComicList.xml";
    private String strListToUse = "myList";

    private String[] aryDomainToUse = { "imgsrv.gocomics.com" };
    private Boolean blnMyComicsOnly = true;

    private DisplayDebugMessage dbgDisplay;

    public ParseComicXml() {

    }

    public ParseComicXml(String strXmlFileLocation) {
	this.strMyXmlFile = strXmlFileLocation;
    }

    public void setStrListToUse(String strListToUse) {
	this.strListToUse = strListToUse;
    }

    public void setDebugDisplay(DisplayDebugMessage DebugDisplay) {
	this.dbgDisplay = DebugDisplay;
    }

    public void setMyXmlFile(String myXmlFile) {
	this.strMyXmlFile = myXmlFile;
    }

    public void setAryDomainToUse(String[] aryDomains) {
	this.aryDomainToUse = aryDomains;
    }

    public void setupVariable() {

	// Get the DOM Builder Factory
	// I added this because I could not get the stream reader to locate the
	// document
	// File myFile = new
	// File("C:/Users/wecksr/Documents/comics/employee.xml");
	// File myFile = new
	// File("C:/Users/wecksr/Documents/comics/Commic.xml");

	myFile = new File(strMyXmlFile);
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	comicList = new ArrayList<>();

	// Get the DOM Builder
	DocumentBuilder builder = null;
	try {
	    builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// Load and Parse the XML document
	// document contains the complete XML as a Tree.
	Document document = null;
	try {
	    document = builder.parse(myFile);
	} catch (SAXException e) {
	    // TODO Auto-generated catch block
	    System.out.println("We have a SAX exception");
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    System.out.println("We have a IO exception");
	    e.printStackTrace();
	}

	// List<ComicStrip> comicList = new ArrayList<>();

	// Iterating through the nodes and extracting the data.
	NodeList nodeList = document.getDocumentElement().getChildNodes();
	// System.out.println("Node count: " + nodeList.getLength());
	// System.out.println("Element: " +
	// document.getDocumentElement().getTagName());
	for (int i = 0; i < nodeList.getLength(); i++) {

	    // We have encountered an <employee> tag.
	    Node node = nodeList.item(i);

	    if (node instanceof Element) {
		// System.out.println("Node att name: " + node.getNodeName());
		if (node.getNodeName() == "ComicId") {
		    ComicStrip cmicStrip = new ComicStrip();
		    // cmicStrip.id =
		    // node.getAttributes().getNamedItem("id").getNodeValue();
		    cmicStrip.id = "ID";

		    NodeList childNodes = node.getChildNodes();
		    Boolean blnAddToList = true;
		    Boolean blnDomainOkay = false;
		    for (int j = 0; j < childNodes.getLength(); j++) {
			Node cNode = childNodes.item(j);

			// Identifying the child tag of employee encountered.
			if (cNode instanceof Element) {
			    String content = cNode.getLastChild().getTextContent().trim();
			    switch (cNode.getNodeName()) {
			    case "ComicCode":
				cmicStrip.ComicCode = content;
				break;
			    case "ComicName":
				cmicStrip.ComicName = content;
				break;
			    case "Domain":
				// blnAddToList = false;
				for (String strAcceptableDomain : aryDomainToUse) {

				    if (content.compareTo(strAcceptableDomain) == 0) {
					cmicStrip.Domain = content;
					blnDomainOkay = true;
					break;
				    }
				}
				break;
			    case "DateFormat":
				cmicStrip.DateFormat = content;
				break;
			    case "SundayExt":
				cmicStrip.SundayExt = content;
				break;
			    case "DailyExt":
				cmicStrip.DailyExt = content;
				break;
			    case "UrlFormat":
				cmicStrip.UrlFormat = content;
				break;
			    case "SundayAvail":
				cmicStrip.SundayAvail = content;
				break;
			    case "OneOfMyComics":
				switch (this.strListToUse) {
				case "AllComics":
				    cmicStrip.OneOfMyComics = content;
				    break;
				case "MyComics":
				    if (content.compareTo("true") == 0) {
					cmicStrip.OneOfMyComics = content;
				    } else {
					blnAddToList = false;
				    }
				    break;
				case "NotMyComics":
				    if (content.compareTo("true") == 0) {
					blnAddToList = false;
				    } else {
					cmicStrip.OneOfMyComics = content;
				    }
				    break;
				case "OtherList":
				    blnAddToList = false;
				    cmicStrip.OneOfMyComics = content;
				    break;

				default:
				    break;
				}
				break;
			    case "DaysAvailable":
				String DaysAvl = content;
				if (DaysAvl == null) {
				    DaysAvl = "127";
				}
				cmicStrip.DaysAvailable = DaysAvl;
				break;

			    }
			}
		    }
		    if (blnAddToList && blnDomainOkay) {
			comicList.add(cmicStrip);
		    }

		}
	    }

	}

	// Printing the Employee list populated.

	int intDebugCode = 4;
	String strMsg = "";
	for (ComicStrip emp : comicList) {
	    // System.out.println(emp);
	    strMsg += emp + "\n";
	}
	dbgDisplay.ShowMessage(strMsg, intDebugCode);

    }

    public List<ComicStrip> getComicStripList() {
	return comicList;
    }
}
/*
 * class ComicStrip { String id; String ComicCode; String ComicName; String
 * Domain; String DateFormat; String SundayExt; String DailyExt; String
 * UrlFormat; String SundayAvail; String OneOfMyComics; String DaysAvailable;
 * 
 * @Override public String toString() { if (DaysAvailable == null) {
 * DaysAvailable = "127"; } return ComicCode + "|" + ComicName + "|" + id + "|"
 * + Domain + "|" + SundayAvail + "|" + OneOfMyComics + "|" + DaysAvailable; } }
 */
