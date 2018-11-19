package cz.uhk.fim.rssfeeder.gui;

import model.RSSItem;

import javax.swing.*;
import java.awt.*;


public class CardView extends JPanel {

    private static final int ITEM_WIDTH = 180;
    private static final int COMPONENT_WIDTH = 160;
    private static final int HEIGHT = 1;

    final String startHtml = "<html><p style='width:" + COMPONENT_WIDTH + " px'>";
    final String endHtml = "</p></html>";
    private Color textColor;

    public CardView(RSSItem item) {
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH, HEIGHT);
        setTitle(item.getTitle());
        setDescription(item.getDescrition());
        setInfo(String.format("%s - %s", item.getPubDate(), item.getAuthor()));
        setBackground(textColor);
    }

    private void setInfo(String format) {
        JLabel lblInfo = new JLabel();
        lblInfo.setSize(COMPONENT_WIDTH, HEIGHT);
        lblInfo.setFont(new Font("Courier New", Font.ITALIC, 10)); // Zdroje: (http://www.java2s.com/Tutorial/Java/0240__Swing/SetFontandforegroundcolorforaJLabel.htm)
        lblInfo.setText(String.format("%s%s%s", startHtml, format, endHtml));
        add(lblInfo);
    }

    private void setDescription(String descrition) {
        JLabel lblDestription = new JLabel();
        lblDestription.setSize(COMPONENT_WIDTH, HEIGHT);
        lblDestription.setFont(new Font("Courier New", Font.PLAIN, 11)); // Zdroje: (http://www.java2s.com/Tutorial/Java/0240__Swing/SetFontandforegroundcolorforaJLabel.htm)
        lblDestription.setText(String.format("%s%5.35s%s%s", startHtml, descrition, "...", endHtml));
        add(lblDestription);
    }

    private void setTitle(String title) {
        JLabel lblTitle = new JLabel();
        lblTitle.setSize(COMPONENT_WIDTH, HEIGHT);
        lblTitle.setFont(new Font("Courier New", Font.BOLD, 12)); // Zdroje: (http://www.java2s.com/Tutorial/Java/0240__Swing/SetFontandforegroundcolorforaJLabel.htm)
        lblTitle.setText(String.format("%s%s%s", startHtml, title, endHtml));
        add(lblTitle);
        getRandomBgColor(title);
    }

    private Color getRandomBgColor(String title) {
        int length = title.length();
        String[] parts = new String[3];
        int[] colors = new int[3];
        parts[0] = title.substring(0, length / 3);
        parts[1] = title.substring(length / 3, 2 * (length / 3));
        parts[2] = title.substring(2 * (length / 3), length);

        colors[0] = Math.abs(parts[0].hashCode() / 10000000);
        colors[1] = Math.abs(parts[1].hashCode() / 10000000);
        colors[2] = Math.abs(parts[2].hashCode() / 10000000);

        Color color = new Color(colors[0], colors[1], colors[2]);
        setInverseTextColor(color);
        return color;
    }

    private void setInverseTextColor(Color bgcolor) {
        textColor = new Color(255 - bgcolor.getRed(), 255 - bgcolor.getGreen(), 255 - bgcolor.getBlue());
    }
}


