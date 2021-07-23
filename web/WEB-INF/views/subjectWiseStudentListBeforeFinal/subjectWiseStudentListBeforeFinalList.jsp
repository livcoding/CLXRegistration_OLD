<%-- 
    Document   : subjectWiseStudentListBeforeFinalList
    Created on : Jan 17, 2019, 11:11:52 AM
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
                            <select class="form-control" id="sty_code" name="sty_code" data-live-search="true" data-validation="required" onchange="subjectCode();">               
                                <option value="">Select</option>
                                <c:forEach items="${sem_list}" var="list">
                                    <option value="${list.id.registrationid}~@~${list.registrationcode}"><c:out value="${list.registrationcode} / ${list.registrationdesc}"/></option>
                                </c:forEach> 
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="sub_code" name="sub_code" data-live-search="true" data-validation="required" onchange="getAcademicYear();">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="acad_year" name="acad_year" data-live-search="true" data-validation="required" onchange="getProgramCode();">                
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Program Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="prog_code" name="prog_code" data-live-search="true" data-validation="required" onchange="getSemester();">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Sty Number:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="sem" name="sem" data-live-search="true" data-validation="required" onchange="getSectionMaster();">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Section:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="sec" name="sec" data-live-search="true" data-validation="required" onchange="getSubSection();">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label"> SubSection:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="sub_sec" name="sub_sec" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="pdf" name="export_To" checked="true" value="P"/>PDF
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
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#sty_code").val('').trigger("chosen:updated");
        $("#sub_code").val('').trigger("chosen:updated");
        $("#acad_year").val('').trigger("chosen:updated");
        $("#prog_code").val('').trigger("chosen:updated");
        $("#sem").val('').trigger("chosen:updated");
        $("#sec").val('').trigger("chosen:updated");
        $("#sub_sec").val('').trigger("chosen:updated");
    }
    $("#sty_code").chosen();
    $("#sub_code").chosen();
    $("#acad_year").chosen();
    $("#prog_code").chosen();
    $("#sem").chosen();
    $("#sec").chosen();
    $("#sub_sec").chosen();
    $(document).ready(function () {
        $.validate();
    });
    function getSemester() {
        $("#sem").val('').trigger("chosen:updated");
        $("#sec").val('').trigger("chosen:updated");
        $("#sub_sec").val('').trigger("chosen:updated");
        $("#sem").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListBeforeFinal/getSemester",
            data: '&reg_id=' + $("#sty_code").val() + '&sub_id=' + $("#sub_code").val() + '&acad_year=' + $("#acad_year").val() + '&prog_code=' + $("#prog_code").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.sem_list != null && data.sem_list != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.sem_list, function (i, item)
                    {
                        str1 += '<option value="' + item + '">' + item + '</option>'
                    });
                    $("#sem").append(str1);
                    $("#sem").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found..");
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getSubSection() {
        $("#sub_sec").val('').trigger("chosen:updated");
        $("#sub_sec").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListBeforeFinal/getsubsection",
            data: '&reg_id=' + $("#sty_code").val() + '&sub_id=' + $("#sub_code").val() + '&acad_year=' + $("#acad_year").val() + '&prog_code=' + $("#prog_code").val() + '&sem=' + $("#sem").val() + '&sec=' + $("#sec").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.subSec_list != null && data.subSec_list != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.subSec_list, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + ',' + item[1] + '">' + item[1] + ' / ' + item[2] + '</option>'
                    });
                    $("#sub_sec").append(str1);
                    $("#sub_sec").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found..");
                }


            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getSectionMaster() {
        $("#sub_sec").val('').trigger("chosen:updated");
        $("#sec").val('').trigger("chosen:updated");
        $("#sec").empty();
        var sem = $("#sem").val();
        if (sem != '') {
            $.ajax({
                type: "POST",
                url: "subjectWiseStudentListBeforeFinal/getSectionMaster",
                data: '&reg_id=' + $("#sty_code").val() + '&sub_id=' + $("#sub_code").val() + '&acad_year=' + $("#acad_year").val() + '&prog_code=' + $("#prog_code").val() + '&sem=' + $("#sem").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.sec_list != null && data.sec_list != '')
                    {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.sec_list, function (i, item)
                        {
                            str1 += '<option value="' + item[1] + '">' + item[2] + ' / ' + item[3] + '</option>'
                        });
                        $("#sec").append(str1);
                        $("#sec").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("No Data Found..");
                    }


                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please select Sty Number..");
        }
    }



    function getProgramCode() {
        $("#sem").val('').trigger("chosen:updated");
        $("#sec").val('').trigger("chosen:updated");
        $("#sub_sec").val('').trigger("chosen:updated");
        $("#prog_code").val('').trigger("chosen:updated");
        $("#prog_code").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListBeforeFinal/getProgramCode",
            data: '&reg_id=' + $("#sty_code").val() + '&sub_id=' + $("#sub_code").val() + '&acad_year=' + $("#acad_year").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.prog_list != null && data.prog_list != '') {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    str1 += '<option value="all">ALL</option>';
                    $.each(data.prog_list, function (i, item)
                    {
                        str1 += '<option value="' + item[1] + ',' + item[2] + '">' + item[2] + ' / ' + item[3] + '</option>'
                    });
                    $("#prog_code").append(str1);
                    $("#prog_code").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found..");
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
    function getAcademicYear() {
        $("#sem").val('').trigger("chosen:updated");
        $("#acad_year").val('').trigger("chosen:updated");
        $("#prog_code").val('').trigger("chosen:updated");
        $("#sec").val('').trigger("chosen:updated");
        $("#sub_sec").val('').trigger("chosen:updated");
        $("#acad_year").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListBeforeFinal/getAcademicYear",
            data: '&reg_id=' + $("#sty_code").val() + '&sub_id=' + $("#sub_code").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.acadyear_list != null && data.acadyear_list != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.acadyear_list, function (i, item)
                    {
                        str1 += '<option value="' + item[1] + '">' + item[1] + '</option>'
                    });
                    $("#acad_year").append(str1);
                    $("#acad_year").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found..");
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
    function subjectCode() {
        $("#sem").val('').trigger("chosen:updated");
        $("#sub_code").val('').trigger("chosen:updated");
        $("#acad_year").val('').trigger("chosen:updated");
        $("#prog_code").val('').trigger("chosen:updated");
        $("#sec").val('').trigger("chosen:updated");
        $("#sub_sec").val('').trigger("chosen:updated");
        $("#sub_code").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListBeforeFinal/getSubjectCode",
            data: '&reg_id=' + $("#sty_code").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.subCode_list != null && data.subCode_list != '') {
                    var str1 = '';
                    str1 += '<option value="">Select</option>';
                    str1 += '<option value="all">ALL</option>';
                    $.each(data.subCode_list, function (i, item)
                    {
                        str1 += '<option value="' + item[1] + ',' + item[2] + ',' + item[4] + '">' + item[4] + ' / ' + item[5] + '</option>'
                    });
                    $("#sub_code").append(str1);
                    $("#sub_code").trigger("chosen:updated");
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
        $.validate();
        var regid_code = $("#sty_code").val();
        var sub_id = $("#sub_code").val();
        var acad_year = $("#acad_year").val();
        var prog_code = $("#prog_code").val();
        var sub_sec = $("#sub_sec").val();
        var sem = $("#sem").val();
        var sec = $("#sec").val();
        var radioValue = $("input[name='export_To']:checked").val();
        var level = "one";
        if (regid_code != '' && sub_id != '' && acad_year != '' && prog_code != '' && sub_sec != '' && sem != '' && sec != '') {
            $.blockUI();
            window.location.assign('subjectWiseStudentListBeforeFinal/getReport?excelfilename=subjectWiseStudentListBeforeFinal&regid_code=' + regid_code + '&sub_id=' + sub_id + '&acad_year=' + acad_year + '&sem=' + sem + '&prog_code=' + prog_code + '&sub_sec=' + sub_sec + '&sec=' + sec + '&exportto=' + radioValue + '&level=' + level + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>
