package com.englearnsh.buildseed.util;

import com.englearnsh.buildseed.pojo.Content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlParse {

    // Need Internet connection

    public List<Content> parseVersion() throws Exception {
        // Get request
        String url = "https://msbuilds.rg-adguard.net";

        // Parse content
        Document document = Jsoup.parse(new URL(url), 10000);
        Element element = document.getElementById("content");
        Elements elements = element.getElementsByClass("block-header");

        ArrayList<Content> versionList = new ArrayList<>();

        for (Element el : elements) {
            String version = el.getElementsByTag("a").text();
            Content content = new Content();
            content.setVersion(version);
            versionList.add(content);
        }

        return versionList;
    }
}
