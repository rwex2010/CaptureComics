package CaptureImages;

import static org.junit.Assert.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class KingFeaturesImageWebPageURLTest {
    private KingFeaturesImageWebPageURL target;
    String[] aryComicCode = { "zits", "retail", "tinasgroove", "beetlebailey", "onthefastrack" };

    @Before
    public void setUp() throws Exception {
	DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(0);
	target = new KingFeaturesImageWebPageURL(dbgDisplay);
    }

    @Test
    public void testSetupWebPageUrl() {
	LocalDate testDate = LocalDate.now();

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-d-yyyy");
	foreachLoop(testDate, formatter);

	testDate = testDate.minusDays(2);
	foreachLoop(testDate, formatter);

	// fail("Not yet implemented"); // TODO
    }

    public void foreachLoop(LocalDate testDate, DateTimeFormatter formatter) {
	URL testURL;
	String myTestString;
	String myExpectedResults;
	String myDomain;
	for (int ix = 0; ix < aryComicCode.length; ix++) {
	    target.setupWebPageUrl(aryComicCode[ix], testDate);
	    testURL = target.getWebPageUrl();
	    myTestString = testURL.toString();

	    switch (aryComicCode[ix]) {
	    case "zits":
		myDomain = aryComicCode[ix] + "comics.com/comics/";
		break;

	    case "retail":
		myDomain = aryComicCode[ix] + "comic.com/comics/";
		break;

	    default:
		myDomain = aryComicCode[ix] + ".com/comics/";
		break;
	    }
	    myExpectedResults = "http://" + myDomain + testDate.format(formatter);
	    assertEquals(myExpectedResults, myTestString);

	}
    }

}
