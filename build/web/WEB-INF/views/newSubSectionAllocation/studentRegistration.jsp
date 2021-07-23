<%-- 
    Document   : studentRegistration
    Created on : 15 Jan, 2019, 11:07:12 AM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="regCodeReg" name="regCodeReg" data-validation="required" onchange="getAcademicYear();" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${stuRegList}" var="reList">
                            <option value="${reList[0]}"><c:out value="${reList[1]} -- ${reList[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="acadYearReg" name="acadYearReg" onchange="getStyNumber(), getBranchForStuReg();" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty No:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="styNumberReg" name="styNumberReg" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="branchReg" name="branchReg" onchange="getSectionForStuReg();" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="sectionReg" name="sectionReg" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <div class="row col-lg-2 form-group" style="margin-left: 100px;">
                    <a href="javascript:loadDataReg();" class="btn btn-success fa fa-search"></a>
                </div>
            </div>    
        </div> 


        <div class="row col-lg-12 form-group" style="border: 1px solid; height: 240px; margin-left: 15px; ">
            <div class="row col-lg-12 form-group"  style="background: #cccccc; "><label style="text-transform: capitalize; color: black; margin-left: 400px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label"><b>Updation Criteria</b></label></div>

            <div class="row col-lg-12 form-group" style="margin-top: -5px;">
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="margin-left: 130px;">
                    <input type="checkbox" id="updateSection" name="updateSection" value="Y" onclick="disable1(), getSectionListForReg();"/>&nbsp;&nbsp;&nbsp;&nbsp;<b>Update Section/Sub-Section</b>
                </div>
            </div>
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="margin-left: 130px;">
                        <input type="checkbox" id="updateStuReg" name="updateStuReg" value="Y" checked="true" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;Also Update Student Master
                    </div>
                </div>
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Reg Allow:
                    </label>
                    <div class="row col-lg-6 form-group">
                        <div class="col-sm-9 col-lg-5 col-xs-12 col-md-6">
                            <input type="radio" id="regAllow" name="regAllow" value="Y" checked="true" disabled="true"/> YES
                        </div>
                        <div class="col-sm-9 col-lg-5 col-xs-12 col-md-6">
                            <input type="radio" id="regAllow1" name="regAllow" value="N" disabled="true"/> NO
                        </div>
                    </div> 
                </div> 
            </div>

            <div class="row col-lg-12 form-group" style="margin-top: -30px;">
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                    </label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                        <select class="form-control" id="sectionCodeReg" name="sectionCodeReg" onchange="getSubSectionListForReg();" data-live-search="true" disabled="">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sub-Section:
                    </label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                        <select class="form-control" id="subSectionReg" name="subSectionReg" data-live-search="true" disabled="">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
            </div>

            <div class="row col-lg-12 form-group" >
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="margin-left: 130px; margin-top: -30px;">
                    <input type="checkbox" id="updatePREvent" name="updatePREvent" value="Y" onclick="disable();"/>&nbsp;&nbsp;&nbsp;&nbsp;<b>Update PR Event From Date / To Date</b>
                </div>
            </div>

            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">From Date:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control" id="fromdate" name="fromdate" data-validation="required" disabled="true"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">To Date:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <div class='input-group date' id='datetimepicker2'>
                            <input type='text' class="form-control" id="todate" name="todate" data-validation="required" disabled="true"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div> 
            </div>
        </div>


        <div class="col-lg-12 container">
            <table id="itemListReg" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th width="1px;"></th>
                        <th width="1px;">Sl No</th>
                        <th width="10px;">Enrollment</th>
                        <th width="25px;">Student Name</th>
                        <th width="5px;">Acad Year</th>
                        <th width="5px;">Branch</th>
                        <th width="5px;">Sem.</th>
                        <th width="14px;">Section</th>
                        <th width="5px;">SubSection</th>
                        <th width="5px;">Reg Allow</th>
                        <th width="5px;">Reg Conf</th>
                        <th width="9px;">Reg Conf Date</th>
                        <th width="10px;">Prevent Freezed</th>
                        <th width="10px;">PR Freeze Date</th>
                        <th width="10px;">PR Event Form Date</th>
                        <th width="10px;">PR Event End Date</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 

        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:getDataReg();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="countval1Reg" id="countval1Reg" value=""/>
    <input type="hidden" name="checkedCountStudentReg" id="checkedCountStudentReg" value=""/>
</form>

