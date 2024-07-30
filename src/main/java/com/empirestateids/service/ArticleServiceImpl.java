/**
 * 
 */
package com.empirestateids.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.empirestateids.common.IConstants;
import com.empirestateids.dao.ArticleMapper;
import com.empirestateids.domain.Article;
import com.empirestateids.domain.ArticleCriteria;
import com.empirestateids.domain.ArticleExt;
import com.empirestateids.security.UserInfo;
import com.empirestateids.utils.CommonUtil;
import com.empirestateids.utls.UtilityMethods;

/**
 * @author Syed
 *
 */
@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {

	static Logger logger = LogManager.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleMapper articleMapper;
	
//	@Autowired
//	private UtilService utilService;
	
	@Override
	public List<ArticleExt> getAllArticles() throws IOException {
		List<Article> articleList = articleMapper.selectByExample(null);
		
		List<ArticleExt> articleExtList = new ArrayList<ArticleExt>();
		
		for(Article article:articleList){
			articleExtList.add(this.getArticle(article.getArticleId()));
		}
		
		return articleExtList;
	}
	
	@Override
	public ArticleExt getArticle(Integer articleId) throws IOException {
		ArticleExt articleExt = new ArticleExt();
		
		String contentFileName = null;
		File nameFile = null;
		File dirFile = null;
		String content = null;
		
		String contextPath = null;
		String appResrcMapper = null;
		String primaryImg = null;
		
		Properties prop = new Properties();
		
		Article article = articleMapper.selectByPrimaryKey(articleId);
		
		try {
            //load a properties file
    		InputStream is = getClass().getResourceAsStream(IConstants.APP_RESRC_BUNDLE_FILE);
    		prop.load(is);
		} catch (IOException ex) {
    		logger.error("Error loading properties file", ex);
    		logger.error("Exception:" + ex.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(ex));
    		throw ex;
        }
		
    	contentFileName = article.getContentFile();
    		
    	try {
    		dirFile = new File(prop.getProperty(IConstants.APP_RESRC_LOC));
    		nameFile = new File(dirFile, contentFileName);
    		
			if(logger.isDebugEnabled()) {
				logger.debug("nameFile: " + nameFile);
			}

    		content = FileUtils.readFileToString(nameFile, IConstants.ARTICLE_ENCODING);
    	} catch (IOException ex) {
    		logger.error("Error fetching article from the file", ex);
    		logger.error("Exception:" + ex.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(ex));
    		
    		content = "Error fetching article from file";
        }
    	
		articleExt.setContent(content);
		
		// build image file path
		contextPath = prop.getProperty(IConstants.APP_CONTEXT_PATH);
    	appResrcMapper = prop.getProperty(IConstants.APP_RESRC_MAPPER);
    	
    	primaryImg = article.getPrimaryImg();
    	
    	if(primaryImg != null){
    		primaryImg = contextPath + appResrcMapper + primaryImg;
    	}
    	
    	article.setPrimaryImg(primaryImg);
    	
		articleExt.setArticle(article);
		
		return articleExt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addArticle(ArticleExt articleExt, UserInfo userInfo) throws IOException, Exception {
		Article article = articleExt.getArticle();
		
		Integer articleId = null;
		String fileName = null;
		File nameFile = null;
		File dirFile = null;
		String contentFile = null;
		String contextPath = null;
		String appResrcMapper = null;
		String primaryImg = null;
		
		Properties prop = new Properties();
		
		int status;
		
		if(article.getSource() == null) {
			article.setSource(getSource(userInfo));
		}
		
		CommonUtil.setAuditInfo(article, userInfo);
		
		//AUTOINCREMENT so set to zero
		article.setArticleId(0);
		status = articleMapper.insertSelective(article);
		if(logger.isDebugEnabled()) {
			logger.debug("Article insert status: " + status);
		}
		
		articleId = articleMapper.getLastInsertId();
		article.setArticleId(articleId);
		
		
    	try {
            //load a properties file
    		InputStream is = getClass().getResourceAsStream(IConstants.APP_RESRC_BUNDLE_FILE);
    		prop.load(is);
    		
    		fileName = "article"+articleId+".html";
    		
    		dirFile = new File(prop.getProperty(IConstants.APP_RESRC_LOC)+prop.getProperty(IConstants.APP_RESRC_ARTICLES_LOC));
    		nameFile = new File(dirFile, fileName);
    		
    		FileUtils.writeStringToFile(nameFile,  articleExt.getContent(), IConstants.ARTICLE_ENCODING);
    		
    	} catch (IOException ex) {
    		logger.error("Error while prepping to save article", ex);
    		logger.error("Exception:" + ex.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(ex));
    		throw ex;
        }
    	
    	contextPath = prop.getProperty(IConstants.APP_CONTEXT_PATH);
    	appResrcMapper = prop.getProperty(IConstants.APP_RESRC_MAPPER);
    	
    	primaryImg = article.getPrimaryImg();
    	
    	if(primaryImg != null){
    		primaryImg = primaryImg.replaceFirst(contextPath, "");
    		primaryImg = primaryImg.replaceFirst(appResrcMapper, "");
    	}
    	
    	article.setPrimaryImg(primaryImg);
    	
    	contentFile = prop.getProperty(IConstants.APP_RESRC_ARTICLES_LOC) + System.getProperty("file.separator") + fileName;
		article.setContentFile(contentFile);
		
		status = articleMapper.updateByPrimaryKeySelective(article);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Article update status: " + status);
		}
	}

	private int getSource(UserInfo userInfo) {
		int source = IConstants.SOURCE_ATLAS;
		
		return source;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateArticle(ArticleExt articleExt, UserInfo userInfo) throws IOException, Exception {
		Article article = articleExt.getArticle();
		
		Integer articleId = null;
		String fileName = null;
		File nameFile = null;
		File dirFile = null;
		String contentFile = null;
		String contextPath = null;
		String appResrcMapper = null;
		String primaryImg = null;
		
		Properties prop = new Properties();
		
		int status;
		
		CommonUtil.setAuditInfo(article, userInfo, false, true);
		
		articleId = article.getArticleId();
		
    	try {
            //load a properties file
    		InputStream is = getClass().getResourceAsStream(IConstants.APP_RESRC_BUNDLE_FILE);
    		prop.load(is);
    		
    		fileName = "article"+articleId+".html";
    		
    		dirFile = new File(prop.getProperty(IConstants.APP_RESRC_LOC)+prop.getProperty(IConstants.APP_RESRC_ARTICLES_LOC));
    		nameFile = new File(dirFile, fileName);
    		
    		FileUtils.writeStringToFile(nameFile,  articleExt.getContent(), IConstants.ARTICLE_ENCODING);
    		
    	} catch (IOException ex) {
    		logger.error("Error while prepping to save article", ex);
    		logger.error("Exception:" + ex.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(ex));
    		throw ex;
        }
    	
    	contextPath = prop.getProperty(IConstants.APP_CONTEXT_PATH);
    	appResrcMapper = prop.getProperty(IConstants.APP_RESRC_MAPPER);
    	
    	primaryImg = article.getPrimaryImg();
    	
    	if(primaryImg != null){
    		primaryImg = primaryImg.replaceFirst(contextPath, "");
    		primaryImg = primaryImg.replaceFirst(appResrcMapper, "");
    	}
    	
    	article.setPrimaryImg(primaryImg);
    	
    	contentFile = prop.getProperty(IConstants.APP_RESRC_ARTICLES_LOC) + System.getProperty("file.separator") + fileName;
		article.setContentFile(contentFile);
		
		status = articleMapper.updateByPrimaryKeySelective(article);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Article update status: " + status);
		}
	}

	@Override
	public List<ArticleExt> getLatestArticles() throws IOException {
		ArticleCriteria criteria = new ArticleCriteria();
		criteria.setOrderByClause("created desc");
		
		List<Article> articleList = articleMapper.selectByExampleWithRowbounds(criteria, new RowBounds(RowBounds.NO_ROW_OFFSET, 20));
		
		List<ArticleExt> articleExtList = new ArrayList<ArticleExt>();
		
		String contextPath = null;
		String appResrcMapper = null;
		String primaryImg = null;
		
		Properties prop = new Properties();
		
		try {
            //load a properties file
    		InputStream is = getClass().getResourceAsStream(IConstants.APP_RESRC_BUNDLE_FILE);
    		prop.load(is);
		} catch (IOException ex) {
    		logger.error("Error loading properties file", ex);
    		logger.error("Exception:" + ex.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(ex));
    		throw ex;
        }
		
		for(Article article:articleList){
			ArticleExt articleExt = new ArticleExt();
			
			
			// build image file path
			contextPath = prop.getProperty(IConstants.APP_CONTEXT_PATH);
	    	appResrcMapper = prop.getProperty(IConstants.APP_RESRC_MAPPER);
	    	
	    	primaryImg = article.getPrimaryImg();
	    	
	    	if(primaryImg != null){
	    		primaryImg = contextPath + appResrcMapper + primaryImg;
	    	}
	    	
	    	article.setPrimaryImg(primaryImg);
			
			articleExt.setArticle(article);
			articleExtList.add(articleExt);
		}
		
		return articleExtList;
	}
	
}
