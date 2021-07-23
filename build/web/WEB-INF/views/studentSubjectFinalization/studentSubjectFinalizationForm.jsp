<%-- 
    Document   : studentSubjectFinalizationForm
    Created on : Jan 16, 2019, 10:15:02 AM
    Author     : ashutosh1.kumar
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
                    <div class="col-lg-12" style="margin-top:10px;">
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="regCode" name="regCode" onchange="getAcadmicYear(); clearTable();" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                        <c:forEach items="${data}" var="reList">
                                            <option value="${reList[0]}"><c:out value="${reList[1]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div> 
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="acadYear" name="acadYear" onchange="getProgram(); clearTable();" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                        </div>

                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform:capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="program" name="program" onchange="getBranch(); clearTable();" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6"   style="margin-top: -10px;">
                                    <select class="form-control" id="branch" name="branch" multiple="true" data-validation="required" onchange="getSection(); clearTable();" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                        </div>

                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform:capitalize; margin-top: -17px;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                    <select class="form-control" id="section" name="section" multiple="true" data-validation="required" onchange="clearTable();" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                            <div class="row col-lg-6 form-group">
                                <div class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label " style="text-align:right">
                                    <input type="checkbox" id="allocatesubwithcrlimit" name="allocatesubwithcrlimit" onclick="change();" checked="true" value="Y"/>  
                                </div>
                                <label style="text-transform: capitalize;text-align:left" class="col-sm-6 col-lg-8 col-xs-6 col-md-6 control-label">Allocate Subject With Credit Limit Check:</label>
                            </div> 
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-2 col-lg-offset-8" style="text-align:center" >
                                <a href="javascript:loadData();" class="btn btn-success fa fa-search"> Load Data</a>
                                <div class="row col-lg-1  form-group">
                                    <button type="button" class="btn  btn-info fa fa-question-circle" data-toggle="tooltip" onclick="hint();" style="padding:2px;font-size:25px;float:right;color:yellow" title="Hint.."/>
                                </div>
                            </div>
                        </div> 

                        <div class="row col-lg-12 form-group" style="border: 1px solid lightgray;margin-left: 15px;display:none" id="errorGrid">
                            <div class="row col-lg-12 form-group"  style="background: #cccccc;margin:auto;text-align: center"><label style="text-transform: capitalize; color: black;"><b>Student Wise Error List Detail</b></label></div>
                            <div class="row col-lg-12 form-group" style="margin:auto">
                                <div id="test" class="table-wrapper-scroll-y">
                                    <table id="datatable" class="table table-bordered table-striped">
                                        <thead id="header" style="background-color:#008080;color:white">
                                            <tr class=""  >             
                                                <th width="90px;" >Type</th>                             
                                                <th width="90px;" >Description</th>                             
                                                <th width="10px;" >No. Of Error</th>                             
                                            </tr>
                                        </thead> 
                                        <tbody id="errordetail">
                                        </tbody>
                                    </table>
                                </div>                            
                            </div>
                        </div>

                        <div class="row col-lg-12 form-group" style="margin-top:20px" >
                            <div class="row col-lg-6 form-group" style="border: 1px solid lightgray; height: 375px; margin-left: 15px; margin-top: -15px;">
                                <div class="row col-lg-12 form-group"  style="background: #cccccc;margin:auto;text-align: center"><label style="text-transform: capitalize; color: black;" ><b>Eligible Student List for Finalization</b></label></div>
                                <div class="row col-lg-12 form-group" style="margin:auto">
                                    <div id="test" class="table-wrapper-scroll-y">
                                        <table id="datatable" class="table table-bordered table-striped">
                                            <thead id="header" style="background-color:#008080;color:white">
                                                <tr class=""  >               
                                                    <th width="1px;"  style="position:sticky;top:0px;background-color:#008080">Sl.No.</th>               
                                                    <th width="29px;"  style="position:sticky;top:0px;background-color:#008080">Enrollment No.</th>                             
                                                    <th width="70px;"  style="position:sticky;top:0px;background-color:#008080">Student Name</th>                             
                                                </tr>
                                            </thead> 
                                            <tbody id="elegiblestu">
                                            </tbody>
                                        </table>
                                    </div>                            
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" style="border: 1px solid lightgray; height: 375px; margin-left: 15px; margin-top: -15px;">
                                <div class="row col-lg-12 form-group"  style="background: #cccccc;margin:auto;text-align: center"><label style="text-transform: capitalize; color: black; " ><b>Student Wise Error List</b></label></div>
                                <div class="row col-lg-12 form-group" style="margin:auto" >
                                    <div id="test" class="table-wrapper-scroll-y">
                                        <table id="datatable" class="table table-bordered table-striped">
                                            <thead id="header" style="background-color:#008080;color:white">
                                                <tr class=""  >               
                                                    <th width="1px;"  style="position:sticky;top:0px;background-color:#008080">Sl.No.</th>               
                                                    <th width="29px;"  style="position:sticky;top:0px;background-color:#008080">Enrollment No.</th>                             
                                                    <th width="70px;"  style="position:sticky;top:0px;background-color:#008080">Student Name</th>                             
                                                </tr>
                                            </thead>  
                                            <tbody id="notelegiblestu">
                                            </tbody>
                                        </table>
                                    </div>                            
                                </div>
                            </div>
                        </div>
                        <c:if test="${parametervalue=='Y'}">
                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize; margin-top: -17px;">Registration Confirmation:</label>
                                    <input type="checkbox" id="chkbox" checked="true" name="chkbox" onclick="change()" value="Y"/>   
                                </div> 
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Reg Confirmation Date:</label>
                                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                                        <div class='input-group date' id='datetimepicker1'>
                                            <input type='text' class="form-control" data-validation="required"  name="regConfirm_date" />
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div> 
                            </div>
                        </c:if>
                        <div class="col-lg-12 form-group"> 
                            <p style="color:green;font-weight:bold">Green Color Record Is Already Finalized</p>
                            <p>Black Color Record Is Not Finalized</p>
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                                <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" style="display:none;" id="regconfirmdate">

                        </div>
                        <input type="hidden" name="elegiblestucountval" id="elegiblestucountval" value=""/>
                        <input type="hidden" name="notelegiblestucountval" id="notelegiblestucountval" value=""/>

                        <!----------------------- Model Window Open --------------------->
                        <div id="myModal" class="modal fade" role="dialog">
                            <div class="modal-dialog" style="margin:0px">
                                <div class="modal-content" style="width:800px;border:2px solid lightgray">
                                    <div class="modal-header bg-primary" style="position:sticky;top:0px;z-index:1">
                                        <button type="button" class="close" data-dismiss="modal" style="color:white;opacity:1">&times;</button>
                                        <h4 class="modal-title"><b>Student Error Wise</b></h4>
                                    </div>
                                    <div class="modal-body" >
                                        <div class="col-lg-12" style="margin-top:10px;">
                                            <div class="form-group" > 
                                                <div id="test" class=" table-wrapper-scroll-y3">
                                                    <table id="studetail" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                                        <thead id="header" style="background-color:#008080;color:white">
                                                            <tr class=""  >
                                                                <th width="1px;"  style="position:sticky;top:0px;background-color:#008080">Sl No.</th>
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080">Enrollment No.</th>               
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080">Name</th>               
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080">Batch</th>               
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080" id="subcodedescomp">Subject Code / Desc. / Component Code</th>               
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080" class="minmaxlimit">Student Credit</th>               
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080" class="minmaxlimit">Min Credit</th>               
                                                                <th width="10px;"  style="position:sticky;top:0px;background-color:#008080" class="minmaxlimit">Max Credit</th>               
                                                            </tr>
                                                        </thead>  
                                                        <tbody id="studetsubwise">
                                                        </tbody>
                                                    </table>
                                                </div>                            
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--------------------Model window End-------------------------->
                        <!----------------------- Model Window Open --------------------->
                        <div id="myModal1" class="modal fade" role="dialog">
                            <div class="modal-dialog" style="margin:0px">
                                <div class="modal-content" style="width:800px;border:2px solid lightgray">
                                    <div class="modal-header bg-primary" style="position:sticky;top:0px;z-index:1">
                                        <button type="button" class="close" data-dismiss="modal" style="color:white;opacity:1">&times;</button>
                                        <h4 class="modal-title"><b>Load Distribution not provided Subject list</b></h4>
                                    </div>
                                    <div class="modal-body" >
                                        <div class="col-lg-12" style="margin-top:10px;">
                                            <div id="test" class=" table-wrapper-scroll-y3">
                                                <table id="studetail" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                                    <thead id="header" style="background-color:#008080;color:white">
                                                        <tr class=""  >
                                                            <th width="1px;">Sl No.</th>
                                                            <th width="10px;" >Academic Year</th>               
                                                            <th width="10px;" >Program Code</th>               
                                                            <th width="10px;" >Section Code</th>               
                                                            <th width="10px;" >SubSection Code</th>               
                                                            <th width="10px;" >Subject Code</th>               
                                                            <th width="10px;" >Subject Component Code</th>               
                                                        </tr>
                                                    </thead>  
                                                    <tbody id="loadDistnotdefine">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--------------------Model window End-------------------------->
                    </div> 
                </form>

            </div>
        </div>
    </div>
