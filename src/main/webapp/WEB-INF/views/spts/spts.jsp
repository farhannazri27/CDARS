<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>SPTS</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <div class="filter-block pull-left">
                                <a href="${contextPath}/spts/getitembyparam" class="btn btn-primary pull-right">
                                    <i class="fa fa-angle-double-right fa-lg"></i> Get Item By Param
                                </a>
                                <a href="${contextPath}/spts/getitemall" class="btn btn-primary pull-right">
                                    <i class="fa fa-angle-double-right fa-lg"></i> Get Item All
                                </a>
                                <a href="${contextPath}/spts/selectitem" class="btn btn-primary pull-right">
                                    <i class="fa fa-angle-double-right fa-lg"></i> Select Item Sample
                                </a>
                                <a href="${contextPath}/spts/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-angle-double-right fa-lg"></i> Add Item
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {               
            });
        </script>
    </s:layout-component>
</s:layout-render>