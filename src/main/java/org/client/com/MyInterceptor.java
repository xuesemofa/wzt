package org.client.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//stop
public class MyInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        logger.info(">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");

//        获取ip
        String addr = request.getRemoteAddr();
//        获取端口号
        int port = request.getRemotePort();
//        获取请求地址
        String path = request.getServletPath();

        //设置3秒钟跳转
//            response.setHeader("refresh", "3;url=/day09_00_HttpServletResponse/servlet/demo6");
//        账号
//        MemberModel login = (MemberModel) request.getSession().getAttribute("user");
        logger.info("addr:" + addr + "-" + "port:" + port + "-" + "path:" + path);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String views = modelAndView == null ? "" : modelAndView.getViewName();
        logger.info("进入：" + views);
//        logger.info(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("返回prot:" + response.getStatus());
//        logger.info(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

}