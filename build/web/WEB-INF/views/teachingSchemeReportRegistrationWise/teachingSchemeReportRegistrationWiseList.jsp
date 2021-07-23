<%-- 
    Document   : teachingSchemeReportRegistrationWiseList
    Created on : Feb 16, 2019, 4:46:12 PM
    Author     : ankit.kumar
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
                            <select class="form-control" id="sty_code" onchange="" name="sty_code" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${sty_data}" var="sty_data">
                                    <option value="${sty_data.id.registrationid},${sty_data.registrationcode}"><c:out value="${sty_data.registrationcode} / ${sty_data.registrationdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="acadYear" name="acadYear" onchange="resetother();getProgramData();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${acadyear_data}" var="acadyear_data">
                                    <option value="${acadyear_data.id.academicyear}"><c:out value="${acadyear_data.id.academicyear}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="program" name="program" onchange="getSemester();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Sty Number:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="semester" name="semester" onchange="getSectionCode();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Section:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="section" name="section" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Report Type:</label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                <input type="radio" id="reportType" name="reportType" checked="true" value="D"/>Teaching Scheme Detail
                            </div>
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:</label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/>PDF
                            </div>
                        </div>
                    </div>
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
        $("#acadYear").val('').trigger("chosen:updated");
        $("#sty_code").val('').trigger("chosen:updated");
        $("#program").empty();
        $("#program").val('').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");

    }
    $("#acadYear").chosen();
    $("#program").chosen();
    $("#semester").chosen();
    $("#sty_code").chosen();
    $("#section").chosen();

    function resetother() {
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#section").val('').trigger("chosen:updated");
    }
    function getProgramData() {
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        $("#program").empty();
        var sty_code = $("#sty_code").val();
        var carter = sty_code;
        var arlene3 = carter.split(",");
        if ($("#sty_code").val() != '') {
            if ($("#acadYear").val() != '') {
                $.ajax({
                    url: "teachingSchemeReportRegistrationWise/getProgramData",
                    dataType: 'json',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: 'acadYear=' + $("#acadYear").val() + '&sty_code=' + arlene3[0],
                    error: (function (response) {
                        alert('Server Error' + response);
                    }),
                    success: function (data) {
                        if (data.prog_data != null) {
                            var str1 = '';
                            var str1 = '<option value="">Select</option>';
                            $.each(data.prog_data, function (i, item)
                            {
                                str1 += '<option value="' + item[1] + '">' + item[2] + '  /  ' + item[3] + '</option>'
                            });
                            $("#program").append(str1);
                            $("#program").trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Program Not Found, Please Select Another Program...");
                        }
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Academic Year first..");
            }
        } else {
            $("#sty_code").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Semester Code first..");
        }
    }
    function getSemester() {
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        var sty_code = $("#sty_code").val();
        var carter = sty_code;
        var arlene3 = carter.split(",");
        $("#semester").empty();
        if ($("#acadYear").val() != '' && $("#sty_code").val() != '' && $("#program").val() != '') {
            $.ajax({
                url: "teachingSchemeReportRegistrationWise/getSemData",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear").val() + "&sty_code=" + arlene3[0] + "&program=" + $("#program").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.sem_data != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.sem_data, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#semester").append(str1);
                        $("#semester").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Sty Number Not Found...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields!");
        }
    }
    function getSectionCode() {
        var sty_code = $("#sty_code").val();
        var carter = sty_code;
        var arlene3 = carter.split(",");
        var carter = sty_code;
        var arlene3 = carter.split(",");
        $("#section").empty();
        if ($("#acadYear").val() != '' && $("#sty_code").val() != '' && $("#program").val() != '' && $("#program").val() != '') {
            $.ajax({
                url: "teachingSchemeReportRegistrationWise/getSectionData",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear").val() + "&sty_code=" + arlene3[0] + "&program=" + $("#program").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.sec_data != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.sec_data, function (i, item)
                        {
                            str1 += '<option value="' + item[1] + '">' + item[2] + '  /  ' + item[3] + '</option>'
                        });
                        $("#section").append(str1);
                        $("#section").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section Not Found, Please Select Another ...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields!");
        }
    }

    function getReport() {
        var sty_code = $("#sty_code").val();
        var carter = sty_code;
        var arlene3 = carter.split(",");
        var program = $("#program").val();
        var acadYear = $("#acadYear").val();
        var semester = $("#semester").val();
        var section = $("#section").val();
        var reportType = $("input[name='reportType']:checked").val();
        var export_To = $("input[name='export_To']:checked").val();
        if (program != '' && sty_code != '' && acadYear != '' && semester != '' && section != '') {
            $.blockUI();
            window.location.assign('teachingSchemeReportRegistrationWise/getReport?excelfilename=teachingSchemeReport&programid=' + program + '&registrationid=' + arlene3[0] + '&registrationcode=' + arlene3[1] + '&sectionid=' + section + '&academicyear=' + acadYear + '&exportto=' + export_To + '&stynumber=' + semester + '&reportType=' + reportType + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>
