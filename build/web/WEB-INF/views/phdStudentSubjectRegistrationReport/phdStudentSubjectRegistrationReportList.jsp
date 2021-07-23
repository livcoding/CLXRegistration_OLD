<%-- 
Document   : phdStudentSubjectRegistrationReportList
Created on : Dec 21, 2018, 10:46:52 AM
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
                    <div class="row col-lg-12 form-group" id="divContainer"> 
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-5 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Registration:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" name="reg" id="reg" value="C" checked="true">  
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Research Registration:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" name="reg" id="reg" value="R">  
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="sem_code" name="sem_code"   data-live-search="true" onchange="course_registration();">               
                                        <option value="">Select</option>
                                        <c:forEach items="${regList}" var="list">
                                            <option value="${list[0]}"><c:out value="${list[1]} --- ${list[2]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Enrollment No:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="enr_no" name="enr_no" data-live-search="true">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group" style="margin-left: -18px;">
                                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Export To: <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                    <div class="col-lg-3">
                                        <label class="radio-inline">
                                            <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                                        </label>
                                    </div> 
                                </div>
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
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $("#sem_code").chosen();
    $("#enr_no").chosen();
    function myReset() {
        $("#sem_code").val('').trigger("chosen:updated");
        $("#enr_no").val('').trigger("chosen:updated");
    }


    var cb = {
        add: {
            link: "",
            name: "Add",
            value: ""
        },
        edit: {
            link: "",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "",
            name: "Delete",
            value: ""
        },
        list: {
            link: "phdStudentSubjectRegistrationReport/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);

    function course_registration() {
        $("#enr_no").val('').trigger("chosen:updated");
        $("#enr_no").empty();
        $.ajax({
            type: "POST",
            url: "phdStudentSubjectRegistrationReport/getEnrollmentCode",
            data: '&sem_code=' + $("#sem_code").val() + '&regType=' + $("input[name='reg']:checked").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.enroll_code != null && data.enroll_code != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.enroll_code, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '">' + item[1] + "/" + item[2] + '</option>'
                    });
                    $("#enr_no").append(str1);
                    $("#enr_no").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("Enrollment No.. Not Found..Please Select Another Semester Code..");
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });

    }

    function getReport() {
        var enr_no = $("#enr_no").val();
        var sem_code = $("#sem_code").val();
        if (enr_no != null && enr_no != '' && sem_code != null && sem_code != '') {
            $.blockUI();
            var radioValue = $("input[name='export_To']:checked").val();
            var reg = $("input[name='reg']:checked").val();
            window.location.assign('phdStudentSubjectRegistrationReport/getBatchWiseStudentList?excelfilename=registrationReport&sem_code=' + sem_code + '&enr_no=' + enr_no + '&reg=' + reg + '&radioValue=' + radioValue + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Select Required Fields..");
        }
    }
</script>