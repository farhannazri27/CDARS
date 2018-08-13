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
                        <div class="clearfix">
                            <h2 class="pull-left">Hardware Request Information</h2>
                            <c:if test="${equipmentType1 == 'Load Card' || equipmentType1 == 'Program Card' || equipmentType1 == 'Load Card & Program Card'}">
                                <div class="filter-block pull-right">
                                    <a href="${contextPath}/admin/cardPairing" class="btn btn-primary pull-right">
                                        <i class="fa fa-truck fa-lg"></i> Card Pairing Management
                                    </a>
                                </div>
                            </c:if>
                        </div>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/dh/dhRequest/save" method="post">
                            <div class="form-group">
                                <label for="requestBy" class="col-lg-2 control-label">Request By</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestBy" name="requestBy" value="${username}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestType" class="col-lg-2 control-label">Hardware From</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="requestType" name="requestType" value="${requestType1}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category</label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" value="${equipmentType1}" readonly>
                                </div>
                            </div>
                            <!--if request for ship-->
                            <c:if test="${requestType1 == 'Rel Lab'}">
                                <c:if test="${equipmentType1 == 'Motherboard'}">
                                    <div class="form-group" id="mblistdiv">
                                        <label for="equipmentIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="equipmentIdMb" name="equipmentIdMb" class="js-example-basic-single" style="width: 100%" >
                                                <option value="" selected=""></option>
                                                <c:forEach items="${bibItemList}" var="group">
                                                    <option mbquantity="${group.OnHandQty}" value="${group.ItemID}">${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyMb" class="col-lg-1 control-label"> On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyMb" name="maxQtyMb" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Stencil'}">
                                    <div class="form-group" id="stencillistdiv" >
                                        <label for="equipmentIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="equipmentIdStencil" name="equipmentIdStencil" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${StencilItemList}" var="group">
                                                    <option stencilquantity="${group.OnHandQty}" value="${group.ItemID}" >${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyStencil" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyStencil" name="maxQtyStencil" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Tray'}">
                                    <div class="form-group" id="traylistdiv" >
                                        <label for="equipmentIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdTray" name="equipmentIdTray" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${trayItemList}" var="group">
                                                    <option trayquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyTray" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyTray" name="maxQtyTray" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantity" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantity" name="quantity" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'PCB'}">
                                    <div class="form-group" id="pcbTypeDiv" >
                                        <label for="pcbType" class="col-lg-2 control-label">PCB Type *</label>
                                        <div class="col-lg-4">
                                            <select id="pcbType" name="pcbType" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${pcbType}" var="group">
                                                    <option pcbTypeQty="${group.quantity}" value="${group.pcbType}" ${group.selected}>${group.pcbType}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyPcbType" class="col-lg-2 control-label">Quantity per Box Limit</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="maxQtyPcbType" name="maxQtyPcbType" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="pcbCtrlistdiv" >
                                        <label for="equipmentIdpcbCtr" class="col-lg-2 control-label">PCB ID Control </label>
                                        <div class="col-lg-7">                                      
                                            <select id="equipmentIdpcbCtr" name="equipmentIdpcbCtr" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${pcbItemListCtr}" var="group">
                                                    <option pcbCtrquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyCtr" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyCtr" name="maxQtyCtr" value="" readonly >
                                            <input type="hidden" class="form-control" id="maxQtyCtr1" name="maxQtyCtr1" value="0" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="pcbCtrQtydiv" >
                                        <label for="pcbCtrQty" class="col-lg-2 control-label">Quantity Control* </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="pcbCtrQty" name="pcbCtrQty" value="0">
                                        </div>
                                    </div>  
                                    <div class="form-group" id="pcbAlistdiv" >
                                        <label for="equipmentIdpcbA" class="col-lg-2 control-label">PCB ID Qual A  </label>
                                        <div class="col-lg-7">                                      
                                            <select id="equipmentIdpcbA" name="equipmentIdpcbA" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${pcbItemListA}" var="group">
                                                    <option pcbAquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyA" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyA" name="maxQtyA" value="" readonly >
                                            <input type="hidden" class="form-control" id="maxQtyA1" name="maxQtyA1" value="0" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="pcbAQtydiv" >
                                        <label for="pcbAQty" class="col-lg-2 control-label">Quantity Qual A *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="pcbAQty" name="pcbAQty" value="0">
                                        </div>
                                    </div> 
                                    <div class="form-group" id="pcbBlistdiv" >
                                        <label for="equipmentIdpcbB" class="col-lg-2 control-label">PCB ID Qual B  </label>
                                        <div class="col-lg-7">                                      
                                            <select id="equipmentIdpcbB" name="equipmentIdpcbB" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${pcbItemListB}" var="group">
                                                    <option pcbBquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyB" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyB" name="maxQtyB" value="" readonly >
                                            <input type="hidden" class="form-control" id="maxQtyB1" name="maxQtyB1" value="0" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="pcbBQtydiv" >
                                        <label for="pcbBQty" class="col-lg-2 control-label">Quantity Qual B *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="pcbBQty" name="pcbBQty" value="0">
                                        </div>
                                    </div>    
                                    <div class="form-group" id="pcbClistdiv" >
                                        <label for="equipmentIdpcbC" class="col-lg-2 control-label">PCB ID Qual C  </label>
                                        <div class="col-lg-7">                                      
                                            <select id="equipmentIdpcbC" name="equipmentIdpcbC" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${pcbItemListC}" var="group">
                                                    <option pcbCquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyC" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyC" name="maxQtyC" value="" readonly >
                                            <input type="hidden" class="form-control" id="maxQtyC1" name="maxQtyC1" value="0" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="pcbCQtydiv" >
                                        <label for="pcbCQty" class="col-lg-2 control-label">Quantity Qual C *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="pcbCQty" name="pcbCQty" value="0">
                                        </div>
                                    </div> 
                                    <div class="form-group" id="quantitytestdiv" >
                                        <label for="quantitytest" class="col-lg-2 control-label">Total Quantity</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="quantitytest" name="quantitytest" value="0" readonly>
                                        </div>
                                    </div> 
                                </c:if>
                                <c:if test="${equipmentType1 == 'Load Card'}">
                                    <div class="form-group" id="lcdiv" >
                                        <label for="equipmentIdLc" class="col-lg-2 control-label">Load Card ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdLc" name="equipmentIdLc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${itemListLc}" var="group">
                                                    <option lcquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyLc" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyLc" name="maxQtyLc" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityLc" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityLc" name="quantityLc" value="">
                                        </div>
                                    </div> 
                                </c:if>
                                <c:if test="${equipmentType1 == 'Program Card'}">
                                    <div class="form-group" id="pcdiv" >
                                        <label for="equipmentIdPc" class="col-lg-2 control-label">Program Card ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdPc" name="equipmentIdPc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${itemListPc}" var="group">
                                                    <option pcquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyPc" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyPc" name="maxQtyPc" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityPc" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityPc" name="quantityPc" value="">
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Load Card & Program Card'}">
                                    <div class="form-group" id="lc1div" >
                                        <label for="equipmentIdLc1" class="col-lg-2 control-label">Load Card ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdLc1" name="equipmentIdLc1" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${itemListLc}" var="group">
                                                    <option lc1quantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyLc1" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyLc1" name="maxQtyLc1" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityLc1" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityLc1" name="quantityLc1" value="">
                                        </div>
                                    </div> 
                                    <div class="form-group" id="pc1div" >
                                        <label for="equipmentIdPc1" class="col-lg-2 control-label">Program Card ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdPc1" name="equipmentIdPc1" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${itemListPc}" var="group">
                                                    <option pc1quantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyPc1" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyPc1" name="maxQtyPc1" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityPc1" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityPc1" name="quantityPc1" value="">
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'BIB Parts'}">
                                    <div class="form-group" id="bibPartlistdiv" >
                                        <label for="equipmentIdBibParts" class="col-lg-2 control-label">BIB Parts ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdBibParts" name="equipmentIdBibParts" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${bibPartsItemList}" var="group">
                                                    <option bibPartsquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyBibParts" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyBibParts" name="maxQtyBibParts" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityBibParts" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityBibParts" name="quantityBibParts" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_DTS'}">
                                    <div class="form-group" id="ateDtslistdiv" >
                                        <label for="equipmentIdAteDts" class="col-lg-2 control-label">ATE DTS ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteDts" name="equipmentIdAteDts" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateDtsItemList}" var="group">
                                                    <option ateDtsquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteDts" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteDts" name="maxQtyAteDts" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteDts" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteDts" name="quantityAteDts" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <!--ate-->
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_FET'}">
                                    <div class="form-group" id="ateFetlistdiv" >
                                        <label for="equipmentIdAteFet" class="col-lg-2 control-label">ATE FET ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteFet" name="equipmentIdAteFet" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateFetItemList}" var="group">
                                                    <option ateFetquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteFet" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteFet" name="maxQtyAteFet" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteFet" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteFet" name="quantityAteFet" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_PFT'}">
                                    <div class="form-group" id="atePftlistdiv" >
                                        <label for="equipmentIdAtePft" class="col-lg-2 control-label">ATE PFT ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAtePft" name="equipmentIdAtePft" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${atePftItemList}" var="group">
                                                    <option atePftquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAtePft" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAtePft" name="maxQtyAtePft" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAtePft" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAtePft" name="quantityAtePft" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_TESEC'}">
                                    <div class="form-group" id="ateTeseclistdiv" >
                                        <label for="equipmentIdAteTesec" class="col-lg-2 control-label">ATE TESEC ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteTesec" name="equipmentIdAteTesec" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateTesecItemList}" var="group">
                                                    <option ateTesecquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteTesec" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteTesec" name="maxQtyAteTesec" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteTesec" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteTesec" name="quantityAteTesec" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_TESTFIXTURE'}">
                                    <div class="form-group" id="ateTestlistdiv" >
                                        <label for="equipmentIdAteTest" class="col-lg-2 control-label">ATE TEST FIXTURE ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteTest" name="equipmentIdAteTest" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateTestItemList}" var="group">
                                                    <option ateTestquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteTest" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteTest" name="maxQtyAteTest" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteTest" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteTest" name="quantityAteTest" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_ETS'}">
                                    <div class="form-group" id="ateEtslistdiv" >
                                        <label for="equipmentIdAteEts" class="col-lg-2 control-label">ATE ETS ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteEts" name="equipmentIdAteEts" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateEtsItemList}" var="group">
                                                    <option ateEtsquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteEts" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteEts" name="maxQtyAteEts" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteEts" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteEts" name="quantityAteEts" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_ESD'}">
                                    <div class="form-group" id="ateTestlistdiv" >
                                        <label for="equipmentIdAteEsd" class="col-lg-2 control-label">ATE ESD ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteEsd" name="equipmentIdAteEsd" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateEsdItemList}" var="group">
                                                    <option ateEsdquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteEsd" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteEsd" name="maxQtyAteEsd" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteEsd" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteEsd" name="quantityAteEsd" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_ACC'}">
                                    <div class="form-group" id="ateTestlistdiv" >
                                        <label for="equipmentIdAteAcc" class="col-lg-2 control-label">ATE ACC ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdAteAcc" name="equipmentIdAteAcc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${ateAccItemList}" var="group">
                                                    <option ateAccquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyAteAcc" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyAteAcc" name="maxQtyAteAcc" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityAteAcc" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityAteAcc" name="quantityAteAcc" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <!--sparepart-->
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_GENERAL'}">
                                    <div class="form-group" id="ateFetlistdiv" >
                                        <label for="equipmentIdEqpGeneral" class="col-lg-2 control-label">EQP GENERAL ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpGeneral" name="equipmentIdEqpGeneral" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpGeneralItemList}" var="group">
                                                    <option eqpGeneralquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpGeneral" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpGeneral" name="maxQtyEqpGeneral" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpGeneral" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpGeneral" name="quantityEqpGeneral" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_H3TRB_AC_HAST'}">
                                    <div class="form-group" id="atePftlistdiv" >
                                        <label for="equipmentIdEqpHast" class="col-lg-2 control-label">EQP H3TRB_AC_HAST ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpHast" name="equipmentIdEqpHast" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpHastItemList}" var="group">
                                                    <option eqpHastquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpHast" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpHast" name="maxQtyEqpHast" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpHast" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpHast" name="quantityEqpHast" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_HTS_HTB_WF'}">
                                    <div class="form-group" id="ateTeseclistdiv" >
                                        <label for="equipmentIdEqpWf" class="col-lg-2 control-label">EQP HTS_HTB_WF ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpWf" name="equipmentIdEqpWf" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpWfItemList}" var="group">
                                                    <option eqpWfquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpWf" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpWf" name="maxQtyEqpWf" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpWf" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpWf" name="quantityEqpWf" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_IOL'}">
                                    <div class="form-group" id="ateTestlistdiv" >
                                        <label for="equipmentIdEqpIol" class="col-lg-2 control-label">EQP IOL ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpIol" name="equipmentIdEqpIol" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpIolItemList}" var="group">
                                                    <option eqpIolquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpIol" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpIol" name="maxQtyEqpIol" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpIol" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpIol" name="quantityEqpIol" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_TC_PTC'}">
                                    <div class="form-group" id="ateEtslistdiv" >
                                        <label for="equipmentIdEqpPtc" class="col-lg-2 control-label">EQP TC_PTC ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpPtc" name="equipmentIdEqpPtc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpPtcItemList}" var="group">
                                                    <option eqpPtcquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpPtc" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpPtc" name="maxQtyEqpPtc" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpPtc" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpPtc" name="quantityEqpPtc" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_FOL'}">
                                    <div class="form-group" id="ateTestlistdiv" >
                                        <label for="equipmentIdEqpFol" class="col-lg-2 control-label">EQP FOL ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpFol" name="equipmentIdEqpFol" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpFolItemList}" var="group">
                                                    <option eqpFolquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpFol" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpFol" name="maxQtyEqpFol" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpFol" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpFol" name="quantityEqpFol" value="">
                                        </div>
                                    </div>  
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_BLR'}">
                                    <div class="form-group" id="ateTestlistdiv" >
                                        <label for="equipmentIdEqpBlr" class="col-lg-2 control-label">EQP BLR ID * </label>
                                        <div class="col-lg-7">                                                    
                                            <select id="equipmentIdEqpBlr" name="equipmentIdEqpBlr" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${eqpBlrItemList}" var="group">
                                                    <option eqpBlrquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="maxQtyEqpBlr" class="col-lg-1 control-label">On Hand Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="maxQtyEqpBlr" name="maxQtyEqpBlr" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="quantitydiv" >
                                        <label for="quantityEqpBlr" class="col-lg-2 control-label">Quantity *</label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantityEqpBlr" name="quantityEqpBlr" value="">
                                        </div>
                                    </div>  
                                </c:if>

                            </c:if>
                            <!--if request for retrieval-->
                            <c:if test="${requestType1 == 'Sg Gadut'}">
                                <!--                                <div class="form-group">
                                                                    <label for="retrievalReason" class="col-lg-2 control-label">Reason for Retrieval</label>
                                                                    <div class="col-lg-8">
                                                                        <input type="text" class="form-control" id="retrievalReason" name="retrievalReason" value="${retrievalReason1}" readonly>
                                                                    </div>
                                                                </div>-->
                                <c:if test="${equipmentType1 == 'Motherboard'}">
                                    <div class="form-group" id="inventorymblistdiv" >
                                        <label for="inventoryIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                        <div class="col-lg-7">                  
                                            <select id="inventoryIdMb" name="inventoryIdMb" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListMb}" var="group">
                                                    <option invQtyMbValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyMb" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyMb" name="invQtyMb" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Stencil'}">
                                    <div class="form-group" id="inventorystencillistdiv" >
                                        <label for="inventoryIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdStencil" name="inventoryIdStencil" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListStencil}" var="group">
                                                    <option invQtyStencilValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyStencil" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyStencil" name="invQtyStencil" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Tray'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdTray" name="inventoryIdTray" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListTray}" var="group">
                                                    <option invQtyTrayValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyTray" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyTray" name="invQtyTray" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'PCB'}">
                                    <div class="form-group" id="inventorypcblistdiv" >
                                        <label for="inventoryIdPcb" class="col-lg-2 control-label">PCB ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdPcb" name="inventoryIdPcb" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListPCB}" var="group">
                                                    <option invQtyPcbValue="${group.quantity}" 
                                                            invQtyPcbAValue="${group.pcbAQty}" 
                                                            invQtyPcbBValue="${group.pcbBQty}" 
                                                            invQtyPcbCValue="${group.pcbCQty}" 
                                                            invQtyPcbCtrValue="${group.pcbCtrQty}"
                                                            value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyPcb" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyPcb" name="invQtyPcb" value="" readonly >
                                        </div>
                                    </div> 
                                    <div class="form-group" id="quantityABdiv" >
                                        <div class="col-lg-1"></div>      
                                        <label for="quantityA" class="col-lg-2 control-label">Quantity Qual A:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="quantityA" name="quantityA" value="" readonly>
                                        </div>
                                        <label for="quantityB" class="col-lg-2 control-label">Quantity Qual B:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="quantityB" name="quantityB" value="" readonly>
                                        </div>
                                    </div>  
                                    <div class="form-group" id="quantityCCtrdiv">
                                        <div class="col-lg-1"></div>      
                                        <label for="quantityC" class="col-lg-2 control-label">Quantity Qual C:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="quantityC" name="quantityC" value="" readonly>
                                        </div>
                                        <label for="quantityCtr" class="col-lg-2 control-label">Quantity Control:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="quantityCtr" name="quantityCtr" value="" readonly>
                                        </div>
                                    </div> 
                                </c:if>
                                <c:if test="${equipmentType1 == 'Load Card'}">
                                    <div class="form-group" id="inventorylclistdiv" >
                                        <label for="inventoryIdTray" class="col-lg-2 control-label">Load Card ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdLc" name="inventoryIdLc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListLoadCard}" var="group">
                                                    <option invQtyLcValue="${group.loadCardQty}" value="${group.id}" ${group.selected}>${group.loadCard}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyLc" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyLc" name="invQtyLc" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Program Card'}">
                                    <div class="form-group" id="inventorypclistdiv" >
                                        <label for="inventoryIdPc" class="col-lg-2 control-label">Program Card ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdPc" name="inventoryIdPc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListProgramCard}" var="group">
                                                    <option invQtyPcValue="${group.programCardQty}" value="${group.id}" ${group.selected}>${group.programCard}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyLc" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyPc" name="invQtyPc" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'Load Card & Program Card'}">
                                    <div class="form-group" id="inventorylc1listdiv" >
                                        <label for="inventoryIdLc1" class="col-lg-2 control-label">Load Card ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdLc1" name="inventoryIdLc1" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListLoadAndProgramCard}" var="group">
                                                    <option invQtyLc1Value="${group.loadCardQty}" 
                                                            invPc1Value="${group.programCard}" 
                                                            invQtyPc1Value="${group.programCardQty}" 
                                                            value="${group.id}" ${group.selected}>${group.loadCard}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyLc1" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyLc1" name="invQtyLc1" value="" readonly >
                                        </div>
                                    </div>
                                    <div class="form-group" id="inventorypc1listdiv" >
                                        <label for="inventoryIdPc1" class="col-lg-2 control-label">Program Card ID * </label>
                                        <div class="col-lg-7">
                                            <input type="text" class="form-control" id="inventoryIdPc1" name="inventoryIdPc1" value="" readonly >
                                        </div>
                                        <label for="invQtyLc" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyPc1" name="invQtyPc1" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'BIB Parts'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdBibParts" class="col-lg-2 control-label">BIB Parts ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdBibParts" name="inventoryIdBibParts" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListBIBParts}" var="group">
                                                    <option invQtyBibPartsValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyBibParts" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyBibParts" name="invQtyBibParts" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_DTS'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteDts" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteDts" name="inventoryIdAteDts" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteDts}" var="group">
                                                    <option invQtyAteDtsValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteDts" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteDts" name="invQtyAteDts" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_FET'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteFet" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteFet" name="inventoryIdAteFet" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteFet}" var="group">
                                                    <option invQtyAteFetValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteFet" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteFet" name="invQtyAteFet" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_PFT'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAtePft" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAtePft" name="inventoryIdAtePft" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAtePft}" var="group">
                                                    <option invQtyAtePftValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAtePft" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAtePft" name="invQtyAtePft" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_TESEC'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteTesec" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteTesec" name="inventoryIdAteTesec" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteTesec}" var="group">
                                                    <option invQtyAteTesecValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteTesec" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteTesec" name="invQtyAteTesec" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_TESTFIXTURE'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteTest" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteTest" name="inventoryIdAteTest" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteTest}" var="group">
                                                    <option invQtyAteTestValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteTest" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteTest" name="invQtyAteTest" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_ETS'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteEts" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteEts" name="inventoryIdAteEts" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteEts}" var="group">
                                                    <option invQtyAteEtsValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteEts" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteEts" name="invQtyAteEts" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_ESD'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteEsd" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteEsd" name="inventoryIdAteEsd" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteEsd}" var="group">
                                                    <option invQtyAteEsdValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteEsd" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteEsd" name="invQtyAteEsd" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'ATE_SPAREPART_ACC'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdAteAcc" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdAteAcc" name="inventoryIdAteAcc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListAteAcc}" var="group">
                                                    <option invQtyAteAccValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyAteAcc" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyAteAcc" name="invQtyAteAcc" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_GENERAL'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpGeneral" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpGeneral" name="inventoryIdEqpGeneral" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpGeneral}" var="group">
                                                    <option invQtyEqpGeneralValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpGeneral" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpGeneral" name="invQtyEqpGeneral" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_H3TRB_AC_HAST'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpHast" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpHast" name="inventoryIdEqpHast" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpHast}" var="group">
                                                    <option invQtyEqpHastValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpHast" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpHast" name="invQtyEqpHast" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_HTS_HTB_WF'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpWf" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpWf" name="inventoryIdEqpWf" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpWf}" var="group">
                                                    <option invQtyEqpWfValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpWf" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpWf" name="invQtyEqpWf" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_IOL'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpIol" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpIol" name="inventoryIdEqpIol" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpIol}" var="group">
                                                    <option invQtyEqpIolValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpIol" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpIol" name="invQtyEqpIol" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_TC_PTC'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpPtc" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpPtc" name="inventoryIdEqpPtc" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpPtc}" var="group">
                                                    <option invQtyEqpPtcValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpPtc" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpPtc" name="invQtyEqpPtc" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_FOL'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpFol" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpFol" name="inventoryIdEqpFol" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpFol}" var="group">
                                                    <option invQtyEqpFolValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpFol" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpFol" name="invQtyEqpFol" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${equipmentType1 == 'EQP_SPAREPART_BLR'}">
                                    <div class="form-group" id="inventorytraylistdiv" >
                                        <label for="inventoryIdEqpBlr" class="col-lg-2 control-label">Hardware ID * </label>
                                        <div class="col-lg-7">                                      
                                            <select id="inventoryIdEqpBlr" name="inventoryIdEqpBlr" class="js-example-basic-single" style="width: 100%">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${inventoryListEqpBlr}" var="group">
                                                    <option invQtyEqpBlrValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;${group.mpNo}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label for="invQtyEqpBlr" class="col-lg-1 control-label">Quantity:</label>
                                        <div class="col-lg-1">
                                            <input type="text" class="form-control" id="invQtyEqpBlr" name="invQtyEqpBlr" value="" readonly >
                                        </div>
                                    </div>
                                </c:if>
                            </c:if>

                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${whRequest.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/dh/dhRequest/request" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="" id="submit" class="btn btn-primary">Send</button>
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

                $("form").keyup(function () {
                    if (!($('#equipmentType').val() === "PCB" && $('#requestType').val() === "Rel Lab" && $("#quantitytest").val() === "0")) {
                        $("#submit").removeAttr('disabled');
                    } else {
                        $("#submit").attr("disabled", true);
                    }
                });

                $("form").mouseup(function () {
                    if (!($('#equipmentType').val() === "PCB" && $('#requestType').val() === "Rel Lab" && $("#quantitytest").val() === "0")) {
                        $("#submit").removeAttr('disabled');
                    } else {
                        $("#submit").attr("disabled", true);
                    }
                });

                $('#pcbCtrQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbAQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbBQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbCQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbType').change(function () {
                    $('#maxQtyPcbType').val($('option:selected', this).attr('pcbTypeQty'));
                });
                $('#equipmentIdpcbCtr').change(function () {
                    $('#maxQtyCtr').val($('option:selected', this).attr('pcbCtrquantity'));
                    $('#maxQtyCtr1').val($('option:selected', this).attr('pcbCtrquantity'));
                    if ($(this).val() === "") {
                        $('#maxQtyCtr1').val("0");
                    }
                });
                $('#equipmentIdpcbA').change(function () {
                    $('#maxQtyA').val($('option:selected', this).attr('pcbAquantity'));
                    $('#maxQtyA1').val($('option:selected', this).attr('pcbAquantity'));
                    if ($(this).val() === "") {
                        $('#maxQtyA1').val("0");
                    }
                });
                $('#equipmentIdpcbB').change(function () {
                    $('#maxQtyB').val($('option:selected', this).attr('pcbBquantity'));
                    $('#maxQtyB1').val($('option:selected', this).attr('pcbBquantity'));
                    if ($(this).val() === "") {
                        $('#maxQtyB1').val("0");
                    }
                });
                $('#equipmentIdpcbC').change(function () {
                    $('#maxQtyC').val($('option:selected', this).attr('pcbCquantity'));
                    $('#maxQtyC1').val($('option:selected', this).attr('pcbCquantity'));
                    if ($(this).val() === "") {
                        $('#maxQtyC1').val("0");
                    }
                });
                $('#equipmentIdMb').change(function () {
                    $('#maxQtyMb').val($('option:selected', this).attr('mbquantity'));
                });
                $('#equipmentIdTray').change(function () {
                    $('#maxQtyTray').val($('option:selected', this).attr('trayquantity'));
                });
                $('#equipmentIdStencil').change(function () {
                    $('#maxQtyStencil').val($('option:selected', this).attr('stencilquantity'));
                });

                $('#equipmentIdLc').change(function () {
                    $('#maxQtyLc').val($('option:selected', this).attr('lcquantity'));
                });
                $('#equipmentIdLc1').change(function () {
                    $('#maxQtyLc1').val($('option:selected', this).attr('lc1quantity'));
                });
                $('#equipmentIdPc').change(function () {
                    $('#maxQtyPc').val($('option:selected', this).attr('pcquantity'));
                });
                $('#equipmentIdPc1').change(function () {
                    $('#maxQtyPc1').val($('option:selected', this).attr('pc1quantity'));
                });
                $('#equipmentIdBibParts').change(function () {
                    $('#maxQtyBibParts').val($('option:selected', this).attr('bibPartsquantity'));
                });
                $('#equipmentIdAteDts').change(function () {
                    $('#maxQtyAteDts').val($('option:selected', this).attr('ateDtsquantity'));
                });
                $('#equipmentIdAteFet').change(function () {
                    $('#maxQtyAteFet').val($('option:selected', this).attr('ateFetquantity'));
                });
                $('#equipmentIdAtePft').change(function () {
                    $('#maxQtyAtePft').val($('option:selected', this).attr('atePftquantity'));
                });
                $('#equipmentIdAteTesec').change(function () {
                    $('#maxQtyAteTesec').val($('option:selected', this).attr('ateTesecquantity'));
                });
                $('#equipmentIdAteTest').change(function () {
                    $('#maxQtyAteTest').val($('option:selected', this).attr('ateTestquantity'));
                });
                $('#equipmentIdAteEts').change(function () {
                    $('#maxQtyAteEts').val($('option:selected', this).attr('ateEtsquantity'));
                });
                $('#equipmentIdAteEsd').change(function () {
                    $('#maxQtyAteEsd').val($('option:selected', this).attr('ateEsdquantity'));
                });
                $('#equipmentIdAteAcc').change(function () {
                    $('#maxQtyAteAcc').val($('option:selected', this).attr('ateAccquantity'));
                });
                $('#equipmentIdEqpGeneral').change(function () {
                    $('#maxQtyEqpGeneral').val($('option:selected', this).attr('eqpGeneralquantity'));
                });
                $('#equipmentIdEqpWf').change(function () {
                    $('#maxQtyEqpWf').val($('option:selected', this).attr('eqpWfquantity'));
                });
                $('#equipmentIdEqpHast').change(function () {
                    $('#maxQtyEqpHast').val($('option:selected', this).attr('eqpHastquantity'));
                });
                $('#equipmentIdEqpIol').change(function () {
                    $('#maxQtyEqpIol').val($('option:selected', this).attr('eqpIolquantity'));
                });
                $('#equipmentIdEqpFol').change(function () {
                    $('#maxQtyEqpFol').val($('option:selected', this).attr('eqpFolquantity'));
                });
                $('#equipmentIdEqpPtc').change(function () {
                    $('#maxQtyEqpPtc').val($('option:selected', this).attr('eqpPtcquantity'));
                });
                $('#equipmentIdEqpBlr').change(function () {
                    $('#maxQtyEqpBlr').val($('option:selected', this).attr('eqpBlrquantity'));
                });

                $('#inventoryIdPcb').change(function () {
                    $('#invQtyPcb').val($('option:selected', this).attr('invQtyPcbValue'));
                    $('#quantityA').val($('option:selected', this).attr('invQtyPcbAValue'));
                    $('#quantityB').val($('option:selected', this).attr('invQtyPcbBValue'));
                    $('#quantityC').val($('option:selected', this).attr('invQtyPcbCValue'));
                    $('#quantityCtr').val($('option:selected', this).attr('invQtyPcbCtrValue'));
                });
                $('#inventoryIdMb').change(function () {
                    $('#invQtyMb').val($('option:selected', this).attr('invQtyMbValue'));
                });
                $('#inventoryIdTray').change(function () {
                    $('#invQtyTray').val($('option:selected', this).attr('invQtyTrayValue'));
                });
                $('#inventoryIdStencil').change(function () {
                    $('#invQtyStencil').val($('option:selected', this).attr('invQtyStencilValue'));
                });
                $('#inventoryIdLc').change(function () {
                    $('#invQtyLc').val($('option:selected', this).attr('invQtyLcValue'));
                });
                $('#inventoryIdPc').change(function () {
                    $('#invQtyPc').val($('option:selected', this).attr('invQtyPcValue'));
                });
                $('#inventoryIdLc1').change(function () {
                    $('#invQtyPc1').val($('option:selected', this).attr('invQtyPc1Value'));
                    $('#invQtyLc1').val($('option:selected', this).attr('invQtyLc1Value'));
                    $('#inventoryIdPc1').val($('option:selected', this).attr('invPc1Value'));
                });

                $('#inventoryIdBibParts').change(function () {
                    $('#invQtyBibParts').val($('option:selected', this).attr('invQtyBibPartsValue'));
                });
                $('#inventoryIdAteDts').change(function () {
                    $('#invQtyAteDts').val($('option:selected', this).attr('invQtyAteDtsValue'));
                });
                $('#inventoryIdAteFet').change(function () {
                    $('#invQtyAteFet').val($('option:selected', this).attr('invQtyAteFetValue'));
                });
                $('#inventoryIdAtePft').change(function () {
                    $('#invQtyAtePft').val($('option:selected', this).attr('invQtyAtePftValue'));
                });
                $('#inventoryIdAteTesec').change(function () {
                    $('#invQtyAteTesec').val($('option:selected', this).attr('invQtyAteTesecValue'));
                });
                $('#inventoryIdAteTest').change(function () {
                    $('#invQtyAteTest').val($('option:selected', this).attr('invQtyAteTestValue'));
                });
                $('#inventoryIdAteEts').change(function () {
                    $('#invQtyAteEts').val($('option:selected', this).attr('invQtyAteEtsValue'));
                });
                $('#inventoryIdAteEsd').change(function () {
                    $('#invQtyAteEsd').val($('option:selected', this).attr('invQtyAteEsdValue'));
                });
                $('#inventoryIdAteAcc').change(function () {
                    $('#invQtyAteAcc').val($('option:selected', this).attr('invQtyAteAccValue'));
                });
                $('#inventoryIdEqpGeneral').change(function () {
                    $('#invQtyEqpGeneral').val($('option:selected', this).attr('invQtyEqpGeneralValue'));
                });
                $('#inventoryIdEqpHast').change(function () {
                    $('#invQtyEqpHast').val($('option:selected', this).attr('invQtyEqpHastValue'));
                });
                $('#inventoryIdEqpWf').change(function () {
                    $('#invQtyEqpWf').val($('option:selected', this).attr('invQtyEqpWfValue'));
                });
                $('#inventoryIdEqpIol').change(function () {
                    $('#invQtyEqpIol').val($('option:selected', this).attr('invQtyEqpIolValue'));
                });
                $('#inventoryIdEqpPtc').change(function () {
                    $('#invQtyEqpPtc').val($('option:selected', this).attr('invQtyEqpPtcValue'));
                });
                $('#inventoryIdEqpFol').change(function () {
                    $('#invQtyEqpFol').val($('option:selected', this).attr('invQtyEqpFolValue'));
                });
                $('#inventoryIdEqpBlr').change(function () {
                    $('#invQtyEqpBlr').val($('option:selected', this).attr('invQtyEqpBlrValue'));
                });


                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        requestType: {
                            required: true
                        },
                        pcbType: {
                            required: true
                        },
                        equipmentType: {
                            required: true
                        },
                        retrievalReason: {
                            required: true
                        },
                        equipmentId: {
                            required: true
                        },
                        equipmentIdMb: {
                            required: true
                        },
                        inventoryIdMb: {
                            required: true
                        },
                        inventoryIdStencil: {
                            required: true
                        },
                        inventoryIdTray: {
                            required: true
                        },
                        inventoryIdPcb: {
                            required: true
                        },
                        equipmentIdStencil: {
                            required: true
                        },
                        equipmentIdTray: {
                            required: true
                        },
                        equipmentIdLc: {
                            required: true
                        },
                        equipmentIdLc1: {
                            required: true
                        },
                        equipmentIdPc: {
                            required: true
                        },
                        equipmentIdPc1: {
                            required: true
                        },
                        equipmentIdBibParts: {
                            required: true
                        },
                        equipmentIdAteDts: {
                            required: true
                        },
                        equipmentIdAteFet: {
                            required: true
                        },
                        equipmentIdAtePft: {
                            required: true
                        },
                        equipmentIdAteTesec: {
                            required: true
                        },
                        equipmentIdAteTest: {
                            required: true
                        },
                        equipmentIdAteEts: {
                            required: true
                        },
                        equipmentIdAteEsd: {
                            required: true
                        },
                        equipmentIdAteAcc: {
                            required: true
                        },
                        //sparepart
                        equipmentIdEqpGeneral: {
                            required: true
                        },
                        equipmentIdEqpHast: {
                            required: true
                        },
                        equipmentIdEqpWf: {
                            required: true
                        },
                        equipmentIdEqpPtc: {
                            required: true
                        },
                        equipmentIdEqpIol: {
                            required: true
                        },
                        equipmentIdEqpFol: {
                            required: true
                        },
                        equipmentIdEqpBlr: {
                            required: true
                        },
                        //inventory
                        inventoryIdLc: {
                            required: true
                        },
                        inventoryIdLc1: {
                            required: true
                        },
                        inventoryIdPc: {
                            required: true
                        },
                        inventoryIdBibParts: {
                            required: true
                        },
                        inventoryIdAteDts: {
                            required: true
                        },
                        inventoryIdAteFet: {
                            required: true
                        },
                        inventoryIdAtePft: {
                            required: true
                        },
                        inventoryIdAteTesec: {
                            required: true
                        },
                        inventoryIdAteTest: {
                            required: true
                        },
                        inventoryIdAteEts: {
                            required: true
                        },
                        inventoryIdAteEsd: {
                            required: true
                        },
                        inventoryIdAteAcc: {
                            required: true
                        },
                        //sparepart
                        inventoryIdEqpGeneral: {
                            required: true
                        },
                        inventoryIdEqpHast: {
                            required: true
                        },
                        inventoryIdEqpWf: {
                            required: true
                        },
                        inventoryIdEqpPtc: {
                            required: true
                        },
                        inventoryIdEqpIol: {
                            required: true
                        },
                        inventoryIdEqpFol: {
                            required: true
                        },
                        inventoryIdEqpBlr: {
                            required: true
                        },
                        remarks: {
                            required: true
                        },
                        pcbCtrQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyCtr1').val());
                            }
                        },
