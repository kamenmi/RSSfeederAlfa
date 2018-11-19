package cz.uhk.fim.rssfeeder.utils;

import cz.uhk.fim.rssfeeder.model.RSSSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final String CONFIG_FILE = "config.cfg";

    public static String loadStringFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static void saveStringToFile(String filepath, byte[] data) throws IOException {
        Path path = Paths.get(filepath);
        Files.write(path, data);

    }

    //ulozeni do souboru ve formatu, ktery se bude dobre cist
    // nazev;link

    public static void saveSources(List<RSSSource> sources){
        StringBuilder fileContent = new StringBuilder();
        for(int i =0; i < sources.size(); i++){
            fileContent.append(String.format("%s;%s", sources.get(i).getName(),
                    sources.get(i).getSource()));
            fileContent.append(i != sources.size()-1 ? "\n" : "");

        }
        try {
            saveStringToFile(CONFIG_FILE, fileContent.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // pro nacitani vyuzijeme bufferedReader
//    we will use the bufferedReader to load
    public static List<RSSSource> loadSources() throws IOException {
        List<RSSSource> sources = new ArrayList<>();
        new BufferedReader(new StringReader(loadStringFromFile(CONFIG_FILE))).lines().forEach(line->{
            String[] parts = line.split(";");
            sources.add(new RSSSource(parts[0],parts[1]));
        });

        return sources;
    }
}
