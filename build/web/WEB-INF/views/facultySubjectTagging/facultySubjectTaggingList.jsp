<%-- 
    Document   : facultySubjectTaggingList
    Created on : Jan 22, 2019, 3:52:22 PM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
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
                <form id="ajaxform" class="form-horizontal">
                    <div class="row col-lg-12 form-group" id="divContainer">
                        <!--                        <div class="row col-lg-6 form-group" >
                                                    <label class="radio-inline">&nbsp;&nbsp; <input type="radio" name="fsttype" id="indv" checked="true" onclick="showHide();" value="I" style="display:inline-block"> &nbsp;Subject Wise Faculty Subject Tagging</label>  
                                                    <label class="radio-inline"> &nbsp;&nbsp; <input type="radio" name="fsttype" id="bulk"  onclick="showHide();" value="B" style="display:inline-block"> &nbsp;Bulk Faculty Subject Tagging</label>  
                                                </div>-->
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="reg_id" name="reg_id" data-live-search="true" data-validation="required"  onchange="getAcadYear();">               
                                        <option value="">Select</option>
                                        <c:forEach items="${reg_list}" var="reList">
                                            <option value="${reList[0]},${reList[1]}"><c:out value="${reList[1]}"/></option>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="acad_year" name="acad_year" data-live-search="true" data-validation="required" onchange="getSemesterCode();">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group" id="stynodiv"  style="display: none">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">STY Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="sem_id" name="sem_id" data-live-search="true" data-validation="required" onchange="getProgramCode();">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="progcodediv"  style="display: none">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="pr_id" name="pr_id" data-live-search="true" data-validation="required" onchange="getSectionCode();">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="departmentdiv">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Department Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="dep_id" name="dep_id" data-live-search="true" data-validation="required" onchange="getSubjectCode();">               
                                        <option value="">Select</option>
                                        <c:forEach items="${depart_list}" var="depart_list">
                                            <option value="${depart_list[0]}"><c:out value="${depart_list[1]} / ${depart_list[2]} "/></option>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="subcodediv" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="sub_id" name="sub_id" data-live-search="true" data-validation="required" onchange="getComponentCode()">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group" id="sectiondiv" style="display: none">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="sec_id" name="sec_id" data-live-search="true" data-validation="required" multiple="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="subjectcompdiv">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Component Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="subcom_id" name="subcom_id" data-live-search="true" data-validation="required">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="departmentdiv">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Stytype:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="stytype_id" name="stytype_id" data-live-search="true" data-validation="required">               
                                        <option value="">Select</option>
                                        <c:forEach items="${stytype_list}" var="stytype_list">
                                            <c:choose>
                                                <c:when test="${stytype_list[1] == 'REG'}">
                                                    <option value="${stytype_list[0]}" selected="true"><c:out value="${stytype_list[1]}  / ${stytype_list[2]}"/></option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${stytype_list[0]}"><c:out value="${stytype_list[1]}  / ${stytype_list[2]}"/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group"></div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize ;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <input type="button" class="btn btn-success btn-md" value="Load Data" onclick="getProgramgramSubjectTagging();">
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group" id="facultydiv" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Faculty Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger" id="important"></span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="faculty_id" name="faculty_id" data-live-search="true" data-validation="required">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12" >
                        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                        <!--<p><input type="checkbox" onclick="showHideCheckBox();" name="subjectFinalization" id="subjectFinalization" value="Y">Subject Finalization is done along with Faculty Subject Tagging</p>-->
                        <p style="color:red" id="hint"><b><u>Hint:</u></b> &nbsp; <br><span style="color:green;font-size:16px">Green coloured records are already saved </span></p>
                        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                            <thead id="header">
                                <tr class="" >
                                    <th ><input type="checkbox" id="chkAll" name="chkAll" onclick="checkAll();"/></th>
                                    <th >Sl.No</th>
                                    <th>Prg.Code</th>
                                    <th style="width: 80px;">Sec.Code</th>
                                    <th>Semester</th>
                                    <th>Basket Code</th>
                                    <th>SubSec.Code</th>
                                    <th id="subjcodeheading" style="display:none;">Subject Code</th>
                                    <th id="subjdescheading" style="display:none;">Subject</th>
                                    <th id="facultyheading" style="width:350px">Facutly</th>
                                    <th>Sub.Comp.Desc.</th>
                                </tr>
                            </thead>  
                        </table>
                    </div>
                    <div class="row col-lg-12" id="buttons" style="display:none">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a href="javascript:saveData();" class="btn btn-success btn-md ">Save</a>
                            <a href="javascript:closePage();" class="btn btn-danger btn-md">Cancel</a>
                        </div>
                    </div>
                    <input type="hidden" name="countval1" id="countval1" value=""/>
                    <input type="hidden" name="checkedCount" id="checkedCount" value=""/>
                    <input type="hidden" name="totalcount" id="totalcount" value=""/>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
