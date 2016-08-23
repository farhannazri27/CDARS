<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Shipping Material Pass List</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Material Pass Number</h2>
                        <form id="add_mp_list_form" class="form-horizontal" role="form" action="${contextPath}/wh/whMpList/save" method="post">
                            <div class="form-group">
                                <label for="mpNo" class="col-lg-4 control-label">Material Pass Number *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="mpNo" name="mpNo" placeholder="Material Pass Number" value="">
                                </div>
                            </div>
                            <a href="${contextPath}/wh/whMpList" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                var validator = $("#add_mp_list_form").validate({
                    rules: {
                        mpNo: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });
            $('#equipmentType').on('change', function () {
                if ($(this).val() === "Motherboard") {
                $("#mblistdiv").show();
                        $("#listdiv").hide();
                        $("#stencillistdiv").hide();
                        $("#traylistdiv").hide();
                        $("#pcblistdiv").hide();
                        $("#typediv").hide();
                        $("#quantitydiv").hide();
                        $("#equipmentIdStencil").val("");
                        $("#equipmentIdTray").val("");
                        $("#equipmentIdPcb").val("");
                        $("#quantity").val("");
                        $("#type").val("");
                } else if ($(this).val() === "Stencil") {
                $("#stencillistdiv").show();
                        $("#listdiv").hide();
                        $("#mblistdiv").hide();
                        $("#traylistdiv").hide();
                        $("#pcblistdiv").hide();
                        $("#typediv").hide();
                        $("#quantitydiv").hide();
                        $("#equipmentIdMb").val("");
                        $("#equipmentIdTray").val("");
                        $("#equipmentIdPcb").val("");
                        $("#quantity").val("");
                        $("#type").val("");
                } else if ($(this).val() === "Tray") {
                $("#traylistdiv").show();
                        $("#quantitydiv").show();
                        $("#listdiv").hide();
                        $("#stencillistdiv").hide();
                        $("#mblistdiv").hide();
                        $("#pcblistdiv").hide();
                        $("#typediv").hide();
                        $("#equipmentIdMb").val("");
                        $("#equipmentIdStencil").val("");
                        $("#equipmentIdPcb").val("");
                        $("#type").val("");
                } else if ($(this).val() === "PCB") {
                $("#pcblistdiv").show();
                        $("#typediv").hide();
                        $("#quantitydiv").show();
                        $("#listdiv").hide();
                        $("#stencillistdiv").hide();
                        $("#mblistdiv").hide();
                        $("#traylistdiv").hide();
                        $("#equipmentIdMb").val("");
                        $("#equipmentIdStencil").val("");
                        $("#equipmentIdTray").val("");
                    }else {
                        $("#listdiv").show();
                         $("#pcblistdiv").hide();
                        $("#mblistdiv").hide();
                        $("#stencillistdiv").hide();
                        $("#traylistdiv").hide();
                        $("#typediv").hide();
                        $("#quantitydiv").hide();
                        $("#equipmentIdMb").val("");
                        $("#equipmentIdStencil").val("");
                        $("#equipmentIdTray").val("");
                        $("#equipmentIdPcb").val("");
                        $("#quantity").val("");
                        $("#type").val("");
                    }

            });

        </script>
    </s:layout-component>
</s:layout-render>