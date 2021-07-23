<%-- 
    Document   : teachingSchemeEdit
    Created on : Feb 21, 2019, 11:51:18 AM
    Author     : mohit1.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">      
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Subject Type:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="subType" name="subType" data-validation="required" disabled="true" onchange="getBasket();" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${subType}" var="subType">
                            <c:if test="${subTypeId == subType[0]}">
                                <option value="${subType[0]}~@~${subType[1]}" selected="true"><c:out value="${subType[1]}"/> / <c:out value="${subType[2]}"/> </option>
                            </c:if>
                            <c:if test="${subTypeId != subType[0]}">
                                <option value="${subType[0]}~@~${subType[1]}"><c:out value="${subType[1]}"/> / <c:out value="${subType[2]}"/> </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Basket Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="basketCode" name="basketCode" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Subject Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="subCode" name="subCode" data-validation="required" disabled="true" onchange="getDepartment();" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${subCode}" var="subCode">
                            <c:if test="${subCode.id.subjectid == teachSchmData[0][5]}">
                                <option value="${subCode.id.subjectid}" selected="true">${subCode.subjectcode} / <c:out value="${subCode.subjectdesc}"/></option>
                            </c:if>
                            <c:if test="${subCode.id.subjectid != teachSchmData[0][5]}">
                                <option value="${subCode.id.subjectid}">${subCode.subjectcode} / <c:out value="${subCode.subjectdesc}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <input type="hidden" id="subId" name="subId" value="${teachSchmData[0][5]}">
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Department Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="depCode" name="depCode" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 

        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Credit:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <c:if test="${teachSchmData[0][13]=='A'}">
                        <input type="text" data-validation-help="Max length is 2" value="${teachSchmData[0][8]}" id="credit" maxlength="2" placeholder="Enter Credit " name="credit"  onkeypress="return isNumberKey(event)"  class="form-control has-help-txt"/>
                    </c:if>
                    <c:if test="${teachSchmData[0][13]!='A'}">
                        <input type="text" data-validation-help="Max length is 2" readonly="true" value="${teachSchmData[0][8]}" id="credit" maxlength="2" placeholder="Enter Credit " name="credit"  onkeypress="return isNumberKey(event)"  class="form-control has-help-txt"/>
                    </c:if>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Reg. Fee Amount:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span> </label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <input type="text" data-validation-help="Max length is 8"  data-validation="number" data-validation-optional="true" id="regFeeAmt" maxlength="8" placeholder="Enter Registration Fee Amount " name="regFeeAmt" value="${teachSchmData[0][12]}" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" style="display: none" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Total Marks:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <input type="text" data-validation-help="Max length is 4" value="${teachSchmData[0][9]}"  onblur="totalpassing();"  data-validation-allowing="float"  id="total" maxlength="4" placeholder="Enter Total marks " name="total" class="form-control has-help-txt"/>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Passing Marks:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span>  </label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <input type="text" data-validation-help="Max length is 4" value="${teachSchmData[0][10]}" onblur="totalpassing();" id="passMarks" data-validation-allowing="float"  maxlength="4" placeholder="Enter Passing Marks" name="passMarks" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${teachSchmData[0][7]=='N'}">
                        <span style="color: green"> Active:</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${teachSchmData[0][7]=='Y'}">
                    <span style="color: red"> Deactive:</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div> 
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                    <span> Audit Subject:</span></label>
                    <c:if test="${teachSchmData[0][14]=='Y'}">
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox"  id="auditsubject" name="auditsubject" checked="true" value="Y"/>   
                    </div>
                </c:if>
                <c:if test="${teachSchmData[0][14]!='Y'}">
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox"  id="auditsubject" name="auditsubject"  value="Y"/>   
                    </div>
                </c:if>
            </div> 
        </div> 

        <table id="datatable" class="box table table-hover table-condensed table-bordered"  cellspacing="0">
            <thead id="header" style="background-color:teal;color:white">
                <tr>
                    <th width="5px;"><input type="checkbox" id="chkAll" onclick="checkAll();"></th>
                    <th width="45px;" >Subject Component Code</th>
                    <th width="10px;" >*Total CCP Marks</th>
                    <th width="10px;" >No of Hours</th>
                    <th width="10px;" >No of Class(Week)</th>
                    <th width="10px;" >Total Class</th>
                    <th width="10px;" >Active</th>
                </tr>
            </thead>  
            <tbody>
                <c:set var="i" value="0"></c:set>
                <c:forEach var="subComp" items="${subComp}">
                    <tr>
                        <c:set var="flag" value="0"></c:set>  
                        <c:forEach var="teachSchmCompoData" items="${teachSchmCompoData}">
                            <c:if test="${subComp.id.subjectcomponentid==teachSchmCompoData[0]}">
                                <td><input type="checkbox" id="chk${i}" checked="true" name="chk${i}" value="c" onclick="show(${i});"></td>    
                                    <c:set var="flag" value="${(flag+1)}"></c:set>
                                <td><input type="hidden" value="${subComp.id.subjectcomponentid}" id="subCompCode${i}" name="subCompCode${i}">${subComp.subjectcomponentcode}</td>
                                <td><input type="text"  maxlength="4"  autocomplete="off"  id="ccp${i}" name="ccp${i}" onkeyup="setCredit();" value="${teachSchmCompoData[1]}" ></td>
                                <td><input type="text" data-validation="number" autocomplete="off"  maxlength="4" value="${teachSchmCompoData[2]}" data-validation-optional="true" id="noHrs${i}" name="noHrs${i}" ></td>
                                <td><input type="text" data-validation="number"  autocomplete="off" maxlength="4" value="${teachSchmCompoData[3]}" data-validation-optional="true" onblur="totalperweek();" id="noClass${i}" name="noClass${i}"></td>
                                <td><input type="text" data-validation="number"  autocomplete="off" maxlength="4" value="${teachSchmCompoData[4]}" data-validation-optional="true" onblur="totalperweek();" id="totalClass${i}" name="totalClass${i}" ></td>
                                    <c:choose>    
                                        <c:when test="${teachSchmCompoData[5]=='N'}">
                                        <td><input type="checkbox" id="active${i}" name="active${i}" value="N" checked="true" ></td>
                                        </c:when>
                                        <c:when test="${teachSchmCompoData[5]=='Y'}">
                                        <td><input type="checkbox" id="active${i}" name="active${i}" value="N"></td>
                                        </c:when>
                                    </c:choose>    
                                </c:if>
                            </c:forEach>
                            <c:if test="${flag==0}">
                            <td><input type="checkbox" id="chk${i}"  name="chk${i}" value="c" onclick="show(${i});"></td>
                            <td><input type="hidden" value="${subComp.id.subjectcomponentid}" id="subCompCode${i}" name="subCompCode${i}">${subComp.subjectcomponentcode}</td>
                            <td><input type="text" disabled="true" autocomplete="off"  maxlength="4"  id="ccp${i}" name="ccp${i}" onkeyup="setCredit();" readonly="true"></td>
                            <td><input type="text" disabled="true"  autocomplete="off" data-validation="number" maxlength="4" data-validation-optional="true" id="noHrs${i}" name="noHrs${i}" ></td>
                            <td><input type="text" disabled="true" autocomplete="off"  data-validation="number" maxlength="4" data-validation-optional="true" onblur="totalperweek();" id="noClass${i}" name="noClass${i}" ></td>
                            <td><input type="text" disabled="true" autocomplete="off"  data-validation="number" maxlength="4" data-validation-optional="true" onblur="totalperweek();" id="totalClass${i}" name="totalClass${i}" ></td>
                            <td><input type="checkbox" id="active${i}" name="active${i}" value="N" ></td>
                            </c:if>

                    </tr>
                    <c:set var="i" value="${(i+1)}"></c:set>
                </c:forEach>
            </tbody>
        </table>

        <input type="hidden"  id="count" name="count"  value="${i}"/>  
        <input type="hidden"  id="programSchemeAcadWiseId" name="programSchemeAcadWiseId"  value="${programSchemeAcadWiseId}"/>  
        <input type="hidden"  id="subjecttype" name="subjecttype"  value="${teachSchmData[0][13]}"/>  

        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form>

