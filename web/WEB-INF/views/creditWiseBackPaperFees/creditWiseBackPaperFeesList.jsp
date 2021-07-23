<%-- 
    Document   : CreditWiseBackPaperFeesList
    Created on : 11 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">STY Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
            <select class="form-control" id="styType" onchange="getGridDate();" name="styType" data-validation="required">
                <option value=value="">Select</option>
                <c:forEach items="${styType}" var="list">
                    <option value="${list[0]}"><c:out value="${list[1]}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class="" >
                    <th style="display:none" ></th>
                    <th>Sl.No</th>
                    <th>STY Type</th>
                    <th>STY Desc.</th>
                    <th>Fee Amount</th>
                    <th>Credit From</th>
                    <th>Credit Up To</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</form>
<script>

    $("#styType").chosen({width: "100%"});
    
    var val = getValue();
    var link = val == 'N' ? 'creditWiseBackPaperFees/add' : '';
    var cb = {
        add: {
            link: link ,
            name: "Add",
            value: ""
        },
        edit: {
            link: "creditWiseBackPaperFees/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "creditWiseBackPaperFees/delete",
            name: "",
            value: ""
        },
        list: {
            link: "creditWiseBackPaperFees/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    commonButtonMethod(cb);

    function getGridDate() {
        $('#datatable').DataTable().clear();
        var tablelist = $('#datatable').DataTable();
        var styType = $("#styType").val();
        if (styType != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "creditWiseBackPaperFees/getGridDate",
                data: '&styType=' + $("#styType").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.gridData != '' && data.gridData != null) {
                        $('#datatable').DataTable().clear();
                        var count = 1;
                        var semesterPattern = ''
                        var semester = ''
                        var deactive = ''

                        $.each(data.gridData, function (i, item)
                        {
                            var str1 = '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[7] + '">';
                            str1 += '  <td style="display:none"></td>'; //Sno.
                            str1 += '  <td>' + count + '</td>'; //Sno.
                            str1 += '  <td>' + item[2] + '</td>'; //styType
                            str1 += '  <td>' + item[3] + '</td>'; //styDesc
                            str1 += '  <td>' + item[4] + '</td>'; //FeeAmount
                            str1 += '  <td>' + item[5] + '</td>'; //CreditForm
                            str1 += '  <td>' + item[6] + '</td></tr>'; //CreditUpTo
                            tablelist.row.add($(str1)).draw();
                            count++;
                        });
                    } else {
                        $('#datatable').DataTable().clear().draw();
                        BootstrapDialog.alert("No Data found for this STY Type");
                        $("#styType").val('').trigger("chosen:updated");
                    }
                    setTimeout($.unblockUI, 1000);

                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select STY Type...");
        }
    }

</script>