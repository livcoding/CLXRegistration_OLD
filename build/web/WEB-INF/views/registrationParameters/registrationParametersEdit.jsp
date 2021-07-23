<%-- 
    Document   : RegistrationParametersEdit
    Created on : 17 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">                  
        <div class="row col-lg-12 form-group" >
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Parameters ID:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <input type="text" name="parameterid" id="parameterid" value="${data.id.parameterid}" readonly="true" class="form-control" >
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Parameters Desc.:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <input type="text" name="parameter" id="parameter"  value="${data.parameter}" readonly="true" class="form-control" >
            </div>
        </div>
        <c:if test="${data.datatype=='N'}">
            <div class="row col-lg-12 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Parameter Value:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="parametervalue" id="parametervalue"  value="${data.parametervalue}" maxlength="50" data-validation-help="Max Length is 50" data-validation-allowing="float,negative" data-validation="number" placeholder="Enter Parameter Value" class="form-control"  data-validation-error-msg="Please enter numeric value only!">
                </div>
            </div>
        </c:if>
        <c:if test="${data.datatype!='N'}">
            <div class="row col-lg-12 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Parameter Value:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="parametervalue" id="parametervalue"  value="${data.parametervalue}" maxlength="50" data-validation-help="Max Length is 50" placeholder="Enter Parameter Value" data-validation="required" class="form-control"  data-validation-error-msg="Please enter parameter value!">
                </div>
            </div>

        </c:if>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:updateData();" class="btn btn-success btn-sm btn-flat">Update</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
    <input type="hidden" name="moduleid"  value="${data.id.moduleid}">
    <input type="hidden" name="instituteid" value="${data.id.instituteid}">
    <input type="hidden" name="datatype" value="${data.datatype}">
</form>
<script>


    $("#styType").chosen({width: "100%"});

    function myReset() {
        $("#styType").val('').trigger("chosen:updated");
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
                                    url: "registrationParameters/update",
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
                                            toastr.success(Success['update_success'], "Success!!");
//                                            loadForm("registrationParameters/list");
                                            closePage();
                                            getGridDate();
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
    function updateData() {
        $("#ajaxform").submit();
    }

</script>