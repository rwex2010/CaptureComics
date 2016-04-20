package CaptureImages;

//import java.awt.EventQueue;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBElement;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
import javafx.scene.shape.Path;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;

import javax.swing.AbstractButton;
//import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;

import java.awt.Panel;

import javax.swing.JTextField;

import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.List;

//import javax.swing.JComboBox;

//import com.sun.org.apache.bcel.internal.generic.LSTORE;

//import java.awt.Scrollbar;

//import javax.swing.JSpinner;
//import javax.swing.JList;

import java.awt.Choice;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.awt.TextField;

import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
//import javax.swing.LayoutStyle.ComponentPlacement;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.ChangeEvent;

//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.border.SoftBevelBorder;

import java.awt.Component;
//import java.awt.Point;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.io.File;
//import java.nio.file.FileSystem;
//import java.nio.file.FileSystems;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.TextField;
import java.awt.Checkbox;

public class UIFormCaptureImages {

    public JFrame frame;
    private TextField tbxWebPageUrl;
    private TextField tbxImageUrl;
    private TextField tbxImageId;
    private TextField tbxImageDate;
    private JTextField tbxDebugLevel;
    private JTextField tbxPathToXML;
    private JTextField tbxStartingDate;
    private JTextField tbxEndDate;
    private JTextArea tbxMessageBoard;

    private Button btnLoadWindow;
    private Button btnCaptureImages;

    private JPanel pnlDomain;
    private JPanel pnlPathToSave;
    private JPanel pnlPathToXmlFile;

    private LocalDate ldStartingDate;
    private LocalDate ldEndingDate;
    private LocalDate[] aryDates = new LocalDate[2];

    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private JComboBox cboxPathToSave;
    private Choice chcComicChooser;
    private Checkbox chckbxJustThis;
    private String[] strDomainArray;
    private String[][] aryComics = new String[4][3];
    private String[] aryPathToSave = new String[3];
    private String strListToUse = "MyComics";

    private java.util.List<ComicStrip> ComicList = new ArrayList<>();
    private boolean blnEndOfListReached = false;
    private Boolean blnEndOfChooserReached = false;
    private DriverImageCapture imDriver;
    private ButtonGroup btngpWhichComics = new ButtonGroup();
    private ButtonGroup btngpDateRange = new ButtonGroup();
    private AbstractButton radAllComics;
    private AbstractButton radMyComics;
    private AbstractButton radNotMyComics;
    private AbstractButton radOtherListToUse;
    private AbstractButton radJustToday;
    private AbstractButton radDateRange;
    private JTextField tbxTotalCount;
    private TextField tbxCurrentIndex;

    // 'other' panel components
    private JPanel pnlOther;
    private JRadioButton radOtherComics;
    private JRadioButton radOtherNew;
    private JTextField tbxOtherText;

    private int intComicChooserSelectedIndex = 0;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private int intDebugCode = 512;
    private String NL = "\n";
    private TextField tbxSuccessful;
    private TextField tbxFailure;

    // private JFileChooser fileChooser;

    /**
     * Create the application.
     * 
     * @wbp.parser.constructor
     */
    public UIFormCaptureImages() {
	initialize();
	setAdditionalInitialization();
    }

    public UIFormCaptureImages(DriverImageCapture imDriver, DisplayDebugMessage DebugObj) {
	this.dbgDisplay = DebugObj;
	this.imDriver = imDriver;
	initialize();
	setAdditionalInitialization();
    }

    public void setDriverObject(DriverImageCapture myDriverObject) {
	this.imDriver = myDriverObject;
    }

    public void setDebugDisplay(DisplayDebugMessage DebugDisplay) {
	this.dbgDisplay = DebugDisplay;
    }

    public void setTextWebPageUrl(String strWebPageUrl) {
	this.tbxWebPageUrl.setText(strWebPageUrl);
    }

    public void setTextImageUrl(String strImageUrl) {
	this.tbxImageUrl.setText(strImageUrl);
    }

    public void setTextImageId(String strImageId) {
	this.tbxImageId.setText(strImageId);
    }

    public void setTextImageDate(String strImageDate) {
	this.tbxImageDate.setText(strImageDate);
    }

    public void incrementSuccessfulImagageCapture() {
	String strSuccessfulCapture = tbxSuccessful.getText();
	Integer intSuccessful = Integer.parseInt(strSuccessfulCapture) + 1;
	strSuccessfulCapture = intSuccessful.toString();
	tbxSuccessful.setText(strSuccessfulCapture);
    }

    public void incrementFailedImagageCapture() {
	String strFailedCapture = tbxFailure.getText();
	Integer intFailed = Integer.parseInt(strFailedCapture) + 1;
	strFailedCapture = intFailed.toString();
	tbxFailure.setText(strFailedCapture);
    }

    private void setAdditionalInitialization() {
	this.ldStartingDate = LocalDate.now();
	this.ldEndingDate = LocalDate.now();

	this.aryDates[0] = this.ldStartingDate;
	this.aryDates[1] = this.ldEndingDate;
	imDriver.setArrayDates(aryDates);

	String strStartDate = ldStartingDate.format(this.dtFormatter);
	String strEndDate = ldEndingDate.format(this.dtFormatter);

	this.tbxEndDate.setText(strEndDate);
	this.tbxStartingDate.setText(strStartDate);

	this.aryPathToSave[0] = "C:/Users/wecksr/Documents/ComicStripImages/";
	this.aryPathToSave[1] = "P:/Hold/";
	this.aryPathToSave[2] = "C:/Users/Rich/Documents/ComicStripImages/";
	this.cboxPathToSave.addItem(aryPathToSave[0]);
	this.cboxPathToSave.addItem(aryPathToSave[1]);
	this.cboxPathToSave.addItem(aryPathToSave[2]);
	this.cboxPathToSave.addItem("");

    }

