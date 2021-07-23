<%-- 
    Document   : subjectComponentMasterEdit
    Created on : 5 Dec, 2018, 4:57:09 PM
    Author     : deepak.gupta
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" modelAttribute="" id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">                
        <div style="height: 12px"></div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Component Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="subLodeCode" id="subLodeCode" maxlength="1" value="${data.subjectcomponentcode}" class="form-control" readonly="true" data-validation-error-msg="Please select subject component code!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Component Description:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="subLoadDesc" id="subLoadDesc" maxlength="60" value="${data.subjectcomponentdesc}"  data-validation-help="Max Length is 60" data-validation="required" placeholder="Enter Subject Component Description" class="form-control" data-validation-error-msg="Please enter subject component description!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">No. of Days Per Week:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="classFormula" id="classFormula" maxlength="3" value="${data.classperweekformula}" onkeypress="return isNumberKey(event)" data-validation-help="Max Length is 3" data-validation="required" placeholder="Enter Class Per Week Formula" class="form-control" data-validation-error-msg="Please enter class per week formula!">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Seq ID:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" data-validation-help="Max Length is 4" value="${data.seqid}" data-validation-optional="number" id="seqId" onkeypress="return isNumberKey(event)" maxlength="4" placeholder="Enter Sequence ID" name="seqId" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-8 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data.deactive!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data.deactive=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
        <input type="hidden" class="form-control" id="instituteId" name="instituteId" value="${data.id.instituteid}"/>
        <input type="hidden" class="form-control" id="subjectComponentId" name="subjectComponentId" value="${data.id.subjectcomponentid}"/>
    </div>
</form>

<script>
    var val = getValue();
    if (val == 'Y') {
        var desc = document.getElementById("subLoadDesc").value;
        document.getElementById("subLoadDesc").addEventListener("keydown", function () {
            this.value = desc
        });
        document.getElementById("subLoadDesc").setAttribute("readonly", true);
    }
    var code = document.getElementById("subLodeCode").value;
    document.getElementById("subLodeCode").addEventListener("keydown", function () {
        this.value = code
    });

    function change() {
        if ($("#deactive").prop('checked') == true) {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        } else
        {
            $("#deactive").val("Y");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: red"> Deactive</span>');
        }
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
                                // $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "subjectComponentMaster/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        //  $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Record  Successfully Updated.', "Success!!");
                                            loadForm("subjectComponentMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        //setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //setTimeout($.unblockUI, 1000);
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
