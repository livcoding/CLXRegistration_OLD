<%-- 
    Document   : subjectWiseStudentCountReport
    Created on : Jan 14, 2019, 12:26:13 PM
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
                    <div class="row col-lg-12 form-group">
                        <div class="col-md-8 col-md-offset-2">
                            <div class="col-md-6" >
                                <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 radio-inline">
                                    <input type="radio" id="showReport" name="showReport" checked="true" value="pre" onclick="ShowHide(this.value);" />Based On Pre Registration
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 radio-inline">
                                    <input type="radio" id="showReport" name="showReport" value="post" onclick="ShowHide(this.value);"/> After Time Table/Teaching Load
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">    
                                <div class="row col-lg-12 form-group">           
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Semester Code:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-md-8">
                                        <select class="form-control" id="semesterCode" name="semesterCode" data-live-search="true" onchange="clearOther();" data-validation="required">               
                                            <option value="">Select</option>
                                            <c:forEach items="${regisList}" var="list">
                                                <%--<option value="${list.id.registrationid}~@~${list.registrationcode}~@~${list.registrationdesc}"><c:out value="${list.registrationcode} -- ${list.registrationdesc}"/></option>--%>
                                                <option value="${list[0]}"><c:out value="${list[1]} -- ${list[2]}"/></option>
                                            </c:forEach> 
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group">     
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Program Type:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-md-8">
                                        <select class="form-control" id="programType" name="programType" data-live-search="true" data-validation="required" onchange="getProgramWithBranchCode(); getStyNumber();">               
                                            <option value="">Select</option>
                                            <c:forEach items="${programTypeList}" var="list">
                                                <option value="${list.id.programtypeid}"><c:out value="${list.programtype} -- ${list.programtypedesc}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group">     
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Sty Number:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-md-8">
                                        <select class="form-control" id="semester" name="semester" multiple="true" data-live-search="true" data-validation="required">               
                                            <!--<option value="">Select</option>-->
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group"> 
                                    <label style="text-transform: capitalize;" class="col-sm-4 control-label">Export To:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-md-8 radio-inline">
                                        <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 radio-inline" id="pdf">
                                            <input type="radio" id="export_Top" name="export_To" value="P"/> PDF
                                        </div>
                                        <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 radio-inline"id="exls">
                                            <input type="radio" id="export_Toe" name="export_To" checked="true" value="E"/> Excel
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" style="margin:auto">
                                <div id="test" class="table-wrapper-scroll-y" style="border:1px solid lightgray">
                                    <table id="datatable" class="table table-bordered table-striped">
                                        <thead id="header" style="background-color:#008080;color:white">
                                            <tr class="" >   
                                                <th style="position:sticky;top:0px;background-color:#008080;width:30px"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkAll();"/></th>               
                                                <th style="position:sticky;top:0px;background-color:#008080;width:60px">Sl.No.</th>                             
                                                <th style="position:sticky;top:0px;background-color:#008080;">Program Code : Branch Desc</th>                             
                                            </tr>
                                        </thead> 
                                        <tbody id="prcodebranch">
                                        </tbody>
                                    </table>
                                </div>                            
                            </div>

                        </div>
                        <div class="row col-lg-12 form-group">
                            <div>
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
                                <div class="row col-lg-12 form-group"></div>
                                <div class="row col-lg-12 form-group"></div>
                                <div class="row col-lg-12 form-group"></div>
                                </form>
                            </div>
                        </div>
                        <input type="hidden" id="count" value=""/>
                    </div>
            </div>

            <script>
                $("#semesterCode").chosen();
                $("#programType").chosen();
                $("#semester").chosen();
                function myReset() {
                    $("#semesterCode").val('x').trigger("chosen:updated");
                    $("#programType").val('x').trigger("chosen:updated");
                    $("#semester").val('x').trigger("chosen:updated");
                    $("#prcodebranch").empty();
                    $("#chkAll").empty();
                }

                function clearOther() {
                    $("#programType").val('x').trigger("chosen:updated");
                    $("#prcodebranch").empty();
                    $("#chkAll").empty();
                    $("#semester").empty().trigger("chosen:updated");
                }

                function clearTable() {
                    $("#prcodebranch").empty();
                    $("#chkAll").empty();
                }

                function checkAll() {
                    var count = $("#count").val();
                    if ($("#chkAll").is(":checked")) {
                        for (var i = 0; i < count; i++) {
                            $("#chk" + i).prop("checked", true);
                        }
                    } else {
                        for (var i = 0; i < count; i++) {
                            $("#chk" + i).prop("checked", false);
                        }
                    }
                }

                function ShowHide(v) {
                    if (v == 'pre') {
                        $('#exls').show();
                        $('#pdf').show();
                    } else {
                        $('#exls').show();
                        $('#pdf').hide();
                        $("#export_Toe").prop("checked", true);
                    }
                }

                function getProgramWithBranchCode() {
                    $("#prcodebranch").empty();
                    $("#count").val('');
                    $("#chkAll").prop("checked", false);
                    if ($("#programType").val() != '') {
                        $.ajax({
                            type: "POST",
                            url: "subjectWiseStudentCountReport/getProgramWithBranchCode",
                            data: '&programType=' + $("#programType").val(),
                            dataType: "json",
                            async: false,
                            timeout: 5000,
                            cache: false,
                            beforeSend: function (xhr, options) {
                                return true;
                            },
                            success: function (data) {
                                var str = '';
                                var count = 1;
                                $.each(data.programCode, function (i, item)
                                {
                                    str += '<tr>';
                                    str += '<td><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[0] + ' : ' + item[1] + '"/></td>';
                                    str += '<td>' + count + '</td>';
                                    str += '<td>' + item[2] + ' : ' + item[3] + '</td></tr>';
                                    count++;
                                });
                                $("#count").val(count);
                                $("#prcodebranch").append(str);
                            },
                            error: function (response) {
                                toastr.warning('Something Wrong.', "Warning!!");
                            }
                        });
                    }
                }

                function getStyNumber() {
                    $("#semester").val('').trigger("chosen:updated");
                    $("#semester").empty();
                    $.ajax({
                        type: "POST",
                        url: "subjectWiseStudentCountReport/getStyNumber",
                        data: '&regid=' + $("#semesterCode").val(),
                        dataType: "json",
                        async: false,
                        timeout: 5000,
                        cache: false,
                        beforeSend: function (xhr, options) {
                            return true;
                        },
                        success: function (data) {
                            var str1 = '';
                            //                            str1 += '<option value="All">All</option>';
                            $.each(data.styNumber, function (i, item)
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
                }
                function getReport() {
                    var semesterCode = $("#semesterCode").val();
                    var programType = $("#programType").val();
                    var branchid = '';
                    var programid = '';
                    var count = $("#count").val();
                    var c = 0;
                    for (var i = 0; i < count; i++) {
                        if ($("#chk" + i).is(":checked")) {
                            programid += "," + $("#chk" + i).val().split(' : ')[0];
                            branchid += "," + $("#chk" + i).val().split(' : ')[1];
                        }
                    }
                    if (branchid != "") {
                        var semester = $("#semester").val();
                        var showReport = $("input[name='showReport']:checked").val();
                        var exportTo = $("input[name='export_To']:checked").val();
                        if (semesterCode != '' && programType != '' && semester != '') {
                            $.blockUI();
                            window.location.assign('subjectWiseStudentCountReport/getReport?excelfilename=SubjectWiseStudentCountReport&registrationid=' + semesterCode + '&programType=' + programType + '&program=' + programid + '&branches=' + branchid + '&stynumber=' + semester + '&showReport=' + showReport + '&exportTo=' + exportTo + '');
                            setTimeout($.unblockUI, 3000);
                        } else {
                            BootstrapDialog.alert("Please Select The Required Field.");
                        }
                    } else {
                        BootstrapDialog.alert("Please Select Atleast one Program/Branch.");
                    }
                }

            </script>
