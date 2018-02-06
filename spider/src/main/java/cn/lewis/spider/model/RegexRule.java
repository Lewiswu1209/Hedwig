package cn.lewis.spider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegexRule {
    
    public RegexRule(){
        
    }
    public RegexRule(String regex){
        addRule(regex);
    }
    public RegexRule(String... regexes){
        for(String regex:regexes){
            addRule(regex);
        }
    }
    public RegexRule(List<String> regexList){
        for (String regex : regexList) {
            addRule(regex);
        }
    }
    
    public boolean isEmpty(){
        return positive.isEmpty();
    }

    private ArrayList<String> positive = new ArrayList<String>();
    private ArrayList<String> negative = new ArrayList<String>();

    public RegexRule addRule(String rule) {
        if (rule.length() == 0) {
            return this;
        }
        char pn = rule.charAt(0);
        String realrule = rule.substring(1);
        if (pn == '+') {
            addPositive(realrule);
        } else if (pn == '-') {
            addNegative(realrule);
        } else {
            addPositive(rule);
        }
        return this;
    }

    public RegexRule addPositive(String positiveregex) {
        positive.add(positiveregex);
        return this;
    }

    public RegexRule addNegative(String negativeregex) {
        negative.add(negativeregex);
        return this;
    }

    public boolean satisfy(String str) {

        for (String nregex : negative) {
            if (Pattern.matches(nregex, str)) {
                return false;
            }
        }

        int count = 0;
        for (String pregex : positive) {
            if (Pattern.matches(pregex, str)) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        } else {
            return true;
        }

    }
}
