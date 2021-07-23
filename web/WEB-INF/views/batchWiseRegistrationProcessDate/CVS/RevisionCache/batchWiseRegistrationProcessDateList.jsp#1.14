<%-- 
    Document   : batchWiseRegistrationProcessDateList
    Created on : Feb 20, 2019, 12:07:38 PM
    Author     : ashutosh1.kumar
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
                    <div class="row col-lg-12 form-group" >
                        <div class="row col-lg-6 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Semester Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                <select class="form-control" id="regCode" name="regCode" onchange="getProgramAndAcademic(this.value);" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                    <c:forEach items="${data}" var="reList">
                                        <option value="${reList[0]}"><c:out value="${reList[1]}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>  
                        <div class="row col-lg-6 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Academic Year:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                <select class="form-control" id="acadYear" name="acadYear" multiple="true"  data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </div>  
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <div class="row col-lg-6 form-group">
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Program Code:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6">
                                <select class="form-control" id="program" name="program" multiple="true" onchange="resetTable();" data-validation="required" data-live-search="true">               
                                    <option value="">Select</option>
                                </select>
                            </div>
                            <div class="col-sm-2 col-lg-2 col-xs-2 col-md-2">         
                                <a type="button" style="display:block" value="Load Branch" class="btn btn-success btn-sm" onclick="loadData();">Load</a>                           
                            </div>
                        </div>  
                        <div class="row col-lg-6  form-group" >
                            <div class="col-lg-3"></div>
                            <div class="col-lg-9">
                                <label class="radio-inline"> &nbsp;&nbsp; <input type="radio" name="basedon" id="general" checked="true" onclick="loadData();" value="G" style="display:inline-block"> &nbsp;General (Based on Registration Permission)</label>  
                                <br> <label class="radio-inline">&nbsp;&nbsp; <input type="radio" name="basedon" id="firstyear" onclick="loadData();" value="F" style="display:inline-block"> &nbsp;First Year (Based on PST)</label>  
                            </div>
                        </div>
                        <!--                        <div class="row col-lg-6 form-group">
                                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Branch Code:
                                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6">
                                                        <select class="form-control" id="branch" name="branch" multiple="true" onchange="resetTable();" data-validation="required" data-live-search="true">               
                                                            <option value="">Select</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-2 col-lg-2 col-xs-2 col-md-2">                                    
                                                        <a href="javascript:;" class="btn btn-success fa fa-search"></a>
                                                    </div>
                                                </div>  -->
                    </div>
                    <div class="col-lg-12" style="margin-top:10px;">
                        <div id="specialCase_Button" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                        <table id="itemListChoice" class=" table table-hover table-condensed table-bordered" cellspacing="0">
                            <thead id="header">
                                <tr class="" style="background-color:#008080;color:white;" >
                                    <th  width="1px;" style="display:none"></th>
                                    <th  width="1px;">Sl.No</th>
                                    <th>Academic Year</th>
                                    <th  width="5px;">Program</th>
                                    <th  width="5px;">Branch</th>
                                    <th  style="width:120px;">Reg. Start Date</th>
                                    <th  style="width:120px;">Reg. End Date</th>
                                    <th  style="width:120px;">Extended Upto Date</th>
                                    <th style="width:80px;">Reg. Late Fee</th>
                                    <th style="width:222px;">Remarks</th>
                                </tr>
                            </thead>  
                            <tbody>
                            </tbody>
                        </table>
                        <div class="col-lg-12 form-group" id="save" style="display:none">              
                            <div  class="form-group pull-right">
                                <a href="javascript:saveAcadWiseRegistration();" class="btn btn-success " style="width:130px">Save</a>
                            </div>
                        </div>
                    </div>
                    <input type="hidden"   name="countval" id="countval" value=""/>
                </form>
            </div> 
        </div> 
    </div> 
