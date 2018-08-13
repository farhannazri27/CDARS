<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Time-Lapse Report</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>Time-Lapse Report</h2>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/whTimelapse/update" method="post">
                            <input type="hidden" name="id" value="${whStatusLog.requestId}" />
                            <input type="hidden" name="requestType" value="${requestType}" />
                            <div class="form-group">
                                <label for="eqptType" class="col-lg-2 control-label">Equipment Type </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="eqptType" name="eqptType"  value="${whStatusLog.eqptType}" readonly >
                                </div>
                            </div>      
                            <div class="form-group">
                                <label for="eqptId" class="col-lg-2 control-label">Equipment ID</label>
                                <div class="col-lg-8">
                                    <c:choose>
                                        <c:when test="${whStatusLog.eqptType == 'Load Card'}">
                                            <input type="text" class="form-control" id="eqptId" name="eqptId" readonly="" value="${whStatusLog.loadCard}">
                                        </c:when>
                                        <c:when test="${whStatusLog.eqptType == 'Program Card'}">
                                            <input type="text" class="form-control" id="eqptId" name="eqptId" readonly="" value="${whStatusLog.programCard}">
                                        </c:when>
                                        <c:when test="${whStatusLog.eqptType == 'Load Card & Program Card'}">
                                            <input type="text" class="form-control" id="eqptId" name="eqptId" readonly="" value="${whStatusLog.loadCard} & ${whStatusLog.programCard}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="form-control" id="eqptId" name="eqptId" readonly="" value="${whStatusLog.eqptId}">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="mpNo" class="col-lg-2 control-label">MP No</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="mpNo" name="mpNo" readonly="" value="${whStatusLog.mpNo}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="reqDate" class="col-lg-2 control-label">Requested Date</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="reqDate" name="reqDate" readonly="" value="${whStatusLog.requestDate}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="duration" class="col-lg-2 control-label">Duration (hrs)</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="duration" name="duration" readonly="" value="${whStatusLog.totalHourTakeShip}">
                                </div>
                            </div>
                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-2 control-label">Process Over USL </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="3" id="remarks" name="remarks" readonly="">${whStatusLog.ca}</textarea>
                                </div>
                            </div><br>
                            <hr class="separator"><br>
                            <div class="form-group">
                                <label for="category" class="col-lg-2 control-label">Category *</label>
                                <div class="col-lg-7">
                                    <select id="category" name="category" class="js-example-basic-single" style="width: 100%" autofocus="autofocus">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${category}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="remarksdiv">
                                <label for="rootCause" class="col-lg-2 control-label">Root Cause * </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="3" id="rootCause" name="rootCause"></textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <div class="form-group" id="remarksdiv">
                                <label for="ca" class="col-lg-2 control-label">Correlative Action *</label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="3" id="ca" name="ca"></textarea>
                                    <small id="noteBsEmail" class="form-text text-muted">150 Character only</small>
                                </div>
                            </div>
                            <c:if test="${requestType == 'send'}">
                                <a href="${contextPath}/whTimelapse/send" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            </c:if>
                            <c:if test="${requestType == 'retrieve'}">
                                <a href="${contextPath}/whTimelapse/retrieve" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            </c:if>
                            <!--<a href="${contextPath}/whTimelapse" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>-->
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                
                 $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });

                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        rootCause: {
                            required: true,
                            maxlength: 150
                        },
                        ca: {
                            required: true,
                            maxlength: 150
                        },
                        category: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });


        </script>
    </s:layout-component>
</s:layout-render>