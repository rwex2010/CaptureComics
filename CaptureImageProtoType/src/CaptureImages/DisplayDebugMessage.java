/**
 * 
 */
package CaptureImages;

/**
 * @author wecksr
 * 
 */
public class DisplayDebugMessage {
    private int intDebugLevel = 0;
    private String strMessageToDisplay = "";
    private int intSuccess = 0;
    private int intFailure = 0;

    public DisplayDebugMessage(int intDebugLevel) {
	this.intDebugLevel = intDebugLevel;
	// TestBitWiseComparison();

    }

    public void setDebugLevel(int debugLevel) {
	this.intDebugLevel = debugLevel;
    }

    public void ShowMessage(String strMessage, int intDebugCode) {
	// System.out.println("DebugLevel:" + intDebugLevel + " DebugCode:" +
	// intDebugCode);
	if ((intDebugLevel & intDebugCode) > 0) {
	    this.strMessageToDisplay += strMessage;
//	    System.out.println();
//	    System.out.println(strMessage);
	}
    }

    public void ShowMessageArray(String[] strMessage, int intDebugCode) {
	// System.out.println("DebugLevel:" + intDebugLevel + " DebugCode:" +
	// intDebugCode);
	if ((intDebugLevel & intDebugCode) > 0) {
//	    System.out.println();
	    for (int ix = 0; ix < strMessage.length; ix++) {
		this.strMessageToDisplay += strMessage[ix];
//		System.out.println(strMessage[ix]);
	    }
	}
    }

    public Boolean thisDoesPrint(int intDebugCode) {
	Boolean blnReturnValue = false;
	if ((intDebugLevel & intDebugCode) > 0) {
	    blnReturnValue = true;
	}
	return blnReturnValue;
    }

    public void TestBitWiseComparison() {
	// int ixLevel = intDebugLevel;
	for (int ixLevel = 0; ixLevel <= intDebugLevel; ixLevel++) {
	    int[] intNbrs = { 1, 2, 4, 8, 16, 32, 64, 128, 256 };
	    for (int ixCode : intNbrs) {
		String strMsg = "DebugLevel: " + ixLevel + " DebugCode: " + ixCode + " = ";
		if ((ixLevel & ixCode) > 0) {
		    strMsg += "true";
		} else {
		    strMsg += "false";
		}
		System.out.println(strMsg);
	    }

	}
    }
    
    public void incrementSuccess(Boolean blnSuccessful) {
	if (blnSuccessful) {
	    this.intSuccess++;
	}
    }

    public int getFailure() {
        return intFailure;
    }

    public void incrementFailure(Boolean blnFailure) {
        if (blnFailure) {
	    this.intFailure++;
	}
    }

    public int getIntSuccess() {
        return intSuccess;
    }

    public String getAllMessageLines() {
	return this.strMessageToDisplay;
    }

    public void setIntDebugLevel(int intDebugLevel) {
        this.intDebugLevel = intDebugLevel;
    }
}
