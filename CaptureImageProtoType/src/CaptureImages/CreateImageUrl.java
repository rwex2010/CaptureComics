package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class CreateImageUrl {

    protected ComicStrip csData;
    protected LocalDate ldWorkinDate;

    protected String strMediaZenfsServer = "http://media.zenfs.com/en_us/News/ucomics.com/";
    protected String strImageUrl;
    protected String strImageId;
    protected String strImageExtension = "gif";

    protected DisplayDebugMessage dbgDisplay;
    protected String strDebugMsg = "";
    protected String[] strDebugMsgArray;
    protected int intDebugCode = 512;

   CreateImageUrl(DisplayDebugMessage dbgDisplay) {
	// TODO Auto-generated constructor stub
    }

    abstract void BuildImageUrl(LocalDate ldWorkingDate, ComicStrip csData);
    
    abstract void setCsData(ComicStrip csData);

    abstract String getStrImageExtension();

    abstract void setStrImageExtension(String strImageExtension);

    abstract String getStrImageUrl();

    abstract String getStrImageId();

    abstract void setDbgDisplay(DisplayDebugMessage dbgDisplay);

}
