package CaptureImages;

//import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;

public class CaptureImage {

    private int avatarnum = 1; //counts number of times it tried to read image - keep from infinite loop
    private BufferedImage image = null;
    private String strImageEextension = "";
    private String strImageId = "";
    private String strImageUrl = "";
    private int intImageWidth;
    private String strFolderToWrite = "";
    private int intImageIndex = 0;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String NL = "\n";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;

    public CaptureImage() {

    }

    public CaptureImage(int DebugLevel) {
    }

    public CaptureImage(DisplayDebugMessage dgbDebug) {
	this.dbgDisplay = dgbDebug;
    }

    public boolean saveImageToFile(String imageUrl, String folderToSaveImage, String imageId, String imageExtension) {
	int intDebugCode = 8;
	strDebugMsg = NL + "CaptureImages->saveImageToFile";
	strDebugMsg += NL + "imageUrl: " + imageUrl;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	boolean blnReturnValue = false;

	this.strImageUrl = imageUrl;
	this.strFolderToWrite = folderToSaveImage;
	this.strImageId = imageId;
	this.strImageEextension = imageExtension;

	    String strImageFullUrl = strImageUrl;
	    
	    URL url = null;
	    try {
		url = new URL(strImageFullUrl);
		image = ImageIO.read(url);
		blnReturnValue = true;
	    } catch (Exception e1) {
		intDebugCode = 1;
		strDebugMsg = NL + "dbg=" + intDebugCode + ") CaptureImage->saveImageToFile() Reading url captured error";
		strDebugMsg += NL + "url: " + url.toExternalForm();
		strDebugMsg += NL + "error message: " + e1.getMessage();
		dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    }

	    intDebugCode = 8;
	   
	    if (blnReturnValue) {
		try {
		    String strImageFileId = strImageId;
		    String strFullPathToWriteImage = strFolderToWrite + strImageFileId + "." + strImageEextension;
		    intDebugCode = 8;
		    strDebugMsg = NL + "dbg=" + intDebugCode + ") CaptureImage->saveImageToFile()";
		    strDebugMsg += NL + "Full path to write Image: " + strFullPathToWriteImage;
		    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

		    ImageIO.write(image, strImageEextension, new File(strFullPathToWriteImage));
		    intDebugCode = 1;
		    strDebugMsg = NL + "dbg=" + intDebugCode + ")Write successful: " + intImageIndex + ")" + strFullPathToWriteImage;
		    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
		    dbgDisplay.incrementSuccess(true);
		    avatarnum++;
		} catch (Exception e) {
		    blnReturnValue = false;
		    dbgDisplay.incrementFailure(true);
		    intDebugCode = 1;
		    strDebugMsg = NL + "CaptureImage.saveImageToFile() *!*!*! Caught Exception *!*!*!\n";
		    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
		    if (dbgDisplay.thisDoesPrint(intDebugCode)) {
			e.printStackTrace();
		    }
		}
	    }
	    avatarnum++;

	intDebugCode = 8;
	strDebugMsg = NL + "Method CaptureImage.SaveImageToFile - is complete";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	return blnReturnValue;
    }

    public void setImageUrl(String ImgUrl) {
	strImageUrl = ImgUrl;
    }

    public void setImageExtension(String ImgExt) {
	strImageEextension = ImgExt;
    }

    public void setImageId(String ImgId) {
	strImageId = ImgId;
    }

    public void setImageWidth(int ImgWdth) {
	intImageWidth = ImgWdth;
    }

    public void setIntImageIndex(int intImageIndex) {
        this.intImageIndex = intImageIndex;
    }
}
