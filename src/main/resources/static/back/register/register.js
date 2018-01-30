//var path = /*[[${#httpServletRequest.getScheme()
//					+ "://" + #httpServletRequest.getServerName()
//					+ ":" + #httpServletRequest.getServerPort()
//					+ #httpServletRequest.getContextPath()}]]*/
//获取当前网址，如： http://localhost:8088/test/test.jsp
var curPath = window.document.location.href;
//获取主机地址之后的目录，如： test/test.jsp
var pathName = window.document.location.pathname;
var pos = curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8088
var localhostPath = curPath.substring(0, pos);
//获取带"/"的项目名，如：/test
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
//    注册提交
/*
ajax其它属性参考
参考网址：http://blog.csdn.net/maxoracle/article/details/51064756
15.global：
要求为Boolean类型的参数，默认为true。表示是否触发全局ajax事件。设置为false将不会触发全局ajax事件，
ajaxStart或ajaxStop可用于控制各种ajax事件。
16.ifModified：
要求为Boolean类型的参数，默认为false。仅在服务器数据改变时获取新数据。服务器数据改变判断的依据是Last-Modified头信息。
默认值是false，即忽略头信息。
17.jsonp：
要求为String类型的参数，在一个jsonp请求中重写回调函数的名字。
该值用来替代在"callback=?"这种GET或POST请求中URL参数里的"callback"部分，
例如{jsonp:'onJsonPLoad'}会导致将"onJsonPLoad=?"传给服务器。
18.username：
要求为String类型的参数，用于响应HTTP访问认证请求的用户名。
19.password：
要求为String类型的参数，用于响应HTTP访问认证请求的密码。
20.processData：
要求为Boolean类型的参数，默认为true。
默认情况下，发送的数据将被转换为对象（从技术角度来讲并非字符串）以配合默认内容类型"application/x-www-form-urlencoded"。
如果要发送DOM树信息或者其他不希望转换的信息，请设置为false。
21.scriptCharset：
要求为String类型的参数，只有当请求时dataType为"jsonp"或者"script"，并且type是GET时才会用于强制修改字符集(charset)。
通常在本地和远程的内容编码不同时使用。
*/
$("#register_submit").click(function(){
    $.ajax({
//        请求地址
        url:localhostPath+"/register/register",
//            请求类型
        type:"post",
//            超时时间（毫秒）
        timeout:1000,
//            同步异步类型
        async:false,
//            是否从缓存中加载
        cache:false,
//            编码类型
        contentType:"application/json",
//            参数
        data:$("#register_from").serialize(),
//            返回类型
        dataType:"json",
//            要求为Function类型的参数，发送请求前可以修改XMLHttpRequest对象的函数，例如添加自定义HTTP头。
//            在beforeSend中如果返回false可以取消本次ajax请求。XMLHttpRequest对象是惟一的参数。
        beforeSend: function(XMLHttpRequest){
//                          this;   //调用本次ajax请求时传递的options参数
                        alert("beforeSend");
                   },
//                       请求成功
        success:function(data){
                    //data可能是xmlDoc、jsonObj、html、text等等
//                    this;  //调用本次ajax请求时传递的options参数
                    alert("success");
                 },
//                     请求失败
        error:function(XMLHttpRequest, textStatus, errorThrown){
                //通常情况下textStatus和errorThrown只有其中一个包含信息
//                this;   //调用本次ajax请求时传递的options参数
                alert(XMLHttpRequest);
             },
//                 成功与否都会调用
        complete:function(XMLHttpRequest, textStatus){
//                      this;    //调用本次ajax请求时传递的options参数
                        alert("complete");
                   },
//要求为Function类型的参数，给Ajax返回的原始数据进行预处理的函数。
//提供data和type两个参数。
//data是Ajax返回的原始数据，type是调用jQuery.ajax时提供的dataType参数。
//函数返回的值将由jQuery进一步处理。
        dataFilter:function(data, type){
                       //返回处理后的数据
                       return data;
                   }
    });
});