<%-- 
    Document   : supplementarySubjectEntryList
    Created on : Sep 25, 2019, 11:37:04 AM
    Author     : malkeet.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
            <select class="form-control" id="semestercode" name="semestercode" data-validation="required" data-validation-error-msg="Please select semester code!">
                <option value="">Select</option>
                <c:forEach items="${semCode}" var="semCode">
                    <option value="${semCode[0]}"><c:out value="${semCode[1]}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>        
    <div class="row col-lg-6 form-group" >
        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Enrollment No.:
            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
        <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
            <input type="text" id="listenrollmentNo" name="listenrollmentNo" onkeydown="clearTable();" onkeyup="this.value = this.value.toUpperCase();"   onblur="getStudentInfo();getGridData();" class="form-control" data-validation="required" autocomplete="off" data-validation-error-msg="Please enter enrollment no.!" placeholder="Enter Enrollment No."/>
        </div><input type="hidden" id="studentid" name="studentid" class="form-control" readonly="true">
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
                <th>Enrollment No. | Name</th>
                <th>Subject Code</th>
                <th>Subject Description</th>
                <th>STY Number</th>
                <th>Basket</th>
                <th>Deactive</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</form>
<script>

    $("#semestercode").chosen({width: "100%"});
    $(document).ready(function () {
        $('#datatable').DataTable().clear().draw();
    });
    function clearTable() {
        $('#datatable').DataTable().clear().draw();
        $("#studentid").val('');
    }
    var cb = {
        add: {
            link: "supplementarySubjectEntry/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "supplementarySubjectEntry/edit",
            name: "",
            value: ""
        },
        delete: {
            link: "supplementarySubjectEntry/delete",
            name: "Delete",
            value: ""
        },
        list: {
            link: "supplementarySubjectEntry/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);


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
    $('#listenrollmentNo').keypress(function (e) {
        if (e.which == 13) {
            $(this).blur();
        }
    });
    function getStudentInfo() {
        var listenrollmentNo = $("#listenrollmentNo").val();
        if (listenrollmentNo != '') {
            $.ajax({
                type: "POST",
                url: "supplementarySubjectEntry/getStudetnInfo",
                data: '&enrollmentno=' + listenrollmentNo,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.studentInfo != '' && data.studentInfo != null) {
                        $.each(data.studentInfo, function (i, item)
                        {
                            $("#studentid").val(item[0]);
                            $("#stuname").val(item[1]);
                        });
                    } else {
                        $("#listenrollmentNo").val('');
                        $("#studentid").val('');
                        BootstrapDialog.alert("Invalid Enrollment No.");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        }
    }

    function getGridData() {
        var tablelist = $('#datatable').DataTable();
        var semestercode = ($("#semestercode").val());
        var studentid = ($("#studentid").val());
        $('#datatable').DataTable().clear().draw();
        if (studentid != '') {
            if (semestercode != '') {
                $.blockUI();
                $.ajax({
                    type: "POST",
                    url: "supplementarySubjectEntry/getGridData",
                    data: '&registrationid=' + $("#semestercode").val() + '&studentid=' + $("#studentid").val(),
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
                                var deactive = '';
                                if (item[11] == 'Y') {
                                    deactive = 'Yes';
                                } else {
                                    deactive = 'No';
                                }
                                var str1 = '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[9] + '">';
                                str1 += '<td id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[9] + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item[0] + '~@~' + item[1] + '~@~' + item[2] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[9] + '"/>';
                                str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                                str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                                str1 += '  <td>' + (i + 1) + '</td>';
                                str1 += '  <td>' + item[3] + ' | ' + item[4] + '</td>';
                                str1 += '  <td>' + item[7] + '</td>';
                                str1 += '  <td>' + item[8] + '</td>';
                                str1 += '  <td>' + item[5] + '</td>';
                                str1 += '  <td>' + item[10] + '</td>';
                                str1 += '  <td>' + deactive + '</td>';
                                str1 += '</tr>';
                                tablelist.row.add($(str1)).draw();
                            });
                        } else {
                            BootstrapDialog.alert("Subject Not Found..");
                        }
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
                setTimeout($.unblockUI, 2000);
            } else {
                BootstrapDialog.alert("Please Select Semester Code!");
                $("#listenrollmentNo").val('');
            }
        }
    }


</script>