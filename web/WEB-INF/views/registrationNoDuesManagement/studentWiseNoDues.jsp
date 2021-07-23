<%-- 
    Document   : studentWiseNoDues
    Created on : Feb 12, 2019, 4:05:38 PM
    Author     : ashutosh1.kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform1" class="form-horizontal">
    <div class="row col-lg-12" style="margin-top:10px;"> 
        <div class="row col-lg-12 form-group" style="border: 1px solid;padding-bottom:10px; margin-left: 15px; margin-top: -15px;">
            <div class="row col-lg-12 form-group"  style="background: #cccccc;"><label style="text-transform: capitalize; color: black; " class="col-sm-3 col-lg-8 col-xs-3 col-md-3 control-label"><b>Save Student Wise No Dues</b></label></div>
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Enrollment Number:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                        <select class="form-control" id="regNo" name="regNo" onchange="setStudentDetails(this.value);" data-validation="required" data-live-search="true" data-validation-error-msg="Please select enrollment number!">               
                            <option value="">Select</option>
                        </select>
                    </div>
                </div>  
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Name:</label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" id="name">
                    </div>
                </div>  
            </div>
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Program(Branch):</label>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6" id="prbr">
                    </div>
                </div>  
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty No:</label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" id="stno">
                    </div>
                </div>  
            </div>
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Activity Type:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                        <select class="form-control" id="activityType" name="activityType" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select activity type!">               
                            <c:forEach items="${activity}" var="activity">
                                <option value="${activity[0]}~@~${activity[1]}"><c:out value="${activity[1]}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>  
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Remarks:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                        <input type="text" name="remarks" id="remarks" maxlength="300" data-validation="required" data-validation-help="Max Length is 300"  class="form-control" placeholder="Enter Remarks" data-validation-error-msg="Please enter remark!" />
                    </div>
                </div>  
            </div>        
            <div class="col-lg-12 form-group">              
                <div style="margin-right: 15px;" class="form-group pull-right">
                    <a href="javascript:saveStudentWiseActivity();" class=" btn buttons-html5 btn-success">Save</a>
                </div>
            </div>
        </div>
        <div class="col-lg-12" style="margin-top:10px;">            
            <div class="col-lg-12 form-group">              
                <div style="margin-right: 15px;" class="form-group pull-right">
                    <a href="javascript:deleteNoDuesActivity();" class="dt-button buttons-html5">Delete</a>&nbsp; &nbsp; 
                </div>
            </div>
            <div id="specialCase_Button" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
            <table id="itemListChoice" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead id="header">
                    <tr class="" >
                        <th  width="1px;"></th>
                        <th  width="1px;">Sl.No</th>
                        <th  width="10px;">Enrollment No.</th>
                        <th  width="20px;">Name</th>
                        <th  width="18px;">Program(Branch)/STY No.</th>
                        <th  width="10px;">Activity Type</th>
                        <th  width="10px;">Activity Status</th>
                        <th  width="10px;">Registration Permission</th>
                        <th  width="20px;">Remarks</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div>
        <input type="hidden"   name="countval" id="countval" value=""/>
    </div> 
</form>

