/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation.Specials;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author jean
 */
public class clsTextFieldLimit extends PlainDocument {
    private final int pLimit;
    
    public clsTextFieldLimit(int aLimit) {
        super();
        this.pLimit = aLimit;
    }
    
    @Override
    public void insertString(int aOffset, String aStringInserted, AttributeSet aAttribute ) throws BadLocationException {
        //If String empty then return.
        if (aStringInserted == null) return;
        
        int mFormerLength = getLength();
        int mInsertedLength = aStringInserted.length();
        int mTotalLength = mFormerLength + mInsertedLength;
        int mDifference = this.pLimit - mFormerLength;
        
        if (mTotalLength <= this.pLimit) {
            super.insertString(aOffset, aStringInserted, aAttribute);
        } else if (mFormerLength < this.pLimit) {
            String mNewString = aStringInserted.substring(0, mDifference);
            super.insertString(aOffset, mNewString, aAttribute);
        }
    }
}
