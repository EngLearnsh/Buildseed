package com.englearnsh.buildseed.util

import com.englearnsh.buildseed.Content

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.net.URL
import java.util.*

class HtmlParse {

    // Need Internet connection

    @Throws(Exception::class)
    fun parseVersion(): MutableList<Content> {
        // Get request
        val url = "https://msbuilds.rg-adguard.net"

        // Parse content
        val document : Document = Jsoup.parse(URL(url), 10000)
        val element : Element = document.getElementById("content")
        val elements : Elements = element.getElementsByClass("block-header")

        val versionList = ArrayList<Content>()

        for (el : Element in elements) {
            val version = el.getElementsByTag("a").text()
            val content = Content(version)
            versionList.add(content)
        }

        return versionList
    }
}