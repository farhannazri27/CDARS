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

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Edit Hardware Request</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>Hardware Request Information</h2>
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRequest/update" method="post">
                            <input type="hidden" name="id" value="${whRequest.id}" />
                            <div class="form-group">
                                <label for="requestBy" class="col-lg-2 control-label">Request By</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestBy" name="requestBy" placeholder="Request For" value="${whRequest.requestedBy}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestType" class="col-lg-2 control-label">Request For</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="requestType" name="requestType" value="${whRequest.requestType}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" value="${whRequest.equipmentType}" readonly>
                                </div>
                            </div>
                            <c:if test="${whRequest.equipmentType == 'Motherboard'}">
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
                            <c:if test="${whRequest.equipmentType == 'Stencil'}">
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
                            <c:if test="${whRequest.equipmentType == 'Tray'}">
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
                                        <input type="text" class="form-control" id="quantity" name="quantity" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>

                            <c:if test="${whRequest.equipmentType == 'PCB'}">
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
                                        <input type="text" class="form-control" id="maxQtyPcbType" name="maxQtyPcbType" value="${PcbLimitQty}" readonly >
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
                                        <input type="text" class="form-control" id="pcbCtrQty" name="pcbCtrQty" value="${whRequest.pcbCtrQty}">
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
                                        <input type="text" class="form-control" id="pcbAQty" name="pcbAQty" value="${whRequest.pcbAQty}">
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
                                        <input type="text" class="form-control" id="pcbBQty" name="pcbBQty" value="${whRequest.pcbBQty}">
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
                                        <input type="text" class="form-control" id="pcbCQty" name="pcbCQty" value="${whRequest.pcbCQty}">
                                    </div>
                                </div> 
                                <div class="form-group" id="quantitytestdiv" >
                                    <label for="quantitytest" class="col-lg-2 control-label">Total Quantity</label>
                                    <div class="col-lg-1">
                                        <input type="text" class="form-control" id="quantitytest" name="quantitytest" value="${whRequest.quantity}" readonly>
                                    </div>
                                </div> 
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'Load Card'}">
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
                                        <input type="text" class="form-control" id="quantityLc" name="quantityLc" value="${whRequest.loadCardQty}">
                                    </div>
                                </div> 
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'Program Card'}">
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
                                        <input type="text" class="form-control" id="quantityPc" name="quantityPc" value="${whRequest.programCardQty}">
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'Load Card & Program Card'}">
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
                                        <input type="text" class="form-control" id="quantityLc1" name="quantityLc1" value="${whRequest.loadCardQty}">
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
                                        <input type="text" class="form-control" id="quantityPc1" name="quantityPc1" value="${whRequest.programCardQty}">
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'BIB Parts'}">
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
                                <div class="form-group" >
                                    <label for="quantityBibParts" class="col-lg-2 control-label">Quantity *</label>
                                    <div class="col-lg-2">
                                        <input type="text" class="form-control" id="quantityBibParts" name="quantityBibParts" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_DTS'}">
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
                                        <input type="text" class="form-control" id="quantityAteDts" name="quantityAteDts" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <!--ate-->
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_FET'}">
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
                                        <input type="text" class="form-control" id="quantityAteFet" name="quantityAteFet" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_PFT'}">
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
                                        <input type="text" class="form-control" id="quantityAtePft" name="quantityAtePft" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_TESEC'}">
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
                                        <input type="text" class="form-control" id="quantityAteTesec" name="quantityAteTesec" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_TESTFIXTURE'}">
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
                                        <input type="text" class="form-control" id="quantityAteTest" name="quantityAteTest" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_ETS'}">
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
                                        <input type="text" class="form-control" id="quantityAteEts" name="quantityAteEts" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_ESD'}">
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
                                        <input type="text" class="form-control" id="quantityAteEsd" name="quantityAteEsd" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'ATE_SPAREPART_ACC'}">
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
                                        <input type="text" class="form-control" id="quantityAteAcc" name="quantityAteAcc" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <!--sparepart-->
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_GENERAL'}">
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
                                        <input type="text" class="form-control" id="quantityEqpGeneral" name="quantityEqpGeneral" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_H3TRB_AC_HAST'}">
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
                                        <input type="text" class="form-control" id="quantityEqpHast" name="quantityEqpHast" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_HTS_HTB_WF'}">
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
                                        <input type="text" class="form-control" id="quantityEqpWf" name="quantityEqpWf" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_IOL'}">
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
                                        <input type="text" class="form-control" id="quantityEqpIol" name="quantityEqpIol" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_TC_PTC'}">
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
                                        <input type="text" class="form-control" id="quantityEqpPtc" name="quantityEqpPtc" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_FOL'}">
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
                                        <input type="text" class="form-control" id="quantityEqpFol" name="quantityEqpFol" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>
                            <c:if test="${whRequest.equipmentType == 'EQP_SPAREPART_BLR'}">
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
                                        <input type="text" class="form-control" id="quantityEqpBlr" name="quantityEqpBlr" value="${whRequest.quantity}">
                                    </div>
                                </div>  
                            </c:if>

                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-2 control-label" id="remarksLabel">Remarks </label>
                                <div class="col-lg-5" id="remarksDiv2">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                </div>
                                <div class="col-lg-5">
                                    <textarea class="form-control" rows="5" id="remarksLog" name="remarksLog" readonly>${whRequest.remarksLog}</textarea>
                                </div>
                            </div>
                            <div class="form-group" id="statusDiv" hidden>
                                <label for="status" class="col-lg-2 control-label">Status</label>                                     
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="status" name="status" value="${whRequest.finalApprovedStatus}" readonly>
                                </div>
                            </div>
                            <div class="form-group"id="approvalRemarksDiv" hidden>
                                <label for="remarksApprover" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarksApprover" name="remarksApprover" readonly>${whRequest.remarksApprover}</textarea>
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

                var element = $('#status');
                if (element.val() === "Approved") {
                    $("#remarksDiv2").hide();
                    $("#statusDiv").show();
                    $("#submit").attr("disabled", true);
                    $("#status").attr("readonly", true);
                    $("#quantity").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);
                    $("#statusDiv").show();
                    $("#approvalRemarksDiv").show();
                    $(".js-example-basic-single").prop("readonly", true);
                    $("#pcbCtrQty").attr("readonly", true);
                    $("#pcbAQty").attr("readonly", true);
                    $("#pcbBQty").attr("readonly", true);
                    $("#pcbCQty").attr("readonly", true);
                    $("#remarks").attr("readonly", true);