<script>
    $("#regCodeReg").chosen({width: "100%"});
    $("#acadYearReg").chosen({width: "100%"});
    $("#styNumberReg").chosen({width: "100%"});
    $("#branchReg").chosen({width: "100%"});
    $("#sectionReg").chosen({width: "100%"});
    $("#sectionCodeReg").chosen({width: "100%"});
    $("#subSectionReg").chosen({width: "100%"});

    $('#itemListReg').DataTable({
        searching: true, paging: true, info: true
    });

    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker2').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker1").on("dp.change", function (e) {
            $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker2").on("dp.change", function (e) {
            $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
        });
    });

    function disable() {
        if ($("input[name='updatePREvent']:checked").val() == 'Y') {
            $('#fromdate').attr("disabled", false);
            $('#todate').attr("disabled", false);
        } else {
            $('#fromdate').attr("disabled", "disabled");
            $('#todate').attr("disabled", "disabled");
        }
    }

    function disable1() {
        if ($("input[name='updateSection']:checked").val() == 'Y') {
            $('#updateStuReg').attr("disabled", false);
            $('#regAllow').attr("disabled", false);
            $('#regAllow1').attr("disabled", false);
            $('#sectionCodeReg').prop('disabled', false).trigger("chosen:updated");
            $('#subSectionReg').prop('disabled', false).trigger("chosen:updated");
        } else {
            $('#updateStuReg').attr("disabled", "disabled");
            $('#regAllow').attr("disabled", "disabled");
            $('#regAllow1').attr("disabled", "disabled");
            $('#sectionCodeReg').prop('disabled', true).trigger("chosen:updated");
            $('#subSectionReg').prop('disabled', true).trigger("chosen:updated");
        }
    }

    function getAcademicYear() {
        if ($("#regCodeReg").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getAcademicYear",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeReg").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#acadYearReg").empty();
                    $("#styNumberReg").empty();
                    if (data.acadYearRegList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.acadYearRegList, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#acadYearReg").append(str1);
                        $("#acadYearReg").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found For This Semester Code,Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function getStyNumber() {
        if ($("#regCodeReg").val() != '' && $("#acadYearReg").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getStyNumber",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeReg").val() + "&academicYear=" + $("#acadYearReg").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#styNumberReg").empty();
                    if (data.styNumberRegList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.styNumberRegList, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#styNumberReg").append(str1);
                        $("#styNumberReg").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found For This Semester Code,Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function getBranchForStuReg() {
        if ($("#regCodeReg").val() != '' && $("#acadYearReg").val() != '' && $("#acadYearReg").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getBranchForStuReg",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeReg").val() + "&academicYear=" + $("#acadYearReg").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#branchReg").empty();
                    if (data.branchRegList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.branchRegList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[2] + '">' + item[2] + ' ---' + item[3] + '</option>'
                        });
                        $("#branchReg").append(str1);
                        $("#branchReg").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found For This Semester Code,Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }
    function getSectionForStuReg() {
        if ($("#regCodeReg").val() != '' && $("#acadYearReg").val() != '' && $("#styNumberReg").val() != '' && $("#branchReg").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSectionForStuReg",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regId=" + $("#regCodeReg").val() + "&academicYear=" + $("#acadYearReg").val() + "&styNum=" + $("#styNumberReg").val() + "&branchId=" + $("#branchReg").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#sectionReg").empty();
                    if (data.secRegList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.secRegList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + ' --- ' + item[2] + '</option>'
                        });
                        $("#sectionReg").append(str1);
                        $("#sectionReg").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found For This Semester Code,Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function getSectionListForReg() {
        if ($("input[name='updateSection']:checked").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSectionListForReg",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "academicYear=" + $("#acadYearReg").val() + "&styNum=" + $("#styNumberReg").val() + "&branchId=" + $("#branchReg").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#sectionCodeReg").empty();
                    if (data.sectionRegList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.sectionRegList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#sectionCodeReg").append(str1);
                        $("#sectionCodeReg").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found For This Semester Code,Please Select Another Semester Code...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function getSubSectionListForReg() {
        alert("Hello");
        if ($("#sectionCodeReg").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSubSectionListForReg",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "academicYear=" + $("#acadYearReg").val() + "&styNum=" + $("#styNumberReg").val() + "&branchId=" + $("#branchReg").val() + "&sectionId=" + $("#sectionCodeReg").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subSectionReg").empty();
                    if (data.getSubSectionList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.getSubSectionList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#subSectionReg").append(str1);
                        $("#subSectionReg").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section  Not Found For This Filter Record ,Please Select Another ...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Section Code first!");
        }
    }

    function loadDataReg() {
        var regIdReg = $("#regCodeReg").val();
        var acadYearReg = $("#acadYearReg").val();
        var styNumberReg = $("#styNumberReg").val();
        var branchReg = $("#branchReg").val();
        var sectionReg = $("#sectionReg").val();
        $('#itemListReg').DataTable().clear().draw();
        var itemChildList = $('#itemListReg').DataTable();
        $.ajax({
            url: "newSubSectionAllocation/loadStudentRegData",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "acadYear=" + acadYearReg + "&styNum=" + styNumberReg + "&branchId=" + branchReg + "&sectionId=" + sectionReg + "&regId=" + regIdReg,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var count = new Array();
                if (data.loadStudentRegData != null) {
                    var countt = 1;
                    $.each(data.loadStudentRegData, function (i, item)
                    {
                        var str1 = '';
                        var cou = 0;
                        str1 += ' <tr id="' + item[2] + '">';
                        str1 += ' <td id="' + item[2] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[2] + '"/>';
                        str1 += ' </td>';
                        str1 += ' <td>' + countt + '</td>';
                        str1 += ' <td>' + item[0] + '</td>';
                        str1 += ' <td>' + item[1] + '</td>';
                        str1 += ' <td>' + item[3] + '</td>';
                        str1 += ' <td>' + item[4] + '</td>';
                        str1 += ' <td>' + item[5] + '</td>';
                        str1 += ' <td>' + item[6] + '</td>';
                        str1 += ' <td>' + item[7] + '</td>';
                        str1 += ' <td>' + item[8] + '</td>';
                        str1 += ' <td>' + item[9] + '</td>';
                        str1 += ' <td>' + item[10] + '</td>';
                        str1 += ' <td>' + item[11] + '</td>';
                        str1 += ' <td>' + item[12] + '</td>';
                        str1 += ' <td>' + item[13] + '</td>';
                        str1 += ' <td>' + item[14] + '</td>';
                        str1 += ' </tr>';
                        count.push(cou++);
                        countt++;
                        itemChildList.row.add($(str1)).draw();
                    });
                    $("#countval1Reg").val(count);
                } else {
                    BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                }
            }
        });
    }

    function getDataReg() {
        var countval1 = $("#countval1Reg").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#chk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCountStudentReg").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select at least one check box..");
        } else {
            setFormIdAndUrl('ajaxform2', 'newSubSectionAllocation/saveStudentReg', 2);
        }
    }

</script>