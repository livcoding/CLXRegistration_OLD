<%-- 
    Document   : studentMaster
    Created on : 15 Jan, 2019, 11:06:50 AM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform1" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="acadYearMas" name="acadYearMas"  onchange="getBranchAndStyNumber()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${acadList}" var="acaList">
                            <option value="${acaList.id.academicyear}"><c:out value="${acaList.id.academicyear}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty No:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="styNumberMas" name="styNumberMas" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="branchMas" name="branchMas" onchange="getSection()" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="margin-top: -10px;">
                    <select class="form-control" id="sectionMas" name="sectionMas" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
                <div class="row col-lg-2 form-group" style="margin-top: -10px;">
                    <a href="javascript:loadDataMas();" class="btn btn-success fa fa-search"></a>
                </div>
            </div> 
        </div>

        <div class="row col-lg-12 form-group" style="border: 1px solid; height: 150px; margin-left: 15px; margin-top:-25px; ">
            <div class="row col-lg-12 form-group"  style="background: #cccccc; "><label style="text-transform: capitalize; color: black; margin-left: 400px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label"><b>Updation Criteria</b></label></div>
            <div class="row col-lg-12 form-group" style="margin-top: -5px;">
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; " class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Active Status:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: 12px;">
                        <select class="form-control" id="activeStatusMas" name="activeStatusMas" data-validation="required" data-live-search="true">               
                            <option value="A" selected="true" >Active</option>
                            <option value="D">Deactive</option>
                            <option value="C">Program Complete</option>
                            <option value="S">Suspended</option>
                            <option value="X">Admission Cancel</option>
                            <option value="N">Not on Roll Call</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group" style="margin-top: 15px;">
                    <div class="col-sm-9 col-lg-4 col-xs-12 col-md-6">
                        <input type="radio" id="currentSem" name="semMas" value="N" onclick="getSubsectionForCombo2();"/> Current Sem:
                    </div>
                    <div class="col-sm-9 col-lg-4 col-xs-12 col-md-6">
                        <input type="radio" id="nextSem" name="semMas" value="Y" onclick="getSubsectionForCombo2();"/> Next Sem:
                    </div>
                </div> 
            </div>

            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;"  class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" >
                        <select class="form-control" id="sectionCodeMas" name="sectionCodeMas" onchange="getSubSectionMas();" data-live-search="true">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sub-Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                        <select class="form-control" id="subSectionMas" name="subSectionMas" data-live-search="true">               
                            <option value="">Select</option>

                        </select>
                    </div>
                </div> 
            </div>
        </div>

        <div class="col-lg-12 container">
            <table id="itemList" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th width="1px;"></th>
                        <th width="1px;">Sl No.</th>
                        <th width="15px;">Enrollment</th>
                        <th width="25px;">Student Name</th>
                        <th width="5px;">Acad Year</th>
                        <th width="5px;">Branch</th>
                        <th width="5px;">Semester</th>
                        <th width="10px;">Section</th>
                        <th width="10px;">SubSection</th>
                        <th width="5px;">Next Sem</th>
                        <th width="5px;">Next Section</th>
                        <th width="9px;">Next SubSection</th>
                        <th width="5px;">Status</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 

        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:getData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="countval1Mas" id="countval1Mas" value=""/>
    <input type="hidden" name="checkedCountStudentMas" id="checkedCountStudentMas" value=""/>
</form>

