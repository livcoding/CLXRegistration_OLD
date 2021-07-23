<%-- 
    Document   : phdSubjectFinalizationApproved
    Created on : Dec 20, 2018, 10:53:20 AM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">        
        <div class="col-lg-12 container">
            <table id="itemListApproved2" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th style="width:20px"></th>
                        <th style="width:20px">Sl.No.</th>
                        <th style="width:130px">Enrollment Number</th>
                        <th>Name</th>
                        <th style="width:70px">Sty Number</th>
                        <th>Subject Code : Name</th>
                        <th style="width:40px">Credit</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:bbb();" class="btn btn-success btn-sm btn-flat">Cancelled Selected Record</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="countval2" id="countval2" value=""/>
    <input type="hidden" name="status2" id="status2" value="A"/>
    <input type="hidden" name="checkedCount2" id="checkedCount2" value=""/>
</form>

<script>

    $('#itemListApproved2').DataTable({
        searching: true, paging: true, info: true
    });

    function getApprovalData() {
        $.ajax({
            type: "POST",
            url: "phdSubjectFinalization/getApproveData",
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
                var count = 0;
                $('#itemListApproved2').DataTable().clear().draw();
                var itemChildList = $('#itemListApproved2').DataTable();
                $.each(data.getApproveData, function (i, item)
                {
                    var str1 = '<tr id="' + item[1] + '~@~' + item[3] + '~@~' + item[4] + ' : ' + item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '">';
                    str1 += '<td id="' + item[1] + '~@~' + item[3] + '~@~' + item[4] + ' : ' + item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '"><input type="checkbox"   id="chk2' + i + '" name="chk2' + i + '"  value="' + item[1] + '~@~' + item[3] + '~@~' + item[4] + ' : ' + item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '"/>';
                    str1 += '  </td>';
                    str1 += '  <td>' + j + '</td>';
                    str1 += '  <td>' + item[0] + '</td>';
                    str1 += '  <td>' + item[2] + '</td>';
                    str1 += '  <td>' + item[3] + '</td>';
                    str1 += '  <td>' + item[4] + ' : ' + item[14] + '</td>';
                    str1 += '  <td>' + item[10] + '</td></tr>';
                    itemChildList.row.add($(str1)).draw();
                    j++;
                    count++;
                });
                $("#countval2").val(count);
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function bbb() {
        var countval22 = $("#countval2").val();
        var checkedStatus = 0;
        for (var j = 0; j < countval22; j++) {
            if ($('#chk2' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCount2").val(countval22);
        if ($("#registrationid").val() != '') {
            if (checkedStatus == 0) {
                BootstrapDialog.alert("Please select at least one check box..");
            } else {
                setFormIdAndUrl('ajaxform2', 'phdSubjectFinalization/savePending', 2, 'C');
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }

</script>