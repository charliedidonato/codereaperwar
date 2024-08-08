<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- ============= NAV START ============== -->
<nav class="navbar navbar-expand-lg navbar-dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="/home"><img
			src="/images/computer.png"
			width="72" height="72"></a>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto">
				<li class="nav-item"><a class="nav-link"
					href="/home">Home</a></li> 
				<li class="nav-item"><a class="nav-link"
					href="/userReg/newUserReg">Create
						Account</a></li>			
				<li class="nav-item"><a class="nav-link"
					href="/rxnorm/getRxNormCodeData">RxNorm Load</a></li>						
				<li class="nav-item"><a class="nav-link"
					href="mailto:support@empirestateids.com"><i
						class="fa fa-envelope"></i>&nbsp;Contact</a></li>
				<li class="nav-item"><a class="nav-link"
					href="/article/viewArticle?articleId=57&">Help</a></li>
			</ul>
		</div>
		<span class="ms-4 pull-right" style="min-width: 120px;">
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button> 
		</span>
		<sec:authorize access="isAnonymous()">
			<a class="btn btn-outline-light my-2 my-sm-0" id="login-btn"
				href="/auth/login" role="button">Login</a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
	    	<a class="btn btn-outline-light my-2 my-sm-0" href="/userReg/viewUserReg?userId=${sessionScope.userInfo.ipowerliftId}&">	
				Hello ${sessionScope.userInfo.firstName} &nbsp; ${sessionScope.userInfo.lastName}
	    	</a>
	    	&nbsp;
	    	<a class="btn btn-outline-light my-2 my-sm-0" href="/logout">Logout</a>&nbsp;
	 	</sec:authorize>
	 	<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a class="btn btn-outline-light my-2 my-sm-0" id="login-btn"
				href="/adminhome" role="button">Admin Home</a>
		</sec:authorize>

	</div>
</nav>
<!-- ============= NAV END ============== -->