    public LocalDate[] getArrayDates() {
	setArrayDates();
	return this.aryDates;
    }

    public int getCurrentIndex() {
	return this.chcComicChooser.getSelectedIndex();
    }

    private void captureSelectedImages() {
	intDebugCode = 4;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> CaptureSelected Images()";
	strDebugMsg += NL + "Start Date: " + this.aryDates[0].format(this.dtFormatter);
	strDebugMsg += NL + "End Date: " + this.aryDates[1].format(this.dtFormatter);
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	blnEndOfChooserReached = false;
	this.imDriver.setPathToSaveImage((String) this.cboxPathToSave.getSelectedItem());
	// this.imDriver.reInitializeSpecificVariables();
	// this.imDriver.CaptureSelectedImages();
	this.imDriver.CaptureSelectedImages(this.chcComicChooser.getItemCount(), this.chcComicChooser.getSelectedIndex(), this.chckbxJustThis.getState());

    }

    private Boolean validateFiles() {
	Boolean blnReturnValue = true;

	String fileToXml = this.tbxPathToXML.getText();
	File fiToCheck = new File(fileToXml);
	java.nio.file.Path pathXmlFile = fiToCheck.toPath();

	pathXmlFile = pathXmlFile.toAbsolutePath();
	fiToCheck = pathXmlFile.toFile();
	Boolean blnXmlExists = fiToCheck.exists();
	if (blnXmlExists) {
	    pnlPathToXmlFile.setBackground(null);
	    intDebugCode = 1;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> Validate XML directory";
	    strDebugMsg += NL + "Directory " + fileToXml + " Exists";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	} else {
	    blnReturnValue = false;
	    pnlPathToXmlFile.setBackground(new Color(255, 0, 0));
	    intDebugCode = 1;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> Validate XML directory";
	    strDebugMsg += NL + "Directory " + fileToXml + " Does Not exist";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}
//	String directoryToTest = "P:/";
//	File fiTestDir = new File(directoryToTest);
//	Boolean blnIsDirectory = fiTestDir.isDirectory();
//	Boolean blnIsFile = fiTestDir.isFile();
//	File[] aryFileLIst = fiTestDir.listFiles();
	// *end of test*
	String fileToSave = (String) this.cboxPathToSave.getSelectedItem();
	File fiSaveCheck = new File(fileToSave);
	Boolean blnSaveExists = fiSaveCheck.isDirectory();
	if (blnSaveExists) {
	    pnlPathToSave.setBackground(null);
	    intDebugCode = 1;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> Validate Save directory";
	    strDebugMsg += NL + "Directory " + fileToSave + " Exists";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	} else {
	    blnReturnValue = false;
	    pnlPathToSave.setBackground(new Color(255, 0, 0));
	    intDebugCode = 1;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> Validate Save directory";
	    strDebugMsg += NL + "Directory " + fileToSave + " Does Not exist";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}

	return blnReturnValue;
    }

    private void loadThisWindow() {
	int intDebugLevel = new Integer(tbxDebugLevel.getText());
	dbgDisplay.setDebugLevel(intDebugLevel);
	intDebugCode = 2;
	setListToUse();
	strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> loadthisWindow() - starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	Boolean blnPathOkay = validateFiles();
	if (blnPathOkay) {
	    this.chcComicChooser.removeAll();
	    buildDomainArray();
	    // this.imDriver.setDebugLevel(intDebugLevel);
	    this.imDriver.setPathToXML(tbxPathToXML.getText());
	    this.imDriver.loadWindow();
	    this.setComicList();
	    // setupComicArray();
	    this.btnCaptureImages.setEnabled(true);
	}

    }

    private void clearVariables() {
	this.chcComicChooser.removeAll();

    }

    private void buildDomainArray() {
	Component[] aryComponentInDomainPanel;
	String strChkBxText;
	this.strDomainArray = null;

	try {
	    strDebugMsg = "";
	    String strMsgOne = "List of check boxes:" + NL;
	    String strDomainsToUse = "";
	    aryComponentInDomainPanel = this.pnlDomain.getComponents();
	    for (Component compCheckBoxes : aryComponentInDomainPanel) {
		strMsgOne += compCheckBoxes.getClass() + NL;
		if (compCheckBoxes.getClass() == javax.swing.JCheckBox.class) {
		    JCheckBox chkbxComponent = (JCheckBox) compCheckBoxes;
		    if (chkbxComponent.isSelected()) {
			strChkBxText = chkbxComponent.getText();
			switch (strChkBxText) {
			case "gocomics":
			    strDebugMsg += "gocomics check box checked" + NL;
			    strDomainsToUse += "imgsrv.gocomics.com;";
			    break;
			case "Arcamax":
			    strDebugMsg += "Arcamax check box checked" + NL;
			    strDomainsToUse += "arcamax;";
			    break;
			case "Kingfeatures":
			    strDebugMsg += "Kingfeatures check box checked" + NL;
			    strDomainsToUse += "kingfeatures;";
			    break;
			case "Dilbert":
			    strDebugMsg += "Dilbert check box checked" + NL;
			    strDomainsToUse += "Dilbert.com;";
			    break;
			case "Media Zenfs":
			    strDebugMsg += "Media Zenfs check box checked" + NL;
			    strDomainsToUse += "media.zenfs.com;";
			    break;
			// case "Safe Havens":
			// strDebugMsg += "Safe Havens check box checked" + NL;
			// strDomainsToUse += "safehavenscomic.com;";
			// break;
			case "U Comics":
			    strDebugMsg += "U Comics check box checked" + NL;
			    strDomainsToUse += "images.ucomics.com;";
			    break;
			case "Kevin and Kell":
			    strDebugMsg += "Kevin and Kell check box checked" + NL;
			    strDomainsToUse += "www.kevinandkell.com;";
			    break;
			// case "On the Fastrack":
			// strDebugMsg += "On the Fastrack check box checked" +
			// NL;
			// strDomainsToUse += "www.onthefastrack.com;";
			// break;
			// case "Comics Kingdom":
			// strDebugMsg += "Comics Kingdom Check box checked" +
			// NL;
			// strDomainsToUse += "content.comicskingdom.net;";
			// break;
			case "All":
			    strDebugMsg += "All check box checked" + NL;
			    break;

			default:
			    break;
			}
		    }
		}
	    }
	    intDebugCode = 4;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages ->  buildDomainArray():" + NL + strMsgOne + strDebugMsg;
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    strDomainArray = strDomainsToUse.split(";");

	} catch (Exception e) {
	    // TODO: handle exception
	    System.out.println("Exception: " + e.toString());
	}
    }