</div> 
<script>
    $("#regCode").chosen();
    $("#acadYear").chosen();
    $("#program").chosen();
    var btns = [];
    table1 = $('#itemListChoice').DataTable({
        "lengthMenu": [[-1], ["All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    btns.push({
        extend: 'excel',
        // mColumns: 'visible',
        text: '<i class=\"fa fa-lg fa-file-excel-o\"></i>',
        exportOptions: {
            //            columns: ':visible'
            modifier: {
                page: 'all'
            }
        }
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table1, {
                    buttons: btns
                });
                $('#specialCase_Button').append(table1.buttons(0, null).container());
            }, 100);

    function resetTable() {
        $('#itemListChoice').DataTable().clear().draw();
        $("#save").hide();
    }

    function getProgramAndAcademic(id) {
        $("#acadYear").empty();
        $("#acadYear").val('a').trigger("chosen:updated");
        $("#program").empty();
        $("#save").hide();
        $('#itemListChoice').DataTable().clear().draw();
        if (id != '') {
            $.ajax({
                url: "batchWiseRegistrationProcessDate/getPrAndAcadYear",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regCode=" + id,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.acadyear != null) {
                        var str1 = '';
                        $.each(data.acadyear, function (i, item)
                        {
                            str1 += '<option value="' + item + '">' + item + '</option>'
                        });
                        $("#acadYear").append(str1);
                        $("#acadYear").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Academic Year Not Found, Please Select Another Semester...");
                    }
                    if (data.program != null) {
                        var str1 = '';
                        $.each(data.program, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                        });
                        $("#program").append(str1);
                        $("#program").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("program Not Found, Please Select Another Semester...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
        }
    }

    function loadData() {
        $("#countval").val('0');
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        var program = $("#program").val();
        var basedon = $("input[name='basedon']:checked").val();
        if (regCode != '') {
            if (acadYear != '') {
                if (program != '') {
                    $('#itemListChoice').DataTable().clear().draw();
                    $.blockUI();
                    $.ajax({
                        url: "batchWiseRegistrationProcessDate/getGridData",
                        dataType: 'json',
                        async: true,
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: "regCode=" + regCode + "&acadYear=" + acadYear + "&basedon=" + basedon + "&program=" + program,
                        error: (function (response) {
                            alert('Server Error' + response);
                        }),
                        success: function (data) {
                            if (data.gridData != null && data.gridData != '') {
                                var countt = 0;
                                debugger;
                                $.each(data.gridData, function (i, item)
                                {
                                    $("#save").show();
                                    countt++;
                                    var str2 = '';
                                    var str3 = '';
                                    var str4 = '';
                                    var str = '';
                                    var regamount = '';
                                    var str1 = '';
                                    var rmrks = '';
                                    var amt = '';
                                    var frdate = '';
                                    var tdate = '';
                                    var tildate = '';
                                    if (item[6] != null)
                                    {
                                        var todayTime = new Date(item[6].split(',')[0]);
                                        var month = todayTime.getMonth() + 1;
                                        var day = todayTime.getDate();
                                        var year = todayTime.getFullYear();
                                        var frdate = day + "/" + month + "/" + year;
                                    }
                                    if (item[6] != null)
                                    {
                                        var todayTime = new Date(item[6].split(',')[1]);
                                        var month = todayTime.getMonth() + 1;
                                        var day = todayTime.getDate();
                                        var year = todayTime.getFullYear();
                                        var tdate = day + "/" + month + "/" + year;
                                    }
                                    if (item[6] != null)
                                    {
                                        var todayTime = new Date(item[6].split(',')[2]);
                                        var month = todayTime.getMonth() + 1;
                                        var day = todayTime.getDate();
                                        var year = todayTime.getFullYear();
                                        var tildate = day + "/" + month + "/" + year;
                                    }
                                    if (item[6] != null)
                                    {
                                        rmrks = item[6].split(',')[4];
                                    }
                                    if (item[6] != null)
                                    {
                                        amt = item[6].split(',')[4];
                                    }
                                    str2 += '<div class="none" id="none"><div class="input-group date" id="fdatetimepicker1' + i + '" >';
                                    str2 += '<input type="text" class="form-control" value="' + frdate + '" id="fromdate' + i + '" name="fromdate' + i + '" style="width:100px" onblur="checkEndDate(' + i + ');"/>';
                                    str2 += ' <span class="input-group-addon">';
                                    str2 += '<span class="glyphicon glyphicon-calendar"></span>';
                                    str2 += '</span>';
                                    str2 += '</div></div>';
                                    str3 += '<div class="input-group date" id="tdatetimepicker2' + i + '">';
                                    str3 += '<input type="text" class="form-control" value="' + tdate + '" id="todate' + i + '" name="todate' + i + '" style="width:100px" onblur="checkStartDate(' + i + ');checkExtendedDate(' + i + ');"/>';
                                    str3 += ' <span class="input-group-addon">';
                                    str3 += '<span class="glyphicon glyphicon-calendar"></span>';
                                    str3 += '</span>';
                                    str3 += '</div>';
                                    str4 += '<div class="input-group date" id="tldatetimepicker3' + i + '">';
                                    str4 += '<input type="text" class="form-control" value="' + tildate + '" id="tilldate' + i + '" name="tilldate' + i + '"  style="width:100px"onblur="checkStartEndDate(' + i + ');"/>';
                                    str4 += ' <span class="input-group-addon">';
                                    str4 += '<span class="glyphicon glyphicon-calendar"></span>';
                                    str4 += '</span>';
                                    str4 += '</div>';
                                    str += '<input type="text"  style="width:100%;"name="remarks' + i + '" id="remarks' + i + '"  value="' + rmrks + '" class="form-control" placeholder="Enter Remarks" />';
                                    regamount += '<input type="text"  style="width:80px;"name="reglatefee' + i + '" id="reglatefee' + i + '"  value="' + amt + '" class="form-control" data-validation="number" data-validation-optional="true" placeholder="Enter Reg Late Fee" />';
                                    str1 += ' <tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[3] + '">';
                                    str1 += '<td style="display:none;" id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[3] + '"><input type="hidden" class="case" checked="true" name="chk' + i + '" id="chk' + i + '" value="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[3] + '"/></td>';
                                    str1 += ' <td>' + countt + '</td>';
                                    str1 += ' <td style="width:120px;">' + item[3] + '</td>';
                                    str1 += ' <td>' + item[4] + '</td>';
                                    str1 += ' <td>' + item[5] + '</td>';
                                    str1 += ' <td>' + str2 + '</td>';
                                    str1 += ' <td>' + str3 + '</td>';
                                    str1 += ' <td>' + str4 + '</td>';
                                    str1 += ' <td style="width:80px;">' + regamount + '</td>';
                                    str1 += ' <td style="width:220px;">' + str + '</td>';
                                    str1 += ' </tr>';
                                    table1.row.add($(str1)).draw();
                                    $("#countval").val(countt);
                                    $('#fdatetimepicker1' + i + '').datetimepicker({
                                        format: 'DD/MM/YYYY'
                                    });
                                    $('#tdatetimepicker2' + i + '').datetimepicker({
                                        useCurrent: false,
                                        format: 'DD/MM/YYYY'
                                    });
                                    $('#tldatetimepicker3' + i + '').datetimepicker({
                                        useCurrent: false,
                                        format: 'DD/MM/YYYY'
                                    });
                                });
                                setTimeout($.unblockUI, 1000);
                                $("#itemListChoice").removeClass("dataTable");
                            } else {
                                BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                                $("#save").hide();
                            }
                        }
                    });
                } else {
                    BootstrapDialog.alert("Please Select Program Code...");
                }
            } else {
                BootstrapDialog.alert("Please Select Academic Year.");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code");
        }
        setTimeout($.unblockUI, 1000);
    }


    function saveAcadWiseRegistration() {
        var total = $("#countval").val();
        var flag = true;
        for (var i = 0; i < total; i++) {
            if ($("#fromdate" + i).val() == '' || $("#todate" + i + "").val() == '') {
                flag = false;
            }
        }
        if (flag) {
            $("#ajaxform").submit();
        } else {
            BootstrapDialog.alert("Please select Reg.Start date and Reg.End first!");
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
                                $.ajax({
                                    type: "POST",
                                    url: "batchWiseRegistrationProcessDate/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("batchWiseRegistrationProcessDate/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);
    function checkEndDate(i) {
        var start_date = $("#fromdate" + i + "").val().split("/");
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#todate" + i + "").val().split("/");
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#todate" + i + "").val() != '') {
            if ($("#fromdate" + i + "").val() != '') {
                if (fromYear > toYear) {
                    $("#fromdate" + i + "").val('');
                    BootstrapDialog.alert("Please select another date less than the End date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#fromdate" + i + "").val('');
                        BootstrapDialog.alert("Please select another date less than the End date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#fromdate" + i + "").val('');
                            BootstrapDialog.alert("Please select another date less than the  End date!");
                        }
                    }
                }

            } else {
                $("#fromdate" + i + "").val('');
                BootstrapDialog.alert("Please select Start date first!");
            }
        }
    }

    function checkStartDate(i) {
        var start_date = $("#fromdate" + i + "").val().split("/");
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#todate" + i + "").val().split("/");
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        debugger;
        if ($("#todate" + i + "").val() != '') {
            if ($("#fromdate" + i + "").val() != '') {
                if (fromYear > toYear) {
                    $("#todate" + i + "").val('');
                    BootstrapDialog.alert("Please select another date greater than the Reg. Start date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#todate" + i + "").val('');
                        BootstrapDialog.alert("Please select another date greater than the Reg. Start date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#todate" + i + "").val('');
                            BootstrapDialog.alert("Please select another date greater than the Reg. Start date!");
                        }
                    }
                }

            } else {
                $("#todate" + i + "").val('');
                BootstrapDialog.alert("Please select Reg. Start date first!");
            }
        }
    }
    function checkStartEndDate(i) {
        var till_date = $("#tilldate" + i + "").val().split("/");
        var tillYear = till_date[2];
        var tillMonth = till_date[1];
        var tillDate = till_date[0];
        var end_date = $("#todate" + i + "").val().split("/");
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#fromdate" + i + "").val() != '') {
            if ($("#todate" + i + "").val() != '') {
                if (tillYear < toYear) {
                    $("#tilldate" + i + "").val('');
                    BootstrapDialog.alert("Please select another date greater than the  Reg. End date!");
                } else if (tillYear == toYear) {
                    if (tillMonth < toMonth) {
                        $("#tilldate" + i + "").val('');
                        BootstrapDialog.alert("Please select another date greater than the Reg. End date!");
                    } else if (tillMonth == toMonth) {
                        if (tillDate < toDate) {
                            $("#tilldate" + i + "").val('');
                            BootstrapDialog.alert("Please select another date greater than the  Reg. End date!");
                        }
                    }
                }

            } else {
                BootstrapDialog.alert("Please select Reg. End date first!");
                $("#tilldate" + i + "").val('');
            }
        } else {
            BootstrapDialog.alert("Please select Reg. Start date first!");
            $("#tilldate" + i + "").val('');
        }
    }

    function checkExtendedDate(i) {
        var till_date = $("#tilldate" + i + "").val().split("/");
        var tillYear = till_date[2];
        var tillMonth = till_date[1];
        var tillDate = till_date[0];
        var end_date = $("#todate" + i + "").val().split("/");
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#fromdate" + i + "").val() != '') {
            if ($("#todate" + i + "").val() != '') {
                if (tillYear < toYear) {
                    $("#todate" + i + "").val('');
                    BootstrapDialog.alert("Please select another date less than the  Reg. Extended Upto date!");
                } else if (tillYear == toYear) {
                    if (tillMonth < toMonth) {
                        $("#todate" + i + "").val('');
                        BootstrapDialog.alert("Please select another date less than the Reg. Extended Upto date!");
                    } else if (tillMonth == toMonth) {
                        if (tillDate < toDate) {
                            $("#todate" + i + "").val('');
                            BootstrapDialog.alert("Please select another date less than the  Reg. Extended Upto date!");
                        }
                    }
                }

            } else {
                BootstrapDialog.alert("Please select Reg. End date first!");
                $("#tilldate" + i + "").val('');
            }
        } else {
            BootstrapDialog.alert("Please select Reg. Start date first!");
            $("#tilldate" + i + "").val('');
        }
    }

</script>

