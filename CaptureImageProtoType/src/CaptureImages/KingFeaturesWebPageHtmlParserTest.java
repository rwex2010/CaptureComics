package CaptureImages;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.junit.Before;
import org.junit.Test;

public class KingFeaturesWebPageHtmlParserTest {
    private KingFeaturesWebPageHtmlParser objectToTest;
    
    private LocalDate ldWorkingDate = LocalDate.of(2015, 4, 28);
//    private String strImageName = "luann";
    private URL urlImageWebPage;
//    private File webPageLocationOnLocalSystem = new File("../BeetleBailey.html");
    private BufferedReader bufferedStreamReader = null;
    private DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(1);
    
    private String myExpectedStringResults;
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Before
    public void setUp() throws Exception {
//	objectToTest = new RichWebPageHtmlParser();
	objectToTest = new KingFeaturesWebPageHtmlParser();
//	urlImageWebPage = new URL("file:../OnTheFastrack.html");
	urlImageWebPage = new URL("file:../BeetleBailey.html");
	readImageWebPage();

	HTMLEditorKit.Parser parser = new ParserDelegator();
	Reader myLineReader = new LineNumberReader(bufferedStreamReader);

	objectToTest.setDebugMessageHandler(dbgDisplay);
	objectToTest.setWorkingDate(ldWorkingDate);
//	objectToTest.setImageName(strImageName);

	try {
	    parser.parse(myLineReader, objectToTest, true);
	    myLineReader.close();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public void readImageWebPage() {
	try {
	    bufferedStreamReader = new BufferedReader(new InputStreamReader(urlImageWebPage.openStream()));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }


    @Test
    public void testGetImageStringDate() {
	myExpectedStringResults = ldWorkingDate.format(dtImageDateFormatter);
	String Step4Results = objectToTest.getImageStringDate();
	assertEquals(myExpectedStringResults, Step4Results);
    }

    @Test
    public void testGetImageLocalDate() {
	LocalDate Step5Results = objectToTest.getImageLocalDate();
	assertEquals(ldWorkingDate, Step5Results);
    }

    @Test
    public void testGetImageUrl() {
//	myExpectedStringResults = "http://safr.kingfeatures.com/idn/etv/zone/hiresimg.php?file=L2hvbWUvdmlydHVhbC9raW5nZmVhdHVyZXMuY29tL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS92YXIvd3d3L3JlcG8vT25UaGVGYXN0cmFjay8yMDE1LzAxL0Zhc3RfVHJhY2suMjAxNTAxMTkuanBn";
	myExpectedStringResults = "http://safr.kingfeatures.com/idn/cnfeed/zone/js/content.php?file=aHR0cDovL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS9CZWV0bGVCYWlsZXkvMjAxNS8wNC9CZWV0bGVfQmFpbGV5LjIwMTUwNDI4XzkwMC5naWY=";
	String strImageUrl = objectToTest.getImageUrl();
//	String strToCompare = Step2Results.substring(0, intLengthToCheck);
	assertEquals(myExpectedStringResults, strImageUrl);
    }

}
