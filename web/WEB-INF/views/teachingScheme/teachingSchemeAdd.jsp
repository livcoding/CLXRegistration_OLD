<%-- 
    Document   : teachingSchemeAdd
    Created on : Feb 19, 2019, 11:51:14 AM
    Author     : mohit1.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<style>
    .form-error{
        color:red;
    }
</style>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">      
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Subject Type:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="subType" name="subType" data-validation="required" onchange="getBasket();" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${subType}" var="subType">
                            <option value="${subType[0]}~@~${subType[1]}"><c:out value="${subType[1]}"/> / <c:out value="${subType[2]}"/> </option>
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
                    <select class="form-control" id="subCode" name="subCode" data-validation="required" onchange="getDepartment();getMarks();setCredit();" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${subCode}" var="subCode">
                            <option value="${subCode.id.subjectid}~@~${subCode.subjectflag}">${subCode.subjectcode} / <c:out value="${subCode.subjectdesc}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Department Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6" style="padding-top:9px;">
                    <select class="form-control" id="depCode" name="depCode" data-validation="required" onchange="enableSubjectComponent();" data-live-search="true">               
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
                    <input type="text" data-validation-help="Max length is 2"  id="credit" maxlength="2" readonly="true" placeholder="Enter Credit " name="credit" class="form-control has-help-txt"/>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Reg. Fee Amount:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span> </label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <input type="text" data-validation-help="Max length is 8"  data-validation="number" data-validation-optional="true" id="regFeeAmt" maxlength="8" placeholder="Enter Registration Fee Amount " name="regFeeAmt" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group"  style="display: none" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Total Marks:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <input type="text" data-validation-help="Max length is 4" onblur="totalpassing();"  id="total" maxlength="4" placeholder="Enter Total marks " name="total" class="form-control has-help-txt"/>
                </div>
            </div> 
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font color="black">Passing Marks:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span>  </label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6" style="padding-top:4px;">
                    <input type="text" data-validation-help="Max length is 4"  id="passMarks" onblur="totalpassing();" maxlength="4" placeholder="Enter Passing Marks" name="passMarks" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                    <span style="color: green"> Active: </span></label>
                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>           
                </div>
            </div> 
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                    <span> Audit Subject:</span></label>
                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox"  id="auditsubject" name="auditsubject" value="Y"/>           
                </div>
            </div> 
        </div> 

        <table id="datatable" class="box table table-hover table-condensed table-bordered"  cellspacing="0">
            <thead id="header" style="background-color:teal;color:white">
                <tr>
                    <th width="5px;"><input type="checkbox" id="chkAll" onclick="checkAll();"></th>
                    <th width="45px;" >Subject Component Code</th>
                    <th width="10px;" >*Total CCP Marks</th>
                    <th width="10px;" >No. of Hours</th>
                    <th width="10px;" >No. of Class(Week)</th>
                    <th width="10px;" >Total Class</th>
                    <th width="10px;" >Active</th>
                </tr>
            </thead>  
            <tbody> 
                <c:set var="i" value="0"></c:set>
                <c:forEach var="subComp" items="${subComp}">
                    <tr>
                        <td><input type="checkbox" id="chk${i}" name="chk${i}" value="c" onclick="show(${i});"></td>
                        <td><input type="hidden" value="${subComp.id.subjectcomponentid}" id="subCompCode${i}" name="subCompCode${i}">${subComp.subjectcomponentcode}</td>
                        <td><input type="text" disabled="true" autocomplete="off"  maxlength="4"  id="ccp${i}" name="ccp${i}" onkeyup="setCredit();" readonly="true"></td>
                        <td><input type="text" disabled="true" autocomplete="off"   data-validation="number" maxlength="4" data-validation-optional="true" id="noHrs${i}" name="noHrs${i}" ></td>
                        <td><input type="text" disabled="true" autocomplete="off"   data-validation="number" maxlength="4" data-validation-optional="true" onblur="totalperweek();" id="noClass${i}" name="noClass${i}" ></td>
                        <td><input type="text" disabled="true" autocomplete="off"   data-validation="number" maxlength="4" data-validation-optional="true" onblur="totalperweek();" id="totalClass${i}" name="totalClass${i}" ></td>
                        <td><input type="checkbox" id="active${i}" name="active${i}" value="N" ><input type="hidden" id="subComFlag${i}" name="subComFlag${i}" ></td>
                    </tr>
                    <c:set var="i" value="${(i+1)}"></c:set>
                </c:forEach>
            </tbody>
        </table>

        <input type="hidden"  id="count" name="count"  value="${i}"/>  
        <input type="hidden"  id="acYear" name="acYear"  value="${acadYear}"/>  
        <input type="hidden"  id="prg" name="prg"  value="${program}"/>  
        <input type="hidden"  id="sect" name="sect"  value="${section}"/>  
        <input type="hidden"  id="sty" name="sty"  value="${semester}"/>  
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form>
<input type="hidden"  id="subComCheck" name="subComCheck" value="">

