/**
 * 
 */
package com.empirestateids.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author Syed
 *
 */
public class CustomAuthenticationSuccessHandlerMobile extends SavedRequestAwareAuthenticationSuccessHandler {
	// getters and setters for injected services

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  throws ServletException, IOException {
		Device currentDevice = DeviceUtils.getCurrentDevice(request);
		if(authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				request.getSession(true).setAttribute("userInfo", principal);
			} else {
				String username = principal.toString();
			}
		}
		if (currentDevice.isMobile()){
			//String targetUrl = super.getTargetUrlParameter();
			//logger.error("TargetURL:"+targetUrl);
			//super.setAlwaysUseDefaultTargetUrl(true);
			//super.setDefaultTargetUrl(request.getContextPath() + "/m/home");
			//logger.error("Determined Target:"+super.determineTargetUrl(request, response));
			super.onAuthenticationSuccess(request, response, authentication);
			//response.sendRedirect(super.getDefaultTargetUrl());
		    return;
		}else {
		    super.onAuthenticationSuccess(request, response, authentication);
		    return;
		}  
  
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
