package fr.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SCPGetter {
    String code;

    public SCPGetter() {
        this.code = "";
    }

    public void reset(){
        this.code = "";
    }

    public void get_html_page(String url) throws IOException {
        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            this.code += inputLine + "\n";
        in.close();
    }

    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    public void parse(){
        String en = "//end//";
        String br = "//bold//";

        this.code = this.code.substring(this.code.indexOf("<strong>Objet "), this.code.indexOf("<div class=\"footer-wikiwalk-nav\">"));
        this.code = this.code.replaceAll("<br />", "\n").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<blockquote>","\n_____________ \n").replaceAll("</blockquote>","\n _____________ \n");
        this.code = this.code.replaceAll("<strong>", br).replaceAll("</strong>", en);
        this.code = this.code.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");

    }

    public List<String> give(String url) throws IOException {
        this.get_html_page(url);
        this.parse();

        if(this.splitStringEvery(this.code, 2000).length != 0){
            return Arrays.asList(this.splitStringEvery(this.code.replaceAll("//bold//", "**").replaceAll("//end//", "**"), 2000));
        }

        return null;
    }

}

