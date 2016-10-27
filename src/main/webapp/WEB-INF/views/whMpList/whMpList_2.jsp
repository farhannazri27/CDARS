<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Dashboard</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <!--<div class="clearfix"></div>-->
                            <div class="pull-left" id="chartContainer" style="height: 300px; width: 49%;"></div>
                            <div class="pull-right" id="chartContainerShi" style="height: 300px; width: 49%;"></div>
                        </div>  
                        <br>
                        <div class="clearfix">
                            <!--<div class="clearfix"></div>-->
                            <div class="pull-left" id="chartContainerRet" style="height: 400px; width: 100%;"></div>
                        </div>  
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/jquery.canvasjs.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            window.onload = function () {
                var chartRe = new CanvasJS.Chart("chartContainer",
                        {
                            animationEnabled: true,
                            title: {
                                text: "Hardware Request"
                            },
                            data: [
                                {
                                    type: "column", //change type to bar, line, area, pie, etc
                                    dataPoints: [
                                        {label: "Waiting for Approval", y: ${countWaitReq}},
                                        {label: "Approved", y: ${countAppReq}},
                                        {label: "Not Approved", y: ${countNotAppReq}}
                                    ]
                                }
                            ]
                        });

                chartRe.render();

                var chartShi = new CanvasJS.Chart("chartContainerShi",
                        {
                            animationEnabled: true,
                            title: {
                                text: "Hardware Shipping"
                            },
                            data: [
                                {
                                    type: "column", //change type to bar, line, area, pie, etc
                                    dataPoints: [
                                        {label: "No Material Pass Number", y: ${countMpShip}},
                                        {label: "Not Scan Barcode Sticker Yet", y: ${countBsShip}},
                                        {label: "Not Scan Trip Ticket Yet", y: ${countTtShip}},
                                        {label: "Shipped", y: ${countShippedShip}}
                                    ]
                                }
                            ]
                        });

                chartShi.render();

                var chartRet = new CanvasJS.Chart("chartContainerRet",
                        {
                            animationEnabled: true,
                            title: {
                                text: "Hardware Retrieval"
                            },
                            data: [
                                {
                                    type: "bar", //change type to bar, line, area, pie, etc
                                    dataPoints: [
                                        {label: "Trip Ticket - Hold By Supervisor", y: ${countTtHoldRet}},
                                        {label: "Trip Ticket - Unverified By Supervisor", y: ${countTtUnvRet}},
                                        {label: "Trip Ticket Scanning Mismatched", y: ${countTtMisRet}},
                                        {label: "Barcode Sticker - Hold By Supervisor", y: ${countBsHoldRet}},
                                        {label: "Barcode Sticker - Unverified By Supervisor", y: ${countBsUnvRet}},
                                        {label: "Barcode Sticker Scanning Mismatched", y: ${countBsMisRet}},
                                        {label: "Barcode Verified", y: ${countBsVerRet}},
                                        {label: "Shipped", y: ${countShipRet}},
                                        {label: "Requested", y: ${countReqRet}}
                                    ]
                                }
                            ]
                        });

                chartRet.render();
            }

        </script>
    </s:layout-component>
</s:layout-render>