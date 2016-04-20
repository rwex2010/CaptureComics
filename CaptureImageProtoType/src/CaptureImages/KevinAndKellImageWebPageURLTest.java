package CaptureImages;

import static org.junit.Assert.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class KevinAndKellImageWebPageURLTest {
    private KevinAndKellImageWebPageURL target;

    @Before
    public void setUp() throws Exception {
	DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(0);
	target = new KevinAndKellImageWebPageURL(dbgDisplay);
    }

    @Test
    public void testSetupWebPageUrl() {
	URL testURL;
	String myTestString;
	String myExpectedResults;
	LocalDate testDate = LocalDate.now();

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
	int workingYear = testDate.getYear();

	target.setupWebPageUrl("kk", testDate);
	testURL = target.getWebPageUrl();
	myTestString = testURL.toString();
	myExpectedResults = "http://www.kevinandkell.com";
	myExpectedResults += "/" + workingYear + "/kk" + testDate.format(formatter) + ".html";
	assertEquals(myExpectedResults, myTestString);

	testDate = testDate.minusDays(2);

	target.setupWebPageUrl("kk", testDate);
	testURL = target.getWebPageUrl();
	myTestString = testURL.toString();
	myExpectedResults = "http://www.kevinandkell.com";
	myExpectedResults += "/" + workingYear + "/kk" + testDate.format(formatter) + ".html";
	assertEquals(myExpectedResults, myTestString);
    }

}
