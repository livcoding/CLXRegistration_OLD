<%-- 
    Document   : DuringCourseElectiveRegistartionList
    Created on : 15 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Program Code<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
            <select class="form-control" id="programId" name="programId" data-validation="required" onchange="getGridData();">
                <option value="">Select</option>
                <c:forEach items="${prog_list}" var="prog_list">
                    <option value="${prog_list[2]}"><c:out value="${prog_list[0]}"/>/<c:out value="${prog_list[1]}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr>
                    <th style="display:none"></th>
                    <th></th>
                    <th colspan="11" style="text-align:center">During Course Registration</th>
                    <th colspan="5"  style="text-align:center">During Elective Registration</th>
                </tr>
                <tr class="" >
                    <th style="display:none"></th>
                    <th>Sl.No</th>
                    <th>Credit To Count In Course Registration</th>
                    <th>Elective Type Subject Shown In Course</th>
                    <th>Elective Subject To Be Disabled </th>
                    <th>Show Back Paper In Course</th>
                    <th>Editable Back Paper In Course</th>
                    <th>Allow Not Offered Subjects</th>
                    <th>Default Selected Option For Back Papers </th>
                    <th>Back Paper Selection Mandatory</th>
                    <th>Printing Of Course Registration</th>
                    <th>Student Fee Dues Checking</th>
                    <th>Back Paper Fee Calculation</th>
                    <th>Credit To Counted In Elective Registration</th>
                    <th>Elective Type Subject Shown In Elective</th>
                    <th>Type Of Electives Subjects To Be Kept Disable In Elective</th>
                    <th>During Elective Subject Choices Preference Can Be Modified</th>
                    <th>Printing Of Elective Preferences Is Allowed</th>
                </tr>
            </thead>  
            <tbody>
            </tbody>
        </table>
    </div> 


</form>
<script>

    $("#programId").chosen();

    var cb = {
        add: {
            link: "duringCourseElectiveRegistration/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "duringCourseElectiveRegistration/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "duringCourseElectiveRegistration/delete",
            name: "",
            value: ""
        },
        list: {
            link: "duringCourseElectiveRegistration/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    commonButtonMethod(cb);

    function getGridData() {
        var programId = ($("#programId").val());
        $('#datatable').DataTable().clear().draw();
        if (programId != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "duringCourseElectiveRegistration/gerGridData",
                data: '&programId=' + $("#programId").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.gridData != null && data.gridData != '') {
                        $.each(data.gridData, function (i, item)
                        {
                            var str1 = '<tr id="' + item[16] + ':' + item[18] + '">';
                            str1 += '<th style="display:none"></th>';
                            str1 += '  <td>' + (i + 1) + '</td>';
                            str1 += '  <td>' + item[0] + '</td>';
                            str1 += '  <td>' + item[1] + '</td>';
                            str1 += '  <td>' + item[2] + '</td>';
                            str1 += '  <td>' + item[3] + '</td>';
                            str1 += '  <td>' + item[4] + '</td>';
                            str1 += '  <td>' + item[5] + '</td>';
                            str1 += '  <td>' + item[6] + '</td>';
                            str1 += '  <td>' + item[7] + '</td>';
                            str1 += '  <td>' + item[8] + '</td>';
                            str1 += '  <td>' + item[9] + '</td>';
                            str1 += '  <td>' + item[10] + '</td>';
                            str1 += '  <td>' + item[11] + '</td>';
                            str1 += '  <td>' + item[12] + '</td>';
                            str1 += '  <td>' + item[13] + '</td>';
                            str1 += '  <td>' + item[14] + '</td>';
                            str1 += '  <td>' + item[15] + '</td>';
                            str1 += '</tr>';
                            tablelist.row.add($(str1)).draw();
                        });
                    } else {
                        BootstrapDialog.alert("No Data Found..");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
            setTimeout($.unblockUI, 2000);
        } else {
            BootstrapDialog.alert("Please Select program Code.");
        }
    }

</script>
