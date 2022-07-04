package gui;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class OffsetTabbedPaneUI extends BasicTabbedPaneUI {
    private int leadingOffset = 0;
    private int minHeight = 0;
    private int minWidth = 0;
    private int trailingOffset;
    
    public OffsetTabbedPaneUI() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#calculateTabHeight(int, int, int)
     */
    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex,
            int fontHeight) {
        return Math.max(super.calculateTabHeight(tabPlacement, tabIndex, fontHeight), this.minHeight);
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex,
            FontMetrics metrics) {
        return Math.max(super.calculateTabWidth(tabPlacement, tabIndex, metrics), this.minWidth);
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTabbedPaneUI#getTabAreaInsets(int)
     */
    @Override
    protected Insets getTabAreaInsets(int tabPlacement) {
        // ignores tab placement for now
        return new Insets(0, this.leadingOffset, 0, this.trailingOffset);
    }


    /**
     * @param offset the offset to set
     */
    public void setLeadingOffset(int offset) {
        this.leadingOffset = offset;
    }

    /**
     * @param minHeight the minHeight to set
     */
    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setTrailingOffset(int offset) {
        this.trailingOffset = offset;
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
            boolean isSelected) {
        // TODO Auto-generated method stub
        // super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
    }
}