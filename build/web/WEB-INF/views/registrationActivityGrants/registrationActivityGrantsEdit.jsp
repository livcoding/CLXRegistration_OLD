<%-- 
    Document   : sis_StudentRegActivitiesEdit
    Created on : Feb 16, 2019, 10:59:44 AM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Activity Name:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" id="" name="" value="${data[0][7]}" readonly="true" class="form-control"  data-validation-error-msg="Please select activity name!">
                </div>
            </div> 
        </div>
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Activity By:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" id="" name="" value="${data[0][9]} / ${data[0][10]}" readonly="true" class="form-control"   data-validation-error-msg="Please select activity by!">
                </div>
            </div> 
            <!--            <div class="row col-lg-6 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label"></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <input type="text" id="" name="" value="${data[0][8]}" readonly="true" class="form-control">
                            </div>
                        </div>-->
        </div>
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">From Date:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <div class='input-group date' id='datetimepicker1'>
                        <fmt:formatDate value='${data[0][4]}' var='from_date' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="fromDate" onblur="checkToDate();" value="${from_date}" name="fromDate" data-validation="required"   data-validation-error-msg="Please select from date!"/>
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
                        <fmt:formatDate value='${data[0][5]}' var='to_date' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="toDate" onblur="checkFromDate();" value="${to_date}" name="toDate" data-validation-optional="true"   data-validation-error-msg="Please select to date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
        </div>
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data[0][6]!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data[0][6]=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
        <input type="text" class="hidden" id="instituteId" name="instituteId" value="${data[0][0]}"/>
        <input type="text" class="hidden" id="activityId" name="activityId" value="${data[0][1]}"/>
        <input type="text" class="hidden" id="staffId" name="staffId" value="${data[0][2]}"/>
        <input type="text" class="hidden" id="staffType" name="staffType" value="${data[0][3]}"/>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form> 

<script>

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

    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker2').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
//        $("#datetimepicker1").on("dp.change", function (e) {
//            $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
//        });
//        $("#datetimepicker2").on("dp.change", function (e) {
//            $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
//        });
    });

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
                                    url: "registrationActivityGrants/update",
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
                                            toastr.success(Success['update_success'], "Success!!");
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

    function checkFromDate() {
        var start_date = $("#fromDate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#toDate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#toDate").val() != '') {
            if ($("#fromDate").val() != '') {
                if (fromYear > toYear) {
                    $("#toDate").val('');
                    BootstrapDialog.alert("Please select another date greater than the from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#toDate").val('');
                        BootstrapDialog.alert("Please select another date greater than the from date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#toDate").val('');
                            BootstrapDialog.alert("Please select another date greater than the  from date!");
                        }
                    }
                }

            } else {
                $("#toDate").val('');
                BootstrapDialog.alert("Please select from date first!");
            }
        }
    }

    function checkToDate() {
        var start_date = $("#fromDate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#toDate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#toDate").val() != '') {
            if ($("#fromDate").val() != '') {
                if (fromYear > toYear) {
                    $("#fromDate").val('');
                    BootstrapDialog.alert("Please select another date less than the To date!");
                } else if (fromYear == toYear){
                    if (fromMonth > toMonth) {
                        $("#fromDate").val('');
                        BootstrapDialog.alert("Please select another date less than the To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#fromDate").val('');
                            BootstrapDialog.alert("Please select another date less than the  To date!");
                        }
                    }
                }

            } else {
                $("#fromDate").val('');
                BootstrapDialog.alert("Please select from date first!");
            }
        }
    }
</script>



