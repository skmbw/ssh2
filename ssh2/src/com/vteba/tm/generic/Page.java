package com.vteba.tm.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页需要提供3个主要的参数，1：页码pageNo，构造时设置；2：总记录数totalRecordCount，查询DB获得；
 * 3：数据result，查询DB获得。pageSize也可设置，否则使用默认值。
 * @author skmbw
 * @param <T> page所封装的对象的类型
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -2847769622362401350L;
	private static int DEFAULT_PAGE_SIZE = 10;// 每页默认的容量大小
	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的容量
	private List<T> result;// 当前页中存放的记录，类型一般为ArrayList
	private long totalRecordCount; // 总记录数
	private int pageNo = 1;// 某一页序号
	private String orderBy;//排序字段
	private String ascDesc;//升序或降序
	/**
	 * 默认构造方法，构造空页。
	 */
	public Page() {
		this(1, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 构造函数。
	 * @param pageNo 本页序号
	 * @param totalRecordCount 数据库中总记录条数
	 * @param pageSize 每页容量
	 * @param result 每页包含的数据
	 */
	public Page(int pageNo, long totalRecordCount, int pageSize, List<T> result) {
		this.pageNo = pageNo;
		this.totalRecordCount = totalRecordCount;
		this.pageSize = pageSize;
		this.result = result;
	}

	/**
	 * 获得某一页序号
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置某一页序号
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 设置总记录数
	 */
	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * 获取总记录数
	 */
	public long getTotalRecordCount() {
		return this.totalRecordCount;
	}

	/**
	 * 设置每页数据容量
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取每页数据容量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置当前页中的记录
	 */
	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 获取当前页中的记录
	 */
	public List<T> getResult() {
		return result;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getAscDesc() {
		return ascDesc;
	}

	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}

	/**
	 * utils，获取总页数
	 */
	public long getTotalPageCount() {
		if (totalRecordCount % pageSize == 0) {
			return totalRecordCount / pageSize;
		} else {
			return totalRecordCount / pageSize + 1;
		}
	}
	
	/**
	 * utils，获取当前页第一条数据在数据库中的起始位置，从0开始，pageSize默认。
	 */
	public int getStartIndex() {
		return this.getStartIndex(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * utils，获取第pageNo页第一条数据在数据库中的起始位置，从0开始，pageSize默认。
	 * @param pageNo 第几页页码
	 */
	public int getStartIndex(int pageNo) {
		return this.getStartIndex(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * utils，获取第pageNo页第一条数据在数据库中的起始位置，从0开始。
	 * @param pageNo 第几页页码
	 * @param pageSize 每页容量
	 */
	public int getStartIndex(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * utils，获取某一记录在第几页，序号从1开始，pageSize默认
	 * @param startIndex 数据在DB中的索引
	 * @return 索引为startIndex的记录在第几页
	 */
	public int getCurrentPageNo(int startIndex){
		return this.getCurrentPageNo(startIndex, DEFAULT_PAGE_SIZE);
	}
	/**
	 * utils，获取某一记录在第几页，序号从1开始。
	 * @param startIndex 数据在DB中的索引
	 * @param pageSize 每页容量
	 */
	public int getCurrentPageNo(int startIndex,int pageSize) {
		return startIndex / pageSize + 1;
	}

	/**
	 * utils，该页是否有下一页。
	 */
	public boolean hasNextPage() {
		return this.getPageNo() < this.getTotalPageCount();
	}

	/**
	 * utils，取得下一页的页号，序号从1开始。当前页为尾页时仍返回尾页序号。
	 */
	public int getNextPage() {
		if (hasNextPage()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * utils，该页是否有上一页。
	 */
	public boolean hasPreviousPage() {
		return this.getPageNo() > 1;
	}

	/**
	 * utils，取得上一页的页号，序号从1开始。当前页为首页时返回首页序号。
	 */
	public int getPreviousPage() {
		if (hasPreviousPage()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}
}
