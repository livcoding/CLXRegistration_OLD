<%-- 
    Document   : ExamPatterMasterAdd
    Created on : 8 AUG : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Component Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                    <select class="form-control" id="cmpCode" name="cmpCode" data-validation="required" onchange="getPatternCode(this.value);" data-live-search="true" data-validation-error-msg="Please select component code!">               
                        <option value="">Select</option>
                        <c:forEach items="${component_code}" var="component_code">
                            <option value="${component_code.subjectcomponentdesc}"><c:out value="${component_code.subjectcomponentcode} -- ${component_code.subjectcomponentdesc}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label">Pattern Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="patternCode" id="patternCode" maxlength="20" onkeyup="this.value = this.value.toUpperCase();"data-validation-help="Max Length is 20"  data-validation="required" placeholder="Enter Pattern Code" class="form-control" data-validation-error-msg="Please enter pattern code!" >
                </div>
            </div>
        </div> 
        <div class=" col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label" >Pattern Name:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                   <input type="text" name="patternName" id="patternName" maxlength="40" data-validation-help="Max Length is 40" data-validation="required" placeholder="Enter Pattern Name" class="form-control"   data-validation-error-msg="Please enter pattern name!">
                </div>
            </div>
        </div>
        <div class=" col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label" >Min Attendance:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
              <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="minAttendance" id="minAttendance" maxlength="4" data-validation-help="Max Length is 4"  onkeypress="return isNumberKey(event)"  data-validation="required" data-validation-allowing="float" placeholder="Enter Min Attendance" class="form-control" data-validation-error-msg="Please enter min attendance!" >
                </div>
            </div>
        </div>
        
        <div class="col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label" id="Ad"><span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                </div>
            </div>
        </div>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form>

<script>
    
     $("#cmpCode").chosen();
    function myReset() {
        $("#cmpCode").val('').trigger("chosen:updated");
        $("#sty_type_code").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }
    function getPatternCode(v){
        $("#patternCode").val(v);
    }

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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "examPatternMaster/save",
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
                                            toastr.success('Record Successfully Saved.', "Success!!");
                                            loadForm("examPatternMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                         setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                         $.unblockUI();
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


