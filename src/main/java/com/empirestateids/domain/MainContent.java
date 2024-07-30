/**
 * 
 */
package com.empirestateids.domain;

import java.io.Serializable;

/**
 * @author Syed
 * @author DiDonato
 */
public class MainContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArticleExt mainArticle;
	private ArticleExt subArticle;
	
	/**
	 * @return the mainArticle
	 */
	public ArticleExt getMainArticle() {
		return mainArticle;
	}
	/**
	 * @param mainArticle the mainArticle to set
	 */
	public void setMainArticle(ArticleExt mainArticle) {
		this.mainArticle = mainArticle;
	}
	/**
	 * @return the subArticle
	 */
	public ArticleExt getSubArticle() {
		return subArticle;
	}
	/**
	 * @param subArticle the subArticle to set
	 */
	public void setSubArticle(ArticleExt subArticle) {
		this.subArticle = subArticle;
	}

}
