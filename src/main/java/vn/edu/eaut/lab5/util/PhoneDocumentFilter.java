package vn.edu.eaut.lab5.util;

import javax.swing.text.*;

public class PhoneDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        String current = fb.getDocument().getText(0, fb.getDocument().getLength());
        String next = current.substring(0, offset) + string + current.substring(offset);
        if (next.matches("\\d{0,10}")) super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) text = "";
        String current = fb.getDocument().getText(0, fb.getDocument().getLength());
        String next = current.substring(0, offset) + text + current.substring(offset + length);
        if (next.matches("\\d{0,10}")) super.replace(fb, offset, length, text, attrs);
    }
}
