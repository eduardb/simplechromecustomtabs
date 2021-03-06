package com.novoda.simplechromecustomtabs.demo.linkify;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class UrlFinderTest {

    Pattern testPattern = Pattern.compile("\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
            "|mil|biz|info|mobi|name|aero|jobs|museum" +
            "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

    private UrlFinder finder = new UrlFinder(testPattern);

    @Test
    public void passingNullReturnsEmptyList() {
        List<MatchedUrl> urls = finder.findUrlsIn(null);
        assertThat(urls.size()).isEqualTo(0);
    }

    @Test
    public void passingEmptyStringReturnsEmptyList() {
        List<MatchedUrl> urls = finder.findUrlsIn("");
        assertThat(urls.size()).isEqualTo(0);
    }

    @Test
    public void urlIsCorrect() {
        String url = "http://www.novoda.com";
        int start = 0;
        int end = url.length();
        MatchedUrl expectedMatchedUrl = new MatchedUrl(url, start, end);

        String fullString = url + " is a great url!";
        List<MatchedUrl> urls = finder.findUrlsIn(fullString);
        MatchedUrl matchedUrl = urls.get(0);
        assertThat(matchedUrl).isEqualTo(expectedMatchedUrl);
    }

    @Test
    public void finderFindsTheCorrectNumberOfUrls() {
        String url = "List of URLs\n 1) http://www.novoda.com\n2) www.google.com 3) ???";
        List<MatchedUrl> urls = finder.findUrlsIn(url);
        assertThat(urls.size()).isEqualTo(2);
    }

}
