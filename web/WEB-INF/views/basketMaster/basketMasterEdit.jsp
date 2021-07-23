<%-- 
    Document   : basketMasterEdit
    Created on : Feb 6, 2019, 3:49:22 PM
    Author     : ankit.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">  
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Program Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="prog_code" id="prog_code" class="form-control" readonly="true" value="${edit[0][12]}" data-validation-error-msg="Please select program code!">  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Sty Number:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="sty_num" id="sty_num" class="form-control" readonly="true" value="${edit[0][4]}" data-validation-error-msg="Please select sty number!"> 
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Basket Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="basket_code" id="basket_code" class="form-control" readonly="true" data-validation="required" value="${edit[0][1]}" data-validation-error-msg="Please enter basket code!">  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Subject Type:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${subtypelov=='hide'}">
                        <c:forEach items="${sub_list}" var="sub_list">
                            <c:if test="${sub_list[0]==edit[0][5]}">
                                <input type="text" name="basket_code" id="basket_code" class="form-control" readonly="true" data-validation="required" value="${sub_list[1]} / ${sub_list[2]}"  data-validation-error-msg="Please select subject type!">
                            </c:if>
                        </c:forEach> 
                    </c:if>
                    <c:if test="${subtypelov!='hide'}">
                        <select class="form-control" id="sub_type" name="sub_type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="">Select</option>
                            <c:forEach items="${sub_list}" var="sub_list">
                                <c:if test="${sub_list[0]==edit[0][5]}">
                                    <option selected="true" value="${sub_list[0]}"><c:out value="${sub_list[1]} / ${sub_list[2]}"/></option>
                                </c:if>
                                <c:if test="${sub_list[0]!=edit[0][5]}">
                                    <option value="${sub_list[0]}"><c:out value="${sub_list[1]} / ${sub_list[2]}"/></option>
                                </c:if>
                            </c:forEach> 
                        </select>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Section/Branch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="sec_code" id="sec_code" class="form-control" readonly="true" value="${edit[0][13]}" data-validation-error-msg="Please select section code!"> 
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Basket Description:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" >
                    <input type="text" name="bas_desc" id="bas_desc" maxlength="50" data-validation-help="Max Length is 50" data-validation="required" value="${edit[0][2]}" class="form-control" placeholder="Enter Basket Description" data-validation-error-msg="Please enter basket description!">  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Total no. of Subjects:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="total" id="total" maxlength="3" onkeypress="return isNumberKey(event)" onblur="clearminmax();" data-validation-help="Max Length is 3" value="${edit[0][11]}"  class="form-control" placeholder="Enter Total no. of Subjects">  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label  class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Min no. of Subjects:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="min_no" id="min_no" maxlength="3" data-validation-help="Max Length is 3" onkeypress="return isNumberKey(event)" value="${edit[0][6]}" class="form-control" placeholder="Enter Min no. of Subjects">  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label  class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Max no. of Subjects:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="max_no" id="max_no" maxlength="3" onkeypress="return isNumberKey(event)" data-validation-help="Max Length is 3"  value="${edit[0][7]}" class="form-control" placeholder="Enter Max no. of Subjects">  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Seq Id:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="seq_id" id="seq_id" maxlength="3" onkeypress="return isNumberKey(event)" data-validation-help="Max Length is 3" value="${edit[0][14]}" class="form-control" placeholder="Enter Seq Id">  
                </div>
            </div>
        </div>
        <%--  <div class="row col-lg-12 form-group">
             <div class="row col-lg-6 form-group">
                 <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Subject Preference Type:</label>
                 <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                     <c:if test="${fn:contains(edit[0][10],'N')}">
                          <input type="checkbox" id="sub_num_count" name="sub_num_count" value="N"/> 
                      </c:if>
                      <c:if test="${fn:contains(edit[0][10],'Y')}">
                          <input type="checkbox" id="sub_num_count" checked="true" name="sub_num_count" value="Y"/> 
                      </c:if>
                     <c:if test="${fn:contains(edit[0][9],'C')}">
                         <div class="col-lg-6">
                             <label class="radio-inline">
                                 <input type="radio" id="export_To" name="export_To" checked="true" value="C"/>Check Box
                             </label>
                         </div>
                                           <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                                 <input type="radio" id="export_To" name="export_To" value="P"/>Combo
                                             </div>
                                             <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                                 <input type="radio" id="export_To" name="export_To"  value="N"/>None
                                             </div>
                     </c:if>
                     <c:if test="${fn:contains(edit[0][9],'P')}">
                         <div class="col-lg-6">
                             <label class="radio-inline">
                                 <input type="radio" id="export_To" name="export_To"  value="C"/>Check Box
                             </label>
                         </div>
                                             <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                                 <input type="radio" id="export_To" name="export_To"  checked="true" value="P"/>Combo
                                             </div>
                                             <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                                 <input type="radio" id="export_To" name="export_To"  value="N"/>None
                                             </div>
                     </c:if>
                     <c:if test="${fn:contains(edit[0][9],'N')}">
                         <div class="col-lg-6">
                             <label class="radio-inline">
                                 <input type="radio" id="export_To" name="export_To"  value="C"/>Check Box
                             </label>
                         </div>
                                            <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                                 <input type="radio" id="export_To" name="export_To" value="P"/>Combo
                                             </div>
                                             <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                                                 <input type="radio" id="export_To" name="export_To" checked="true" value="N"/>None
                                             </div>-->
                     </c:if>
                 </div>
             </div>--%>
        <div class="row col-lg-6 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label" id="Ad">
                <span style="color: green"> Active</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                <c:if test="${edit[0][8]=='N'}">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                </c:if>
                <c:if test="${edit[0][8]=='Y'}">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" value="Y"/>   
                </c:if>          
            </div>
        </div>
    </div>
    <!--        <div class="row col-lg-12 form-group">                 
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Preference:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
    <c:if test="${fn:contains(edit[0][9],'C')}">
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To" checked="true" value="C"/>Check Box
        </div>
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To" value="P"/>Combo
        </div>
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To"  value="N"/>None
        </div>
    </c:if>
    <c:if test="${fn:contains(edit[0][9],'P')}">
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To"  value="C"/>Check Box
        </div>
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To"  checked="true" value="P"/>Combo
        </div>
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To"  value="N"/>None
        </div>
    </c:if>
    <c:if test="${fn:contains(edit[0][9],'N')}">
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To"  value="C"/>Check Box
        </div>
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To" value="P"/>Combo
        </div>
        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
            <input type="radio" id="export_To" name="export_To" checked="true" value="N"/>None
        </div>
    </c:if>
