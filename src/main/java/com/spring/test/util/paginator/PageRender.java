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
		
		int desde, hasta;
		if(totalPages <= elementsPerPage) {
			desde = 1;
			hasta = totalPages;
		} else {
			if(actualPage <= elementsPerPage/2) {
				desde = 1;
				hasta = elementsPerPage;
			} else if(actualPage >= totalPages - elementsPerPage/2 ) {
				desde = totalPages - elementsPerPage + 1;
				hasta = elementsPerPage;
			} else {
				desde = actualPage -elementsPerPage/2;
				hasta = elementsPerPage;
			}
		}
		
		for(int i=0; i < hasta; i++) {
			pages.add(new PageItem(desde + i, actualPage == desde+i));
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
