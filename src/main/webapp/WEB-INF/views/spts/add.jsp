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
            <h1>Add Item</h1>
            <form id="add_item_form" class="form-horizontal" role="form" action="${contextPath}/spts/save" method="post">
                <div class="row">
                    <div class="col-lg-4">
                        <div class="main-box">
                            <h2>Item Information Part 1</h2>
                            <div class="form-group">
                                <label for="itemID" class="col-lg-4 control-label">Item ID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="itemID" name="itemID" placeholder="Item ID" value="${item.itemID}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="itemName" class="col-lg-4 control-label">Item Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="itemName" name="itemName" placeholder="Item Name" value="${item.itemName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="onHandQty" class="col-lg-4 control-label">On Hand Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="onHandQty" name="onHandQty" placeholder="On Hand Quantity" value="${item.onHandQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="prodQty" class="col-lg-4 control-label">Production Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="prodQty" name="prodQty" placeholder="Production Quantity" value="${item.prodQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="repairQty" class="col-lg-4 control-label">Repair Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="repairQty" name="repairQty" placeholder="Repair Quantity" value="${item.repairQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="otherQty" class="col-lg-4 control-label">Other Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="otherQty" name="otherQty" placeholder="Other Quantity" value="${item.otherQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="quarantineQty" class="col-lg-4 control-label">Quarantine Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="Quarantine Quantity" value="${item.quarantineQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="externalCleaningQty" class="col-lg-4 control-label">Ext. Cleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="externalCleaningQty" name="externalCleaningQty" placeholder="External Cleaning Quantity" value="${item.externalCleaningQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="externalRecleaningQty" class="col-lg-4 control-label">Ext. Recleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="externalRecleaningQty" name="externalRecleaningQty" placeholder="External Recleaning Quantity" value="${item.externalRecleaningQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="internalCleaningQty" class="col-lg-4 control-label">Int. Cleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="internalCleaningQty" name="internalCleaningQty" placeholder="Internal Cleaning Quantity" value="${item.internalCleaningQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="internalRecleaningQty" class="col-lg-4 control-label">Int. Recleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="internalRecleaningQty" name="internalRecleaningQty" placeholder="Internal Recleaning Quantity" value="${item.internalRecleaningQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="storageFactoryQty" class="col-lg-4 control-label">Storage Factory Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="Storage Factory Quantity" value="${item.storageFactoryQty}">
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="main-box">
                            <h2>Item Information Part 2</h2>
                            <div class="form-group">
                                <label for="minQty" class="col-lg-4 control-label">Min Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="minQty" name="minQty" placeholder="Min Quantity" value="${item.minQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="maxQty" class="col-lg-4 control-label">Max Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="maxQty" name="maxQty" placeholder="Max Quantity" value="${item.maxQty}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="unit" class="col-lg-4 control-label">Unit *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="unit" name="unit" placeholder="Unit" value="${item.unit}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="unitCost" class="col-lg-4 control-label">Unit Cost *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="unitCost" name="unitCost" placeholder="Unit Cost" value="${item.unitCost}">
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label for="rack" class="col-lg-4 control-label">Rack *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="rack" name="rack" placeholder="Rack" value="${item.rack}">
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label for="rack" class="col-lg-4 control-label">Rack *</label>
                                <div class="col-lg-8">
                                    <select id="rack" name="rack" class="form-control">
                                        <option value="" selected="">Select Rack...</option>
                                        <c:forEach items="${rackList}" var="rack">
                                            <option shelfnum="${rack.ShelfNum}" value="${rack.RackName}">${rack.RackName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="shelf" class="col-lg-4 control-label">Shelf *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="shelf" name="shelf" placeholder="Shelf" value="${item.shelf}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="model" class="col-lg-4 control-label">Model *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="model" name="model" placeholder="Model" value="${item.model}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-4 control-label">Equipment Type *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" placeholder="Equipment Type" value="${item.equipmentType}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="stressType" class="col-lg-4 control-label">Stress Type *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="stressType" name="stressType" placeholder="Stress Type" value="${item.stressType}">
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label for="isCritical" class="col-lg-4 control-label">Is Critical *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="isCritical" name="isCritical" placeholder="Is Critical" value="${item.isCritical}">
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label for="isCritical" class="col-lg-4 control-label">Is Critical *</label>
                                <div class="col-lg-8">
                                    <select id="isCritical" name="isCritical" class="form-control">
                                        <option value="" selected="">Select Is Critical...</option>
                                        <option value="true">TRUE</option>
                                        <option value="false">FALSE</option>
                                    </select>
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label for="isConsumeable" class="col-lg-4 control-label">Is Consumeable *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="isConsumeable" name="isConsumeable" placeholder="Is Consumeable" value="${item.isConsumeable}">
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label for="isConsumeable" class="col-lg-4 control-label">Is Consumeable *</label>
                                <div class="col-lg-8">
                                    <select id="isConsumeable" name="isConsumeable" class="form-control">
                                        <option value="" selected="">Select Is Consumeable...</option>
                                        <option value="true">TRUE</option>
                                        <option value="false">FALSE</option>
                                    </select>
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label for="itemType" class="col-lg-4 control-label">Item Type *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="itemType" name="itemType" placeholder="Item Type" value="${item.itemType}">
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label for="itemType" class="col-lg-4 control-label">Item Type *</label>
                                <div class="col-lg-8">
                                    <select id="itemType" name="itemType" class="form-control">
                                        <option value="" selected="">Select Item Type...</option>
                                        <c:forEach items="${itemTypeList}" var="itemType">
                                            <option pkid="${itemType.PKID}" value="${itemType.ItemType}">${itemType.ItemType}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="main-box">
                            <h2>Item Information Part 3</h2>
                            <!--<div class="form-group">
                                <label for="cardType" class="col-lg-4 control-label">Card Type *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="cardType" name="cardType" placeholder="Card Type" value="${item.cardType}">
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label for="cardType" class="col-lg-4 control-label">Card Type *</label>
                                <div class="col-lg-8">
                                    <select id="cardType" name="cardType" class="form-control">
                                        <option value="" selected="">Select Card Type...</option>
                                        <c:forEach items="${cardTypeList}" var="cardType">
                                            <option pkid="${cardType.PKID}" value="${cardType.CardType}">${cardType.CardType}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="bibPKID" class="col-lg-4 control-label">BIB PKID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="bibPKID" name="bibPKID" placeholder="BIB PKID" value="${item.bibPKID}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="bibCardPKID" class="col-lg-4 control-label">BIB Card PKID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="bibCardPKID" name="bibCardPKID" placeholder="BIB Card PKID" value="${item.bibCardPKID}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remarks" class="col-lg-4 control-label">Remarks *</label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" id="remarks" name="remarks" placeholder="Remarks" style="height:83px;"><c:out value="${item.remarks}"/></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="downtimeValue" class="col-lg-4 control-label">Downtime Value *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="downtimeValue" name="downtimeValue" placeholder="Downtime Value" value="${item.downtimeValue}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="downtimeUnit" class="col-lg-4 control-label">Downtime Unit *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="downtimeUnit" name="downtimeUnit" placeholder="Downtime Unit" value="${item.downtimeUnit}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="implementationCost" class="col-lg-4 control-label">Implementation Cost *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="implementationCost" name="implementationCost" placeholder="Implementation Cost" value="${item.implementationCost}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="manpowerValue" class="col-lg-4 control-label">Manpower Value *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="manpowerValue" name="manpowerValue" placeholder="Manpower Value" value="${item.manpowerValue}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="manpowerUnit" class="col-lg-4 control-label">Manpower Unit *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="manpowerUnit" name="manpowerUnit" placeholder="Manpower Unit" value="${item.manpowerUnit}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="complexityScore" class="col-lg-4 control-label">Complexity Score *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="complexityScore" name="complexityScore" placeholder="Complexity Score" value="${item.complexityScore}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="expirationDate" class="col-lg-4 control-label">Expiration Date *</label>
                                <div class="col-lg-8">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="expirationDate" class="form-control" id="datepickerDate" value="${item.expirationDate}">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="datepickerDate" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="main-box">
                            <a href="${contextPath}/spts" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                $('#datepickerDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });
                
                $('#rack').change(function () {
                    $('#shelf').val($('option:selected', this).attr('shelfnum'));
                });
                
                $('#itemType').change(function () {
                    $('#bibPKID').val($('option:selected', this).attr('pkid'));
                });
                
                $('#cardType').change(function () {
                    $('#bibCardPKID').val($('option:selected', this).attr('pkid'));
                });
                
                var validator = $("#add_item_form").validate({
                    rules: {
                        itemID: {
                            required: true
                        },
                        itemName: {
                            required: true
                        },
                        onHandQty: {
                            required: true
                        },
                        prodQty: {
                            required: true
                        },
                        repairQty: {
                            required: true
                        },
                        otherQty: {
                            required: true
                        },
                        quarantineQty: {
                            required: true
                        },
                        externalCleaningQty: {
                            required: true
                        },
                        externalRecleaningQty: {
                            required: true
                        },
                        internalCleaningQty: {
                            required: true
                        },
                        internalRecleaningQty: {
                            required: true
                        },
                        storageFactoryQty: {
                            required: true
                        },
                        minQty: {
                            required: true
                        },
                        maxQty: {
                            required: true
                        },
                        unit: {
                            required: true
                        },
                        unitCost: {
                            required: true
                        },
                        rack: {
                            required: true
                        },
                        shelf: {
                            required: true
                        },
                        model: {
                            required: true
                        },
                        equipmentType: {
                            required: true
                        },
                        stressType: {
                            required: true
                        },
                        isCritical: {
                            required: true
                        },
                        isConsumeable: {
                            required: true
                        },
                        itemType: {
                            required: true
                        },
                        cardType: {
                            required: true
                        },
                        bibPKID: {
                            required: true
                        },
                        bibCardPKID: {
                            required: true
                        },
                        remarks: {
                            required: true
                        },
                        downtimeValue: {
                            required: true
                        },
                        downtimeUnit: {
                            required: true
                        },
                        implementationCost: {
                            required: true
                        },
                        manpowerValue: {
                            required: true
                        },
                        manpowerUnit: {
                            required: true
                        },
                        complexityScore: {
                            required: true
                        },
                        expirationDate: {
                            required: true
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