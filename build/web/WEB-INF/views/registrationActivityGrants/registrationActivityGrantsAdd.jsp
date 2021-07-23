<%-- 
    Document   : sis_StudentRegActivitiesAdd
    Created on : Feb 16, 2019, 10:59:25 AM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Activity Name:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="activityName" name="activityName" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select activity name!">
                        <option value="">Select</option>
                        <c:forEach items="${activityList}" var="actList">
                            <option value="${actList[0]}"><c:out value="${actList[1]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div> 
        </div>
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Activity By:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="activityBy" name="activityBy" data-validation="required" data-live-search="true"   data-validation-error-msg="Please select activity by!">
                        <option value="">Select</option>
                        <c:forEach items="${staffList}" var="staff">
                            <option value="${staff[0]}~@~${staff[3]}"><c:out value="${staff[1]} -- ${staff[2]} -- ${staff[3]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div> 
        </div>
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">From Date:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <div class='input-group date' id='datetimepicker1'>
                        <input type='text' class="form-control" id="fromDate" name="fromDate" data-validation="required"   data-validation-error-msg="Please select from date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">To Date:</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <div class='input-group date' id='datetimepicker2'>
                        <input type='text' class="form-control" id="toDate" name="toDate" data-validation-optional="true"  data-validation-error-msg="Please select to date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
        </div>
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label" id="Ad"><span style="color: green"> Active</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
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
    $("#activityName").chosen();
    $("#activityBy").chosen();

    function myReset() {
        $("#activityName").val('').trigger("chosen:updated");
        $("#activityBy").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }

    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker2').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker1").on("dp.change", function (e) {
            $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker2").on("dp.change", function (e) {
            $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
        });
    });

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
                                    url: "registrationActivityGrants/save",
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
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("registrationActivityGrants/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        $.unblockUI();
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
