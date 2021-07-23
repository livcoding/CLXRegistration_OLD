<%-- 
    Document   : summerRegistrationReportList
    Created on : Jan 8, 2019, 11:56:55 AM
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
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="semesterCode" name="semesterCode" data-live-search="true" data-validation="required" onchange="enrollment();">               
                                <option value="">Select</option>
                                <c:forEach items="${regList}" var="list">
                                    <option value="${list[0]}~@~${list[1]}~@~${list[2]}"><c:out value="${list[1]}"/></option>
                                </c:forEach> 
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Enrollment No:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="enrollmentNo" name="enrollmentNo" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat"> Download</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $("#semesterCode").chosen();
    $("#enrollmentNo").chosen();

    function myReset() {
        $("#semesterCode").val('').trigger("chosen:updated");
        $("#enrollmentNo").val('').trigger("chosen:updated");
    }

    function enrollment() {
        $("#enrollmentNo").val('').trigger("chosen:updated");
        $("#enrollmentNo").empty();
        $.ajax({
            type: "POST",
            url: "summerRegistrationReport/getEnrollmentCode",
            data: '&semesterCode=' + $("#semesterCode").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.enrollmentCode != null && data.enrollmentCode != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.enrollmentCode, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '~@~' + item[3] + '~@~' + item[4] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '">' + item[4] + '</option>'
                    });
                    $("#enrollmentNo").append(str1);
                    $("#enrollmentNo").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found..");
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getReport() {
        var semesterCode = $("#semesterCode").val();
        var enrollmentNo = $("#enrollmentNo").val();
        var radioValue = $("input[name='export_To']:checked").val();
        var level = "one";
        if (semesterCode != '' && enrollmentNo != '') {
            $.blockUI();
            window.location.assign('summerRegistrationReport/getReport?excelfilename=SummerRegistrationReport&semesterCode=' + semesterCode + '&enrollmentNo=' + enrollmentNo + '&exportto=' + radioValue + '&level=' + level + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>
