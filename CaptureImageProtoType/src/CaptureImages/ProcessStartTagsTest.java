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

public class ProcessStartTagsTest {

    private ProcessStartTags objectToTest;
    private GoComicsStepThruWebPageTagsToGetImageUrl StepsToFollow;
    
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
	StepsToFollow = new GoComicsStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
	objectToTest = new ProcessStartTags();
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
		    StepsToFollow.processNextStep();
		    if (StepsToFollow.tagToFind == null) {
			strImageDate = objectToTest.getStrImageDate();
			strImageUrl = objectToTest.getStrImageUrl();
			ldImageDate = objectToTest.getLdImageDate();
		    } else {
			if (StepsToFollow.tagToFind.equals(tagReturnedFromParse)) {
			    MyResults = objectToTest.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, StepsToFollow.tagToFind, StepsToFollow.attributeToFind, StepsToFollow.strValueToFind, StepsToFollow.intLengthOfValueToFind, StepsToFollow.attributeToGetValueFrom);
			    StepsToFollow.checkReturnValues(MyResults);
			}
		    }
		}

		public void handleStartTag(HTML.Tag tagReturnedFromParse, MutableAttributeSet attributeSetReturnedFromParse, int pos) {
		    StepsToFollow.processNextStep();
		    if (StepsToFollow.tagToFind == null) {
			 strImageDate = objectToTest.getStrImageDate();
			 strImageUrl = objectToTest.getStrImageUrl();
			 ldImageDate = objectToTest.getLdImageDate();
		    } else {
			if (StepsToFollow.tagToFind.equals(tagReturnedFromParse)) {
			    MyResults = objectToTest.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, StepsToFollow.tagToFind, StepsToFollow.attributeToFind, StepsToFollow.strValueToFind, StepsToFollow.intLengthOfValueToFind, StepsToFollow.attributeToGetValueFrom);
			    StepsToFollow.checkReturnValues(MyResults);
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
    public void testStep1Results() {
	myExpectedStringResults = "content";
	String Step1Results = StepsToFollow.getStep1RighDivTagValue();
	assertEquals(myExpectedStringResults, Step1Results);
    }

    @Test
    public void testStep2Results() {
	myExpectedStringResults = "GotRightTag";
	String Step2Results = StepsToFollow.getStep2Results();
	assertEquals(myExpectedStringResults, Step2Results);
    }

    @Test
    public void testStep3Results() {
	myExpectedStringResults = "2014/08/18";
	String Step3Results = StepsToFollow.getStep3Results();
	assertEquals(myExpectedStringResults, Step3Results);
    }

    @Test
    public void testStep4Results() {
	myExpectedStringResults = "mutable_1197540";
	String Step4Results = StepsToFollow.getStep4Results();
	assertEquals(myExpectedStringResults, Step4Results);
    }

    @Test
    public void testStep5Results() {
	myExpectedStringResults = "http://assets.amuniversal.com/51df05a0047f013290e0005056a9545d";
	String Step5Results = StepsToFollow.getStep5Results();
	assertEquals(myExpectedStringResults, Step5Results);
    }
}