    private void setArrayDates() {
	if (this.radJustToday.isSelected()) {
	    tbxEndDate.setEnabled(false);
	    tbxStartingDate.setEnabled(false);
	    this.aryDates[0] = LocalDate.now();
	    this.aryDates[1] = LocalDate.now();
	} else {
	    tbxEndDate.setEnabled(true);
	    tbxStartingDate.setEnabled(true);
	    try {
		this.aryDates[0] = LocalDate.parse(this.tbxStartingDate.getText(), this.dtFormatter);
		this.aryDates[1] = LocalDate.parse(this.tbxEndDate.getText(), this.dtFormatter);
	    } catch (DateTimeParseException dte) {
		int intDebugLevel = new Integer(tbxDebugLevel.getText());
		if (dbgDisplay.thisDoesPrint(intDebugLevel)) {
		    System.out.println("actionOnDateRangeTextFields error: " + dte.getMessage());
		    dte.printStackTrace();
		}

	    } catch (Exception e) {
		int intDebugLevel = new Integer(tbxDebugLevel.getText());
		if (dbgDisplay.thisDoesPrint(intDebugLevel)) {
		    System.out.println("actionOnDateRangeTextFields error: " + e.getMessage());
		    e.printStackTrace();
		}
	    }
	}
    }

    /*
     * ********************** Event Handler Subroutines ***********************
     */

    private void actionOnDateRangeButton(AbstractButton radButton) {
	if (radButton.isSelected()) {
	    switch (radButton.getName()) {
	    case "JustToday":
		setArrayDates();
		tbxEndDate.setEnabled(false);
		tbxStartingDate.setEnabled(false);
		break;
	    case "DateRange":
		setArrayDates();
		tbxEndDate.setEnabled(true);
		tbxStartingDate.setEnabled(true);
		break;

	    default:
		break;
	    }
	}

    }

    private void actionOnListToUseRadioButtons(AbstractButton radButton) {

	if (radButton.isSelected()) {
	    switch (radButton.getName()) {
	    case "radAllComics":
		this.strListToUse = "AllComics";
		pnlOther.setVisible(false);
		break;
	    case "radMyComics":
		this.strListToUse = "MyComics";
		pnlOther.setVisible(false);
		break;
	    case "radNotMyComics":
		this.strListToUse = "NotMyComics";
		pnlOther.setVisible(false);
		break;
	    case "radOtherListToUse":
		this.strListToUse = "OtherList";
		pnlOther.setVisible(true);
		break;
	    default:
		break;
	    }
	}

    }

    private void mouseButtonClicked(MouseEvent mseEvent) {
	JCheckBox chbxMouseClicked = (JCheckBox) mseEvent.getComponent();
	Component[] aryComponentInDomainPanel;
	this.strDomainArray = null;

	aryComponentInDomainPanel = this.pnlDomain.getComponents();
	for (Component compCheckBoxes : aryComponentInDomainPanel) {
	    if (compCheckBoxes.getClass() == javax.swing.JCheckBox.class) {
		JCheckBox chkbxComponent = (JCheckBox) compCheckBoxes;
		if (chkbxComponent.isEnabled()) {
		    chkbxComponent.setSelected(chbxMouseClicked.isSelected());
		}
	    }

	}

    }

    private void stateChangedOnDomainCheckBoxes(ItemEvent chkbxE) {
	JCheckBox chbxStateChanged = (JCheckBox) chkbxE.getItem();
	strDebugMsg = "";
	intDebugCode = 4;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") UIFormCaptureImages -> stateChangedOnDomainCheckBoxes():";
	strDebugMsg += NL + "Check Box item state Changed";
	strDebugMsg += NL + "Check Box Text: " + chbxStateChanged.getText();
	strDebugMsg += NL + "ItemSelectable: " + chkbxE.getItemSelectable().toString();
	String strChangedState = "";

	switch (chkbxE.getStateChange()) {
	case ItemEvent.SELECTED:
	    strChangedState = "Selected";
	    break;
	case ItemEvent.DESELECTED:
	    strChangedState = "DeSelected";
	    break;

	default:
	    strChangedState = "UnknownChange";
	    break;
	}

	strDebugMsg += NL + "get State change(): " + strChangedState;
	strDebugMsg += NL + "************************************************";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	try {
	    this.btnCaptureImages.setEnabled(false);
	} catch (Exception e) {
	    String emsg = e.getMessage();
	}
    }

