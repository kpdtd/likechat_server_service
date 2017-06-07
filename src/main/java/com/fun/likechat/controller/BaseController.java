package com.fun.likechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fun.likechat.util.LogFactory;

public abstract class BaseController {
	
	private static final Logger logger = LogFactory.getInstance().getLogger();
	    
	    protected HttpServletRequest request;
	    protected HttpServletResponse response;
	    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    
	/**
	 * 打印错误信息
	 * @param result - BindingResult
	 */
	protected void printError(BindingResult result){
		List<FieldError> fes = result.getFieldErrors();
		if(fes != null && !fes.isEmpty()) {
			for(FieldError fe : fes){
				logger.info("FieldError : "+fe.getField()+" :"+fe.getDefaultMessage());
			}
		}
	}
	
	/**
	 * 错误页面
	 * @param msg - 内容
	 * @return String
	 */
	protected String errorPage(String msg , Model model) {
		//model.addAttribute("title", title);
		model.addAttribute("msg", msg);
		return "error";
	}

    /**
     * 将数据信息写到客户端
     * @param resStr
     */
    protected void writerToClient(String respData) {
        logger.debug("页面数据"+respData);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(respData);
//            writer.flush();//应该不用，spring会自动做。印象中加了这个在大文件的时候好像会有问题
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
