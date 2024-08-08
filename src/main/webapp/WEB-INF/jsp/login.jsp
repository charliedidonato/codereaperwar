<%@page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:directive.include file="include.jsp"/>
<!DOCTYPE html>
<html lang="en">
  <head>
    <jsp:directive.include file="empireidsheader.jsp" />
    <title>Login</title>
  </head>
<body>
	<div class="container justify-content-md-center">
		<jsp:directive.include file="empireidsnav.jsp"/> 
		<div class="card justify-content-md-center width: 30%">
			<div class="card-header bg-brand-red justify-content-md-center">
				<h2 class="card-title">Login</h2>
			</div>
			<div class="card-body justify-content-md-center"
				style="background-color: darkgray;">
		

		<c:if test="${login_error}">
			<div align="center" class="alert alert-danger">
				<p>Your login attempt was not successful, try again.</p>
				<p>Reason:<strong><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /></strong></p>
			</div>
		</c:if>

		<form:form name='loginForm' class="form-signin" action='/login' method='POST'>
		<div class="text-center mb-4">
			<h1 class="text-dark fs-2 fw-bolder">Login with your <br> Username and Password</h1>
		</div>
		<table class="table table-striped table-hover" id="viewTable">	
			<tbody>
			<tr>
		        <td align="right" class="pull-right">	
			    	<label class="text-dark fs-3 fw-bolder pull-right">Username</label>
			    </td>
			    <td>
			    	<input class="form-control" type='text' name='username' value='<c:out value="${SPRING_SECURITY_LAST_USERNAME}" /> '>
			    </td>
			    <td>
			    	<p class="text-dark fs-3 mb-0"><a href="/auth/forgotUsername" class="forgot-links">Forgot Username</a></p>
			    </td>	
			</tr>
			<tr>
			    <td align="right">				
			    	<label class="text-dark fs-3 fw-bolder" for="password">Password</label>
			    </td>
			    <td>
			    	<input class="form-control" type='password' name='password'/>
			    </td>
			    <td>
			    	<p class="text-dark fs-3 mb-0"><a href="/auth/forgotPassword">Forgot Password</a></p>
			    </td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>		
				<td align="center">
					<button class="btn btn-lg btn-danger btn-block" name="submit" type="submit" value="Submit">Sign in</button>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			</tbody>
		</table>	
		</form:form>
	</div>
    </div>
</div>    
</body>
</html>
