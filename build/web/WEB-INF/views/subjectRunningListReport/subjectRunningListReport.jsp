<%-- 
    Document   : subjectRunningListReport
    Created on : 7 Jan, 2019, 2:30:58 PM
    Author     : deepak.gupta
--%>

<%@include file="../mainjstl.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <form method="POST" id="ajaxform" class="form-horizontal">

                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Semester Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="regId" name="regId" onchange="resetother();getSubjectType();" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                                <c:forEach items="${regList}" var="reList">
                                    <option value="${reList.id.registrationid}~@~${reList.registrationcode}"><c:out value="${reList.registrationcode}   ---   ${reList.registrationdesc}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Type:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="subjectType" name="subjectType" onchange="resetother2();getStyNumber();"data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">STY Number:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="semester" name="semester" data-live-search="true" onchange="getDepartment();" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Department:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="department" name="department" data-live-search="true" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>

                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/> PDF
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12">
                        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat"> Download</a>
                            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                            <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                    <div class="row col-lg-12 form-group"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset() {
        $("#regId").val('').trigger("chosen:updated");
        $("#subjectType").empty();
        $("#subjectType").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#department").empty();
        $("#department").val('').trigger("chosen:updated");

    }
    function resetother() {
        $("#subjectType").empty();
        $("#subjectType").val('').trigger("chosen:updated");
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#department").empty();
        $("#department").val('').trigger("chosen:updated");
    }
    function resetother2() {
        $("#semester").empty();
        $("#semester").val('').trigger("chosen:updated");
        $("#department").empty();
        $("#department").val('').trigger("chosen:updated");
    }

    function getSubjectType() {
        $('#datatable').DataTable().clear().draw();
        if ($("#regId").val() != '') {
            $.ajax({
                url: "subjectRunningListReport/getSubjectType",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "registrationid=" + $("#regId").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#subjectType").empty();
                    if (data.subjecttype != null || data.subjecttype != '') {
                        var str1 = '';
                        str1 = '<option value="">Select</option>';
                        str1 += '<option value="All~@~All">All</option>';
                        $.each(data.subjecttype, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[1] + '">' + item[1] + " -- " + item[2] + '</option>'
                        });
                        $("#subjectType").append(str1);
                        $("#subjectType").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Subject Type not found for this semester, Please select another semester.");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first.. !");
        }
    }

    function getStyNumber() {
        $('#datatable').DataTable().clear().draw();
        if ($("#regId").val() != '') {
            if ($("#subjectType").val() != '') {
                $.ajax({
                    url: "subjectRunningListReport/getStyNumber",
                    dataType: 'json',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: "registrationid=" + $("#regId").val() + "&subjecttypeid=" + $("#subjectType").val(),
                    error: (function (response) {
                        BootstrapDialog.alert('Server Error' + response);
                    }),
                    success: function (data) {
                        $("#semester").empty();
                        if (data.stynumber != null  || data.stynumber != '') {
                            var str1 = '';
                            var str1 = '<option value="">Select</option>';
                            $.each(data.stynumber, function (i, item)
                            {
                                str1 += '<option value="' + item + '">' + item + '</option>'
                            });
                            $("#semester").append(str1);
                            $("#semester").trigger("chosen:updated");
                        } else {
                            BootstrapDialog.alert("Sty Number not found for this Subject Type, Please select another Subject Type.");
                        }
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Subject Type first.. !");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code first.. !");
        }
    }

    function getDepartment() {
        $('#datatable').DataTable().clear().draw();
        if ($("#regId").val() != '') {
            if ($("#subjectType").val() != '') {
                if ($("#semester").val() != '') {
                    $.ajax({
                        url: "subjectRunningListReport/getDepartment",
                        dataType: 'json',
                        async: false,
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: "registrationid=" + $("#regId").val() + "&subjecttypeid=" + $("#subjectType").val() + "&stynumber=" + $("#semester").val(),
                        error: (function (response) {
                            BootstrapDialog.alert('Server Error' + response);
                        }),
                        success: function (data) {
                            $("#department").empty();
                            if (data.department != null  || data.department != '') {
                                var str1 = '';
                                var str1 = '<option value="">Select</option>';
                                $.each(data.department, function (i, item)
                                {
                                    str1 += '<option value="' + item[0] + '~@~' + item[1] + '">' + item[1] + " -- " + item[2] + '</option>'
                                });
                                $("#department").append(str1);
                                $("#department").trigger("chosen:updated");
                            } else {
                                BootstrapDialog.alert("Department not found for this Sty Number, Please select another Sty Number.");
                            }
                        }
                    });
                } else {
                    BootstrapDialog.alert("Please Select Sty Number first.. !");
                }
            } else {
                BootstrapDialog.alert("Please Select Subject Type first.. !");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code first.. !");
        }
    }

    $("#regId").chosen();
    $("#subjectType").chosen();
    $("#semester").chosen();
    $("#department").chosen();

    function getReport() {
        var regId = $("#regId").val();
        var subjectType = $("#subjectType").val();
        var semester = $("#semester").val();
        var department = $("#department").val();
        var radioValue = $("input[name='export_To']:checked").val();
        if (regId != '' && subjectType != '' && semester != '' && department != '') {
            $.blockUI();
            window.location.assign('subjectRunningListReport/subjectRunningListReport?excelfilename=registrationReport&regId=' + regId + '&subjectType=' + subjectType + '&semester=' + semester + '&department=' + department + '&radioValue=' + radioValue + '');
            setTimeout($.unblockUI, 3000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>