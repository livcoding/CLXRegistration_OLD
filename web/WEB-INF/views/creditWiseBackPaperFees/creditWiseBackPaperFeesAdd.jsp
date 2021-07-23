<%-- 
    Document   : CreditWiseBackPaperFeesAdd
    Created on : 11 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">                 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">STY Type:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <select class="form-control" id="styType" name="styType" data-validation="required" data-validation-error-msg="Please select sty type!">
                        <option value="">Select</option>
                        <c:forEach items="${styType}" var="list">
                            <option value="${list[0]}"><c:out value="${list[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Fee Amount:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="feeAmount" id="feeAmount" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" placeholder="Enter Fee Amount" onkeypress="return isNumberKey(event)" class="form-control"  data-validation-error-msg="Please enter fee amount!">
                </div>
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Credit From:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="creditFrom" id="creditFrom" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-allowing="float"  placeholder="Credit From" onkeypress="return isNumberKey(event)" class="form-control"  data-validation-error-msg="Please enter credit from!">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Credit Up To:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="creditUpTo" id="creditUpTo" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-allowing="float" placeholder="Credit UpTo" onkeypress="return isNumberKey(event)" class="form-control"  data-validation-error-msg="Please enter credit to!">
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>


    $("#styType").chosen({width: "100%"});

    function myReset() {
        $("#styType").val('').trigger("chosen:updated");
    }

    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "creditWiseBackPaperFees/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("creditWiseBackPaperFees/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                        setTimeout($.unblockUI, 1000);
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);
    function saveData() {
        var from = $("#creditFrom").val();
        var to = $("#creditUpTo").val();
        var creditFrom = parseFloat($("#creditFrom").val());
        var creditUpTo = parseFloat($("#creditUpTo").val());
        if (from != '' || to != '') {
            if (creditFrom < creditUpTo) {
                $("#ajaxform").submit();
            } else if (creditFrom > creditUpTo) {
                BootstrapDialog.alert("Value of CreditUpTo should not be less than the Value of CreditFrom.");
                $("#creditUpTo").val('');
            } else if (creditFrom == creditUpTo) {
                BootstrapDialog.alert("Value of CreditUpTo should not be equal to Value of CreditFrom.");
                $("#creditUpTo").val('');
            }
        } else {
            $("#ajaxform").submit();

        }
    }

</script>