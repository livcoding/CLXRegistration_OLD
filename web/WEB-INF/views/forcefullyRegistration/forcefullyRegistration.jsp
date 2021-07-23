<%-- 
    Document   : forcefullyRegistration
    Created on : Feb 12, 2019, 2:32:35 PM
    Author     : ankit.kumar
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
                <form method="POST" id="ajaxform" class="form-horizontal">
                    <div class="col-lg-12" style="margin-top:10px;">
                        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
                        <div class="row col-lg-12 form-group" id="divContainer">  
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-8 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Code:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                        <select class="form-control" id="sty_code" name="sty_code"   data-live-search="true" data-validation="required">               
                                            <option value="">Select</option>
                                            <c:forEach items="${semester}" var="sem_code">
                                                <option value="${sem_code[0]}"><c:out value="${sem_code[1]} / ${sem_code[2]}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-2 form-group">
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-8 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Program Code:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                        <select class="form-control" id="prog_code" name="prog_code"   data-live-search="true" data-validation="required" onchange="getBranchCode();">               
                                            <option value="">Select</option>
                                            <c:forEach items="${prog_list}" var="prog_list">
                                                <option value="${prog_list[2]}"><c:out value="${prog_list[0]} / ${prog_list[1]}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-8 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Branch Code:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                        <select class="form-control" id="br_code" name="br_code"   data-live-search="true" data-validation="required" onchange="getRegistraionNumber();">               
                                            <option value="">Select</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-8 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Enrollment Number:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                        <select class="form-control" id="reg_num" name="reg_num"   data-live-search="true" data-validation="required" onchange="getStyNumber(this.value);">               
                                            <option value="">Select</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-8 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Sty Number:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                        <input type="text" id="sty_num" name="sty_num" class="form-control" readonly="true" data-live-search="true" data-validation="required">
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-8 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Reg. Sty Number:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                        <input type="text" id="reg_sty_num" name="reg_sty_num" class="form-control" readonly="true" data-live-search="true" data-validation="required">
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-10 form-group checkbox-inline">
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                    </div>
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                        <input type="checkbox" id="allow_for_sub_choice" name="allow_for_sub_choice" value="Y"/>Allow For Subject Choice
                                    </div>
                                    <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6">
                                        <input type="checkbox" id="fees_paid" name="fees_paid" value="Y"/>Fees Paid
                                    </div>
                                    <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6">   
                                        <input type="checkbox" id="hostel_allow" name="hostel_allow" value="Y"/>Hostel Allowed
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-10 form-group checkbox-inline">
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                    </div>
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                        <input type="checkbox" id="pr_event_freezed" name="pr_event_freezed" value="Y"/>Pr Event Freezeed
                                    </div>
                                    <div class="col-sm-6 col-lg-2 col-xs-6 col-md-6">
                                        <input type="checkbox" id="document_verified" name="document_verified" value="Y"/>Document-Verified
                                    </div>
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                        <input type="checkbox" id="transport_allow" name="transport_allow" value="Y"/>Transport Allowed
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group">
                                <div class="row col-lg-10 form-group checkbox-inline">
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                    </div>
                                    <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                        <input type="checkbox" id="allow_pay_fee" name="allow_pay_fee" value="Y"/>Allow to Pay Fee
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12">
                                <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                                    <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Update</a>
                                    <button onclick="myReset1()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                                    <a href="javascript:blank();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                                </div>
                            </div>
                        </div>
                    </div> 
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function myReset1() {
        $("#reg_num").val('');
        $("#reg_num").empty();
        $("#reg_num").trigger("chosen:updated");
        $("#prog_code").val('').trigger("chosen:updated");
        $("#sty_code").val('').trigger("chosen:updated");
        $("#br_code").val('');
        $("#br_code").empty();
        $("#br_code").trigger("chosen:updated");
    }
    $("#prog_code").chosen();
    $("#sty_code").chosen();
    $("#br_code").chosen();
    $("#reg_num").chosen();

    function myReset() {
        $("#reg_num").val('').trigger("chosen:updated");
        $("#sty_num").val('');
        $("#hostel_allow").prop("checked", false);
        $("#fees_paid").prop("checked", false);
        $("#allow_pay_fee").prop("checked", false);
        $("#allow_for_sub_choice").prop("checked", false);
        $("#pr_event_freezed").prop("checked", false);
        $("#document_verified").prop("checked", false);
        $("#transport_allow").prop("checked", false);
        getRegistraionNumber();

    }
    function getStyNumber(id)
    {
        $("#sty_num").val('');
        var carter = id;
        var arlene3 = carter.split("~@~");
        $("#sty_num").val(arlene3[1]);
        $("#reg_sty_num").val(arlene3[10]);
        if (arlene3[2] == 'Y')
        {
            $('#allow_for_sub_choice').prop('checked', true);

        } else {
            $('#allow_for_sub_choice').prop('checked', false);

        }
        if (arlene3[3] == 'Y')
        {
            $('#fees_paid').prop('checked', true);

        } else {
            $('#fees_paid').prop('checked', false);

        }
        if (arlene3[4] == 'Y')
        {
            $('#hostel_allow').prop('checked', true);
        } else {
            $('#hostel_allow').prop('checked', false);
        }
        if (arlene3[5] == 'Y')
        {
            $('#pr_event_freezed').prop('checked', true);
        } else {
            $('#pr_event_freezed').prop('checked', false);
        }
        if (arlene3[6] == 'Y')
        {
            $('#document_verified').prop('checked', true);
        } else {
            $('#document_verified').prop('checked', false);
        }
        if (arlene3[7] == 'Y')
        {
            $('#transport_allow').prop('checked', true);
        } else {
            $('#transport_allow').prop('checked', false);
        }
        if (arlene3[8] == 'Y')
        {
            $('#allow_pay_fee').prop('checked', true);
        } else {
            $('#allow_pay_fee').prop('checked', false);
        }
    }

    function getBranchCode() {
        $("#reg_num").val('').trigger("chosen:updated");
        $("#br_code").val('').trigger("chosen:updated");
        $("#sty_num").val('').trigger("chosen:updated");
        if ($("#prog_code").val() != '') {
            $("#br_code").empty();
            $.ajax({
                type: "POST",
                url: "forcefullyRegistration/getBranchCode",
                data: '&prog_code=' + $("#prog_code").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    str1 += '<option value="">Select</option>';
                    if (data.branch_list != null && data.branch_list != '') {
                        $.each(data.branch_list, function (i, item)
                        {
                            str1 += '<option value="' + item[4] + '">' + item[0] + '/' + item[1] + '</option>'
                        });
                        $("#br_code").append(str1);
                        $("#br_code").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("No Data Found...");
                    }
                },
                error: function (response) {
                    BootstrapDialog.alert("Some Thing Went Wrong...");
                }
            });
        } else {
            BootstrapDialog.alert("Please select Program.");
        }
    }

    function getRegistraionNumber() {
        if ($("#prog_code").val() != '' && $("#br_code").val() != '' && $("#sty_code").val() != '') {
            $("#reg_num").empty();
            $.ajax({
                type: "POST",
                url: "forcefullyRegistration/getRegistrationNumber",
                data: '&prog_code=' + $("#prog_code").val() + '&br_code=' + $("#br_code").val() + '&sty_code=' + $("#sty_code").val(),
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
                    if (data.reg_list != null && data.reg_list != '') {
                        $.each(data.reg_list, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[3] + '~@~' + item[4] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '~@~' + item[10] + '~@~' + item[11] + '~@~' + item[12] + '">' + item[1] + ' / ' + item[2] + '</option>'
                        });
                        $("#reg_num").append(str1);
                        $("#reg_num").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("No Data Found..");
                    }
                },
                error: function (response) {
                    BootstrapDialog.alert("Something Went Wrong...");
                }
            });
        } else {
            BootstrapDialog.alert("Select required fields.");
        }
    }

    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                $.ajax({
                                    type: "POST",
                                    url: "forcefullyRegistration/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success(Success['update_success'], "Success!!");
                                            myReset();
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 2000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

    function saveData() {
        $("#ajaxform").submit();
    }
</script>