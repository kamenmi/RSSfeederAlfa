package cz.uhk.fim.rssfeeder.gui;

import cz.uhk.fim.rssfeeder.model.RSSItem;
import cz.uhk.fim.rssfeeder.model.RSSItemsList;
import cz.uhk.fim.rssfeeder.model.RSSSource;
import cz.uhk.fim.rssfeeder.utils.FileUtils;
import cz.uhk.fim.rssfeeder.utils.RSSParser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VATLIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";


    private JLabel lblErrorMessage = new JLabel("Zpráva");
    private JTextField txtInputField = new JTextField();

    private RSSItemsList rssList;
    private WindowRSS windowRSS;

    public void init() {
        setTitle("RSSfeeder");
        setSize(725, 825);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initContentUI();
    }

    private void initContentUI() {
        MainFrame self = this;
        JPanel controlPanel = new JPanel(new BorderLayout());

//        RSSSource[] sourcesList = new RSSSource[];
        Vector sourcesItems = new Vector();
        final DefaultComboBoxModel comboModel = new DefaultComboBoxModel(sourcesItems);
//        RSSComboModel comboModel = new RSSComboModel(sourcesItems);
        JComboBox<RSSSource> comboBoxRss = new JComboBox<>(comboModel);

        List<RSSSource> rsslist = null;
        try {
            rsslist = FileUtils.loadSources();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (RSSSource rss : rsslist) {
            comboModel.addElement(rss);
        }

        JPanel northPanel = new JPanel(new BorderLayout());
        controlPanel.add(northPanel, BorderLayout.NORTH);

        // buttony - vytvoreni
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton load = new JButton("Load Example items");
        JButton remove = new JButton("Remove");

        // buttony - pridani do boxu
        Box boxButton = Box.createHorizontalBox();
        boxButton.add(add);
        boxButton.add(edit);

        boxButton.add(remove);
//        boxButton.add(load);

        // pridani do northPanelu
        northPanel.add(comboBoxRss, BorderLayout.NORTH);
        northPanel.add(boxButton, BorderLayout.SOUTH);


        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                WindowRSS dlg = new WindowRSS(self, "Add New RSS");
                RSSSource result = dlg.showDialog();
//                String name = dlg.getName();
//                String text = dlg.getLink();
                System.out.println("name " + result.getName());
                System.out.println("text " + result.getSource());
                if (result.isValidRSS()) {
                    sourcesItems.addElement(result);
//                    comboBoxRss.validate();
//                    comboBoxRss.repaint();
                    FileUtils.saveSources(sourcesItems);
                    // TODO: validate if zero exists
                    comboBoxRss.setSelectedIndex(sourcesItems.size() - 1);
                }
            }
        });

        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (comboBoxRss.getSelectedIndex() == -1) {
                    return;
                }
                RSSSource item = (RSSSource) comboBoxRss.getSelectedItem();
                WindowRSS dlg = new WindowRSS(self, "Edit New RSS", item);
                RSSSource result = dlg.showDialog();
                System.out.println("edit name " + result.getName());
                System.out.println("edit text " + result.getSource());
                comboBoxRss.setSelectedIndex(comboBoxRss.getSelectedIndex());
                comboBoxRss.validate();
                comboBoxRss.repaint();
//                sourcesItems.addElement(result);
                FileUtils.saveSources(sourcesItems);

            }
        });

        load.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RSSSource s1 = new RSSSource("name file", "rss.xml");
//                RSSSource s1 = new RSSSource("name file", "rss.xml");
                RSSSource s2 = new RSSSource("name web", "http://www.canaltrans.com/podcast/rssaudio.xml");
                List sources = new ArrayList<RSSSource>();

                sources.add(s1);
                sources.add(s2);
                FileUtils.saveSources(sources);
            }

        });


        JPanel contentPanel = new JPanel(new WrapLayout());
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                WindowRSS dlg = new WindowRSS(self, "New RSS");
//                RSSSource result = dlg.showDialog();
//                System.out.println("name " + result.getName());
//                System.out.println("text " + result.getSource());
                int selected = comboBoxRss.getSelectedIndex();
                sourcesItems.remove(selected);
                if (sourcesItems.size() == 0) {

                    fillRSSData(contentPanel, comboBoxRss);
                    comboBoxRss.setSelectedIndex(-1);
                } else {
                    comboBoxRss.setSelectedIndex(0);
                }
                FileUtils.saveSources(sourcesItems);
                // TODO: validate if zero exists

            }
        });

        comboBoxRss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                RSSSource selected = comboModel.getSelectedItem();
