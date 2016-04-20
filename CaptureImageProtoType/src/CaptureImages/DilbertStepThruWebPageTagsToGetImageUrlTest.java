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

public class DilbertStepThruWebPageTagsToGetImageUrlTest {

    private ProcessStartTags objProcessStartTags;
    private DilbertStepThruWebPageTagsToGetImageUrl objectToTest;
    
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    
    private String myExpectedStringResults;
    private BufferedReader bufferedStreamReader = null;
    private File webPageLocationOnLocalSystem = new File("../Dilbert.html");
    private HTMLEditorKit.Parser parser;
    private Reader myLineReader;
    private String MyResults = "NotWhatIWanted";

    @Before
    public void setUp() throws Exception {
	int WorkingYear = 2014;
	int WorkingMonth = 8;
	int WorkingDay = 28;
	LocalDate ldWorkingDate = LocalDate.of(WorkingYear, WorkingMonth, WorkingDay);
	objectToTest = new DilbertStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
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
	myExpectedStringResults = "img-comic-container";
	String Step1Results = objectToTest.getStep1RighDivTagValue();
	assertEquals(myExpectedStringResults, Step1Results);
    }

    @Test
    public void testGetImageUrl() {
//	myExpectedStringResults = "http://dilbert.com/dyn/str_strip/000000000/00000000/0000000/200000/20000/7000/700/227707/227707.strip.zoom.gif";
	myExpectedStringResults = "http://assets.amuniversal.com/ab213e4070e40132b90b005056a9545d";
//	String Step5Results = StepsToFollow.getStep5Results();
	String Step5Results = objectToTest.getStrImageUrl();
	assertEquals(myExpectedStringResults, Step5Results);
    }
    
    @Test
    public void testGetStrImageDate() {
	myExpectedStringResults = "20140828";
	String FinalStepResults = objectToTest.getStrImageDate();
	assertEquals(myExpectedStringResults, FinalStepResults);
    }
    

}
