package CaptureImages;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.time.LocalDate;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.Parser;
import javax.swing.text.html.parser.ParserDelegator;

import org.junit.Before;
import org.junit.Test;

public class GoComicsStepThruWebPageTagsToGetImageUrlTest {

    private ProcessStartTags objProcessStartTags;
    private GoComicsStepThruWebPageTagsToGetImageUrl objectToTest;
    
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    
    private String myExpectedStringResults;
    private BufferedReader bufferedStreamReader = null;
    private File webPageLocationOnLocalSystem = new File("../gocomicLuann.html");
    private HTMLEditorKit.Parser parser;
    private Reader myLineReader;
    private String MyResults = "NotWhatIWanted";

    @Before
    public void setUp() throws Exception {
	int WorkingYear = 2014;
	int WorkingMonth = 8;
	int WorkingDay = 18;
	LocalDate ldWorkingDate = LocalDate.of(WorkingYear, WorkingMonth, WorkingDay);
	objectToTest = new GoComicsStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
	objProcessStartTags = new ProcessStartTags();
	readImageWebPage();
	parser = new ParserDelegator();
	myLineReader = new LineNumberReader(bufferedStreamReader);

	parseThisPage();

    }

    private void readImageWebPage() {
	try {
	    this.bufferedStreamReader = new BufferedReader(new FileReader(this.webPageLocationOnLocalSystem));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

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
			if (objectToTest.tagToFind.equals(tagReturnedFromParse)) {
			    MyResults = objProcessStartTags.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, objectToTest.tagToFind, objectToTest.attributeToFind, objectToTest.strValueToFind, objectToTest.intLengthOfValueToFind, objectToTest.attributeToGetValueFrom);
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
    
    @Test
    public void testGetStep1RighDivTagValue() {
	myExpectedStringResults = "content";
	String Step1Results = objectToTest.getStep1RighDivTagValue();
	assertEquals(myExpectedStringResults, Step1Results);
    }

    @Test
    public void testGetStep4RighDivTagValue() {
	myExpectedStringResults = "mutable_1197540";
	String TestResults = objectToTest.getStep4Results();
	assertEquals(myExpectedStringResults, TestResults);
    }

    @Test
    public void testGetImageUrl() {
	myExpectedStringResults = "http://assets.amuniversal.com/51df05a0047f013290e0005056a9545d";
//	String Step5Results = StepsToFollow.getStep5Results();
	String Step5Results = objectToTest.getStrImageUrl();
	assertEquals(myExpectedStringResults, Step5Results);
    }
    
    @Test
    public void testGetStrImageDate() {
	myExpectedStringResults = "20140818";
	String FinalStepResults = objectToTest.getStrImageDate();
	assertEquals(myExpectedStringResults, FinalStepResults);
    }
    

}
