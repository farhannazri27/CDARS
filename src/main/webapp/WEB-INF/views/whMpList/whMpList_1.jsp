<!DOCTYPE HTML>
<html>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<head>
<script type="text/javascript">
window.onload = function () {
	var chart = new CanvasJS.Chart("chartContainer",
	{
		animationEnabled: true,
		title:{
			text: "Hardware Request"
		},
		data: [
		{
			type: "column", //change type to bar, line, area, pie, etc
			dataPoints: [
				{ label: "Waiting for Approval", y: 18 },
				{ label: "Approved", y: 29 },
				{ label: "Not Approved",  y: 40 }                                   
			]
		}
		]
		});

	chart.render();
}
</script>
 <script src="${contextPath}/resources/private/js/jquery.canvasjs.min.js"></script>
</head>
<body>
<div id="chartContainer" style="height: 300px; width: 100%;"></div>
</body>

</html>