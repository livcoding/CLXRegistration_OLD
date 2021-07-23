<%-- 
    Document   : subjectCoordinatorReportList
    Created on : Feb 4, 2019, 3:49:41 PM
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
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Coordinate For:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="co_ordinate" name="co_ordinate" data-live-search="true" data-validation="required" onchange="disableSubjectCode(this.value);getSubCode();">               
                                <option value="">Select</option>
                                <c:forEach items="${coordinate_list}" var="coordinate_list">
                                    <option value="${coordinate_list[0]},${coordinate_list[1]}"><c:out value="${coordinate_list[1]}  /  ${coordinate_list[4]}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" style="display:none;" id="regdiv">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="regId" name="regId" data-live-search="true" data-validation="required" onchange="getSubCode();">               
                                <option value="">Select</option>
                                <c:forEach items="${regList}" var="reList">
                                    <option value="${reList[0]},${reList[1]}"><c:out value="${reList[1]}  /  ${reList[2]}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" id="SubjectCode">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="sub_code" name="sub_code" data-live-search="true" data-validation="required" onchange="getEmpType();">               
                                <option value="All">All</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group " id="AcademicYear" style="display: none">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Academic Year:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="acadyear" name="acadyear" data-live-search="true" data-validation="required" onchange="">               
                                <option value="All">All</option>
                                <c:forEach items="${acadyear}" var="acadyear">
                                    <option value="${acadyear}"><c:out value="${acadyear}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="co_ord" name="co_ord" checked="true" value="A" onclick="getCoordinate();"/>All Coordinator
                            </div>
                            <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="co_ord" name="co_ord"  value="S" onclick="myFunction();"/>Selected Coordinator
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            </div>
                            <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                <input type="radio" id="staff" name="staff" value="TEACH" onclick="getEmp_type();"/>Teaching Staff &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" id="staff1" name="staff"  value="ALL" onclick="getEmp_type1();"/>All Staff
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Coordinate Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="co_or_code" name="co_or_code" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Order By:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="margin-top:8px">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="ord_by" name="ord_by" checked="true" value="C" class="radio-inline" /> Employee
                            </div>
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="ord_by" name="ord_by"  value="S" class="radio-inline"/> Subject
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label"> Sorted By:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="margin-top:8px">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="sorted_by" name="sorted_by" checked="true" value="asc" class="radio-inline"/> Ascending
                            </div>
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="sorted_by" name="sorted_by"  value="desc" class="radio-inline"/> Descending
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label"> Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="margin-top:8px">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P" class="radio-inline"/> PDF
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
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#regId").val('').trigger("chosen:updated");
        $("#co_ordinate").val('').trigger("chosen:updated");
        $("#sub_code").empty();
        $("#sub_code").val('').trigger("chosen:updated");
        $("#co_or_code").empty();
        $("#co_or_code").val('').trigger("chosen:updated");
        $("#acadyear").empty();
        $("#acadyear").val('').trigger("chosen:updated");
    }
    $(document).ready(function () {
        getCoordinate();
    });
    $("#regId").chosen({width: "100%"});
    $("#co_ordinate").chosen({width: "100%"});
    $("#sub_code").chosen({width: "100%"});
    $("#co_or_code").chosen({width: "100%"});
    $("#acadyear").chosen({width: "100%"});

    function disableSubjectCode(id) {
        var carter = id;
        var arlene3 = carter.split(",");
        if (arlene3[1] == 'A') {
            $("#SubjectCode").hide();
            $("#AcademicYear").show();
            $("#regdiv").hide();
            $("#sub_code").val('').trigger("chosen:updated");
        } else if (arlene3[1] == 'C') {
            $("#SubjectCode").show();
            $("#AcademicYear").hide();
            $("#regdiv").hide();
            $("#acadyear").val('All').trigger("chosen:updated");
        } else {
            $("#SubjectCode").show();
            $("#AcademicYear").hide();
            $("#regdiv").show();
            $("#acadyear").val('All').trigger("chosen:updated");
        }
    }

    function myFunction() {
        document.getElementById("staff").disabled = false;
        document.getElementById("staff1").disabled = false;
        $("#co_or_code").val('').trigger("chosen:updated");
        $('#co_or_code').prop('disabled', false).trigger("chosen:updated");
    }

    function getCoordinate() {
        document.getElementById("staff").disabled = true;
        document.getElementById("staff1").disabled = true;
        $("#co_or_code").val('').trigger("chosen:updated");
        $('#co_or_code').prop('disabled', true).trigger("chosen:updated");
    }

    function getEmp_type() {
        $("#co_or_code").empty();
        $.ajax({
            type: "POST",
            url: "subjectCoordinatorReport/getEmp_Type",
            data: '&staff=' + $("#staff").val(),
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
                $.each(data.emp_list, function (i, item)
                {
                    str1 += '<option value="' + item[1] + ',' + item[11] + '">' + item[2] + " / " + item[4] + '</option>'
                });
                $("#co_or_code").append(str1);
                $("#co_or_code").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function getEmp_type1() {
        $("#co_or_code").empty();
        $.ajax({
            type: "POST",
            url: "subjectCoordinatorReport/getEmp_Type",
            data: '&staff=' + $("#staff1").val(),
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
                $.each(data.emp_list, function (i, item)
                {
                    str1 += '<option value="' + item[1] + ',' + item[11] + '">' + item[2] + " / " + item[4] + '</option>'
                });
                $("#co_or_code").append(str1);
                $("#co_or_code").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });

    }
    function getSubCode() {
        var red_id = $("#regId").val();
        $("#sub_code").empty();
//        if (red_id != '') {
        $.ajax({
            type: "POST",
            url: "subjectCoordinatorReport/getSubCode",
            data: '&regId=' + $("#regId").val() + '&co_ordinate=' + $("#co_ordinate").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="All">All</option>';
                $.each(data.getSubjectCode, function (i, item)
                {
                    str1 += '<option value="' + item[1] + '">' + item[3] + " / " + item[4] + '</option>'
                });
                $("#sub_code").append(str1);
                $("#sub_code").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
//        } else {
//            BootstrapDialog.alert("Please Select Semester Code..");
//        }
    }

    function getReport() {
        var regId = $("#regId").val();
        var sub_code = $("#sub_code").val();
        var acadyear = $("#acadyear").val();
        var co_or_code = $("#co_or_code").val();
        var co_ordinate = $("#co_ordinate").val();
        var carter = co_ordinate;
        var arlene3 = carter.split(",");
        var radioValue = $("input[name='export_To']:checked").val();
        var orderVal = $("input[name='ord_by']:checked").val();
        var sortedVal = $("input[name='sorted_by']:checked").val();
        var co_ord = $("input[name='co_ord']:checked").val();
        if (regId != '' && co_ordinate != '') {
            if (arlene3[1] == 'A') {
                if (co_ord == 'A') {
                    $.blockUI();
                    window.location.assign('subjectCoordinatorReport/getReport?excelfilename=SubjectCoorinateReport&regId=' + regId + '&sub_code=' + sub_code + '&sortedVal=' + sortedVal + '&orderVal=' + orderVal + '&co_or_code=' + co_or_code + '&co_ord=' + co_ord + '&co_ordinate=' + co_ordinate + '&radioValue=' + radioValue + '&acadyear=' + acadyear + '');
                    setTimeout($.unblockUI, 3000);
                } else {
                    if (co_or_code != '')
                    {
                        $.blockUI();
                        window.location.assign('subjectCoordinatorReport/getReport?excelfilename=SubjectCoorinateReport&regId=' + regId + '&sub_code=' + sub_code + '&sortedVal=' + sortedVal + '&orderVal=' + orderVal + '&co_or_code=' + co_or_code + '&co_ord=' + co_ord + '&co_ordinate=' + co_ordinate + '&radioValue=' + radioValue + '&acadyear=' + acadyear + '');
                        setTimeout($.unblockUI, 3000);
                    } else {
                        BootstrapDialog.alert("Please Select The Required Field..");
                    }
                }
            } else {
                if (sub_code != '') {
                    if (co_ord == 'A') {
                        $.blockUI();
                        window.location.assign('subjectCoordinatorReport/getReport?excelfilename=SubjectCoorinateReport&regId=' + regId + '&sub_code=' + sub_code + '&sortedVal=' + sortedVal + '&orderVal=' + orderVal + '&co_or_code=' + co_or_code + '&co_ord=' + co_ord + '&co_ordinate=' + co_ordinate + '&radioValue=' + radioValue + '&acadyear=' + acadyear + '');
                    } else {
                        if (co_or_code != '')
                        {
                            window.location.assign('subjectCoordinatorReport/getReport?excelfilename=SubjectCoorinateReport&regId=' + regId + '&sub_code=' + sub_code + '&sortedVal=' + sortedVal + '&orderVal=' + orderVal + '&co_or_code=' + co_or_code + '&co_ord=' + co_ord + '&co_ordinate=' + co_ordinate + '&radioValue=' + radioValue + '&acadyear=' + acadyear + '');
                            setTimeout($.unblockUI, 3000);
                        } else {
                            BootstrapDialog.alert("Please Select The Required Field..");
                        }
                    }
                } else {
                    BootstrapDialog.alert("Please Select The Required Field..");
                }
            }
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Select The Required Field..");
        }
    }
</script>