<%-- 
    Document   : subjectWiseStudentListReport
    Created on : Jan 19, 2019, 12:28:16 PM
    Author     : ankit.kumar
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
                    <div class="row col-lg-12 form-group" id="divContainer"> 
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="reg_id" name="reg_id" data-live-search="true" data-validation="required" onchange="departmentCode();">               
                                        <option value="">Select</option>
                                        <c:forEach items="${regList}" var="list">
                                            <option value="${list[0]},${list[2]}"><c:out value="${list[1]} / ${list[2]}"/></option>
                                        </c:forEach> 
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Department Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="dept_id" name="dept_id" data-live-search="true" data-validation="required" onchange="employeeCode();">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-5 col-md-3 control-label">Employee Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="emp_id" name="emp_id" data-live-search="true" data-validation="required">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6">
                                    <button type="button" class="fa fa-search btn btn-success" onclick="getSubectListData();"></button>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12" style="margin-top:200px"></div>
                        <div class="col-lg-12 container">
                            <table id="itemList" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Sl No.</th>
                                        <th>Subject Code</th>
                                        <th>Subject Name</th>
                                        <th>Subject Component</th>
                                        <th>No Of Student</th>
                                        <th>Export Excel</th>
                                    </tr>
                                </thead>  
                                <tbody>
                                </tbody>
                            </table>
                        </div> 
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat">Export All Subject </a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#reg_id").val('').trigger("chosen:updated");
        $("#dept_id").val('').trigger("chosen:updated");
        $("#emp_id").val('').trigger("chosen:updated");
    }
    $("#reg_id").chosen();
    $("#dept_id").chosen();
    $("#emp_id").chosen();
    $('#itemList').DataTable({
        searching: false, paging: false, info: false
    });
    function employeeCode() {
        $("#emp_id").val('').trigger("chosen:updated");
        $("#emp_id").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListReport/getEmployeeCode",
            data: '&dept_id=' + $("#dept_id").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.emp_list != null && data.emp_list != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.emp_list, function (i, item)
                    {
                        str1 += '<option value="' + item[0]+ ',' + item[1] + ',' + item[2] + '">' + item[1] + ' / ' + item[2] + '</option>'
                    });
                    $("#emp_id").append(str1);
                    $("#emp_id").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found...");
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }



    function departmentCode() {
        $("#dept_id").val('').trigger("chosen:updated");
        $("#emp_id").val('').trigger("chosen:updated");
        $("#dept_id").empty();
        $.ajax({
            type: "POST",
            url: "subjectWiseStudentListReport/getDepartmentCode",
            data: '&reg_id=' + $("#reg_id").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.dep_list != null && data.dep_list != '')
                {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.dep_list, function (i, item)
                    {
                        str1 += '<option value="' + item.departmentid + ',' + item.department + '">' + item.departmentcode + ' / ' + item.department + '</option>'
                    });
                    $("#dept_id").append(str1);
                    $("#dept_id").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Data Found...");
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }


    function getSubectListData() {
        var reg_id = ($("#reg_id").val());
        var dep_id = ($("#dep_id").val());
        var emp_id = ($("#emp_id").val());
        $('#itemList').DataTable().clear().draw();
        var itemChildList = $('#itemList').DataTable();
        if (reg_id != "" && dep_id != "" && emp_id != "") {
            $.ajax({
                type: "POST",
                url: "subjectWiseStudentListReport/getAllSubjectWiseStudentList",
                data: '&reg_id=' + $("#reg_id").val() + '&dep_id=' + $("#dep_id").val() + '&emp_id=' + $("#emp_id").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var j = 1;
                    if (data.sub_list != null && data.sub_list != '') {
                        $.blockUI();
                        $.each(data.sub_list, function (i, item)
                        {
                            var str = "'" + item[4] + "~@~" + item[5] + "'";
                            var str1 = '<tr id="' + item[4] + '~@~' + item[5] + '">';
                            str1 += '  <td>' + j + '</td>';
                            str1 += '  <td>' + item[0] + '</td>';
                            str1 += '  <td>' + item[1] + '</td>';
                            str1 += '  <td>' + item[2] + '</td>';
                            str1 += '  <td>' + item[3] + '</td>';
                            str1 += '  <td> <input type="button" class="btn btn-success btn-md" id="subjectRunning " name="subjectRunning"  value="Export Excel" onclick="javascript:getReport(' + str + ');"/> </td></tr>';
                            itemChildList.row.add($(str1)).draw();
                            j++;
                        });
                        setTimeout($.unblockUI, 3000);
                    } else {
                        BootstrapDialog.alert("No Data Found...");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Required Fields...");
        }
    }

    function getReport(id) {
        var table = $('#datatable').DataTable();
        if (table.data().any()) {
            var reg_id = $("#reg_id").val();
            var dept_id = $("#dept_id").val();
            var emp_id = $("#emp_id").val();
            var radioValue = $("input[name='export_To']:checked").val();
            if (reg_id != '' && dept_id != '' && emp_id != '') {
                $.blockUI();
                window.location.assign('subjectWiseStudentListReport/getReport?excelfilename=subjectWiseStudentListReport&dept_id=' + dept_id + '&emp_id=' + emp_id + '&reg_id=' + reg_id + '&sub_id=' + id + '&radioValue=' + radioValue + '');
                setTimeout($.unblockUI, 3000);
            } else {
                BootstrapDialog.alert("Please Select Required Fields !.");
            }
        } else {
            BootstrapDialog.alert("Grid Data Not Found !.");
        }
    }
</script>