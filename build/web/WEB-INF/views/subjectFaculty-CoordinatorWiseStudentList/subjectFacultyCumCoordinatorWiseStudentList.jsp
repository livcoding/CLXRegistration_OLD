<%-- 
    Document   : subjectFaculty-CoordinatorWiseStudentList
    Created on : Feb 12, 2019, 10:44:05 AM
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
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="semesterCode" name="semesterCode" data-live-search="true" onchange="getSubjectCode();getDate(this.value);" data-validation="required">               
                                    <option value="">Select</option>
                                    <c:forEach items="${regCode}" var="semList">
                                        <option value="${semList[0]}~@~${semList[1]}~@~${semList[3]}~@~${semList[4]}"><c:out value="${semList[1]} -- ${semList[2]}" /></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row col-sm-3 col-lg-3 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-1 col-xs-3 col-md-3 control-label"></label>
                            <div class="col-sm-9 col-lg-10 col-xs-12 col-md-6">
                                <input type="text" name="fromDate" id="fromDate" readonly="true" data-validation="required" placeholder="From Date" class="form-control">
                            </div>
                        </div>
                        <div class="row col-sm-3 col-lg-3 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-1 col-xs-3 col-md-3 control-label"></label>
                            <div class="col-sm-9 col-lg-10 col-xs-12 col-md-6">
                                <input type="text" name="toDate" id="toDate" readonly="true" data-validation="required" placeholder="To Date" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="subjectCode" name="subjectCode" data-live-search="true" onchange="getCoordinatorWiseList();" data-validation="required">               
                                    <option value="">Select</option>subList
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <div class="row col-sm-3 col-lg-11 col-xs-3 col-md-3 form-group" style="margin-left: -80px;">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Generate Report As:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                            <div class="col-sm-9 col-lg-9 col-xs-12 col-md-6">
                                <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="reportAs" name="reportAs" checked="true" value="all" onchange="generateReportAsAll();"/> All
                                </div>
                                <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="reportAs" name="reportAs" value="cor" onchange="generateReportAsCoordinator();"/> Co-ordinator Wise List
                                </div>
                                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="reportAs" name="reportAs" value="fac" onchange="generateReportAsFaculty();"/> Faculty Wise List
                                </div>
                            </div>  
                        </div>
                    </div>
                    <!----------------Co-ordinator Wise List Code combo----------------->
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showCo-ordinatorCode" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Co-ordinator Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="co-ordinatorCode" name="co-ordinatorCode" data-live-search="true" data-validation="required">               
                                    <option value="">Select</option>cordCode
                                </select>
                            </div>
                        </div>
                    </div>
                    <!----------------End Co-ordinator Wise List Code Combo----------------->

                    <!--------------------Faculty Wise List Code Combo---------------------->
                    <div class="row col-sm-3 col-lg-12 col-xs-3 col-md-3 form-group" id="showFacultyCode" style="display: none;">
                        <div class="row col-sm-3 col-lg-6 col-xs-3 col-md-3 form-group">
                            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Faculty Wise Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <select class="form-control" id="facultyWiseCode" name="facultyWiseCode" data-live-search="true" data-validation="required">               
                                    <option value="">Select</option>
                                    <c:forEach items="${facultyCode}" var="faculty">
                                        <option value="${faculty.employeeid}~@~${faculty.employeecode}~@~${faculty.employeename}"><c:out value="${faculty.employeecode} -- ${faculty.employeename}" /></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <!--------------------End Faculty Wise List Code Combo--------------------->
                    <div class="row col-lg-12 form-group">
                        <div class="row col-sm-3 col-lg-8 col-xs-3 col-md-3 form-group" style="margin-left: -72px;">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Export To:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                            <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                                <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6 radio-inline">
                                    <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                                </div>
                                
                                <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6 radio-inline" style="display:none">
                                    <input type="radio" id="export_To" name="export_To" value="W"/> Word
                                </div>
                                <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6 radio-inline" style="display:none">
                                    <input type="radio" id="export_To" name="export_To" value="E"/> Excel
                                </div>
                                <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6 radio-inline" style="display:none">
                                    <input type="radio" id="export_To" name="export_To" value="H"/> HTML
                                </div>                               
                            </div>  
                        </div>
                    </div>
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
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $("#semesterCode").chosen();
    $("#subjectCode").chosen();
    $("#co-ordinatorCode").chosen({width: "100%"});
    $("#facultyWiseCode").chosen({width: "100%"});

    function myReset() {
        $("#semesterCode").val('').trigger("chosen:updated");
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#co-ordinatorCode").val('').trigger("chosen:updated");
        $("#facultyWiseCode").val('').trigger("chosen:updated");
        $("#showFacultyCode").hide();
        $("#showCo-ordinatorCode").hide();
    }

    function generateReportAsAll() {
        $("#facultyWiseCode").val('').trigger("chosen:updated");
        $("#co-ordinatorCode").val('').trigger("chosen:updated");
        $("#showCo-ordinatorCode").hide();
        $("#showFacultyCode").hide();
    }

    function generateReportAsCoordinator() {
        $("#facultyWiseCode").val('').trigger("chosen:updated");
        $("#showCo-ordinatorCode").show();
        $("#showFacultyCode").hide();
    }

    function getCoordinatorWiseList() {
        $("#co-ordinatorCode").empty();
        $.ajax({
            type: "POST",
            url: "subjectFacultyCumCoordinatorWiseStudentList/getCoordinatotCode",
            data: '&registrationId=' + $("#semesterCode").val() + '&subjectId=' + $("#subjectCode").val(),
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
                $.each(data.cordCode, function (i, item)
                {
//                    str1 += '<option value="' + item[0] + '~@~' + item[1] + '">' + item[1] + '--' + item[2] + '</option>'
                    str1 += '<option value="' + item.employeeid +  '~@~' + item.employeename + '~@~' + item.employeecode + '">' + item.employeename + '('+item.employeecode+')'+'</option>'
                });
                $("#co-ordinatorCode").append(str1);
                $("#co-ordinatorCode").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function generateReportAsFaculty() {
        $("#showFacultyCode").show();
        $("#showCo-ordinatorCode").hide();
    }

    function getSubjectCode() {
        $("#subjectCode").empty();
        $.ajax({
            type: "POST",
            url: "subjectFacultyCumCoordinatorWiseStudentList/getSubjectCode",
            data: '&regId=' + $("#semesterCode").val(),
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
                $.each(data.subCode, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '">' + item[1] + '--' + item[2] + '</option>'
                });
                $("#subjectCode").append(str1);
                $("#subjectCode").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getDate(id) {
        $("#fromDate").empty();
        $("#toDate").empty();
        var ids = id.split("~@~");
        $("#fromDate").val(ids[2]);
        $("#toDate").val(ids[3]);
    }

    function getReport() {
        var semester = $("#semesterCode").val();
        var subject = $("#subjectCode").val();
        var coordinator = $("#co-ordinatorCode").val();
        var faculty = $("#facultyWiseCode").val();
        var exportTo = $("input[name='export_To']:checked").val();
        var reportAs = $("input[name='reportAs']:checked").val();
        if (semester != '' && subject != '') {
            if (reportAs == "all") {
                window.location.assign('subjectFacultyCumCoordinatorWiseStudentList/getReport?excelfilename=subjectFacultyCumCoordinatorWiseStudentListReport&regId=' + semester + '&subject=' + subject + '&coordinator=' + coordinator + '&faculty=' + faculty + '&exportTo=' + exportTo + '');
            }
            if (reportAs == "fac") {
                if (faculty != '') {
                    window.location.assign('subjectFacultyCumCoordinatorWiseStudentList/getReport?excelfilename=subjectFacultyCumCoordinatorWiseStudentListReport&regId=' + semester + '&subject=' + subject + '&coordinator=' + coordinator + '&faculty=' + faculty + '&exportTo=' + exportTo + '');
                } else {
                    BootstrapDialog.alert("Please Enter The Faculty Code...!");
                }
            } else if (reportAs == "cor") {
                if (coordinator != '') {
                    window.location.assign('subjectFacultyCumCoordinatorWiseStudentList/getReport?excelfilename=subjectFacultyCumCoordinatorWiseStudentListReport&regId=' + semester + '&subject=' + subject + '&coordinator=' + coordinator + '&faculty=' + faculty + '&exportTo=' + exportTo + '');
                } else {
                    BootstrapDialog.alert("Please Enter The Co-ordinator Code...!.");
                }
            }
        } else {
            BootstrapDialog.alert("Please Enter The Semester Code, Subject Code...!");
        }
    }
</script>



