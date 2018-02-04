# BeanUtils

这个BeanUtils是对apache的commons-beanutils的封装，解决了BeanUtils.describe无视Convertor的问题。

开始发现commons-beanutils的BeanUtils.describe不能按照需要的格式转化java.util.Date, 那要他还有啥用，提到前台格式又不对。

后来百度发现，原来是ConverterUtilsBean的convert的函数无视了注册的Convertor，就Override了这个。

再后来……发现commons-beanutils里有BeanUtilsBean2和ConverterUtilsBean2这两个类，其实是解决了这个问题的，但是不知道为啥没有启用，于是我就又写了个BeanUtils和ConverterUtils。

这下就可以……

	DateConverter converter = new DateConverter("yyyy-MM-dd");
	ConvertUtils.register(converter, Date.class);
	BeanUtils.describe(bean);
	……

瞬间感觉清静了许多，只是不知道为啥commons-beanutils只是写了BeanUtilsBean2和ConverterUtilsBean2，而没有……