<script>
    $("#subType").chosen();
    $("#subCode").chosen();
    $("#basketCode").chosen();
    $("#depCode").chosen();
    $("#elecCode").chosen();
    $(document).ready(function () {
        getBasket();
        getDepartment();
        getBasket();
    });
    $(document).ready(function () {
        var index = 0;
        for (index = 0; index < 3; index++) {
            if ($("#chk" + index).prop('checked') == true)
            {
                $("#ccp" + index).attr({
                    'required': "true",
                    'data-validation': "number"
                });
            }
        }
    });
    function totalpassing() {

        if (parseFloat($("#passMarks").val()) > parseFloat($("#total").val()))
        {
            BootstrapDialog.alert("Passing Marks can't be more than Total Marks");
            document.getElementById("pMarks").value = '';
        }
        if (parseFloat($("#passMarks").val()) == parseFloat($("#total").val()))
        {
            BootstrapDialog.alert("Passing Marks can't be equal to Total Marks");
            document.getElementById("pMarks").value = '';
        }
    }
    function totalperweek() {
        for (var i = 0; i < 3; i++) {
            if (parseFloat($("#noClass" + i).val()) > parseFloat($("#totalClass" + i).val()))
            {
                BootstrapDialog.alert("No. Of Class(Week) Should not be greater than Total Class");
                document.getElementById("noClass" + i).value = '';
            }
        }
    }
    function myReset() {
        $("#subType").val('').trigger("chosen:updated");
        $("#subCode").val('').trigger("chosen:updated");
        $("#basketCode").val('').trigger("chosen:updated");
        $("#depCode").val('').trigger("chosen:updated");
        $("#elecCode").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }

    function getBasket() {
        if ($("#subType").val() != '') {
            $.ajax({
                url: "teachingSch/getAllBasket",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "subType=" + $("#subType").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#basketCode").empty();
                    if (data.basket != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.basket, function (i, item)
                        {
                            if (item[0] == '${teachSchmData[0][4]}') {
                                str1 += '<option value="' + item[0] + '" selected="true">' + item[1] + ' / ' + item[2] + '</option>'
                            } else {
                                str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
                            }
                        });
                        $("#basketCode").append(str1);
                        $("#basketCode").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Basket Not Found For This Subject Type,Please Select Another Subject Type...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select subject type first!");
        }
    }
    function getDepartment() {
        if ($("#subCode").val() != '') {
            $.ajax({
                url: "teachingSch/getDepartment",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "subCode=" + $("#subCode").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#depCode").empty();
                    if (data.dep != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.dep, function (i, item)
                        {
                            if (item[0] == '${teachSchmData[0][6]}') {
                                str1 += '<option value="' + item[0] + '" selected="true ">' + item[1] + ' / ' + item[2] + '</option>'
                            } else {
                                str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
                            }
                        });
                        $("#depCode").append(str1);
                        $("#depCode").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Department Not Found For This Subject,Please Select Another Subject...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select subject first!");
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
                                    url: "teachingSch/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success(Success['update_success'], "Success!!");
                                            closePage();
                                            setLoadData();
//                                            $("#semester").val('').trigger("chosen:updated");
//                                            $('#datatable').DataTable().clear().draw();
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
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
        var checked = 0;
        var i = 1;
        var tccp = 0;
        var count = $("#count").val();
        for (var i = 0; i < count; i++) {
            if ($("#chk" + i).is(":checked")) {
                checked++;
            }
        }
        for (i = 0; i < 3; i++) {
            var totalccp = parseFloat($("#ccp" + i).val());
            if ($("#ccp" + i).val() != '') {
                if (i == 2) {
                    tccp = tccp + (totalccp / 2);
                } else {
                    tccp = tccp + totalccp;
                }
            }
        }
        var credit = tccp;
        var subjecttype = $("#subjecttype").val();
        if (subjecttype != 'A') {
            $("#credit").val(credit);
        } else {
            var cp = $("#credit").val();
            if (cp == '') {
                $("#credit").val('0');
            }
        }
        if (checked > 0) {
            $("#ajaxform").submit();
        } else {
            BootstrapDialog.alert("Please select atleast one Subject component checkbox.");
        }
    }

    function checkAll() {
        var count = $("#count").val();
        if ($("#chkAll").is(":checked")) {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", true);
                show(i);
            }
        } else {
            for (var i = 0; i < count; i++) {
                $("#chk" + i).prop("checked", false);
                show(i);
            }
        }
    }
    function show(i) {
        if ($("#chk" + i).is(":checked")) {
            document.getElementById("subCompCode" + i).disabled = false;
            document.getElementById("ccp" + i).disabled = false;
            $("#ccp" + i).attr("readonly", false);
            $("#ccp" + i).attr({
                'required': "true",
                'data-validation': "number"
            });
            document.getElementById("noHrs" + i).disabled = false;
            document.getElementById("noClass" + i).disabled = false;
            document.getElementById("totalClass" + i).disabled = false;
            $('#active' + i).prop('checked', true)
            document.getElementById("active" + i).disabled = false;
        } else {
            document.getElementById("subCompCode" + i).disabled = true;
            document.getElementById("ccp" + i).disabled = true;
            $("#ccp" + i).val('');
            $("#ccp" + i).removeAttr('number data-validation');
            document.getElementById("ccp" + i).disabled = true;
            document.getElementById("noHrs" + i).disabled = true;
            $("#noHrs" + i).val('');
            document.getElementById("noClass" + i).disabled = true;
            $("#noClass" + i).val('');
            document.getElementById("totalClass" + i).disabled = true;
            $("#totalClass" + i).val('');
            $('#active' + i).prop('checked', false)
            document.getElementById("active" + i).disabled = true;
        }
    }
    function setCredit() {
        debugger;
        var tccp = 0;
        for (var i = 0; i < 3; i++) {
            var totalccp = parseFloat($("#ccp" + i).val());
            if ($("#ccp" + i).val() != '') {
                if (i == 2) {
                    tccp = tccp + (totalccp / 2);
                } else {
                    tccp = tccp + totalccp;
                }
            }
        }
        document.getElementById("credit").value = tccp;
    }
</script>

