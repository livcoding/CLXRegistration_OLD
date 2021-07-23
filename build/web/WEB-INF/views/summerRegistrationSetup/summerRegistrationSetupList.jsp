<%-- 
    Document   : SummerRegistrationSetupList
    Created on : 1 AUG: 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
            <select class="form-control" id="programCode" name="programCode" data-validation="required" onchange="getGridData();">
                <option value="">Select</option>
                <c:forEach items="${prog_list}" var="prog_list">
                    <option value="${prog_list[2]}"><c:out value="${prog_list[0]}"/>/<c:out value="${prog_list[1]}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>
<div class="col-lg-12" style="margin-top:30px;"></div>
<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th ></th>
                <th>Sl.No.</th>
                <th>Program Code</th>
                <th>Program Desc.</th>
                <th>STY No.</th>
                <th>Max Credit</th>
                <th>Max Subjects </th>
                <th>Max Subjects(Project)</th>
                <th>Max Subjects(Industrial Case)</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</form>
<script>

    $("#programCode").chosen({width: "100%"});

    var cb = {
        add: {
            link: "summerRegistrationSetup/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "summerRegistrationSetup/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "summerRegistrationSetup/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "summerRegistrationSetup/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    function callChkBox(index) {
        if ($("#chk1" + index).prop('checked') == true) {
            $("#chk1" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk1" + index).prop("checked", true);
            $("#del_img" + index).hide();
            $("#d_img" + index).show();
        }
    }

    commonButtonMethod(cb);
    function getGridData() {
        var tablelist = $('#datatable').DataTable();
        var programCode = ($("#programCode").val());
        $('#datatable').DataTable().clear().draw();
        if (programCode != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "summerRegistrationSetup/getGridDate",
                data: '&programId=' + $("#programCode").val(),
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
                            var str1 = '<tr id="' + item[0] + ':' + item[1] + ':' + item[4] + '">';
                            str1 += '<td id="' + item[0] + '~@~' + item[1] + '~@~' + item[4] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[4] + '"/>';
                            str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                            str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                            str1 += '  <td>' + (i + 1) + '</td>';
                            str1 += '  <td>' + item[2] + '</td>';
                            str1 += '  <td>' + item[3] + '</td>';
                            str1 += '  <td>' + item[4] + '</td>';
                            str1 += '  <td>' + item[5] + '</td>';
                            str1 += '  <td>' + item[6] + '</td>';
                            str1 += '  <td>' + item[7] + '</td>';
                            str1 += '  <td>' + item[8] + '</td>';
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