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
            <h1>Add Bib Card Pairing</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>Bib Card Information</h2>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/admin/cardPairing/save" method="post">
                            <div class="form-group">
                                <label for="type" class="col-lg-2 control-label">Single/Pair *</label>
                                <div class="col-lg-2">
                                    <select id="type" name="type" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pairingType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="programCard" class="col-lg-2 control-label">Program Card ID *</label>
                                <div class="col-lg-6">
                                    <select id="programCard" name="programCard" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListPc}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="loadCard" class="col-lg-2 control-label">Load Card ID *</label>
                                <div class="col-lg-6">
                                    <select id="loadCard" name="loadCard" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListLc}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="pairId" class="col-lg-2 control-label hidden">Pair ID *</label>
                                <div class="col-lg-2">
                                    <input type="hidden" class="form-control" id="pairId" name="pairId" value="${pairId}">
                                </div>
                            </div>       

                            <a href="${contextPath}/admin/cardPairing" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                        type: {
                            required: true
                        },
                        programCard: {
                            required: function () {
                                if ($('#type').val() === "PAIR") {
                                    return true;
                                }else if($('#type').val() === "SINGLE" && $('#loadCard').val() === ""){
                                     return true;
                                }
                                else {
                                    return false;
                                }
                            }
                        },
                        loadCard: {
                            required: function () {
                                if ($('#type').val() === "PAIR") {
                                    return true;
                                }else if($('#type').val() === "SINGLE" && $('#programCard').val() === ""){
                                     return true;
                                } else {
                                    return false;
                                }
                            }
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