package CaptureImages;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

import org.junit.Before;
import org.junit.Test;

public class ArcamaxStepThruWebPageTagsToGetImageUrlTest {
    private ProcessStartTags objProcessStartTags;
    private ArcamaxStepThruWebPageTagsToGetImageUrl objectToTest;

    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    private DateTimeFormatter dtSavedImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public boolean foundFigure = false;

    private String myExpectedStringResults;
    private BufferedReader bufferedStreamReader = null;
    private File webPageLocationOnLocalSystem = new File("../BabyBlues2.html");
    private HTMLEditorKit.Parser parser;
    private Reader myLineReader;
    private String MyResults = "NotWhatIWanted";

    private void parseThisPage() {
	try {
	    parser.parse(myLineReader, new HTMLEditorKit.ParserCallback() {
		public void handleSimpleTag(Tag tagReturnedFromParse, MutableAttributeSet attributeSetReturnedFromParse, int pos) {
		    if (tagReturnedFromParse.toString().equals("figure")) {
			foundFigure = true;
		    }
		    objectToTest.processNextStep();
		    if (objectToTest.tagToFind == null) {
			strImageDate = objProcessStartTags.getStrImageDate();
			strImageUrl = objProcessStartTags.getStrImageUrl();
			ldImageDate = objProcessStartTags.getLdImageDate();
		    } else {
			if (objectToTest.tagToFind.equals(tagReturnedFromParse)) {
			    Tag tagOneToFind = (Tag) objectToTest.tagToFind;
			    MyResults = objProcessStartTags.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, tagOneToFind, objectToTest.attributeToFind, objectToTest.strValueToFind, objectToTest.intLengthOfValueToFind, objectToTest.attributeToGetValueFrom);
			    objectToTest.checkReturnValues(MyResults);
			}
		    }
		}

		public void handleStartTag(HTML.Tag tagReturnedFromParse, MutableAttributeSet attributeSetReturnedFromParse, int pos) {
		    objectToTest.processNextStep();
		    if (objectToTest.tagToFind == null) {
			strImageDate = objProcessStartTags.getStrImageDate();
			strImageUrl = objProcessStartTags.getStrImageUrl();
			ldImageDate = objProcessStartTags.getLdImageDate();
		    } else {
			if (objectToTest.tagToFind.equals(tagReturnedFromParse)) {
			    MyResults = objProcessStartTags.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, objectToTest.tagToFind, objectToTest.attributeToFind, objectToTest.strValueToFind, objectToTest.intLengthOfValueToFind, objectToTest.attributeToGetValueFrom);
			    objectToTest.checkReturnValues(MyResults);
			}
		    }
		}

		public void handleText(char[] data, int pos) {
		    if (MyResults == "GotRightElement") {
			String DateOfImage = "";
			for (int ix = 0; ix < data.length; ix++) {
			    DateOfImage += data[ix];
			}
			DateOfImage += ", 2015";
			ldImageDate = LocalDate.parse(DateOfImage, dtImageDateFormatter);
			strImageDate = ldImageDate.format(dtSavedImageDateFormatter);

			objectToTest.checkReturnValues(strImageDate);

		    }
		}

	    }, true);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	MyResults = "Oops";
    }

    private void readImageWebPage() {
	try {
	    this.bufferedStreamReader = new BufferedReader(new FileReader(this.webPageLocationOnLocalSystem));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Before
    public void setUp() throws Exception {
	int WorkingYear = 2015;
	int WorkingMonth = 4;
	int WorkingDay = 16;
	this.ldWorkingDate = LocalDate.of(WorkingYear, WorkingMonth, WorkingDay);
	objectToTest = new ArcamaxStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
	objProcessStartTags = new ProcessStartTags();
	readImageWebPage();
	parser = new ParserDelegator();
	myLineReader = new LineNumberReader(bufferedStreamReader);

	parseThisPage();
    }

    // @Test
    // public void testThisTesting() {
    // myExpectedStringResults = "Something";
    // String HereIam = this.strWhereIAm;
    // // String strToCompare = Step2Results.substring(0, intLengthToCheck);
    // assertEquals(myExpectedStringResults, HereIam);
    //
    // // fail("Not yet implemented"); // TODO
    // }

    // @Test
    // public void testCheckReturnValues() {
    // fail("Not yet implemented"); // TODO
    // }
    //
    // @Test
    // public void testSetNextItemsToCheck() {
    // fail("Not yet implemented"); // TODO
    // }
    //
    // @Test
    // public void testSaveReturnValue() {
    // fail("Not yet implemented"); // TODO
    // }

    // @Test
    // public void testParseDateFormat() {
    // fail("Not yet implemented"); // TODO
    // }

    @Test
    public void testGetStrObjectName() {
	myExpectedStringResults = "ArcamaxStepThruWebPageTagsToGetImageUrl";
	String strImageUrl = objectToTest.getStrObjectName();
	assertEquals(myExpectedStringResults, strImageUrl);
	// fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetStrImageUrl() {
	myExpectedStringResults = "http://www.arcamax.com/newspics/115/11534/1153404.gif";
	String strImageUrl = objectToTest.getStrImageUrl();
	assertEquals(myExpectedStringResults, strImageUrl);
    }

    @Test
    public void testGetStrImageDate() {
	myExpectedStringResults = this.ldWorkingDate.format(dtSavedImageDateFormatter);
	String strTestResults = objectToTest.getStrImageDate();
	assertEquals(myExpectedStringResults, strTestResults);
    }

    @Test
    public void testGetStep1RighDivTagValue() {
	myExpectedStringResults = "comic";
	String strImageUrl = objectToTest.getStep1RighDivTagValue();
	assertEquals(myExpectedStringResults, strImageUrl);
    }

    @Test
    public void testGetStep2Results() {
	myExpectedStringResults = "http://www.arcamax.com/newspics/115/11534/1153404.gif";
	String strImageUrl = objectToTest.getStep2Results();
	assertEquals(myExpectedStringResults, strImageUrl);
    }

    @Test
    public void testGetPrevWebPageUrl() {
	myExpectedStringResults = "http://www.arcamax.com/thefunnies/babyblues/s-1639037";
	String strImageUrl = objectToTest.getStrPrevWebPageUrl();
	assertEquals(myExpectedStringResults, strImageUrl);
    }

    // @Test
    // public void testGetStep3Results() {
    // myExpectedStringResults =
    // "http://www.arcamax.com/thefunnies/babyblues/s-1639037";
    // String strImageUrl = objectToTest.getStep3Results();
    // assertEquals(myExpectedStringResults, strImageUrl);
    // }

    @Test
    public void testGetStep4Results() {
	myExpectedStringResults = this.ldWorkingDate.format(dtSavedImageDateFormatter);
	String strTestResults = objectToTest.getStep4Results();
	assertEquals(myExpectedStringResults, strTestResults);
    }

    // @Test
    // public void testGetStep5Results() {
    // fail("Not yet implemented"); // TODO
    // }

}
