package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTML;

public abstract class StepThruWebPageTagsToGetImageUrl {
    public HTML.Tag tagToFind = HTML.Tag.DIV;
    public HTML.Attribute attributeToFind;
    public String strValueToFind;
    public int intLengthOfValueToFind;
    public int intNextStepToCheck = 1;
    public String strObjectName = "Abstract Class";
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

    StepThruWebPageTagsToGetImageUrl(LocalDate ldWorkingDate) {
    }

    abstract void processNextStep(int intNextStep);

    abstract void processNextStep();

    abstract void checkReturnValues(String strTestResults);

    abstract void setNextItemsToCheck();

    abstract void saveReturnValue(String strTestResults);

    abstract LocalDate parseDateFormat(String strComicDateString);

    abstract String getStrObjectName();

    abstract String getStrImageUrl();

    abstract LocalDate getLdImageDate();

    abstract String getStrImageDate();

    abstract String getStep1RighDivTagValue();

    abstract String getStep2Results();

    abstract String getStep3Results();

    abstract String getStep4Results();

    abstract String getStep5Results();

}
