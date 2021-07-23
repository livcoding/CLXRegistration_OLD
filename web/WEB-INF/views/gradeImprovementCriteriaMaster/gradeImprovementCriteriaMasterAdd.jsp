<%-- 
    Document   : gradeImprovementCriteriaMasterAdd
    Created on : Mar 12, 2019, 12:16:36 PM
    Author     : campus.trainee
--%>

<%@include file="../mainjstl.jsp"%>
<style >

    .table-wrapper-scroll-y2 { height: 300px;  overflow: scroll}

</style> 
<form id="ajaxform" class="form-horizontal">
        <div class="col-lg-12 container">
            <label style="text-transform:capitalize;" ><u>Eligibility Criteria :-</u></label>
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                        <select class="form-control" id="programCode" name="programid" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select program code!">
                            <option value="">Select</option>
                            <c:forEach items="${programList}" var="list">
                                <option value="${list[2]}"><c:out value="${list[0]} -- ${list[1]}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">CGPA Range From:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                        <input type="text" name="cgpaFrom" id="cgpaRangeFrom" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-allowing="float" placeholder="Enter CGPA" class="form-control"  data-validation-error-msg="Please enter cgpa range from!">
                    </div>
                </div>
            </div> 
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">CGPA Range UpTo:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                        <input type="text" name="cgpaTo" id="cgpaRangeUpTo" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-allowing="float" placeholder="Enter CGPA" class="form-control" data-validation-error-msg="Please enter cgpa range upto!">
                    </div>
                </div>
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Applicable Grades:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                        <input type="text"  data-toggle="tooltip" title="Grade Value i.e A,B,C and so on " onkeyup="this.value = this.value.toUpperCase();" name="applicablegrades" id="applicableGrades" maxlength="10" data-validation-help="Max Length is 8" data-validation="required" placeholder="Enter Applicable Grades" class="form-control" data-validation-error-msg="Please enter applicable grades!">
                    </div>
                </div>
            </div>        
        </div>        
        <div class="col-lg-12 container">
            <label style="text-transform:capitalize;" ><u>GIP Result Criteria :-</u></label>
            <div class="row col-lg-12 form-group" ><br>
                <div class="row col-lg-6 form-group">
                    <label  class="col-sm-7 control-label">If student gets Fail in GIP exam:</label>
                    <div class="col-sm-10 col-sm-offset-2"><br>
                        <input type="radio" name="assignGrade" id="Last" value="L"  checked="true" > Assign Previous Passing Grade<br>
                        <input type="radio" name="assignGrade" id="Current" value="C"   > Assign Current Fail Grade<br>
                    </div>
                </div>
            </div>        
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label  class="col-sm-10 control-label">Grade to be assign If student Passed in GIP exam:</label>

                </div> <div class="col-sm-10 col-sm-offset-1"> 
                    <div id="test" class="row col-lg-12 form-group table-wrapper-scroll-y2" style="border:1px solid lightgray;margin-top:-15px">
                      <br>  <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                            <thead id="header">
                                <tr class="" style="background-color:#008080;color:white">
                                    <th>Sl.No</th>
                                    <th>Select</th>
                                    <th>Grade</th>
                                    <th>Grade Description</th>
                                </tr>
                            </thead>  
                            <tbody>
                                <% int i = 1;%>
                                <c:forEach items="${grades}" var="list">
                                    <tr id="${list[0]}:${list[1]}">
                                        <td align="center">
                                            <%=i%>
                                        </td>
                                        <td><input type="checkbox" name="chk<%=i%>" value="${list[1]}"/></td>
                                        <td>${list[2]}</td>
                                        <td>${list[3]}</td>
                                    </tr>
                                    <% i++;%>
                                </c:forEach> 
                            <input type="hidden" name="count" value="<%=i%>">
                            </tbody>
                        </table>
                    </div>
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
</form>

<script>


    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();

    });



    $("#programCode").chosen();

    $("#cgpaRangeUpTo").focusout(function () {

        if (parseFloat($("#cgpaRangeFrom").val()) > parseFloat($("#cgpaRangeUpTo").val()))
        {
            BootstrapDialog.alert("Range UpTo can't be less than Range From Value");
            document.getElementById("cgpaRangeUpTo").value = '';
        }
        if (parseFloat($("#cgpaRangeFrom").val()) == parseFloat($("#cgpaRangeUpTo").val()))
        {
            BootstrapDialog.alert("Range UpTo can't be same to Range From Value");
            document.getElementById("cgpaRangeUpTo").value = '';
        }
    });
    $("#cgpaRangeFrom").focusout(function () {

        if (parseFloat($("#cgpaRangeFrom").val()) > parseFloat($("#cgpaRangeUpTo").val()))
        {
            BootstrapDialog.alert("Range UpTo can't be less than Range From Value");
            document.getElementById("cgpaRangeUpTo").value = '';
        }
        if (parseFloat($("#cgpaRangeFrom").val()) == parseFloat($("#cgpaRangeUpTo").val()))
        {
            BootstrapDialog.alert("Range UpTo can't be same to Range From Value");
            document.getElementById("cgpaRangeUpTo").value = '';
        }
    });

    function myReset() {
        $("#programCode").val('').trigger("chosen:updated");
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
                                    url: "gradeImprovementCriteriaMaster/save",
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
                                            toastr.success('Record Successfully Saved', "Success!!");
                                            loadForm("gradeImprovementCriteriaMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
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
        $("#ajaxform").submit();
    }
</script>