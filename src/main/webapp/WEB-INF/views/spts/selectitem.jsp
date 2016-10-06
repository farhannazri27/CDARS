<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Select Item Sample</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Item</h2>
                        <form id="select_item_form" class="form-horizontal" role="form" action="${contextPath}/spts/selectitem/save" method="post">
                            <div class="form-group">
                                <label for="item" class="col-lg-4 control-label">Item *</label>
                                <div class="col-lg-8">
                                    <select id="item" name="item" class="form-control">
                                        <option value="" selected="">Select Item...</option>
                                        <c:forEach items="${itemList}" var="item">
                                            <option 
                                                onHandQty="${item.OnHandQty}" 
                                                prodQty="${item.ProductionQty}" 
                                                repairQty="${item.RepairQty}" 
                                                otherQty="${item.OtherQty}" 
                                                quarantineQty="${item.QuarantineQty}" 
                                                externalCleaningQty="${item.ExternalCleaningQty}" 
                                                externalRecleaningQty="${item.ExternalRecleaningQty}" 
                                                internalCleaningQty="${item.InternalCleaningQty}" 
                                                internalRecleaningQty="${item.InternalRecleaningQty}" 
                                                storageFactoryQty="${item.StorageFactoryQty}" 
                                                minQty="${item.MinQty}" 
                                                maxQty="${item.MaxQty}" 
                                                value="${item.ItemID}"
                                            >
                                                ${item.ItemID} - ${item.ItemName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="onHandQty" class="col-lg-4 control-label">On Hand Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="onHandQty" name="onHandQty" placeholder="On Hand Qty" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="prodQty" class="col-lg-4 control-label">Production Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="prodQty" name="prodQty" placeholder="Production Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="repairQty" class="col-lg-4 control-label">Repair Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="repairQty" name="repairQty" placeholder="Repair Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="otherQty" class="col-lg-4 control-label">Other Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="otherQty" name="otherQty" placeholder="Other Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="quarantineQty" class="col-lg-4 control-label">Quarantine Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="quarantineQty" name="quarantineQty" placeholder="Quarantine Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="externalCleaningQty" class="col-lg-4 control-label">Ext. Cleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="externalCleaningQty" name="externalCleaningQty" placeholder="External Cleaning Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="externalRecleaningQty" class="col-lg-4 control-label">Ext. Recleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="externalRecleaningQty" name="externalRecleaningQty" placeholder="External Recleaning Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="internalCleaningQty" class="col-lg-4 control-label">Int. Cleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="internalCleaningQty" name="internalCleaningQty" placeholder="Internal Cleaning Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="internalRecleaningQty" class="col-lg-4 control-label">Int. Recleaning Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="internalRecleaningQty" name="internalRecleaningQty" placeholder="Internal Recleaning Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="storageFactoryQty" class="col-lg-4 control-label">Storage Factory Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="storageFactoryQty" name="storageFactoryQty" placeholder="Storage Factory Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="minQty" class="col-lg-4 control-label">Min Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="minQty" name="minQty" placeholder="Min Quantity" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="maxQty" class="col-lg-4 control-label">Max Qty *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="maxQty" name="maxQty" placeholder="Max Quantity" value="">
                                </div>
                            </div>
                            <a href="${contextPath}/spts" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                $('#item').change(function () {
                    $('#onHandQty').val($('option:selected', this).attr('onHandQty'));
                    $('#prodQty').val($('option:selected', this).attr('prodQty'));
                    $('#repairQty').val($('option:selected', this).attr('repairQty'));
                    $('#otherQty').val($('option:selected', this).attr('otherQty'));
                    $('#quarantineQty').val($('option:selected', this).attr('quarantineQty'));
                    $('#externalCleaningQty').val($('option:selected', this).attr('externalCleaningQty'));
                    $('#externalRecleaningQty').val($('option:selected', this).attr('externalRecleaningQty'));
                    $('#internalCleaningQty').val($('option:selected', this).attr('internalCleaningQty'));
                    $('#internalRecleaningQty').val($('option:selected', this).attr('internalRecleaningQty'));
                    $('#storageFactoryQty').val($('option:selected', this).attr('storageFactoryQty'));
                    $('#minQty').val($('option:selected', this).attr('minQty'));
                    $('#maxQty').val($('option:selected', this).attr('maxQty'));
                });
                
                var validator = $("#select_item_form").validate({
                    rules: {
                        item: {
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