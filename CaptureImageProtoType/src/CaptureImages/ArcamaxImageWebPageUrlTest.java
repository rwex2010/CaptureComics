package CaptureImages;

import static org.junit.Assert.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

public class ArcamaxImageWebPageUrlTest {
ArcamaxImageWebPageUrl target;
    
    @Before
    public void setUp() throws Exception {
	DisplayDebugMessage dbgDisplay = new DisplayDebugMessage(0);
	target = new ArcamaxImageWebPageUrl(dbgDisplay);
	
    }

    @Test
    public void testGetWebPageUrl() {
	URL testURL;
	String myTestString;
	String myExpectedResults;
	LocalDate testDate = LocalDate.now();

	target.setupWebPageUrl("babyblues", testDate);
	
	testURL = target.getWebPageUrl();
	myTestString = testURL.toString();
	myExpectedResults = "http://www.arcamax.com/thefunnies/babyblues";
	assertEquals(myExpectedResults, myTestString);
    }

}
