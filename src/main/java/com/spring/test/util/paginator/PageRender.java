package com.spring.test.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	private int totalPages;
	private int elementsPerPage;
	private int actualPage;
	private List<PageItem> pages;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		
		elementsPerPage = page.getSize();
		totalPages = page.getTotalPages();
		actualPage = page.getNumber() + 1;
		
		int from, to;
		
		if(totalPages <= elementsPerPage) {
			from = 1;
			to = totalPages;
		}else {
			if(actualPage <= elementsPerPage / 2) {
				from = 1;
				to = elementsPerPage;
			}else if(actualPage >= totalPages - elementsPerPage / 2) {
				from = totalPages - elementsPerPage + 1;
				to = elementsPerPage;
			}else {
				from = actualPage - elementsPerPage / 2;
				to = elementsPerPage;
			}
		}
		
		for(int i=0; i<to;i++) {
			pages.add(new PageItem(from + 1,actualPage == from+i));
		}
		
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getActualPage() {
		return actualPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
}
