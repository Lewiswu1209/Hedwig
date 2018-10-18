package cn.lewis.spider.crawler;

import cn.edu.hfut.dmic.webcollector.fetcher.Executor;
import cn.edu.hfut.dmic.webcollector.fetcher.Visitor;
import cn.edu.hfut.dmic.webcollector.fetcher.VisitorMethodDispatcher;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.Requester;
import cn.edu.hfut.dmic.webcollector.util.ConfigurationUtils;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;
import cn.lewis.spider.requester.HttpRequester;

public abstract class Crawler extends cn.edu.hfut.dmic.webcollector.crawler.Crawler implements Executor, Visitor{

    /**
     * 是否自动抽取符合正则的链接并加入后续任务
     */
    protected boolean autoParse = true;
    protected Visitor visitor;
    //protected Requester requester;

    protected VisitorMethodDispatcher visitorMethodDispatcher;

    public Crawler(boolean autoParse) {
        this.autoParse = autoParse;
        //this.requester = new HttpRequester();
        this.visitor = this;
        this.executor = this;
    }

    @Override
    public void start(int depth) throws Exception {
        this.visitorMethodDispatcher = new VisitorMethodDispatcher(visitor, autoParse, regexRule);
        ConfigurationUtils.setTo(this, this.visitorMethodDispatcher);
        super.start(depth);
    }

    @Override
    protected void registerOtherConfigurations() {
        super.registerOtherConfigurations();
        //ConfigurationUtils.setTo(this, requester);
        ConfigurationUtils.setTo(this, visitor);
    }

    /**
     * URL正则约束
     */
    protected RegexRule regexRule = new RegexRule();

    @Override
    public void execute(CrawlDatum datum, CrawlDatums next) throws Exception {
        Page page = getRequester().getResponse(datum);
        visitorMethodDispatcher.dispatch(page, next);
    }

    /**
     * 添加URL正则约束
     *
     * @param urlRegex URL正则约束
     */
    public void addRegex(String urlRegex) {
        regexRule.addRule(urlRegex);
    }

    /**
     *
     * @return 返回是否自动抽取符合正则的链接并加入后续任务
     */
    public boolean isAutoParse() {
        return autoParse;
    }

    /**
     * 设置是否自动抽取符合正则的链接并加入后续任务
     *
     * @param autoParse 是否自动抽取符合正则的链接并加入后续任务
     */
    public void setAutoParse(boolean autoParse) {
        this.autoParse = autoParse;
    }

    /**
     * 获取正则规则
     *
     * @return 正则规则
     */
    public RegexRule getRegexRule() {
        return regexRule;
    }

    /**
     * 设置正则规则
     *
     * @param regexRule 正则规则
     */
    public void setRegexRule(RegexRule regexRule) {
        this.regexRule = regexRule;
    }

    /**
     * 获取Visitor
     *
     * @return Visitor
     */
    public Visitor getVisitor() {
        return visitor;
    }

    public Requester getRequester() {
    	HttpRequester requester = new HttpRequester();
    	ConfigurationUtils.setTo(this, requester);
    	return requester;
    }
}
