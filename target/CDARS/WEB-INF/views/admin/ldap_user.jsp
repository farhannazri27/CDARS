<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>User Management</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">User List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/admin/user/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add User
                                </a>
                            </div>
                            <div class="filter-block pull-right">
                                <form class="form-inline" role="form" action="${contextPath}/admin/user" method="post">
                                    <div class="form-group" style="margin-right: 0px;">
                                        <select id="selectedGroup" name="selectedGroup" class="form-control pull-left">
                                            <option value="" selected="">All Groups</option>
                                            <c:forEach items="${userGroupList}" var="group">
                                                <option value="${group.id}" ${group.selected}>${group.code} - ${group.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary pull-right">Search</button>
                                    </div>
                                </form>
                            </div>
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
                                        <th><span>Firstname</span></th>
                                        <th><span>Lastname</span></th>
                                        <th><span>Title</span></th>
                                        <th><span>Group</span></th>
                                        <th><span>Login ID</span></th>
                                        <th><span>Email</span></th>
                                        <th><span>Oncid</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${userList}" var="user" varStatus="userLoop">
                                        <tr>
                                            <td><c:out value="${userLoop.index+1}"/></td>
                                            <td id="modal_delete_info_${user.id}"><c:out value="${user.firstname}"/></td>
                                            <td><c:out value="${user.lastname}"/></td>
                                            <td><c:out value="${user.title}"/></td>
                                            <td>
                                                <c:if test="${not empty user.groupCode}">
                                                    <c:out value="${user.groupCode} - ${user.groupName}"/>
                                                </c:if>

                                            </td>
                                            <td><c:out value="${user.loginId}"/></td>
                                            <td><c:out value="${user.email}"/></td>
                                            <td><c:out value="${user.oncid}"/></td>
                                            <td align="center">
                                                <a syncid="${user.loginId}" href="#sync" class="table-link sync_user">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-refresh fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a modaldeleteid="${user.id}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
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
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                                    $(document).ready(function () {

                                                        $.fn.center = function () {
                                                            this.css("position", "absolute");
                                                            this.css("top", ($(window).height() - this.height()) / 2 + $(window).scrollTop() + "px");
                                                            this.css("left", ($(window).width() - this.width()) / 2 + $(window).scrollLeft() + "px");
                                                            return this;
                                                        };

                                                        function showBlockUI() {
                                                            $.blockUI({
                                                                css: {
                                                                    width: 'auto',
                                                                    padding: '5px',
                                                                    backgroundColor: '#fff',
                                                                    '-webkit-border-radius': '10px',
                                                                    '-moz-border-radius': '10px'
                                                                },
                                                                message: '<img src="${contextPath}/resources/private/img/loading_gedik.gif" width="100" />'
                                                            });
                                                            $('.blockUI.blockMsg').center();
                                                        }

                                                        function hideBlockUI() {
                                                            $.unblockUI();
                                                        }

                                                        oTable = $('#dt_spml').DataTable({
                                                            "pageLength": 10,
                                                            "order": [],
                                                            "aoColumnDefs": [
                                                                {'bSortable': false, 'aTargets': [0]},
                                                                {'bSortable': false, 'aTargets': [5]}
                                                            ]
                                                        });
                                                        $('#dt_spml_search').keyup(function () {
                                                            oTable.search($(this).val()).draw();
                                                        });
                                                        $("#dt_spml_rows").change(function () {
                                                            oTable.page.len($(this).val()).draw();
                                                        });

                                                        $(".sync_user").click(function () {
                                                            showBlockUI();
                                                            userSync($(this).attr("syncid"));
                                                        });

                                                        function userSync(loginId) {
                                                            var url = '${contextPath}/admin/user/sync/' + loginId;
                                                            var data = {};

                                                            function success(res) {
                                                                hideBlockUI();
                                                                if (res.status) {
                                                                    swal({
                                                                        title: "Success",
                                                                        text: res.statusMessage,
                                                                        html: true,
                                                                        type: "success",
                                                                        showCancelButton: false,
                                                                        closeOnConfirm: false
                                                                    },
                                                                    function () {
                                                                        window.location.reload(true);
                                                                    });
                                                                } else {
                                                                    swal('Error!', res.statusMessage, 'error');
                                                                }
                                                            }

                                                            $.get(url, data, success, 'json').fail(function (res) {
                                                                hideBlockUI();
                                                                swal('Error!', JSON.stringify(res, null, 4), 'error');
                                                            });
                                                        }
                                                    });

                                                    function modalDelete(e) {
                                                        var deleteId = $(e).attr("modaldeleteid");
                                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                        var deleteUrl = "${contextPath}/admin/user/delete/" + deleteId;
                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                    }
            </script>
    </s:layout-component>
</s:layout-render>