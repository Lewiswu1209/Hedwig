package cn.lewis.spider.conf;

public class Config {
    public static String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0";
    public static int THREAD_KILLER = 1000 * 60 * 2;
    public static int WAIT_THREAD_END_TIME = 1000 * 60;
    public static int MAX_REDIRECT = 2;
    public static int TIMEOUT_CONNECT = 3000;
    public static int TIMEOUT_READ = 10000;
    public static int TOP_N = 0;
    public static int EXECUTE_INTERVAL = 1000;
    public static boolean AUTO_DETECT_IMG = false;
}
