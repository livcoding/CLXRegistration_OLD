<%-- 
    Document   : eligibleStudentsForUpcomingSemesterReport
    Created on : 9 Jan, 2019, 5:10:07 PM
    Author     : deepak.gupta
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
                            <select class="form-control" id="regId" name="regId" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${regList}" var="reList">
                                    <option value="${reList.id.registrationid}~@~${reList.registrationcode}"><c:out value="${reList.registrationcode}   ---   ${reList.registrationdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="program" name="program" onchange="getBranch();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${progList}" var="proList">
                                    <option value="${proList.id.programid}~@~${proList.programcode}"><c:out value="${proList.programcode}   ---   ${proList.programdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Branch Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="branch" name="branch" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="acadYear" name="acadYear" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${acadYearList}" var="list">
                                    <option value="${list}"><c:out value="${list}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">No. of fail Subject
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <input type="text" name="nooffailsubject" id="nooffailsubject" maxlength="2" data-validation-help="Max Length is 2" data-validation-optional="true" data-validation="number" data-validation="required" placeholder="Enter No. of fail Subject" class="form-control">
                        </div>
                    </div>
<!--                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Current Registration:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="currentReg" name="currentReg"  value="E"/> Even
                            </div>
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="currentReg" name="currentReg" checked="O" value="O"/> Odd
                            </div>
                        </div>
                    </div>-->
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="export_To" name="export_To" value="E"/> Excel
                            </div>
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                            </div>
                        </div>
                    </div>
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
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#regId").val('').trigger("chosen:updated");
        $("#program").val('').trigger("chosen:updated");
        $("#branch").val('').empty();
        $("#branch").val('').trigger("chosen:updated");
        $("#acadYear").val('').trigger("chosen:updated");
    }
    $("#regId").chosen();
    $("#program").chosen();
    $("#branch").chosen();
    $("#acadYear").chosen();

    function getBranch() {
        if ($("#program").val() != '') {
            $.ajax({
                url: "eligibleStudentsForUpcomingSemesterReport/getBranch",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#branch").empty();
                    if (data.branch != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.branch[0], function (i, item)
                        {
                            str1 += '<option value="' + item[4] + "~@~" + item[0] + '">' + item[0] + '--' + item[1] + '</option>'
                        });
                        $("#branch").append(str1);
                        $("#branchstr").append(str1);
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

    function getReport() {
        var regId = $("#regId").val();
        var programId = $("#program").val();
        var branch = $("#branch").val();
        var acadYear = $("#acadYear").val();
        var nooffailsubject = $("#nooffailsubject").val();
        var currentReg = $("input[name='currentReg']:checked").val();
        var radioValue = $("input[name='export_To']:checked").val();
        if (programId != '' && branch != '' && regId != '' && acadYear != '' && nooffailsubject != '') {
            $.blockUI();
            window.location.assign('eligibleStudentsForUpcomingSemesterReport/eligibleStudentsForUpcomingSemesterReport?excelfilename=registrationReport&programId=' + programId + '&branch=' + branch + '&regId=' + regId + '&acadYear=' + acadYear + '&radioValue=' + radioValue + '&currentReg=' + currentReg + '&nooffailsubject=' + nooffailsubject + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>