<%-- 
    Document   : studentChoiceDetail
    Created on : 15 Jan, 2019, 11:07:32 AM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform3" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="regCodeChoice" name="regCodeChoice" onchange="getSubjectCode();" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${stuRegList}" var="reList">
                            <option value="${reList[0]}"><c:out value="${reList[1]} -- ${reList[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="subCode" name="subCode" onchange="getDepartmentCode(), getSubjectCompCode();"data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Department Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="deptCode" name="deptCode" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subj Component:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6"  style="margin-top: -10px;">
                    <select class="form-control" id="subComp" name="subComp" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-top: -15px;">
                <div class="row col-lg-6 form-group" style="margin-left: 870px;">
                    <a href="javascript:loadDataCho(),getAcademicYearChoice(),getSectionForChoice();" class="btn btn-success fa fa-search"></a>
                </div>
            </div>    
        </div> 

        <div class="row col-lg-12 form-group" style="border: 1px solid; height: 180px; margin-left: 15px; margin-top: -20px;">
            <div class="row col-lg-12 form-group"  style="background: #cccccc; "><label style="text-transform: capitalize; color: black; margin-left: -40px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label"><b>Section Allocation</b></label></div>
            <div class="row col-lg-12 form-group" style="margin-top: 15px;" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                    </label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                        <select class="form-control" id="acadYearCho" name="acadYearCho" onchange="getProgramForChoice();" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                    </label>
                    <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: -10px;">
                        <select class="form-control" id="programCode" name="programCode" onchange="getBranchForChoice(), getStyTypeChoice(), getStyNumberChoice();" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch Code:
                    </label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                        <select class="form-control" id="branchCho" name="branchCho" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">STY Type:
                    </label>
                    <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: -10px;">
                        <select class="form-control" id="styType" name="styType" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
            </div>

            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">STY number:
                    </label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                        <select class="form-control" id="styNumber" name="styNumber" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group">
                    <div class="row col-lg-6 form-group" style="margin-left: 150px; margin-top: -14px;">
                        <a href="javascript:loadDataChoice();" class="btn btn-success fa fa-search"></a>
                    </div>
                </div> 
            </div>
        </div>

        <div class="row col-lg-12 form-group" style="border: 1px solid; height: 125px; margin-left: 15px; margin-top:-10px; ">
            <div class="row col-lg-12 form-group"  style="background: #cccccc; "><label style="text-transform: capitalize; color: black; margin-left: -40px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label"><b>Updation Criteria</b></label></div>
            <div class="row col-lg-12 form-group" style="margin-top: -5px;">
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;"  class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Running:
                    </label>
                    <div class="col-sm-9 col-lg-4 col-xs-12 col-md-6" style="margin-top: 8px;">
                        <input type="radio" id="subjectRunningYes" name="subjectRunning" checked="true" value="Y"/> Yes
                    </div>
                    <div class="col-sm-9 col-lg-4 col-xs-12 col-md-6" style="margin-top: 8px;">
                        <input type="radio" id="subjectRunningNo" name="subjectRunning" value="N"/> No
                    </div>
                </div> 
            </div>

            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -25px" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger" ></span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -15px">
                        <select class="form-control" id="sectionCodechoice" name="sectionCodechoice" onchange="getSubSectionChoice();" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -25px" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sub-Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -15px">
                        <select class="form-control" id="subSectionChoice" name="subSectionChoice" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
            </div>
        </div>
        <div class="col-lg-12 container">
            <table id="itemListChoice" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th width="1px;"></th>
                        <th width="1px;">Sl No</th>
                        <th width="10px;">Enrollment No.</th>
                        <th width="25px;">Student Name</th>
                        <th width="5px;">Acad Year</th>
                        <th width="10px;">Program</th>
                        <th width="10px;">Section</th>
                        <th width="10px;">SubSection</th>
                        <th width="5px;">Basket</th>
                        <th width="5px;">semester</th>
                        <th width="9px;">Sty Type</th>
                        <th width="9px;">Subject Running</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveDataStudentChoice();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="countval1choice" id="countval1choice" value=""/>
    <input type="hidden" name="checkedCountStudentChoice" id="checkedCountStudentChoice" value=""/>
</form>

