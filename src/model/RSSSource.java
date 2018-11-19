package model;

import java.io.File;

public class RSSSource{
    private String name;
    private String source;

    public RSSSource(String name, String source) {
        this.name = name.replaceAll(";", "");
        this.source = source;
    }

    public RSSSource() {
        this("", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replaceAll(";", "");
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String toString(){
        return this.name;
    }

    public boolean isValidRSS(){
        String s = this.source.trim().toLowerCase();
        // TODO: check if is a valid RSS file, it is only verifying that it exists
        if (this.name == "" || this.name == null || this.source == ""|| this.source== null){
            return false;
        }

        boolean isWeb = s.startsWith("http://") || s.startsWith("https://");
        if (isWeb) {
            return true;
        } else {
            File f = new File(this.source);
            return (f.exists() && !f.isDirectory());
        }
    }
}
