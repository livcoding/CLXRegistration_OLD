<%-- 
    Document   : phdSubjectFinalizationEdit
    Created on : Dec 19, 2018, 12:00:14 PM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform4" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">        
        <div class="col-lg-12 container">
            <table id="itemListApproved4" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th>Sl.No</th>
                        <th>Subject Code</th>
                        <th>Subject Description</th>
                        <th>Credit 1</th>
                        <th>Credit 2</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 
    </div> 
    <input type="hidden" name="countval4" id="countval4" value=""/>
    <input type="hidden" name="checkedCount4" id="checkedCount4" value=""/>
</form>

<script>

    $('#itemListApproved4').DataTable({
        searching: true, paging: true, info: true
    });

    function getSubjectReportData() {
        $('#itemListApproved4').DataTable().clear().draw();
        var itemChildList = $('#itemListApproved4').DataTable();
        $.ajax({
            type: "POST",
            url: "phdSubjectFinalization/getReportData",
            data: '&registrationid=' + $("#registrationid").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var j = 1;
                var table = $('#datatable').DataTable();
                if (data.getReportData != null) {
                    $.each(data.getReportData, function (i, item)
                    {
                        var str1 = '<tr>';
                        str1 += '  <td>' + j + '</td>';
                        str1 += '  <td>' + item[1] + '</td>';
                        str1 += '  <td>' + item[2] + '</td>';
                        str1 += '  <td>' + item[3] + '</td>';
                        str1 += '  <td>' + item[4] + '</td></tr>';
                        itemChildList.row.add($(str1)).draw();
                        j++;
                    });
                } else {
                    BootstrapDialog.alert("Data not found for this Semester Code...");
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
</script>