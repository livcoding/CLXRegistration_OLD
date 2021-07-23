<%-- 
Document   : teachingSchemeReportList
Created on : Feb 9, 2019, 5:43:47 PM
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
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="acadYear" name="acadYear" multiple="true"  onchange="resetother()" data-live-search="true" data-validation="required">               
                                <c:forEach items="${acadyear}" var="acadyear">
                                    <option value="${acadyear[0]}"><c:out value="${acadyear[0]}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="program" name="program" onchange="getBranch();getSemester();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <!--<option value="All">All</option>-->
                                <c:forEach items="${program}" var="program">
                                    <option value="${program.id.programid}~@~${program.programcode}"><c:out value="${program.programcode}   --   ${program.programdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Branch Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="branch" name="branch" multiple="true"  data-live-search="true" onchange="resetlowerlov();"data-validation="required">               
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Sty Number:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="semester" onchange="getSection();" multiple="true"  name="semester" data-live-search="true" data-validation="required">               
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Section:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="section" name="section" multiple="true"  data-live-search="true" data-validation="required">               
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
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
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
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $("#acadYear").chosen();
    $("#program").chosen();
    $("#branch").chosen();
    $("#semester").chosen();
    $("#section").chosen();
    function myReset() {
        $("#acadYear").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
    }
    function resetother() {
        $("#branch").empty();
        $("#branch").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
    }

    function getBranch() {
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        $("#branch").empty();
        if ($("#program").val() != '') {
            $.ajax({
                url: "teachingSchemeReport/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "program=" + $("#program").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.branch != null) {
                        var str1 = '';
                        $.each(data.branch, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[3] + '--' + item[4] + '</option>'
                        });
                        $("#branch").append(str1);
                        $("#branch").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Branch Not Found, Please Select Another Program...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Program first!");
        }
    }
    function getSemester() {
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        var pg = $("#program").val();
        var program = pg.split("~@~");
        $("#semester").empty();
        if ($("#acadYear").val() != '' && $("#program").val() != '') {
            $.ajax({
                url: "teachingSchemeReport/getStyNumber",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYear").val() + "&program=" + program ,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.sem_data != null) {
                        var str1 = '';
                        $.each(data.sem_data, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#semester").append(str1);
                        $("#semester").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Sty Number Not Found..");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields!");
        }
    }
    function getSection() {
        $("#section").empty();
        $("#section").val('').trigger("chosen:updated");
        var program = $("#program").val();
        var acadYear = $("#acadYear").val();
        var branch = $("#branch").val();
        var semester = $("#semester").val();
        if (acadYear != '' && program != '' && branch != '' && semester != '') {
            $.ajax({
                url: "teachingSchemeReport/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "program=" + program + "&acadYear=" + acadYear + "&branch=" + branch + "&semester=" + semester,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.section != null) {
                        var str1 = '';
                        $.each(data.section, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[1] + '">' + item[1] + '</option>'
                        });
                        $("#section").append(str1);
                        $("#section").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section Not Found, Please Select Another Criteria...");
                    }
                }
            });
        } else {
            $("#semester").val('All').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Academic Year, Program, Branch, Semester first!");
        }
    }

    function getReport() {
        var program = $("#program").val();
        var acadYear = $("#acadYear").val();
        var branch = $("#branch").val();
        var semester = $("#semester").val();
        var section = $("#section").val();
        var reportType = $("input[name='reportType']:checked").val();
        var export_To = $("input[name='export_To']:checked").val();
        if (program != '' && branch != '' && acadYear != '' && semester != '' && section != '') {
            $.blockUI();
            window.location.assign('teachingSchemeReport/getReport?excelfilename=teachingSchemeReport&program=' + program + '&branch=' + branch + '&section=' + section + '&acadYear=' + acadYear + '&export_To=' + export_To + '&semester=' + semester + '&reportType=' + reportType + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
    function resetlowerlov(){
        $("#section").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#section").val('').trigger("chosen:updated");
    }
</script>
