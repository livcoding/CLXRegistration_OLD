<%-- 
    Document   : backPaperReport
    Created on : 8 Jan, 2019, 12:32:36 PM
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
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="program" name="program" onchange="getBranch();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${progList}" var="proList">
                                    <option value="${proList.id.programid}~@~${proList.programcode}"><c:out value="${proList.programcode} / ${proList.programdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Branch:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="branch" name="branch" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Semester:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="semester" name="semester" onchange="getSubject();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${semester}" var="semList">
                                    <option value="${semList}"><c:out value="${semList}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="subCode" name="subCode" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <%--<c:forEach items="${subList}" var="suList">
                                <option value="${suList.id.subjectid}~@~${suList.subjectcode}"><c:out value="${suList.subjectcode} --- ${suList.subjectdesc} "/></option>-->
                               </c:forEach>--%>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Order By:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="orderBy" name="orderBy" checked="true" value="E"/>Enrollment
                            </div>
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="orderBy" name="orderBy" value="N"/>Student Name
                            </div> 
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Sort By:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="soryBy" name="sortBy" checked="true" value="asc"/>Ascending
                            </div>
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="soryBy" name="sortBy" value="desc"/> Descending
                            </div>
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
<!--                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" value="W"/> Word
                            </div>-->
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat"> Download</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $("#program").chosen();
    $("#branch").chosen();
    $("#semester").chosen();
    $("#subCode").chosen();
    function myReset() {
        $("#program").val('').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        $("#subCode").empty();
        $("#subCode").val('').trigger("chosen:updated");
    }

    function getBranch() {
        if ($("#program").val() != '') {
            $.ajax({
                url: "backPaperReport/getBranch",
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
                            str1 += '<option value="' + item[4] + "~@~" + item[0] + '">' + item[0] + ' / ' + item[1] + '</option>'
                        });
                        $("#branch").append(str1);
                        $("#branch").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Program Not Found, Please Select Another Program...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Program first!");
        }
    }

    function getSubject() {
        var programId = $("#program").val();
        var semester = $("#semester").val();
        if (programId != '' && semester != '') {
            $.ajax({
                url: "backPaperReport/getSubject",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "programId=" + $("#program").val() + '&semester=' + $("#semester").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subCode").empty();
                    if (data.subject != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subject[0], function (i, item)
                        {
                            str1 += '<option value="' + item[0] + "~@~" + item[1] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                        });
                        $("#subCode").append(str1);
                        $("#subCode").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Program Not Found, Please Select Another Program...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Program,Semester");
        }
    }

    function getReport() {
        var programId = $("#program").val();
        var branch = $("#branch").val();
        var semester = $("#semester").val();
        var subCode = $("#subCode").val();
        var orderBy = $("input[name='orderBy']:checked").val();
        var sortBy = $("input[name='sortBy']:checked").val();
        var radioValue = $("input[name='export_To']:checked").val();
        if (programId != '' && branch != '' && semester != '' && subCode != '') {
            $.blockUI();
            window.location.assign('backPaperReport/backPaperReport?excelfilename=registrationReport&programId=' + programId + '&branch=' + branch + '&semester=' + semester + '&subCode=' + subCode + '&radioValue=' + radioValue + '&orderBy=' + orderBy + '&sortBy=' + sortBy + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field.");
        }
    }
</script>