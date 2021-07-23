<%-- 
    Document   : SummerRegistrationSetupAdd
    Created on : 1 AUG : 2019 
    Author     : Malkeet Singh
--%>


<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">     
        <div class="row col-lg-12 form-group" >
            <p class="warning" style="color:#6d6d00" id="hint"><b> &nbsp; &nbsp;<u> Warning:</u></b> &nbsp; If you select the already saved  STY Number for the corresponding Program Code Then it can't be saved.</p>            
            <br>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select class="form-control" id="programId" name="programId" multiple="true" onchange="getStyNumber();" data-validation="required" data-validation-error-msg="Please select program code!">
                        <c:forEach items="${prog_list}" var="prog_list">
                            <option value="${prog_list[2]}"><c:out value="${prog_list[0]}"/>/<c:out value="${prog_list[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">STY Number:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select   class="form-control" id="stynumber" name="stynumber" multiple="true"  data-validation="required" data-validation-error-msg="Please select sty number!" >
                    </select>
                </div>
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Credit:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxCredit" id="maxCredit" maxlength="4" data-validation-help="Max Length is 4" data-validation="number" data-validation-allowing="float"  placeholder="Enter Max Credit" onkeypress="return isNumberKey(event)" class="form-control"  data-validation-error-msg="Please enter max credit!">
                </div>
            </div>
        </div>             
        <div class="row col-lg-12 form-group" >              
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Subject:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxSubject" id="maxSubject" maxlength="4" data-validation-help="Max Length is 4" data-validation="number" placeholder="Enter Max Subject" onkeypress="return isNumberKey(event)" class="form-control"data-validation-error-msg="Please enter max subject!">
                </div>
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >              
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Subject(Project):<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxSubjectProject" id="maxSubjectProject" maxlength="4" data-validation-help="Max Length is 4" data-validation="number" placeholder="Enter Max Subject(Project)" onkeypress="return isNumberKey(event)" class="form-control"  data-validation-error-msg="Please enter max subject(project)!">
                </div>
            </div>
        </div>              
        <div class="row col-lg-12 form-group" >              
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Max Subject(Industrial Case):<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="maxSubjectIndusCase" id="maxSubjectIndusCase" maxlength="4" onblur="validateCheckBox();" data-validation-help="Max Length is 4" data-validation="number" placeholder="Enter Max Subject(Industrial Case)" onkeypress="return isNumberKey(event)" class="form-control" data-validation-error-msg="Please enter max subject(industrial case)!">
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
                    <c:forEach var = "i" begin = "1" end = "2">
                        <tr>
                            <td><input type="checkbox" name="chk${i}" id="chk${i}" onclick="show(${i});"/></td>
                                <c:if test="${i==1}">
                                <td><input type="hidden" name="majorOrMinor${i}" id="majorOrMinor${i}" value="M" /><b>MAJOR</b></td>
                                </c:if>
                                <c:if test="${i==2}">
                                <td><input type="hidden" name="majorOrMinor${i}" id="majorOrMinor${i}" value="N"/><b>MINOR</b></td>
                                </c:if>
                            <td>
                                <select class="form-control" name="lpCriteriaType${i}" id="lpCriteriaType${i}" >
                                    <option value="AND">AND</option>
                                    <option value="OR">OR</option>
                                    <option value="EQL">EQUAL</option>
                                </select>
                            </td>
                            <td><input type="text" name="maxCredit${i}" id="maxCredit${i}"  maxlength="4" onkeypress="return isNumberKey(event)" disabled="true" /></td>
                            <td><input type="text" name="maxLsubjectCredit${i}" id="maxLsubjectCredit${i}" maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                            <td><input type="text" name="maxPsubjectCredit${i}" id="maxPsubjectCredit${i}" maxlength="4"  onkeypress="return isNumberKey(event)"disabled="true" /></td>
                            <td><input type="text" name="maxLtheorySubject${i}" id="maxLtheorySubject${i}" maxlength="4" onkeypress="return isNumberKey(event)" disabled="true" /></td>
                            <td><input type="text" name="maxPlabSubject${i}" id="maxPlabSubject${i}" maxlength="4"  onkeypress="return isNumberKey(event)" disabled="true" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>   
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<input type="hidden"  id="summerDetail" name="summerDetail" value="">
<script>


    $("#programId").chosen({width: "100%"});
    $("#stynumber").chosen({width: "100%"});
    $("#lpCriteriaType1").chosen({width: "100%"});
    $("#lpCriteriaType2").chosen({width: "100%"});
    function myReset() {
        $("#programId").empty();
        $("#stynumber").empty();
        $("#stynumber").val('').trigger("chosen:updated");
        $("#summerDetail").val('');
        $("#lpCriteriaType1").val('AND').trigger("chosen:updated");
        $("#lpCriteriaType2").val('AND').trigger("chosen:updated");
        for (var i = 1; i < 3; i++) {
            document.getElementById("maxCredit" + i).disabled = true;
            document.getElementById("maxLsubjectCredit" + i).disabled = true;
            document.getElementById("maxPsubjectCredit" + i).disabled = true;
            document.getElementById("maxLtheorySubject" + i).disabled = true;
            document.getElementById("maxPlabSubject" + i).disabled = true;
        }
    }
    function validateCheckBox() {
        debugger;
        if ($("#maxSubjectIndusCase").val() != '') {
            var save = false;
            for (var i = 1; i < 3; i++) {
                if ($("#chk" + i + "").is(":checked")) {
                    save = true;
                }
            }
            if (save) {
                $("#summerDetail").val('');
            } else {
                $("#summerDetail").val("0");
            }
        } else {
            $("#summerDetail").val('');
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

    function getStyNumber() {
        if ($("#program").val() != '') {
            $.ajax({
                url: "summerRegistrationSetup/getStyNumber",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "&programId=" + $("#programId").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#stynumber").empty();
                    if (data.stynumber[0] != null) {
                        var str1 = '';
                        for (var i = parseInt(data.stynumber[0][0]); i <= parseInt(data.stynumber[0][1]); i++)
                        {
                            str1 += '<option value="' + i + '">' + i + '</option>'
                        }
                        $("#stynumber").append(str1);
                        $("#stynumber").trigger("chosen:updated");
                    } else {
                        $("#stynumber").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("STY No.not found for this Program Code");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Program Code first!");
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "summerRegistrationSetup/save",
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
                                            toastr.success(Success['save_success'], "Success!!");
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
    function saveData() {
        var summerDetail = $("#summerDetail").val();
        var flag = true;
        if (summerDetail == '') {
            for (var i = 1; i < 3; i++) {
                var maxcredit = parseFloat($("#maxCredit" + i + "").val());
                var lcredit = parseFloat($("#maxLsubjectCredit" + i + "").val());
                var pcredit = parseFloat($("#maxPsubjectCredit" + i + "").val());
                if (maxcredit < lcredit + pcredit) {
                    flag = false;
                }
            }
            if (flag) {
                $("#ajaxform").submit();
            } else {
                BootstrapDialog.alert("Sum of L or P Subject Credit should not be greater than Max Credit");
            }
        } else {
            BootstrapDialog.alert("Please select atleast one Checkbox.");
        }
    }

    function show(i) {
        if ($("#chk" + i).is(":checked")) {
            validateCheckBox();
            $("#lpCriteriaType" + i).val('AND').trigger("chosen:updated");
            document.getElementById("maxCredit" + i).disabled = false;
            document.getElementById("maxLsubjectCredit" + i).disabled = false;
            document.getElementById("maxPsubjectCredit" + i).disabled = false;
            document.getElementById("maxLtheorySubject" + i).disabled = false;
            document.getElementById("maxPlabSubject" + i).disabled = false;
        } else {
            validateCheckBox();
            $("#lpCriteriaType" + i).val('AND').trigger("chosen:updated");
            document.getElementById("maxCredit" + i).disabled = true;
            document.getElementById("maxLsubjectCredit" + i).disabled = true;
            document.getElementById("maxPsubjectCredit" + i).disabled = true;
            document.getElementById("maxLtheorySubject" + i).disabled = true;
            document.getElementById("maxPlabSubject" + i).disabled = true;
        }
    }
</script>