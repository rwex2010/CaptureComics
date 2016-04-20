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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class KingFeaturesStepThruWebPageTagsToGetImageUrlTest {
    private ProcessStartTags objProcessStartTags;
    private KingFeaturesStepThruWebPageTagsToGetImageUrl objectToTest;

    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private String myExpectedStringResults;
    private BufferedReader bufferedStreamReader = null;
    private File webPageLocationOnLocalSystem = new File("../BeetleBaileyMay.html");
    private HTMLEditorKit.Parser parser;
    private Reader myLineReader;
    private String MyResults = "NotWhatIWanted";
    
    private void parseThisPage() {
	try {
	    parser.parse(myLineReader, new HTMLEditorKit.ParserCallback() {
		public void handleSimpleTag(Tag tagReturnedFromParse, MutableAttributeSet attributeSetReturnedFromParse, int pos) {
		    objectToTest.processNextStep();
		    if (objectToTest.tagToFind == null) {
			strImageDate = objProcessStartTags.getStrImageDate();
			strImageUrl = objProcessStartTags.getStrImageUrl();
			ldImageDate = objProcessStartTags.getLdImageDate();
		    } else {
			MyResults = "NotYet";
			if (objectToTest.tagToFind.equals(tagReturnedFromParse)) {
			    String attributeName = objectToTest.strAttributeToFind;
			    String propertyValue = objectToTest.strValueToFind;
			    String contentAttribute = objectToTest.strAttributeToGetValueFrom;
			    if (attributeSetReturnedFromParse.containsAttribute(attributeName, propertyValue)) {
//				String contentValue = (String) attributeSetReturnedFromParse.getAttribute(contentAttribute);
				Properties result = new Properties();
				Enumeration<?> names = attributeSetReturnedFromParse.getAttributeNames();
				Object name;
				while (names.hasMoreElements()) {
				    name = names.nextElement();
				    result.setProperty(name.toString(), attributeSetReturnedFromParse.getAttribute(name).toString());
				}
				MyResults = result.getProperty(contentAttribute); 
			    }

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
	int WorkingMonth = 5;
	int WorkingDay = 1;
	this.ldWorkingDate = LocalDate.of(WorkingYear, WorkingMonth, WorkingDay);
	objectToTest = new KingFeaturesStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
	objProcessStartTags = new ProcessStartTags();
	readImageWebPage();
	parser = new ParserDelegator();
	myLineReader = new LineNumberReader(bufferedStreamReader);

	parseThisPage();
    }

    @Test
    public void testGetStrImageUrl() {
//	myExpectedStringResults = "http://safr.kingfeatures.com/idn/cnfeed/zone/js/content.php?file=aHR0cDovL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS9CZWV0bGVCYWlsZXkvMjAxNS8wNC9CZWV0bGVfQmFpbGV5LjIwMTUwNDI4XzkwMC5naWY=";
////	myExpectedStringResults = "http://safr.kingfeatures.com/idn/cnfeed/zone/js/content.php?file=aHR0cDovL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS9CZWV0bGVCYWlsZXkvMjAxNS8wNS9CZWV0bGVfQmFpbGV5LjIwMTUwNTAxXzkwMC5naWY=";
	myExpectedStringResults = "http://safr.kingfeatures.com/idn/cnfeed/zone/js/content.php?file=aHR0cDovL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS9CZWV0bGVCYWlsZXkvMjAxNS8wNS9CZWV0bGVfQmFpbGV5LjIwMTUwNTAxXzkwMC5naWY=";
	
	String strImageUrl = objectToTest.getStrImageUrl();
	// String strToCompare = Step2Results.substring(0, intLengthToCheck);
	assertEquals(myExpectedStringResults, strImageUrl);
    }

    @Test
    public void testGetStrImageDate() {
	myExpectedStringResults = ldWorkingDate.format(dtImageDateFormatter);
	String strImageDate = objectToTest.getStrImageDate();
	assertEquals(myExpectedStringResults, strImageDate);
    }

    @Test
    public void testGetStep1Results() {
	myExpectedStringResults = "http://safr.kingfeatures.com/idn/cnfeed/zone/js/content.php";
	int intLengthToCheck = myExpectedStringResults.length();
	String Step1Results = objectToTest.getStep1Results();
	String strToCompare = Step1Results.substring(0, intLengthToCheck);
	assertEquals(myExpectedStringResults, strToCompare);
    }

    @Test
    public void testgetStep2Results() {
	myExpectedStringResults = ldWorkingDate.format(dtImageDateFormatter);
	String Step2Results = objectToTest.getStep2Results();
	assertEquals(myExpectedStringResults, Step2Results);
    }

}
