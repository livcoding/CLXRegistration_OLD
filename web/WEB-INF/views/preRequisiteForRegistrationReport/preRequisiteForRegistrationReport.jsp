<%-- 
    Document   : preRequisiteForRegistrationReport
    Created on : Feb 25, 2019, 3:03:02 PM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">
    <div class="col-md-12">
        <div class="box" style="box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
            <div class="box-header with-border bg-navy">
                <h3 class="box-title header_name" id="box-title"></h3>
                <div class="box-tools pull-right">                  
                    <button onclick="javascript:blank();" class="btn btn-box-tool"><i class="fa fa-close fa-2x" style="color:white"></i></button>
                </div>
            </div>
            <div class="box-body" >
                <form id="ajaxform" class="form-horizontal">
                    <div class="col-lg-12" style="margin-top:10px;">
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="academicYear" name="academicYear" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                        <c:forEach items="${acadList}" var="list">
                                            <option value="${list}"><c:out value="${list}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-5 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Program Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                    <select class="form-control" id="programCode" name="programCode" multiple="true" data-validation="required" data-live-search="true">
                                        <c:forEach items="${progList}" var="list">
                                            <option value="${list[2]}"><c:out value="${list[0]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6">
                                <button type="button" class="fa fa-search btn btn-success" onclick="getBranchCode();"></button>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Branch Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="branchCode" name="branchCode" multiple="true" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-6 form-group">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">STY Number:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                    <select class="form-control" id="styNumber" name="styNumber" data-validation="required" data-live-search="true">               
                                        <option value="">Select</option>
                                        <option value="All">ALL</option>
                                        <c:forEach items="${styList}" var="semList">
                                            <option value="${semList.id.stynumber}"><c:out value="${semList.id.stynumber}" /></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12">
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                <a onclick="getGridData();" class="btn btn-success btn-sm btn-flat"> Load Data</a>
                                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                                <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                            </div>
                        </div>
                        <div class="col-lg-12" style="margin-top:10px;">
                            <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                            <table id="datatable1" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                <thead id="header">
                                    <tr class=""  >
                                        <th width="" >Sl.No</th>
                                        <th width="" >Program Code</th>
                                        <th width="" >Branch Code</th>
                                        <th width="" >STY Number</th>
                                        <th width="" >Max Fail Subject</th>
                                        <th width="" >Min SGPA</th>
                                        <th width="" >Min CGPA</th>
                                        <th width="" >Min Earned Points</th>
                                        <th width="" >Min Attendance Percent</th>
                                        <th width="" >Max Disciplinary</th>
                                        <th width="" >Min Credits</th>
                                        <th width="" >Lateral Entry Credits</th>
                                        <th width="" >Max No of Attempts</th>
                                        <th width="" >Deactive</th>
                                    </tr>
                                </thead> 
                            </table>
                        </div>
                    </div> 
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $("#academicYear").chosen();
    $("#programCode").chosen();
    $("#branchCode").chosen();
    $("#styNumber").chosen();
    
    function myReset() {
        $('#datatable').DataTable().clear().draw();
        $("#academicYear").val('').trigger("chosen:updated");
        $("#programCode").val('x').trigger("chosen:updated");
        $("#branchCode").val('x').trigger("chosen:updated");
        $("#styNumber").val('').trigger("chosen:updated");
    }

    var btns = [];
    table1 = $('#datatable1').DataTable({
        "lengthMenu": [[10, 50, 100, -1], [10, 50, 100, "All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    btns.push({
        extend: 'excel',
        text: '<i class=\"fa fa-lg fa-file-excel-o\"></i>',
        exportOptions: {
            modifier: {
                page: 'all'
            }
        }
    });

    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table1, {
                    buttons: btns
                });
                $('#button_wrapper').append(table1.buttons(0, null).container());
            }, 100);

    commonButtonMethod(cb);

    function getBranchCode() {
        var acadYear = $("#academicYear").val();
        var program = $("#programCode").val();
        if (acadYear != '') {
            if (program != '') {
                $("#branchCode").empty();
                $("#styNumber").val('').trigger("chosen:updated");
                $.ajax({
                    type: "POST",
                    url: "preRequisiteForPromotionReport/getBranch",
                    data: '&programCode=' + $("#programCode").val(),
                    dataType: "json",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        var str1 = '';
                        $.each(data.branchCode, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + ' -- ' + item[2] + '</option>'
                        });
                        $("#branchCode").append(str1);
                        $("#branchCode").trigger("chosen:updated");
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong.', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Program Code..!");
            }
        } else {
            BootstrapDialog.alert("Please Select Academic Year..!");
        }
    }

    function getGridData() {
        var acadYear = $("#academicYear").val();
        var program = $("#programCode").val();
        var branch = $("#branchCode").val();
        var styno = $("#styNumber").val();
        $('#datatable').DataTable().clear().draw();
        var itemChildList = $('#datatable').DataTable();
        if (acadYear != '' && program != '' && branch != '' && styno != '') {
            $.ajax({
                type: "POST",
                url: "preRequisiteForPromotionReport/getGridData",
                data: '&academicYear=' + $("#academicYear").val() + '&programCode=' + $("#programCode").val() + '&branchCode=' + $("#branchCode").val() + '&styNumber=' + $("#styNumber").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var count = new Array();
                    if (data.gridData != '') {
                        $.blockUI();
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
                        var str11 = '';
                        $.each(data.gridData, function (i, item)
                        {
                            var str1 = '';
                            var cou = 0;
                            if (item[3] == null) {
                                str2 = '--';
                            } else {
                                str2 = item[3];
                            }
                            if (item[4] == null) {
                                str3 = '--';
                            } else {
                                str3 = item[4];
                            }
                            if (item[5] == null) {
                                str4 = '--';
                            } else {
                                str4 = item[5];
                            }
                            if (item[6] == null) {
                                str5 = '--';
                            } else {
                                str5 = item[6];
                            }
                            if (item[7] == null) {
                                str6 = '--';
                            } else {
                                str6 = item[7];
                            }
                            if (item[8] == null) {
                                str7 = '--';
                            } else {
                                str7 = item[8];
                            }
                            if (item[9] == null) {
                                str8 = '--';
                            } else {
                                str8 = item[9];
                            }
                            if (item[10] == null) {
                                str9 = '--';
                            } else {
                                str9 = item[10];
                            }
                            if (item[11] == null) {
                                str10 = '--';
                            } else {
                                str10 = item[11];
                            }
                            if (item[12] == null) {
                                str11 = '--';
                            } else {
                                str11 = item[12];
                            }
                            str1 += ' <tr id="' + item[0] + '">';
                            str1 += ' <td>' + countt + '</td>';
                            str1 += ' <td>' + item[0] + '</td>'; //Program code
                            str1 += ' <td>' + item[1] + '</td>'; //Branch Code
                            str1 += ' <td>' + item[2] + '</td>'; //STY Number
                            str1 += ' <td>' + str2 + '</td>'; //Max Fail Subjects
                            str1 += ' <td>' + str3 + '</td>'; //Min SGPA
                            str1 += ' <td>' + str4 + '</td>'; //Min CGPA
                            str1 += ' <td>' + str5 + '</td>'; //Min Earned Point
                            str1 += ' <td>' + str6 + '</td>'; //Min Attendance Percent
                            str1 += ' <td>' + str7 + '</td>'; //Max Discplinary
                            str1 += ' <td>' + str8 + '</td>'; //Min Credits
                            str1 += ' <td>' + str9 + '</td>'; //Lateral Entry Credits
                            str1 += ' <td>' + str10 + '</td>'; //Max No Attempts
                            str1 += ' <td>' + str11 + '</td>'; //Deactive
                            str1 += ' </tr>';
                            count.push(cou++);
                            countt++;
                            itemChildList.row.add($(str1)).draw();
                        });
                        setTimeout($.unblockUI, 1000);
                    } else {
                        BootstrapDialog.alert("Data not Available in this Academic Year, Program, Branch, Semester....! Please Select Another Fields");
                    }
                }
            });

        } else {
            BootstrapDialog.alert("Please Select Required Field...!");
        }
    }
</script>
