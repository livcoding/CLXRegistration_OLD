<%-- 
    Document   : bulkStudentSubjectChoice
    Created on : 27 Dec, 2018, 11:19:23 AM
    Author     : Nazar.Mohammad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">

    <style >
        .table-wrapper-scroll-y { height: 250px;  overflow: scroll;  width: 650px; }
        .table-wrapper-scroll-y2 { height: 200px;  overflow: scroll;  width: 380px; }
        .table-wrapper-scroll-y3 { height: 200px;  overflow: scroll;  width: 630px; }
        @media screen and (min-width: 992px) {
            .modal-lg {
                width: 700px; /* New width for large modal */
            }
        }
    </style> 
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
                    <div class="row col-lg-12 form-group" id="divContainer" >
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                    <select class="form-control" id="semCode" name="semCode" data-validation="required" data-live-search="true" onchange="getAcadYear()">               
                                        <option value="">Select</option>
                                        <c:forEach items="${semester}" var="regisList">
                                            <option value="${regisList[0]}~@~${regisList[1]}"><c:out value="${regisList[1]} -- ${regisList[2]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <select class="form-control" id="acad_year" name="acad_year" data-live-search="true" onchange="getProgramCode()">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <select class="form-control" id="program_code" name="program_code" data-validation="required" data-live-search="true" onchange="getBranchCode()">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <select class="form-control" id="branch_code" name="branch_code" onchange="clearstysection()" data-validation="required" multiple="true" data-live-search="true">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                        </div>
                        <div class="row col-lg-12 form-group">                          
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <select class="form-control" id="stynumber" name="stynumber" data-live-search="true" onchange="getSectionCode();" multiple="true" data-validation="required">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" >  
                            </div> 
                        </div>
                        <div class="row col-lg-12 form-group" >  
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <select class="form-control" id="section" name="section"  onchange="electiveSubjectData(0); coreSubjectData(0);" data-validation="required" multiple="true" data-live-search="true">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-3 form-group" > 
                                <a onclick="coreSubjectData(1)" class="btn btn-info">Core Subject</a>
                            </div>
                            <div class="row col-lg-3 form-group" > 
                                <a onclick="electiveSubjectData(1)" class="btn btn-info">Elective Subject</a> 
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-2 form-group" style="float:right">
                                <a onclick="getData()" class="btn btn-success btn-sm btn-flat">Load Data</a>                
                            </div>
                            <div class="row col-lg-1  form-group" style="float:right;margin-right:8px">
                                <button type="button" class="btn  btn-info fa fa-question-circle" data-toggle="tooltip" onclick="hint();" style="padding:2px;font-size:25px;float:right;color:yellow" title="Hint.."/>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="row col-lg-5 form-group" > 
                                <div id="test" class="table-wrapper-scroll-y2" style="border:1px solid lightgray;">
                                    <table class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                        <thead id="header">
                                            <tr class="" style="background-color:#008080;color:white">
                                                <th width="5px;" style="position:sticky;top:0px;background-color:#008080">Sl. No.</th>
                                                <th width="10px;" style="position:sticky;top:0px;background-color:#008080">Enrollment Number</th>               
                                                <th width="30px;" style="position:sticky;top:0px;background-color:#008080">Student Name</th>             
                                            </tr>
                                        </thead>  
                                        <tfoot>
                                            <tr id="filterrow" class="no-print">                                                           
                                            </tr>
                                        </tfoot>
                                        <tbody id="studentlist">
                                        </tbody>
                                    </table>                                                          
                                </div>                       
                            </div>

                            <div class="row col-lg-7 form-group" > 
                                <div id="test" class="table-wrapper-scroll-y3" style="border:1px solid lightgray;">
                                    <table  class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                        <thead id="header">
                                            <tr class="" style="background-color:#008080;color:white" >                                               
                                                <th width="1px;" style="position:sticky;top:0px;background-color:#008080" >Sl. No.</th>
                                                <th width="10px;" style="position:sticky;top:0px;background-color:#008080">Enrollment Number</th>               
                                                <th width="20px;" style="position:sticky;top:0px;background-color:#008080">Student Name</th>               
                                                <th width="30px;"style="position:sticky;top:0px;background-color:#008080" >Error detail (if any)</th>              
                                            </tr>
                                        </thead>  
                                        <tfoot>
                                            <tr id="filterrow" class="no-print">                                                           
                                            </tr>
                                        </tfoot>
                                        <tbody id="errorList">
                                        </tbody>
                                    </table>                                                          
                                </div>                       
                            </div>
                        </div>
                        <div class="row col-lg-12">
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                <a onclick="save()" class="btn btn-success btn-sm btn-flat">Save</a>                
                            </div>
                        </div>

                        <!----------------------- Model Window Open --------------------->   
                        <div id="myModal" class="modal fade" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content" >
                                    <div class="modal-header" style="background-color:black;color:white">
                                        <button type="button" class="close" data-dismiss="modal" style="color:white;opacity:1">&times;</button>
                                        <h4 class="modal-title"><b>Core / Supplimentory Subject selection</b></h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container-fluid" >
                                            <div class="row col-lg-12 form-group" >
                                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" id="regCode" name="regCode" class="form-control has-help-txt" readonly="true"/>
                                                </div>
                                            </div>
                                            <div class="row col-lg-12 form-group">
                                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" id="acadYear" name="acadYear" class="form-control has-help-txt" readonly="true"/>

                                                </div>
                                            </div> 
                                            <div class="row col-lg-12 form-group">
                                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" id="progCode" name="progCode" class="form-control has-help-txt" readonly="true"/>
                                                </div> 
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="row col-lg-12 form-group" > 
                                                    <div id="test" class="table-wrapper-scroll-y">
                                                        <table  class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                                            <thead id="header">
                                                                <tr class="" style="background-color:#008080;color:white" >
                                                                    <th width="1px;"></th>
                                                                    <th width="5px;">Sl. No.</th>
                                                                    <th width="10px;" >Section/Branch</th>               
                                                                    <th width="10px;" >STY No.</th>               
                                                                    <th width="10px;" >Basket</th>               
                                                                    <th width="15px;" >Subject Type</th>               
                                                                    <th width="15px;" >Subject Code</th>               
                                                                    <th width="20px;" >Subject Desc.</th>               
                                                                    <th width="10px;" >Credit</th>              
                                                                </tr>
                                                            </thead> 
                                                            <tbody id="coreSubject">
                                                            </tbody>
                                                        </table>                                                          
                                                    </div>                       
                                                </div>
                                            </div> 

                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div>
                            </div>
                        </div>   
                        <!--///////////////////Model window End///////////////////-->
                        <input type="hidden"   name="countval" id="countval" value=""/>
                        <input type="hidden"   name="countval1" id="countval1" value=""/>
                        <input type="hidden"   name="countval2" id="countval2" value=""/>
                        <input type="hidden"   name="registrationid" id="registrationid" value=""/>
                        <input type="hidden"   name="registrationcode" id="registrationcode" value=""/>
                        <input type="hidden"   name="programid" id="programid" value=""/>
                        <input type="hidden"   name="programcode" id="programcode" value=""/>
                        <!----------------------- Model Window Open --------------------->   
                        <div id="myModal1" class="modal fade" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header" style="background-color:black;color:white">
                                        <button type="button" class="close" data-dismiss="modal" style="color:white;opacity:1">&times;</button>
                                        <h4 class="modal-title"><b>Elective Subject selection</b></h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container-fluid" >
                                            <div class="row col-lg-12 form-group" >
                                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" id="regCode1" name="regCode1" class="form-control has-help-txt" readonly="true"/>
                                                </div>
                                            </div>
                                            <div class="row col-lg-12 form-group">
                                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" id="acadYear1" name="acadYear1" class="form-control has-help-txt" readonly="true"/>

                                                </div>   
                                            </div>
                                            <div class="row col-lg-12 form-group">
                                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
                                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" id="progCode1" name="progCode1" class="form-control has-help-txt" readonly="true"/>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="row col-lg-12 form-group" > 
                                                    <div id="test1" class="table-wrapper-scroll-y1">
                                                        <table  class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                                            <thead id="header">
                                                                <tr class="" style="background-color:#008080;color:white" >
                                                                    <th width="1px;"></th>
                                                                    <th width="5px;">Sl. No.</th>
                                                                    <th width="10px;" >Section/Branch</th>               
                                                                    <th width="10px;" >STY No.</th>               
                                                                    <th width="10px;" >Basket</th>              
                                                                    <th width="10px;" >Subject Type</th>              
                                                                    <th width="15px;" >Subject Code</th>               
                                                                    <th width="20px;" >Subject Desc.</th>               
                                                                    <th width="10px;" >Credit</th>              
                                                                </tr>
                                                            </thead>  
                                                            <tbody id="electiveSubject">
                                                            </tbody>
                                                        </table>                                                          
                                                    </div>                       
                                                </div>
                                            </div> 

                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div>
                            </div>
                        </div>   
                        <!--///////////////////Model window End///////////////////-->
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>


    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
    function hint() {
        BootstrapDialog.show({
            title: 'Bulk Student Subject Choices Hint',
            message: (Hint['bulk_student_sub_choice']),
            buttons: [{
                    label: 'Ok',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
        });
    }



    $("#acad_year").chosen();
    $("#program_code").chosen();
    $("#branch_code").chosen();
    $("#stynumber").chosen();
    $("#semCode").chosen();
    $("#section").chosen();
    function getAcadYear() {
        $("#acad_year").empty();
        var regid = $("#semCode").val().split("~@~");
        $("#registrationid").val(regid[0]);
        $("#registrationcode").val(regid[1]);
        var regid =
                $.ajax({
                    type: "POST",
                    url: "bulkStudentSubjectChoice/getQueryCriteriaData",
                    data: '&semCode=' + $("#registrationid").val(),
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
                        $.each(data.acadYearList, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#acad_year").append(str1);
                        $("#acad_year").trigger("chosen:updated");
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
    }

    function getProgramCode() {
        $("#program_code").empty();
        $.ajax({
            type: "POST",
            url: "bulkStudentSubjectChoice/getProgram",
            data: '&semCode=' + $("#registrationid").val() + '&acad_year=' + $("#acad_year").val(),
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
                $.each(data.programList, function (i, item)
                {
                    str1 += '<option value="' + item[1] + "~@~" + item[0] + '">' + item[0] + '</option>'
                });
                $("#program_code").append(str1);
                $("#program_code").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function getBranchCode() {
        $("#branch_code").empty();
        $("#stynumber").empty();
        var progid = $("#program_code").val().split("~@~");
        $("#programid").val(progid[0]);
        $("#programcode").val(progid[1]);
        $.ajax({
            type: "POST",
            url: "bulkStudentSubjectChoice/getBranch",
            data: '&semCode=' + $("#registrationid").val() + '&acad_year=' + $("#acad_year").val() + '&program_code=' + $("#programid").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str2 = '';
                $.each(data.branchList, function (i, item)
                {
                    str1 += '<option value="' + item[1] + '">' + item[0] + '</option>'
                });
                $.each(data.styList, function (i, item)
                {
                    str2 += '<option value="' + item + '">' + item + '</option>'
                });
                $("#branch_code").append(str1);
                $("#branch_code").trigger("chosen:updated");
                $("#stynumber").append(str2);
                $("#stynumber").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function getSectionCode() {
        if ($("#acad_year").val() != '' && $("#program_code").val() != '' && $("#branch_code").val() != '' && $("#stynumber").val() != '' && $("#semCode").val() != '' && $("#stynumber").val() != '')
        {
            $("#section").empty();
            $.ajax({
                type: "POST",
                url: "bulkStudentSubjectChoice/getSection",
                data: '&semCode=' + $("#registrationid").val() + '&acad_year=' + $("#acad_year").val() + '&program_code=' + $("#programid").val() + '&branch_code=' + $("#branch_code").val() + '&stynumber=' + $("#stynumber").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str = '';
                    $.each(data.sectionList, function (i, item)
                    {
                        str += '<option value="' + item[1] + '">' + item[0] + '</option>'
                    });
                    $("#section").append(str);
                    $("#section").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }
    }
    function coreSubjectData(i) {
        if ($("#acad_year").val() != '' && $("#program_code").val() != '' && $("#branch_code").val() != '' && $("#stynumber").val() != '' && $("#semCode").val() != '' && $("#section").val() != '') {
            $("#regCode").val($("#registrationcode").val());
            $("#progCode").val($("#programcode").val());
            $("#acadYear").val($("#acad_year").val());
            if (i == 1) {
                $("#myModal").modal();
            }
            $("#coreSubject").empty();
            var subjectType = 'C';
            $.ajax({
                type: "POST",
                url: "bulkStudentSubjectChoice/getCoreSubject",
                data: '&semCode=' + $("#registrationid").val() + '&acad_year=' + $("#acad_year").val() + '&program_code=' + $("#programid").val() + '&branch_code=' + $("#branch_code").val() + '&stynumber=' + $("#stynumber").val() + '&section=' + $("#section").val() + '&subjectType=' + subjectType,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str = '';
                    var count = new Array();
                    var cou = 0;
                    var cou1 = 1;
                    $.each(data.coreSubjectList, function (i, item1)
                    {
                        str += '<tr>';
                        str += ' <td><input type="checkbox" checked="true" class="" name="chk1' + i + '" id="chk1' + i + '" value="' + item1[0] + "~@~" + item1[1] + "~@~" + item1[3] + "~@~" + item1[5] + "~@~" + item1[6] + "~@~" + item1[12] + "~@~" + item1[7] + "~@~" + item1[13] + "~@~" + item1[14] + '"/>';
                        str += '  </td>';
                        str += '  <td>' + cou1++ + '</td>';
                        str += '  <td>' + item1[9] + '</td>';
                        str += '  <td>' + item1[6] + '</td>';
                        str += '  <td>' + item1[8] + '</td>';
                        str += '  <td>' + item1[12] + '</td>';
                        str += '  <td>' + item1[10] + '</td>';
                        str += '  <td>' + item1[11] + '</td>';
                        str += '  <td>' + item1[7] + '</td>';
                        str += '  </tr> ';
                        cou++;
                    });
                    $("#coreSubject").append(str);
                    $("#countval").val(cou);
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }
    }

    function electiveSubjectData(i) {
        if ($("#acad_year").val() != '' && $("#program_code").val() != '' && $("#branch_code").val() != '' && $("#stynumber").val() != '' && $("#semCode").val() != '' && $("#section").val() != '') {
            $("#regCode1").val($("#registrationcode").val());
            $("#progCode1").val($("#programcode").val());
            $("#acadYear1").val($("#acad_year").val());
            if (i == 1) {
                $("#myModal1").modal();
            }
            $("#electiveSubject").empty();
            var subjectType = 'E';
            $.ajax({
                type: "POST",
                url: "bulkStudentSubjectChoice/electiveSubjectData",
                data: '&semCode=' + $("#registrationid").val() + '&acad_year=' + $("#acad_year").val() + '&program_code=' + $("#programid").val() + '&branch_code=' + $("#branch_code").val() + '&stynumber=' + $("#stynumber").val() + '&section=' + $("#section").val() + '&subjectType=' + subjectType,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str = '';
                    var count = new Array();
                    var cou = 0;
                    var cou1 = 1;
                    $.each(data.electiveSubjectList, function (i, item1)
                    {
                        str += '<tr>';
                        str += ' <td><input type="checkbox"  class="" name="chk2' + i + '" id="chk2' + i + '" value="' + item1[0] + "~@~" + item1[1] + "~@~" + item1[3] + "~@~" + item1[5] + "~@~" + item1[6] + "~@~" + item1[12] + "~@~" + item1[7] + "~@~" + item1[13] + "~@~" + item1[14] + '"/>';
                        str += '  </td>';
                        str += '  <td>' + cou1++ + '</td>';
                        str += '  <td>' + item1[9] + '</td>';
                        str += '  <td>' + item1[6] + '</td>';
                        str += '  <td>' + item1[8] + '</td>';
                        str += '  <td>' + item1[12] + '</td>';
                        str += '  <td>' + item1[10] + '</td>';
                        str += '  <td>' + item1[11] + '</td>';
                        str += '  <td>' + item1[7] + '</td>';
                        str += '  </tr> ';
                        cou++;
                    });
                    $("#electiveSubject").append(str);
                    $("#countval1").val(cou);
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }
    }

    function getData() {
        if ($("#acad_year").val() != '' && $("#program_code").val() != '' && $("#branch_code").val() != '' && $("#stynumber").val() != '' && $("#semCode").val() != '' && $("#section").val() != '') {
            $("#studentlist").empty();
            $("#errorList").empty();
            var coresubject = 0;
            var electivesubject = 0;
            var countval = $("#countval").val();
            var countval1 = $("#countval1").val();
            var totalcredit = 0
            for (var j = 0; j < countval; j++) {
                if ($('#chk1' + j).is(':checked')) {
                    var c = $('#chk1' + j).val();
                    var ids = c.split("~@~");
                    var coreCd = ids[6];
                    var corecredit = parseFloat(coreCd);
                    totalcredit = totalcredit + corecredit;
                    coresubject++;
                }
            }
            for (var j = 0; j < countval1; j++) {
                if ($('#chk2' + j).is(':checked')) {
                    var e = $('#chk2' + j).val();
                    var ids = e.split("~@~");
                    var electiveCd = ids[6];
                    var electivecredit = parseFloat(electiveCd);
                    totalcredit = totalcredit + electivecredit;
                    electivesubject++;
                }
            }
            if (coresubject == 0 && electivesubject == 0)
            {
                BootstrapDialog.alert("Please Select Atleast One Core Subject or Elective Subject");
            } else {
                $.blockUI();
                $.ajax({
                    type: "POST",
                    url: "bulkStudentSubjectChoice/getStudentsList",
                    data: '&semCode=' + $("#registrationid").val() + '&acad_year=' + $("#acad_year").val() + '&program_code=' + $("#programid").val() + '&branch_code=' + $("#branch_code").val() + '&stynumber=' + $("#stynumber").val() + '&section=' + $("#section").val() + '&totalcredit=' + totalcredit,
                    dataType: "json",
                    async: true,
                    timeout: 20000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        var str = '';
                        var str1 = '';
                        var count = new Array();
                        var cou = 0;
                        var cou1 = 1;
                        var cou2 = 1;
                        if (data.studentwithouterror != null || data.studentwithouterror != '' && data.studentwitherror != null || data.studentwitherror != '')
                        {
                            if (data.studentwithouterror != '' && data.studentwithouterror != null) {
                                $.each(data.studentwithouterror, function (i, item)
                                {
                                    str += '<tr>';
                                    str += '  <td>' + cou1++ + '<input type="checkbox" checked="true" class="hidden" name="chk3' + i + '" id="chk3' + i + '" value="' + item.studentid + "~@~" + item.registrationid + "~@~" + item.instituteid + '"/></td>';
                                    str += '  <td>' + item.enrollmentno + '</td>';
                                    str += '  <td>' + item.studentname + '</td>';
                                    str += '  </tr> ';
                                    cou++;
                                });
                                $("#studentlist").append(str);
                                $("#countval2").val(cou);
                            } else {
                                BootstrapDialog.alert("No Student found for choice...");
                            }
                            $.each(data.studentwitherror, function (i, item1)
                            {
                                str1 += '<tr>';
                                str1 += '  <td>' + cou2++ + '</td>';
                                str1 += '  <td>' + item1.enrollmentno + '</td>';
                                str1 += '  <td>' + item1.studentname + '</td>';
                                str1 += '  <td>' + item1.error + '</td>';
                                str1 += '  </tr> ';
                            });
                            $("#errorList").append(str1);
                        } else {
                            BootstrapDialog.alert("No Data Found...");
                        }
                        setTimeout($.unblockUI, 1000);
                    },
                    error: function (response) {
                        setTimeout($.unblockUI, 1000);
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            }

        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }
    }

    function save() {
        var students = 0;
        var countval = $("#countval").val();
        var countval1 = $("#countval1").val();
        var countval2 = $("#countval2").val();
        var selectedCoreSubject = '';
        var selectedElecSubject = '';
        var studentsList = '';
        for (var j = 0; j < countval2; j++) {
            if ($('#chk3' + j).is(':checked')) {
                students++;
            }
        }
        if (students == 0) {
            BootstrapDialog.alert("Student not Exits for this criteria");
        } else {
            for (var i = 0; i < countval; i++)
            {
                if ($('#chk1' + i + '').is(':checked')) {
                    if (i != 0) {
                        selectedCoreSubject += ",";
                    }
                    selectedCoreSubject += $('#chk1' + i + '').val();
                }
            }

            for (var i = 0; i < countval1; i++)
            {
                if ($('#chk2' + i + '').is(':checked')) {
                    if (i != 0) {
                        selectedElecSubject += ",";
                    }
                    selectedElecSubject += $('#chk2' + i + '').val();
                }
            }

            for (var i = 0; i < countval2; i++)
            {
                if ($('#chk3' + i + '').is(':checked')) {
                    if (i != 0) {
                        studentsList += ",";
                    }
                    studentsList += $('#chk3' + i + '').val();
                }
            }

            saveData(selectedCoreSubject, selectedElecSubject, studentsList);
        }

    }

    function saveData(selectedCoreSubject, selectedElecSubject, studentsList) {
        $.ajax({
            type: "POST",
            url: "bulkStudentSubjectChoice/doSaveBulkStudents",
            data: '&selectedCoreSubject=' + selectedCoreSubject + '&selectedElecSubject=' + selectedElecSubject + '&studentsList=' + studentsList,
            dataType: "json",
            async: false,
            timeout: 20000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.griddata == 'Success') {
                    toastr.success('Record Successfully Saved', "Success!!");
                } else {
                    BootstrapDialog.alert(data.griddata);
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function clearstysection() {
        $("#stynumber").val('X').trigger("chosen:updated");
        $("#section").val('X').trigger("chosen:updated");
    }

</script>

