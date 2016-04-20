package CaptureImages;

import static org.junit.Assert.*;

import java.io.BufferedReader;
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

public class GoComicsWebPageHtmlParserTest {
//    private RichWebPageHtmlParser objectToTest;
    private GoComicsWebPageHtmlParser objectToTest;
    
    private LocalDate ldWorkingDate = LocalDate.of(2014, 8, 18);
    private String strImageName = "luann";
    private URL urlImageWebPage;
    private BufferedReader bufferedStreamReader = null;
    private DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(1);
    
    private String myExpectedStringResults;
    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    @Before
    public void setUp() throws Exception {
//	objectToTest = new RichWebPageHtmlParser();
	objectToTest = new GoComicsWebPageHtmlParser();
	urlImageWebPage = new URL("file:../gocomicLuann.html");
	readImageWebPage();

	HTMLEditorKit.Parser parser = new ParserDelegator();
	Reader myLineReader = new LineNumberReader(bufferedStreamReader);

	objectToTest.setDebugMessageHandler(dbgDisplay);
	objectToTest.setWorkingDate(ldWorkingDate);
	objectToTest.setImageName(strImageName);

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
    public void FirstTest() {
	myExpectedStringResults = "div|h1|a|div|img|";
	String Step1Results = objectToTest.getMyTestResults();
	assertEquals(myExpectedStringResults, Step1Results);
    }

    @Test
    public void testGetImageStringDate() {
	myExpectedStringResults = "20140818";
	String Step4Results = objectToTest.getImageStringDate();
	assertEquals(myExpectedStringResults, Step4Results);
    }

    @Test
    public void testGetImageLocalDate() {
	myExpectedStringResults = "08/18/2014";
	LocalDate ldStep3Results = objectToTest.getImageLocalDate();
	String Step3Results = ldStep3Results.format(dtFormatter);
	assertEquals(myExpectedStringResults, Step3Results);
    }

    @Test
    public void testGetImageUrl() {
	myExpectedStringResults = "http://assets.amuniversal.com/51df05a0047f013290e0005056a9545d";
	String Step2Results = objectToTest.getImageUrl();
	assertEquals(myExpectedStringResults, Step2Results);
    }

}