<script>
    $("#subType").chosen();
    $("#subCode").chosen();
    $("#basketCode").chosen();
    $("#depCode").chosen();
    $("#elecCode").chosen();

    function setCredit() {
        var subjecttype = $("#subCode").val().split('~@~')[1];
        if (subjecttype == 'A') {
            document.getElementById("credit").readOnly = false;
        } else {
            document.getElementById("credit").value = "0";
            document.getElementById("credit").readOnly = true;
        }
    }
    function enableSubjectComponent() {
        if ($("#depCode").val() != '') {
            var save = false;
            for (var i = 0; i < 3; i++) {
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
    }
    function myOwnReset() {
        var i = 0;
        for (i = 0; i < 3; i++) {
            document.getElementById("subCompCode" + i).disabled = true;
            document.getElementById("ccp" + i).disabled = true;
            $("#ccp" + i).val('');
            $("#ccp" + i).removeAttr('number data-validation');
            document.getElementById("ccp" + i).value = "";
            document.getElementById("ccp" + i).disabled = true;
            document.getElementById("noHrs" + i).value = "";
            document.getElementById("noHrs" + i).disabled = true;
            document.getElementById("noClass" + i).value = "";
            document.getElementById("noClass" + i).disabled = true;
            document.getElementById("totalClass" + i).value = "";
            document.getElementById("totalClass" + i).disabled = true;
            document.getElementById("chk" + i).checked = false;
            document.getElementById("active" + i).checked = false;
        }
        $("#subCode").val('').trigger("chosen:updated");
        $("#depCode").val('').empty();
        $("#depCode").val('').trigger("chosen:updated");
        $("#elecCode").val('').trigger("chosen:updated");
        $("#subComCheck").val('');
        $("#regFeeAmt").val('');
        $("#credit").val('');
        $("#total").val('');
        $("#passMarks").val('');
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }
    function myReset() {
        var i = 0;
        for (i = 0; i < 3; i++) {
            document.getElementById("subCompCode" + i).disabled = true;
            document.getElementById("ccp" + i).disabled = true;
            $("#ccp" + i).val('');
            $("#ccp" + i).removeAttr('number data-validation');
            document.getElementById("ccp" + i).disabled = true;
            document.getElementById("noHrs" + i).disabled = true;
            document.getElementById("noClass" + i).disabled = true;
            document.getElementById("totalClass" + i).disabled = true;
        }
        $("#subType").val('').trigger("chosen:updated");
        $("#subCode").val('').trigger("chosen:updated");
        $("#basketCode").empty();
        $("#basketCode").val('').trigger("chosen:updated");
        $("#depCode").val('').empty();
        $("#depCode").val('').trigger("chosen:updated");
        $("#elecCode").val('').trigger("chosen:updated");
        $("#subComCheck").val('');
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }
    function totalpassing() {

        if (parseFloat($("#passMarks").val()) > parseFloat($("#total").val()))
        {
            BootstrapDialog.alert("Passing Marks can't be more than Total Marks");
            document.getElementById("passMarks").value = '';
        }
        if (parseFloat($("#passMarks").val()) == parseFloat($("#total").val()))
        {
            BootstrapDialog.alert("Passing Marks can't be equal to Total Marks");
            document.getElementById("passMarks").value = '';
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
    function getMarks() {
        var count = $("#count").val();
        for (var i = 0; i < count; i++) {
            $("#chk" + i).prop("checked", false);
            show(i);
        }
        if ($("#subCode").val() != '') {
            $.ajax({
                url: "teachingSch/getMarks",
                data: 'subjectid=' + $("#subCode").val().split('~@~')[0],
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#passMarks").empty();
                    if (data.marks != null || data.marks != '') {
                        $.each(data.marks[0], function (i, item)
                        {
                            $("#credit").val(item[0]);
                            $("#total").val(item[1]);
                            $("#passMarks").val(item[2]);
                            for (var j = 0; j < 3; j++) {
                                var subcomid = $("#subCompCode" + j).val();
                                if (item[3] == subcomid) {
                                    $('#chk' + j).prop('checked', true);
                                    show(j);
                                    $("#ccp" + j).val(item[5]);
                                    $("#noHrs" + j).val(item[6]);
                                    $("#noClass" + j).val(item[7]);
                                    $("#totalClass" + j).val(item[8]);
                                    if (item[9] == 'N') {
                                        $('#active' + j).prop('checked', true);
                                    }
                                    $("#subComFlag" + j).val("Y");
                                }
                            }
                        });
                    } else {
                        BootstrapDialog.alert("Total or Passing marks not found for this Subject");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Subject Code first!");
        }
    }
    function getBasket() {
        if ($("#subType").val() != '') {
            $.ajax({
                url: "teachingSch/getBasketData",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: 'program=' + $("#prg").val() + '&semester=' + $("#sty").val() + '&section=' + $("#sect").val() + '&subType=' + $("#subType").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#basketCode").empty();
                    if (data.basket != null || data.basket != '') {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.basket, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
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
        if ($("#subCode").val().split('~@~')[1] == 'A') {
            $("#auditsubject").prop("checked", true);
        } else {
            $("#auditsubject").prop("checked", false);
        }
        if ($("#subCode").val() != '') {
            $.ajax({
                url: "teachingSch/getDepartment",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "subCode=" + $("#subCode").val().split('~@~')[0],
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
                            str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
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
                                    url: "teachingSch/save",
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
                                            toastr.success(Success['save_success'], "Success!!");
                                            myOwnReset();
//                                            loadForm("teachingSch/list");
//                                            closePage();
//                                            setLoadData();
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

//    function saveData() {
//        var checked = 0;
//        var checkboxcheck = $("#subComCheck").val();
//        if (checkboxcheck == '') {
//            $("#ajaxform").submit();
//        } else {
//            BootstrapDialog.alert("Please select atleast one Subject component checkbox.");
//        }
//    }
    function saveData() {
        var i = 1;
        var tccp = 0;
        var checkboxcheck = $("#subComCheck").val();
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
        var subjecttype = $("#subCode").val().split('~@~')[1];
        if (subjecttype != 'A') {
            $("#credit").val(credit);
        } else {
            var cp = $("#credit").val();
            if (cp == '') {
                $("#credit").val('0');
            }
        }
        if (checkboxcheck == '') {
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
            enableSubjectComponent();
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
            $('#active' + i).prop('checked', true);
        } else {
            enableSubjectComponent();
            document.getElementById("subCompCode" + i).disabled = true;
            document.getElementById("ccp" + i).disabled = true;
            $("#ccp" + i).removeAttr('number data-validation');
            document.getElementById("ccp" + i).disabled = true;
            $("#ccp" + i).val('');
            document.getElementById("noHrs" + i).disabled = true;
            $("#noHrs" + i).val('');
            document.getElementById("noClass" + i).disabled = true;
            $("#noClass" + i).val('');
            document.getElementById("totalClass" + i).disabled = true;
            $("#totalClass" + i).val('');
            $('#active' + i).prop('checked', false);
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