    private void actionOnDateRangeTextFields(JTextField tbxTextBox) {
	setArrayDates();
    }

    private void selectedChooserItemChanged() {
	intComicChooserSelectedIndex = this.chcComicChooser.getSelectedIndex();
	this.tbxCurrentIndex.setText(Integer.toString(intComicChooserSelectedIndex));
    }

    /*
     * ********************************** Getters *****************************
     */

    public Boolean getJustThisComicFlag() {
	Boolean blnReturnValue;
	if (this.chckbxJustThis.getState()) {
	    blnReturnValue = true;
	} else {
	    blnReturnValue = false;
	}
	return blnReturnValue;
    }

    public String[] getStrDomainArray() {
	return this.strDomainArray;
    }

    public ComicStrip getNextComicFmChooser() {
	ComicStrip ReturnValue;
	if (blnEndOfChooserReached) {
	    ReturnValue = null;

	} else {
	    selectedChooserItemChanged();
	    ReturnValue = ComicList.get(intComicChooserSelectedIndex);
	    intComicChooserSelectedIndex++;
	    if (intComicChooserSelectedIndex >= chcComicChooser.getItemCount()) {
		intComicChooserSelectedIndex = chcComicChooser.getItemCount() - 1;
		blnEndOfChooserReached = true;
		btnCaptureImages.setEnabled(false);
	    } else {
		chcComicChooser.select(intComicChooserSelectedIndex);
	    }
	}
	return ReturnValue;
    }

    public String getPathToSaveImage() {
	return (String) this.cboxPathToSave.getSelectedItem();
    }

    public String getListToUse() {
	return this.strListToUse;
    }

    /*
     * ********************************** Setters *****************************
     */

    public void setMessageBoard(String strMessageText) {
	this.tbxMessageBoard.setText(strMessageText);
    }

    public void setSuccessful(int intSuccess) {
	this.tbxSuccessful.setText(Integer.toString(intSuccess));
    }

    public void setFailure(int intFailure) {
	this.tbxFailure.setText(Integer.toString(intFailure));
    }

    public void setComicList() {
	this.ComicList = null;
	this.ComicList = imDriver.getComicList();
	fillTheComicListBox();
	Integer intComicCount = ComicList.size();
	this.tbxTotalCount.setText(intComicCount.toString());
    }

    private void fillTheComicListBox() {
	Integer cntr = 0;
	for (ComicStrip cs : ComicList) {
	    // type type = (type) iterator.next();
	    ++cntr;

	    chcComicChooser.add(cs.ComicName);

	    if (cntr > 500) {
		break;
	    }
	}
    }

    /*
     * switch (radButton.getName()) { case "radAllComics": this.strListToUse =
     * "AllComics"; break; case "radMyComics": this.strListToUse = "MyComics";
     * break; case "radNotMyComics": this.strListToUse = "NotMyComics"; break;
     * case "radOtherListToUse": this.strListToUse = "OtherList"; break;
     * default: break; } }
     */

    private void setListToUse() {
	if (this.radAllComics.isSelected()) {
	    strListToUse = "AllComics";
	} else if (radMyComics.isSelected()) {
	    strListToUse = "MyComics";
	} else if (radNotMyComics.isSelected()) {
	    strListToUse = "NotMyComics";
	} else if (radOtherListToUse.isSelected()) {
	    strListToUse = "OtherList";
	} else {
	    strListToUse = "MyComics";
	}
    }

