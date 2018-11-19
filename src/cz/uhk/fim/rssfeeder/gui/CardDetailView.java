package cz.uhk.fim.rssfeeder.gui;

import model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CardDetailView extends  JPanel{

    Color colorBG = Color.decode("0xC02F1D");

    private static final int ITEM_WIDTH = 300;
    private static final int COMPONENT_WIDTH = 280;
    private static final int HEIGHT = 1;

    final String startHtml = "<html><p style='width:" + COMPONENT_WIDTH + " px'>";
    final String endHtml = "</p></html>";

    public CardDetailView(RSSItem item) {
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH, HEIGHT);
        setTitle(item.getTitle());
        setDescription(item.getDescrition());
        setInfo(String.format("%s - %s", item.getPubDate(), item.getAuthor()));
        setLink(item.getLink());
        setBackground(colorBG);
    }

    private void setInfo(String format) {
        JLabel lblInfo = new JLabel();
        lblInfo.setSize(COMPONENT_WIDTH, HEIGHT);
        lblInfo.setFont(new Font("Courier New", Font.ITALIC, 15)); // Zdroje: (http://www.java2s.com/Tutorial/Java/0240__Swing/SetFontandforegroundcolorforaJLabel.htm)
        lblInfo.setText(String.format("%s%s%s", startHtml, format, endHtml));
        lblInfo.setForeground(Color.WHITE);
        add(lblInfo);
    }

    private void setDescription(String descrition) {
        JLabel lblDestription = new JLabel();
        lblDestription.setSize(COMPONENT_WIDTH, HEIGHT);
        lblDestription.setFont(new Font("Courier New", Font.PLAIN, 18)); // Zdroje: (http://www.java2s.com/Tutorial/Java/0240__Swing/SetFontandforegroundcolorforaJLabel.htm)
        lblDestription.setText(String.format("%s%s%s", startHtml, descrition, endHtml));
        lblDestription.setForeground(Color.WHITE);
        add(lblDestription);
    }

    private void setTitle(String title) {
        JLabel lblTitle = new JLabel();
        lblTitle.setSize(COMPONENT_WIDTH, HEIGHT);
        lblTitle.setFont(new Font("Courier New", Font.BOLD, 25)); // Zdroje: (http://www.java2s.com/Tutorial/Java/0240__Swing/SetFontandforegroundcolorforaJLabel.htm)
        lblTitle.setText(String.format("%s%s%s", startHtml, title, endHtml));
        lblTitle.setForeground(Color.WHITE);
        add(lblTitle);
    }
    private void setLink(String link){
        JLabel lblLink = new JLabel();
        lblLink.setSize(COMPONENT_WIDTH, HEIGHT);
        lblLink.setFont(new Font("Courier New", Font.BOLD, 8));
        lblLink.setText(String.format("%s%s%s", startHtml, link, endHtml));
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link.trim()));

                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                    System.out.println(".");
                }
            }
        });
        lblLink.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
               lblLink.setCursor(hand);
            }
        });
        add(lblLink);
    }
}
