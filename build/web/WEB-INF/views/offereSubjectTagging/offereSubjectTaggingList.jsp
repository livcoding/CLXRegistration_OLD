<%-- 
    Document   : offereSubjectTaggingList
    Created on : 18 Dec, 2018, 5:15:05 PM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                    <select class="form-control" id="semesterCode" name="semesterCode" data-validation="required" data-live-search="true">               
                        <option value="">Select</option>
                        <c:forEach items="${semester}" var="list">
                            <option value="${list[0]}"><c:out value="${list[1]} -- ${list[2]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>
            <div class="row col-lg-3 form-group">
                <a href="javascript:loadData();" class="btn btn-success fa fa-search"></a>
            </div>
        </div>
        <div class="col-lg-12" style="margin-top:10px;">
            <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
            <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead id="header">
                    <tr class=""  >
                        <!--<th width="1px;"></th>-->
                        <th >Sl.No</th>
                        <th >Semester Code</th>
                        <th style="width:70px;height:30px;">Current Subject Code</th>
                        <th style="width:50px">Credits</th>
                        <th  >Old Subject Code</th>
                        <th style="width:150px">Department Name</th>
                        <th  >Deactive</th>
                    </tr>
                </thead> 
            </table>
        </div>
    </div> 
</form>

<script>
    $("#semesterCode").chosen();

    var cb = {
        add: {
            link: "offereSubjectTagging/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "offereSubjectTagging/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "offereSubjectTagging/delete",
            name: "",
            value: ""
        },
        list: {
            link: "offereSubjectTagging/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }

    commonButtonMethod(cb);

    function callChkBox(index) {
        var value = $("#chk" + index).val();
        if ($("#chk" + index).prop('checked') == true) {
            $("#chk" + index).prop("checked", false);
            $("#del_img" + index).show();
            $("#d_img" + index).hide();
        } else {
            $("#chk" + index).prop("checked", true);
            checkChild(value, index);
        }
    }
    function checkChild(v, i) {
        $("#del_img" + i).hide();
        $("#d_img" + i).show();
    }

    function loadData() {
        var semesterCode = $("#semesterCode").val();
        $('#datatable').DataTable().clear().draw();
        var itemChildList = $('#datatable').DataTable();
        if (semesterCode != '') {
            $.ajax({
                type: "POST",
                url: "offereSubjectTagging/getGridData",
                data: "semesterCode=" + $("#semesterCode").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    $.blockUI();
                    var count = new Array();
                    if (data.gridData != '') {
                        $.blockUI();
                        var countt = 1;
                        var j = 1;
                        var str2 = '';
                        var str3 = '';
                        $.each(data.gridData, function (i, item)
                        {
                            var str1 = '';
                            var cou = 0;
                            if (item[3] == null) {
                                str2 = '';
                            } else {
                                str2 = item[3];
                            }
                            if (item[5] == null) {
                                str3 = '';
                            } else {
                                str3 = item[5];
                            }
                            str1 += ' <tr id="' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '">'; //item[6] --Instituteid,item[7]--registrationid,item[8]--offerid
                            str1 += ' <td>' + countt + '</td>';
                            str1 += ' <td>' + item[0] + '</td>'; //Semester Code
                            str1 += ' <td>' + item[1] + '</td>'; //Current Subject Code
                            str1 += ' <td>' + item[2] + '</td>'; //Credits
                            str1 += ' <td>' + str2 + '</td>';    //Old Subject Code
                            str1 += ' <td>' + item[4] + '</td>'; //Department Name
                            str1 += ' <td>' + str3 + '</td>';    //Deactive
                            str1 += ' </tr>';
                            count.push(cou++);
                            countt++;
                            itemChildList.row.add($(str1)).draw();
                        });
                        setTimeout($.unblockUI, 1000);
                    } else {
                        $.unblockUI();
                        BootstrapDialog.alert("Data Not Found...!");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code...!");
        }
    }

</script>