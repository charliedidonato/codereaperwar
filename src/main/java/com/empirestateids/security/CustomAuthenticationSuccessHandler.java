package com.empirestateids.security;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Syed
 * @author DiDonato
 *
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	// getters and setters for injected services
	static Logger logger = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);
	
	
	public CustomAuthenticationSuccessHandler() {
		super();
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		if (authentication!=null) {
		    logger.error("Authentication:"+authentication.toString());
		}
		
		if(authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				request.getSession(true).setAttribute("userInfo", principal);
			    logger.error("Auth success:"+principal.toString());
			} else {
				String username = principal.toString();
			}
			
		}
		String uri = "/home";
		logger.error("URI after auth:"+uri);
		
		SimpleUrlAuthenticationSuccessHandler handler = new  SimpleUrlAuthenticationSuccessHandler("/home");
		handler.onAuthenticationSuccess(request, response, authentication);
		return;
	}
}

/*
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();

    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) throws ServletException, IOException{
    	if(authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				request.getSession(true).setAttribute("userInfo", principal);
			} else {
				String username = principal.toString();
			}
		}
        target.onAuthenticationSuccess(request, response, authentication);
    }

    public void proceed(HttpServletRequest request, 
        HttpServletResponse response, Authentication auth) throws ServletException, IOException{
        target.onAuthenticationSuccess(request, response, auth);
    }
}
*/
