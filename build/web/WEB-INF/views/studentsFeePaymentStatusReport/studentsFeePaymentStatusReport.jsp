<%-- 
    Document   : studentsFeePaymentStatusReport
    Created on : Dec 24, 2019, 12:42:17 PM
    Author     : priyanka.tyagi
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
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
                    <form id="ajaxform" class="form-horizontal">
                        <div class="row col-lg-12 form-group" id="">
                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Institute:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="multiinstituteid" multiple="true" name="multiinstituteid" data-validation="required"data-live-search="true" onchange="">               
                                            <c:forEach items="${institutelist}" var="institutelist">
                                                <c:if test="${maininstituteid==institutelist[0]}">
                                                    <option value="${institutelist[0]}" selected="true"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                                </c:if>
                                                <c:if test="${maininstituteid!=institutelist[0]}">
                                                    <option value="${institutelist[0]}"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label"> Registration Type:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="row col-lg-8 form-group" style="margin-top:8px">
                                    <input type="radio" id="greg_Type" name="reg_Type" value="G" onchange ="clearTable(); getRegCode();" class="radio-inline"/> GIP - Grade Improvements
                                    &nbsp; &nbsp; &nbsp; <input type="radio" id="preg_Type" name="reg_Type"  value="P" class="radio-inline" onchange ="clearTable(); getRegCode();"/> Supplimentory
                                    &nbsp; &nbsp; &nbsp; <input type="radio" id="sreg_Type" name="reg_Type"  value="S" class="radio-inline" onchange ="clearTable(); getRegCode();"/> Condensed/Summer Exam
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">    
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="row col-lg-3 col-md-6 col-sm-6  form-group" style="margin-top:8px">
                                    <select class="form-control" id="regCode" name="regCode" onchange="setRegDesc(this.value); clearTable();" data-live-search="true" data-validation="required">               
                                    </select>
                                </div>
                                <div class="row col-lg-5  col-md-6 form-group" style="margin:auto;margin-top:8px;">
                                    <input type="text" name="regDesc" id="regDesc" readonly="true" onchange ="clearTable();" style="width:80%;"/> 
                                </div>
                            </div>

                            <div class="row col-lg-12 form-group"> 
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-2 col-xs-3 col-md-3 control-label"> Order By:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">&nbsp;</span></label>
                                <div class="row col-lg-8 form-group " style="margin-top:8px">   
                                    <input type="radio" id="Order_By" name="Order_By" value="En" checked="true" class="radio-inline" onchange ="clearTable();"/> Enrollment No.
                                    &nbsp;  &nbsp;  &nbsp; <input type="radio" id="Order_By" name="Order_By"  value="St" class="radio-inline" onchange ="clearTable();"/>  Student Name
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div  class="col-sm-1 col-lg-2 col-xs-1 col-md-1 control-label" style="text-align:right;">
                                    <input type="checkbox" id="duestudent" name="duestudent" checked="true" onchange ="clearTable();"/>  
                                </div>
                                <div  class="col-sm-11 col-lg-10 col-xs-11 col-md-11 control-label" style="text-align:left;">
                                    <label style="text-transform: capitalize;text-align:left" >Show students having Due only</label>
                                </div>
                                <div  class="col-sm-1 col-lg-2 col-xs-1 col-md-1 control-label" style="text-align:right;">
                                    <input type="checkbox" id="lesssubject" name="registeredstudents" onchange ="clearTable();"/>
                                </div>
                                <div  class="col-sm-11 col-lg-10 col-xs-11 col-md-11 control-label" style="text-align:left;">
                                    <label style="text-transform: capitalize;text-align:left" style="text-align:left;">Show students who have registered less subjects but Paid Excess Subjects</label>
                                </div>
                                <div  class="col-sm-1 col-lg-2 col-xs-1 col-md-1 control-label" style="text-align:right;">
                                    <input type="checkbox" id="exceedsubject" name="includestudents" onchange ="clearTable();"/>
                                </div>
                                <div  class="col-sm-11 col-lg-10 col-xs-11 col-md-11 control-label" style="text-align:left;">
                                    <label style="text-transform: capitalize;text-align:left" style="text-align:left;">Include Students who have registered excess subjects but Paid less</label>
                                </div>
                            </div> 
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                <a onclick="getGridData()" class="btn btn-success btn-sm btn-flat">Load Data</a>
                                <!--<button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button>--> 
                                <!--<a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>-->
                            </div>
                        </div>
                        <div class="col-lg-12" style="margin-top:10px;overflow-x: auto" >
                            <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                            <div class="col-lg-12" style="overflow-x: auto" >
                                <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                    <thead id="header">
                                        <tr class="" >
                                            <th >Sl.No.</th>
                                            <th>Enrollment No.</th>
                                            <th>Name</th>
                                            <th>Mobile No.</th>
                                            <th>EmailID</th>
                                            <th>No. of Subjects</th>
                                            <th>Subject Codes</th>
                                            <th>Fee Amount</th>
                                            <th>paid Amount</th>
                                            <th>Due Amount</th>
                                            <th>Special Approval</th>
                                            <th>Registration Status</th>
                                            <th>Registered Subjects</th>
                                        </tr>
                                    </thead>
                                    <tbody id="clearGrid">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <input type="hidden" id="instituteid" name="instituteid"class="form-control" readonly="true">
                        <input type="hidden" id="registrationid" name="registrationid"class="form-control" readonly="true">
                        </div>
                        </div>
                    </form>
                    <script>

                        $("#regCode").chosen({width: '100%'});
                        $("#multiinstituteid").chosen({width: '100%'});
                        $(document).ready(function () {
                            $('#datatable').DataTable().clear().draw();
                        });

                        function setRegDesc(id) {
                            $("#regDesc").val('');
                            var ids = id.split("~@~");
                            $("#regDesc").val(ids[1]);
                        }

                        var btns = [];
                        table1 = $('#datatable').DataTable({
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
                                    $('#button_wrapper').append(table1.buttons(0, null).container());
                                }, 100);
                        function clearTable() {
                            $("#clearGrid").empty();
                        }

                        function getGridData() {
                            $('#datatable').DataTable().clear().draw();
                            var tablelist = $('#datatable').DataTable();
                            var Order_By = $("#Order_By").val();
                            var multiinstituteid = $("#multiinstituteid").val();
                            var registrationid = $("#regCode").val().split('~@~')[0];
                            var reg_Type = $("input[name='reg_Type']:checked").val();
                            var duestudent = '';
                            var lesssubject = '';
                            var exceedsubject = '';
                            if ($("#duestudent").prop('checked') == true) {
                                duestudent = 'Y';
                            } else {
                                duestudent = 'N';
                            }
                            if ($("#lesssubject").prop('checked') == true) {
                                lesssubject = 'Y';
                            } else {
                                lesssubject = 'N';
                            }
                            if ($("#exceedsubject").prop('checked') == true) {
                                exceedsubject = 'Y';
                            } else {
                                exceedsubject = 'N';
                            }
//                            debugger;
                            if (registrationid != '') {
                                $("#button_wrapper").show();
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "studentsFeePaymentStatusReport/getGridData",
                                    data: '&registrationid=' + registrationid +
                                            '&institutelist=' + multiinstituteid +
                                            '&reg_Type=' + reg_Type +
                                            '&Order_By=' + Order_By +
                                            '&duestudent=' + duestudent +
                                            '&lesssubject=' + lesssubject +
                                            '&exceedsubject=' + exceedsubject,
                                    dataType: "json",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        $('#datatable').DataTable().clear().draw();
                                        var count = 1;
                                        $.each(data.gridData, function (i, item)
                                        {
                                            var mobno = '';
//                                            debugger;
                                            if (item[4] != null) {
                                                mobno = item[4];
                                            } else {
                                                mobno = '--';
                                            }
                                            var nam = '';
                                            if (item[1] != null) {
                                                nam = item[1];
                                            } else {
                                                nam = '--';
                                            }
                                            var enroll = '';
                                            if (item[0] != null) {
                                                enroll = item[0];
                                            } else {
                                                enroll = '--';
                                            }
                                            var email = '';
                                            if (item[2] != null) {
                                                email = item[2];
                                            } else {
                                                email = '--';
                                            }
                                            var nsub = '';
                                            if (item[5] != null) {
                                                nsub = item[5];
                                            } else {
                                                nsub = '--';
                                            }
                                            var regstatus = '';
                                            if (item[3] != null) {
                                                if (item[3] == 'Y')
                                                    regstatus = 'YES';
                                                regstatus = 'No';
                                            } else {
                                                regstatus = '--';
                                            }
                                            var feeamount = '';
                                            if (item[6] != null) {
                                                feeamount = item[6];
                                            } else {
                                                feeamount = '--';
                                            }
                                            var paidamount = '';
                                            if (item[7] != null) {
                                                paidamount = item[7];
                                            } else {
                                                paidamount = '--';
                                            }
                                            var dueamount = '';
                                            if (item[8] != null) {
                                                dueamount = item[8];
                                            } else {
                                                dueamount = '--';
                                            }
                                            var approval = '';
                                            if (item[9] != null) {
                                                if (item[9] == 0) {
                                                    approval = 'No';
                                                } else {
                                                    approval = 'Yes';
                                                }
                                            } else {
                                                approval = '--';
                                            }

                                            var str1 = '<tr id="' + item[0] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[12] + '~@~' + item[1] + '~@~' + item[11] + '~@~' + item[2] + '">';
                                            str1 += '  <td>' + count + '</td>'; //Sno.
                                            str1 += '  <td>' + enroll + '</td>'; //Enrollment No.
                                            str1 += '  <td>' + nam + '</td>'; //Name
                                            str1 += '  <td>' + mobno + '</td>'; //Mobile No.
                                            str1 += '  <td>' + email + '</td>'; //EmailID
                                            str1 += '  <td>' + nsub + '</td>'; //No. of Subjects
                                            var subcode = '';
                                            var x = 0;
                                            $.each(data.subCode, function (i, item1)
                                            {
                                                if (item1[0] == item[10]) {
                                                    if (x == 0)
                                                        subcode += item1[1];
                                                    else
                                                        subcode += ', ' + item1[1];
                                                    x++;
                                                }
                                            });
                                            str1 += '  <td>' + subcode + '</td>'; //Subject Codes
                                            str1 += '  <td>' + feeamount + '</td>'; //Fee Amount
                                            str1 += '  <td>' + paidamount + '</td>'; //paid Amount
                                            str1 += '  <td>' + dueamount + '</td>'; //Due Amount
                                            str1 += '  <td>' + approval + '</td>'; //Special Approval 
                                            str1 += '  <td>' + regstatus + '</td>'; //Registration Status
                                            var regsub = '';
                                            var y = 0;
                                            $.each(data.regSub, function (i, item2)
                                            {
                                                if (item2[0] == item[10]) {
                                                    if (y == 0)
                                                        regsub += item2[1];
                                                    else
                                                        regsub += ', ' + item2[1];
                                                    y++;
                                                }
                                            });
                                            str1 += '  <td>' + regsub + '</td>'; //Registered Subjects 
                                            str1 += '  </tr>';
                                            tablelist.row.add($(str1)).draw();
                                            count++;
                                        });
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong...............', "Warning!!");
                                        setTimeout($.unblockUI, 1000);
                                    }
                                });
                            } else {
                                BootstrapDialog.alert("Please Select Semester Code!");
                            }
                        }


                        function getRegCode() {
                            $("#regDesc").val('');
                            $("#regCode").empty().trigger("chosen:updated");
                            var reg_Type = $("input[name='reg_Type']:checked").val();
                            var ids =  $("#multiinstituteid").val();
                            $.ajax({
                                type: "POST",
                                url: "studentsFeePaymentStatusReport/getSemesterCode",
                                data: '&instituteids=' + ids + '&reg_Type=' + reg_Type ,
                                dataType: "json",
                                async: false,
                                timeout: 5000,
                                cache: false,
                                beforeSend: function (xhr, options) {
                                    return true;
                                },
                                success: function (data) {
                                    var str1 = '<option value="">Select</option>';
                                    $.each(data.semCode, function (i, item)
                                    {
                                        str1 += '<option value="' + item[2] + '~@~' + item[3] +  '">' + item[1] + ' -- ' + item[3] +  '</option>';
                                    });
                                    $("#regCode").append(str1);
                                    $("#regCode").trigger("chosen:updated");
                                },
                                error: function (response) {
                                    toastr.warning('Something Wrong...............', "Warning!!");
                                }
                            });
                        }


                    </script>
