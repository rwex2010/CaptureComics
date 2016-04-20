package CaptureImages;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class DilbertImageWebPageUrlTest {
    DilbertImageWebPageUrl objectToTest;
    ValidateMyUrl data = new ValidateMyUrl();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String myExpectedResults;
    LocalDate testDate = LocalDate.now();
    String myTestString;
    ValidateMyUrl valMyUrl = new ValidateMyUrl();

    @Before
    public void setUp() throws Exception {
	DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(0);
	objectToTest = new DilbertImageWebPageUrl(dbgDisplay);

    }

    @Test
    public void testUrlForCurrentDate() {
	objectToTest.setupWebPageUrl("dilbert", testDate);
	data.testURL = objectToTest.getWebPageUrl();
//	String strValidation = validateUrl(data.testURL);
	String strValidation = valMyUrl.validateUrl(data.testURL);
	if (strValidation == "Valid") {
		myTestString = data.testURL.toString();
	} else {
		myTestString = strValidation;
	}
	myExpectedResults = "http://dilbert.com/strip/" + testDate.format(formatter);
	assertEquals(myExpectedResults, myTestString);
    }

    @Test
    public void testUrlForPriorDate() {
	// fail("Not yet implemented"); // TODO
	testDate = testDate.minusDays(2);

	objectToTest.setupWebPageUrl("dilbert", testDate);
	data.testURL = objectToTest.getWebPageUrl();
	String strValidation = valMyUrl.validateUrl(data.testURL);
	if (strValidation == "Valid") {
		myTestString = data.testURL.toString();
	} else {
		myTestString = strValidation;
	}
	myExpectedResults = "http://dilbert.com/strip/" + testDate.format(formatter);
	assertEquals(myExpectedResults, myTestString);
    }

}
