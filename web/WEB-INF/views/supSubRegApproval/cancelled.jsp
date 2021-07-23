<%-- 
    Document   : cancelled
    Created on : Feb 6, 2019, 11:03:07 AM
    Author     : ashutosh1.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform3" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">        
        <div class="col-lg-12 container" style="overflow-x:auto;">
            <table id="itemListCanceled" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th width="2px;">Sl.No.</th>
                        <th width="10px;">Enrollment No.</th>
                        <th width="25px;">Name</th>
                        <th width="25px;">Program(Branch)</th>
                        <th width="8px;">Sty No.</th>
                        <th width="30px;">Subject(Code:Description)</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 
    </div> 
</form>

<script>

    $('#itemListCanceled').DataTable({
        searching: true, paging: true, info: true
    });

    function loadCanceledData() {
        var regCode = $("#regCode").val();
        var styType = $("#styType").val();
        if (regCode != '') {
            $('#itemListCanceled').DataTable().clear().draw();
            var itemChildList = $('#itemListCanceled').DataTable();
            $.ajax({
                url: "supplementarySubjectRagistrationApproval/getCanceledData",
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
                    $.each(data.canceledData, function (i, item)
                    {
                        var str1 = '';
                        str1 += ' <tr id="' + item[1] + '">';
                        str1 += ' <td>' + countt + '</td>';
                        str1 += ' <td>' + item[0] + '</td>';
                        str1 += ' <td>' + item[2] + '</td>';
                        str1 += ' <td>' + item[3] + '</td>';
                        str1 += ' <td>' + item[4] + '</td>';
                        str1 += ' <td>' + item[5] + '</td>';
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
