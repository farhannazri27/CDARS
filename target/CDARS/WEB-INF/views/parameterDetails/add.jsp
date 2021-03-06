<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <!--parameterdetail list-->
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Parameter Details</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Add Parameter Details</h2>
                        <form id="add_parameter_form" class="form-horizontal" role="form" action="${contextPath}/admin/parameterMaster/saveDetail" method="post">
                            <div class="form-group">
                                <label for=" masterName" class="col-lg-4 control-label">Master Name </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="masterName" name="masterName" placeholder="Code" value="${codeName}" disabled>
                                </div>
                            </div>
                            <div class="form-group" hidden>
                                <label for=" masterCode" class="col-lg-4 control-label">master Code</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="masterCode" name="masterCode" value="${masterCode}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" detailsCode" class="col-lg-4 control-label">Details Code </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="detailsCode" name="detailsCode" placeholder="Details Code" value="${detailsCode}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="detailsName" class="col-lg-4 control-label">Details Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="detailsName" name="detailsName" placeholder="Details Name" value="" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remarks" class="col-lg-4 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/parameterMaster" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
        <div class="col-lg-12">
            <!--<h1>Parameter Details</h1>-->
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Parameter Details List</h2>

<!--                            <div class="filter-block pull-right">
                                <a href="${contextPath}/admin/parameterMaster/detail/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add Parameter Details
                                </a>
                            </div>-->
                        </div>
                        <hr/>
                        <div class="clearfix">
                            <div class="form-group pull-left">
                                <select id="dt_spml_rows" class="form-control">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                            </div>
                            <div class="filter-block pull-right">
                                <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 5px;">
                                </div>
                                <div class="form-group pull-left" style="margin-right: 0px;">
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>Detail Code</span></th>
                                        <th><span>Name</span></th>
                                        <th><span>Remarks</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${parameterDetailsList}" var="parameterDetails" varStatus="parameterDetailsLoop">
                                        <tr>
                                            <td><c:out value="${parameterDetailsLoop.index+1}"/></td>
                                            <td><c:out value="${parameterDetails.detailCode}"/></td>
                                            <td id="modal_delete_info_${parameterDetails.id}"><c:out value="${parameterDetails.name}"/></td>
                                            <td><c:out value="${parameterDetails.remarks}"/></td>
                                            <td align="center">
                                                <a href="${contextPath}/admin/parameterMaster/editDetails/${parameterDetails.id}" class="table-link" title="Edit">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a modaldeleteid="${parameterDetails.id}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <!--parameterdetail list-->
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.tableTools.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                                    $(document).ready(function () {

                                                        var validator = $("#add_parameter_form").validate({
                                                            rules: {
                                                                code: {
                                                                    required: true,
                                                                    alphanumericdu: true,
                                                                    minlength: 2,
                                                                    maxlength: 10
                                                                },
                                                                detailsName: {
                                                                    required: true,
//                                                                    lsnandbasicsymbol: true,
                                                                    minlength: 2,
                                                                    maxlength: 100
                                                                }
                                                            }
                                                        });
                                                        $(".cancel").click(function () {
                                                            validator.resetForm();
                                                        });

//                                                        parameterdetail list
                                                        oTable = $('#dt_spml').DataTable({
                                                            "pageLength": 10,
                                                            "order": [],
                                                            "aoColumnDefs": [
                                                                {"bSortable": false, "aTargets": [0]},
                                                                {"bSortable": false, "aTargets": [4]}
                                                            ],
                                                            "sDom": "tp"
                                                        });
                                                        var exportTitle = "Parameter Details List";
                                                        var tt = new $.fn.dataTable.TableTools(oTable, {
                                                            "sSwfPath": "${contextPath}/resources/private/datatables/swf/copy_csv_xls_pdf.swf",
                                                            "aButtons": [
                                                                {
                                                                    "sExtends": "copy",
                                                                    "sButtonText": "Copy",
                                                                    "sTitle": exportTitle,
                                                                    "mColumns": [0, 1, 2, 3]
                                                                },
                                                                {
                                                                    "sExtends": "xls",
                                                                    "sButtonText": "Excel",
                                                                    "sTitle": exportTitle,
                                                                    "mColumns": [0, 1, 2, 3]
                                                                },
                                                                {
                                                                    "sExtends": "pdf",
                                                                    "sButtonText": "PDF",
                                                                    "sTitle": exportTitle,
                                                                    "mColumns": [0, 1, 2, 3]
                                                                },
                                                                {
                                                                    "sExtends": "print",
                                                                    "sButtonText": "Print"
                                                                }
                                                            ]
                                                        });
                                                        $(tt.fnContainer()).appendTo("#dt_spml_tt");
                                                        $('#dt_spml_search').keyup(function () {
                                                            oTable.search($(this).val()).draw();
                                                        });
                                                        $("#dt_spml_rows").change(function () {
                                                            oTable.page.len($(this).val()).draw();
                                                        });
                                                    });

//                                                    parameterdetail list
                                                    function modalDelete(e) {
                                                        var deleteId = $(e).attr("modaldeleteid");
                                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                        var deleteUrl = "${contextPath}/admin/parameterMaster/deleteDetail/" + deleteId;
                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                    }
            </script>
    </s:layout-component>
</s:layout-render>