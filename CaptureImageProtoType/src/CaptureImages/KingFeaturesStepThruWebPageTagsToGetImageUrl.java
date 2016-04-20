package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;

public class KingFeaturesStepThruWebPageTagsToGetImageUrl extends StepThruWebPageTagsToGetImageUrl {
    public HTML.Tag tagToFind = HTML.Tag.DIV;
    public HTML.Attribute attributeToFind;
    public HTML.Attribute attributeToGetValueFrom;
    public String strAttributeToFind = "";
//    public String strAttributeValueToFind = "";
    public String strValueToFind;
    public String strAttributeToGetValueFrom;
    public int intLengthOfValueToFind;
    public int intNextStepToCheck = 1;
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    private DateTimeFormatter dtSavedImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    private String strDateRegex = "[0-9]{8}";
    //
    //
    private String step1Results;
    private String step2Results;
    private String step3Results;

    // private String Step4Results;
    // private String Step5Results;

    public KingFeaturesStepThruWebPageTagsToGetImageUrl(LocalDate ldWorkingDate) {
	super(ldWorkingDate);
	this.ldWorkingDate = ldWorkingDate;
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

    public void checkReturnValues(String strTestResults, String DataToSave) {

	saveReturnValue(strTestResults);
    }

    @Override
    void setNextItemsToCheck() {
	switch (intNextStepToCheck) {
	case 1:
	    this.tagToFind = HTML.Tag.META;
	    this.strAttributeToFind = "property";
	    this.strValueToFind = "og:image";
	    this.strAttributeToGetValueFrom = "content";
	    // this.attributeToFind = HTML.Attribute.ID;
	    // this.strValueToFind = "strip_enlarged_" +
	    // ldWorkingDate.format(dtWorkingDateFormatter);

	    // this.attributeToFind = HTML.Attribute.CLASS;
	    // this.attributeToGetValueFrom = HTML.Attribute.CLASS;
	    // this.intLengthOfValueToFind = 0;

	    // this.strAttributeValueToFind = "og:image";

	    break;

	case 2:
	    this.tagToFind = HTML.Tag.META;
	    this.strAttributeToFind = "property";
	    this.strValueToFind = "og:title";
	    this.strAttributeToGetValueFrom = "content";
	    this.intLengthOfValueToFind = 0;
//	    this.attributeToGetValueFrom = HTML.Attribute.VALUE;
	    // this.attributeToFind = HTML.Attribute.NAME;
	    // this.strValueToFind = "printResUri";
	    break;

//	case 3:
//	    this.tagToFind = HTML.Tag.INPUT;
//	    this.attributeToFind = HTML.Attribute.NAME;
//	    this.strValueToFind = "featureDate";
//	    this.attributeToGetValueFrom = HTML.Attribute.VALUE;
//	    this.intLengthOfValueToFind = 0;
//	    break;

	default:
	    this.tagToFind = null;
	    this.attributeToFind = null;
	    this.strValueToFind = "done";
	    this.attributeToGetValueFrom = null;
	    this.intLengthOfValueToFind = 0;
	    break;
	}
	// TODO Auto-generated method stub

    }

    @Override
    void saveReturnValue(String strTestResults) {
	// TODO Auto-generated method stub
	switch (intNextStepToCheck) {
	case 1:
	    if (!strTestResults.equals("NotYet")) {
		step1Results = strTestResults;
		this.strImageUrl=strTestResults;
		intNextStepToCheck++;
	    }
	    break;
	case 2:
	    if (!strTestResults.equals("NotYet")) {
		this.ldImageDate = parseDateFormat(strTestResults);
		step2Results = this.ldImageDate.format(dtSavedImageDateFormatter);
		// strImageUrl =
		// "http://safr.kingfeatures.com/idn/etv/zone/hiresimg.php?file="+strTestResults;
		// strImageUrl = strTestResults;
		intNextStepToCheck++;
	    }
	    break;
//	case 3:
//	    if (strTestResults.matches(strDateRegex)) {
//		step3Results = strTestResults;
//		strImageDate = strTestResults;
//		ldImageDate = parseDateFormat(strTestResults);
//		// strImageUrl = "http://dilbert.com"+strTestResults;
//		// strImageUrl = strTestResults;
//		intNextStepToCheck++;
//	    }
//	    break;
	default:
	    break;
	}

    }

    @Override
    LocalDate parseDateFormat(String strTestResults) {
	LocalDate ldReturnValue;
//	int year = Integer.parseInt(strTestResults.substring(0, 4));
//	int month = Integer.parseInt(strTestResults.substring(4, 6));
//	int day = Integer.parseInt(strTestResults.substring(6));
//	ldReturnValue = LocalDate.of(year, month, day);
//	String strDateToTry = "april 28, 2015";
//	LocalDate ldDateToTry = LocalDate.parse(strDateToTry, dtImageDateFormatter);
//	String strFirstLetter = strTestResults.substring(0, 1);
//	strFirstLetter = strFirstLetter.toUpperCase();
//	String strDateToConvert = strFirstLetter + strTestResults.substring(1);
//	ldReturnValue = LocalDate.parse(strDateToConvert, dtImageDateFormatter);
	ldReturnValue = LocalDate.parse(strTestResults, dtImageDateFormatter);
	strImageDate = ldReturnValue.format(dtSavedImageDateFormatter);
	return ldReturnValue;
    }

    @Override
    String getStrObjectName() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    String getStrImageUrl() {
	return this.strImageUrl;
    }

    @Override
    LocalDate getLdImageDate() {
	return ldImageDate;
    }

    @Override
    String getStrImageDate() {
	return this.strImageDate;
    }

    String getStep1Results() {
	return step1Results;
    }

    @Override
    String getStep2Results() {
	// TODO Auto-generated method stub
	return step2Results;
    }

    @Override
    String getStep3Results() {
	// TODO Auto-generated method stub
	return step3Results;
    }

    @Override
    String getStep4Results() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    String getStep5Results() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    String getStep1RighDivTagValue() {
	// TODO Auto-generated method stub
	return null;
    }

}
/*
 * 
 * case "div": if (tag == HTML.Tag.DIV) { // if (this.blnLookForFirstDivTag) {
 * blnFoundRightDivTag = FindRightDivTag(tag, attrSet, pos); if
 * (blnFoundRightDivTag) { displayThisTag(tag, attrSet, pos); strTagToFind =
 * "input"; } // } } break; private boolean FindRightDivTag(Tag tag,
 * MutableAttributeSet attrSet, int pos) { boolean blnReturnValue = false; try {
 * String strDivClass = ""; strDivClass = (String)
 * attrSet.getAttribute(HTML.Attribute.CLASS);
 * 
 * String strToCompare = ""; if (!(strDivClass == null || strDivClass == "")) {
 * if (strToCompare.equals(strDivClass)) { blnReturnValue = true; } } } catch
 * (Exception e) { intDebugCode = 1; this.strDebugMsg =
 * "*!*!*!*! htmlParser Callback.FindRightDivTag *!*!*!*!*!*!*"; strDebugMsg +=
 * "\nException caught: tag - " + tag.toString() + "\n" + e.toString();
 * strDebugMsg += "\n" + e.getStackTrace()[0];
 * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); } return blnReturnValue; }
 */
