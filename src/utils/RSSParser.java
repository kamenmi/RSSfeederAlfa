package utils;

import model.RSSItemsList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RSSParser {

    private RSSItemsList rssList;
    private ItemHandler itemHandler;

    public RSSParser() {
        this.rssList = new RSSItemsList();
        this.itemHandler = new ItemHandler(rssList);
    }

    private void parse(String source) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        // TODO: Done
        String s = source.trim().toLowerCase();
        boolean isWeb = s.startsWith("http://") || s.startsWith("https://");
        if (isWeb) {
            parser.parse(new InputSource(new URL(source).openStream()), itemHandler);
        } else {
            parser.parse(new File(source), itemHandler);
        }

    }

    public RSSItemsList getParsedRSS(String source) throws IOException, SAXException, ParserConfigurationException {
        parse(source);
        return rssList;
    }
}
