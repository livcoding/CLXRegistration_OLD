<%-- 
    Document   : basketMasterList
    Created on : Feb 6, 2019, 3:49:32 PM
    Author     : ankit.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row col-lg-12 form-group">
    <div class="row col-lg-6 form-group">
        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program Code:
            <span style="font-size:19px;font-weight:bold;"  class="text-danger">*</span></label>
        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
            <select class="form-control" id="prog_code_list" name="prog_code_list"  onchange="getSectionCodeForList();" data-live-search="true" data-validation="required">               
                <option value="">Select</option>
                <c:forEach items="${prog_list}" var="prog_list">
                    <option value="${prog_list[2]}"><c:out value="${prog_list[0]} / ${prog_list[1]}"/></option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Section/Branch:
            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
            <select class="form-control" id="sec_code_list" name="sec_code_list" onchange="getBasketMaster();" data-validation="required" data-live-search="true">
                <!--                <option value="">Select</option>
                <c:forEach items="${reg_list}" var="sec_list">
                    <option value="${sec_list[2]}"><c:out value="${sec_list[0]} / ${sec_list[1]}"/></option>
                </c:forEach> -->
            </select>
        </div>
    </div>
    <!--    <div class="col-sm-6 col-lg-1 col-xs-6 col-md-6">
            <button type="button" class="fa fa-search btn btn-success" onclick="getBasketMaster();"></button>
        </div>-->
</div>

<div class="col-lg-12" style="margin-top:10px;"></div>
<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th style="display:none"></th>
                <th>Sl.No</th>
                <th>Program Code</th>
                <th>Section/Branch</th>
                <th>STY Number</th>
                <th>Basket Code</th>
                <th>Basket Description</th>
                <th>Total No. of Subjects</th>
                <th>Min No. of Subjects</th>
                <th>Max No. of Subjects</th>
                <!--<th>Subject Number Count(Optional)</th>-->
                <!--<th>Subject Preference</th>-->
                <th>De-Active</th>
            </tr>
        </thead>  
        <tbody>
        </tbody>
    </table>
</div> 
<script>
    $("#prog_code_list").chosen();
    $("#sec_code_list").chosen();

    var val = getValue();
    var link = val == 'N' ? 'basketMasterController/add' : '';
    var cb = {
        add: {
            link: link,
            name: "Add",
            value: ""
        },
        edit: {
            link: "basketMasterController/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "",
            name: "",
            value: ""
        },
        list: {
            link: "basketMasterController/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }

    }


    function getBasketMaster() {
        $.blockUI();
        var prog_code = ($("#prog_code_list").val());
        var sec_code = ($("#sec_code_list").val());
        $('#datatable').DataTable().clear().draw();
        if (prog_code != '' && sec_code != '') {
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "basketMasterController/getBasketMaster",
                data: '&prog_code=' + $("#prog_code_list").val() + '&sec_code=' + $("#sec_code_list").val(),
                dataType: "json",
                async: true,
                timeout: 10000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var j = 1;
                    var str = '';
                    var str2 = '';
                    var str3 = '';
                    var str4 = '';
                    var str5 = '';
                    var str6 = '';
                    if (data.basket_list != null && data.basket_list != '') {
                        $.each(data.basket_list, function (i, item)
                        {
                            if (item[6] == null)
                            {
                                str5 = '';
                            } else {
                                str5 = item[6];
                            }
                            if (item[7] == null)
                            {
                                str6 = '';
                            } else {
                                str6 = item[7];
                            }
                            if (item[11] == null)
                            {
                                str4 = '';
                            } else {
                                str4 = item[11];
                            }
                            if (item[10] == 'N')
                            {
                                str3 = 'NO';
                            } else {
                                str3 = 'YES';
                            }
                            if (item[8] == 'N')
                            {
                                str = 'NO';
                            } else {
                                str = 'YES';
                            }
                            if (item[9] == 'C')
                            {
                                str2 = 'Check BOX';
                            } else if (item[9] == 'P')
                            {
                                str2 = 'Combo';
                            } else {
                                str2 = 'None';
                            }
                            var str1 = '<tr id="' + item[0] + '">';
                            str1 += '<td style="display:none"> </td> ';
                            str1 += '  <td>' + j + '</td>';
                            str1 += '  <td>' + item[12] + '</td>';
                            str1 += '  <td>' + item[13] + '</td>';
                            str1 += '  <td>' + item[4] + '</td>';
                            str1 += '  <td>' + item[1] + '</td>';
                            str1 += '  <td>' + item[2] + '</td>';
                            str1 += '  <td>' + str4 + '</td>';
                            str1 += '  <td>' + str5 + '</td>';
                            str1 += '  <td>' + str6 + '</td>';
//                            str1 += '  <td>' + str3 + '</td>';
//                            str1 += '  <td>' + str2 + '</td>';
                            str1 += '  <td>' + str + '</td></tr>';
                            tablelist.row.add($(str1)).draw();
                            j++;
                        });
                    } else {
                        BootstrapDialog.alert("No Data Found..");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
            setTimeout($.unblockUI, 2000);
        } else {
            $.unblockUI();
            BootstrapDialog.alert("Please Select Required Fields..");
        }
    }
    function getSectionCodeForList() {
        var prog_code = $("#prog_code_list").val();
        $('#datatable').DataTable().clear().draw();
        $("#sec_code_list").empty();
        $.ajax({
            type: "POST",
            url: "basketMasterController/getSectionCodeForList",
            data: '&programid=' + $("#prog_code_list").val(),
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
                $.each(data.secList, function (i, item)
                {
                    str1 += '<option value="' + item[2] + '">' + item[0] + '/' + item[1] + '</option>'
                });
                $("#sec_code_list").append(str1);
                $("#sec_code_list").trigger("chosen:updated");
            },
            error: function (response) {
                BootstrapDialog.alert("Some Thing Went Wrong...");
            }
        });
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
</script>


