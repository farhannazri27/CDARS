<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                /* -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
                                   box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;*/
            }

            .select2-dropdown-open .select2-choice {
                border-bottom: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open.select2-drop-above .select2-choice,
            .select2-dropdown-open.select2-drop-above .select2-choices {
                border: 1px solid $input-border-focus !important;
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .no-border {
                border: 0;
                box-shadow: none; /* You may want to include this as bootstrap applies these styles too */
            }

            span.tab-space {padding-left:20em;}

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Hardware Request</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>Hardware Request Information</h2>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRequest/add" method="get">
                            <div class="form-group">
                                <label for="requestType" class="col-lg-2 control-label">Request For *</label>
                                <div class="col-lg-8">
                                    <select id="requestType" name="requestType" class="js-example-basic-single" style="width: 30%" autofocus="autofocus">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${requestType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category *</label>
                                <div class="col-lg-5">
                                    <select id="equipmentType" name="equipmentType" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${equipmentType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="retrievalReasonDiv" hidden>
                                <label for="retrievalReason" class="col-lg-2 control-label">Reason for Retrieval *</label>
                                <div class="col-lg-8">
                                    <select id="retrievalReason" name="retrievalReason" class="js-example-basic-single" style="width: 30%" autofocus="autofocus">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${retrievalReason}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <a href="${contextPath}/wh/whRequest" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" id="submit" class="btn btn-primary">Send</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
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
                        requestType: {
                            required: true
                        },
                        equipmentType: {
                            required: true
                        },
                        retrievalReason: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });
            $('#requestType').on('change', function () {
                if ($(this).val() === "Ship") {
                    $("#retrievalReasonDiv").hide();
                    $("#retrievalReason").val("").trigger('change');
                }  else if($(this).val() === "Retrieve"){
                    $("#retrievalReasonDiv").show()();
                    $("#retrievalReason").val("").trigger('change');
                }else{
                    $("#retrievalReasonDiv").hide();
                    $("#retrievalReason").val("").trigger('change');
                }

            });



        </script>
    </s:layout-component>
</s:layout-render>