//                String src = selected.getSource();
                fillRSSData(contentPanel, comboBoxRss);
            }
        });



 /*       JButton btnLoad = new JButton("Load");
        JButton btnSave = new JButton("save");

        controlPanel.add(btnLoad, BorderLayout.WEST);
        controlPanel.add(txtInputField, BorderLayout.CENTER);
        controlPanel.add(btnSave, BorderLayout.EAST);
        controlPanel.add(lblErrorMessage, BorderLayout.SOUTH);*/

        //umisteni do panelu a zarovani na stred -- (https://stackoverflow.com/questions/16957329/borderlayout-center-doesnt-center)
        lblErrorMessage.setVerticalAlignment(SwingConstants.CENTER);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblErrorMessage.setForeground(Color.RED); // nastaveni barvy textu -- (https://stackoverflow.com/questions/2966334/how-do-i-set-the-colour-of-a-label-coloured-text-in-java)
        lblErrorMessage.setVisible(false);

        add(controlPanel, BorderLayout.NORTH);


        fillRSSData(contentPanel, comboBoxRss);

        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        /* btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatInput()) {
                    try {
                        txtContent.setText(FileUtils.loadStringFromFile(txtInputField.getText()));
                    } catch (IOException e1) {
                        showErrorMessage(IO_LOAD_TYPE);
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatInput()) {

                    try {
                        rssList = new RSSParser().getParsedRSS(txtInputField.getText());
                        txtContent.setText("");
                        for (RSSItem item : rssList.getAllItem()) {
                            txtContent.append(String.format("%s - autor: %s%n", item.getTitle(), item.getAuthor()));
                        }
                    } catch (IOException | SAXException | ParserConfigurationException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
*/

        /*btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RSSSource> sources = new ArrayList<>();
                sources.add(new RSSSource("živě.cz","https://www.zive.cz/rss/sc-47/"));
                sources.add(new RSSSource("adlskjaf","afasdfads"));
                sources.add(new RSSSource("665465","adfasdf"));
                FileUtils.saveSources(sources);
            }
        });

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<RSSSource> sources = FileUtils.loadSources();
                    for(RSSSource s : sources){
                        System.out.println(s.getName() + " - " + s.getSource());
                        }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });*/
    }

    private void fillRSSData(JPanel contentPanel, JComboBox comboModel) {
        if (comboModel.getItemCount() == 0) {
            contentPanel.removeAll();
            contentPanel.add(new JLabel("No Data."));
            contentPanel.validate();
            contentPanel.repaint();
            return;
        }
        try {
            rssList = new RSSParser().getParsedRSS(((RSSSource) comboModel.getSelectedItem()).getSource());

            contentPanel.removeAll();
            for (RSSItem item : rssList.getAllItem()) {
                CardView cardView = new CardView(item);
                cardView.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (e.getClickCount() == 2) {
                                SwingUtilities.invokeLater(() -> new DetailFrame(item).setVisible(true));
                            }
                        }
                    }
                });
                contentPanel.add(cardView);
            }
            contentPanel.validate();
            contentPanel.repaint();
//            contentPanel.paint();
        } catch (IOException | SAXException | ParserConfigurationException e1) {
            e1.printStackTrace();
        }
    }

    private boolean validatInput() {
        if (txtInputField.getText().trim().isEmpty()) {
            showErrorMessage(VALIDATION_TYPE);
            return false;
        }
        lblErrorMessage.setVisible(false);
        return true;
    }

    private void showErrorMessage(String type) {
        String message;
        switch (type) {
            case VALIDATION_TYPE:
                message = "Chyba! Pole nesmí být prázdné!";
                break;
            case IO_LOAD_TYPE:
                message = "Chyba při načítání souboru!";
                break;
            case IO_SAVE_TYPE:
                message = "Chyba při ukládání souboru!";
                break;
            default:
                message = "Něco se nepovedlo!";
                break;
        }
        lblErrorMessage.setText(message);
        lblErrorMessage.setVisible(true);
    }

    public MainFrame() {
        init();
    }
}

//TODO - dialog: - 2 fieldy (název, link) - pro oba validace (validateInput - upravit metodu pro potřeby kódu)
//      - validace polí "název" a "link" na přítomnost středníku (replaceAll(";", "");)
// - přidat do/upravit GUI - tlačítka "Add", "Edit", "Remove/Delete" - pro CRUD akce se sources
//      - přidat ComboBox pro výběr zdroje feedu - pouze název feedu (bez linku)
// - tlačítko "Load" - volitelně - buď automatická změna při výběru v ComboBoxu nebo výběr v ComboBoxu a pak Load
// - aplikace bude fungovat jak pro lokální soubor, tak pro online feed z internetu
// - aplikace v žádném případě nespadne na hubu - otestovat a ošetřit
// - funkční ukládání a načítání konfigurace
// - při spuštění aplikace se automaticky načte první záznam z konfigurace
//          - pokud konfigurace existuje nebo není prázdná -> nutno kontrolovat