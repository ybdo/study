package com.yb.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
public class RRExceptionHandler {
	/**
	 * 自定义异常
	 */
	@ExceptionHandler(MyException.class)
	public Message handleRRException(MyException e){
		Message r = new Message();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}

	@ExceptionHandler(Exception.class)
	public Message handleDuplicateKeyException(Exception e){
		Message r = new Message();
		r.put("code", 500);
		r.put("msg", e.getMessage());
		return r;
	}

}
