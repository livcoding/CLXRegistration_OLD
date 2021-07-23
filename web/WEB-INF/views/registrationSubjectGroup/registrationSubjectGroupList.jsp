<%-- 
    Document   : RegistrationSubjectGroupList
    Created on : 19 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
            <select class="form-control" id="programCode" onchange="getGridDate();" name="programCode" data-validation="required">
                <option value="">Select</option>
                <c:forEach items="${prog_list}" var="prog_list">
                    <option value="${prog_list[2]}"><c:out value="${prog_list[0]}"/>/<c:out value="${prog_list[1]}"/></option>
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
                    <th style="width:20px"></th>
                    <th style="width:50px">Sl.No</th>
                    <th style="width:100px">Program Code</th>
                    <th style="width:200px">Program Desc.</th>
                    <th style="width:200px">Group Name</th>
                    <th>Subjects</th>
                    <th style="width:70px">Active/Deactive</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</form>
<script>

    $("#programCode").chosen({width: "100%"});
    var val = getValue();
    var link = val == 'N' ? 'registrationSubjectGroup/add' : '';
    var cb = {
        add: {
            link: link,
            name: "Add",
            value: ""
        },
        edit: {
            link: "registrationSubjectGroup/edit",
            name: "",
            value: ""
        },
        delete: {
            link: "registrationSubjectGroup/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "registrationSubjectGroup/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    commonButtonMethod(cb);
    function callChkBox(index) {
        var value = $("#chk1" + index).val();
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

    function ActiveDeactive(count) {
        var programid = $("#groidprogramid" + count).val();
        var groupid = $("#groidgroupid" + count).val();
        var subjectid = $("#groidsubejctid" + count).val();
        var deactive = $("#groidstatus" + count).val();
        $.ajax({
            type: "POST",
            url: "registrationSubjectGroup/update",
            data: '&programid=' + programid + '&subjectid=' + subjectid + '&groupid=' + groupid + '&deactive=' + deactive,
            datatype: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data === 'Success') {
                    toastr.success(Success['update_success'], "Success!!");
//                    lodForm("registrationSubjectGroup/list");
                    getGridDate();
                } else if (data === 'Error') {
                    toastr.error('Form Submission Failed.', "Error!!");
                } else {
                    BootstrapDialog.alert(data);
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getGridDate() {
        $('#datatable').DataTable().clear();
        var tablelist = $('#datatable').DataTable();
        var programid = $("#programCode").val();
        if (programid != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "registrationSubjectGroup/getGridDate",
                data: '&programid=' + $("#programCode").val(),
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
                        $.each(data.gridData, function (i, item)
                        {
                            var programid = item[0];
                            var subjectid = item[1];
                            var groupid = item[4];
                            var activedeactive = item[7];
                            var str1 = '<tr>';
                            str1 += '<td id="' + item[0] + '~@~' + item[1] + '~@~' + item[4] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[4] + '"/>';
                            str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                            str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                            str1 += '  <td>' + count + '</td>'; //Sno.
                            str1 += '  <td>' + item[2] + '</td>'; //programCode
                            str1 += '  <td>' + item[3] + '</td>'; //programDesc
                            str1 += '  <td>' + item[4] + '</td>'; //group
                            str1 += '  <td>' + item[5] + ' - ' + item[6] + '</td>'; //subjects
                            if (item[7] == 'Y') {
                                str1 += '  <td><a class="btn btn-info" href="javascript:ActiveDeactive(' + count + ');">Active</a></td>';//deactive
                            } else {
                                str1 += '  <td><a class="btn btn-info" href="javascript:ActiveDeactive(' + count + ');">Deactive</a></td>';//deactive
                            }
                            str1 += '  <td style="display:none"><input type="hidden" value="' + programid + '" id="groidprogramid' + count + '" >'
                            str1 += '  <input type="hidden" value="' + groupid + '" id="groidgroupid' + count + '">'
                            str1 += '  <input type="hidden" value="' + subjectid + '" id="groidsubejctid' + count + '">'
                            str1 += '  <input type="hidden" value="' + activedeactive + '" id="groidstatus' + count + '"></td></tr>'
                            tablelist.row.add($(str1)).draw();
                            count++;
                        });
                    } else {
                        $('#datatable').DataTable().clear().draw();
                        BootstrapDialog.alert("No Data found for this Program Code");
                        $("#programCode").val('').trigger("chosen:updated");
                    }
                    setTimeout($.unblockUI, 1000);

                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Program Code..");
        }
    }
</script>