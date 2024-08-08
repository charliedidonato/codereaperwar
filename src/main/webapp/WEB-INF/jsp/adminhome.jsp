<%@ page language="java" isELIgnored="false" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:directive.include file="include.jsp"/>
<!DOCTYPE html>
<html>
<head>
	<title>Code Reaper Admin Home</title>
	<meta charset="UTF-8">
	<meta name="description" content="Healthcare Codeset Update Tool">
	<meta name="keywords" content="LOINC,SNOMED,CPT,RxNorm,ICD10, CVX Vaccine">
	<meta name="author" content="Charlie DiDonato">
	<link type="text/css" href="/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />

<!--========== CSS Includes =========-->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
	integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
	crossorigin="anonymous">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
<link rel="icon" type="image/x-icon" href="/pegasus/images/favicon.png">
<link href="/css/whyilift.css" rel="stylesheet">
<link href="/css/nav.css" rel="stylesheet">
<link href="/css/main.css" rel="stylesheet">
<!--========== CSS Includes =========-->
<!--========== js include =========-->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/pegasus/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/pegasus/js/jquery.tablesorter.js"></script>
<script>
	$(document).ready(function() {
		$("#listTable1").tablesorter({
			sortList : [ [ 14, 1 ], [ 1, 0 ] ]
		});
		$("#listTable2").tablesorter({
			sortList : [ [ 14, 1 ], [ 1, 0 ] ]
		});
		$("#listTable3").tablesorter({
			sortList : [ [ 14, 1 ], [ 1, 0 ] ]
		});
		$("#listTable4").tablesorter({
			sortList : [ [ 14, 1 ], [ 1, 0 ] ]
		});
	});
</script>

<script>
	document.addEventListener("DOMContentLoaded", function(event) {
		const tooltipTriggerList = [].slice.call(document
				.querySelectorAll('[data-bs-toggle="tooltip"]'))
		const tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
			return new bootstrap.Tooltip(tooltipTriggerEl)
		});

	});

	function launchModalSingle(id) {
		//alert(id);
		const detailsModal = document.getElementById(id);
		const bsDetailsModal = new bootstrap.Modal(detailsModal);
		bsDetailsModal.show();
	}

	function confirmCVXLoad() {
		alert("This will delete ALL CVX codes from the CVX Vaccine Staging Tables.");
		let result = confirm("Press OK to Run CVX DELETE and LOAD.");
		if (result === true) {
            alert("Initializing....");
        }else {
        	alert("No action taken."");
        }

	}
</script>
<!--================== js section end ==================-->

</head>
<body>
	<div class="container-fluid container-xl justify-content-center">
		<jsp:directive.include file="empireidsnav.jsp"/>
		<div align="center"><h2 class="text-danger">Code Reaper Application</h2></div>
		<div class="table-responsive">
			<table class="table table-responsive table-light table-striped table-hover pb-3" id="listTable">
				<thead>
					<tr class="text-center text-dark fw-bolder">
						<th class="d-none d-md-table-cell"></th>
						<th>Admin Utilities</th>
						<th></th>


					</tr>
					<tr class="text-center text-dark fw-bolder">
						<td>
							<a href="/userReg/newUserReg">
							<input type="button" class="btn btn-danger btn-sm fs-5"
								id="account" value="Create Account"/>
							</a>
						</td>
						<td>
							<a href="/userReg/indexUserReg">
							<input type="button" class="btn btn-danger btn-sm fs-5"
							id="usres" value="Manage Users" />
							</a>
						</td>
						<td>
							<a href="/group/indexGroup">
							<input type="button" class="btn btn-danger btn-sm fs-5"
								id="group" value="Manage Groups" />
							</a>
						</td>
					</tr>
					<tr class="text-center text-dark fw-bolder">
						<td>
							<a href="/parser/uploadFile">
								<input type="button" class="btn btn-danger btn-sm fs-5"
								id="upload" value="ICD Upload" />
							</a>
						</td>
						<td>
							<a href="/article/indexArticle">
								<input type="button" class="btn btn-danger btn-sm fs-5"
									id="article" value="Manage Help" />
							</a>
						</td>
						<td>
							<a href="/cvxvaccine/getAndStoreCvxCodeData">
								<input type="button" onClick="javascript:confirmCVXLoad();" class="btn btn-danger btn-sm fs-5"
									id="cvx_vaccine" value="Store CVX Codes"/>
							</a>
						</td>
					</tr>
					<tr class="text-center text-dark fw-bolder">
						<td>
							<a href="/icdcode/getAndStoreIcd10CodeData">
								<input type="button" class="btn btn-danger btn-sm fs-5"
								id="icdcode10" value="Load ICD 10"/>
							</a>
						</td>
						<td>
							<a href="/icdcode/getAndStoreIcd11CodeData">
								<input type="button" class="btn btn-danger btn-sm fs-5"
								id="icdcode11" value="Load ICD 11"/>
							</a>
						</td>
						<td>
							<a href="/rxnorm/getRxNormCodeData">
								<input type="button" class="btn btn-danger btn-sm fs-5"
									id="rxnorm" value="Load RxNorm"/>
							</a>
						</td>
					</tr>
					<tr class="text-center text-dark fw-bolder">
						<td>
							<a href="/log/showLogFiles">
								<input type="button" class="btn btn-danger btn-sm fs-5"
									id="logs" value="Log Files"/>
							</a>
						</td>
						<td>
							<a href="/maint/showthreads">
								<input type="button" class="btn btn-danger btn-sm fs-5"
								id="showthreads" value="Show Threads"/>
							</a>
						</td>
						<td>

						</td>
					</tr>
				</thead>
			</table>
		</div>
		<div align="center">
			<jsp:directive.include file="empireidsfooter.jsp"/>
		</div>
	</div>
</body>
</html>