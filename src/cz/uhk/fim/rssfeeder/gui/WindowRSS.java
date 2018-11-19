package cz.uhk.fim.rssfeeder.gui;


import cz.uhk.fim.rssfeeder.model.RSSSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class WindowRSS extends JDialog {

    RSSSource source = new RSSSource();
    ArrayList<RSSSource> seznamRss = new ArrayList<>();
    String textNazev;
    String textLink;
    RSSSource updatingRSS;

//    public WindowRSS() {
//        super();
//        init();
//    }

//    public void init() {
//        setTitle("RSS Detail");
//        setSize(500, 120);
////        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        initContentUI();
//    }

    public WindowRSS(MainFrame mf, String title) {
        this(mf, title, new RSSSource());
    }

    public WindowRSS(MainFrame mf, String title, RSSSource selected) {
        super(mf, title, true);
        updatingRSS = selected;
        setLocationRelativeTo(mf);
        setSize(500, 150);
        //        textFieldLink = new JTextField();
//        textFieldLink = new JTextField();
        textNazev = updatingRSS.getName();
        textLink = updatingRSS.getSource();
//        JPanel panel=new JPanel();
//        panel.add(new JLabel("Hello dialog"));
//        this.getContentPane().add(panel);
//        this.setVisible(true);
        initContentUI();
    }

    private void initContentUI() {
        JDialog self = this;
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        JButton buttonOK = new JButton("Ok");
        JButton buttonCancel = new JButton("Cancel");
        JLabel linkNazev = new JLabel("Název RSS");
        JLabel linkLink = new JLabel("Link");
        JTextField textFieldNazev = new JTextField(textNazev);
        JTextField textFieldLink = new JTextField(textLink);
        JTextArea textArea = new JTextArea();

//        textFieldNazev.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                textNazev = textFieldNazev.getText();
//                System.out.println("textFieldNazev=" + textFieldNazev.getText());
//            }
//        });
//        textFieldLink.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                textLink = textFieldLink.getText();
//                System.out.println("link=" + textFieldLink.getText());
//            }
//        });

        Box southBox = Box.createHorizontalBox();
        southBox.add(Box.createHorizontalGlue());
        southBox.add(buttonOK);
        southBox.add(Box.createHorizontalGlue());
        southBox.add(buttonCancel);
        southBox.add(Box.createHorizontalGlue());

        northPanel.add(linkNazev, BorderLayout.NORTH);
        northPanel.add(textFieldNazev, BorderLayout.SOUTH);

        centerPanel.add(linkLink, BorderLayout.NORTH);
        centerPanel.add(textFieldLink, BorderLayout.CENTER);
//        centerPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        southPanel.add(southBox, BorderLayout.SOUTH);

//        setUndecorated(false);


//        setVisible(true);


        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                self.setVisible(false);
                self.dispose();
            }
        });
        buttonOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((new RSSSource(textFieldNazev.getText(), textFieldLink.getText()).isValidRSS())) {
                    updatingRSS.setName(textFieldNazev.getText());
                    updatingRSS.setSource(textFieldLink.getText());
                }
                self.setVisible(false);
                self.dispose();
//                System.out.println(source);
//                FileUtils.saveSources(Collections.singletonList(source));
            }
        });

    }

    public RSSSource showDialog() {
        setVisible(true);
//        dlg.setLocationRelativeTo(self); // A cz.uhk.fim.rssfeeder.model doesn't set its location automatically relative to its parent
//        dlg.setVisible(true);
        return updatingRSS;
    }

//    public String getName(){
//        return textFieldNazev.getText();
//    }
//    public String getLink(){
//        return textFieldLink.getText();
//    }

}