//                        equipmentIdpcbA: {
//                            required: true
//                        },
                        pcbAQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyA1').val());
                            }
                        },
//                        equipmentIdpcbB: {
//                            required: true
//                        },
                        pcbBQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyB1').val());
                            }
                        },
//                        equipmentIdpcbC: {
//                            required: true
//                        },
                        pcbCQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyC1').val());
                            }
                        },
                        quantitytest: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPcbType').val());
                            }
                        },
                        quantityLc: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyLc').val());
                            }
                        },
                        quantityLc1: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyLc1').val());
                            }
                        },
                        quantityPc: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPc').val());
                            }
                        },
                        quantityPc1: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPc1').val());
                            }
                        },
                        quantityBibParts: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyBibParts').val());
                            }
                        },
                        quantityAteDts: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteDts').val());
                            }
                        },
                        quantityAteFet: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteFet').val());
                            }
                        },
                        quantityAtePft: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAtePft').val());
                            }
                        },
                        quantityAteTesec: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteTesec').val());
                            }
                        },
                        quantityAteTest: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteTest').val());
                            }
                        },
                        quantityAteEts: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteEts').val());
                            }
                        },
                        quantityAteEsd: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteEsd').val());
                            }
                        },
                        quantityAteAcc: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteAcc').val());
                            }
                        },
                        //sparepart
                        quantityEqpGeneral: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpGeneral').val());
                            }
                        },
                        quantityEqpWf: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpWf').val());
                            }
                        },
                        quantityEqpPtc: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpPtc').val());
                            }
                        },
                        quantityEqpIol: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpIol').val());
                            }
                        },
                        quantityEqpHast: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpHast').val());
                            }
                        },
                        quantityEqpFol: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpFol').val());
                            }
                        },
                        quantityEqpBlr: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyEqpBlr').val());
                            }
                        },
                        quantity: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyTray').val());
                            }
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });
//          



        </script>
    </s:layout-component>
</s:layout-render>