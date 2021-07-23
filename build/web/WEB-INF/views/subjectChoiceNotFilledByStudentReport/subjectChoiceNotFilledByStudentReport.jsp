<%-- 
    Document   : subjectChoiceNotFilledByStudentReport
    Created on : Feb 19, 2019, 2:45:14 PM
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
                    <div class="row col-lg-12 form-group" style="margin-left: -105px;">
                        <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Report Type:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                            <div class="col-sm-9 col-lg-9 col-xs-12 col-md-6">
                                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="report" name="report" checked="true" onchange="choiceNotFilledByStudent();" value="report0"/><b> Choice Not Filled By Student</b>
                                </div>
                                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="report" name="report" onchange="studentWiseSubjectChoice();" value="report1"/><b> Student Wise Subject Choice</b>
                                </div>
                            </div>  
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" style="margin-left: -105px;">
                        <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label"></label>
                            <div class="col-sm-9 col-lg-9 col-xs-12 col-md-6">
                                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline" >
                                    <input type="radio" id="report" name="report" onchange="departmentWise();" value="report2"/><b> Department Wise</b>
                                </div>
                                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="report" name="report" onchange="subjectWise();" value="report3"/><b> Subject Wise</b>
                                </div>
                            </div>  
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="registrationCode" name="registrationCode" data-validation="required" onchange="getAcademicYear();" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${regCode}" var="list">
                                        <option value="${list[0]}~@~${list[1]}"><c:out value="${list[1]} -- ${list[2]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group" id="showHideDepartment" style="display: none;">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Department:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="department" name="department" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${depList}" var="list">
                                        <option value="${list[0]}~@~${list[2]}"><c:out value="${list[1]} -- ${list[2]}"/></option>
                                    </c:forEach> 
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group" id="showHideAcademic" style="display: none;">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="academicYear" name="academicYear" data-validation="required" onchange="getProgramCode();" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideProgram" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="programCode" name="programCode" data-validation="required" onchange="getSectionCode();" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideSection" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="sectionCode" name="sectionCode" onchange="getBasketCode();getSemester();" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideSemester" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group" >
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty Number
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="semester" name="semester" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideBasket" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Basket Type:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="basketCode" name="basketCode" multiple="true"  data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6">
                            <button type="button" class="fa fa-search btn btn-success" onclick="getSubjectCode();"></button>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideSubject" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="subjectCode" name="subjectCode" onchange="getSubSectionCode();" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideSubSection" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sub-Section Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="subSectionCode" name="subSectionCode" multiple="true" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Export To:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline" id="showHidePdf">
                                    <input type="radio" id="export_To1" name="export_To1" checked="true"  value="P"/> PDF
                                </div>
                                <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline" id="showHideExcel" style="display:none">
                                    <input type="radio" id="export_To2" name="export_To2"  checked="true" value="E" /> Excel
                                </div>
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
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $("#registrationCode").chosen({width: "100%"});
    $("#academicYear").chosen({width: "100%"});
    $("#programCode").chosen({width: "100%"});
    $("#sectionCode").chosen({width: "100%"});
    $("#basketCode").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});
    $("#subSectionCode").chosen({width: "100%"});
    $("#department").chosen({width: "100%"});
    $("#semester").chosen({width: "100%"});

    function myReset() {
        $("#registrationCode").val('').trigger("chosen:updated");
        $("#academicYear").val('').trigger("chosen:updated");
        $("#programCode").val('').trigger("chosen:updated");
        $("#sectionCode").val('').trigger("chosen:updated");
        $("#basketCode").val('x').trigger("chosen:updated");
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#subSectionCode").val('x').trigger("chosen:updated");
        $("#department").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");
        choiceNotFilledByStudent();
    }

    function choiceNotFilledByStudent() {
        $("#showHideAcademic").hide();
        $("#showHideProgram").hide();
        $("#showHideSection").hide();
        $("#showHideBasket").hide();
        $("#showHideSubject").hide();
        $("#showHideSubSection").hide();
        $("#showHideDepartment").hide();
        $("#showHideSemester").hide();
        $("#showHideExcel").hide();
        $("#showHidePdf").show();
        $("#registrationCode").val('').trigger("chosen:updated");
    }

    function studentWiseSubjectChoice() {
        $("#showHideAcademic").show();
        $("#showHideProgram").show();
        $("#showHideSection").show();
        $("#showHideBasket").show();
        $("#showHideSubject").show();
        $("#showHideSubSection").show();
        $("#showHideExcel").show();
        $("#showHidePdf").hide();
        $("#showHideDepartment").hide();
        $("#showHideSemester").hide();
        $("#registrationCode").val('').trigger("chosen:updated");
        $("#academicYear").val('').trigger("chosen:updated");
        $("#programCode").val('').trigger("chosen:updated");
        $("#sectionCode").val('').trigger("chosen:updated");
        $("#basketCode").val('x').trigger("chosen:updated");
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#subSectionCode").val('x').trigger("chosen:updated");
    }

    function departmentWise() {
        $("#showHideAcademic").show();
        $("#showHideProgram").show();
        $("#showHideSection").show();
        $("#showHideBasket").show();
        $("#showHideSubject").show();
        $("#showHideSubSection").show();
        $("#showHideExcel").hide();
        $("#showHidePdf").show();
        $("#showHideDepartment").show();
        $("#showHideSemester").show();
        $("#registrationCode").val('').trigger("chosen:updated");
        $("#academicYear").val('').trigger("chosen:updated");
        $("#programCode").val('').trigger("chosen:updated");
        $("#sectionCode").val('').trigger("chosen:updated");
        $("#basketCode").val('x').trigger("chosen:updated");
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#subSectionCode").val('x').trigger("chosen:updated");
        $("#department").val('').trigger("chosen:updated");
        $("#semester").val('').trigger("chosen:updated");

    }

    function subjectWise() {
        $("#showHideAcademic").show();
        $("#showHideProgram").show();
        $("#showHideSection").show();
        $("#showHideBasket").show();
        $("#showHideSubject").show();
        $("#showHideExcel").hide();
        $("#showHidePdf").show();
        $("#showHideSubSection").show();
        $("#showHideDepartment").hide();
        $("#showHideSemester").hide();
        $("#registrationCode").val('').trigger("chosen:updated");
        $("#academicYear").val('').trigger("chosen:updated");
        $("#programCode").val('').trigger("chosen:updated");
        $("#sectionCode").val('').trigger("chosen:updated");
        $("#basketCode").val('x').trigger("chosen:updated");
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#subSectionCode").val('x').trigger("chosen:updated");
    }

    function getAcademicYear() {
        var reg = $("#registrationCode").val();
        if (reg != '') {
            $("#academicYear").empty();
            $("#programCode").empty();
            $("#sectionCode").empty();
            $("#basketCode").empty();
            $("#subjectCode").empty();
            $("#subSectionCode").empty();
            $("#semester").empty();
            $("#academicYear").val('').trigger("chosen:updated");
            $("#programCode").val('').trigger("chosen:updated");
            $("#sectionCode").val('').trigger("chosen:updated");
            $("#basketCode").val('x').trigger("chosen:updated");
            $("#subjectCode").val('').trigger("chosen:updated");
            $("#subSectionCode").val('x').trigger("chosen:updated");
            $("#semester").val('').trigger("chosen:updated");
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getAcademicYear",
                data: '&regId=' + $("#registrationCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    str1 += '<option value="All">ALL</option>';
                    $.each(data.acadList, function (i, item)
                    {
                        str1 += '<option value="' + item + '">' + item + '</option>'
                    });
                    $("#academicYear").append(str1);
                    $("#academicYear").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        }
    }

    function getProgramCode() {
        var reg = $("#registrationCode").val();
        var acad = $("#academicYear").val();
        if (reg != '' && acad != '') {
            $("#programCode").empty();
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getProgramCode",
                data: '&regId=' + $("#registrationCode").val() + '&acadYear=' + $("#academicYear").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    str1 += '<option value="All">ALL</option>';
                    $.each(data.progList, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                    });
                    $("#programCode").append(str1);
                    $("#programCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code or Academic Year..!");
        }
    }

    function getSectionCode() {
        var reg = $("#registrationCode").val();
        var prog = $("#programCode").val();
        if (reg != '' && prog != '') {
            $("#sectionCode").empty();
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getSectionCode",
                data: '&regId=' + $("#registrationCode").val() + '&programCode=' + $("#programCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    str1 += '<option value="All">ALL</option>';
                    $.each(data.secList, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                    });
                    $("#sectionCode").append(str1);
                    $("#sectionCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code or Academic Year..!");
        }
    }

    function getBasketCode() {
        var reg = $("#registrationCode").val();
        var prog = $("#programCode").val();
        var acad = $("#academicYear").val();
        var section = $("#sectionCode").val();
        if (reg != '' && prog != '' && acad != '' && section != '') {
            $("#basketCode").empty();
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getBasketCode",
                data: '&registrationCode=' + $("#registrationCode").val() + '&programCode=' + $("#programCode").val() + '&academicYear=' + $("#academicYear").val() + '&sectionCode=' + $("#sectionCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    debugger;
                    $.each(data.basketList, function (i, item)
                    {
                        str1 += '<option value="' + item + '">' + item + '</option>'
                    });
                    $("#basketCode").append(str1);
                    $("#basketCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields..!");
        }
    }

    function getSubjectCode() {
        var reg = $("#registrationCode").val();
        var prog = $("#programCode").val();
        var acad = $("#academicYear").val();
        var section = $("#sectionCode").val();
        var basket = $("#basketCode").val();
        if (reg != '' && prog != '' && acad != '' && section != '' && basket != '') {
            $("#subjectCode").empty();
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getSubjectCode",
                data: '&registrationCode=' + $("#registrationCode").val() + '&programCode=' + $("#programCode").val() + '&academicYear=' + $("#academicYear").val() + '&sectionCode=' + $("#sectionCode").val() + '&basketCode=' + $("#basketCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    str1 += '<option value="All">ALL</option>';
                    $.each(data.subjectList, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '">' + item[2] + ' -- ' + item[3] + '</option>'
                    });
                    $("#subjectCode").append(str1);
                    $("#subjectCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields..!");
        }
    }

    function getSubSectionCode() {
        var reg = $("#registrationCode").val();
        var acad = $("#academicYear").val();
        var subject = $("#subjectCode").val();
        var section = $("#sectionCode").val();
        if (reg != '' && acad != '' && subject != '' && section != '') {
            $("#subSectionCode").empty();
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getSubSectionCode",
                data: '&registrationCode=' + $("#registrationCode").val() + '&academicYear=' + $("#academicYear").val() + '&subjectCode=' + $("#subjectCode").val() + '&sectionCode=' + $("#sectionCode").val(),
                dataType: "json",
                async: true,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    $.each(data.subSectList, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                    });
                    $("#subSectionCode").append(str1);
                    $("#subSectionCode").trigger("chosen:updated");
                    setTimeout($.unblockUI, 1000);
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                    $.unblockUI();
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields..!");
        }
    }

    function getSemester() {
        var reg = $("#registrationCode").val();
        var prog = $("#programCode").val();
        var section = $("#sectionCode").val();
        if (reg != '' && prog != '' && section != '') {
            $("#semester").empty();
            $.ajax({
                type: "POST",
                url: "subjectChoiceNotFilledByStudentReport/getSemester",
                data: '&registrationCode=' + $("#registrationCode").val() + '&programCode=' + $("#programCode").val() + '&sectionCode=' + $("#sectionCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    str1 += '<option value="">Select</option>';
                    $.each(data.semList, function (i, item)
                    {
                        str1 += '<option value="' + item + '">' + item + '</option>'
                    });
                    $("#semester").append(str1);
                    $("#semester").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong.', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields..!");
        }
    }

    function getReport() {
        var registration = $("#registrationCode").val();
        var department = $("#department").val();
        var academicYear = $("#academicYear").val();
        var semester = $("#semester").val();
        var programCode = $("#programCode").val();
        var sectionCode = $("#sectionCode").val();
        var basketCode = $("#basketCode").val();
        var subjectCode = $("#subjectCode").val();
        var subSectionCode = $("#subSectionCode").val();
        var report = $("input[name='report']:checked").val();
        if (report == 'report0') {
            if (registration != '') {
                var exportto = $("input[name='export_To1']:checked").val();
                var reporttype = "0";
                var reportName = "Choice Not Filled By Student";
                window.location.assign('subjectChoiceNotFilledByStudentReport/getReport?excelfilename=SubjectChoiceNotFilledByStudentReport&registration=' + registration + '&academicYear=' + academicYear + '&programCode=' + programCode + '&sectionCode=' + sectionCode + '&basketCode=' + basketCode + '&subjectCode=' + subjectCode + '&subSectionCode=' + subSectionCode + '&department=' + department + '&semester=' + semester + '&reporttype=' + reporttype + '&exportto=' + exportto + '&reportName=' + reportName + '');
            } else {
                BootstrapDialog.alert("Please Select The Required Field..");
            }
        } else if (report == 'report1') {
            if (registration != '' && academicYear != '' && programCode != '' && sectionCode != '' && basketCode != '' && subjectCode != '' && subSectionCode != '') {
                var reporttype = 1;
                var exportto = $("input[name='export_To2']:checked").val();
                var reportName = "Student Wise Subject Choice";
                window.location.assign('subjectChoiceNotFilledByStudentReport/getReport?excelfilename=StudentWiseSubjectChoiceReport&registration=' + registration + '&academicYear=' + academicYear + '&programCode=' + programCode + '&sectionCode=' + sectionCode + '&basketCode=' + basketCode + '&subjectCode=' + subjectCode + '&subSectionCode=' + subSectionCode + '&department=' + department + '&semester=' + semester + '&reporttype=' + reporttype + '&exportto=' + exportto + '&reportName=' + reportName + '');
            } else {
                BootstrapDialog.alert("Please Select The Required Field..");
            }
        } else if (report == 'report2') {
            if (registration != '' && academicYear != '' && programCode != '' && sectionCode != '' && basketCode != '' && subjectCode != '' && subSectionCode != '' && department != '' && semester != '') {
                var reporttype = 2;
                var exportto = $("input[name='export_To1']:checked").val();
                var reportName = "Department Wise";
                window.location.assign('subjectChoiceNotFilledByStudentReport/getReport?excelfilename=DepartmentWise&registration=' + registration + '&academicYear=' + academicYear + '&programCode=' + programCode + '&sectionCode=' + sectionCode + '&basketCode=' + basketCode + '&subjectCode=' + subjectCode + '&subSectionCode=' + subSectionCode + '&department=' + department + '&semester=' + semester + '&reporttype=' + reporttype + '&exportto=' + exportto + '&reportName=' + reportName + '');
            } else {
                BootstrapDialog.alert("Please Select The Required Field..");
            }
        } else if (report == 'report3') {
            if (registration != '' && academicYear != '' && programCode != '' && sectionCode != '' && basketCode != '' && subjectCode != '' && subSectionCode != '') {
                var reporttype = 5;
                var exportto = $("input[name='export_To1']:checked").val();
                var reportName = "Subejct Wise";
                window.location.assign('subjectChoiceNotFilledByStudentReport/getReport?excelfilename=SubjectWiseReport&registration=' + registration + '&academicYear=' + academicYear + '&programCode=' + programCode + '&sectionCode=' + sectionCode + '&basketCode=' + basketCode + '&subjectCode=' + subjectCode + '&subSectionCode=' + subSectionCode + '&department=' + department + '&semester=' + semester + '&reporttype=' + reporttype + '&exportto=' + exportto + '&reportName=' + reportName + '');
            } else {
                BootstrapDialog.alert("Please Select The Required Field..");
            }
        }
    }

</script>
