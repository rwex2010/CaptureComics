package CaptureImages;

import static org.junit.Assert.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class DilbertComicStripImagesTest {
    ComicStrip csData = new ComicStrip();
    private DisplayDebugMessage dbgDisplay;
    private DilbertComicStripImages objectToTest;
    private LocalDate[] aryDates = new LocalDate[2];
    private LocalDate ldWorkingDate;
    private String strFolderToSaveImg = "C:/Users/wecksr/Documents/ComicStripImages/";
    String myExpectedResults;
    ValidateMyUrl valMyUrl = new ValidateMyUrl();
    DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
	dbgDisplay = new DisplayDebugMessage(1);
	objectToTest = new DilbertComicStripImages(dbgDisplay);
	LocalDate ldToday = LocalDate.of(2015, 1, 14);
	aryDates[0] = ldToday;
	aryDates[1] = ldToday.plusDays(3);
	objectToTest.initializeClassVariables(aryDates, strFolderToSaveImg);
	// objectToTest.setDriverObject(this);
	setCsData();
	objectToTest.setComicStrip(csData);
//	objectToTest.setStrComicCode(csData.ComicCode);
	objectToTest.setDateRelatedFields(aryDates);
//	objectToTest.setGoComicWebPageUrlObject();
	objectToTest.findWebPageUrlForThisComic();
    }

    private void setCsData() {
	csData.ComicCode = "dilbert";
	csData.ComicName = "Dilbert";
	csData.DailyExt = "gif";
	csData.DateFormat = "yyyy-mm-dd";
	csData.DaysAvailable = "254";
	csData.Domain = "Dilbert.com";
	csData.id = "ID";
	csData.OneOfMyComics = "true";
	csData.SundayAvail = "true";
	csData.SundayExt = "gif";
	csData.UrlFormat = "http://imgsrv.gocomics.com/dim/?fh={ComicNumber}&amp;w=900.0";
    }

    @Test
    public void testgetUrlWebpage() {
	String Step4Results = "";
	myExpectedResults = "http://dilbert.com/strip/2015-01-14";
	URL urlResults = objectToTest.getUrlWebpage();

	if (urlResults == null) {
	    Step4Results = "urlResults is null";
	} else {
	    String strValidation = valMyUrl.validateUrl(urlResults);
	    if (strValidation == "Valid") {
		Step4Results = urlResults.toExternalForm();
	    } else {
		Step4Results = "URL is not valid";
	    }
	}
	assertEquals(myExpectedResults, Step4Results);
    }

    @Test
    public void testgetDtStartDate() {
	String myResults = "";
	myExpectedResults = "2015-01-14";
	LocalDate ldStartDate = objectToTest.getDtStartDate();
	myResults = ldStartDate.format(dtImageDateFormatter);
	assertEquals(myExpectedResults, myResults);
    }
    
    @Test
    public void testgetDtEndDate() {
	String myResults = "";
	myExpectedResults = "2015-01-17";
	LocalDate ldEndDate = objectToTest.getDtEndDate();
	myResults = ldEndDate.format(dtImageDateFormatter);
	assertEquals(myExpectedResults, myResults);
    }

    @Test
    public void testgetPerDateRange() {
 	int myResults = 0;
	int myIntExpectedResults = 3;
	Period perLength = objectToTest.getPerDateRange();
	myResults = perLength.getDays();
	assertEquals(myIntExpectedResults, myResults);
   }

    @Test
    public void testgetIntLoopLimit() {
 	int myResults = 0;
	int myIntExpectedResults = 8;
	myResults = objectToTest.getIntLoopLimit();
	assertEquals(myIntExpectedResults, myResults);
    }

}
