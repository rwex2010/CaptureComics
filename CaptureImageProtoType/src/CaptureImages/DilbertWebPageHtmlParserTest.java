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

public class DilbertWebPageHtmlParserTest {
//  private RichWebPageHtmlParser objectToTest;
  private DilbertWebPageHtmlParser objectToTest;
  
  private LocalDate ldWorkingDate = LocalDate.of(2014, 8, 28);
//  private String strImageName = "dilbert";
  private URL urlImageWebPage;
  private BufferedReader bufferedStreamReader = null;
  private DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(1);
  
  private String myExpectedStringResults;
  private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
//	objectToTest = new RichWebPageHtmlParser();
	objectToTest = new DilbertWebPageHtmlParser();
	urlImageWebPage = new URL("file:../Dilbert.html");
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
	myExpectedStringResults = "20140828";
	String Step4Results = objectToTest.getImageStringDate();
	assertEquals(myExpectedStringResults, Step4Results);
    }

    @Test
    public void testGetImageLocalDate() {
//	myExpectedStringResults = "20140828";
	LocalDate Step5Results = objectToTest.getImageLocalDate();
	assertEquals(ldWorkingDate, Step5Results);
    }

    @Test
    public void testGetImageUrl() {
//	myExpectedStringResults = "http://dilbert.com/dyn/str_strip/000000000/00000000/0000000/200000/20000/7000/700/227707/227707.strip.zoom.gif";
	myExpectedStringResults = "http://assets.amuniversal.com/ab213e4070e40132b90b005056a9545d";
	String Step2Results = objectToTest.getImageUrl();
	assertEquals(myExpectedStringResults, Step2Results);
    }

}
