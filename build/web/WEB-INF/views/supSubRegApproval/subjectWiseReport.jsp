<%-- 
    Document   : subjectWiseReport
    Created on : Feb 6, 2019, 11:03:47 AM
    Author     : ashutosh1.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform4" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">        
        <div class="col-lg-12 container">
            <table id="itemListreport" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr> 
                        <th width="50px;">Subject(Code:Description)</th>                       
                        <th width="25px;">No. Of Student Applied</th>
                        <th width="25px;">No. Of Student Approved</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 
    </div> 
</form>

<script>

    $('#itemListreport').DataTable({
        searching: true, paging: true, info: true
    });

    function loadReportData() {
        var regCode = $("#regCode").val();
        var styType = $("#styType").val();
        if (regCode != '') {
            $('#itemListreport').DataTable().clear().draw();
            var itemChildList = $('#itemListreport').DataTable();
            $.ajax({
                url: "supplementarySubjectRagistrationApproval/getReportData",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "regCode=" + regCode+ "&styType=" + styType,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    var countt = 1;
                    $.each(data.reportData, function (i, item)
                    {
                        var str1 = '';
                        str1 += ' <tr id="' + item[0] + '">';
                        str1 += ' <td>' + item[1] + '</td>';
                        str1 += ' <td>' + item[2] + '</td>';
                        str1 += ' <td>' + item[3] + '</td>';
                        str1 += ' </tr>';
                        countt++;
                        itemChildList.row.add($(str1)).draw();
                    });
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code ...");
        }
    }

</script>

