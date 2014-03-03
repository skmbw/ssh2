package com.googlecode.hibernatedao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author libinsong1204@gmail.com
 */
@SuppressWarnings("serial")
public class Pagination<E> implements Serializable, Iterable<E> {

	protected List<E> result;

	protected int limit;

	protected long pageNumber;

	protected long totalCount = 0;

	public Pagination(long pageNumber, int limit, long totalCount) {
		this(pageNumber, limit, totalCount,new ArrayList<E>(0));
	}

	public Pagination(long pageNumber, int limit, long totalCount,List<E> result) {
		if(limit <= 0) throw new IllegalArgumentException("[pageSize] must great than zero");
		this.limit = limit;
		this.pageNumber = pageNumber;
		this.totalCount = totalCount;
		setResult(result);
	}

	public void setResult(List<E> elements) {
		if (elements == null)
			throw new IllegalArgumentException("'result' must be not null");
		this.result = elements;
	}

	/**
     * 每一页显示的条目数
     *
     * @return 每一页显示的条目数
     */
	public int getLimit() {
		return limit;
	}

	/**
	 * 当前页包含的数据
	 *
	 * @return 当前页数据源
	 */
	public List<E> getResult() {
		return result;
	}

	/**
	 * 得到数据库的第一条记录号
	 * @return
	 */
	public long getFirstResult() {
		return pageNumber * limit;
	}

	/**
	 * 总的数据条目数量，0表示没有数据
	 *
	 * @return 总数量
	 */
	public long getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 总的页数
	 *
	 * @return 总页数
	 */
	public long getPageNumber() {
		return pageNumber;
	}

	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		return (Iterator<E>) (result == null ? Collections.emptyList().iterator() : result.iterator());
	}
}