</div>
<script>

    $("#regCode").chosen({width: "100%"});
    $("#acadYear").chosen({width: "100%"});
    $("#program").chosen({width: "100%"});
    $("#branch").chosen({width: "100%"});
    $("#section").chosen({width: "100%"});
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });


    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
    function hint() {
        BootstrapDialog.show({
            title: 'Student Subject Finalization Hint',
            message: (Hint['student_subject_finalization']),
            buttons: [{
                    label: 'Ok',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
        });
    }
    function change() {
        if ($("#allocatesubwithcrlimit").is(":checked")) {
            $('#regconfirmdate').show();
        } else {
            $('#regconfirmdate').hide();
        }
    }
    function clearTable() {
        $("#errorGrid").hide();
        $('#studetsubwise').empty();
        $('#elegiblestu').empty();
        $('#notelegiblestu').empty();
        $('#errordetail').empty();
    }
    function getAcadmicYear() {
        $("#errorGrid").hide();
        $("#acadYear").empty();
        $("#acadYear").val('x').trigger("chosen:updated");
        $("#program").empty();
        $("#program").val('x').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('x').trigger("chosen:updated");
        $.ajax({
            url: "studentSubjectFinalization/getAcadyear",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regId=" + $("#regCode").val(),
            error: (function (response) {
                    alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.acadyear, function (i, item)
                {
                    str1 += '<option value="' + item + '">' + item + '</option>'
                });
                $("#acadYear").append(str1);
                $("#acadYear").trigger("chosen:updated");
            }
        });
    }

    function getProgram() {
        $("#errorGrid").hide();
        $("#program").empty();
        $("#program").val('x').trigger("chosen:updated");
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('x').trigger("chosen:updated");
        $.ajax({
            url: "studentSubjectFinalization/getProgram",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regId=" + $("#regCode").val() + "&acadYear=" + $("#acadYear").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.program, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                });
                $("#program").append(str1);
                $("#program").trigger("chosen:updated");
            }
        });
    }

    function getBranch() {
        $("#errorGrid").hide();
        $("#branch").empty();
        $("#branch").val('x').trigger("chosen:updated");
        $("#section").empty();
        $("#section").val('x').trigger("chosen:updated");
        $.ajax({
            url: "studentSubjectFinalization/getBranch",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "regId=" + $("#regCode").val() + "&acadYear=" + $("#acadYear").val() + "&programId=" + $("#program").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                $.each(data.branch, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                });
                $("#branch").append(str1);
                $("#branch").trigger("chosen:updated");
            }
        });
    }

    function getSection() {
        $("#errorGrid").hide();
        $("#section").empty();
        $("#section").val('x').trigger("chosen:updated");
        if ($("#regCode").val() != '') {
            if ($("#acadYear").val() != '') {
                if ($("#program").val() != '') {
                    if ($("#branch").val() != '') {
                        $.ajax({
                            url: "studentSubjectFinalization/getSection",
                            dataType: 'json',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            data: "regId=" + $("#regCode").val() + "&acadYear=" + $("#acadYear").val() + "&programId=" + $("#program").val() + "&branchId=" + $("#branch").val(),
                            error: (function (response) {
                                alert('Server Error' + response);
                            }),
                            success: function (data) {
                                if (data.section != null && data.section != '')
                                {
                                    var str1 = '';
                                    $.each(data.section, function (i, item)
                                    {
                                        str1 += '<option value="' + item[2] + '">' + item[3] + "(" + item[1] + ")" + '</option>'
                                    });
                                    $("#section").append(str1);
                                    $("#section").trigger("chosen:updated");
                                } else {
                                    BootstrapDialog.alert("No Section Found...Please Select Another Branch...");
                                }

                            }
                        });
                    } else {
                        BootstrapDialog.alert("Please Select Branch...!");
                    }
                } else {
                    BootstrapDialog.alert("Please Select Program...!");
                }
            } else {
                BootstrapDialog.alert("Please Select Academic year...!");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code...!");
        }
    }

    function loadData() {
        $('#elegiblestu').empty();
        $('#notelegiblestu').empty();
        $('#errordetail').empty();
        var flag;
        if ($('#allocatesubwithcrlimit').is(':checked')) {
            flag = $("#allocatesubwithcrlimit").val();
        } else {
            flag = 'N';
        }
        if ($("#regCode").val() != '') {
            if ($("#acadYear").val() != '') {
                if ($("#program").val() != '') {
                    if ($("#branch").val() != '') {
                        if ($("#section").val() != '') {
                            var sec = $("#section").val();
                            var selectSec = sec.length;
                            $.blockUI();
                            $.ajax({
                                url: "studentSubjectFinalization/getStudentSubjectFinalizationData",
                                dataType: "json",
                                async: true,
                                cache: false,
                                timeout: 500000,
                                contentType: false,
                                processData: false,
                                data: "regId=" + $("#regCode").val() + "&acadYear=" + $("#acadYear").val() + "&programId=" + $("#program").val() + "&sectionId=" + $("#section").val() + "&branchId=" + $("#branch").val() + "&allocatesubwithcrlimit=" + flag,
                                success: function (data) {
                                    var str = '';
                                    var str1 = '';
                                    var str2 = '';
                                    var count = 0;
                                    var count1 = 0;
                                    if (data.first != '' && data.first != null) {
                                        $.each(data.first, function (i, item)
                                        {
                                            if (item.alreadyexists == "0") {
                                                str += '<tr>';
                                            } else {
                                                str += '<tr style="color:green;font-weight:bold"> ';
                                            }
                                            str += '  <td><input type="hidden" class="case" checked="true" name="chk' + i + '" id="chk' + i + '" value="' + item.studentid + '"/>' + (i + 1) + '</td>';
                                            str += '  <td>' + item.enrollmentno + '</td>';
                                            str += '  <td>' + item.name + '</td>';
                                            str += '  </tr> ';
                                            count++;
                                        });
                                    } else {
                                        str = '<tr><td colspan="3" style="text-align:center">No Data Found</td></tr>';
                                    }
                                    $("#elegiblestu").append(str);
                                    $("#elegiblestucountval").val(count);
                                    if (data.second != '' && data.second != null) {
                                        $.each(data.second, function (i, item)
                                        {
                                            str1 += '<tr>';
                                            str1 += '  <td><input type="hidden" class="case" checked="true" name="chkerror' + i + '" id="chkerror' + i + '" value="' + item.studentid + '"/>' + (i + 1) + '</td>';
                                            str1 += '  <td>' + item.enrollmentno + '</td>';
                                            str1 += '  <td>' + item.name + '</td>';
                                            str1 += '  </tr> ';
                                            count1++
                                        });
                                    } else {
                                        str1 = '<tr><td colspan="3" style="text-align:center">No Data Found</td></tr>';
                                    }
                                    $("#notelegiblestu").append(str1);
                                    $("#notelegiblestucountval").val(count1);
                                    var flag = 0;
                                    $.each(data.third, function (i, item)
                                    {
                                        if (item.value > 0) {
                                            var popupdata = '';
                                            $.each(item.list, function (j, item1)
                                            {
                                                if (item.title == 'Load Distribution not provided') {
                                                    if (j > 0) {
                                                        popupdata += '@@' + item1[0] + '~@~' + item1[1] + '~@~' + item1[2] + '~@~' + item1[3] + '~@~' + item1[4] + '~@~' + item1[5];
                                                    } else {
                                                        popupdata += item1[0] + '~@~' + item1[1] + '~@~' + item1[2] + '~@~' + item1[3] + '~@~' + item1[4] + '~@~' + item1[5];
                                                    }
                                                } else if (item.title == 'Zero Cr Points') {
                                                    if (j > 0) {
                                                        popupdata += '@@' + item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.zeroccpsubject + '~@~' + item1.sectionsubsection;
                                                    } else {
                                                        popupdata += item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.zeroccpsubject + '~@~' + item1.sectionsubsection;
                                                    }
                                                } else if (item.title == 'Credit Limit not Entered') {
                                                    if (j > 0) {
                                                        popupdata += '@@' + item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.nullccpsubject + '~@~' + item1.sectionsubsection;
                                                    } else {
                                                        popupdata += item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.nullccpsubject + '~@~' + item1.sectionsubsection;
                                                    }
                                                } else if (item.title == 'Min Max Credit Limit') {
                                                    if (j > 0) {
                                                        popupdata += '@@' + item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.studcredit + '@' + item1.minlimit + '@' + item1.maxlimit + '~@~' + item1.sectionsubsection;
                                                    } else {
                                                        popupdata += item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.studcredit + '@' + item1.minlimit + '@' + item1.maxlimit + '~@~' + item1.sectionsubsection;
                                                    }
                                                } else if (item.title == 'Subject Not Found in PST') {
                                                    if (j > 0) {
                                                        popupdata += '@@' + item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.nopstsubject + '~@~' + item1.sectionsubsection;
                                                    } else {
                                                        popupdata += item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.nopstsubject + '~@~' + item1.sectionsubsection;
                                                    }
                                                } else {
                                                    if (j > 0) {
                                                        popupdata += '@@' + item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.nofstsubject + '~@~' + item1.sectionsubsection;
                                                    } else {
                                                        popupdata += item1.studentid + '~@~' + item1.enrollmentno + '~@~' + item1.name + '~@~' + item1.nofstsubject + '~@~' + item1.sectionsubsection;
                                                    }
                                                }
                                            });
                                            str2 += '<tr>';
                                            if (item.title == 'Load Distribution not provided') {
                                                str2 += '  <td style="color:#afaf0b;">Warning</td>';
                                            } else {
                                                str2 += '  <td  style="color:red">Error</td>';
                                            }
                                            if (item.title == 'Load Distribution not provided') {
                                                str2 += '  <td style="color:#afaf0b;"> ' + item.title + '</td>';
                                            } else {
                                                str2 += '  <td>' + item.title + '</td>';
                                            }
                                            if (item.title == 'Load Distribution not provided') {
                                                if (item.value > 0) {
                                                    str2 += ' <td> <button type="button" class="btn btn-info" data-toggle="modal" onclick=javascript:getStudentErrorDetail("' + encodeURI(popupdata) + '","Teaching"); data-target="#myModal1">' + item.value + '</button></td>';
                                                    flag = 1;
                                                } else {
                                                    str2 += ' <td> <button type="button" class="btn btn-info" data-toggle="modal" >' + item.value + '</button></td>';
                                                }
                                            } else {
                                                if (item.value > 0) {
                                                    str2 += ' <td> <button type="button" class="btn btn-info" data-toggle="modal" onclick=javascript:getStudentErrorDetail("' + encodeURI(popupdata) + '","Other"); data-target="#myModal">' + item.value + '</button></td>';
                                                    flag = 1;
                                                } else {
                                                    str2 += ' <td> <button type="button" class="btn btn-info" data-toggle="modal" >' + item.value + '</button></td>';
                                                }
                                            }
                                            str2 += '  </tr> ';
                                        }
                                    });
                                    if (flag == 1) {
                                        $("#errordetail").append(str2);
                                        $("#errorGrid").show();
                                    } else {
                                        $("#errorGrid").hide();
                                    }
                                    setTimeout($.unblockUI, 1000);
                                },
                                error: function (response) {
                                    $.unblockUI();
                                    toastr.warning('Something Wrong...............', "Warning!!");
                                }
                            });
                        } else {
                            BootstrapDialog.alert("Please Select Section...!");
                        }
                    } else {
                        BootstrapDialog.alert("Please Select Branch...!");
                    }
                } else {
                    BootstrapDialog.alert("Please Select Program...!");
                }
            } else {
                BootstrapDialog.alert("Please Select Academic Year...!");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code...!");
        }
    }


    function getStudentErrorDetail(id, type) {
        var data = decodeURI(id);
        var ids = data.split("@@");
        var str = '';
        var len = ids.length;
        if (type == 'Teaching') {
            var data = decodeURI(id);
            if (data != '') {
                $('#loadDistnotdefine').empty();
                var ids = data.split("@@");
                var str = '';
                for (var i = 0; i < ids.length; i++) {
                    var loadDis = ids[i].split("~@~");
                    str += '<tr>';
                    str += '  <td>' + (i + 1) + '</td>';
                    str += '  <td>' + loadDis[0] + '</td>';
                    str += '  <td>' + loadDis[1] + '</td>';
                    str += '  <td>' + loadDis[2] + '</td>';
                    str += '  <td>' + loadDis[3] + '</td>';
                    str += '  <td>' + loadDis[4] + '</td>';
                    str += '  <td>' + loadDis[5] + '</td>';
                    str += '  </tr> ';
                }
                $("#loadDistnotdefine").append(str);
            }
        } else {
            $('#studetsubwise').empty();
            var data = decodeURI(id);
            if (data != '') {
                var ids = data.split("@@");
                var str = '';
                for (var i = 0; i < ids.length; i++) {
                    var studata = ids[i].split("~@~");
                    str += '<tr>';
                    str += '  <td>' + (i + 1) + '</td>';
                    str += '  <td>' + studata[1] + '</td>';
                    str += '  <td>' + studata[2] + '</td>';
                    str += '  <td>' + studata[4] + '</td>';
                    if (typeof studata[3] != 'undefined' && studata[3] != 'undefined') {
                        var v = studata[3].split('@');
                        if (v.length > 1) {
                            str += '  <td>' + v[0] + '</td>';
                            str += '  <td>' + v[1] + '</td>';
                            str += '  <td>' + v[2] + '</td>';
                            $(".minmaxlimit").show();
                            $("#subcodedescomp").hide();
                        } else {
                            str += '  <td>' + studata[3] + '</td>';
                            $("#subcodedescomp").show();
                            $(".minmaxlimit").hide();
                        }
                    } else {
                        $("#subcodedescomp").hide();
                        $(".minmaxlimit").hide();
                    }
                    str += '  </tr> ';
                }
            }
            $("#studetsubwise").append(str);
        }
    }
    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "studentSubjectFinalization/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 0,
                                    cache: false, beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Saved', "Success!!");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 2000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                        setTimeout($.unblockUI, 2000);
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        });
            }, 100);
    function saveData() {
        debugger;
        if ($("#regCode").val() != '' && $("#acadYear").val() != '' && $("#program").val() != '' && $("#branch").val() != '' && $("#section").val() != '') {
            var countval1 = $("#notelegiblestucountval").val();
            //Comment for Skip Error List Students
//            if (countval1 == 0) {
//                $("#ajaxform").submit();
//            } else {
//                BootstrapDialog.alert("Student(s) Found in error List");
//            }
            var count = $("#elegiblestucountval").val();
            if (count > 0) {
                $("#ajaxform").submit();
            } else {
                BootstrapDialog.alert("No Eligible Student's Found For Finalization...!");
            }
        } else {
            BootstrapDialog.alert("Please Select Required Fields... !");
        }
    }

</script>