/**
 * db泛型接口抽象和包装.mongodb之所以不继承SimpleMongoRepository，是因为，spring代理扫描的时候，扫描到了MongoGenericDaoImpl，
 * 但是没有默认构造函数出错。两种解决办法，1、移到其他的包，2、考出来，放到MongoGenericDaoImpl中，增加默认构造函数。
 */
/**
 * @author yinlei
 * @date 2013年10月19日 上午12:12:54
 */
package com.vteba.tm.generic.impl;