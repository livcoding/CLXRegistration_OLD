<%-- 
    Document   : subjectMasterAdd
    Created on : 17 Nov, 2018, 2:30:46 PM
    Author     : Nazar.Mohammad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">                 
        <div style="height: 12px"></div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-left: 10px;">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 20" autocomplete="off" onkeyup="this.value = this.value.toUpperCase();" data-validation="required" id="subjectcode" maxlength="20" placeholder="Enter Subject Code " name="subjectcode" class="form-control has-help-txt" data-validation-error-msg="Please enter subject code!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Name:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 160"  autocomplete="off" data-validation="required" id="subjectdesc" maxlength="160" placeholder="Enter Subject Name " name="subjectdesc" class="form-control has-help-txt" data-validation-error-msg="Please select subject name!"/>
                </div>
            </div>
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-left: 10px;">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Short Name:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 20"   autocomplete="off" id="shortname" maxlength="20" placeholder="Enter Short Name" name="shortname" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Nature:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="subjecttype" name="subjecttype" onchange="enableSubjectComponent();" data-validation="required"  data-live-search="true" data-validation-error-msg="Please select subject nature!">
                        <option value="">Select</option>
                        <option value="A">Audit</option>
                        <option value="G">General</option>
                        <option value='K'>Mock</option>
                        <option value="W">Workshop</option>
                        <option value="S">Sport</option>
                        <option value="M">Major Project</option>
                        <option value="N">Minor Project</option>
                    </select>
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-left: 10px;">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Total Marks:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 5" autocomplete="off" onblur="totalMarks();totalpassing();"  id="totalmarks" maxlength="5" placeholder="Enter Toral Marks " name="totalmarks" onkeypress="return isNumberKey(event)" class="form-control has-help-txt"/>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Passing Marks:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 5"  autocomplete="off"onblur="totalpassing();"  id="pMarks" maxlength="5" placeholder="Enter Passing Marks " name="pMarks"  onkeypress="return isNumberKey(event)" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-left: 10px;">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Pattern:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="sPattern" name="sPattern" data-validation="required"  data-live-search="true" data-validation-error-msg="Please select subject pattern!">
                        <option value="">Select</option>
                        <c:forEach items="${patternList}" var="patternList">
                            <option value="${patternList.id.patternid}"><c:out value="${patternList.patterncode} - ${patternList.patternname}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Credits:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 3"  autocomplete="off" readonly="true" id="credits" maxlength="3" placeholder="Enter Credits" name="credits" onkeypress="return isNumberKey(event)" class="form-control has-help-txt" data-validation-error-msg="Please enter credits!"/>
                </div>
            </div> 

        </div>
        <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                <span style="color: green"> Active</span></label>
            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>           
            </div>
        </div>      
        <div class="col-lg-12" style="margin-top:10px;">
            <table id="datatable1" class="box table table-hover table-condensed table-bordered"  cellspacing="0">
                <thead id="header" >
                    <tr class="" style="background-color:#008080;color:white" >
                        <th colspan="2">Subject Component</th>
                        <th width="10px;" >*Total CCP Marks</th>
                        <th width="10px;" >*LTP Passing Marks</th>
                        <th width="10px;" >No. of Hours</th>
                        <th width="10px;" >No. of Class(Week)</th>
                        <th width="10px;" >Total Class</th>
                        <th width="10px;" >Total Marks</th>
                        <th width="10px;" >Passing Marks</th>
                        <th width="5px;" >Active</th>
                    </tr>
                </thead>  
                <tbody>
                    <% int i = 1;%>
                    <c:forEach items="${componentList}" var="list" >
                        <tr id="${list.getIdStr()}" >
                            <td id="${list.getIdStr()}" style="width:20px"><input type="checkbox" class=""  onclick="getChekedValue(<%=i%>);" id="chk<%=i%>" name="chk<%=i%>"  value="${list.getIdStr()}"/>                       
                            </td>
                            <td style="width:50px">${list.subjectcomponentcode}</td>                     
                            <td><input type="text" disabled="true"  autocomplete="off"  id="totalccpmarks<%=i%>" name="totalccpmarks<%=i%>" style="width:70px" onkeypress="return isNumberKey(event);" onkeyup="setCredit();" maxlength="3" class="form-control has-help-txt" data-validation-error-msg="Please enter total ccp marks!"/></td>
                            <td><input type="text"  disabled="true" autocomplete="off"   id="ltppassingmarks<%=i%>" name="ltppassingmarks<%=i%>" style="width:90px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" data-validation-error-msg="Please enter LTP passing marks!"/></td>
                            <td><input type="text" disabled="true" autocomplete="off"   id="noofhours<%=i%>" name="noofhours<%=i%>" style="width:60px" onkeypress="return isNumberKey(event)"   maxlength="3"  class="form-control has-help-txt"/></td>
                            <td><input type="text" disabled="true"  autocomplete="off"  id="noofclassinaweek<%=i%>" name ="noofclassinaweek<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt"/></td>
                            <td><input type="text" disabled="true"  autocomplete="off"  id="totalclass<%=i%>" name="totalclass<%=i%>" style="width:60px" onkeypress="return isNumberKey(event)"  class="form-control has-help-txt"/></td>
                            <td><input type="text" disabled="true" autocomplete="off"   id="totalmarks<%=i%>" name="totalmarks<%=i%>" style="width:60px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt"/></td>
                            <td><input type="text"disabled="true" autocomplete="off"   id="passingmarks<%=i%>" name="passingmarks<%=i%>" style="width:60px" onkeypress="return isNumberKey(event)"  maxlength="3"   class="form-control has-help-txt"/></td>
                            <td><input type="checkbox" id="deactivecheck<%=i%>" name="deactivecheck<%=i%>" class="" name="check" id="chek"  /></td>
                        </tr>
                        <% i++;%>                        
                    </c:forEach> 
                </tbody>
            </table>
        </div>   
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<input type="hidden"  id="subComCheck" name="subComCheck" value="">
<script>

    $("#sPattern").chosen();
    $("#subjecttype").chosen();


    function enableSubjectComponent() {
        if ($("#subjecttype").val() != '') {
            var save = false;
            for (var i = 1; i < 4; i++) {
                if ($("#chk" + i + "").is(":checked")) {
                    save = true;
                }
            }
            if (save) {
                $("#subComCheck").val('');
            } else {
                $("#subComCheck").val("0");
            }
        } else {
            $("#subComCheck").val('');
        }
        if ($("#subjecttype").val() == 'A') {
            document.getElementById("credits").readOnly = false;
        } else {
            document.getElementById("credits").readOnly = true;
        }
    }

    function totalpassing() {

        if (parseFloat($("#pMarks").val()) > parseFloat($("#totalmarks").val()))
        {
            BootstrapDialog.alert("Passing Marks can't be more than Total Marks");
            document.getElementById("pMarks").value = '';
        }
        if (parseFloat($("#pMarks").val()) == parseFloat($("#totalmarks").val()))
        {
            BootstrapDialog.alert("Passing Marks can't be equal to Total Marks");
            document.getElementById("pMarks").value = '';
        }
    }


    function getChekedValue(index) {
        if ($("#chk" + index).prop('checked') == true)
        {
            enableSubjectComponent();
            $('#deactivecheck' + index).prop('checked', true)
            document.getElementById("totalccpmarks" + index).disabled = false;
            $("#totalccpmarks" + index).attr({'required': "true", 'data-validation': "number"});
            document.getElementById("ltppassingmarks" + index).disabled = false;
            $("#ltppassingmarks" + index).attr({'required': "true", 'data-validation': "number"});
            document.getElementById("noofhours" + index).disabled = false;
            document.getElementById("noofclassinaweek" + index).disabled = false;
            document.getElementById("totalmarks" + index).disabled = false;
            document.getElementById("passingmarks" + index).disabled = false;
            document.getElementById("totalclass" + index).disabled = false;
        } else {
            enableSubjectComponent();
            $('#deactivecheck' + index).prop('checked', false)
            document.getElementById("totalccpmarks" + index).disabled = true;
            $("#totalccpmarks" + index).val('');
            document.getElementById("ltppassingmarks" + index).disabled = true;
            $("#ltppassingmarks" + index).val('');
            document.getElementById("noofhours" + index).disabled = true;
            $("#noofhours" + index).val('');
            document.getElementById("noofclassinaweek" + index).disabled = true;
            $("#noofclassinaweek" + index).val('');
            document.getElementById("totalmarks" + index).disabled = true;
            $("#totalmarks" + index).val('');
            document.getElementById("passingmarks" + index).disabled = true;
            $("#passingmarks" + index).val('');
            document.getElementById("totalclass" + index).disabled = true;
            $("#totalclass" + index).val('');
        }

    }

    function myReset() {
        var index = 1;
        for (index = 1; index <= 3; index++) {
            document.getElementById("totalccpmarks" + index).disabled = true;
            document.getElementById("ltppassingmarks" + index).disabled = true;
            document.getElementById("noofhours" + index).disabled = true;
            document.getElementById("noofclassinaweek" + index).disabled = true;
            document.getElementById("totalmarks" + index).disabled = true;
            document.getElementById("passingmarks" + index).disabled = true;
            document.getElementById("totalclass" + index).disabled = true;
        }
        $("#subComCheck").val('');
        $("#sPattern").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
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
                                    url: "subjectMaster/save",
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
                                            toastr.success('Record Successfully Saved', "Success!!");
                                            loadForm("subjectMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 3000);
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
    function totalMarks() {
        var tm = $("#totalmarks").val();
        if (tm > 9999) {
            BootstrapDialog.alert("Total Marks should not be grater then 9999 ")
        }
    }
    function enableDisable(id) {
        $("#totalccp").prop('readonly', false);
    }
    function saveData() {
        var i = 1;
        var flag = 0;
        var tccp = 0;
        var tcredits = parseFloat($("#credits").val());
        var checkboxcheck = $("#subComCheck").val();
        for (i = 1; i < 4; i++) {
            var totalccp = parseFloat($("#totalccpmarks" + i).val());
            if ($("#totalccpmarks" + i).val() != '') {
                if (i == 3) {
                    tccp = tccp + (totalccp / 2);
                } else {
                    tccp = tccp + totalccp;
                }
            }
            var passingltp = parseFloat($("#ltppassingmarks" + i).val());
            var total = parseFloat($("#totalmarks" + i).val());
            var passing = parseFloat($("#passingmarks" + i).val());
            var totalclass = parseFloat($("#totalclass" + i).val());
            var noofclassinaweek = parseFloat($("#noofclassinaweek" + i).val());
            if (passingltp > totalccp) {
                flag = 1;
            }
            if (passing > total) {
                flag = 3;
            } else if (passing == total) {
                flag = 4;
            }
            if (noofclassinaweek > totalclass) {
                flag = 5;
            }
        }
        var credit = tccp;
        if ($("#subjecttype").val() != 'A') {
            $("#credits").val(credit);
        } else {
            var crd = $("#credits").val();
            if (crd == '') {
                $("#credits").val('0');
            }
        }
        if (flag != 0) {
            if (flag == 1) {
                BootstrapDialog.alert("LTP Passing Marks should not be greater than Total CCP Marks in Grid.");
            } else if (flag == 3) {
                BootstrapDialog.alert("Passing Marks should not be greater than Total Marks in Grid.");
            } else if (flag == 4) {
                BootstrapDialog.alert("Passing Marks should not be equal to Total Marks in Grid.");
            } else if (flag == 5) {
                BootstrapDialog.alert("No. of class(Week) should not be greater than Total Class in Grid.");
            }
        } else {
            if (checkboxcheck == '') {
                $("#ajaxform").submit();
            } else {
                BootstrapDialog.alert("Please select atleast one Subject component checkbox.");
            }
        }
    }

    function setCredit() {
        debugger;
        var tccp = 0;
        for (var i = 1; i < 4; i++) {
            var totalccp = parseFloat($("#totalccpmarks" + i).val());
            if ($("#totalccpmarks" + i).val() != '') {
                if (i == 3) {
                    tccp = tccp + (totalccp / 2);
                } else {
                    tccp = tccp + totalccp;
                }
            }
        }
        document.getElementById("credits").value = tccp;
    }
</script>
