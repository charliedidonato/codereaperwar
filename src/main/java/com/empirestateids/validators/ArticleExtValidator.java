package com.empirestateids.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.empirestateids.domain.ArticleExt;

/**
 * @author Syed
 *
 */
@Component("ArticleExtValidator")
public class ArticleExtValidator implements Validator {
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ArticleExt.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "content", "article.content.empty", "Article Content empty");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "article.title", "article.title.empty", "Article Title empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "article.author", "article.author.empty", "Article Author empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "article.outline", "article.outline.empty", "Article Outline empty");
		
		ArticleExt articleExt = (ArticleExt) obj;
		
		if ( ! e.hasFieldErrors("article.outline")) {
			if(!articleExt.getArticle().getOutline().isEmpty() && articleExt.getArticle().getOutline().length()>400){
				e.rejectValue("article.outline", "article.outline.oversize", "Article Outline cannot exceed 400");
			}
		}
				
	}

}
