/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.googlecode.hibernatedao.jdbc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author   bsli@starit.com.cn
 * @Date	 2011-6-14 下午01:41:33
 */
public class CustomSQLUtil {
	private static Logger logger = LoggerFactory.getLogger(CustomSQLUtil.class);

	private static CustomSQLUtil _instance = new CustomSQLUtil();

	private CustomSQL _customSQL;
	
	private CustomSQLUtil() {
		try {
			_customSQL = new CustomSQL();
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}

	public static String appendCriteria(String sql, String criteria) {
		return _instance._customSQL.appendCriteria(sql, criteria);
	}

	public static String get(String id) {
		return _instance._customSQL.get(id);
	}
	
	public static String get(String id, Map<String, Object> models) {
		return _instance._customSQL.get(id, models);
	}
}