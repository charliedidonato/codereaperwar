/**
 * 
 */
package com.empirestateids.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.empirestateids.utls.UtilityMethods;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Syed
 * 
 */
public class ChangePasswordFilter extends OncePerRequestFilter implements
		Filter, InitializingBean {
	protected final String ERRORS_KEY = "errors";
	protected String changePasswordKey = "user.must.change.password";

	private Log logger = LogFactory.getLog(getClass());

	private String changePasswordUrl = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	public void  doFilterIntenal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		UserInfo userInfo = null;
		String requestURL = request.getRequestURL().toString();
		if (!requestURL.endsWith("css") || !requestURL.endsWith("js") || !requestURL.endsWith("jpg")) {
			if(logger.isDebugEnabled()){
				logger.debug("changepasswordfilter URL: " + request.getRequestURL());
				logger.debug("changepasswordfilter URI: " + request.getRequestURI());
			}
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
				
				if(authentication != null){
					Object obj = authentication.getPrincipal();
	
					if (obj instanceof UserDetails) {
						userInfo = (UserInfo) obj;
					} else {
					}
	
					if (userInfo != null && userInfo.isPasswordReset() == true) {
						// send user to change password page
						if(logger.isInfoEnabled()){
							logger.info("credentials expired - sending to changepassword page.");
						}
						
						/*
						int pos = requestURL.indexOf(changePasswordUrl);
						if (pos == -1) {
							saveError(request, changePasswordKey);
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
						*/
						
						if(!requestURL.matches(".*changePassword|.*saveChangePassword|.*j_spring_security_logout")){
							saveError(request, changePasswordKey);
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
					}
				}
			} catch (Exception e) {
				logger.error("Exception in ChangePasswordFilter: " + e.getMessage());
				logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			}
		}
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(changePasswordUrl, "changePasswordUrl must be set.");
		Assert.notNull(changePasswordKey, "changePasswordKey must be set.");
	}

	/**
	 * The URL to the Change Password page. It must begin with a slash and
	 * should be relative from the application's contextPath root (ex:
	 * /changepassword.do).
	 * 
	 * @param changePasswordUrl
	 *            the changePasswordUrl to set
	 */
	public void setChangePasswordUrl(String changePasswordUrl) {
		this.changePasswordUrl = changePasswordUrl;
	}

	/**
	 * Allow subclasses to modify the redirection message.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param url
	 *            the URL to redirect to
	 * 
	 * @throws IOException
	 *             in the event of any failure
	 */
	protected void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = request.getContextPath() + url;
		}

		response.sendRedirect(response.encodeRedirectURL(url));
	}

	public void saveError(HttpServletRequest request, String msg) {
		Set errors = (Set) request.getSession().getAttribute(ERRORS_KEY);

		if (errors == null) {
			errors = new HashSet();
		}

		errors.add(msg);
		request.getSession().setAttribute(ERRORS_KEY, errors);
	}

	/**
	 * The message bundle key that will hold the "You must change your password"
	 * error message. The default key name is <b>user.must.change.password</b>.
	 * 
	 * @param changePasswordKey
	 *            the changePasswordKey to set
	 */
	public void setChangePasswordKey(String changePasswordKey) {
		this.changePasswordKey = changePasswordKey;
	}

	@Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException {
		
		UserInfo userInfo = null;
		String requestURL = request.getRequestURL().toString();
		if (!requestURL.endsWith("css") || !requestURL.endsWith("js") || !requestURL.endsWith("jpg")) {
			if(logger.isDebugEnabled()){
				logger.debug("changepasswordfilter URL: " + request.getRequestURL());
				logger.debug("changepasswordfilter URI: " + request.getRequestURI());
			}
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
				
				if(authentication != null){
					Object obj = authentication.getPrincipal();
	
					if (obj instanceof UserDetails) {
						userInfo = (UserInfo) obj;
					} else {
					}
	
					if (userInfo != null && userInfo.isPasswordReset() == true) {
						// send user to change password page
						if(logger.isInfoEnabled()){
							logger.info("credentials expired - sending to changepassword page.");
						}
						
						/*
						int pos = requestURL.indexOf(changePasswordUrl);
						if (pos == -1) {
							saveError(request, changePasswordKey);
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
						*/
						
						if(!requestURL.matches(".*changePassword|.*saveChangePassword|.*j_spring_security_logout")){
							saveError(request, changePasswordKey);
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
					}
				}
			} catch (Exception e) {
				logger.error("Exception in ChangePasswordFilter: " + e.getMessage());
				logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
			}
		}
		
		filterChain.doFilter(request, response);

	}
}