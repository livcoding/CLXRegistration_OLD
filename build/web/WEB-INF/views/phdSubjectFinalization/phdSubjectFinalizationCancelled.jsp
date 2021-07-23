<%-- 
    Document   : phdSubjectFinalizationCancelled
    Created on : Dec 20, 2018, 10:55:03 AM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform3" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">        
        <div class="col-lg-12 container">
            <table id="itemListApproved3" class="box table table-hover table-condensed table-bordered" cellspacing="0">
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
                <a href="javascript:ccc();" class="btn btn-success btn-sm btn-flat">Approve Selected Record</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="countval3" id="countval3" value=""/>
    <input type="hidden" name="checkedCount3" id="checkedCount3" value=""/>
</form>

<script>

    var tablelist = $('#itemListApproved3').DataTable({
        searching: true, paging: true, info: true
    });


    function getCancelledData() {
        $('#itemListApproved3').DataTable().clear().draw();
        var itemChildList = $('#itemListApproved3').DataTable();
        $.ajax({
            type: "POST",
            url: "phdSubjectFinalization/getCancelledData",
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
                $.each(data.getCancelledData, function (i, item)
                {
                    var str1 = '<tr id="' + item[1] + '~@~' + item[3] + '~@~' + item[4] + ' : ' + item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '">';
                    str1 += '<td id="' + item[1] + '~@~' + item[3] + '~@~' + item[4] + ' : ' + item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '"><input type="checkbox"   id="chk3' + i + '" name="chk3' + i + '"  value="' + item[1] + '~@~' + item[3] + '~@~' + item[4] + ' : ' + item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '"/>';
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
                $("#countval3").val(count);
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function ccc() {
        var countval13 = $("#countval3").val();
        var checkedStatus = 0;
        for (var j = 0; j < countval13; j++) {
            if ($('#chk3' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCount3").val(countval13);
        if ($("#registrationid").val() != '') {
            if (checkedStatus == 0) {
                BootstrapDialog.alert("Please select at least one check box..");
            } else {
                setFormIdAndUrl('ajaxform3', 'phdSubjectFinalization/saveCancelledData', 3, 'A');
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }


</script>