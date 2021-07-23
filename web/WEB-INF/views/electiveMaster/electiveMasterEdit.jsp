<%-- 
    Document   : electiveMasterEdit
    Created on : Nov 26, 2018, 10:16:33 AM
    Author     : ankit.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Elective Code:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="elec_code" id="elec_code" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" data-validation-help="Max Length is 20"  data-validation="required" placeholder="Enter Elective Code" class="form-control" data-validation-error-msg="Please enter elective code!" value="${edit[0][3]}">                                              
                </div>
            </div>
            
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Elective Description:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="elec_desc" id="elec_desc" maxlength="50" data-validation-help="Max Length is 50"  data-validation="required" placeholder="Enter Elective Description" class="form-control" data-validation-error-msg="Please enter elective description!" value="${edit[0][4]}">                                              
                </div>
            </div>
<!--            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Institute Name:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="hidden" name="ins" id="ins" class="form-control" readonly="true" value="${edit[0][2]}">                                              
                </div>
            </div>
            -->
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Credits<span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="credit" id="credit" maxlength="3" data-validation-help="Max Length is 3"  onkeypress="return isNumberKey(event)"  placeholder="Enter Credit" class="form-control" data-validation-error-msg="Please enter credits!" value="${edit[0][6]}">                                              
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label" id="Ad">
                    <span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${edit[0][5]!='Y'}">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </c:if>
                    <c:if test="${edit[0][5]=='Y'}">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" value="Y"/>   
                    </c:if>          
                </div>
            </div>
        </div>
        <input type="hidden" name="elec_id" value="${edit[0][0]}"/>    
        <input type="hidden" name="ins_id" value="${edit[0][1]}"/>    
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>
    if ($("#cc").val() == 'Y') {
         document.getElementById("elec_code").setAttribute("readonly",true);
          document.getElementById("elec_desc").setAttribute("readonly",true);
    } 
    
    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }
    $(document).ready(function () {
        change();
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

    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                             //   $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "ElectiveMaster/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                     //   $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success(Success['update_success'], "Success!!");
                                            loadForm("ElectiveMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                      //  setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                     //   setTimeout($.unblockUI, 1000);
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