    /*
     * ************************* Initialize the Components *****************
     */
    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 918, 783);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);

	Panel panel = new Panel();
	panel.setBounds(10, 10, 388, 50);
	frame.getContentPane().add(panel);
	panel.setLayout(null);

	Label label = new Label("Web Page URL:");
	label.setBounds(10, 0, 104, 22);
	panel.add(label);

	tbxWebPageUrl = new TextField();
	tbxWebPageUrl.setBounds(0, 28, 378, 22);
	panel.add(tbxWebPageUrl);

	Panel panel_1 = new Panel();
	panel_1.setLayout(null);
	panel_1.setBounds(423, 10, 332, 50);
	frame.getContentPane().add(panel_1);

	Label label_1 = new Label("Image URL:");
	label_1.setBounds(10, 0, 104, 22);
	panel_1.add(label_1);

	tbxImageUrl = new TextField();
	tbxImageUrl.setBounds(0, 28, 332, 22);
	panel_1.add(tbxImageUrl);

	pnlPathToSave = new JPanel();
	pnlPathToSave.setLayout(null);
	pnlPathToSave.setBounds(10, 122, 332, 50);
	frame.getContentPane().add(pnlPathToSave);

	Label label_2 = new Label("Path where to save image");
	label_2.setBounds(10, 0, 151, 22);
	pnlPathToSave.add(label_2);

	cboxPathToSave = new JComboBox();
	cboxPathToSave.setBounds(0, 28, 317, 20);
	pnlPathToSave.add(cboxPathToSave);
	cboxPathToSave.setEditable(true);

	Panel panel_3 = new Panel();
	panel_3.setLayout(null);
	panel_3.setBounds(348, 66, 126, 50);
	frame.getContentPane().add(panel_3);

	Label label_3 = new Label("Image Date");
	label_3.setBounds(10, 0, 104, 22);
	panel_3.add(label_3);

	tbxImageDate = new TextField();
	tbxImageDate.setBounds(0, 18, 114, 22);
	panel_3.add(tbxImageDate);

	Panel panel_4 = new Panel();
	panel_4.setLayout(null);
	panel_4.setBounds(479, 66, 276, 50);
	frame.getContentPane().add(panel_4);

	Label label_4 = new Label("Image ID");
	label_4.setBounds(10, 0, 104, 22);
	panel_4.add(label_4);

	tbxImageId = new TextField();
	tbxImageId.setBounds(0, 18, 266, 22);
	panel_4.add(tbxImageId);

	pnlDomain = new JPanel();
	pnlDomain.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	pnlDomain.setBounds(761, 11, 131, 237);
	frame.getContentPane().add(pnlDomain);
	pnlDomain.setLayout(null);

	JLabel lblPublisher = new JLabel("Domain");
	lblPublisher.setHorizontalAlignment(SwingConstants.CENTER);
	lblPublisher.setBounds(0, 0, 120, 25);
	pnlDomain.add(lblPublisher);

	JCheckBox chkbxGoComics = new JCheckBox("gocomics");
	chkbxGoComics.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});

	chkbxGoComics.setSelected(true);
	chkbxGoComics.setBounds(6, 47, 114, 23);
	pnlDomain.add(chkbxGoComics);

	JCheckBox chkbxArcamax = new JCheckBox("Arcamax");
	chkbxArcamax.setEnabled(true);
	chkbxArcamax.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});
	chkbxArcamax.setSelected(true);
	chkbxArcamax.setBounds(6, 73, 114, 23);
	pnlDomain.add(chkbxArcamax);

	JCheckBox chkbxKingfeatures = new JCheckBox("Kingfeatures");
	chkbxKingfeatures.setSelected(true);
	chkbxKingfeatures.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});
	chkbxKingfeatures.setBounds(6, 103, 114, 23);
	pnlDomain.add(chkbxKingfeatures);

	JCheckBox chkbxDilbert = new JCheckBox("Dilbert");
	chkbxDilbert.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});
	chkbxDilbert.setSelected(true);
	chkbxDilbert.setBounds(6, 129, 114, 23);
	pnlDomain.add(chkbxDilbert);

	JCheckBox chkbxMediaZenfs = new JCheckBox("Media Zenfs");
	chkbxMediaZenfs.setEnabled(true);
	chkbxMediaZenfs.setSelected(true);
	chkbxMediaZenfs.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});
	chkbxMediaZenfs.setBounds(6, 207, 114, 23);
	pnlDomain.add(chkbxMediaZenfs);

	JCheckBox chkbxUComics = new JCheckBox("U Comics");
	chkbxUComics.setEnabled(true);
	chkbxUComics.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});
	chkbxUComics.setSelected(true);
	chkbxUComics.setBounds(6, 155, 114, 23);
	pnlDomain.add(chkbxUComics);

	JCheckBox chkbxKevinAndKell = new JCheckBox("Kevin and Kell");
	chkbxKevinAndKell.setEnabled(true);
	chkbxKevinAndKell.setSelected(true);
	chkbxKevinAndKell.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		stateChangedOnDomainCheckBoxes(e);
	    }
	});
	chkbxKevinAndKell.setBounds(6, 181, 114, 23);
	pnlDomain.add(chkbxKevinAndKell);

	JCheckBox chkbxAll = new JCheckBox("All");
	chkbxAll.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		mouseButtonClicked(e);
	    }
	});
	chkbxAll.setSelected(true);
	// chkbxAll.addItemListener(new ItemListener() {
	// public void itemStateChanged(ItemEvent e) {
	// stateChangedOnDomainCheckBoxes(e);
	// }
	// });
	chkbxAll.setBounds(0, 21, 114, 23);
	pnlDomain.add(chkbxAll);

	Panel panel_6 = new Panel();
	panel_6.setLayout(null);
	panel_6.setBounds(348, 128, 99, 44);
	frame.getContentPane().add(panel_6);

	tbxDebugLevel = new JTextField();
	tbxDebugLevel
		.setToolTipText("<html>1 = error messages<br>2 = tracking (tells where it is)<br>4 = Data collected<br>8 = CaptureImage class - tracking<br>16 = Parsing Class - tracking the methods<br>32 = Parsing Class - shows the attributes if the tags<br>64 = Parsing class - tracking the tags<br>128 = write out the html page in text<br>256 =write out results from Capture Images<br>512 =<br>1024 =<br>2048 =<br>4096 =<br>8192 =<br></html>");
	tbxDebugLevel.setHorizontalAlignment(SwingConstants.CENTER);
	tbxDebugLevel.setText("1");
	tbxDebugLevel.setColumns(4);
	tbxDebugLevel.setBounds(10, 19, 52, 20);
	panel_6.add(tbxDebugLevel);

	Label label_5 = new Label("Debug Level");
	label_5.setBounds(10, 0, 63, 22);
	panel_6.add(label_5);

	btnCaptureImages = new Button("Capture Images");
	btnCaptureImages.setEnabled(false);
	btnCaptureImages.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		captureSelectedImages();
	    }
	});
	btnCaptureImages.setBounds(348, 206, 99, 22);
	frame.getContentPane().add(btnCaptureImages);

	JPanel panel_8 = new JPanel();
	panel_8.setBounds(10, 178, 332, 58);
	frame.getContentPane().add(panel_8);
	panel_8.setLayout(null);

	chcComicChooser = new Choice();
	chcComicChooser.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		System.out.println("Chooser ItemListener event: " + e.paramString());
		selectedChooserItemChanged();
	    }
	});
	chcComicChooser.setBounds(0, 35, 267, 20);
	panel_8.add(chcComicChooser);

	JLabel lblComicImage = new JLabel("Comic / Image List");
	lblComicImage.setBounds(0, 11, 119, 18);
	panel_8.add(lblComicImage);

	chckbxJustThis = new Checkbox("Just this comic");
	chckbxJustThis.setBounds(148, 6, 119, 23);
	panel_8.add(chckbxJustThis);

	btnLoadWindow = new Button("Load Window");
	btnLoadWindow.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		loadThisWindow();
	    }
	});
	btnLoadWindow.setBounds(348, 178, 99, 22);
	frame.getContentPane().add(btnLoadWindow);

	pnlPathToXmlFile = new JPanel();
	pnlPathToXmlFile.setLayout(null);
	pnlPathToXmlFile.setBounds(10, 66, 332, 50);
	frame.getContentPane().add(pnlPathToXmlFile);

	tbxPathToXML = new JTextField();
	tbxPathToXML.setText("../JavaComicList.xml");
	tbxPathToXML.setColumns(10);
	tbxPathToXML.setBounds(20, 23, 302, 20);
	pnlPathToXmlFile.add(tbxPathToXML);

	Label label_6 = new Label("XML dataset of Images (full Path)");
	label_6.setBounds(10, 0, 173, 22);
	pnlPathToXmlFile.add(label_6);

	JPanel panel_10 = new JPanel();
	panel_10.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
	panel_10.setBounds(446, 122, 118, 191);
	frame.getContentPane().add(panel_10);
	GridBagLayout gbl_panel_10 = new GridBagLayout();
	gbl_panel_10.columnWidths = new int[] { 116 };
	gbl_panel_10.rowHeights = new int[] { 35, 23, 23, 42, 42 };
	gbl_panel_10.columnWeights = new double[] { 0.0 };
	gbl_panel_10.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0 };
	panel_10.setLayout(gbl_panel_10);

	JLabel lblDateRange = new JLabel("Date Range");
	lblDateRange.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblDateRange.setHorizontalAlignment(SwingConstants.CENTER);
	GridBagConstraints gbc_lblDateRange = new GridBagConstraints();
	gbc_lblDateRange.fill = GridBagConstraints.BOTH;
	gbc_lblDateRange.insets = new Insets(0, 0, 5, 0);
	gbc_lblDateRange.gridx = 0;
	gbc_lblDateRange.gridy = 0;
	panel_10.add(lblDateRange, gbc_lblDateRange);

	// final JRadioButton radJustToday = new JRadioButton("Just Today");
	radJustToday = new JRadioButton("Just Today");
	radJustToday.setName("JustToday");
	radJustToday.setSelected(true);
	radJustToday.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		actionOnDateRangeButton(radJustToday);
	    }
	});
	btngpDateRange.add(radJustToday);
	GridBagConstraints gbc_radJustToday = new GridBagConstraints();
	gbc_radJustToday.anchor = GridBagConstraints.NORTHWEST;
	gbc_radJustToday.insets = new Insets(0, 0, 5, 0);
	gbc_radJustToday.gridx = 0;
	gbc_radJustToday.gridy = 1;
	panel_10.add(radJustToday, gbc_radJustToday);

	// final JRadioButton radDateRange = new JRadioButton("Date Range");
	radDateRange = new JRadioButton("Date Range");
	radDateRange.setName("DateRange");
	radDateRange.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		actionOnDateRangeButton(radDateRange);
	    }
	});
	btngpDateRange.add(radDateRange);
	GridBagConstraints gbc_radDateRange = new GridBagConstraints();
	gbc_radDateRange.insets = new Insets(0, 0, 5, 0);
	gbc_radDateRange.anchor = GridBagConstraints.NORTH;
	gbc_radDateRange.fill = GridBagConstraints.HORIZONTAL;
	gbc_radDateRange.gridx = 0;
	gbc_radDateRange.gridy = 2;
	panel_10.add(radDateRange, gbc_radDateRange);

	JPanel pnlStartingDate = new JPanel();
	GridBagConstraints gbc_pnlStartingDate = new GridBagConstraints();
	gbc_pnlStartingDate.insets = new Insets(0, 0, 5, 0);
	gbc_pnlStartingDate.gridx = 0;
	gbc_pnlStartingDate.gridy = 3;
	panel_10.add(pnlStartingDate, gbc_pnlStartingDate);
	GridBagLayout gbl_pnlStartingDate = new GridBagLayout();
	gbl_pnlStartingDate.columnWidths = new int[] { 116, 0 };
	gbl_pnlStartingDate.rowHeights = new int[] { 14, 23 };
	gbl_pnlStartingDate.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
	gbl_pnlStartingDate.rowWeights = new double[] { 0.0, 0.0 };
	pnlStartingDate.setLayout(gbl_pnlStartingDate);

	JLabel lblNewLabel = new JLabel("Starting Date");
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
	gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
	gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
	gbc_lblNewLabel.gridx = 0;
	gbc_lblNewLabel.gridy = 0;
	pnlStartingDate.add(lblNewLabel, gbc_lblNewLabel);

	tbxStartingDate = new JTextField();
	tbxStartingDate.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		calendarPopup.CalendarDialogOptionPanel optPanel = new calendarPopup.CalendarDialogOptionPanel();
		LocalDate ldSelected = optPanel.SetupOptionPane();
		tbxStartingDate.setText(ldSelected.format(dtFormatter));
		actionOnDateRangeTextFields(tbxStartingDate);
	    }
	});
	tbxStartingDate.setName("StartDate");
