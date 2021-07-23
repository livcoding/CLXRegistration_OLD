<%-- 
    Document   : bulkSubjectRegistrationList
    Created on : 22 Jan, 2019, 5:27:17 PM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">
    <div class="col-md-12">
        <div class="box-body" >
            <form method="POST" id="ajaxform" name="ajaxform" class="form-horizontal" enctype="multipart/form-data">
                <div class="col-lg-12" style="margin-top:10px;">
                    <div class="row col-lg-12 form-group" >
                        <div class="row col-lg-6 form-group">
                            <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-5 col-lg-4 col-xs-5 col-md-5 control-label">Semester Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top: -10px;">
                                <select class="form-control" id="regCode" name="regCode" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${regCodeList}" var="regList">
                                        <option value="${regList.registrationcode}"><c:out value="${regList.registrationcode}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <div class="row col-lg-6 form-group">
                            <label style="text-transform: capitalize; margin-top: -10px;" class="col-sm-5 col-lg-4 col-xs-5 col-md-5 control-label">Select File:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>  
                            <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" >
                                <input type="file" name="fileUpload" data-icon="false" id="fileUpload" onclick="clearRecord();">
                            </div>
                        </div>
                        <div class="row col-lg-6 form-group">
                            <div class="row col-lg-4 form-group" >
                                <!--<a href="javascript:loadData();" class="btn btn-success fa fa-search"></a>-->
                                <input type="button" value="Load Excel Data" class="btn btn-success btn-sm btn-flat" onclick="deleteExcelData(0);loadData();">
                            </div>
                            <div class="row col-lg-3 form-group" >
                                <input type="button" value="Validate Excel" class="btn btn-success btn-sm btn-flat" onclick="analyzeAndSaveExcelData();">
                            </div>
                            <div class="row col-lg-3 form-group" >
                                <input type="button" value="Download Templet" class="btn btn-success btn-sm btn-flat" onclick="report();">
                            </div>
                        </div> 
                    </div>
                </div>

                <div class="row col-lg-12 form-group" >
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-4 col-lg-2 col-xs-4 col-md-4 control-label">Total Record:
                    </label>
                    <div id="totalrecord"></div>
                </div>

                <div class="row col-lg-12 form-group" >
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-4 col-lg-2 col-xs-4 col-md-4 control-label">Saved Record:
                    </label>
                    <div id="savedrecord"></div>
                </div>

                <div class="row col-lg-12 form-group" >
                    <label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-4 col-lg-2 col-xs-4 col-md-4 control-label">Error Prone Record:
                    </label>
                    <div id="errorrecord"></div>
                </div>

                <div class="row col-lg-12 form-group" >  
                    <div class="box-header with-border" style="background-color: teal;">
                        <h3 class="box-title header_name" id="box-title" style="color: white;"><b>Error List Description:</b></h3>
                    </div>
                    <div id="test" class=" table-wrapper-scroll-y4" >
                        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                            <thead id="header">
                                <tr class=""  >                                          
                                    <td><b>ENROLLMENT NO.</b></td>                                       
                                    <td><b>SUBJECT CODE</b></td>                                       
                                    <td><b>SUBJECT TYPE</b></td>                                       
                                    <td><b>STY NUMBER</b></td>                                       
                                    <td><b>COURSE CREDIT POINT</b></td>                                       
                                    <td><b>SEMESTER TYPE</b></td>  
                                    <td style="color:red"><b>ERROR DESCRIPTION</b></td>                                        
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
                <div class="row col-lg-12" >   
                    <div  style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">                                  
                        <input type="button" value="Save" class="btn btn-success btn-sm btn-flat" onclick="saveData();">
                        <input type="button" value="Delete Temp XL Data" class="btn btn-success btn-sm btn-flat" onclick="deleteExcelData(1);">
                        <a href="javascript:deleteExcelData(0);blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                    </div>
                </div>
                <div class="row col-lg-12 form-group" ></div>
                <div class="row col-lg-12 form-group" ></div>
                <div class="row col-lg-12 form-group" ></div>
                <div class="row col-lg-12 form-group" ></div>
                <input type="hidden" name="enroll" id="enroll" value="ENROLLMENTNO:SUBJECTCODE"/>
                <input type="hidden" name="userid" id="userid" value=""/>
                <input type="hidden" name="taskid" id="taskid" value=""/>
                <!--for validate date before save-->
                <input type="hidden" name="total" id="total" value=""/>
                <input type="hidden" name="save" id="save" value=""/>
                <input type="hidden" name="error" id="error" value=""/>
            </form>
        </div>
    </div> 