//                } else if (element.val() === "Waiting for Approval") { //original 3/11/16
//                } else if (element.val() === "Pending Approval") { //as requested 2/11/16
                } else if (element.val() === "") { //29/11/16
                    $("#statusDiv").hide();
                    $("#approvalRemarksDiv").hide();
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);
                } else if (element.val() === "Not Approved") {
                    $("#remarksDiv2").hide();
                    $("#statusDiv").show();
                    $("#approvalRemarksDiv").show();
                    $(".js-example-basic-single").prop("readonly", true);
                    $("#pcbCtrQty").attr("readonly", true);
                    $("#pcbAQty").attr("readonly", true);
                    $("#pcbBQty").attr("readonly", true);
                    $("#pcbCQty").attr("readonly", true);
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);
                    $("#submit").attr("disabled", true);
                    $("#remarks").attr("readonly", true);
                } else {
                    $("#statusDiv").hide();
                    $("#approvalRemarksDiv").hide();
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);

                }


                $('#pcbType').val("<c:out value="${whRequest.pcbType}"/>");
                $('#equipmentIdMb').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdTray').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdStencil').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdpcbA').val("<c:out value="${whRequest.pcbA}"/>");
                $('#equipmentIdpcbB').val("<c:out value="${whRequest.pcbB}"/>");
                $('#equipmentIdpcbC').val("<c:out value="${whRequest.pcbC}"/>");
                $('#equipmentIdpcbCtr').val("<c:out value="${whRequest.pcbCtr}"/>");

                $('#equipmentIdLc').val("<c:out value="${whRequest.loadCard}"/>");
                $('#equipmentIdLc1').val("<c:out value="${whRequest.loadCard}"/>");
                $('#equipmentIdPc').val("<c:out value="${whRequest.programCard}"/>");
                $('#equipmentIdPc1').val("<c:out value="${whRequest.programCard}"/>");
                $('#equipmentIdBibParts').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAteDts').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAteFet').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAtePft').val("<c:out value="${whRequest.equipmentId}"/>");

                $('#equipmentIdAteTesec').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAteTest').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAteEts').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAteEsd').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdAteAcc').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdEqpGeneral').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdEqpHast').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdEqpPtc').val("<c:out value="${whRequest.equipmentId}"/>");

                $('#equipmentIdEqpWf').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdEqpIol').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdEqpFol').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdEqpBlr').val("<c:out value="${whRequest.equipmentId}"/>");

                var trayId = $('#equipmentIdTray');
                $('#maxQtyTray').val($('option:selected', trayId).attr('trayquantity'));

                var stencilId = $('#equipmentIdStencil');
                $('#maxQtyStencil').val($('option:selected', stencilId).attr('stencilquantity'));

                var mbId = $('#equipmentIdMb');
                $('#maxQtyMb').val($('option:selected', mbId).attr('mbquantity'));

                var bibPartsId = $('#equipmentIdBibParts');
                $('#maxQtyBibParts').val($('option:selected', bibPartsId).attr('bibPartsquantity'));

                var lcId = $('#equipmentIdLc');
                $('#maxQtyLc').val($('option:selected', lcId).attr('lcquantity'));

                var lc1Id = $('#equipmentIdLc1');
                $('#maxQtyLc1').val($('option:selected', lc1Id).attr('lc1quantity'));

                var pcId = $('#equipmentIdPc');
                $('#maxQtyPc').val($('option:selected', pcId).attr('pcquantity'));

                var pc1Id = $('#equipmentIdPc1');
                $('#maxQtyPc1').val($('option:selected', pc1Id).attr('pc1quantity'));

                var ateDtsId = $('#equipmentIdAteDts');
                $('#maxQtyAteDts').val($('option:selected', ateDtsId).attr('ateDtsquantity'));

                var ateFetId = $('#equipmentAteFet');
                $('#maxQtyAteFet').val($('option:selected', ateFetId).attr('ateFetquantity'));

                var atePftId = $('#equipmentIdAtePft');
                $('#maxQtyAtePft').val($('option:selected', atePftId).attr('atePftquantity'));

                var ateTesecId = $('#equipmentIdAteTesec');
                $('#maxQtyAteTesec').val($('option:selected', ateTesecId).attr('ateTesecquantity'));

                var ateTestId = $('#equipmentIdAteTest');
                $('#maxQtyAteTest').val($('option:selected', ateTestId).attr('ateTestquantity'));

                var ateEsdId = $('#equipmentIdAteEsd');
                $('#maxQtyAteEsd').val($('option:selected', ateEsdId).attr('ateEsdquantity'));

                var ateEtsId = $('#equipmentIdAteEts');
                $('#maxQtyAteEts').val($('option:selected', ateEtsId).attr('ateEtsquantity'));

                var ateAccId = $('#equipmentIdAteAcc');
                $('#maxQtyAteAcc').val($('option:selected', ateAccId).attr('ateAccquantity'));

                var eqpGeneralId = $('#equipmentIdEqpGeneral');
                $('#maxQtyEqpGeneral').val($('option:selected', eqpGeneralId).attr('eqpGeneralquantity'));

                var eqpHastId = $('#equipmentIdEqpHast');
                $('#maxQtyEqpHast').val($('option:selected', eqpHastId).attr('eqpHastquantity'));

                var eqpPtcId = $('#equipmentIdEqpPtc');
                $('#maxQtyEqpPtc').val($('option:selected', eqpPtcId).attr('eqpPtcquantity'));

                var eqpWfId = $('#equipmentIdEqpWf');
                $('#maxQtyEqpWf').val($('option:selected', eqpWfId).attr('eqpWfquantity'));

                var eqpIolId = $('#equipmentIdEqpIol');
                $('#maxQtyEqpIol').val($('option:selected', eqpIolId).attr('eqpIolquantity'));

                var eqpFolId = $('#equipmentIdEqpFol');
                $('#maxQtyEqpFol').val($('option:selected', eqpFolId).attr('eqpFolquantity'));

                var eqpBlrId = $('#equipmentIdEqpBlr');
                $('#maxQtyEqpBlr').val($('option:selected', eqpBlrId).attr('eqpBlrquantity'));

                var pcbA = $('#equipmentIdpcbA');
                $('#maxQtyA').val($('option:selected', pcbA).attr('pcbAquantity'));

                if (pcbA.val() === "") {
                    $('#maxQtyA1').val("0");
                } else {
                    $('#maxQtyA1').val($('option:selected', pcbA).attr('pcbAquantity'));
                }
                var pcbB = $('#equipmentIdpcbB');
                $('#maxQtyB').val($('option:selected', pcbB).attr('pcbBquantity'));
                if (pcbB.val() === "") {
                    $('#maxQtyB1').val("0");
                } else {
                    $('#maxQtyB1').val($('option:selected', pcbB).attr('pcbBquantity'));
                }
                var pcbC = $('#equipmentIdpcbC');
                $('#maxQtyC').val($('option:selected', pcbC).attr('pcbCquantity'));
                if (pcbC.val() === "") {
                    $('#maxQtyC1').val("0");
                } else {
                    $('#maxQtyC1').val($('option:selected', pcbC).attr('pcbCquantity'));
                }
                var pcbCtr = $('#equipmentIdpcbCtr');
                $('#maxQtyCtr').val($('option:selected', pcbCtr).attr('pcbCtrquantity'));
                if (pcbCtr.val() === "") {
                    $('#maxQtyCtr1').val("0");
                } else {
                    $('#maxQtyCtr1').val($('option:selected', pcbCtr).attr('pcbCtrquantity'));
                }


                $("form").keyup(function () {
//                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Waiting for Approval") { //original 3/11/16
                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Pending Approval Approval") { //as requested 2/11/16
                        $("#submit").attr("disabled", true);
                    } else if ($('#status').val() === "Approved" || $('#status').val() === "Not Approved") {
                        $("#submit").attr("disabled", true);
                    } else {
                        $("#submit").removeAttr('disabled');
                    }

                });

                $("form").mouseup(function () {
//                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Waiting for Approval") { //original 3/11/16
                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Pending Approval") { //as requested 2/11/16
                        $("#submit").attr("disabled", true);
                    } else if ($('#status').val() === "Approved" || $('#status').val() === "Not Approved") {
                        $("#submit").attr("disabled", true);
                    } else {
                        $("#submit").removeAttr('disabled');
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


                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                var validator = $("#edit_hardwarequest_form").validate({
                    rules: {
                        equipmentId: {
                            required: true
                        },
                        equipmentIdMb: {
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
                        quantityLc: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyLc').val());
                            }
                        },
                        quantityLc1: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyLc1').val());
                            }
                        },
                        quantityPc: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPc').val());
                            }
                        },
                        quantityPc1: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPc1').val());
                            }
                        },
                        quantityBibParts: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyBibParts').val());
                            }
                        },
                        quantityAteDts: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteDts').val());
                            }
                        },
                        quantityAteFet: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteFet').val());
                            }
                        },
                        quantityAtePft: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAtePft').val());
                            }
                        },
                        quantityAteTesec: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteTesec').val());
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
                        quantityAteTest: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyAteTest').val());
                            }
                        },
                        quantitytest: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPcbType').val());
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

