package CaptureImages;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit;

public abstract class HtmlParserCallback extends HTMLEditorKit.ParserCallback {

    public abstract void flush() throws BadLocationException;

    public abstract void handleText(char[] data, int pos);

    public abstract void handleComment(char[] data, int pos);

    public abstract void handleStartTag(Tag t, MutableAttributeSet a, int pos);

    public abstract void handleEndTag(Tag t, int pos);

    public abstract void handleSimpleTag(Tag t, MutableAttributeSet a, int pos);

    public abstract void handleError(String errorMsg, int pos);

    public abstract void handleEndOfLineString(String eol);

    // @Override
    // public void flush() throws BadLocationException {
    // // TODO Auto-generated method stub
    // super.flush();
    // }
    //
    // @Override
    // public void handleText(char[] data, int pos) {
    // // TODO Auto-generated method stub
    // super.handleText(data, pos);
    // }
    //
    // @Override
    // public void handleComment(char[] data, int pos) {
    // // TODO Auto-generated method stub
    // super.handleComment(data, pos);
    // }
    //
    // @Override
    // public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
    // // TODO Auto-generated method stub
    // super.handleStartTag(t, a, pos);
    // }
    //
    // @Override
    // public void handleEndTag(Tag t, int pos) {
    // // TODO Auto-generated method stub
    // super.handleEndTag(t, pos);
    // }
    //
    // @Override
    // public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
    // // TODO Auto-generated method stub
    // super.handleSimpleTag(t, a, pos);
    // }
    //
    // @Override
    // public void handleError(String errorMsg, int pos) {
    // // TODO Auto-generated method stub
    // super.handleError(errorMsg, pos);
    // }
    //
    // @Override
    // public void handleEndOfLineString(String eol) {
    // // TODO Auto-generated method stub
    // super.handleEndOfLineString(eol);
    // }
    //
}
