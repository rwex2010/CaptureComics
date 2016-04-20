package CaptureImages;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class GoComicsImageWebPageUrlTest {
    GoComicsImageWebPageUrl objectToTest;
    URL testURL;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    String myExpectedResults;
    LocalDate testDate = LocalDate.now();
    String myTestString;

    @Before
    public void setUp() throws Exception {
	DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(0);
	objectToTest = new GoComicsImageWebPageUrl(dbgDisplay);
    }
    
    private String validateUrl(URL urlToValidate) {
	String ReturnValue = "Did not try";
	try {
	   URLConnection connection = urlToValidate.openConnection();
	   connection.connect();
	    ReturnValue = "Valid";
	} catch (IOException ioError) {
	    ReturnValue = ioError.getMessage();
	} catch (Exception otherError) {
	    ReturnValue = otherError.getMessage();
	}
	
	return ReturnValue;
    }

    @Test
    public void testUrlForCurrentDate() {
	objectToTest.setupWebPageUrl("ziggy", testDate);
	testURL = objectToTest.getWebPageUrl();
	String strValidation = validateUrl(testURL);
	if (strValidation == "Valid") {
		myTestString = testURL.toString();
	} else {
		myTestString = strValidation;
	}
	myExpectedResults = "http://www.gocomics.com/ziggy";
	assertEquals(myExpectedResults, myTestString);
    }

    @Test
    public void testUrlForPriorDate() {
	// fail("Not yet implemented"); // TODO
	testDate = testDate.minusDays(2);

	objectToTest.setupWebPageUrl("ziggy", testDate);
	testURL = objectToTest.getWebPageUrl();
	String strValidation = validateUrl(testURL);
	if (strValidation == "Valid") {
		myTestString = testURL.toString();
	} else {
		myTestString = strValidation;
	}
	myExpectedResults =  "http://www.gocomics.com/ziggy/" + testDate.format(formatter);
	assertEquals(myExpectedResults, myTestString);
    }


}
