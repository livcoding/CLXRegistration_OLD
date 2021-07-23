<%-- 
    Document   : upgradeDegradeStudentReport
    Created on : Jan 24, 2019, 3:22:06 PM
    Author     : ankur.goyal
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
                    <div class="row col-lg-12 form-group" id="divContainer"> 
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize    ;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="academicYear" name="academicYear" data-live-search="true" data-validation="required" onchange="">               
                                        <option value="">Select</option>
                                        <c:forEach items="${acadList}" var="list">
                                            <option value="${list}"><c:out value="${list}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="programCode" name="programCode" data-live-search="true" data-validation="required" onchange="getBranchCode();">               
                                        <option value="">Select</option>
                                        <c:forEach items="${programList}" var="list">
                                    <option value="${list[2]}"><c:out value="${list[0]} -- ${list[1]}"/></option>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="branchCode" name="branchCode" data-live-search="true" data-validation="required">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Semester:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="semester" name="semester" data-live-search="true" data-validation="required">               
                                        <option value="">Select</option>
                                        <c:forEach items="${styList}" var="list">
                                            <option value="${list}"><c:out value="${list}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Upgrade From:</label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <div class='input-group date' id='datetimepicker1'>
                                        <input type='text' class="form-control" id="fromDate" name="fromDate" data-validation="required" placeholder="From Date"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Upgrade To:</label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <div class='input-group date' id='datetimepicker2'>
                                        <input type='text' class="form-control" id="toDate" name="toDate" data-validation="required" placeholder="To Date"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-8 form-group" style="margin-left: -80px;">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Status:</label>
                            <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="status" name="status" value="U"/>Upgraded
                                    </label>
                                </div>
                                <div class="col-lg-4">
                                    <label class="radio-inline">
                                        <input type="radio" id="status" name="status" value="N"/> Not Upgraded
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="status" name="status" checked="true" value="B"/> Both
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-8 form-group" style="margin-left: -80px;">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Export To:</label>
                            <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                <div class="col-lg-3"  hidden="true">
                                    <label class="radio-inline" >
                                        <input type="radio"  id="exportTo" name="exportTo" value="W"/>Word
                                    </label>
                                </div>
                                <div class="col-lg-3" hidden="true">
                                    <label class="radio-inline">
                                        <input type="radio" id="exportTo" name="exportTo" value="E"/> Excel
                                    </label>
                                </div>
                                <div class="col-lg-3" hidden="true">
                                    <label class="radio-inline">
                                        <input type="radio" id="exportTo" name="exportTo" value="H"/> HTML
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="exportTo" name="exportTo" checked="true" value="P"/> PDF
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport();" class="btn btn-success btn-sm btn-flat">Download</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                    <div class="row col-lg-12"></div>
                    <div class="row col-lg-12"></div>
                    <div class="row col-lg-12"></div>
                    <div class="row col-lg-12"></div>
                    <div class="row col-lg-12"></div>
                    <div class="row col-lg-12"></div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $("#academicYear").chosen();
    $("#programCode").chosen();
    $("#branchCode").chosen();
    $("#semester").chosen();

    function myReset() {
        $("#academicYear").val('').trigger("chosen:updated");
        $("#programCode").val('').trigger("chosen:updated");
        $("#branchCode").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
    }

    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker2').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker1").on("dp.change", function (e) {
            $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
        })
        $('#datetimepicker2').on("dp.change", function (e) {
            $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
        });
    });

    function getBranchCode() {
        $("#branchCode").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#branchCode").empty();
        $.ajax({
            type: "POST",
            url: "upgradeDegradeStudentsReports/getBranchCode",
            data: '&programCode=' + $("#programCode").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.branch != null && data.branch != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.branch, function (i, item)
                    {
                        str1 += '<option value="' + item[4] + '">' + item[0] + ' -- ' + item[1] + '</option>'
                    });
                    $("#branchCode").append(str1);
                    $("#branchCode").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("Branch Code Not Found..Please Select Another Program Code..");
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getReport() {
        var acadYear = $("#academicYear").val();
        var program = $("#programCode").val();
        var Branch = $("#branchCode").val();
        var semester = $("#semester").val();
        var fromDate = $("#fromDate").val();
        var toDate = $("#toDate").val();
        var status = $("input[name='status']:checked").val();
        var exportTo = $("input[name='exportTo']:checked").val();
        if (acadYear != '' && program != '' && Branch != '' && semester != '') {
            $.blockUI();
            window.location.assign('upgradeDegradeStudentsReports/getReport?excelfilename=upgradeDegradeStudentReport&acadYear=' + acadYear + '&program=' + program + '&Branch=' + Branch + '&semester=' + semester + '&status=' + status + '&exportTo=' + exportTo + '&fromDate=' + fromDate + '&toDate=' + toDate + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Select Required Fields..");
        }
    }
</script>