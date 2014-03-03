package com.vteba.test;

import java.util.HashMap;
import java.util.Map;

public class TestMap {

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("template_", "好家伙，Hello。");
		params.put("destination_", "yinlei@eecn.com.cn");
		params.put("servermail", "kefu@eecn.com.cn");
		
		String[] toMails = {"zhenshi@eecn.com.cn", "hao@eecn.com.cn"};
		
		for (String mail : toMails) {
			params.put("destination_", mail);
			params.put("subject_", "好家伙啊，" + mail);
			send(params);
		}

	}

	public static void send(Map<String, Object> params) {
		System.out.println(params);
	}
}