<script>

    $("#regCodeChoice").chosen({width: "100%"});
    $("#subCode").chosen({width: "100%"});
    $("#deptCode").chosen({width: "100%"});
    $("#subComp").chosen({width: "100%"});
    $("#acadYearCho").chosen({width: "100%"});
    $("#programCode").chosen({width: "100%"});
    $("#branchCho").chosen({width: "100%"});
    $("#styType").chosen({width: "100%"});
    $("#styNumber").chosen({width: "100%"});
    $("#sectionCodechoice").chosen({width: "100%"});
    $("#subSectionChoice").chosen({width: "100%"});

    $('#itemListChoice').DataTable({
        searching: true, paging: true, info: true
    });


    function getSubjectCode() {
        if ($("#regCodeChoice").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSubjectCode",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subCode").empty();
                    if (data.subjectCode != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subjectCode, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[2] + ' --- ' + item[3] + '</option>'
                        });
                        $("#subCode").append(str1);
                        $("#subCode").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Subject Code Not Found For This Semester Code ,Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function getDepartmentCode() {
        if ($("#regCodeChoice").val() != '' && $("#subCode").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getDepartmentCode",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#deptCode").empty();
                    if (data.deptCode != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.deptCode, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + ' --- ' + item[2] + '</option>'
                        });
                        $("#deptCode").append(str1);
                        $("#deptCode").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Department Code Not Found For This Subject Code,Please Select Another Department Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Subject Code first!");
        }
    }

    function getSubjectCompCode() {
        if ($("#regCodeChoice").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSubjectCompCode",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "subjectId=" + $("#subCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subComp").empty();
                    if (data.subComp != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subComp, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[2] + '">' + item[2] + ' --- ' + item[3] + '</option>'
                        });
                        $("#subComp").append(str1);
                        $("#subComp").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Subjet Component Code Not Found For This Branch,Please Select Another Department Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Subject Component Code first!");
        }
    }

    function loadDataCho() {
        var regIdCho = $("#regCodeChoice").val();
        var subCodeCho = $("#subCode").val();
        var deptCodeCho = $("#deptCode").val();
        var subCompCho = $("#subComp").val();
        if (regIdCho != '' && subCodeCho != '' && deptCodeCho != '' && subCompCho != '') {
            $('#itemListChoice').DataTable().clear().draw();
            var itemChildList = $('#itemListChoice').DataTable();
            $.ajax({
                url: "newSubSectionAllocation/loadStudentChoiceData",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regIdCho=" + regIdCho + "&subCodeCho=" + subCodeCho + "&deptCodeCho=" + deptCodeCho + "&subCompCho=" + subCompCho,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    var count = new Array();
                    if (data.loadStudentChoiceData != null) {
                        var countt = 1;
                        $.each(data.loadStudentChoiceData, function (i, item)
                        {
                            var str1 = '';
                            var cou = 0;
                            str1 += ' <tr id="' + item[2] + '~@~' + item[3] + '~@~' + item[8] + '~@~' + item[11] + '">';
                            str1 += ' <td id="' + item[2] + '~@~' + item[3] + '~@~' + item[8] + '~@~' + item[11] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[2] + '~@~' + item[3] + '~@~' + item[8] + '~@~' + item[11] + '"/>';
                            str1 += ' </td>';
                            str1 += ' <td>' + countt + '</td>';
                            str1 += ' <td>' + item[4] + '</td>';
                            str1 += ' <td>' + item[5] + '</td>';
                            str1 += ' <td>' + item[7] + '</td>';
                            str1 += ' <td>' + item[6] + '</td>';
                            str1 += ' <td>' + item[9] + '</td>';
                            str1 += ' <td>' + item[10] + '</td>';
                            str1 += ' <td>' + item[12] + '</td>';
                            str1 += ' <td>' + item[8] + '</td>';
                            str1 += ' <td>' + item[13] + '</td>';
                            str1 += ' <td>' + item[11] + '</td>';
                            str1 += ' </tr>';
                            count.push(cou++);
                            countt++;
                            itemChildList.row.add($(str1)).draw();
                        });
                        $("#countval1choice").val(count);
                    } else {
                        BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                    }
                }
            });
        } else {
        }
    }

    function getSectionForChoice() {
        if ($("#regCodeChoice").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSectionForChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&deptId=" + $("#deptCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#sectionCodechoice").empty();
                    if (data.sectionChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.sectionChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#sectionCodechoice").append(str1);
                        $("#sectionCodechoice").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section Code Not Found For This filter, Please Select Another Filter...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Section Code first!");
        }
    }

    function getSubSectionChoice() {
        if ($("#regCodeChoice").val() != '' && $("#acadYearCho").val() != '' && $("#styNumber").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSubSectionChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&sectionId=" + $("#sectionCodechoice").val() + "&acadYear=" + $("#acadYearCho").val() + "&styNum=" + $("#styNumber").val() + "&SubCompId=" + $("#subComp").val() + "&departmentId=" + $("#deptCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subSectionChoice").empty();
                    if (data.subSectionChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subSectionChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#subSectionChoice").append(str1);
                        $("#subSectionChoice").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Sub-Section Code Not Found For This Section,Please Select Another Section Code Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic Year And Sty Number First..!");
        }
    }


    function getAcademicYearChoice() {
        if ($("#regCodeChoice").val() != '' && $("#subCode").val() != '' && $("#deptCode").val() != '' && $("#subComp").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getAcademicYearForChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&subCompId=" + $("#subComp").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#acadYearCho").empty();
                    if (data.acadYearChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.acadYearChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#acadYearCho").append(str1);
                        $("#acadYearCho").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert(" Academic Year Not Found For This Filter,Please Select Another Filters...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields first!");
        }
    }

    function getProgramForChoice() {
        if ($("#acadYearCho").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getProgramForChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&subCompId=" + $("#subComp").val() + "&acadYear=" + $("#acadYearCho").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#programCode").empty();
                    if (data.progChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.progChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#programCode").append(str1);
                        $("#programCode").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert(" Program Code Not Found For Academic Year,Please Select Another Academic Year...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic Year first!");
        }
    }

    function getBranchForChoice() {
        if ($("#programCode").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getBranchForChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&subCompId=" + $("#subComp").val() + "&acadYear=" + $("#acadYearCho").val() + "&programId=" + $("#programCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#branchCho").empty();
                    if (data.branchChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.branchChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#branchCho").append(str1);
                        $("#branchCho").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert(" Branch Code Not Found For Academic Year,Please Select Another Program Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Program Code first!");
        }
    }

    function getStyTypeChoice() {
        if ($("#programCode").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getStyTypeChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&acadYear=" + $("#acadYearCho").val() + "&programId=" + $("#programCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#styType").empty();
                    if (data.styTypeChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.styTypeChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#styType").append(str1);
                        $("#styType").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert(" Program Not Found For Sty Type,Please Select Another Program Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Sty Type first!");
        }
    }

    function getStyNumberChoice() {
        if ($("#programCode").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getStyNumberChoice",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeChoice").val() + "&subjectId=" + $("#subCode").val() + "&acadYear=" + $("#acadYearCho").val() + "&programId=" + $("#programCode").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#styNumber").empty();
                    if (data.styNumChoice != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.styNumChoice, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#styNumber").append(str1);
                        $("#styNumber").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert(" Program Not Found For Sty Type,Please Select Another Program Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Sty Type first!");
        }
    }

    function loadDataChoice() {
        var regIdCho = $("#regCodeChoice").val();
        var subCodeCho = $("#subCode").val();
        var deptCodeCho = $("#deptCode").val();
        var subCompCho = $("#subComp").val();
        var acadyear = $("#acadYearCho").val();
        var programId = $("#programCode").val();
        var branchId = $("#branchCho").val();
        var styNum = $("#styNumber").val();
        var styType = $("#styType").val();
        $('#itemListChoice').DataTable().clear().draw();
        var itemChildList = $('#itemListChoice').DataTable();
        $.ajax({
            url: "newSubSectionAllocation/loadStudentChoiceFilterData",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regIdCho=" + regIdCho + "&subCodeCho=" + subCodeCho + "&deptCodeCho=" + deptCodeCho + "&subCompCho=" + subCompCho + "&acadYear=" + acadyear + "&programId=" + programId + "&branchId=" + branchId + "&styNum=" + styNum + "&styType=" + styType,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var count = new Array();
                if (data.loadStudentChoiceFilterData != null) {
                    var countt = 1;
                    $.each(data.loadStudentChoiceFilterData, function (i, item)
                    {
                        var str1 = '';
                        var cou = 0;
                        str1 += ' <tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[7] + '">';
                        str1 += ' <td id="' + item[0] + '~@~' + item[1] + '~@~' + item[7] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[7] + '"/>';
                        str1 += ' </td>';
                        str1 += ' <td>' + countt + '</td>';
                        str1 += ' <td>' + item[2] + '</td>';
                        str1 += ' <td>' + item[3] + '</td>';
                        str1 += ' <td>' + item[4] + '</td>';
                        str1 += ' <td>' + item[5] + '</td>';
                        str1 += ' <td>' + item[10] + '</td>';
                        str1 += ' <td>' + item[6] + '</td>';
                        str1 += ' <td>' + item[11] + '</td>';
                        str1 += ' <td>' + item[7] + '</td>';
                        str1 += ' <td>' + item[8] + '</td>';
                        str1 += ' <td>' + item[9] + '</td>';
                        str1 += ' </tr>';
                        count.push(cou++);
                        countt++;
                        itemChildList.row.add($(str1)).draw();
                    });
                    $("#countval1choice").val(count);
                } else {
                    BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                }
            }
        });
    }

    function saveDataStudentChoice() {
        var countval1 = $("#countval1choice").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#chk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCountStudentChoice").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select at least one check box..");
        } else {
            setFormIdAndUrl('ajaxform3', 'newSubSectionAllocation/saveStudentChoice', 3);
        }
    }

</script>