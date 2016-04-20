package CaptureImages;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class GoComicsHtmlWebPageReaderTest {
//    private File webPageLocationOnLocalSystem = new File("../gocomicLuann.html");
    private URL urlImageWebPage;
    private DisplayDebugMessage dbgDisplay;
    private GoComicsHtmlWebPageReader objectToTest;
    private String myExpectedStringResults;
    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private LocalDate ldWorkingDate = LocalDate.of(2014, 8, 18);
    private String strImageName = "luann";


    @Before
    public void setUp() throws Exception {
	urlImageWebPage = new URL("file:../gocomicLuann.html");
	dbgDisplay = new DisplayDebugMessage(1);
	objectToTest=new GoComicsHtmlWebPageReader(dbgDisplay);
	objectToTest.setWebPageURL(urlImageWebPage);
	objectToTest.setWorkingDate(ldWorkingDate);
	objectToTest.setImageName(strImageName);
//	read(urlImageWebPage);
	objectToTest.parseImageUrlFromImageWebPage("");
    }
    
    public void read( URL urlImageWebPage ) {
        try {
          java.net.URL url = urlImageWebPage;
          java.io.InputStream is = url.openStream();
          int ch;
          while ( ( ch = is.read() ) != -1 ) {
            System.out.print( (char)ch );
          }
          is.close();
        }
        catch ( java.io.IOException ex ) {
          System.err.println( "Error reading " +
             "URL : " + ex );
        }
      }

    @Test
    public void testreadImageWebPage() {
	myExpectedStringResults = "GotIt";
	String strResultsTest1 = objectToTest.strResultsTest1;
	assertEquals(myExpectedStringResults, strResultsTest1);
    }

     @Test
    public void testParseImageUrlFromImageWebPage() {
	myExpectedStringResults = "CompletedNoErrors";
	String strResultsTest2 = objectToTest.strResultsTest2;
	assertEquals(myExpectedStringResults, strResultsTest2);
    }

     @Test
    public void testCheckStepObjectName() {
	myExpectedStringResults = "GoComicsStepThruWebPageTagsToGetImageUrl";
	String strResultsTest3 = objectToTest.strResultsTest3;
	assertEquals(myExpectedStringResults, strResultsTest3);
    }

   @Test
    public void testGetImageUrl() {
	myExpectedStringResults = "http://assets.amuniversal.com/51df05a0047f013290e0005056a9545d";
	String strResultsTest3 = objectToTest.getImageUrl();
	assertEquals(myExpectedStringResults, strResultsTest3);
    }

    @Test
    public void testGetImageId() {
	myExpectedStringResults = "luann20140818";
	String strResultsTest4 = objectToTest.getImageId();
	assertEquals(myExpectedStringResults, strResultsTest4);
    }

    @Test
    public void testGetImageDate() {
	myExpectedStringResults = "08/18/2014";
	LocalDate ldImgDate = objectToTest.getImageDate();
	String strResultsTest5 = ldImgDate.format(dtFormatter);
	assertEquals(myExpectedStringResults, strResultsTest5);
    }

}
