<%-- 
    Document   : electiveSubjectAllocationStatus
    Created on : 8 Jan, 2019, 10:26:55 AM
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
                            <select class="form-control" id="regId" name="regId" data-live-search="true" onchange="getSubjectType();" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${regList}" var="reList">
                                    <option value="${reList.id.registrationid}~@~${reList.registrationcode}"><c:out value="${reList.registrationcode}   ---   ${reList.registrationdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Type:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="subjectType" name="subjectType" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" >
                            <input type="radio" id="export_To" name="export_To" value="W"/> Word &nbsp;&nbsp;
                            <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                        </div>
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat"> Download</a>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
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
    $("#regId").chosen();
    $("#subjectType").chosen();

    function getSubjectType() {
        if ($("#regId").val() != '') {
            $.ajax({
                url: "electiveSubjectAllocationStatus/getSubjectType",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regId").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subjectType").empty();
                    if (data.subjectTypeList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subjectTypeList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + "~@~" + item[1] + '">' + item[1] + " --- " + item[2] + '</option>'
                        });
                        $("#subjectType").append(str1);
                        $("#subjectType").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Semester Number Not Found For This Program,Please Select Another Program...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }
    function getReport() {
        var regId = $("#regId").val();
        var subjectType = $("#subjectType").val();
        var radioValue = $("input[name='export_To']:checked").val();
        if (regId != '' && subjectType != '') {
            $.blockUI();
            window.location.assign('electiveSubjectAllocationStatus/electiveSubjectAllocationStatus?excelfilename=registrationReport&regId=' + regId + '&subjectType=' + subjectType + '&radioValue=' + radioValue + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>