<%-- 
    Document   : SummerRegistrationSetupEdit
    Created on : 1 AUG : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">                 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="hidden" name="programId" id="programId"  value="${srsData[0][1]}" >
                    <input type="text" value="${srsData[0][2]} / ${srsData[0][3]}" class="form-control" readonly="true" data-validation-error-msg="Please select program code!">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">STY Number:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="stynumber" id="stynumber" value="${srsData[0][4]}" class="form-control" readonly="true" data-validation-error-msg="Please select sty number!">  
                </div>
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Credit:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxCredit" id="maxCredit" maxlength="4" value="${srsData[0][5]}" data-validation-help="Max Length is 4" data-validation="number" data-validation-allowing="float"  placeholder="Enter Max Credit" onkeypress="return isNumberKey(event)" class="form-control" data-validation-error-msg="Please enter max credit!">
                </div>
            </div>
        </div>             
        <div class="row col-lg-12 form-group" >              
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Subject:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxSubject" id="maxSubject" maxlength="4" value="${srsData[0][6]}" data-validation-help="Max Length is 4" data-validation="number" placeholder="Enter Max Subject" onkeypress="return isNumberKey(event)" class="form-control" data-validation-error-msg="Please enter max subject!">
                </div>
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >              
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Subject(Project):<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxSubjectProject" id="maxSubjectProject" maxlength="4" value="${srsData[0][7]}" data-validation-help="Max Length is 4" data-validation="number" placeholder="Enter Max Subject(Project)" onkeypress="return isNumberKey(event)" class="form-control" data-validation-error-msg="Please enter max subject(project)!">
                </div>
            </div>
        </div>              
        <div class="row col-lg-12 form-group" >              
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Subject(Industrial Case):<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxSubjectIndusCase" id="maxSubjectIndusCase" maxlength="4" value="${srsData[0][8]}"  data-validation-help="Max Length is 4" data-validation="number" placeholder="Enter Max Subject(Industrial Case)" onkeypress="return isNumberKey(event)" class="form-control" data-validation-error-msg="Please enter max subject(industrial case)!">
                </div>
            </div>
        </div>  
        <p style="color:red" id="hint"><b><u>Hint:</u></b> &nbsp; <br><span style="color:green;font-size:16px">L = Lecture <br> P = Practical In Table </span></p>
        <div class="col-lg-12" style="margin-top:10px;overflow-x:auto;">
            <table id="datatable1" class="box table table-hover table-condensed table-bordered"  cellspacing="0">
                <thead id="header" >
                    <tr class="" style="background-color:#008080;color:white" >
                        <th>Check</th>
                        <th>Project</th>
                        <th><span style="font-size:16px">LP</span> Criteria Type</th>
                        <th>Max Credit</th>
                        <th>Max <span style="font-size:16px">L</span> Subject Credit</th>
                        <th>Max <span style="font-size:16px">P</span> Subject Credit</th>
                        <th>Max <span style="font-size:16px">L</span> Theory Subject</th>
                        <th>Max <span style="font-size:16px">P</span> Lab Subject</th>
                    </tr>
                </thead>  
                <tbody> 
                    <c:set var="i" value="1"></c:set>
                    <c:set var="mm" value="null"></c:set>
                    <c:forEach var="srsd" items="${srsdData}">
                        <tr>
                            <td><input type="checkbox" name="chk${i}" id="chk${i}"  checked="true" onclick="show(${i});"/></td>
                                <c:if test="${srsd[5]=='M'}">
                                    <c:set var="mm" value="M"></c:set>
                                <td><input type="hidden" name="majorOrMinor${i}" id="majorOrMinor${i}" value="M"/>MAJOR</td>
                                </c:if>
                                <c:if test="${srsd[5]=='N'}">
                                    <c:set var="mm" value="N"></c:set>
                                <td><input type="hidden" name="majorOrMinor${i}" id="majorOrMinor${i}" value="N"/>MINOR</td>
                                </c:if>
                            <td>
                                <select class="form-control" name="lpCriteriaType${i}" id="lpCriteriaType${i}" >
                                    <c:if test="${srsd[6]=='AND'}">
                                        <option value="AND" selected="true">AND</option>
                                        <option value="OR">OR</option>
                                        <option value="EQL">EQUAL</option>
                                    </c:if>
                                    <c:if test="${srsd[6]=='OR'}">
                                        <option value="AND" >AND</option>
                                        <option value="OR" selected="true">OR</option>
                                        <option value="EQL">EQUAL</option>
                                    </c:if>
                                    <c:if test="${srsd[6]=='EQL'}">
                                        <option value="AND" >AND</option>
                                        <option value="OR">OR</option>
                                        <option value="EQL" selected="true">EQUAL</option>
                                    </c:if>
                                </select>
                            </td>
                            <td><input type="text" name="maxCredit${i}" id="maxCredit${i}" value="${srsd[7]}" maxlength="4" onkeypress="return isNumberKey(event)"/></td>
                            <td><input type="text" name="maxLsubjectCredit${i}" id="maxLsubjectCredit${i}" value="${srsd[8]}"  onkeypress="return isNumberKey(event)"maxlength="4"/></td>
                            <td><input type="text" name="maxPsubjectCredit${i}" id="maxPsubjectCredit${i}" value="${srsd[9]}"  onkeypress="return isNumberKey(event)"maxlength="4"/></td>
                            <td><input type="text" name="maxLtheorySubject${i}" id="maxLtheorySubject${i}" value="${srsd[10]}" onkeypress="return isNumberKey(event)" maxlength="4"/></td>
                            <td><input type="text" name="maxPlabSubject${i}" id="maxPlabSubject${i}" value="${srsd[11]}" onkeypress="return isNumberKey(event)" maxlength="4"/></td>
                        </tr>
                        <c:set var="i" value="${(i+1)}"></c:set>
                    </c:forEach>
                    <c:if test="${i<=2}">
                        <c:if test="${mm=='M'}">
                            <tr>
                                <td><input type="checkbox" name="chk${i}" id="chk${i}" onclick="show(${i});"/></td>
                                <td><input type="hidden" name="majorOrMinor${i}" id="majorOrMinor${i}" value="N"/>MINOR</td>
                                <td>
                                    <select class="form-control" name="lpCriteriaType${i}" id="lpCriteriaType${i}" >
                                        <option value="AND">AND</option>
                                        <option value="OR">OR</option>
                                        <option value="EQL">EQUAL</option>
                                    </select>
                                </td>
                                <td><input type="text" name="maxCredit${i}" id="maxCredit${i}"  maxlength="4" disabled="true" onkeypress="return isNumberKey(event)" /></td>
                                <td><input type="text" name="maxLsubjectCredit${i}" id="maxLsubjectCredit${i}" maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                                <td><input type="text" name="maxPsubjectCredit${i}" id="maxPsubjectCredit${i}" maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                                <td><input type="text" name="maxLtheorySubject${i}" id="maxLtheorySubject${i}" maxlength="4" onkeypress="return isNumberKey(event)" disabled="true" /></td>
                                <td><input type="text" name="maxPlabSubject${i}" id="maxPlabSubject${i}" maxlength="4" onkeypress="return isNumberKey(event)" disabled="true" /></td>
                            </tr>
                        </c:if>
                        <c:if test="${mm=='N'}">
                            <tr>
                                <td><input type="checkbox" name="chk${i}" id="chk${i}" onclick="show(${i});"/></td>
                                <td><input type="hidden" name="majorOrMinor${i}" id="majorOrMinor${i}" value="M"/>MAJOR</td>
                                <td>
                                    <select class="form-control" name="lpCriteriaType${i}" id="lpCriteriaType${i}" >
                                        <option value="AND">AND</option>
                                        <option value="OR">OR</option>
                                        <option value="EQL">EQUAL</option>
                                    </select>
                                </td>
                                <td><input type="text" name="maxCredit${i}" id="maxCredit${i}"  maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                                <td><input type="text" name="maxLsubjectCredit${i}" id="maxLsubjectCredit${i}" maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                                <td><input type="text" name="maxPsubjectCredit${i}" id="maxPsubjectCredit${i}" maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                                <td><input type="text" name="maxLtheorySubject${i}" id="maxLtheorySubject${i}" maxlength="4" onkeypress="return isNumberKey(event)" disabled="true" /></td>
                                <td><input type="text" name="maxPlabSubject${i}" id="maxPlabSubject${i}" maxlength="4" onkeypress="return isNumberKey(event)" disabled="true" /></td>
                            </tr>
                        </c:if>
                    </c:if>
                </tbody>
            </table>
        </div>   
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:updateData();" class="btn btn-success btn-sm btn-flat">Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>
    $("#lpCriteriaType1").chosen({width: "100%"});
    $("#lpCriteriaType2").chosen({width: "100%"});
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
                                    url: "summerRegistrationSetup/update",
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
                                            loadForm("summerRegistrationSetup/list");
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
        var save = false;
        var flag = true;
        for (var i = 1; i < 3; i++) {
            if ($("#chk" + i + "").is(":checked")) {
                save = true;
                var maxcredit = parseFloat($("#maxCredit" + i + "").val());
                var lcredit = parseFloat($("#maxLsubjectCredit" + i + "").val());
                var pcredit = parseFloat($("#maxPsubjectCredit" + i + "").val());
                if (maxcredit < lcredit + pcredit) {
                    flag = false;
                }
            }
        }
        if (save) {
            if (flag) {
                $("#ajaxform").submit();
            } else {
                BootstrapDialog.alert("Sum of L or P Subject Credit should not be greater than Max Credit");
            }
        } else {
            BootstrapDialog.alert("Passing Select atleast one checkbox.");
        }
    }

    function show(i) {
        if ($("#chk" + i).is(":checked")) {
            $("#lpCriteriaType" + i).val('AND').trigger("chosen:updated");
            document.getElementById("maxCredit" + i).disabled = false;
            document.getElementById("maxLsubjectCredit" + i).disabled = false;
            document.getElementById("maxPsubjectCredit" + i).disabled = false;
            document.getElementById("maxLtheorySubject" + i).disabled = false;
            document.getElementById("maxPlabSubject" + i).disabled = false;
        } else {
            $("#lpCriteriaType" + i).val('AND').trigger("chosen:updated");
            document.getElementById("maxCredit" + i).disabled = true;
            document.getElementById("maxCredit" + i).value = "";
            document.getElementById("maxLsubjectCredit" + i).disabled = true;
            document.getElementById("maxLsubjectCredit" + i).value = "";
            document.getElementById("maxPsubjectCredit" + i).disabled = true;
            document.getElementById("maxPsubjectCredit" + i).value = "";
            document.getElementById("maxLtheorySubject" + i).disabled = true;
            document.getElementById("maxLtheorySubject" + i).value = "";
            document.getElementById("maxPlabSubject" + i).disabled = true;
            document.getElementById("maxPlabSubject" + i).value = "";
        }
    }
</script>