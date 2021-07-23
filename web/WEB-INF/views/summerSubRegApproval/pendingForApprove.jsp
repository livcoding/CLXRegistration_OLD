<%-- 
    Document   : pendingForApprove
    Created on : Feb 6, 2019, 10:25:23 AM
    Author     : ashutosh1.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform1" class="form-horizontal">            
<!--    <div style="margin-top: 10px;margin-left: 10px;" class="form-group pull-left" id="chkhideshow">
        <label style="text-transform: capitalize; margin-top: -17px;" class="checkbox-inline"><font style="color:black;font-weight:bold">Merge Common Subject Batches:</font> 
            <input type="checkbox" id="mergebatches" name="mergebatches" checked="true" value="Y" style="margin-left:10px"/> </label>
    </div>-->
    <div class="col-lg-12" style="margin-top:10px;" >        
        <div class="col-lg-12 container" style="overflow-x:auto;">
            <table id="itemListChoice" class="box table table-hover table-condensed table-bordered" cellspacing="0" >
                <thead>
                    <tr>
                        <th width="1px;"><input type="checkbox" id="chkAll" name="chkAll" onclick="checkAll();"/></th>
                        <th width="1px;">Sl.No.</th>
                        <th width="10px;">Institute Code</th>
                        <th width="10px;">Enrollment No.</th>
                        <th width="25px;">Name</th>
                        <th width="20px;">Program(Branch)</th>
                        <th width="8px;">Sty No.</th>
                        <th width="25px;">Subject(Code:Description)</th>
                        <th width="10px;">Fee Paid</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div> 
        <div class="form-group">       
            <div class="col-lg-9">         
                <div class="col-lg-12 form-group">              
                    <div style="margin-top: 10px;margin-left: 10px;" class="form-group pull-left" id="chkhideshow">
                        <label style="text-transform: capitalize; margin-top: -17px;"><font style="color:black">Registration Confirmation:</font></label>
                        <input type="checkbox" id="regconf" name="regconf" onclick="change()" value="Y" />  
                    </div>
                </div>
                <div class="row col-lg-12 form-group" style="display:none;" id="regconfirmhideshow">
                    <div class="row col-lg-9 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label"><font style="color:black">Reg Confirmation Date:</font></label>
                        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                            <div class='input-group date' id='datetimepicker'>
                                <input type='text' class="form-control" id="regConfirm_date" name="regConfirm_date" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3">                 
                <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                    <a href="javascript:approveSelectedRecord();" class="btn btn-success btn-md">Approve Selected Record</a>
                </div>
            </div>
        </div>
    </div> 
    <input type="hidden" name="total" id="total" value=""/>
    <input type="hidden" name="countval1" id="countval1" value=""/>
    <input type="hidden" name="checkedCount1" id="checkedCount1" value=""/>
</form>

<script>

    $('#itemListChoice').DataTable({
        searching: true, paging: true, info: true
    });
    function change() {
        if ($("#regconf").prop('checked') == true) {
            $('#regconfirmhideshow').show();
        } else {
            $('#regconfirmhideshow').hide();
        }
    }
    $(function () {
        var todayTime = new Date();
        var month = (todayTime.getMonth() + 1) < 10 ? '0' + (todayTime.getMonth() + 1) : '' + (todayTime.getMonth() + 1);
        var day = todayTime.getDate() < 10 ? '0' + todayTime.getDate() : '' + todayTime.getDate();
        var year = todayTime.getFullYear();
        $('#regConfirm_date').val(day + "/" + month + "/" + year);
    });
    $(function () {
        $('#datetimepicker').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });

    function checkAll() {
        var count = $("#total").val();
        if ($("#chkAll").is(":checked")) {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", true);
            }
        } else {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", false);
            }
        }
    }

    function loadPendingForApproveData() {
        var instids = $('#multiinstituteid').val();
        var regCode = $("#regCode").val();
        if (instids != '') {
            if (regCode != '') {
                if ($("#total").val() == "") {
                    $("#styType").val('');
                    $('#itemListChoice').DataTable().clear().draw();
                    var itemChildList = $('#itemListChoice').DataTable();
                    $.blockUI();
                    $.ajax({
                        url: "summerSubjectRagistrationApproval/getPendingForApprovalData",
                        dataType: 'json',
                        async: true,
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: "regCode=" + regCode + '&instituteids=' + instids,
                        error: (function (response) {
                            alert('Server Error' + response);
                            setTimeout($.unblockUI, 2000);
                        }),
                        success: function (data) {
                            $("#styType").val(data.styTypevalue);
                            var count = new Array();
                            if (data.pendingforapprovaldata != null) {
                                var countt = 1;
                                var c = 0;
                                var str = '';
                                if (data.pendingforapprovaldata != null && data.pendingforapprovaldata != '') {
                                    $.each(data.pendingforapprovaldata, function (i, item)
                                    {
                                        if (item[6] == null)
                                        {
                                            str = '';
                                        } else {
                                            str = item[6];
                                        }
                                        var str1 = '';
                                        var cou = 0;
                                        str1 += ' <tr id="' + item[1] + '">';
                                        str1 += ' <td id="' + item[1] + '"><input type="checkbox" id="chk' + i + '" name="chk' + i + '"  value="' + item[1] + '~@~' + item[4] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[15] + '~@~' + item[16] + '~@~' + item[21] + '~@~' + item[17] + '/' + item[18] + '/' + item[8] + '/' + item[19] + '/' + item[20] + '"/>';
                                        str1 += ' </td>';
                                        str1 += ' <td>' + countt + '</td>';
                                        str1 += ' <td>' + item[17] + '</td>';
                                        str1 += ' <td>' + item[0] + '</td>';
                                        str1 += ' <td>' + item[2] + '</td>';
                                        str1 += ' <td>' + item[3] + '</td>';
                                        str1 += ' <td>' + item[4] + '</td>';
                                        str1 += ' <td>' + item[5] + '</td>';
                                        str1 += ' <td>' + str + '</td>';
                                        str1 += ' </tr>';
                                        count.push(cou++);
                                        countt++;
                                        c++;
                                        itemChildList.row.add($(str1)).draw();
                                    });
                                    $("#countval1").val(count);
                                    $("#total").val(c);
                                } else {
                                    BootstrapDialog.alert("No Data Found...");
                                }
                            } else {
                                BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                            }
                            setTimeout($.unblockUI, 2000);
                        }
                    });
                }
            } else {
                BootstrapDialog.alert("Please Select Semester Code ...");
            }
        } else {
            BootstrapDialog.alert("Please Select Institute...");
        }
    }

    function approveSelectedRecord() {
        var countval1 = $("#countval1").val().split(",");
        var checkedStatus = 0;
        for (var j = 0; j < countval1.length; j++) {
            if ($('#chk' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCount1").val(countval1.length);
        if (checkedStatus == 0) {
            BootstrapDialog.alert("Please select at least one check box...!");
        } else {
            if ($("#regconf").prop('checked') == true) {
                if ($('#regConfirm_date').val() != '') {

                } else {
                    BootstrapDialog.alert("Please select Reg Confirmation Date...!");
                }
            } else {
                setFormIdAndUrl('ajaxform1', 'summerSubjectRagistrationApproval/approve', 1);
            }
        }
    }

</script>