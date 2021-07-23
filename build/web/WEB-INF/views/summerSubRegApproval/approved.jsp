<%-- 
    Document   : approved
    Created on : Feb 6, 2019, 10:25:52 AM
    Author     : ashutosh1.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">        
        <div class="col-lg-12 container" style="overflow-x:auto;">
            <table id="itemListApproved" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead>
                    <tr>
                        <th width="1px;"></th>
                        <th width="1px;">Sl.No.</th>
                        <th width="10px;">Institute Code</th>
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
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;" class="form-group pull-right">
                <a href="javascript:cancelSelectedRecord();" class="btn btn-success btn-md">Cancel Selected Record</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="countval2" id="countval2" value=""/>
    <input type="hidden" name="checkedCount2" id="checkedCount2" value=""/>
</form>

<script>

    $('#itemListApproved').DataTable({
        searching: true, paging: true, info: true
    });

    function loadApprovedData() {
        var instids = $('#multiinstituteid').val();
        var regCode = $("#regCode").val();
        var styType = $("#styType").val();
        if (instids != '') {
            if (regCode != '') {
                $('#itemListApproved').DataTable().clear().draw();
                var itemChildList = $('#itemListApproved').DataTable();
                $.ajax({
                    url: "summerSubjectRagistrationApproval/getApprovedData",
                    dataType: 'json',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: "regCode=" + regCode + "&styType=" + styType + '&instituteids=' + instids,
                    error: (function (response) {
                        alert('Server Error' + response);
                    }),
                    success: function (data) {
                        var count = new Array();
                        if (data.approvedData != null) {
                            var countt = 1;
                            $.each(data.approvedData, function (i, item)
                            {
                                var str1 = '';
                                var cou = 0;
                                str1 += ' <tr id="' + item[1] + '">';
                                str1 += ' <td id="' + item[1] + '"><input type="checkbox" id="approvedchk' + i + '" name="approvedchk' + i + '" value="' + item[1] + '~@~' + item[4] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[15] + '~@~' + item[16] + '"/>';
                                str1 += ' </td>';
                                str1 += ' <td>' + countt + '</td>';
                                str1 += ' <td>' + item[0] + '</td>';
                                        str1 += ' <td>' + item[17] + '</td>';
                                str1 += ' <td>' + item[2] + '</td>';
                                str1 += ' <td>' + item[3] + '</td>';
                                str1 += ' <td>' + item[4] + '</td>';
                                str1 += ' <td>' + item[5] + '</td>';
                                str1 += ' </tr>';
                                count.push(cou++);
                                countt++;
                                itemChildList.row.add($(str1)).draw();
                            });
                            $("#countval2").val(count);
                        } else {
                            BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                        }
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Semester Code ...");
            }
        } else {
            BootstrapDialog.alert("Please Select Institute...");
        }
    }

    function cancelSelectedRecord() {
        var countval1 = $("#countval2").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#approvedchk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCount2").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select at least one check box..");
        } else {
            setFormIdAndUrl('ajaxform2', 'summerSubjectRagistrationApproval/cancel', 2);
        }
    }

</script>