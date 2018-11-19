package cz.uhk.fim.rssfeeder.gui;

import model.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DetailFrame extends JFrame {

    Color colorBG = Color.decode("0xF58B4C");


    public void init(RSSItem item) {
        setSize(380, 480);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        initContentUI(item);
    }

    private void initContentUI(RSSItem item) {
        JPanel controlPanel = new JPanel(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new WrapLayout());
        contentPanel.setBackground(colorBG);

        add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        contentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    dispose();
                }
            }
        });
        CardDetailView cardView = new CardDetailView(item);
        cardView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    dispose();
                }
            }
        });
        contentPanel.add(cardView);
    }

    public DetailFrame(RSSItem item) {
        init(item);
    }
}
