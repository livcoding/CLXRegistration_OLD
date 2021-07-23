<%-- 
    Document   : subjectWiseSubSectionListReport
    Created on : Feb 15, 2019, 11:22:48 AM
    Author     : ankit.kumar
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
                            <select class="form-control" id="regId" name="regId" data-live-search="true" data-validation="required" onchange="getDepCode();"ub>               
                                <option value="">Select</option>
                                <c:forEach items="${reg_code}" var="reList">
                                    <option value="${reList[0]},${reList[1]}"><c:out value="${reList[1]}  /  ${reList[2]}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Department Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="dep_id" name="dep_id" data-live-search="true" data-validation="required" onchange="getSubCode();" data-validation="required">               
                                <option value="">Select</option>
                                <option value="ALL,ALL,ALL">ALL</option>

                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Code:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                            <select class="form-control" id="sub_id" name="sub_id" data-live-search="true" data-validation="required" data-validation="required">               
                                <option value="">Select</option>
                            </select>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Order By:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="ord_by" name="ord_by" checked="true" value="name"/>Student Name
                            </div>
                            <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                                <input type="radio" id="ord_by" name="ord_by"  value="rollno"/>Enrollment No
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Sorted By:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="sorted_by" name="sorted_by" checked="true" value="asc"/>Ascending
                            </div>
                            <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6">
                                <input type="radio" id="sorted_by" name="sorted_by"  value="desc"/>Descending
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group">                 
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Export To:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6 radio-inline">
                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                <input type="radio" id="export_To" name="export_To" checked="true" value="P"/>PDF
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
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $("#regId").chosen();
    $("#dep_id").chosen();
    $("#sub_id").chosen();

    function myReset() {
        $("#regId").val('').trigger("chosen:updated");
        $("#dep_id").val('').trigger("chosen:updated");
        $("#sub_id").val('').trigger("chosen:updated");
    }
    function getDepCode() {
        var regId = $("#regId").val();
        var dep_id = $("#dep_id").val();
        var carter = regId;
        var arlene3 = carter.split(",");
        $("#dep_id").empty();
        if (regId != '') {
            $.ajax({
                type: "POST",
                url: "subjectWiseSubSectionList/getDepCode",
                data: '&reg_id=' + arlene3[0],
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str2 = '';
                    var str2 = '<option value="">Select</option>';
                    $.each(data.dep_list, function (i, item)
                    {
                        str2 += '<option value="' + item[0] + ',' + item[1] + ',' + item[2] + '">' + item[1] + " / " + item[2] + '</option>'
                    });
                    $("#dep_id").append(str2);
                    $("#dep_id").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }
    function getSubCode() {
        $("#sub_id").val('').trigger("chosen:updated");
        var regId = $("#regId").val();
        var carter = regId;
        var arlene3 = carter.split(",");
        var dep_id = $("#dep_id").val();
        var carter_dep = dep_id;
        var arlene3_dep = carter_dep.split(",");
        $("#sub_id").empty();
        if (regId != '') {
            if (dep_id != '') {
                $.ajax({
                    type: "POST",
                    url: "subjectWiseSubSectionList/getSubCode",
                    data: '&reg_id=' + arlene3[0] + '&dep_id=' + arlene3_dep[0],
                    dataType: "json",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.sub_list, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + ',' + item[1] + ',' + item[2] + '">' + item[1] + " / " + item[2] + '</option>'
                        });
                        $("#sub_id").append(str1);
                        $("#sub_id").trigger("chosen:updated");
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Department Code..");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code..");
        }
    }

    function getReport() {
        var regId = $("#regId").val();
        var carter = regId;
        var arlene3 = carter.split(",");
        var dep_id = $("#dep_id").val();
        var carter_dep = dep_id;
        var arlene3_dep = carter_dep.split(",");
        var sub_id = $("#sub_id").val();
        var carter_dep = sub_id;
        var arlene3_sub = carter_dep.split(",");
        var radioValue = $("input[name='export_To']:checked").val();
        var orderVal = $("input[name='ord_by']:checked").val();
        var sortedVal = $("input[name='sorted_by']:checked").val();
        if (regId != '' && dep_id != '' && sub_id != '') {
            $.blockUI();
            window.location.assign('subjectWiseSubSectionList/getReport?excelfilename=SubjectWiseSubSectionlist&registrationid=' + arlene3[0] + '&registrationcode=' + arlene3[1] + '&departmentcode=' + arlene3_dep[1] + '&departmentdesc=' + arlene3_dep[2] + '&subjectid=' + arlene3_sub[0] + '&subjectcode=' + arlene3_sub[1] + '&subjectdesc=' + arlene3_sub[2] + '&sortedby=' + sortedVal + '&orderby=' + orderVal + '&exportto=' + radioValue + '');
            setTimeout($.unblockUI, 8000);
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }
</script>