</div>
</div>-->
    <input type="hidden" id="bask_id" name="bask_id" value="${edit[0][0]}">
    <input type="hidden" id="prog_id" name="prog_id" value="${edit[0][3]}">
    <input type="hidden" id="sem_id" name="sem_id" value="${edit[0][4]}">
    <input type="hidden" id="sub_type_id" name="sub_type_id" value="${edit[0][5]}">
    <input type="hidden" id="sec_id" name="sec_id" value="${edit[0][15]}">
    <div class="row col-lg-12">
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Update</a>
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
        </div>
    </div>
</div>
</form>
<script>
    var val = getValue();
    if (val == 'Y') {
        document.getElementById("bas_desc").setAttribute("readonly", true);
    }
    $("#sub_type").chosen();
    $(document).ready(function () {
        change();
    });
    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
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
                                    url: "basketMasterController/update",
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
                                            var prog_code = $("#prog_code_list").val();
                                            var sec_code = $("#sec_code_list").val();
                                            if (prog_code != '' && sec_code != '') {
                                                closePage();
                                                getBasketMaster();
                                            } else {
                                                loadForm("basketMasterController/list");
                                            }
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        //  setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //   $.unblockUI();
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
    function clearminmax() {
        $("#min_no").val('');
        $("#max_no").val('');
    }
    $("#max_no").focusout(function () {
        if ($("#total").val() !== '') {
            if ($("#max_no").val() !== '') {
                if (parseFloat($("#total").val()) >= parseFloat($("#max_no").val())) {
                    if (parseFloat($("#min_no").val()) > parseFloat($("#max_no").val())) {
                        BootstrapDialog.alert("Max no. of Subjects should not be less than Min no. of Subjects. ");
                        document.getElementById("max_no").value = '';
                    }
                } else {
                    if (parseFloat($("#total").val()) != 0) {
                        BootstrapDialog.alert("Max no. of Subjects should not be greater than Total no. of Subjects.");
                        document.getElementById("max_no").value = '';
                    }
                }
            }
        } else {
            BootstrapDialog.alert("Plese select Total No. of Subjects first!");
            document.getElementById("max_no").value = '';
        }
    });
    $("#min_no").focusout(function () {
        if ($("#total").val() !== '') {
            if ($("#min_no").val() !== '') {
                if (parseFloat($("#total").val()) >= parseFloat($("#min_no").val())) {
                    if (parseFloat($("#min_no").val()) > parseFloat($("#max_no").val())) {
                        BootstrapDialog.alert("Min no. of Subjects should not be greater than Max no. of Subjects. ");
                        document.getElementById("min_no").value = '';
                    }
                } else {
                    if (parseFloat($("#total").val()) != 0) {
                        BootstrapDialog.alert("Min no. of Subjects should not be greater than Total no. of Subjects.");
                        document.getElementById("min_no").value = '';
                    }
                }
            }
        } else {
            BootstrapDialog.alert("Plese select Total No. of Subjects first!");
            document.getElementById("min_no").value = '';
        }
    });
</script>