//	tbxStartingDate.addActionListener(new ActionListener() {
//	    public void actionPerformed(ActionEvent e) {
//		actionOnDateRangeTextFields(tbxStartingDate);
//	    }
//	});
	tbxStartingDate.setEnabled(false);
	tbxStartingDate.setHorizontalAlignment(SwingConstants.CENTER);
	tbxStartingDate.setToolTipText("Date in format mm/dd/yyyy");
	tbxStartingDate.setColumns(10);
	GridBagConstraints gbc_tbxStartingDate = new GridBagConstraints();
	gbc_tbxStartingDate.anchor = GridBagConstraints.NORTH;
	gbc_tbxStartingDate.fill = GridBagConstraints.HORIZONTAL;
	gbc_tbxStartingDate.gridx = 0;
	gbc_tbxStartingDate.gridy = 1;
	pnlStartingDate.add(tbxStartingDate, gbc_tbxStartingDate);

	JPanel pnlEndingDate = new JPanel();
	GridBagConstraints gbc_pnlEndingDate = new GridBagConstraints();
	gbc_pnlEndingDate.gridx = 0;
	gbc_pnlEndingDate.gridy = 4;
	panel_10.add(pnlEndingDate, gbc_pnlEndingDate);
	GridBagLayout gbl_pnlEndingDate = new GridBagLayout();
	gbl_pnlEndingDate.columnWidths = new int[] { 116 };
	gbl_pnlEndingDate.rowHeights = new int[] { 14, 23 };
	gbl_pnlEndingDate.columnWeights = new double[] { 0.0 };
	gbl_pnlEndingDate.rowWeights = new double[] { 0.0, 0.0 };
	pnlEndingDate.setLayout(gbl_pnlEndingDate);

	JLabel lblEndingDate = new JLabel("Ending Date");
	lblEndingDate.setHorizontalAlignment(SwingConstants.CENTER);
	GridBagConstraints gbc_lblEndingDate = new GridBagConstraints();
	gbc_lblEndingDate.anchor = GridBagConstraints.NORTH;
	gbc_lblEndingDate.fill = GridBagConstraints.HORIZONTAL;
	gbc_lblEndingDate.insets = new Insets(0, 0, 5, 0);
	gbc_lblEndingDate.gridx = 0;
	gbc_lblEndingDate.gridy = 0;
	pnlEndingDate.add(lblEndingDate, gbc_lblEndingDate);

	tbxEndDate = new JTextField();
	tbxEndDate.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		calendarPopup.CalendarDialogOptionPanel optPanel = new calendarPopup.CalendarDialogOptionPanel();
		LocalDate ldSelected = optPanel.SetupOptionPane();
		tbxEndDate.setText(ldSelected.format(dtFormatter));
		actionOnDateRangeTextFields(tbxEndDate);
	    }
	});
	tbxEndDate.setName("EndDate");
	tbxEndDate.setEnabled(false);
	tbxEndDate.setHorizontalAlignment(SwingConstants.CENTER);
	tbxEndDate.setToolTipText("Date in format mm/dd/yyyy");
	tbxEndDate.setColumns(10);
	GridBagConstraints gbc_tbxEndDate = new GridBagConstraints();
	gbc_tbxEndDate.anchor = GridBagConstraints.NORTH;
	gbc_tbxEndDate.fill = GridBagConstraints.HORIZONTAL;
	gbc_tbxEndDate.gridx = 0;
	gbc_tbxEndDate.gridy = 1;
	pnlEndingDate.add(tbxEndDate, gbc_tbxEndDate);

	JPanel pnlListToUse = new JPanel();
	pnlListToUse.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
	pnlListToUse.setBounds(574, 122, 106, 131);
	frame.getContentPane().add(pnlListToUse);
	GridBagLayout gbl_pnlListToUse = new GridBagLayout();
	gbl_pnlListToUse.columnWidths = new int[] { 120 };
	gbl_pnlListToUse.rowHeights = new int[] { 23, 23, 23, 23, 23 };
	gbl_pnlListToUse.columnWeights = new double[] { 1.0 };
	gbl_pnlListToUse.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0 };
	pnlListToUse.setLayout(gbl_pnlListToUse);

	JLabel lblWhichComics = new JLabel("List to use");
	lblWhichComics.setFont(new Font("Tahoma", Font.BOLD, 11));
	GridBagConstraints gbc_lblWhichComics = new GridBagConstraints();
	gbc_lblWhichComics.fill = GridBagConstraints.VERTICAL;
	gbc_lblWhichComics.insets = new Insets(0, 0, 5, 0);
	gbc_lblWhichComics.gridx = 0;
	gbc_lblWhichComics.gridy = 0;
	pnlListToUse.add(lblWhichComics, gbc_lblWhichComics);
	lblWhichComics.setHorizontalAlignment(SwingConstants.CENTER);

	radAllComics = new JRadioButton("All");
	radAllComics.setName("radAllComics");
	btngpWhichComics.add(radAllComics);
	GridBagConstraints gbc_radAllComics = new GridBagConstraints();
	gbc_radAllComics.anchor = GridBagConstraints.WEST;
	gbc_radAllComics.insets = new Insets(0, 0, 5, 0);
	gbc_radAllComics.gridx = 0;
	gbc_radAllComics.gridy = 1;
	pnlListToUse.add(radAllComics, gbc_radAllComics);
	radAllComics.setFont(new Font("Tahoma", Font.PLAIN, 10));
	radAllComics.setHorizontalAlignment(SwingConstants.LEFT);
	radAllComics.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		actionOnListToUseRadioButtons(radAllComics);
	    }
	});

	radMyComics = new JRadioButton("My Comics");
	radMyComics.setName("radMyComics");
	btngpWhichComics.add(radMyComics);
	radMyComics.setFont(new Font("Tahoma", Font.PLAIN, 10));
	radMyComics.setHorizontalAlignment(SwingConstants.LEFT);
	GridBagConstraints gbc_radMyComics = new GridBagConstraints();
	gbc_radMyComics.fill = GridBagConstraints.BOTH;
	gbc_radMyComics.insets = new Insets(0, 0, 5, 0);
	gbc_radMyComics.gridx = 0;
	gbc_radMyComics.gridy = 2;
	pnlListToUse.add(radMyComics, gbc_radMyComics);
	radMyComics.setSelected(true);
	radMyComics.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		actionOnListToUseRadioButtons(radMyComics);
	    }
	});

	radNotMyComics = new JRadioButton("Not My Comics");
	radNotMyComics.setName("radNotMyComics");
	btngpWhichComics.add(radNotMyComics);
	radNotMyComics.setFont(new Font("Tahoma", Font.PLAIN, 10));
	radNotMyComics.setHorizontalAlignment(SwingConstants.LEFT);
	GridBagConstraints gbc_radNotMyComics = new GridBagConstraints();
	gbc_radNotMyComics.insets = new Insets(0, 0, 5, 0);
	gbc_radNotMyComics.fill = GridBagConstraints.BOTH;
	gbc_radNotMyComics.gridx = 0;
	gbc_radNotMyComics.gridy = 3;
	pnlListToUse.add(radNotMyComics, gbc_radNotMyComics);
	radNotMyComics.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		actionOnListToUseRadioButtons(radNotMyComics);
	    }
	});

	radOtherListToUse = new JRadioButton("Other");
	radOtherListToUse.setName("radOtherListToUse");
	btngpWhichComics.add(radOtherListToUse);
	radOtherListToUse.setFont(new Font("Tahoma", Font.PLAIN, 10));
	radOtherListToUse.setHorizontalAlignment(SwingConstants.LEFT);
	GridBagConstraints gbc_radOtherListToUse = new GridBagConstraints();
	gbc_radOtherListToUse.anchor = GridBagConstraints.WEST;
	gbc_radOtherListToUse.gridx = 0;
	gbc_radOtherListToUse.gridy = 4;
	pnlListToUse.add(radOtherListToUse, gbc_radOtherListToUse);
	radOtherListToUse.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		actionOnListToUseRadioButtons(radOtherListToUse);
	    }
	});

	tbxTotalCount = new JTextField();
	tbxTotalCount.setBounds(10, 267, 51, 20);
	frame.getContentPane().add(tbxTotalCount);
	tbxTotalCount.setColumns(10);

	tbxCurrentIndex = new TextField();
	tbxCurrentIndex.setBounds(86, 267, 58, 20);
	frame.getContentPane().add(tbxCurrentIndex);

	JLabel lblNewLabel_1 = new JLabel("Total Count");
	lblNewLabel_1.setBounds(10, 250, 86, 14);
	frame.getContentPane().add(lblNewLabel_1);

	JLabel lblCurrentIndx = new JLabel("Current Indx");
	lblCurrentIndx.setBounds(86, 250, 73, 14);
	frame.getContentPane().add(lblCurrentIndx);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(0, 335, 902, 410);
	frame.getContentPane().add(scrollPane);

	tbxMessageBoard = new JTextArea();
	scrollPane.setViewportView(tbxMessageBoard);

	tbxSuccessful = new TextField();
	tbxSuccessful.setText("0");
	tbxSuccessful.setBounds(170, 267, 58, 20);
	frame.getContentPane().add(tbxSuccessful);

	JLabel lblSuccessful = new JLabel("Successful");
	lblSuccessful.setHorizontalAlignment(SwingConstants.CENTER);
	lblSuccessful.setBounds(170, 250, 58, 14);
	frame.getContentPane().add(lblSuccessful);

	tbxFailure = new TextField();
	tbxFailure.setText("0");
	// tbxFailure.setHorizontalAlignment(SwingConstants.CENTER);
	// tbxFailure.setColumns(10);
	tbxFailure.setBounds(238, 267, 58, 22);
	frame.getContentPane().add(tbxFailure);

	JLabel lblFailure = new JLabel("failure");
	lblFailure.setHorizontalAlignment(SwingConstants.CENTER);
	lblFailure.setBounds(238, 250, 58, 14);
	frame.getContentPane().add(lblFailure);

	pnlOther = new JPanel();
	pnlOther.setVisible(false);
	pnlOther.setName("pnlOther");
	pnlOther.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
	pnlOther.setBounds(574, 264, 215, 64);
	frame.getContentPane().add(pnlOther);
	pnlOther.setLayout(null);

	radOtherComics = new JRadioButton("Comics");
	radOtherComics.setName("radOtherComics");
	radOtherComics.setBounds(6, 7, 65, 23);
	pnlOther.add(radOtherComics);

	radOtherNew = new JRadioButton("New");
	radOtherNew.setName("radOtherNew");
	radOtherNew.setBounds(96, 7, 65, 23);
	pnlOther.add(radOtherNew);

	tbxOtherText = new JTextField();
	tbxOtherText.setName("tbxOtherText");
	tbxOtherText.setBounds(16, 33, 163, 20);
	pnlOther.add(tbxOtherText);
	tbxOtherText.setColumns(10);

    }
}
