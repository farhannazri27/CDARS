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
            <h1>Add User From LDAP</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">LDAP User List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/admin/user" class="btn btn-info pull-right"><i class="fa fa-reply"></i> Back</a>
                            </div>
                            <div class="filter-block pull-right">
                                <div class="form-group" style="margin-right: 0px;">
                                    <select id="selectedGroup" name="selectedGroup" class="form-control pull-left">
                                        <option value="" selected="">Assign CDARS User Group</option>
                                        <c:forEach items="${userGroupList}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.code} - ${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <form action="${contextPath}/admin/user/add" method="POST">
                                <div class="filter-block pull-right">
                                    <button type="submit" class="btn btn-info pull-right" style="margin-right: 10px;"><i class="fa fa-search"></i> Search</button>
                                </div>
                                <div class="filter-block pull-right">
                                    <div class="form-group" style="margin-right: 0px;">
                                        <div class="col-lg-12" style="padding-right: 0px;">
                                            <input type="text" class="form-control" name="loginId" placeholder="Search by Login ID">
                                        </div>
                                    </div>
                                </div>
                            </form>
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
                                            <td id="firstname_${user.loginId}"><c:out value="${user.firstname}"/></td>
                                            <td id="lastname_${user.loginId}"><c:out value="${user.lastname}"/></td>
                                            <td id="title_${user.loginId}"><c:out value="${user.title}"/></td>
                                            <td id="login_id_${user.loginId}"><c:out value="${user.loginId}"/></td>
                                            <td id="email_${user.loginId}"><c:out value="${user.email}"/></td>
                                            <td id="oncid_${user.loginId}"><c:out value="${user.oncid}"/></td>
                                            <td align="center">
                                                <a addid="${user.loginId}" href="#add" class="table-link sync_user">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-plus fa-stack-1x fa-inverse"></i>
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
                    var selectedGroup = $("#selectedGroup").val();
                    if (selectedGroup === "") {
                        swal('Error!', 'Please select group to assign first!', 'error');
                    } else {
                        showBlockUI();
                        var loginId = $(this).attr("addid");
                        userExistence(loginId, selectedGroup);
                    }

                    function userExistence(loginId, selectedGroup) {
                        var url = '${contextPath}/admin/user/loginid/' + loginId;
                        var data = {};

                        function success(res) {
                            if (res.status) {
                                hideBlockUI();
                                swal('Error!', res.statusMessage, 'error');
                            } else {
                                hideBlockUI();
                                addUser(loginId, selectedGroup);
                            }
                        }

                        $.get(url, data, success, 'json').fail(function (res) {
                            hideBlockUI();
                            swal('Error!', JSON.stringify(res, null, 4), 'error');
                        });
                    }

                    function addUser(loginId, selectedGroup) {
                        var firstname = $("#firstname_" + loginId).html();
                        var lastname = $("#lastname_" + loginId).html();
                        var title = $("#title_" + loginId).html();
                        var email = $("#email_" + loginId).html();
                        var oncid = $("#oncid_" + loginId).html();

                        var url = '${contextPath}/admin/user/ldap/save';
                        var data = {
                            firstname: firstname,
                            lastname: lastname,
                            title: title,
                            email: email,
                            oncid: oncid,
                            loginId: loginId,
                            groupId: selectedGroup
                        };

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
                                    window.location = "${contextPath}/admin/user";
                                });
                            } else {
                                swal('Error!', res.statusMessage, 'error');
                            }
                        }

                        $.post(url, data, success, 'json').fail(function (res) {
                            hideBlockUI();
                            swal('Error!', JSON.stringify(res, null, 4), 'error');
                        });
                    }
                });
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