/**
 * 
 */
package com.empirestateids.service;

import java.io.IOException;
import java.util.List;

import com.empirestateids.domain.ArticleExt;
import com.empirestateids.security.UserInfo;

/**
 * @author Syed
 *
 */
public interface ArticleService {
	public List<ArticleExt> getAllArticles() throws IOException;
	
	public ArticleExt getArticle(Integer articleId) throws IOException;

	public void addArticle(ArticleExt articleExt, UserInfo userInfo) throws IOException, Exception;

	public void updateArticle(ArticleExt articleExt, UserInfo userInfo) throws IOException, Exception;

	public List<ArticleExt> getLatestArticles() throws IOException;
}
