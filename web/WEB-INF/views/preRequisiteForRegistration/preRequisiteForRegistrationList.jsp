<%-- 
    Document   : preRequisiteForRegistrationList
    Created on : Feb 5, 2019, 4:53:14 PM
    Author     : ankur.goyal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="acadYear" name="acadYear" onchange="loadData();" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${acadYear}" var="list">
                            <option value="${list}"><c:out value="${list}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <!--            <div class="row col-lg-3 form-group">
                            <a href="javascript:loadData();" class="btn btn-success fa fa-search"></a>
                        </div>-->
        </div>
        <div class="row col-lg-12 form-group" ></div>
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class=""  >
                    <th style="display:none"></th>
                    <th width="" >Sl.No</th>
                    <th width="" >Program Code</th>
                    <th width="" >Branch Code</th>
                    <th width="" >STY Number</th>
                    <th width="" >Max Fail Subjects</th>
                    <th width="" >Min Earned Points</th>
                    <th width="" >Max No of Attempts</th>
                    <th width="" >Min Credits</th>
                    <th width="" >Lateral Entry Credits</th>
                    <th width="" >Min CGPA</th>
                    <th width="" >Min SGPA</th>
                    <th width="" >Min % Attendance</th>
                    <th width="" >Max Disciplinary</th>
                </tr>
            </thead> 
        </table>
    </div> 
</form>

<script>
    $("#acadYear").chosen();

    var cb = {
        add: {
            link: "preRequisiteForRegistration/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "preRequisiteForRegistration/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "preRequisiteForRegistration/delete",
            name: "",
            value: ""
        },
        list: {
            link: "preRequisiteForRegistration/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);

    function callChkBox(index) {
        var value = $("#chk" + index).val();
        if ($("#chk" + index).prop('checked') == true) {
            $("#chk" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk" + index).prop("checked", true);
            checkChild(value, index);
        }
    }

    function checkChild(v, i) {
        $.ajax({
            url: "preRequisiteForRegistration/checkIfChildExist",
            datatype: 'String',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "pk=" + v,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                if (data === "true") {
                    BootstrapDialog.alert("Child exists for this record!");
                    $("#chk" + i).prop("checked", false);
                    $("#chk" + i).attr("disabled", true);
                } else {
                    $("#del_img" + i).hide();
                    $("#d_img" + i).show();
                }
            }
        });
    }

    function loadData() {
        $.blockUI();
        var acadYear = $("#acadYear").val();
        $('#datatable').DataTable().clear().draw();
        var itemChildList = $('#datatable').DataTable();
        if (acadYear != '') {
            $.ajax({
                type: "POST",
                url: "preRequisiteForRegistration/getGridData",
                data: "acadYear=" + $("#acadYear").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    $.blockUI();
                    var count = new Array();
                    if (data.gridData != '') {
                        var countt = 1;
                        var j = 1;
                        var str2 = '';
                        var str3 = '';
                        var str4 = '';
                        var str5 = '';
                        var str6 = '';
                        var str7 = '';
                        var str8 = '';
                        var str9 = '';
                        var str10 = '';
                        $.each(data.gridData, function (i, item)
                        {
                            var str1 = '';
                            var cou = 0;
                            if (item[4] == null) {
                                str2 = '--';
                            } else {
                                str2 = item[4];
                            }
                            if (item[5] == null) {
                                str3 = '--';
                            } else {
                                str3 = item[5];
                            }
                            if (item[6] == null) {
                                str4 = '--';
                            } else {
                                str4 = item[6];
                            }
                            if (item[7] == null) {
                                str5 = '--';
                            } else {
                                str5 = item[7];
                            }
                            if (item[8] == null) {
                                str6 = '--';
                            } else {
                                str6 = item[8];
                            }
                            if (item[9] == null) {
                                str7 = '--';
                            } else {
                                str7 = item[9];
                            }
                            if (item[10] == null) {
                                str8 = '--';
                            } else {
                                str8 = item[10];
                            }
                            if (item[11] == null) {
                                str9 = '--';
                            } else {
                                str9 = item[11];
                            }
                            if (item[12] == null) {
                                str10 = '--';
                            } else {
                                str10 = item[12];
                            }
                            str1 += ' <tr id="' + item[0] + '~@~' + item[3] + '~@~' + item[13] + '~@~' + item[14] + '~@~' + item[15] + '">';
                            str1 += ' <td style="display:none"> </td> ';
                            str1 += ' <td>' + countt + '</td>';
                            str1 += ' <td>' + item[1] + '</td>'; //Program code
                            str1 += ' <td>' + item[2] + '</td>'; //Branch Code
                            str1 += ' <td>' + item[3] + '</td>'; //STY Number
                            str1 += ' <td>' + str2 + '</td>'; //Max Fail Subjects
                            str1 += ' <td>' + str3 + '</td>'; //Min Earned Points
                            str1 += ' <td>' + str4 + '</td>'; //Max No of Attempts
                            str1 += ' <td>' + str5 + '</td>'; //Min Credits
                            str1 += ' <td>' + str6 + '</td>'; //Lateral Entry Credits
                            str1 += ' <td>' + str7 + '</td>'; //Min CGPA
                            str1 += ' <td>' + str8 + '</td>'; //Min SGPA
                            str1 += ' <td>' + str9 + '</td>'; //Min % Attendance
                            str1 += ' <td>' + str10 + '</td>'; //Max Discplinary
                            str1 += ' </tr>';
                            count.push(cou++);
                            countt++;
                            itemChildList.row.add($(str1)).draw();
                        });
                        setTimeout($.unblockUI, 1000);
                    } else {
                        $.unblockUI();
                        BootstrapDialog.alert("Data not Available in this Academic Year, Please Select Another Academic Year...");
                    }
                }
            });
        } else {
            $.unblockUI();
            BootstrapDialog.alert("Please Select Academic Year..!");
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
            checkChild(value, index);
        }
    }

    function checkChild(v, i) {
        $.ajax({
            url: "preRequisiteForRegistration/checkIfChildExist",
            datatype: 'String',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "pk=" + v,
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                if (data === "true") {
                    BootstrapDialog.alert("Child exists for this record!");
                    $("#chk1" + i).prop("checked", false);
                    $("#chk1" + i).attr("disabled", true);
                } else {
                    $("#del_img" + i).hide();
                    $("#d_img" + i).show();
                }
            }
        });
    }
</script>
