<%-- 
    Document   : phdSubjectFinalizationPendingForApproved
    Created on : Dec 20, 2018, 10:52:59 AM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform1" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;" id="pending">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="itemListpeding1" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class="" >
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
            <a href="javascript:aa();" class="btn btn-success btn-sm btn-flat">Approve Selected Record</a>
        </div>
    </div>
    <input type="hidden" name="countva1" id="countva1" value=""/>
    <input type="hidden" name="checkedCount1" id="checkedCount1" value=""/>
</form>
<script>
    $("#reg_cod").chosen();
    var tablelist = $('#itemListpeding1').DataTable({
        searching: true, paging: true, info: true
    });
//    var cb = {
//        add: {
//            link: "",
//            name: "",
//            value: ""
//        },
//        edit: {
//            link: "",
//            name: "",
//            value: ""
//        },
//        delete: {
//            link: "",
//            name: "",
//            value: ""
//        },
//        list: {
//            link: "phdSubjectFinalization/list"
//        },
//        expression: {
//            link: "",
//            name: "",
//            value: ""
//        }
//
//    }
//
//    commonButtonMethod(cb);

    function callChkBox(index) {
        var value = $("#chk1" + index).val();
        $("#chk1" + index).prop("checked", true);
        checkChild(value, index);
    }


    function getPendingForApprovalData() {
        $('#itemListpeding1').DataTable().clear().draw();
        var itemChildList = $('#itemListpeding1').DataTable();
        $.ajax({
            type: "POST",
            url: "phdSubjectFinalization/getPendingData",
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
                var table = $('#datatable').DataTable();
                $.each(data.pendingData, function (i, item)
                {
                    var str1 = '<tr id="' + item[1] + '~@~' + item[3] + '~@~' + iitem[4]+' : '+ item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '">';
                    str1 += '<td id="' + item[1] + '~@~' + item[3] + '~@~' + item[4]+' : '+ item[14]+ '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '"><input type="checkbox"   id="chk1' + i + '" name="chk1' + i + '"  value="' + item[1] + '~@~' + item[3] + '~@~' + item[4]+' : '+ item[14] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[11] + '~@~' + item[12] + '~@~' + item[13] + '~@~' + item[10] + '"/>';
                    str1 += '  </td>';
                    str1 += '  <td>' + j + '</td>';
                    str1 += '  <td style="width:130px">' + item[0] + '</td>';
                    str1 += '  <td>' + item[2] + '</td>';
                    str1 += '  <td style="width:10px">' + item[3] + '</td>';
                    str1 += '  <td>' + item[4] + ' : ' + item[14] + '</td>';
                    str1 += '  <td>' + item[10] + '</td></tr>';
                    itemChildList.row.add($(str1)).draw();
                    j++;
                    count++;
                });
                $("#countva1").val(count);
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function aa() {
        var countval1 = $("#countva1").val();
        var checkedStatus = 0;
        for (var j = 0; j < countval1; j++) {
            if ($('#chk1' + j).is(':checked')) {
                checkedStatus++;
            }
        }
        $("#checkedCount1").val(countval1);
        if ($("#registrationid").val() != '') {
            if (checkedStatus == 0) {
                BootstrapDialog.alert("Please select at least one check box..");
            } else {
                setFormIdAndUrl('ajaxform1', 'phdSubjectFinalization/savePending', 1, 'A');
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }

    }

</script>