<script>
    $("#regNo").chosen();
    $("#activityType").chosen();

    var btns = [];
    table1 = $('#itemListChoice').DataTable({
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
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



    function setStudentDetails(id) {
        $("#name").empty();
        $("#prbr").empty();
        $("#stno").empty();
        var ids = id.split("~@~");
        $("#name").append(ids[1]);
        $("#prbr").append(ids[3] + '(' + ids[4] + ')');
        $("#stno").append(ids[2]);
    }

    function getEnrollmentNo() {
        $("#regNo").empty();
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        var programid = $("#programid").val();
        if (regCode != '') {
            if (acadYear != '') {
                if (programid != '') {
                    $.blockUI();
                    $.ajax({
                        url: "registrationNoDuesManagement/getEnrollmentNo",
                        dataType: 'json',
                        async: true,
                        timeout: 6000,
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: "regCode=" + regCode + "&acadYear=" + acadYear + "&programid=" + programid,
                        error: (function (response) {
                            alert('Server Error' + response);
                        }),
                        success: function (data) {
                            if (data.studetail != null) {
                                var str1 = '';
                                var str1 = '<option value="">Select</option>';
                                $.each(data.studetail, function (i, item)
                                {
                                    str1 += '<option value="' + item[2] + '~@~' + item[1] + '~@~' + item[3] + '~@~' + item[4] + '~@~' + item[5] + '">' + item[0] + '</option>'
                                });
                                $("#regNo").append(str1);
                                $("#regNo").trigger("chosen:updated");
                            } else {
                                BootstrapDialog.alert("Enrollment Number Not Found, Please Select Another Semester...");
                            }
                            setTimeout($.unblockUI, 1000);
                        }
                    });
                } else {
                    BootstrapDialog.alert("Please Select Program Code...");
                }
            } else {
                BootstrapDialog.alert("Please Select Academic Year ...");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code...");
        }
    }

    function loadStudentWiseNoDuesData() {
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        var programid = $("#programid").val();
        if (regCode != '') {
            if (acadYear != '') {
                if (programid != '') {
                    $('#itemListChoice').DataTable().clear().draw();
                    $.blockUI();
                    $.ajax({
                        url: "registrationNoDuesManagement/getStudentWiseNoDuesGridData",
                        dataType: 'json',
                        async: true,
                        cache: false,
                        timeout: 5000,
                        contentType: false,
                        processData: false,
                        data: "regCode=" + regCode + "&acadYear=" + acadYear + "&programid=" + programid,
                        error: (function (response) {
                            alert('Server Error' + response);
                            setTimeout($.unblockUI, 1000);
                        }),
                        success: function (data) {
                            if (data.studentWiseData != null) {
                                var countt = 0;
                                $.each(data.studentWiseData, function (i, item)
                                {
                                    countt++;
                                    var str2 = '';
                                    var str = '';
                                    var remark = '';
                                    if (item[9] == 'N')
                                    {
                                        str2 = 'NO';
                                    } else {
                                        str2 = 'YES';
                                    }
                                    if (item[8] == 'N')
                                    {
                                        str = 'NO';
                                    } else {
                                        str = 'YES';
                                    }
                                    if (item[10] == '' || item[10] == null)
                                    {
                                        remark = '--';
                                    } else {
                                        remark = item[10];
                                    }
                                    var str1 = '';
                                    str1 += ' <tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '">';
                                    str1 += '<td id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '"/>';
                                    str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash_100980.png"  onclick="callChkBox(' + i + ');"/>';
                                    str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash_100984.png"  onclick="callChkBox(' + i + ');"/> </td>';
                                    str1 += ' <td>' + countt + '</td>';
                                    str1 += ' <td>' + item[3] + '</td>';
                                    str1 += ' <td>' + item[4] + '</td>';
                                    str1 += ' <td>' + item[6] + ' (' + item[7] + ') ' + '/' + item[5] + '</td>';
                                    str1 += ' <td>' + item[11] + '</td>';
                                    str1 += ' <td>' + str2 + '</td>';
                                    str1 += ' <td>' + str + '</td>';
                                    str1 += ' <td>' + remark + '</td>';
                                    str1 += ' </tr>';
                                    table1.row.add($(str1)).draw();
                                    $("#countval").val(countt);
                                });
                            } else {
                                BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                            }
                            setTimeout($.unblockUI, 1000);
                        }
                    });
                }
            }
        }
    }

    function saveStudentWiseActivity() {
        setFormIdAndUrl('ajaxform1', 'registrationNoDuesManagement/saveStudentWise', 1);
    }

    function callChkBox(index) {
        var value = $("#chk1" + index).val();
        if ($("#chk1" + index).prop('checked') == true) {
            $("#chk1" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk1" + index).prop("checked", true);
            checkChild(value, index);
        }
    }

    function checkChild(v, i) {
        $("#del_img" + i).hide();
        $("#d_img" + i).show();
    }

    function deleteNoDuesActivity() {
        var ids = '';
        var countval1 = $("#countval").val();
        var checkedStatus = 0;
        for (var j = 0; j < countval1; j++) {
            if ($('#chk1' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select at least one record for delete..");
        } else {
            for (var j = 0; j < countval1; j++) {
                if ($('#chk1' + j).is(':checked')) {
                    if (checkedStatus == 1) {
                        ids += $('#chk1' + j).val();
                    } else {
                        ids += $('#chk1' + j).val() + ',';
                    }
                }
            }
            $.ajax({
                url: "registrationNoDuesManagement/deleteNoDuesActivity",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "ids=" + ids,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    if (data.status === 'Success') {
                        toastr.success(Success['delete_success'], "Success !!");
//                        getEnrollmentNo();
                        loadStudentWiseNoDuesData();
//                        loadAcdWiseNoDuesData();
//                        loadForm("registrationNoDuesManagement/list");
                    } else if (data.status === 'Error') {
                        toastr.error(Error['error_code'], "Error!!");
                    } else {
                        toastr.error('Can Not Delete Record, Please try again.', "Error !!");
                    }
                }
            });
        }
    }


</script>
