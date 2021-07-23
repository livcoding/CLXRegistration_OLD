<%-- 
    Document   : studentNRSubjectsList
    Created on : Sep 21, 2019
    Author     : malkeet.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>

<form id="ajaxform" class="form-horizontal"> 
    <div class="col-lg-12" style="margin-top:10px;">         
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Enrollment Number:
                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
                <input type="text" id="enrollmentNo" name="enrollmentNo" onkeydown="clearTable();"onkeyup="this.value=this.value.toUpperCase();"   onblur="getNRStudentInfo();" class="form-control" data-validation="required" autocomplete="off" placeholder="Enter Enrollment Number"/>
            </div>
        </div>
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Student Name:</label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
                <input type="text" id="stuname" name="stuname"class="form-control" readonly="true">
            </div>
        </div>
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:</label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
                <input type="text" id="acadyear" name="acadyear"class="form-control" readonly="true">
            </div>
        </div>
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Sty no.:</label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">    
                <input type="text" id="stynumber" name="stynumber"class="form-control" readonly="true">
            </div>
        </div>
    </div><input type="hidden" id="studentid" name="studentid"class="form-control" readonly="true">
    <div class="row col-lg-12">
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a onclick="setNRData();getNRGridData()" class="btn btn-success btn-sm btn-flat">Load Data</a> 
        </div>
    </div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class="" >
                    <th></th>
                    <th>Slno.</th>
                    <th>Subject Code</th>
                    <th>Subject Name</th>
                    <th>Registered</th>
                    <th>Semester Code(When Left)</th>
                    <th>Semester Code(When Take)</th>
                    <th>Academic Year</th>
                    <th>Program</th>
                    <th>STY No.</th>
                    <th>Section</th>
                    <th>Basket</th>
                    <th>Remark</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
</form>
<script>

    $(document).ready(function () {
        $('#datatable').DataTable().clear().draw();
    });

    function clearTable() {
        $('#datatable').DataTable().clear().draw();
        $("#studentid").val('');
        $("#stuname").val('');
        $("#acadyear").val('');
        $("#stynumber").val('');
        $("#button_wrapper").hide();
    }
    function setNRData() {
        var section = $("#section").val();
        if (section != '') {
            $('#datatable').DataTable().destroy();
            debugger;
            var cb = {
                add: {
                    link: "studentNRSubjects/add",
                    name: "Add",
                    value: "special",
                    data: $("#studentid").val() + '~@~' + encodeURI($("#stuname").val())
                },
                edit: {
                    link: "studentNRSubjects/edit",
                    name: "",
                    value: ""
                },
                delete: {
                    link: "studentNRSubjects/delete",
                    name: "Delete",
                    value: ""
                },
                list: {
                    link: "studentNRSubjects/list"
                },
                expression: {
                    link: "",
                    name: "",
                    value: ""
                }
            }
            commonButtonMethod(cb);
        }
    }
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

    $('#enrollmentNo').keypress(function (e) {
        if (e.which == 13) {
            $(this).blur();
        }
    });

    function getNRStudentInfo() {
        var enrollmentno = $("#enrollmentNo").val();
        if (enrollmentno != '') {
            $.ajax({
                type: "POST",
                url: "studentNRSubjects/getStudetnInfo",
                data: '&enrollmentno=' + enrollmentno,
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
                            $("#acadyear").val(item[2]);
                            $("#stynumber").val(item[3]);
                        });
                    } else {
                        $("#enrollmentNo").val('');
                        $("#studentid").val('');
                        $("#stuname").val('');
                        $("#acadyear").val('');
                        $("#stynumber").val('');
                        BootstrapDialog.alert("Invalid Registartion no.");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        }
    }
    function getNRGridData() {
        $('#datatable').DataTable().clear().draw();
        var tablelist = $('#datatable').DataTable();
        var studentid = $("#studentid").val();
        $("#button_wrapper").hide();
        if (studentid != '') {
            $("#button_wrapper").show();
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "studentNRSubjects/getGridDate",
                data: '&studentid=' + studentid,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    $('#datatable').DataTable().clear().draw();
                    var count = 1;
                    $.each(data.gridData, function (i, item)
                    {
                        var nrsemester = '';
                        if (item[6] != null) {
                            nrsemester = item[6];
                        } else {
                            nrsemester = '--';
                        }
                        var semseter = '';
                        if (item[7] != null) {
                            semseter = item[7];
                        } else {
                            semseter = '--';
                        }
                        var remark = '';
                        if (item[15] != null) {
                            remark = item[15];
                        } else {
                            remark = '--';
                        }
                        var str1 = '<tr id="' + item[0] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[12] + '~@~' + item[1] + '~@~' + item[11] + '~@~' + item[2] + '">';
                        str1 += '<td id="' + item[0] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[12] + '~@~' + item[1] + '~@~' + item[11] + '~@~' + item[2] + '"><input type="checkbox" class="hidden" id="chk1' + i + '" value="' + item[0] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[12] + '~@~' + item[1] + '~@~' + item[11] + '~@~' + item[2] + '"/>';
                        str1 += ' <img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                        str1 += '  <img id="d_img' + i + '" style="display:none;" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/> </td>';
                        str1 += '  <td>' + count + '</td>'; //Sno.
                        str1 += '  <td>' + item[3] + '</td>'; //subject code
                        str1 += '  <td>' + item[4] + '</td>'; //subject desc
                        str1 += '  <td>' + item[5] + '</td>'; //Registered
                        str1 += '  <td>' + nrsemester + '</td>'; //Semescter Left
                        str1 += '  <td>' + semseter + '</td>'; //Semescter Take
                        str1 += '  <td>' + item[8] + '</td>'; //academic Year
                        str1 += '  <td>' + item[10] + '</td>'; //program
                        str1 += '  <td>' + item[11] + '</td>'; //section
                        str1 += '  <td>' + item[13] + '</td>'; //section
                        str1 += '  <td>' + item[14] + '</td>'; //basket 
                        str1 += '  <td>' + remark + '</td>'; //remark 
                        str1 += '  </tr>';
                        tablelist.row.add($(str1)).draw();
                        count++;
                    });
                    setTimeout($.unblockUI, 1000);
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please enter Enrollment No.!");
        }
    }

</script>



