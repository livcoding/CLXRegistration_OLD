<%-- 
    Document   : studentRegistrationPermission
    Created on : Feb 6, 2019, 2:17:56 PM
    Author     : mohit1.kumar
--%>

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
                <form method="POST" id="ajaxform" class="form-horizontal">
                    <div class="row col-lg-12 form-group" id="divContainer" >
                        <div class="row col-lg-6 form-group" >
                            <label class="radio-inline"> &nbsp;&nbsp; <input type="radio" name="permission" id="bulk" checked="true" onclick="showHide();" value="B" style="display:inline-block"> &nbsp;Bulk Registration Permission</label>  
                            <label class="radio-inline">&nbsp;&nbsp; <input type="radio" name="permission" id="indv" onclick="showHide();" value="I" style="display:inline-block"> &nbsp;Individual Registration Permission</label>  
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Semester Code:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                    <select class="form-control" id="semCode" name="semCode" onchange="resetother(); getFromToDate();" data-validation="required"  data-live-search="true" onchange="" data-validation-error-msg="Please select semester code...!">               
                                        <option value="">Select</option>
                                        <c:forEach items="${semCode}" var="semCode">
                                            <option value="${semCode[0]}"><c:out value="${semCode[1]}"/></option>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="acd1">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Academic Year:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
                                    <select class="form-control" id="acadYear" name="acadYear"  data-validation="required" data-live-search="true" onchange="getProgram();" data-validation-error-msg="Please select academic year...!">               
                                        <option value="">Select</option>
                                        <c:forEach items="${acad}" var="acad">
                                            <option value="${acad}"><c:out value=" ${acad}"/></option>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group" id="acd2" style="display:none">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Enrollment No.:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
                                    <input type="text" id="enrollmentNo1" name="enrollmentNo1" onkeyup="this.value = this.value.toUpperCase();" onblur="getStudentInfo();" class="form-control" autocomplete="off" placeholder="Enter Enrollment No."/>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group" id="pgsec">
                                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Program Code:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                    <select class="form-control" id="prgCode" name="prgCode" data-validation="required" data-live-search="true" onchange="getBranch();" data-validation-error-msg="Please select program code...!">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>                        
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Semester Type:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <select class="form-control" id="styType" name="styType" data-live-search="true"  data-validation="required">
                                        <option value="">Select</option>
                                        <c:forEach items="${styType}" var="styType">
                                            <c:choose>
                                                <c:when test="${styType[1]=='REG'}">
                                                    <option selected="true" value="${styType[0]}"><c:out value="${styType[1]}"/></option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${styType[0]}"><c:out value="${styType[1]}"/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">From Date:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <div class='input-group date' id='fromDate'>
                                        <input type='text' name="fromDate" data-validation="required" id="FD" onblur="checkToDate();" class="form-control" autocomplete="off" />
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>          
                                </div>
                            </div> 
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">To Date:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <div class='input-group date' id='toDate'>
                                        <input type='text' name="toDate" data-validation="required" id="TD" onblur="checkFromDate();" class="form-control" autocomplete="off" />
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>          
                                </div>
                            </div> 
                        </div>
                        <div class="row col-lg-12 form-group">  
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Reg. Allow Date:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <div class='input-group date' id='regAllowDate'>
                                        <fmt:formatDate value='${data.registrationdatefrom}' var='reg_from' type='date' pattern='dd/MM/yyyy'/>
                                        <input type='text' name="regAllowDate"  id="raDate" onblur="checkDate();" data-validation="required" class="form-control" autocomplete="off" />
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>          
                                </div>                          
                            </div>
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Remarks:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                    <textarea id="remarks" name="remarks" maxlength="160" placeholder="Enter Remarks" class="form-control has-help-txt" style="width:100%;min-width: 100%;max-width: 100%;min-height:40px"> </textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" id="pgsec1">  
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" >New Registration:</label>
                                <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6  checkbox-inline">
                                    <input type="checkbox" id="newReg" name="newReg" value="Y" onclick="showhide1();" />
                                </div></div>
                            <div class="row col-lg-6 form-group" id="sectincodediv">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Section Code:</font></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                    <select class="form-control" id="branchCode" name="branchCode"    onchange="getRegNum();" >               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" >  
                            <div class="row col-lg-6 form-group" ></div>
                            <div class="row col-lg-6 form-group" >
                                <b>Registration in Next Semester(STY):&nbsp;</b> <input type="radio" checked="true" name="regSem" id="yes" value="Y">Yes
                                &nbsp;&nbsp; <input type="radio" name="regSem" id="no" value="N" >No
                                <br><br> <b>Populate PST Automatically:&nbsp;</b>&nbsp;&nbsp; <input type="checkbox" checked="true" name="ppst" id="ppst" value="Y">
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" style="display: none;" id="regis" >  
                            <div class="row col-lg-6 form-group" >
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Enrollment No:</font>
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                                    <select class="form-control" id="regNO" name="regNO"  data-validation="" data-live-search="true" >               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12">
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                <a onclick="getData()" class="btn btn-success btn-sm btn-flat">Load Data</a> 
                                <button class="btn btn-warning btn-sm btn-flat" onclick="myReset();" type="reset"> Reset</button> 
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="col-lg-12" style="margin-top:10px;">
                                <div id="specialCase_Button" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                                <p style="color:red" id="hint"><b><u>Hint:</u></b> &nbsp; All records must be saved in any case<br>Check or Uncheck change only Reg.Allow<br><span style="color:green">Green coloured records are already saved</span></p>
                                <table id="gridTable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                    <thead id="header">
                                        <tr class="" >
                                            <th width="2px;">Select</th>
                                            <th width="2px;">Sl No.</th>
                                            <th width="5px;">Reg. Allow</th>
                                            <th width="10px;" >Enrollment Number</th>               
                                            <th width="10px;" >Rank</th>               
                                            <th width="10px;" >Student Name</th>             
                                            <th width="10px;" >Program Branch</th>             
                                            <th width="10px;" >Registration Allow Date</th>             
                                            <th width="10px;" >STY No.</th>             
                                            <th width="10px;" >Pre Reg Event From Date</th>             
                                            <th width="10px;" >Pre Reg Event To Date</th>             
                                            <th width="10px;" >Semester Type</th>             
                                            <th width="10px;" >Remarks</th>             
                                            <th width="10px;" >Status</th>    
                                            <th style="display:none" >alert</th>    
                                        </tr>
                                    </thead>  
                                    <tbody> </tbody>
                                </table>                                                          
                                <!--</div>-->                       
                            </div>
                        </div>
                        <input type="hidden" id="count" name="count" value="">
                        <input type="hidden" name="programcode1" id="programcode1">
                        <input type="hidden" name="academicyear1" id="academicyear1">
                        <input type="hidden" name="selectedstudentid" id="selectedstudentid">
                        <input type="hidden" name="sectionid1" id="sectionid1">
                        <input type="hidden" name="section_id" id="section_id">
                        <input type="hidden" name="secId" id="sectionid">
                        <input type="hidden" name="alert" id="alert">
                        <input type="hidden" name="alert_sectionid" id="alert_sectionid">
                        <input type="hidden" name="alert_sub_sectionid" id="alert_sub_sectionid">
                        <div class="row col-lg-12">
                            <div  class="form-group pull-right" style="position: fixed;bottom: 20px;right: 30px;display:none" id="savebtn" >
                                <a href="javascript:saveData();" class="btn btn-success btn-lg" >Save</a>
                                  <a href="javascript:myReset();" class="btn btn-danger btn-lg"> Cancel</a>
                                <a href="javascript:topFunction();" id="myBtn" style="display:none" class="btn btn-primary  btn-lg"> Top</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('#regAllowDate').datetimepicker({format: 'DD/MM/YYYY'});
        $('#fromDate').datetimepicker({format: 'DD/MM/YYYY'});
        $('#toDate').datetimepicker({format: 'DD/MM/YYYY', useCurrent: false});
    });
    $('#enrollmentNo1').keypress(function (e) {
        if (e.which == 13) {
            $(this).blur();
        }
    });
    $(function () {
        var todayTime = new Date();
        var month = (todayTime.getMonth() + 1) < 10 ? '0' + (todayTime.getMonth() + 1) : '' + (todayTime.getMonth() + 1);
        var day = todayTime.getDate() < 10 ? '0' + todayTime.getDate() : '' + todayTime.getDate();
        var year = todayTime.getFullYear();
        $('#raDate').val(day + "/" + month + "/" + year);
    });
    function checkFromDate() {
        var start_date = $("#FD").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#TD").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#TD").val() != '') {
            if ($("#FD").val() != '') {
                if (fromYear > toYear) {
                    $("#TD").val('');
                    BootstrapDialog.alert("Please select another date greater than the from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#TD").val('');
                        BootstrapDialog.alert("Please select another date greater than the from date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#TD").val('');
                            BootstrapDialog.alert("Please select another date greater than the  from date!");
                        }
                    }
                }

            } else {
                $("#TD").val('');
                BootstrapDialog.alert("Please select from date first!");
            }
        }
    }


    function checkToDate() {
        var start_date = $("#FD").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#TD").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#TD").val() != '') {
            if ($("#FD").val() != '') {
                if (fromYear > toYear) {
                    $("#FD").val('');
                    BootstrapDialog.alert("Please select another date less than the To date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#FD").val('');
                        BootstrapDialog.alert("Please select another date less than the To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#FD").val('');
                            BootstrapDialog.alert("Please select another date less than the  To date!");
                        }
                    }
                }

            } else {
                $("#FD").val('');
                BootstrapDialog.alert("Please select from date first!");
            }
        }
    }
    function checkDate() {
        var start_date = $("#raDate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#TD").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#TD").val() != '') {
            if ($("#raDate").val() != '') {
                if (fromYear > toYear) {
                    $("#raDate").val('');
                    BootstrapDialog.alert("Please select another date less than the To date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#raDate").val('');
                        BootstrapDialog.alert("Please select another date less than the To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#raDate").val('');
                            BootstrapDialog.alert("Please select another date less than the  To date!");
                        }
                    }
                }

            } else {
                $("#raDate").val('');
                BootstrapDialog.alert("Please select from date first!");
            }
        }
    }
    function resetother() {
        $("#acadYear").val('').trigger("chosen:updated");
        $("#prgCode").val('').trigger("chosen:updated");
        $("#branchCode").val('').trigger("chosen:updated");
    }

    $("#semCode").chosen();
    $("#acadYear").chosen();
    $("#prgCode").chosen();
    $("#branchCode").chosen();
    $("#styType").chosen();
    $("#regNO").chosen({width: '100%'});
    var btns = [];
    var table = $('#gridTable').DataTable({
//        "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]],
        "lengthMenu": [[-1], ["All"]],
        "order": [],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    btns.push({
        extend: 'excel',
        text: '<i class=\"fa fa-lg fa-file-excel-o\"></i>',
        exportOptions: {
            modifier: {
                page: 'all'
            }
        }
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table, {
                    buttons: btns
                });
                $('#specialCase_Button').append(table.buttons(0, null).container());
            }, 100);
    function getFromToDate() {
        if ($("#semCode").val() != '')
        {
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/getFromToDate",
                data: '&registrationid=' + $("#semCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    $.each(data.fromToDate, function (i, item)
                    {
                        var fd = item[0];
                        var fdate = fd.split("-");
                        var fdd = fdate[2];
                        var fmm = fdate[1];
                        var fyy = fdate[0];
                        var fromDate = fdd + "/" + fmm + "/" + fyy;
                        $("#FD").val(fromDate);
                        var td = item[1];
                        var tdate = td.split("-");
                        var tdd = tdate[2];
                        var tmm = tdate[1];
                        var tyy = tdate[0];
                        var toDate = tdd + "/" + tmm + "/" + tyy;
                        $("#TD").val(toDate);
                    });
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select The Semester Code...");
        }
    }

    function getStudentInfo() {
        var enrollmentno = $("#enrollmentNo1").val();
        if (enrollmentno != '') {
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/getStudetnInfo",
                data: '&enrollmentno=' + enrollmentno,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.studentInfo != '' && data.studentInfo != null) {
                        $.each(data.studentInfo, function (i, item)
                        {
                            $("#programcode1").val(item[0]);
                            $("#academicyear1").val(item[1]);
                            $("#selectedstudentid").val(item[2]);
                            $("#sectionid1").val(item[3]);
                        });
                    } else {
                        BootstrapDialog.alert("Invalid Enrollment No.");
                        $("#enrollmentNo1").val('');
                        $("#selectedstudentid").val('');
                        $("#academicyear1").val('');
                        $("#programcode1").val('');
                        $("#sectionid1").val('');
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        }
    }


    function getProgram() {
        if ($("#acadYear").val() != '')
        {
            $("#prgCode").empty();
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/getProgramCodeReg",
                data: '&acad_year=' + $("#acadYear").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str = '';
                    var str = '<option value="">Select</option>';
                    $.each(data.programList, function (i, item)
                    {
                        str += '<option value="' + item[1] + '">' + item[0] + '</option>'
                    });
                    $("#prgCode").append(str);
                    $("#prgCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }
    }

    function getBranch() {
        if ($("#semCode").val() != '' && $("#acadYear").val() != '' && $("#prgCode").val() != '')
        {
            $("#branchCode").empty();
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/getSecCodeReg",
                data: '&prgCode=' + $("#prgCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str = '';
                    var str = '<option value="">Select</option>';
                    $.each(data.section, function (i, item)
                    {
                        str += '<option value="' + item[1] + '">' + item[0] + '</option>'
                    });
                    $("#branchCode").append(str);
                    $("#branchCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }
    }

    function getRegNum() {
        if ($("#acadYear").val() != '' && $("#prgCode").val() != '' && $("#branchCode").val() != '')
        {
            $("#regNO").empty();
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/getRegNum",
                data: '&prgCode=' + $("#prgCode").val() + '&acadYear=' + $("#acadYear").val() + '&branchCode=' + $("#branchCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str = '';
                    var str = '<option value="">Select</option>';
                    $.each(data.regNo, function (i, item)
                    {
                        str += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + ' </option>'
                    });
                    $("#regNO").append(str);
                    $("#regNO").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Enter The Required Field...");
        }

    }
    function showhide1() {
        //        alert("hello"+$("#newReg").prop("checked"))
        if ($("#newReg").prop("checked") == true) {
            $("#sectincodediv").hide();
            document.getElementById("yes").checked = false;
            document.getElementById("no").checked = true;
        } else {
            $("#sectincodediv").show();
            document.getElementById("yes").checked = true;
            document.getElementById("no").checked = false;
        }
    }


    function getData() {
        var radioValue = $("input[name='permission']:checked").val();
        var flag = '';
        if (radioValue == 'I') {
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#enrollmentNo1").val() == '') {
                flag = 'enrollno';
            } else if ($("#styType").val() == '') {
                flag = 'stytype';
            }
        } else {
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#acadYear").val() == '') {
                flag = 'acadyear';
            } else if ($("#prgCode").val() == '') {
                flag = 'progcode';
            } else if ($("#branchCode").val() == '' && $("#newReg").prop("checked") == false) {
                flag = 'seccode';
            } else if ($("#styType").val() == '') {
                flag = 'stytype';
            }
        }
        $("#alert").val('');
        $("#alert_sectionid").val('');
        $("#alert_sub_sectionid").val('');
        var newRegist = $("input[name='newReg']:checked").val();
        if (flag == '') {
            var radioValue = $("input[name='permission']:checked").val();
            var regSem = $("input[name='regSem']:checked").val();
            var data = '';
            if (radioValue == 'I') {
                var pgcode = $("#programcode1").val();
                var acade = $("#academicyear1").val();
                var stuid = $("#selectedstudentid").val();
                var sectionid = $("#sectionid1").val();
                if (sectionid != '') {
                    data = '&registrationid=' + $("#semCode").val() + '&acadYear=' + acade + '&prgCode=' + pgcode + '&branchCode=' + sectionid + '&styType=' + $("#styType").val()
                            + '&flag=' + radioValue + '&studentId=' + stuid + '&regSem=' + regSem + '&newReg=' + newRegist;
                }
            } else {
                data = '&registrationid=' + $("#semCode").val() + '&acadYear=' + $("#acadYear").val() + '&prgCode=' + $("#prgCode").val() + '&branchCode=' + $("#branchCode").val() + '&styType=' + $("#styType").val() + '&flag=' + radioValue + '&regSem=' + regSem + '&newReg=' + newRegist;
            }
            if (data != '') {
                $('#gridTable').DataTable().clear().draw();
                $.blockUI();
                $.ajax({
                    type: "POST",
                    url: "stdRegPermsn/loadData",
                    data: data,
                    dataType: "json",
                    async: true,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        var count = 0;
                        if (data.gridData != '' || data.gridData != null) {
                            if (data.gridData.length > 0) {
                                debugger;
                                if (data.gridData == 'A') {
                                    BootstrapDialog.alert("You can't do anything with this student registration as batch has been defined for this student.");
                                } else if (data.gridData == 'B') {
                                    BootstrapDialog.confirm("Student Subject Choice Master data has been defined for this student.Do you want to clean all data associated with this student registration?", function (result) {
                                        if (result) {
                                            BootstrapDialog.confirm("Are you sure you want to clean this data?", function (Yes) {
                                                if (Yes) {
                                                    deleteSSM();
                                                }
                                            })
                                        }
                                    });
                                } else if (data.gridData == 'C') {
                                    BootstrapDialog.confirm("Student has been already registered.Do you want to clean all registration data associated with this student?", function (result) {
                                        if (result) {
                                            BootstrapDialog.confirm("Are you sure you want to clean this data?", function (Yes) {
                                                if (Yes) {
                                                    deleteSR();
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    var flag = 0;
                                    var c = 0;
                                    var s = 0;
                                    var ss = 0;
                                    var quotaflag = "";
                                    var sectionflag = "";
                                    var subsectionflag = "";
                                    $('#alert').val('');
                                    $('#alert').empty();
                                    $("#savebtn").hide();
                                    $.each(data.gridData, function (i, item) {
                                        $("#savebtn").show();
                                        var str1 = '';
                                        var regAllow = item[10] == 'Y' ? 'Yes' : 'No';
                                        var remark = item[11] == null ? '--' : item[11];
                                        var rank = item[1] == null ? '--' : item[1];
                                        if (item[15] == "E") {
                                            str1 += ' <tr id="' + item[12] + ' ~@~ ' + item[13] + '" style="color:darkgreen;font-weight:bold">';
                                            if (regAllow == "Yes") {
                                                str1 += ' <td ><input type="checkbox" id="regAllowChk' + i + '" name="regAllowChk' + i + '" checked="true" ><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + ' " checked="true" class="hidden" />';
                                                str1 += ' </td>';
                                            } else {
                                                str1 += ' <td ><input type="checkbox" id="regAllowChk' + i + '" name="regAllowChk' + i + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + '" checked="true" class="hidden" />';
                                                str1 += ' </td>';
                                            }
                                        } else if (item[15] == "N") {
                                            str1 += ' <tr id="' + item[12] + ' ~@~ ' + item[13] + '" style="font-weight:bold">';
                                            if (regAllow == "Yes") {
                                                str1 += ' <td ><input type="checkbox" id="regAllowChk' + i + '" name="regAllowChk' + i + '"  checked="true"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + ' " checked="true" class="hidden"/>';
                                                str1 += ' </td>';
                                            } else {
                                                str1 += ' <td ><input type="checkbox" id="regAllowChk' + i + '" name="regAllowChk' + i + '" ><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + ' " checked="true" class="hidden"/>';
                                                str1 += ' </td>';
                                            }
                                        }
                                        str1 += ' <td>' + (i + 1) + '</td>';
                                        str1 += ' <td>' + regAllow + '</td>';
                                        str1 += ' <td>' + item[0] + '</td>';
                                        str1 += ' <td>' + rank + '</td>';
                                        str1 += ' <td>' + item[2] + '</td>';
                                        str1 += ' <td>' + item[3] + ' /' + item[4] + ' </td>';
                                        str1 += ' <td>' + item[5] + '</td>';
                                        str1 += ' <td>' + item[6] + '</td>';
                                        str1 += ' <td>' + item[7] + '</td>';
                                        str1 += ' <td>' + item[8] + '</td>';
                                        str1 += ' <td>' + item[9] + '</td>';
                                        str1 += ' <td>' + remark + '</td>';
                                        str1 += ' <td>' + item[14] + '</td>';
                                        str1 += ' <td style="display:none">';
                                        //Quota,Section and SubSection is not check of 1st or 3rd sem lateralEntry='Y' Students
                                        if (!(item[6] == 1 || (item[6] == 3 && item[22] == 'Y'))) {
                                            var studata = item[0] + " / " + item[2];
                                            if (item[16] == '' || item[16] == null) {
                                                str1 += '<input type="hidden" id="hiddenquotaalert' + i + '" value="' + studata + '<br>" >';
                                            } else {
                                                str1 += '<input type="hidden" id="hiddenquotaalert' + i + '" value="" >';
                                            }

                                            if (item[20] == '' || item[20] == null) {
                                                str1 += '<input type="hidden" id="hiddensectionalert' + i + '" value="' + studata + '<br>" >';
                                            } else {
                                                str1 += '<input type="hidden" id="hiddensectionalert' + i + '" value="" >';
                                            }

                                            if (item[21] == '' || item[21] == null) {
                                                str1 += '<input type="hidden" id="hiddensubsectionalert' + i + '" value="' + studata + '<br>" >';
                                            } else {
                                                str1 += '<input type="hidden" id="hiddensubsectionalert' + i + '" value="" >';
                                            }
                                        } else {
                                            str1 += '<input type="hidden" id="hiddenquotaalert' + i + '" value="" >';
                                            str1 += '<input type="hidden" id="hiddensectionalert' + i + '" value="" >';
                                            str1 += '<input type="hidden" id="hiddensubsectionalert' + i + '" value="" >';
                                        }
                                        str1 += ' </td></tr>';
                                        count++;
                                        table.row.add($(str1)).draw();
                                        $("#save").removeAttr("disabled");
                                        $("#section_id").val(item[8]);
                                    });
                                    $("#count").val(count);
                                }
                            } else {
                                BootstrapDialog.alert("No Data Found For Given Selection Criteria..");
                            }
                        } else {
                            BootstrapDialog.alert("No Data Found For Given Selection Criteria..");
                        }
                        setTimeout($.unblockUI, 2000);
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............' + response, "Warning!!");
                        $.unblockUI();
                    }
                });
            } else {
                BootstrapDialog.alert("Please Enter The Enrollment No.");
            }
        } else {
            if (flag == 'enrollno') {
                BootstrapDialog.alert("Please Enter Enrollment No.");
            } else if (flag == 'progcode') {
                BootstrapDialog.alert("Please Select Program Code..");
            } else if (flag == 'acadyear') {
                BootstrapDialog.alert("Please Select Academic Year..");
            } else if (flag == 'semcode') {
                BootstrapDialog.alert("Please Select Semester Code..");
            } else if (flag == 'stytype') {
                BootstrapDialog.alert("Please Select Semester Type..");
            } else if (flag == 'seccode') {
                BootstrapDialog.alert("Please Select Section Code..");
            }
        }
    }
    setTimeout(
            function () {

                $.validate(
                        {
                            //                            modules: 'file, date',
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "stdRegPermsn/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 0,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        debugger;
                                        var data0 = data.split('~@~')[0]; // success msg
                                        var data1 = data.split('~@~')[1]; // sty excedd studentlist
                                        var data2 = data.split('~@~')[2]; // no pws define studentlist
                                        if (data0 === 'success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            if (data1 != '') {
                                                var msg = 'Following Student Registration is not done because student STY number exceed Program Max Sty';
                                                BootstrapDialog.alert(msg + "<br>" + data1);
                                            }
                                            if (data2 != '') {
                                                var msg = 'Following Student Registration is not done because Program Wise Sub-Section is not define';
                                                BootstrapDialog.alert(msg + "<br>" + data2);
                                            }
                                            $('#gridTable').DataTable().clear().draw();
                                            //                                            loadForm("stdRegPermsn/list");
                                        } else {
                                            toastr.error(Error['error_code'], "Error!!");
                                        }
                                        setTimeout($.unblockUI, 2000);
                                    },
                                    error: function (response) {
                                        BootstrapDialog.alert("You have saved Bulk data! Your process is running in background. It can take a long time <br>You can wait or proceed further! ");
                                        //                                         toastr.warning('Something Wrong.', "Warning!!");
                                        $.unblockUI();
                                        loadForm("stdRegPermsn/list");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);
    function saveData() {
        var count = $("#count").val();
        var qalert = '';
        var salert = '';
        var ssalert = '';
        var q = 0;
        var s = 0;
        var ss = 0;
        var qindex = 1;
        var sindex = 1;
        var ssindex = 1;
        for (var i = 0; i < count; i++) {
            if ($("#regAllowChk" + i).is(":checked")) {
                if ($("#hiddenquotaalert" + i).val() != '') {
                    if (q == 0) {
                        qalert += "Please Enter the Student Quota of these student's first by Student Master.. Then Save Record<br> ";
                        qalert += qindex + ". " + $("#hiddenquotaalert" + i).val();
                        qindex++;
                        q++;
                    } else {
                        qalert += qindex + ". " + $("#hiddenquotaalert" + i).val();
                        qindex++;
                    }
                }
                if ($("#hiddensubsectionalert" + i).val() != '') {
                    if (s == 0) {
                        s++;
                        salert += "Please Enter the Student Section of these student's first by Student Master.. Then Save Record<br> ";
                        salert += sindex + ". " + $("#hiddensectionalert" + i).val();
                        sindex++;
                    } else {
                        salert += sindex + ". " + $("#hiddensectionalert" + i).val();
                        sindex++;
                    }
                }
                if ($("#hiddensubsectionalert" + i).val() != '') {
                    if (ss == 0) {
                        ss++;
                        ssalert += "Please Enter the Student Sub Section of these student's first by Student Master.. Then Save Record<br> ";
                        ssalert += ssindex + ". " + $("#hiddensubsectionalert" + i).val();
                        ssindex++;
                    } else {
                        ssalert += ssindex + ". " + $("#hiddensubsectionalert" + i).val();
                        ssindex++;
                    }
                }
            }
        }
        if (ssalert == '') {
            if (salert == '') {
                if (qalert == '') {
                    var checkNewReg = $("input[name='newReg']:checked").val();
                    if (checkNewReg == 'Y') {
                        if ($("input[type='checkbox']:checked").val() != '') {
                            $("#ajaxform").submit();
                        } else {
                            BootstrapDialog.alert("Please Select The Required Field...");
                        }
                    } else {
                        if ($("input[type='checkbox']:checked").val() != '') {
                            $("#ajaxform").submit();
                        } else {
                            BootstrapDialog.alert("Please Select The Required Field...");
                        }
                    }
                } else {
                    BootstrapDialog.alert(qalert);
                }
            } else {
                BootstrapDialog.alert(salert);
            }
        } else {
            BootstrapDialog.alert(ssalert);
        }
    }

    function checkAll() {

        var count = $("#count").val();
        if ($("#chkAll").is(":checked")) {
            for (var i = 0; i < count; i++) {
                $("#regAllowChk" + i).prop("checked", true);
            }
        } else {
            for (var i = 0; i < count; i++) {
                $("#regAllowChk" + i).prop("checked", false);
            }
        }

    }


    function showHide() {
        $('#gridTable').DataTable().clear().draw();
        var radioValue = $("input[name='permission']:checked").val();
        if (radioValue == 'I') {
            $("#pgsec").hide();
            $("#pgsec1").hide();
            $("#acd1").hide();
            $("#acd2").show();
            $("#hint").hide();
            $("#acadYear").removeAttr('required data-validation');
            $("#prgCode").removeAttr('required data-validation');
            $("#branchCode").removeAttr('required data-validation');
            $('#enrollmentNo1').attr("data-validation", "required");
        } else {
            $("#pgsec").show();
            $("#pgsec1").show();
            $("#acd1").show();
            $("#acd2").hide();
            $("#hint").show();
            $("#enrollmentNo1").removeAttr('required data-validation');
            $('#acadYear').attr("data-validation", "required");
            $('#prgCode').attr("data-validation", "required");
            $('#branchCode').attr("data-validation", "required");
        }
    }
    function  deleteSSM() {

        var radioValue = $("input[name='permission']:checked").val();
        var flag = '';
        if (radioValue == 'I') {
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#enrollmentNo1").val() == '') {
                flag = 'enrollno';
            } else if ($("#styType").val() == '') {
                flag = 'stytype';
            }
        } else {
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#acadYear").val() == '') {
                flag = 'acadyear';
            } else if ($("#prgCode").val() == '') {
                flag = 'progcode';
            } else if ($("#branchCode").val() == '') {
                flag = 'seccode';
            } else if ($("#styType").val() == '') {
                flag = 'stytype';
            }
        }
        if (flag == '') {
            var radioValue = $("input[name='permission']:checked").val();
            var regSem = $("input[name='regSem']:checked").val();
            var data;
            if (radioValue == 'I') {
                var pgcode = $("#programcode1").val();
                var acade = $("#academicyear1").val();
                var stuid = $("#selectedstudentid").val();
                var sectionid = $("#sectionid1").val();
                if (sectionid != '') {
                    data = '&registrationid=' + $("#semCode").val() + '&acadYear=' + acade + '&prgCode=' + pgcode + '&branchCode=' + sectionid + '&styType=' + $("#styType").val()
                            + '&flag=' + radioValue + '&studentId=' + stuid + '&regSem=' + regSem;
                } else {
                    BootstrapDialog.alert("Please Enter The Enrollment No.");
                }
            }
            $('#gridTable').DataTable().clear().draw();
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/deleteSSM",
                data: data,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var count = 0;
                    $("#savebtn").hide();
                    $.each(data.gridData, function (i, item) {
                        $("#savebtn").show();
                        var str1 = '';
                        var regAllow = '';
                        var remark = '';
                        var rank = '';
                        if (item[10] == 'Y') {
                            regAllow = 'Yes';
                        } else {
                            regAllow = 'No';
                        }
                        if (item[11] == null) {
                            remark = '';
                        } else {
                            remark = item[11];
                        }
                        if (item[1] == null) {
                            rank = '--';
                        } else {
                            rank = item[1];
                        }
                        str1 += ' <tr id="' + item[12] + ' ~@~ ' + item[13] + '">';
                        if (item[14] == 'ALLOW' || regAllow == "Yes") {
                            str1 += ' <td ><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + '" checked="true" />';
                            str1 += ' </td>';
                        } else {
                            str1 += ' <td ><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + ' "/>';
                            str1 += ' </td>';
                        }
                        str1 += ' <td>' + (i + 1) + '</td>';
                        str1 += ' <td>' + regAllow + '</td>';
                        str1 += ' <td>' + item[0] + '</td>';
                        str1 += ' <td>' + rank + '</td>';
                        str1 += ' <td>' + item[2] + '</td>';
                        str1 += ' <td>' + item[3] + ' /' + item[4] + ' </td>';
                        str1 += ' <td>' + item[5] + '</td>';
                        str1 += ' <td>' + item[6] + '</td>';
                        str1 += ' <td>' + item[7] + '</td>';
                        str1 += ' <td>' + item[8] + '</td>';
                        str1 += ' <td>' + item[9] + '</td>';
                        str1 += ' <td>' + remark + '</td>';
                        str1 += ' <td>' + item[14] + '</td>';
                        str1 += ' </tr>';
                        count++;
                        table.row.add($(str1)).draw();
                        $("#save").removeAttr("disabled");
                    });
                    $("#count").val(count);
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............' + response, "Warning!!");
                }
            });
        } else {
            if (flag == 'enrollno') {
                BootstrapDialog.alert("Please Enter Enrollment No.");
            } else if (flag == 'progcode') {
                BootstrapDialog.alert("Please Select Program Code..");
            } else if (flag == 'acadyear') {
                BootstrapDialog.alert("Please Select Academic Year..");
            } else if (flag == 'semcode') {
                BootstrapDialog.alert("Please Select Semester Code..");
            } else if (flag == 'stytype') {
                BootstrapDialog.alert("Please Select Semester Type..");
            } else if (flag == 'seccode') {
                BootstrapDialog.alert("Please Select Section Code..");
            }
        }
    }

    function  deleteSR() {

        var radioValue = $("input[name='permission']:checked").val();
        var flag = '';
        if (radioValue == 'I') {
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#enrollmentNo1").val() == '') {
                flag = 'enrollno';
            } else if ($("#styType").val() == '') {
                flag = 'stytype';
            }
        } else {
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#acadYear").val() == '') {
                flag = 'acadyear';
            } else if ($("#prgCode").val() == '') {
                flag = 'progcode';
            } else if ($("#branchCode").val() == '') {
                flag = 'seccode';
            } else if ($("#styType").val() == '') {
                flag = 'stytype';
            }
        }
        if (flag == '') {
            var radioValue = $("input[name='permission']:checked").val();
            var regSem = $("input[name='regSem']:checked").val();
            var data;
            if (radioValue == 'I') {
                var pgcode = $("#programcode1").val();
                var acade = $("#academicyear1").val();
                var stuid = $("#selectedstudentid").val();
                var sectionid = $("#sectionid1").val();
                if (sectionid != '') {
                    data = '&registrationid=' + $("#semCode").val() + '&acadYear=' + acade + '&prgCode=' + pgcode + '&branchCode=' + sectionid + '&styType=' + $("#styType").val()
                            + '&flag=' + radioValue + '&studentId=' + stuid + '&regSem=' + regSem;
                } else {
                    BootstrapDialog.alert("Please Enter The Enrollment No.");
                }
            }
            $('#gridTable').DataTable().clear().draw();
            $.ajax({
                type: "POST",
                url: "stdRegPermsn/deleteSR",
                data: data,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var count = 0;
                    $("#savebtn").hide();
                    $.each(data.gridData, function (i, item) {
                        $("#savebtn").show();
                        var str1 = '';
                        var regAllow = '';
                        var remark = '';
                        var rank = '';
                        if (item[10] == 'Y') {
                            regAllow = 'Yes';
                        } else {
                            regAllow = 'No';
                        }
                        if (item[11] == null) {
                            remark = '';
                        } else {
                            remark = item[11];
                        }
                        if (item[1] == null) {
                            rank = '--';
                        } else {
                            rank = item[1];
                        }
                        str1 += ' <tr id="' + item[12] + ' ~@~ ' + item[13] + '">';
                        if (item[14] == 'ALLOW' || regAllow == "Yes") {
                            str1 += ' <td ><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + '" checked="true" />';
                            str1 += ' </td>';
                        } else {
                            str1 += ' <td ><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[12] + ' ~@~ ' + item[13] + ' ~@~ ' + item[16] + ' ~@~ ' + item[17] + ' ~@~ ' + item[18] + ' ~@~ ' + item[19] + ' ~@~ ' + item[6] + ' ~@~ ' + item[20] + ' ~@~ ' + item[21] + ' ~@~ ' + item[0] + ' ~@~ ' + item[2] + ' "/>';
                            str1 += ' </td>';
                        }
                        str1 += ' <td>' + (i + 1) + '</td>';
                        str1 += ' <td>' + regAllow + '</td>';
                        str1 += ' <td>' + item[0] + '</td>';
                        str1 += ' <td>' + rank + '</td>';
                        str1 += ' <td>' + item[2] + '</td>';
                        str1 += ' <td>' + item[3] + ' /' + item[4] + ' </td>';
                        str1 += ' <td>' + item[5] + '</td>';
                        str1 += ' <td>' + item[6] + '</td>';
                        str1 += ' <td>' + item[7] + '</td>';
                        str1 += ' <td>' + item[8] + '</td>';
                        str1 += ' <td>' + item[9] + '</td>';
                        str1 += ' <td>' + remark + '</td>';
                        str1 += ' <td>' + item[14] + '</td>';
                        str1 += ' </tr>';
                        count++;
                        table.row.add($(str1)).draw();
                        $("#save").removeAttr("disabled");
                    });
                    $("#count").val(count);
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            if (flag == 'enrollno') {
                BootstrapDialog.alert("Please Enter Enrollment No.");
            } else if (flag == 'progcode') {
                BootstrapDialog.alert("Please Select Program Code..");
            } else if (flag == 'acadyear') {
                BootstrapDialog.alert("Please Select Academic Year..");
            } else if (flag == 'semcode') {
                BootstrapDialog.alert("Please Select Semester Code..");
            } else if (flag == 'stytype') {
                BootstrapDialog.alert("Please Select Semester Type..");
            } else if (flag == 'seccode') {
                BootstrapDialog.alert("Please Select Section Code..");
            }
        }
    }
    mybutton = document.getElementById("myBtn");
    window.onscroll = function () {
        scrollFunction()
    };

    function scrollFunction() {
        if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
            mybutton.style.display = "inline-block";
        } else {
            mybutton.style.display = "none";
        }
    }
    function topFunction() {
        document.body.scrollTop = 0; // For Safari
        document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
    }
    function myReset() {
        $("#semCode").val('').trigger("chosen:updated");
        $("#acadYear").val('').trigger("chosen:updated");
        $("#prgCode").empty();
        $("#prgCode").val('').trigger("chosen:updated");
        $("#branchCode").empty();
        $("#branchCode").val('').trigger("chosen:updated");
        $("#regNO").val('').trigger("chosen:updated");
        $("#hint").show();
        $("#regis").hide();
        $("#regNO").removeAttr('required data-validation');
        $("#acadYear").attr({
            'required': "true",
            'data-validation': "required",
            'data-validation-error-msg': "Please select academic year...!"
        });
        $("#prgCode").attr({
            'required': "true",
            'data-validation': "required",
            'data-validation-error-msg': "Please select program code...!"
        });
        $("#pgsec").show();
        $("#pgsec1").show();
        $("#acd1").show();
        $("#acd2").hide();
        $("#savebtn").hide();
        $("#hint").show();
        $('#gridTable').DataTable().clear().draw();
    }
</script>


