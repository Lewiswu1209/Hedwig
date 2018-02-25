package cn.lewis.spider.model;

import org.hedwig.textutils.CharsetDetector;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Page {

    private CrawlerItem item = null;

    private String contentType;
    private Integer code = null;

    private Exception exception = null;

    private String html = null;
    private Document doc = null;

    private String charset = null;
    private byte[] content = null;

    public boolean matchUrl(String urlRegex) {
        return Pattern.matches(urlRegex, url());
    }

    public boolean matchContentType(String contentTypeRegex) {
        if (contentTypeRegex == null) {
            return contentType() == null;
        }
        return Pattern.matches(contentTypeRegex, contentType());
    }

    public ArrayList<String> attrs(String cssSelector, String attrName) {
        ArrayList<String> result = new ArrayList<String>();
        Elements eles = select(cssSelector);
        for (Element ele : eles) {
            if (ele.hasAttr(attrName)) {
                result.add(ele.attr(attrName));
            }
        }
        return result;
    }

    public String attr(String cssSelector, String attrName) {
        return select(cssSelector).attr(attrName);
    }

    public Links links(boolean parseImg) {
        Links links = new Links().addFromElement(doc(),parseImg);
        return links;
    }

    public Links links() {
        return links(false);
    }

    public Links links(String cssSelector, boolean parseSrc) {
        Links links = new Links().addBySelector(doc(), cssSelector,parseSrc);
        return links;
    }
    public Links links(String cssSelector) {
        return links(cssSelector,false);
    }

    public Links regexLinks(RegexRule regexRule, boolean parseSrc) {
        return new Links().addByRegex(doc(), regexRule, parseSrc);
    }

    public Links regexLinks(String regex, boolean parseSrc){
        return new Links().addByRegex(doc(),regex,parseSrc);
    }

    public Links regexLinks(RegexRule regexRule) {
        return regexLinks(regexRule, false);
    }

    public Links regexLinks(String regex){
        return regexLinks(regex,false);
    }

    public ArrayList<String> selectTextList(String cssSelector){
        ArrayList<String> result = new ArrayList<String>();
        Elements eles = select(cssSelector);
        for(Element ele:eles){
            result.add(ele.text());
        }
        return result;
    }

    public String selectText(String cssSelector, int index){
        List<String> list = selectTextList(cssSelector);
    	int realIndex = index;
        if (index < 0) {
            realIndex = list.size() + index;
        }
        return list.get(realIndex);
    }

    public String selectText(String cssSelector) {
        return select(cssSelector).first().text();
    }

    public ArrayList<Integer> selectIntList(String cssSelector){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String text:selectTextList(cssSelector)){
            result.add(Integer.valueOf(text.trim()));
        }
        return result;
    }

    public int selectInt(String cssSelector, int index){
        String text = selectText(cssSelector,index).trim();
        return Integer.valueOf(text);
    }

    public int selectInt(String cssSelector){
        return selectInt(cssSelector,0);
    }

    public ArrayList<Double> selectDoubleList(String cssSelector){
        ArrayList<Double> result = new ArrayList<Double>();
        for(String text:selectTextList(cssSelector)){
            result.add(Double.valueOf(text.trim()));
        }
        return result;
    }

    public double selectDouble(String cssSelector, int index){
        String text = selectText(cssSelector,index).trim();
        return Double.valueOf(text);
    }

    public double selectDouble(String cssSelector){
        return selectDouble(cssSelector,0);
    }

    public ArrayList<Long> selectLongList(String cssSelector){
        ArrayList<Long> result = new ArrayList<Long>();
        for(String text:selectTextList(cssSelector)){
            result.add(Long.valueOf(text.trim()));
        }
        return result;
    }

    public long selectLong(String cssSelector, int index){
        String text = selectText(cssSelector,index).trim();
        return Long.valueOf(text);
    }

    public long selectLong(String cssSelector){
        return selectLong(cssSelector,0);
    }


    public Elements select(String cssSelector) {
        return this.doc().select(cssSelector);
    }

    public Element select(String cssSelector, int index) {
        Elements eles = select(cssSelector);
        int realIndex = index;
        if (index < 0) {
            realIndex = eles.size() + index;
        }
        return eles.get(realIndex);
    }

    public String regex(String regex, int group, String defaultResult) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html());
        if (matcher.find()) {
            return matcher.group(group);
        } else {
            return defaultResult;
        }
    }

    public String regex(String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html());
        matcher.find();
        return matcher.group(group);
    }
/*
    public String regexAndFormat(String regex, String format){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html());
        matcher.find();
        String[] strs = new String[matcher.groupCount()];
        for(int i=0;i<matcher.groupCount();i++){
            strs[i] = matcher.group(i+1);
        }
        return String.format(format, strs);
    }
*/
    public String regex(String regex, String defaultResult) {
        return regex(regex, 0, defaultResult);
    }

    public String regex(String regex) {
        return regex(regex, 0);
    }

    public Page(CrawlerItem datum,
                Integer code,
                String contentType,
                byte[] content){

        this.item = datum;
        this.code = code;
        this.contentType = contentType;
        this.content = content;
    }

    public byte[] content() {
        return content;
    }

    public void content(byte[] content){
        this.content = content;
    }

    public String url() {
        return item.getUrl();
    }

    public String html() {
        if (html != null) {
            return html;
        }

        if (content == null) {
            return null;
        }
        if (charset == null) {
            charset = CharsetDetector.guessEncoding(content());
        }
        try {
            html = new String(content, charset);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return html;
    }

    public void html(String html) {
        this.html = html;
    }

    public String contentType() {
        return contentType;
    }

    public Document doc() {
        if (doc != null) {
            return doc;
        }
        this.doc = Jsoup.parse(html(), url());
        return doc;
    }

    public void doc(Document doc){
        this.doc = doc;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public CrawlerItem crawlDatum() {
        return item;
    }

    public void crawlDatum(CrawlerItem crawlDatum) {
        this.item = crawlDatum;
    }

//    public JsonObject meta() {
//        return crawlDatum.getMeta();
//    }

    public int metaAsInt(String key) {
        return item.getMetaAsInt(key);
    }

    public boolean metaAsBoolean(String key) {
        return item.getMetaAsBoolean(key);
    }

    public double metaAsDouble(String key) {
        return item.getMetaAsDouble(key);
    }

    public long metaAsLong(String key) {
        return item.getMetaAsLong(key);
    }

    public String charset() {
        if (charset == null) {
            charset = CharsetDetector.guessEncoding(content());
        }
        return charset;
    }

    public void charset(String charset) {
        this.charset = charset;
    }

    public String key() {
        return item.getKey();
    }

    public void code(int code){
        this.code = code;
    }

    public int code() {
        return code;
    }

    public Page meta(JsonObject metaData) {
        item.meta(metaData);
        return this;
    }

    public String meta(String key) {
        return item.getMeta(key);
    }

    public Page meta(String key, String value) {
        item.meta(key,value);
        return this;
    }

    public Page meta(String key, int value) {
        item.meta(key,value);
        return this;
    }

    public Page meta(String key, boolean value) {
        item.meta(key,value);
        return this;
    }

    public Page meta(String key, double value) {
        item.meta(key,value);
        return this;
    }

    public Page meta(String key, long value) {
        item.meta(key,value);
        return this;
    }
}
