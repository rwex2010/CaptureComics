package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTML;

public class GoComicsStepThruWebPageTagsToGetImageUrl extends StepThruWebPageTagsToGetImageUrl {
    public HTML.Tag tagToFind = HTML.Tag.DIV;
    public HTML.Attribute attributeToFind;
    public HTML.Attribute attributeToGetValueFrom;
    public String strValueToFind;
    public int intLengthOfValueToFind;
    public int intNextStepToCheck = 1;
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private String step1RightDivTagValue;
    private String Step2Results;
    private String Step3Results;
    private String Step4Results;
    private String Step5Results;
    public String strObjectName = "GoComicsStepThruWebPageTagsToGetImageUrl";

    public GoComicsStepThruWebPageTagsToGetImageUrl(LocalDate ldWorkingDate) {
	// TODO Auto-generated constructor stub
	super(ldWorkingDate);
	this.ldWorkingDate = ldWorkingDate;
    }

    public void processNextStep(int intNextStep) {
	this.intNextStepToCheck = intNextStep;
	setNextItemsToCheck();
    }

    public void processNextStep() {
	setNextItemsToCheck();
    }

    public void checkReturnValues(String strTestResults) {
	saveReturnValue(strTestResults);
    }

    public void setNextItemsToCheck() {

	switch (intNextStepToCheck) {
	case 1:
	    this.tagToFind = HTML.Tag.DIV;
	    this.attributeToFind = HTML.Attribute.ID;
	    this.strValueToFind = "content";
	    this.attributeToGetValueFrom = HTML.Attribute.ID;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 2:
	    this.tagToFind = HTML.Tag.H1;
	    this.attributeToFind = null;
	    this.strValueToFind = null;
	    this.attributeToGetValueFrom = null;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 3:
	    this.tagToFind = HTML.Tag.A;
	    this.attributeToFind = HTML.Attribute.HREF;
	    this.strValueToFind = "DateFormat";
	    this.attributeToGetValueFrom = HTML.Attribute.HREF;
	    this.intLengthOfValueToFind = 0;
	    break;

	case 4:
	    this.tagToFind = HTML.Tag.DIV;
	    this.attributeToFind = HTML.Attribute.ID;
	    this.strValueToFind = "mutable_";
	    this.attributeToGetValueFrom = HTML.Attribute.ID;
	    this.intLengthOfValueToFind = 8;
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

    public void saveReturnValue(String strTestResults) {
	switch (intNextStepToCheck) {
	case 1:
	    if (strTestResults.equals(this.strValueToFind)) {
		step1RightDivTagValue = strTestResults;
		intNextStepToCheck++;
	    }
	    break;
	case 2:
	    if (strTestResults.equals("GotRightTag")) {
		Step2Results = strTestResults;
		intNextStepToCheck++;
	    }
	    break;
	case 3:
	    LocalDate ldDateFromWebPage = parseDateFormat(strTestResults);
	    if (ldDateFromWebPage != null) {
		if (this.ldWorkingDate.equals(ldDateFromWebPage)) {
		    Step3Results = ldDateFromWebPage.format(formatter);
		    intNextStepToCheck++;
		} else {
		    intNextStepToCheck = 99;
		}
	    }
	    break;
	case 4:
	    if (strTestResults.length() > 8) {
		if (strTestResults.substring(0, 8).equals(this.strValueToFind)) {
		    Step4Results = strTestResults;
		    intNextStepToCheck++;
		}
	    }
	    break;
	case 5:
	    Step5Results = strTestResults;
	    strImageUrl = strTestResults;
	    intNextStepToCheck++;
	    break;
	default:
	    break;
	}
    }

    public LocalDate parseDateFormat(String strComicDateString) {
	LocalDate ldReturnValue = null;
	String[] aryHrefDate = strComicDateString.split("/");

	if (aryHrefDate.length >= 5) {

	    strImageDate = aryHrefDate[2] + aryHrefDate[3] + aryHrefDate[4];

	    Integer intYear = new Integer(aryHrefDate[2]);
	    Integer intMonth = new Integer(aryHrefDate[3]);
	    Integer intDay = new Integer(aryHrefDate[4]);
	    ldImageDate = LocalDate.of(intYear, intMonth, intDay);
	    ldReturnValue = ldImageDate;

	}
	return ldReturnValue;
    }

    public String getStrObjectName() {
	return strObjectName;
    }

    public String getStrImageUrl() {
	return strImageUrl;
    }

    public LocalDate getLdImageDate() {
	return ldImageDate;
    }

    public String getStrImageDate() {
	return strImageDate;
    }

    public String getStep1RighDivTagValue() {
	return step1RightDivTagValue;
    }

    public String getStep2Results() {
	return Step2Results;
    }

    public String getStep3Results() {
	return Step3Results;
    }

    public String getStep4Results() {
	return Step4Results;
    }

    public String getStep5Results() {
	return Step5Results;
    }

}
