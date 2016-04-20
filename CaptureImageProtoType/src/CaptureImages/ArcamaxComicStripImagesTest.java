package CaptureImages;

import static org.junit.Assert.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class ArcamaxComicStripImagesTest {
    ComicStrip csData = new ComicStrip();
    private URL urlImageWebPage;
    private ArcamaxHtmlWebPageReader arcamaxFindImageUrl;
   private DisplayDebugMessage dbgDisplay;
    private ArcamaxComicStripImages objectToTest;
    private LocalDate[] aryDates = new LocalDate[2];
    private LocalDate ldWorkingDate;
    private String strFolderToSaveImg = "C:/Users/wecksr/Documents/ComicStripImages/";
    String myExpectedResults;
    ValidateMyUrl valMyUrl = new ValidateMyUrl();
    DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
	dbgDisplay = new DisplayDebugMessage(1);
	objectToTest = new ArcamaxComicStripImages(dbgDisplay);
	LocalDate ldToday = LocalDate.of(2015, 2, 3);
	aryDates[0] = ldToday;
	aryDates[1] = ldToday.plusDays(3);
	objectToTest.initializeClassVariables(aryDates, strFolderToSaveImg);
	// objectToTest.setDriverObject(this);
	urlImageWebPage = new URL("file:../BabyBlues2.html");
	    arcamaxFindImageUrl = new ArcamaxHtmlWebPageReader(this.dbgDisplay);
	setCsData();
	objectToTest.setComicStrip(csData);
	objectToTest.setStrComicCode(csData.ComicCode);
	objectToTest.setDateRelatedFields(aryDates);
	// objectToTest.setGoComicWebPageUrlObject();
	objectToTest.findWebPageUrlForThisComic();
    }

    private void setCsData() {
	csData.ComicCode = "babyblues";
	csData.ComicName = "Baby Blues";
	csData.DailyExt = "jpg";
	csData.DateFormat = "yyyy-mm-dd";
	csData.DaysAvailable = "254";
	csData.Domain = "arcamax";
	csData.id = "ID";
	csData.OneOfMyComics = "true";
	csData.SundayAvail = "true";
	csData.SundayExt = "jpg";
	csData.UrlFormat = "http://www.arcamax.com/thefunnies/babyblues/";
    }

    @Test
    public void testgetUrlWebpage() {
	String Step4Results = "";
	myExpectedResults = "http://www.arcamax.com/thefunnies/" + csData.ComicCode;
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
    public void testThisDayIsValidForThisComic() {
	myExpectedResults = "Valid Day";
	boolean testResults;
	testResults = objectToTest.thisDayIsValidForThisComic();
	String testHere = "Not Valid";
	if (testResults) {
	    testHere = "Valid Day";
	} else {
	    testHere = "Not Valid";
	}
	assertEquals(myExpectedResults, testHere);
    }

    @Test
    public void testGetPerDateRange() {
	myExpectedResults = "";
	LocalDate dtStartDate = LocalDate.now();
	LocalDate dtEndDate = aryDates[0];
	Period perDateRange = dtEndDate.until(dtStartDate);
	int myNumericExpectedResults = perDateRange.getDays();
	Period dateRange = objectToTest.getPerDateRange();
	int intRange = dateRange.getDays();
	assertEquals(myNumericExpectedResults, intRange);
    }

    @Test
    public void testGetStrComicCode() {
	myExpectedResults = csData.ComicCode;
	String strTestResults = objectToTest.getStrComicCode();
	assertEquals(myExpectedResults, strTestResults);
    }

    @Test
    public void testGetStrImageUrl() {
//	myExpectedResults = "http://www.arcamax.com/newspics/cache/w800/112/11229/1122931.jpg";
	myExpectedResults = "http://www.arcamax.com/newspics/115/11534/1153404.gif";
	objectToTest.setClassVariablesForImage();
	objectToTest.setUrlWebpage(urlImageWebPage);
	objectToTest.setImageUrlForThisComic();
	objectToTest.setClassVariablesForImage();
	String strTestResults = objectToTest.getStrImageUrl();
	assertEquals(myExpectedResults, strTestResults);
	
//	fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetStrImageId() {
	myExpectedResults = "babyblues20150416";
	objectToTest.setClassVariablesForImage();
	objectToTest.setUrlWebpage(urlImageWebPage);
	objectToTest.setImageUrlForThisComic();
	objectToTest.setClassVariablesForImage();
	String strTestResults = objectToTest.getStrImageId();
	assertEquals(myExpectedResults, strTestResults);
//	fail("Not yet implemented"); // TODO
    }

}
