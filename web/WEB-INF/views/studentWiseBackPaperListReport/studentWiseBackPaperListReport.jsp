<%-- 
    Document   : studentWiseBackPaperListReport
    Created on : Jan 10, 2019, 12:04:08 PM
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
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                <select class="form-control" id="acadYear" name="acadYear" multiple="true" data-validation="required" data-live-search="true">               
                                    <c:forEach items="${acadYear}" var="list">
                                        <option value="${list}"><c:out value="${list}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" style="margin-left: -140px;">
                        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Report For:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6 radio-inline">
                            <input type="radio" id="reportFor" name="reportFor" value="failgrade" onchange="semesterShowHide();"/> Branch Wise Fail Students Subject with fail grade <br> 
                            <input type="radio" id="reportFor" name="reportFor" value="regwise" onchange="semesterShowHide1();"/> Registration detail of students <br>
                            <input type="radio" id="reportFor" name="reportFor" value="compulsorysubject" onchange="semesterShowHide1();"/> Regular student compulsory subjects who have done course registration <br>
                            <input type="radio" id="reportFor" name="reportFor" value="electivesubject" onchange="semesterShowHide1();"/> Regular student Elective subjects who have done course registration <br>
                            <input type="radio" id="reportFor" name="reportFor" checked="true" value="notregister" onchange="semesterShowHide1();"/> Student having back paper and not registered <br>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group"></div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showHideSemesterCode">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                <select class="form-control" id="semesterCode" name="semesterCode"  data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${semCode}" var="list">
                                        <option value="${list[0]}~@~${list[1]}"><c:out value="${list[1]} -- ${list[2]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                <select class="form-control" id="programCode" name="programCode" data-validation="required" data-live-search="true" onchange="getBranch();">               
                                    <option value="">Select</option>
                                    <c:forEach items="${program}" var="list">
                                        <option value="${list.id.programid}~@~${list.programcode}"><c:out value="${list.programcode} -- ${list.programdesc}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                <select class="form-control" id="branchCode" name="branchCode" data-validation="required" onchange="getStyNo();" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty Number:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                <select class="form-control" id="styNumber" name="styNumber" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Export To:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="export_To" name="export_To" value="E"/> Excel
                                </div>
                                <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
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
    $("#acadYear").chosen();
    $("#semesterCode").chosen();
    $("#programCode").chosen();
    $("#branchCode").chosen();
    $("#styNumber").chosen();

    function myReset() {
        $("#acadYear").val('select').trigger("chosen:updated");
        $("#semesterCode").val('').trigger("chosen:updated");
        $("#programCode").val('').trigger("chosen:updated");
        $("#branchCode").val('').trigger("chosen:updated");
        $("#styNumber").val('').trigger("chosen:updated");
        $("#showHideSemesterCode").show();
    }

    function getBranch() {
        $("#branchCode").empty();
        $.ajax({
            type: "POST",
            url: "studentWiseBackPaperList/getBranchCode",
            data: '&programCode=' + $("#programCode").val(),
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
                str1 += '<option value="All">ALL</option>';
                $.each(data.branchCode, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '~@~' + item[1] + '~@~' + item[4] + '">' + item[0] + '--' + item[1] + '</option>'
                });
                $("#branchCode").append(str1);
                $("#branchCode").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getStyNo() {
        $("#styNumber").val('').trigger("chosen:updated");
        $("#styNumber").empty();
        var semesterCode = $("#semesterCode").val();
        var programCode = $("#programCode").val();
        var branchCode = $("#branchCode").val();
        debugger;
        if (programCode != '' && branchCode != '') {
            $.ajax({
                url: "studentWiseBackPaperList/getStyNo",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "semesterCode=" + semesterCode + "&programCode=" + programCode + "&branchCode=" + encodeURIComponent(branchCode),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.styno != null) {
                        var str1 = '';
                        var str1 = '<option value="ALL">All</option>';
                        $.each(data.styno, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#styNumber").append(str1);
                        $("#styNumber").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("styNumber Not Found, Please Select Another Criteria...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code, Program, Branch first!");
        }
    }

    function semesterShowHide() {
        var semster = $("#showHideSemesterCode").hide();
        $("#semesterCode").val('').trigger("chosen:updated");
    }

    function semesterShowHide1() {
        var semester1 = $("#showHideSemesterCode").show();
    }

    function getReport() {
        var acadYear = $("#acadYear").val();
        var semesterCode = $("#semesterCode").val();
        var programCode = $("#programCode").val();
        var branchCode = $("#branchCode").val();
        var styNumber = $("#styNumber").val();
        var exportTo = $("input[name='export_To']:checked").val();
        var reportFor = $("input[name='reportFor']:checked").val();
        var level = "one";
        if (reportFor == 'failgrade') {
            if (acadYear != '' && programCode != '' && branchCode != '' && styNumber != '') {
                $.blockUI();
                window.location.assign('studentWiseBackPaperList/getReport?excelfilename=StudentWiseBackPaperList&academicyr=' + acadYear + '&reportfor=' + reportFor + '&programid=' + programCode + '&branchid=' + encodeURIComponent(branchCode) + '&stynumber=' + styNumber + '&level=' + level + '&exportto=' + exportTo + '');
                setTimeout($.unblockUI, 3000);
            } else {
                BootstrapDialog.alert("Please Enter The Required Field..");
            }
        } else {
            if (acadYear != '' && semesterCode != '' && programCode != '' && branchCode != '' && styNumber != '') {
                $.blockUI();
                window.location.assign('studentWiseBackPaperList/getReport?excelfilename=StudentWiseBackPaperList&academicyr=' + acadYear + '&reportfor=' + reportFor + '&registrationid=' + semesterCode + '&programid=' + programCode + '&branchid=' + encodeURIComponent(branchCode) + '&stynumber=' + styNumber + '&level=' + level + '&exportto=' + exportTo + '');
                setTimeout($.unblockUI, 3000);
            } else {
                BootstrapDialog.alert("Please Enter The Required Field..");
            }
        }
    }
</script>
