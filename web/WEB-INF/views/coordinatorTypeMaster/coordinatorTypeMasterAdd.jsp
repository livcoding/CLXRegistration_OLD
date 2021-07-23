<%-- 
    Document   : coordinatorTypeMasterAdd
    Created on : Nov 8, 2019, 4:58:30 PM
    Author     : priyanka.tyagi
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="col-lg-12 form-group"></div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select class="form-control" id="coordType" name="coordType" data-validation="required" data-live-search="true" data-validation-error-msg="Please select coordinator Type!">               
                        <option value="">Select</option>
                        <option value="A"> Student Advisors </option>
                        <option value="E"> External Marks </option>
                        <option value="C"> Subject </option>
                        <option value="M"> Marks</option>
                        <option value="T"> Attendance</option>
                        <option value="G"> Grade</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="coord_code" id="coord_code" maxlength="20" data-validation-help="Max Length is 20"  data-validation="required" placeholder="Enter Coordinator Type Code" class="form-control" data-validation-error-msg="Please enter coordinator type code!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type Description:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="coorTypeDesc" id="coorTypeDesc" maxlength="60" data-validation-help="Max Length is 60" data-validation="required" placeholder="Enter Coordinator Type Description" class="form-control" data-validation-error-msg="Please enter coordinator type description!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Coordinator Type Based On:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select class="form-control" id="CoordTypeBased" name="CoordTypeBased" data-validation="required" data-live-search="true" data-validation-error-msg="Please select coordinator type based on!">               
                        <option value="">Select</option>
                        <option value="S"> Student Based </option>
                        <option value="F"> FST Based </option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Student Limit:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="stuLimit" id="stuLimit" maxlength="6" data-validation-help="Max Length is 6" onkeypress="return isNumberKey(event)" data-validation="number" placeholder="Enter Student Limit" class="form-control" data-validation-error-msg="Please enter student limit!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label" id="Ad"><span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N">
                </div>
            </div>
        </div>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>
    $("#coordType").chosen();
    $("#CoordTypeBased").chosen();
    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }

    function myReset() {
        $("#coordType").val('').trigger("chosen:updated");
        $("#CoordTypeBased").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }

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
                                    url: "coordinatorTypeMaster/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Saved.', "Success!!");
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

    function saveData() {
        $("#ajaxform").submit();
    }
</script>
