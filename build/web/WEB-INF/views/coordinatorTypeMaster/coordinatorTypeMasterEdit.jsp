<%-- 
    Document   : coordinatorTypeMasterEdit
    Created on : Nov 9, 2019, 10:49:10 AM
    Author     : priyanka.tyagi
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" modelAttribute="" id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">                
        <div style="height: 12px"></div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <c:if test="${data.coordinatortype =='C'}">
                        <input type="text" value="Subject" class="form-control" readonly="true" data-validation-error-msg="Please select coordinator type!">                                              
                    </c:if>
                    <c:if test="${data.coordinatortype =='A'}">
                        <input type="text" value="Student Advisors" class="form-control" readonly="true" data-validation-error-msg="Please select coordinator type!">                                              
                    </c:if>
                    <c:if test="${data.coordinatortype =='E'}">
                        <input type="text" value="External Marks" class="form-control" readonly="true" data-validation-error-msg="Please select coordinator type!">                                              
                    </c:if>
                    <c:if test="${data.coordinatortype =='M'}">
                        <input type="text" value="Marks" class="form-control" readonly="true" data-validation-error-msg="Please select coordinator type!">                                              
                    </c:if>
                        <c:if test="${data.coordinatortype =='T'}">
                        <input type="text" value="Attendance" class="form-control" readonly="true" data-validation-error-msg="Please select coordinator type!">                                              
                    </c:if>
                        <c:if test="${data.coordinatortype =='G'}">
                        <input type="text" value="Grade" class="form-control" readonly="true" data-validation-error-msg="Please select coordinator type!">                                              
                    </c:if>
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="coorTypeCode" id="coorTypeCode" maxlength="20" data-validation-help="Max Length is 20"  value="${data.coordinatortypecode}" placeholder="Enter Coordinator Type Description" class="form-control" data-validation-error-msg="Please select coordinator type code!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type Description:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="coorTypeDesc" id="coorTypeDesc" maxlength="60" value="${data.coordinatortypedesc}"  data-validation-help="Max Length is 60" data-validation="required" placeholder="Enter Coordinator Type Description" class="form-control" data-validation-error-msg="Please enter coordinator type description!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type Based On:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <c:if test="${data.groupflag=='S'}">
                        <select class="form-control" id="CoordTypeBased" name="CoordTypeBased" data-validation="required" data-live-search="true" data-validation-error-msg="Please select coordinator type based on!">               
                            <option value="">Select</option>
                            <option value="S"  selected="true"> Student Based </option>
                            <option value="F"> FST Based </option>
                        </select>
                    </c:if>
                    <c:if test="${data.groupflag=='F'}">
                        <select class="form-control" id="CoordTypeBased" name="CoordTypeBased" data-validation="required" data-live-search="true" data-validation-error-msg="Please select coordinator type based on!">               
                            <option value="">Select</option>
                            <option value="S"> Student Based </option>
                            <option value="F" selected="true"> FST Based </option>
                        </select>
                    </c:if>
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Student Limit:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="stuLimit" id="stuLimit" maxlength="6" data-validation-help="Max Length is 6" onkeypress="return isNumberKey(event)" value="${data.studentlimit}" data-validation="number" placeholder="Enter Student Limit" class="form-control" data-validation-error-msg="Please enter student limit!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data.deactive!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data.deactive=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:updateData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
        <input type="hidden" name="coorType" id="coorType" value="${data.coordinatortype}"/>
        <input type="hidden" class="form-control" id="instituteId" name="instituteId" value="${data.id.instituteid}"/>
        <input type="hidden" class="form-control" id="coordinatorTypeId" name="coordinatorTypeId" value="${data.id.coordinatortypeid}"/>
    </div>
</form>

<script>
    $("#CoordTypeBased").chosen();
    function change() {
        if ($("#deactive").prop('checked') == true) {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        } else
        {
            $("#deactive").val("Y");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: red"> Deactive</span>');
        }
    }

    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }

    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "coordinatorTypeMaster/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        //  $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Record  Successfully Updated.', "Success!!");
                                            loadForm("coordinatorTypeMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        setTimeout($.unblockUI, 1000);
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

    function updateData() {
        $("#ajaxform").submit();
    }

</script>
