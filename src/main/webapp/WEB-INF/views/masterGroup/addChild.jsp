<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
                table thead {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
                table tbody {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
            }
            .dataTables_wrapper .dt-buttons {
                float:none;  
                text-align:right;
            }


        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Group</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>List of group</h2>
                        <form id="add_mp_list_form" class="form-horizontal" role="form" action="${contextPath}/admin/masterGroup/updateChildGroup" method="post">
                            <div class="form-group">
                                <label for="masterGroup" class="col-lg-2 control-label">Master Group</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="masterGroup" name="masterGroup" value="${masterGroup}" readonly>
                                    <input type="text" class="form-control hide" id="masterGroupId" name="masterGroupId" value="${masterId}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="masterHardware" class="col-lg-2 control-label">Hardware</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="masterHardware" name="masterHardware" value="${masterHardware}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="id" class="col-lg-2 control-label">Group List *</label>
                                <div class="col-lg-5">
                                    <select id="id" name="id" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${userGroupListAll}" var="group">
                                            <option value="${group.id}">${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/masterGroup" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Add</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
        <!--tablelist-->
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Group List</h2>
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
                        <div class="clearfix">
                        </div>

                        <div class="table-responsive">
                            <table id="dt_spml" class="table align-center">
                                <thead title="Test">
                                    <tr>
                                        <th class="col-lg-1"><span>No</span></th>
                                        <th class=""><span>Code</span></th>
                                        <th class=""><span>Name</span></th>
                                        <th class=""><span>Delete</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${userGroupList}" var="userGroup" varStatus="userGroupLoop">
                                        <tr>
                                            <td class="col-lg-1 align-center"><c:out value="${userGroupLoop.index+1}"/></td>
                                            <td class="align-center"><c:out value="${userGroup.code}"/></td>
                                            <td class="align-center"><c:out value="${userGroup.name}"/></td>
                                            <td align="left">
                                                <a modaldeleteid="${userGroup.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
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
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                                    $(document).ready(function () {

                                                        $(".js-example-basic-single").select2({
                                                            placeholder: "Choose one",
                                                            allowClear: true
                                                        });

                                                        oTable = $('#dt_spml').DataTable({
                                                            dom: 'Brtip',
                                                            buttons: [
                                                                {
                                                                    extend: 'copy',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'excel',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'pdf',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5]
                                                                    }
                                                                }
                                                            ]
                                                        });

                                                        $('#dt_spml_search').keyup(function () {
                                                            oTable.search($(this).val()).draw();
                                                        });

                                                        $("#dt_spml_rows").change(function () {
                                                            oTable.page.len($(this).val()).draw();
                                                        });

                                                        var validator = $("#add_mp_list_form").validate({
                                                            rules: {
                                                                id: {
                                                                    required: true
                                                                }
                                                            }
                                                        });
                                                        $(".cancel").click(function () {
                                                            validator.resetForm();
                                                        });

                                                    });

                                                    function modalDelete(e) {
                                                        var deleteId = $(e).attr("modaldeleteid");
                                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                        var deleteUrl = "${contextPath}/admin/masterGroup/deleteChildGroup/" + deleteId;
                                                        var deleteMsg = "Are you sure want to delete this row?";
                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                    }

        </script>
    </s:layout-component>
</s:layout-render>