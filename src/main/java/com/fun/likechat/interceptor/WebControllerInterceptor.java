package com.fun.likechat.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fun.likechat.constant.ErrCodeEnum;
import com.fun.likechat.util.AESCrypto;
import com.fun.likechat.util.JsonHelper;
import com.fun.likechat.util.LogFactory;
import com.fun.likechat.util.SeqIdGenerator;



public class WebControllerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LogFactory.getInstance().getLogger();

    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链, 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        MDC.put("seqID", SeqIdGenerator.generate()+request.getRequestURI()); // seqID
        // 此处必须重置线程变量
        BeatContext context = new BeatContext();
        RequestUtils.bindBeatContextToCurrentThread(context);
        context.setRequest(request);
        context.setResponse(response);
        System.out.println("@@@@@@@"+request.getHeader("userId"));
        System.out.println("@@@@@@@"+ request.getHeader("openId"));
        if(request.getHeader("userId") != null && !"".equals(request.getHeader("userId"))) {
        	context.setUserid(Integer.parseInt(request.getHeader("userId")));
        }
        if(request.getHeader("openId") != null && !"".equals(request.getHeader("openId"))) {
        	context.setOpenId(request.getHeader("openId"));
        }
        context.setRequestBody(null);
        String noSign = request.getParameter("noEncode");

        // 验证信息
        boolean isSign = false;
        boolean isLog = true;
       //HeaderData header = null;
        Method method = null;
        boolean isWeb = false;// 是否是web访问
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            method = handlerMethod.getMethod();
            // 设置context method
            context.setMethod(method);
            context.setClazz(handlerMethod.getBeanType());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            writerError(request, response, isSign, ErrCodeEnum.error.getCode(), method, isWeb);
            return false;
        }
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        RequestUtils.remove();
    }

    /**
     * 此方法在Controller方法return的时候开始完成，此方法是异步执行的，只有等到该方法执行完成，才会触发postHandle方法
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        // super.afterConcurrentHandlingStarted(request, response, handler);
    }

    /**
     * 输出错误信息
     * 
     * @param request
     *            - HttpServletRequest
     * @param response
     *            - HttpServletResponse
     * @param isSign
     *            - 是否加密
     * @param code
     *            - 错误码
     * @throws IOException
     */
    private void writerError(HttpServletRequest request, HttpServletResponse response,
            boolean isSign, int code, Method method, boolean isWeb) throws IOException {
        String err = JsonHelper.toJson(ActionResult.fail(code));

        StringBuilder buf = new StringBuilder();
        buf.append(request.getRequestURI()).append("|");
        if (method != null) {
            buf.append(method.getDeclaringClass().getName()).append("|");
            buf.append(method.getName()).append("|");
        }
        buf.append(err);
        logger.info(buf.toString());
        if (isWeb) {
            response.sendRedirect("/error?s="
                    + URLEncoder.encode(ErrCodeEnum.getDescByCode(code), "UTF-8"));
        } else {
            response.getWriter().print(isSign ? AESCrypto.encrypt(err) : err);
        }
    }

}
