<%-- 
    Document   : basketMasterAdd
    Created on : Feb 6, 2019, 3:49:12 PM
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
                    <select class="form-control" id="prog_code" name="prog_code"   data-live-search="true" data-validation="required" onchange="getSemester();" data-validation-error-msg="Please select program code!">               
                        <option value="">Select</option>
                        <c:forEach items="${prog_list}" var="prog_list">
                            <option value="${prog_list[2]}"><c:out value="${prog_list[0]} / ${prog_list[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Sty Number:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="sty_num" name="sty_num"   data-live-search="true" onchange="getSectionCode();" data-validation="required" data-validation-error-msg="Please select sty number!">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Basket Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="basket_code" id="basket_code" onkeyup="this.value = this.value.toUpperCase();" maxlength="20" data-validation="required" data-validation-help="Max Length is 20"  class="form-control" placeholder="Enter Basket Code" onblur="getSectionCode();" data-validation-error-msg="Please enter basket code!">  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Subject Type:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="sub_type" name="sub_type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                        <option value="">Select</option>
                        <c:forEach items="${sub_list}" var="sub_list">
                            <option value="${sub_list[0]}"><c:out value="${sub_list[1]} / ${sub_list[2]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Section/Branch:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" >
                    <select class="form-control" id="sec_code" name="sec_code" multiple="true" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select section code!">
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Basket Description:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="bas_desc" id="bas_desc" maxlength="50" data-validation-help="Max Length is 50" data-validation="required"  class="form-control" placeholder="Enter Basket Description"  data-validation-error-msg="Please enter basket description!">  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label  class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Total no. of Subjects:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="total" id="total" maxlength="3" onkeypress="return isNumberKey(event)"onblur="clearminmax();" data-validation-help="Max Length is 3"  class="form-control" placeholder="Enter Total no. of Subjects">  
                    <label style="color: red"> Hint: Enter 0 for all subject</label>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label  class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Min no. of Subjects:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="min_no" id="min_no" maxlength="3" data-validation-help="Max Length is 3" onkeypress="return isNumberKey(event)" class="form-control" placeholder="Enter Min no. of Subjects">  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Max no. of Subjects:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" name="max_no" id="max_no" maxlength="3" onkeypress="return isNumberKey(event)" data-validation-help="Max Length is 3"  class="form-control" placeholder="Enter Max no. of Subjects">  
                </div>
            </div>
            <%-- <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Subject Preference Type:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class="col-lg-6">
                        <label class="radio-inline">
                            <input type="radio" id="export_To" name="export_To" checked="true" value="C"/>Check Box
                        </label>
                    </div>
                </div>
            </div>--%>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label" id="Ad">
                    <span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>           
                </div>
            </div>
        </div>
        <!--        <div class="row col-lg-12 form-group">                 
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Subject Preference:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                            <input type="radio" id="export_To" name="export_To" checked="true" value="C"/>Check Box
                        </div>
                        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                            <input type="radio" id="export_To" name="export_To" value="P"/>Combo
                        </div>
                        <div class="col-sm-6 col-lg-3 col-xs-6 col-md-6">
                            <input type="radio" id="export_To" name="export_To"  value="N"/>None
                        </div>
                    </div>
                </div>-->
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
    function clearBasketCode() {

    }
    function myReset() {
        $("#prog_code").val('').trigger("chosen:updated");
        $("#sec_code").empty();
        $("#sec_code").val('a').trigger("chosen:updated");
        $("#sty_num").empty();
        $("#sty_num").val('').trigger("chosen:updated");
        $("#sub_type").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }
    $("#prog_code").chosen();
    $("#sec_code").chosen();
    $("#sty_num").chosen();
    $("#sub_type").chosen();

    function getSectionCode() {
        var progarmid = $("#prog_code").val();
        if (progarmid == '' || progarmid == null) {
            $("#basket_code").val('');
            BootstrapDialog.alert("Program Select Program Code.");
        } else {
            $("#sec_code").empty();
            $.ajax({
                type: "POST",
                url: "basketMasterController/getSectionCode",
                data: '&bas_code=' + $("#basket_code").val() + '&programid=' + $("#prog_code").val() + '&styno=' + $("#sty_num").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    $.each(data.sec_list, function (i, item)
                    {
                        str1 += '<option value="' + item[2] + '">' + item[0] + '/' + item[1] + '</option>'
                    });
                    $("#sec_code").append(str1);
                    $("#sec_code").trigger("chosen:updated");
                },
                error: function (response) {
                    BootstrapDialog.alert("SomeThing Went Wrong...");
                }
            });
        }
    }

    function getSemester() {
        $("#basket_code").val('');
        $("#sec_code").empty();
        $("#sec_code").val('a').trigger("chosen:updated");
        $("#sub_type").val('').trigger("chosen:updated");
        $("#sty_num").empty();
        $.ajax({
            type: "POST",
            url: "basketMasterController/getSemester",
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
                var str1 = '<option value="">Select</option>';
                for (var i = parseInt(data.sem_list[0][0]); i <= parseInt(data.sem_list[0][1]); i++)
                {
                    str1 += '<option value="' + i + '">' + i + '</option>'
                }
                $("#sty_num").append(str1);
                $("#sty_num").trigger("chosen:updated");
            },
            error: function (response) {
                BootstrapDialog.alert("SomeThing Went Wrong...");
            }
        });
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
                                    url: "basketMasterController/save",
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
                                            toastr.success(Success['save_success'], "Success!!");
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
                                        // setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //  $.unblockUI();
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