//    var cb = {
//        add: {
//            link: "",
//            name: "",
//            value: ""
//        },
//        edit: {
//            link: "",
//            name: "",
//            value: ""
//        },
//        delete: {
//            link: "",
//            name: "",
//            value: ""
//        },
//        list: {
//            link: "facultySubjectTagging/list"
//        },
//        expression: {
//            link: "",
//            name: "",
//            value: ""
//        }
//
//    }
//
//    commonButtonMethod(cb);

    var btns = [];
    table1 = $('#datatable').DataTable({
        "lengthMenu": [[-1], ["All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table1, {
                    buttons: btns
                });
                $('#button_wrapper').append(table1.buttons(0, null).container());
            }, 100);


    function showHide() {
        var radioValue = $("input[name='fsttype']:checked").val();
        if (radioValue == 'I') {
            $('#datatable').DataTable().clear().draw();
            $("#subcodediv,#facultydiv,#subjectcompdiv,#departmentdiv,#facultyheading").show();
            $("#sectiondiv,#progcodediv,#stynodiv,#subjdescheading,#subjcodeheading").hide();
        } else {
            $('#datatable').DataTable().clear().draw();
            $("#subcodediv,#facultydiv,#subjectcompdiv,#departmentdiv,#facultyheading").hide();
            $("#sectiondiv,#progcodediv,#stynodiv,#subjdescheading,#subjcodeheading").show();
        }
    }
    function showButtons() {
        var flag = false;
        var count = $("#countval1").val().split(",");
        for (var i = 0; i < count.length; i++) {
            if ($('#chk' + i).is(':checked')) {
                flag = true;
            }
        }
        if (flag) {
            $("#buttons").show();
            $("#important").html("*");
        } else {
            $("#buttons").hide();
            $("#important").html("");
        }
    }
    function showHideCheckBox() {
        var count = $("#totalcount").val();
        if ($('#subjectFinalization').is(':checked')) {
            for (var j = 0; j < count; j++) {
                $('#chk' + j).prop("checked", true);
                $('#chk' + j).attr("style", "display:none");
            }
            showButtons();
        } else {
            for (var j = 0; j < count; j++) {
                $('#chk' + j).prop("checked", false);
                $('#chk' + j).attr("style", "display:show");
            }
            showButtons();
        }
        $('#chkAll').prop("checked", false);
    }
    function checkAll() {
        var count = $("#totalcount").val();
        if ($("#chkAll").is(":checked")) {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", true);
            }
            showButtons();
        } else {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", false);
            }
            showButtons();
        }
    }
    $("#reg_id").chosen({width: '100%'});
    $("#acad_year").chosen({width: '100%'});
    $("#pr_id").chosen({width: '100%'});
    $("#sec_id").chosen({width: '100%'});
    $("#sem_id").chosen({width: '100%'});
    $("#sub_id").chosen({width: '100%'});
    $("#faculty_id").chosen({width: '100%'});
    $("#subcom_id").chosen({width: '100%'});
    $("#dep_id").chosen({width: '100%'});
    $("#stytype_id").chosen({width: '100%'});

    function getProgramgramSubjectTagging() {
        var AcadYear = $("#acad_year").val();
        var reg_id = $("#reg_id").val();
        var pr_id = $("#pr_id").val();
        var sec_id = $("#sec_id").val();
        var sem_id = $("#sem_id").val();
        var dep_id = $("#dep_id").val();
        var sub_id = $("#sub_id").val();
        var subcom_id = $("#subcom_id").val();
        var faculty_id = $("#faculty_id").val();
        var stytype_id = $("#stytype_id").val();
        $('#datatable').DataTable().clear().draw();
        var itemChildList = $('#datatable').DataTable();
        var radioValue = $("input[name='fsttype']:checked").val();
        var flag = true;
        var data
        if (radioValue == "B") {
            if (AcadYear != "" && pr_id != "" && reg_id != "" && sec_id != "" && sem_id != "") {
            } else {
                BootstrapDialog.alert("Please Select Required Fields..");
                flag = false;
            }
        } else {
            if (AcadYear != "" && dep_id != "" && (sub_id != "" || sub_id != null) && subcom_id != "" && stytype_id != "") {
            } else {
                BootstrapDialog.alert("Please Select Required Fields..");
                flag = false;
            }
        }
        if (flag) {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "facultySubjectTagging/getFstList",
                data: '&regId=' + reg_id + '&acadYear=' + AcadYear + '&programId=' + pr_id + '&sec_id=' + sec_id + '&sty_num=' + sem_id + '&sub_id=' + sub_id + '&subcom_id=' + subcom_id + '&radioValue=' + radioValue + '&stytype_id=' + stytype_id + '&dep_id=' + dep_id,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.fst_list[0] == 'NotApprove') {
                        BootstrapDialog.alert("Load of this Depratment is not Approved!");
                    } else {
                        if (data.fst_list != null && data.fst_list != '') {
                            $.blockUI();
                            var count = new Array();
                            var j = 1;
                            var countt = 1;
                            var cou = 0;
                            var ct = 0;
                            debugger;
                            $.each(data.fst_list, function (i, item)
                            {
                                var fst_id = item[20];
                                var faculty = "";
                                if (fst_id === null || fst_id == null) {
                                    var str1 = '<tr>';
                                    faculty = "Not Tagged";
                                    str1 += '<td id="' + item[1] + '~@~' + item[2] + '~@~' + item[4] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[14] + '~@~' + item[19] + '~@~' + item[20] + '"><input type="checkbox" onclick="showButtons();" id="chk' + i + '"  name="chk' + i + '"  value="' + item[1] + '~@~' + item[2] + '~@~' + item[4] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[14] + '~@~' + item[19] + '~@~' + item[20] + '~@~' + item[21] + '~@~' + item[22] + '"/>';
                                } else {
                                    var str1 = '<tr style="color:green;font-weight:bold">';
                                    faculty = fst_id;
                                    str1 += '<td id="' + item[1] + '~@~' + item[2] + '~@~' + item[4] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[14] + '~@~' + item[19] + '~@~' + item[20] + '"><input type="checkbox" style="display:none"  id="chkd' + i + '"  named="chk' + i + '"  value="' + item[1] + '~@~' + item[2] + '~@~' + item[4] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[14] + '~@~' + item[19] + '~@~' + item[20] + '~@~' + item[21] + '~@~' + item[22] + '"/>';
                                }
                                str1 += '  </td>';
                                str1 += '  <td>' + j + '</td>';
                                str1 += '  <td>' + item[3] + '</td>';
                                str1 += '  <td>' + item[5] + '</td>';
                                str1 += '  <td>' + item[6] + '</td>';
                                str1 += '  <td>' + item[8] + '</td>';
                                str1 += '  <td>' + item[9] + '</td>'; // done
                                if (radioValue == "B") {
                                    str1 += '  <td>' + item[12] + '</td>';
                                    str1 += '  <td>' + item[13] + '</td>';
                                    str1 += '  <td style="display:none">' + faculty + '</td>';
                                } else {
                                    str1 += '  <td style="display:none">' + item[12] + '</td>';
                                    str1 += '  <td style="display:none">' + item[13] + '</td>';
                                    str1 += '  <td>' + faculty + '</td>';
                                }
                                str1 += '  <td>' + item[16] + '</td></tr>';
                                count.push(cou++);
                                countt++;
                                itemChildList.row.add($(str1)).draw();
                                j++;
                                ct++;
                            });
                            $("#countval1").val(count);
                            $("#totalcount").val(ct);
                        } else {
                            BootstrapDialog.alert("No Data Found..");
                        }
                    }
                    $.unblockUI();
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    $.unblockUI();
                }
            });
        }
    }

    function getSubjectCode() {
        var registrationid = $("#reg_id").val();
        var academicyear = $("#acad_year").val();
        var departmentid = $("#dep_id").val();
        $('#datatable').DataTable().clear().draw();
        if (registrationid !== "") {
            if (academicyear !== "") {
                $("#sub_id").empty().trigger("chosen:updated");
                $("#subcom_id").empty().trigger("chosen:updated");
                $("#faculty_id").empty().trigger("chosen:updated");
                $.blockUI();
                $.ajax({
                    type: "POST",
                    url: "facultySubjectTagging/getSubject_FacultyCode",
                    data: 'registrationid=' + registrationid + '&academicyear=' + academicyear + '&departmentid=' + departmentid,
                    datatype: 'json',
                    async: true,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        if (data.subjectCode.length > 0) {
                            var str1 = '';
                            var str1 = '<option value="">Select</option>';
                            $.each(data.subjectCode, function (i, item)
                            {
                                str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
                            });
                            $("#sub_id").append(str1).trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Subject not found for this department!");
                        }
                        if (data.facultyCode.length > 0) {
                            var str2 = '';
                            var str2 = '<option value="">Select</option>';
                            $.each(data.facultyCode, function (i, item)
                            {
                                str2 += '<option value="' + item[0] + '~@~' + item[3] + '">' + item[1] + ' / ' + item[2] + '</option>'
                            });
                            $("#faculty_id").append(str2).trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Faculty not found for this department!");
                        }
                        $.unblockUI();
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                        $.unblockUI();
                    }
                });
            } else {
                BootstrapDialog.alert("Please select Academic Year!");
                $("#dep_id").val('x').trigger("chosen:updated");
            }
        } else {
            BootstrapDialog.alert("Please select Registration Code!");
            $("#dep_id").val('x').trigger("chosen:updated");
        }
    }

    function getComponentCode() {
        var registrationid = $("#reg_id").val();
        var academicyear = $("#acad_year").val();
        var subjectid = $("#sub_id").val();
        $("#subcom_id").empty().trigger("chosen:updated");
        $('#datatable').DataTable().clear().draw();
        if (registrationid !== "") {
            if (academicyear !== "") {
                $.ajax({
                    type: "POST",
                    url: "facultySubjectTagging/getComponentCode",
                    data: 'registrationid=' + registrationid + '&academicyear=' + academicyear + '&subjectid=' + subjectid,
                    datatype: 'json',
                    async: true,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        if (data.componentCode.length > 0) {
                            var str1 = '';
                            var str1 = '<option value="">Select</option>';
                            $.each(data.componentCode, function (i, item)
                            {
                                str1 += '<option value="' + item[0] + '">' + item[2] + '</option>'
                            });
                            $("#subcom_id").append(str1).trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Subject Component found for this Subject!");
                        }
                        $.unblockUI();
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                        $.unblockUI();
                    }
                });
            } else {
                BootstrapDialog.alert("please select Academic Year!");
            }
        } else {
            BootstrapDialog.alert("please select Registration Code!");
        }

    }

    function saveData() {
        var countval1 = $("#countval1").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#chk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCount").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select atleast one check box");
        } else {
            if ($("#faculty_id").val() != "") {
                $.blockUI();
                $.ajax({
                    type: "POST",
                    url: "facultySubjectTagging/save",
                    data: $("#ajaxform").serialize(),
                    datatype: "String",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        if (data.savaGridData == 'Success') {
                            toastr.success(Success['save_success'], "Success!!");
                            getProgramgramSubjectTagging();
//                        loadForm("facultySubjectTagging/list");
                        } else if (data.savaGridData == 'Error') {
                            toastr.error(Error['error_code'], "Error!!");
                        } else {
                            BootstrapDialog.alert(data);
                        }
                        $.unblockUI();
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong.', "Warning!!");
                        $.unblockUI();
                    }
                });
            } else {
                BootstrapDialog.alert("Please select Faculty Code!");
            }
        }
    }

    function getAcadYear() {
        $("#pr_id").empty();
        $("#pr_id").val('a').trigger("chosen:updated");
        $("#sec_id").val('a').trigger("chosen:updated");
        $("#sem_id").val('a').trigger("chosen:updated");
        $("#acad_year").val('a').trigger("chosen:updated");
        $("#sec_id").empty();
        $("#sem_id").empty();
        $("#acad_year").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getAcadyear",
            data: '&regId=' + $("#reg_id").val(),
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
                $.each(data.acadyear, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[0] + '</option>'
                });
                $("#acad_year").append(str1);
                $("#acad_year").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
    function getProgramCode() {
        $("#pr_id").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getProgramCode",
            data: '&regId=' + $("#reg_id").val() + '&acadYear=' + $("#acad_year").val(),
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
                $.each(data.pr_code, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#pr_id").append(str1);
                $("#pr_id").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getSemesterCode() {
        $("#dep_id").val("x").trigger("chosen:updated");
        $("#sem_id").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getSemesterCode",
            data: '&regId=' + $("#reg_id").val() + '&acadYear=' + $("#acad_year").val() + '&programId=' + $("#pr_id").val(),
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
                $.each(data.sem_code, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#sem_id").append(str1);
                $("#sem_id").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
    function getSectionCode() {
        $("#sec_id").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getSectionCode",
            data: '&regId=' + $("#reg_id").val() + '&acadYear=' + $("#acad_year").val() + '&programId=' + $("#pr_id").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                $.each(data.br_code, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#sec_id").append(str1);
                $("#sec_id").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
</script>