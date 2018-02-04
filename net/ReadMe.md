# Net

这里是网络访问的组件，目前只有一个HTTP的组件，当然，HTTPS也是将就可以的，这部分是自己封装的HTTPConnection。

可以就像这样使用：

	HttpRequest request = new HttpGet("https://www.baidu.com/");
	request.setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:36.0) Gecko/20100101 Firefox/36.0");
	/* 如果需要代理
	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
	request.setProxy(proxy);
	*/
	HttpClient client = new HttpClient(request);
	HttpResponse response = client.connect();
	response.getCode();
	response.getHtml();
	……
	client.disconnect();
