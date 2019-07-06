package com.yb.util;

import java.util.HashMap;
import java.util.Map;

public class Message extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public Message() {
		put("code", 0);
	}
	
	public static Message error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static Message error(String msg) {
		return error(500, msg);
	}
	
	public static Message error(int code, String msg) {
		Message r = new Message();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Message ok(String msg) {
		Message r = new Message();
		r.put("msg", msg);
		return r;
	}
	
	public static Message ok(Map<String, Object> map) {
		Message r = new Message();
		r.putAll(map);
		return r;
	}
	
	public static Message ok() {
		return new Message();
	}

	public Message put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}