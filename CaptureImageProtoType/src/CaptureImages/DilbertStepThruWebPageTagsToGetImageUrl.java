package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTML;

public class DilbertStepThruWebPageTagsToGetImageUrl {
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
    private DateTimeFormatter dtWorkingDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private String step1RightDivTagValue;
    private String Step2Results;
    private String Step3Results;
    private String Step4Results;
    private String Step5Results;

    public DilbertStepThruWebPageTagsToGetImageUrl(LocalDate ldWorkingDate) {
	// TODO Auto-generated constructor stub
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

    private void setNextItemsToCheck() {

	switch (intNextStepToCheck) {
	case 1:
	    this.tagToFind = HTML.Tag.DIV;
	    // this.attributeToFind = HTML.Attribute.ID;
	    // this.strValueToFind = "strip_enlarged_" +
	    // ldWorkingDate.format(dtWorkingDateFormatter);
	    this.attributeToFind = HTML.Attribute.CLASS;
	    this.strValueToFind = "img-comic-container";
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

	default:
	    this.tagToFind = null;
	    this.attributeToFind = null;
	    this.strValueToFind = "done";
	    this.attributeToGetValueFrom = null;
	    this.intLengthOfValueToFind = 0;
	    break;
	}
    }

    private void saveReturnValue(String strTestResults) {
	switch (intNextStepToCheck) {
	case 1:
	    if (strTestResults.equals(this.strValueToFind)) {
		step1RightDivTagValue = strTestResults;
		this.ldImageDate = this.ldWorkingDate;
		this.strImageDate = this.ldWorkingDate.format(dtImageDateFormatter);
		intNextStepToCheck++;
	    }
	    break;
	case 2:
	    Step5Results = strTestResults;
	    // strImageUrl = "http://dilbert.com"+strTestResults;
	    strImageUrl = strTestResults;
	    intNextStepToCheck++;
	    break;
	default:
	    break;
	}
    }

    private LocalDate parseDateFormat(String strComicDateString) {
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
