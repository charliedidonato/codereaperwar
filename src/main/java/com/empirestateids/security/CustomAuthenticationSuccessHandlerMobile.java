/**
 * 
 */
package com.empirestateids.security;

import java.io.IOException;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Syed
 *
 */
public class CustomAuthenticationSuccessHandlerMobile extends SavedRequestAwareAuthenticationSuccessHandler {
	// getters and setters for injected services

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  throws ServletException, IOException {
		if(authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				request.getSession(true).setAttribute("userInfo", principal);
			} else {
				String username = principal.toString();
			}
		}
		super.onAuthenticationSuccess(request, response, authentication);
  
	}
}


//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    private AuthenticationSuccessHandler target = new SavedRequestAwareAuthenticationSuccessHandler();
//
//    public void onAuthenticationSuccess(HttpServletRequest request,
//        HttpServletResponse response, Authentication authentication) throws ServletException, IOException{
//    	if(authentication != null){
//			Object principal = authentication.getPrincipal();
//			if (principal instanceof UserDetails) {
//				request.getSession(true).setAttribute("userInfo", principal);
//			} else {
//				String username = principal.toString();
//			}
//		}
//        target.onAuthenticationSuccess(request, response, authentication);
//    }
//
//    public void proceed(HttpServletRequest request, 
//        HttpServletResponse response, Authentication auth) throws ServletException, IOException{
//        target.onAuthenticationSuccess(request, response, auth);
//    }
    
//}