</div>
<script>
    $("#regCode").chosen();

    function clearRecord() {
        $("#total").val('');
        $("#save").val('');
        $("#error").val('');
        document.getElementById("totalrecord").innerHTML = '';
        document.getElementById("savedrecord").innerHTML = '';
        document.getElementById("errorrecord").innerHTML = '';
        $("#errorList").empty();
    }
    function loadData() {
        if ($("#regCode").val() == '') {
            BootstrapDialog.alert("Please Select Semester Code first!");
        } else {
            var exlfile = $("#fileUpload").val();
            if (exlfile != '') {
                var ext = exlfile.split('.').pop().toLowerCase();
                if ($.inArray(ext, ['xls']) == -1) {
                    BootstrapDialog.show({
                        message: 'Invalid extension! Only Excel Format is allowed here. Download Excel Templet',
                        buttons: [{
                                label: 'Download',
                                action: function (dialog) {
                                    report();
                                }
                            }]
                    });
                } else {
                    var data = new FormData()
                    var regId = $("#regCode").val();
                    var enroll = $("#enroll").val();
                    var fl = $("#fileUpload").get(0).files[0];
                    if (fl != undefined) {
                        data.append("file", fl);
                        data.append("regId", regId);
                        data.append("enroll", enroll);
                    }
                    $.ajax({
                        type: "POST",
                        url: "bulkSubjectRegistration/checkExcelData",
                        contentType: false,
                        processData: false,
                        data: data,
                        async: false,
                        timeout: 5000,
                        cache: false,
                        beforeSend: function (xhr, options) {
                            return true;
                        },
                        success: function (data) {
                            if (data.errorAlert != null) {
                                var err = '';
                                $.each(data.errorAlert, function (i, item)
                                {
                                    if (i == 0) {
                                        err = err + data.errorAlert[i] + "<br>";
                                    } else {
                                        err = err + i + ". " + data.errorAlert[i] + "<br>";
                                    }
                                });
                                BootstrapDialog.alert(err);
                            } else {
                                if (data.excelColumndata != null) {
                                    var str = '';
                                    var str1 = '';
                                    str1 += '<label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label">' + data.excelColumndata[2] + '</label>';
                                    $("#userid").val(data.excelColumndata[0])
                                    $("#taskid").val(data.excelColumndata[1])
                                    document.getElementById("totalrecord").innerHTML = str1;
                                    $("#total").val(data.excelColumndata[2]);
                                    document.getElementById("savedrecord").innerHTML = '';
                                    document.getElementById("errorrecord").innerHTML = '';
                                    $("#save").val('');
                                    $("#error").val('');
                                    $("#datatable").append(str);
                                } else {
                                    BootstrapDialog.alert(data);
                                }
                            }
                        },
                        error: function (response) {
                            toastr.warning('Something Wrong...............', "Warning!!");
                        }
                    });
                }
            }

        }
    }

    function analyzeAndSaveExcelData() {
        if ($("#regCode").val() != '') {
            var exlfile = $("#fileUpload").val();
            if (exlfile != '') {
                var total = $("#total").val();
                if (total != '') {
                    $.ajax({
                        url: "bulkSubjectRegistration/analyzeAndSaveExcelData",
                        dataType: 'json',
                        async: false,
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: "userid=" + $("#userid").val() + "&taskid=" + $("#taskid").val(),
                        error: (function (response) {
                            alert('Server Error' + response);
                        }),
                        success: function (data) {
                            $("#errorList").empty();
                            var count = new Array();
                            var cou = 0;
                            if (data.analyzeexceldata != null) {
                                var str = '';
                                var str1 = '';
                                var str2 = '';
                                str1 += '<label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label">' + data.analyzeexceldata[0][1] + '</label>';
                                str2 += '<label style="text-transform: capitalize; margin-top: -17px;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label">' + data.analyzeexceldata[0][2] + '</label>';
                                document.getElementById("savedrecord").innerHTML = str1;
                                document.getElementById("errorrecord").innerHTML = str2;
                                $("#save").val(data.analyzeexceldata[0][1]);
                                $("#error").val(data.analyzeexceldata[0][2]);
                                $.each(data.analyzeexceldata[0][3], function (i, item)
                                {
                                    str += '<tr>';
                                    str += '<td>' + item[0] + '</td>';
                                    str += '<td>' + item[1] + '</td>';
                                    str += '<td>' + item[2] + '</td>';
                                    str += '<td>' + item[3] + '</td>';
                                    str += '<td>' + item[4] + '</td>';
                                    str += '<td>' + item[5] + '</td>';
                                    str += '<td style="font-weight:bold">' + item[6] + '</td>';
                                    str += '</tr>';
                                    count.push(cou++);
                                });

                                $("#errorList").append(str);

                                if (data.analyzeexceldata[0][0] && data.analyzeexceldata[0][4]) {
                                    BootstrapDialog.alert(data.analyzeexceldata[0][0] + " <br> " + data.analyzeexceldata[0][4]);
                                } else {
                                    BootstrapDialog.alert(data.analyzeexceldata[0][0]);
                                }
                            } else {
                                alert("hello");
                                BootstrapDialog.alert(data);
                            }
                        }
                    });
                } else {
                    BootstrapDialog.alert("Please Load Excel file data first!");
                }
            } else {
                BootstrapDialog.alert("Please Choose File first!");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function report() {
        window.location.assign('bulkSubjectRegistration/report?excelfilename=report');
    }

    function saveData() {
        if ($("#regCode").val() != '') {
            var exlfile = $("#fileUpload").val();
            if (exlfile != '') {
                var total = $("#total").val();
                if (total != '') {
                    var save = $("#save").val();
                    var error = $("#error").val();
                    if (save != '' && error != '') {
                        $.ajax({
                            url: "bulkSubjectRegistration/saveAndDeleteExcelData",
                            dataType: 'json',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            data: "userid=" + $("#userid").val() + "&taskid=" + $("#taskid").val(),
                            error: (function (response) {
                                alert('Server Error' + response);
                            }),
                            success: function (data) {
                                if (data.savedata != null) {
                                    BootstrapDialog.alert(data.savedata[0][0]);
                                } else {
                                    BootstrapDialog.alert(data);
                                }
                            }
                        });
                    } else {
                        BootstrapDialog.alert("Please Validate Excel first!");
                    }
                } else {
                    BootstrapDialog.alert("Please Load Excel File data first!");
                }
            } else {
                BootstrapDialog.alert("Please Choose File first!");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }
    function deleteExcelData(v) {
        if ($("#regCode").val() != '') {
            $.ajax({
                url: "bulkSubjectRegistration/saveAndDeleteExcelData1",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "userid=" + $("#userid").val() + "&taskid=" + $("#taskid").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.deleteData != null) {
                        debugger;
                        if (v == 1) {
                            BootstrapDialog.alert(data.deleteData[0][0]);
                        }
                    } else {
                        BootstrapDialog.alert(data);
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

</script>

