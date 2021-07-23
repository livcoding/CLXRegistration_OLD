<%-- 
    Document   : RegistrationParametersList
    Created on : 17 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="row col-lg-6 form-group">
        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Parameters:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
            <select class="form-control" id="parameters" onchange="getGridDate();" name="parameters" data-validation="required">
                <option value="">Select</option>
                <option value="supplementry">Supplementary Registration</option>
                <option value="attendance">Attendance Entry</option>
                <option value="pgregistration">PG Registration</option>
                <option value="marksview">Marks View</option>
                <option value="course">Course Registration</option>
                <option value="summerReg">Summer Registration</option>
                <option value="other">Others Registration</option>
            </select>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top:30px;"></div>
    <div class="col-lg-12" style="margin-top:10px;">
        <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
        <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
            <thead id="header">
                <tr class="" >
                    <th style="display:none" ></th>
                    <th width="20px">Sl.No</th>
                    <th width="150px">Parameter ID</th>
                    <th>Parameter Description</th>
                    <th>Value</th>
                    <th width="100px">Data Type</th>
                    <!--<th width="30px">SeqId</th>-->
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</form>
<script>

    $("#parameters").chosen({width: "100%"});

    var cb = {
        add: {
            link: "",
            name: "",
            value: ""
        },
        edit: {
            link: "registrationParameters/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "registrationParameters/delete",
            name: "",
            value: ""
        },
        list: {
            link: "registrationParameters/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    commonButtonMethod(cb);

    function getGridDate() {
        $('#datatable').DataTable().clear();
        var tablelist = $('#datatable').DataTable();
        var parameters = $("#parameters").val();
        if (parameters != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "registrationParameters/getGridDate",
                data: '&parameters=' + $("#parameters").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.gridData != '' && data.gridData != null) {
                        $('#datatable').DataTable().clear();
                        var count = 1;
                        var value = ''
                        var seqid = ''
                        var datatype = ''

                        $.each(data.gridData, function (i, item)
                        {
                            if(item[4]==''||item[4]==null){
                                value="--";                                
                            }else{
                                value=item[4];
                            }
                            if(item[5]==''||item[5]==null){
                                seqid="--";                                
                            }else{
                                seqid=item[5];
                            }
                            if(item[6]==''||item[6]==null){
                                datatype="--";                                
                            }else if(item[6]=='N'){
                                datatype="Numeric";
                            }else if(item[6]=='C'){
                                datatype="Character";
                            }
                            var str1 = '<tr id="' + item[0] + '~@~' + item[1] + '~@~' + item[2] +'"</tr>';
                            str1 += '  <td style="display:none"></td>'; 
                            str1 += '  <td>' + count + '</td>'; //Sno.
                            str1 += '  <td>' + item[2] + '</td>'; //parametercode
                            str1 += '  <td>' + item[3] + '</td>'; //parameterdecs
                            str1 += '  <td>' + value + '</td>'; //value
                            str1 += '  <td>' + datatype + '</td>'; //datatype
//                            str1 += '  <td>' + seqid + '</td></tr>'; //seqid
                            tablelist.row.add($(str1)).draw();
                            count++;
                        });
                    } else {
                        $('#datatable').DataTable().clear().draw();
                        BootstrapDialog.alert("No Data found for this Parameter");
                        $("#parameters").val('').trigger("chosen:updated");
                    }
                    setTimeout($.unblockUI, 1000);

                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Parameters...");
        }
    }

</script>