<%-- 
    Document   : registrationNoDuesStatusReportList
    Created on : Feb 18, 2019, 11:07:11 AM
    Author     : ashutosh1.kumar
--%>
<%@include file="../mainjstl.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row" id="dashboard">
    <div class="col-md-12">
        <div class="box" style="box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
            <div class="box-header with-border bg-navy">
                <h3 class="box-title header_name" id="box-title"></h3>
                <div class="box-tools pull-right">                  
                    <button onclick="javascript:blank();" class="btn btn-box-tool"><i class="fa fa-close fa-2x" style="color:white"></i></button>
                </div>
            </div>
            <div class="box-body" >
                <form method="POST" id="ajaxform" class="form-horizontal">
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="regCode" name="regCode" onchange="getAcademicYear(this.value);" data-validation="required" data-live-search="true">               
                                <option value="">Select</option>
                                <c:forEach items="${data}" var="reList">
                                    <option value="${reList[0]}"><c:out value="${reList[1]}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="acadYear" name="acadYear" onchange="getProgram();" data-validation="required" data-live-search="true">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="program" name="program" onchange="getStyNo();"  data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">STY No:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="semester" name="semester" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Activity Type:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="activityType" name="activityType" multiple="true" data-validation="required" data-live-search="true">               
                                <c:forEach items="${activity}" var="activity">
                                    <option value="${activity[0]}"><c:out value="${activity[1]}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat"> Download</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>    
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#regCode").val('').trigger("chosen:updated");
        $("#acadYear").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#activityType").val('a').trigger("chosen:updated");
    }

    $("#regCode").chosen();
    $("#acadYear").chosen();
    $("#program").chosen();
    $("#semester").chosen();
    $("#activityType").chosen();
    function getAcademicYear(id) {
        $("#acadYear").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#activityType").val('a').trigger("chosen:updated");
        $("#acadYear").empty();
        if (id != '') {
            $.ajax({
                url: "registrationNoDuesStatusReport/getAcademicYear",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regCode=" + id,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.acadyear != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.acadyear, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#acadYear").append(str1);
                        $("#acadYear").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found, Please Select Another Semester...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }


    function getProgram() {
        $("#program").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#activityType").val('a').trigger("chosen:updated");
        $("#program").empty();
        if ($("#regCode").val() != '') {
            $.ajax({
                url: "registrationNoDuesStatusReport/getProgram",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regCode=" + $("#regCode").val() + "&acadYear=" + $("#acadYear").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.program != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.program, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
                        });
                        $("#program").append(str1);
                        $("#program").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Program Not Found, Please Select Another Academic Year...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic Year first!");
        }
    }

    function getStyNo() {
        $("#semester").val('').trigger("chosen:updated");
        $("#activityType").val('a').trigger("chosen:updated");
        $("#semester").empty();
        var program = $("#program").val();
        var acadYear = $("#acadYear").val();
        var regCode = $("#regCode").val();
        if (regCode != '' && acadYear != '' && program != '') {
            $.ajax({
                url: "registrationNoDuesStatusReport/getStyNo",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "program=" + program + "&acadYear=" + acadYear + "&regCode=" + regCode,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.styno != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.styno, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#semester").append(str1);
                        $("#semester").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Semester Not Found, Please Select Another Criteria...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code, Academic Year, Program first!");
        }
    }

    function getReport() {
        var program = $("#program").val();
        var acadYear = $("#acadYear").val();
        var regCode = $("#regCode").val();
        var semester = $("#semester").val();
        var activityType = $("#activityType").val();
        if (program != '' && regCode != '' && acadYear != '' && semester != '' && activityType != '') {
            $.blockUI();
            window.location.assign('registrationNoDuesStatusReport/getReport?excelfilename=registrationNoDuesStatusReport&program=' + program + '&regCode=' + regCode + '&acadYear=' + acadYear + '&semester=' + semester + '&activityType=' + activityType + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>
