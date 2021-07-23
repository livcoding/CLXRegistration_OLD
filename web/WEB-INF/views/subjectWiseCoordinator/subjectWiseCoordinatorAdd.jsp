<%-- 
    Document   : subjectWiseCoordinatorAdd
    Created on : Oct 19, 2019, 4:51:38 PM
    Author     : priyanka.tyagi
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 container" >       
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-8 col-lg-8 col-xs-8 col-md-8">
                    <select class="form-control" id="sub_code" name="sub_code" onchange="getCoordinator();" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select subject code!">
                        <option value="">Select</option>
                        <c:forEach items="${subject_code}" var="subject_code">
                            <option value="${subject_code[0]}~@~${subject_code[3]}"><c:out value="${subject_code[1]} / ${subject_code[2]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>

        </div>          
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Staff Department:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-8 col-lg-8 col-xs-8 col-md-8">
                    &nbsp;&nbsp; <input type="radio" name="staff" id="Sstaff" checked="true" onclick="getCoordinator();"   value="S"style="display:inline-block"> &nbsp; Same Department
                    &nbsp;&nbsp; <input type="radio" name="staff" id="Ostaff" onclick="getCoordinator();" value="O" style="display:inline-block"> &nbsp; Other Department
                </div>       
            </div>               

        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Coordinator Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-8 col-lg-8 col-xs-8 col-md-8">
                    <select class="form-control" id="coord_code" name="coord_code" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select Coordinator code!">
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="row col-lg-12 form-group" >  
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Remarks:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea id="remarks" name="remarks" maxlength="300" placeholder="Enter Remarks" class="form-control has-help-txt" style="width:100%;min-width: 100%;max-width: 100%;min-height:40px"> </textarea>
                </div>
            </div>
            <div class=" col-lg-12 form-group">
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label" >Chief Coordinator:</label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" id="chiefCoor" name="chiefCoor" value="Y"/>
                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group" >  
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label" id="Ad">
                        <span style="color: green"> Active</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>           
                    </div>
                </div>
            </div>
            <div class="row col-lg-12">
                <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                    <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                    <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button> 
                    <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                    &nbsp; &nbsp; &nbsp; <button type="button" class="btn  btn-info fa fa-question-circle" data-toggle="tooltip" onclick="hint();" style="padding:2px;font-size:25px;float:right;color:yellow" title="Hint.."/>
                </div>
            </div>
        </div>
    </div>
</form>
<script>

    function myReset() {
        $("#sub_code").val('').trigger("chosen:updated");
        $("#coord_code").empty();
        $("#coord_code").val('').trigger("chosen:updated");
        $("#Ad").html('<span style="color: green"> Active</span>');
    }

    $("#sub_code").chosen();
    $("#coord_code").chosen();
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
    function hint() {
        BootstrapDialog.show({
            title: 'Subejct_Coordinator',
            message: (Hint['subejct_coordinator']),
            buttons: [{
                    label: 'Ok',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
        });
    }


    function getCoordinator() {
        var radioValue = $("input[name='staff']:checked").val();
        if ($("#sub_code").val() != '') {
            $.ajax({
                url: "subjectWiseCoordinator/getCoordinator",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "sub_code=" + $("#sub_code").val() + '&status=' + radioValue,
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#coord_code").empty();
                    if (data.coordinatorsList != null) {
                        debugger;
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.coordinatorsList, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '~@~' + item[3] + '">' + item[1] + ' / ' + item[2] + '</option>'
                        });
                        $("#coord_code").append(str1);
                        $("#coord_code").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("Coordinator not found for this subject department...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Subject Code first!");
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
                                    url: "subjectWiseCoordinator/save",
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
                                            loadForm("subjectWiseCoordinator/list");
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
        $("#ajaxform").submit();
    }

</script>