<script>
    $("#acadYearMas").chosen();
    $("#styNumberMas").chosen();
    $("#branchMas").chosen();
    $("#sectionMas").chosen();
    $("#activeStatusMas").chosen();
    $("#sectionCodeMas").chosen();
    $("#subSectionMas").chosen();

    $('#itemList').DataTable({
        searching: true, paging: true, info: true
    });

    function getBranchAndStyNumber() {
        if ($("#acadYearMas").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getBranchAndStyNumber",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYearMas=" + $("#acadYearMas").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#styNumberMas").empty();
                    $("#branchMas").empty();
                    if (data[0].stuNumberList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data[0].stuNumberList, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#styNumberMas").append(str1);
                        $("#styNumberMas").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("StyNumber Not Found For This Academic Year ,Please Select Another Academimc Year...");
                    }
                    if (data[0].branchList != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data[0].branchList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[2] + '">' + item[2] + ' --- ' + item[3] + '</option>'
                        });
                        $("#branchMas").append(str1);
                        $("#branchMas").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Branch Not Found For This Academic Year,Please Select Another Academic Year...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Academic Year first!");
        }
    }

    function getSection() {
        if ($("#branchMas").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSection",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYearMas").val() + "&styNum=" + $("#styNumberMas").val() + "&branchId=" + $("#branchMas").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#sectionMas").empty();
                    if (data.section != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.section, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#sectionMas").append(str1);
                        $("#sectionMas").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section not found for this semester, Please select another semester.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Branch First!");
        }
    }

    function getSubsectionForCombo2() {
        var nextSemValue = $("input[name='semMas']:checked").val();
        if (nextSemValue != '' && $("#acadYearMas").val() != '' && $("#styNumberMas").val() != '' && $("#sectionMas").val() != '' && $("#branchMas").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSubsectionForCombo2",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYearMas").val() + "&styNum=" + $("#styNumberMas").val() + "&branchId=" + $("#branchMas").val() + "&nextSemValue=" + nextSemValue,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#sectionCodeMas").empty();
                    if (data.SubsectionForCombo2 != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.SubsectionForCombo2, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#sectionCodeMas").append(str1);
                        $("#sectionCodeMas").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Section not found for this semester, Please select another semester.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields First!");
        }
    }

    function getSubSectionMas() {
        if ($("#currentSem").val() != '' && $("#nextSem").val() != '') {
            $.ajax({
                url: "newSubSectionAllocation/getSubSectionStudentMas",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "acadYear=" + $("#acadYearMas").val() + "&styNum=" + $("#styNumberMas").val() + "&branchId=" + $("#branchMas").val() + "&sectionId=" + $("#sectionCodeMas").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subSectionMas").empty();
                    if (data.subSection != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.subSection, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#subSectionMas").append(str1);
                        $("#subSectionMas").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("SubSection not found for this Section, Please select another Section.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Section First!");
        }
    }

    function loadDataMas() {
        var acadYearMas = $("#acadYearMas").val();
        var styNumberMas = $("#styNumberMas").val();
        var branchMas = $("#branchMas").val();
        var sectionMas = $("#sectionMas").val();
        $('#itemList').DataTable().clear().draw();
        var itemChildList = $('#itemList').DataTable();
        $.ajax({
            url: "newSubSectionAllocation/loadStudentMasterData",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "acadYear=" + acadYearMas + "&styNum=" + styNumberMas + "&branchId=" + branchMas + "&sectionId=" + sectionMas,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var count = new Array();
                if (data.loadStudentMasterList != null) {
                    var countt = 1;
                    $.each(data.loadStudentMasterList, function (i, item)
                    {
                        var str1 = '';
                        var cou = 0;
                        str1 += ' <tr id="' + item[3] + '">';
                        str1 += ' <td id="' + item[3] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[3] + '"/>';
                        str1 += ' </td>';
                        str1 += ' <td>' + countt + '</td>';
                        str1 += ' <td>' + item[1] + '</td>';
                        str1 += ' <td>' + item[2] + '</td>';
                        str1 += ' <td>' + item[4] + '</td>';
                        str1 += ' <td>' + item[5] + '</td>';
                        str1 += ' <td>' + item[6] + '</td>';
                        str1 += ' <td>' + item[8] + '</td>';
                        str1 += ' <td>' + item[9] + '</td>';
                        str1 += ' <td>' + item[7] + '</td>';
                        str1 += ' <td>' + item[10] + '</td>';
                        str1 += ' <td>' + item[11] + '</td>';
                        str1 += ' <td>' + item[12] + '</td>';
                        str1 += ' </tr>';
                        count.push(cou++);
                        countt++;
                        itemChildList.row.add($(str1)).draw();
                    });
                    $("#countval1Mas").val(count);
                } else {
                    BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                }
            }
        });
    }

    function getData() {
        var countval1 = $("#countval1Mas").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#chk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCountStudentMas").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select at least one check box..");
        } else {
            setFormIdAndUrl('ajaxform1', 'newSubSectionAllocation/saveStudentMas', 1);
        }
    }

</script>

