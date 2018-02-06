package cn.lewis.spider.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Links implements Iterable<String> {

    protected LinkedList<String> linkList = new LinkedList<String>();

    public Links() {

    }

    public Links(Iterable<String> links) {
        add(links);
    }

    public Links(Collection<String> urls) {
        add(urls);
    }

    public Links add(String url) {
        linkList.add(url);
        return this;
    }

    public Links add(Iterable<String> links) {
        for (String url : links) {
            linkList.add(url);
        }
        return this;
    }

    public Links add(Collection<String> urls) {
        linkList.addAll(urls);
        return this;
    }

    public Links filterByRegex(RegexRule regexRule) {
        Iterator<String> ite = iterator();
        while(ite.hasNext()){
            String url = ite.next();
            if (!regexRule.satisfy(url)) {
                ite.remove();
            }
        }
        return this;
    }

    public Links filterByRegex(String regex) {
        RegexRule regexRule = new RegexRule();
        regexRule.addRule(regex);
        return filterByRegex(regexRule);
    }

    public Links addFromElement(Element ele) {
        addFromElement(ele,false);
        return this;
    }

    public Links addFromElement(Element ele, boolean parseImg) {
        add(ele.select("a[href]").eachAttr("abs:href"));
        if(parseImg){
            add(ele.select("img[src]").eachAttr("abs:src"));
        }
        return this;
    }

    public Links addBySelector(Element ele, String cssSelector, boolean parseSrc) {
        Elements as = ele.select(cssSelector);
        for (Element a : as) {
            if (a.hasAttr("href")) {
                String href = a.attr("abs:href");
                this.add(href);
            }
            if(parseSrc){
                if(a.hasAttr("src")){
                    String src = a.attr("abs:src");
                    this.add(src);
                }
            }
        }
        return this;
    }
    public Links addBySelector(Element ele, String cssSelector){
        return addBySelector(ele ,cssSelector,false);
    }

    public Links addByRegex(Element ele, RegexRule regexRule, boolean parseSrc) {
        for(String href: ele.select("a[href]").eachAttr("abs:href")){
            if (regexRule.satisfy(href)) {
                this.add(href);
            }
        }
        if(parseSrc) {
            for (String src : ele.select("*[src]").eachAttr("abs:src")){
                if(regexRule.satisfy(src)){
                    this.add(src);
                }
            }
        }
        return this;
    }

    public Links addByRegex(Element ele, RegexRule regexRule) {
        return addByRegex(ele, regexRule, false);
    }

    public Links addByRegex(Element ele, String regex, boolean parseSrc) {
        RegexRule regexRule = new RegexRule(regex);
        return addByRegex(ele, regexRule, parseSrc);
    }
    public Links addByRegex(Element ele, String regex) {
        RegexRule regexRule = new RegexRule(regex);
        return addByRegex(ele,regexRule,false);
    }

    public String get(int index) {
        return linkList.get(index);
    }

    public int size() {
        return linkList.size();
    }

    public String remove(int index) {
        return linkList.remove(index);
    }

    public boolean remove(String url) {
        return linkList.remove(url);
    }

    public void clear() {
        linkList.clear();
    }

    public boolean isEmpty() {

        return linkList.isEmpty();
    }

    public int indexOf(String url) {
        return linkList.indexOf(url);
    }

    @Override
    public String toString() {
        return linkList.toString();
    }

	public Iterator<String> iterator() {
		return linkList.iterator();
	}

}
