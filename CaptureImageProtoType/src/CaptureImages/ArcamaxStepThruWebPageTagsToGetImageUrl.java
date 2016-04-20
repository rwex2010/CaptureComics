package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.UnknownTag;
import javax.swing.text.html.parser.ContentModel;

//import com.sun.java.util.jar.pack.Attribute.Layout.Element;

public class ArcamaxStepThruWebPageTagsToGetImageUrl extends StepThruWebPageTagsToGetImageUrl {
    public HTML.Tag tagToFind = HTML.Tag.DIV;
    public HTML.Attribute attributeToFind;
    public HTML.Attribute attributeToGetValueFrom;
    public String strValueToFind;
    public int intLengthOfValueToFind;
    public int intNextStepToCheck = 1;
    private String strImageUrl = "";
    private String strPrevWebPageUrl = "";
    private String strImageDate = "";
    private String strWorkingDate;
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private HTML.UnknownTag FIGURE;

    private String step1RightDivTagValue;
    private String Step2Results;
    private String Step3Results;
    private String Step4Results;
    private String Step5Results;
    public String strObjectName = "ArcamaxStepThruWebPageTagsToGetImageUrl";

    ArcamaxStepThruWebPageTagsToGetImageUrl(LocalDate ldWorkingDate) {
	super(ldWorkingDate);
	this.ldWorkingDate = ldWorkingDate;
	this.strWorkingDate = ldWorkingDate.format(formatter);
	FIGURE = new HTML.UnknownTag("figure");
    }

    @Override
    void processNextStep(int intNextStep) {
	this.intNextStepToCheck = intNextStep;
	setNextItemsToCheck();
    }

    @Override
    void processNextStep() {
	setNextItemsToCheck();
    }

    @Override
    void checkReturnValues(String strTestResults) {
	saveReturnValue(strTestResults);
    }

    @Override
    void setNextItemsToCheck() {
	switch (intNextStepToCheck) {
	case 1:
	    this.tagToFind = FIGURE;
	    this.attributeToFind = HTML.Attribute.CLASS;
	    this.strValueToFind = "comic";
	    this.attributeToGetValueFrom = HTML.Attribute.CLASS;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 2:
	    this.tagToFind = HTML.Tag.IMG;
	    this.attributeToFind = HTML.Attribute.SRC;
	    this.strValueToFind = "FinalUrl";
	    this.attributeToGetValueFrom = HTML.Attribute.SRC;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 3:
	    this.tagToFind = HTML.Tag.A;
	    this.attributeToFind = HTML.Attribute.CLASS;
	    this.strValueToFind = "prev";
	    this.attributeToGetValueFrom = HTML.Attribute.HREF;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 4:
	    this.tagToFind = HTML.Tag.SPAN;
	    this.attributeToFind = HTML.Attribute.CLASS;
	    this.strValueToFind = "cur";
	    this.attributeToGetValueFrom = HTML.Attribute.VALUE;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 5:
	    this.tagToFind = HTML.Tag.IMG;
	    this.attributeToFind = HTML.Attribute.SRC;
	    this.strValueToFind = "FinalUrl";
	    this.attributeToGetValueFrom = HTML.Attribute.SRC;
	    this.intLengthOfValueToFind = 0;
	    break;

	default:
	    this.tagToFind = null;
	    this.attributeToFind = null;
	    this.strValueToFind = "done";
	    this.attributeToGetValueFrom = null;
	    this.intLengthOfValueToFind = 0;
	    break;
	}
    }

    @Override
    void saveReturnValue(String strTestResults) {
	switch (intNextStepToCheck) {
	case 1:
	    if (strTestResults.equals(this.strValueToFind)) {
		step1RightDivTagValue = strTestResults;
		intNextStepToCheck++;
	    }
	    break;
	case 2:
	    Step2Results = "http://www.arcamax.com" + strTestResults;
	    strImageUrl = strTestResults;
//	    strImageUrl = Step2Results;
System.out.println("Case2 ImageUrl: "+strImageUrl);
System.out.println("Case2 WorkingDate: "+strWorkingDate);
	    intNextStepToCheck++;
	    break;
	case 3:
	    // Step3Results = "http://www.arcamax.com" + strTestResults;
//	    strPrevWebPageUrl = "http://www.arcamax.com" + strTestResults;
	    strPrevWebPageUrl = strTestResults;
System.out.println("Case3 webpageurl: "+strPrevWebPageUrl);	    
System.out.println("Case3 WorkingDate: "+strWorkingDate);
	    intNextStepToCheck++;
	    break;
	case 4:
	    if (strTestResults.equals(strWorkingDate)) {
		Step4Results = strTestResults;
		strImageDate = strTestResults;
		intNextStepToCheck = 99;
System.out.println("Case4 strWorkingDate: " + strWorkingDate);		
	    }
	    break;
	// case 5:
	// Step5Results = strTestResults;
	// strImageUrl = strTestResults;
	// intNextStepToCheck++;
	// break;
	default:
	    break;
	}
    }

    @Override
    LocalDate parseDateFormat(String strComicDateString) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    String getStrObjectName() {
	return strObjectName;
    }

    @Override
    String getStrImageUrl() {
	return strImageUrl;
    }

    public String getStrPrevWebPageUrl() {
	return strPrevWebPageUrl;
    }

    @Override
    LocalDate getLdImageDate() {
	return ldImageDate;
    }

    @Override
    String getStrImageDate() {
	return strImageDate;
    }

    @Override
    String getStep1RighDivTagValue() {
	return step1RightDivTagValue;
    }

    @Override
    String getStep2Results() {
	return Step2Results;
    }

    @Override
    String getStep3Results() {
	return Step3Results;
    }

    @Override
    String getStep4Results() {
	return Step4Results;
    }

    @Override
    String getStep5Results() {
	return Step5Results;
    }
}