//                var element = $('#equipmentType');
//                if (element.val() === "Motherboard") {
//                    $("#mblistdiv").show();
//                    $("#pcbTypeDiv").hide();
//                    $("#stencillistdiv").hide();
//                    $("#traylistdiv").hide();
//                    $("#pcbAlistdiv").hide();
//                    $("#pcbAQtydiv").hide();
//                    $("#pcbBlistdiv").hide();
//                    $("#pcbBQtydiv").hide();
//                    $("#pcbClistdiv").hide();
//                    $("#pcbCQtydiv").hide();
//                    $("#pcbCtrlistdiv").hide();
//                    $("#pcbCtrQtydiv").hide();
//                    $("#quantity").val("");
//                    $("#quantitytest").val("0");
//                    $("#quantitydiv").hide();
//                    $("#quantitytestdiv").hide();
//                    $("#pcbType").val("").trigger('change');
//                    $("#equipmentIdStencil").val("").trigger('change');
//                    $("#equipmentIdTray").val("").trigger('change');
//                    $("#equipmentIdpcbA").val("").trigger('change');
//                    $("#pcbAQty").val("0");
//                    $("#equipmentIdpcbB").val("").trigger('change');
//                    $("#pcbBQty").val("0");
//                    $("#equipmentIdpcbC").val("").trigger('change');
//                    $("#pcbCQty").val("0");
//                    $("#equipmentIdpcbCtr").val("").trigger('change');
//                    $("#pcbCtrQty").val("0");
//                    $("#quantity").val("");
//                } else if (element.val() === "Stencil") {
//                    $("#stencillistdiv").show();
//                    $("#pcbTypeDiv").hide();
//                    $("#mblistdiv").hide();
//                    $("#traylistdiv").hide();
//                    $("#pcbAlistdiv").hide();
//                    $("#pcbAQtydiv").hide();
//                    $("#pcbBlistdiv").hide();
//                    $("#pcbBQtydiv").hide();
//                    $("#pcbClistdiv").hide();
//                    $("#pcbCQtydiv").hide();
//                    $("#pcbCtrlistdiv").hide();
//                    $("#pcbCtrQtydiv").hide();
//                    $("#quantitydiv").hide();
//                    $("#quantitytestdiv").hide();
//                    $("#pcbType").val("").trigger('change');
//                    $("#equipmentIdMb").val("").trigger('change');
//                    $("#equipmentIdTray").val("").trigger('change');
//                    $("#equipmentIdpcbA").val("").trigger('change');
//                    $("#pcbAQty").val("0");
//                    $("#equipmentIdpcbB").val("").trigger('change');
//                    $("#pcbBQty").val("0");
//                    $("#equipmentIdpcbC").val("").trigger('change');
//                    $("#pcbCQty").val("0");
//                    $("#equipmentIdpcbCtr").val("").trigger('change');
//                    $("#pcbCtrQty").val("0");
//                    $("#quantity").val("");
//                    $("#quantitytest").val("0");
//                } else if (element.val() === "Tray") {
//                    $("#traylistdiv").show();
//                    $("#quantitydiv").show();
//                    $("#quantitytestdiv").hide();
//                    $("#quantityABdiv").hide();
//                    $("#quantityCCtrdiv").hide();
//                    $("#pcbTypeDiv").hide();
//                    $("#listdiv").hide();
//                    $("#stencillistdiv").hide();
//                    $("#mblistdiv").hide();
//                    $("#pcbAlistdiv").hide();
//                    $("#pcbAQtydiv").hide();
//                    $("#pcbBlistdiv").hide();
//                    $("#pcbBQtydiv").hide();
//                    $("#pcbClistdiv").hide();
//                    $("#pcbCQtydiv").hide();
//                    $("#pcbCtrlistdiv").hide();
//                    $("#pcbCtrQtydiv").hide();
//                    $("#pcbType").val("").trigger('change');
//                    $("#equipmentIdMb").val("").trigger('change');
//                    $("#equipmentIdStencil").val("").trigger('change');
//                    $("#equipmentIdpcbA").val("").trigger('change');
//                    $("#pcbAQty").val("0");
//                    $("#equipmentIdpcbB").val("").trigger('change');
//                    $("#pcbBQty").val("0");
//                    $("#equipmentIdpcbC").val("").trigger('change');
//                    $("#pcbCQty").val("0");
//                    $("#equipmentIdpcbCtr").val("").trigger('change');
//                    $("#pcbCtrQty").val("0");
//                    $("#quantitytest").val("0");
//                } else if (element.val() === "PCB") {
//                    $("#pcbTypeDiv").show();
//                    $("#pcbAlistdiv").show();
//                    $("#pcbAQtydiv").show();
//                    $("#pcbBlistdiv").show();
//                    $("#pcbBQtydiv").show();
//                    $("#pcbClistdiv").show();
//                    $("#pcbCQtydiv").show();
//                    $("#pcbCtrlistdiv").show();
//                    $("#pcbCtrQtydiv").show();
//                    $("#quantitytestdiv").show();
//                    $("#quantityABdiv").hide();
//                    $("#quantityCCtrdiv").hide();
//                    $("#quantitydiv").hide();
//                    $("#listdiv").hide();
//                    $("#stencillistdiv").hide();
//                    $("#mblistdiv").hide();
//                    $("#traylistdiv").hide();
//                    $("#equipmentIdMb").val("").trigger('change');
//                    $("#equipmentIdStencil").val("").trigger('change');
//                    $("#equipmentIdTray").val("").trigger('change');
//                } else {
//                    $("#pcbTypeDiv").hide();
//                    $("#pcbAlistdiv").hide();
//                    $("#pcbAQtydiv").hide();
//                    $("#pcbBlistdiv").hide();
//                    $("#pcbBQtydiv").hide();
//                    $("#pcbClistdiv").hide();
//                    $("#pcbCQtydiv").hide();
//                    $("#pcbCtrlistdiv").hide();
//                    $("#pcbCtrQtydiv").hide();
//                    $("#mblistdiv").hide();
//                    $("#stencillistdiv").hide();
//                    $("#traylistdiv").hide();
//                    $("#quantitydiv").hide();
//                    $("#quantitytestdiv").hide();
//                    $("#pcbType").val("").trigger('change');
//                    $("#equipmentIdMb").val("").trigger('change');
//                    $("#equipmentIdStencil").val("").trigger('change');
//                    $("#equipmentIdTray").val("").trigger('change');
//                    $("#equipmentIdpcbA").val("").trigger('change');
//                    $("#pcbAQty").val("0");
//                    $("#equipmentIdpcbB").val("").trigger('change');
//                    $("#pcbBQty").val("0");
//                    $("#equipmentIdpcbC").val("").trigger('change');
//                    $("#pcbCQty").val("0");
//                    $("#equipmentIdpcbCtr").val("").trigger('change');
//                    $("#pcbCtrQty").val("0");
//                    $("#quantity").val("");
//                    $("#quantitytest").val("0");
//                }
            });
        </script>
    </s:layout-component>
</s:layout-render>