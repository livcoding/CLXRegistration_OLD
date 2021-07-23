<%-- 
    Document   : mainjstl
    Created on : Sep 19, 2018, 11:38:03 AM
    Author     : mohit1.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE html>
<style>
    table.dataTable thead tr{
        color: white !important;
        background-color: teal !important;
    }

    .table-wrapper-scroll-y {
        display: block;
        max-height: 300px;
        overflow-y: auto;
        -ms-overflow-style: -ms-autohiding-scrollbar;
    }
</style>
<script>
    var Success = {
        save_success: "Record Successfully Saved.",
        update_success: "Record Successfully Updated.",
        delete_success: "Record Successfully Deleted."
    };

    var Error = {
        error_code: "Form Submission Failed."
    }

    var Hint = {
        bulk_student_sub_choice: "<p><b><u>Prerequisites:</u></b><br> Step1. This form can be used to provide Bulk Student Subject Choice as per teaching scheme (Semester Wise) <br>Step2. Only those students will be considered which are not given in subject prefrences or previously Bulk Subject Choice has not been given any prototype <br>Step3. Subejct(Elective,Core) will be fetched as per Program subject Tagging <br>Step4. Error list will be shown on right side data grid if Student credit exceeds as per Program Min Max Limit</p>",
        department_subejct_tagging: "<p><b><u>Hints:</u></b><br> 1. Only those Subject will be shown which is not tagged with any Department and Active in Subject Master.</p>",
        subejct_coordinator: "<p><b><u>Prerequisites:</u></b><br> Step1. Department Subject Tagging must be define for Staff(Same Department)..</p>",
        student_subject_finalization: "<p><b><u>Hints:</u></b><br> 1. Only those Students Will be shown whose Registration Confirmation is not Done <br> 2. If Registration Confirmation is done of Students then Subjects will be Add by Add/Drop Form (Advance)<br> 3. For 1<sup>st</sup> Semester and 3<sup>rd</sup> Semester(Lateral Entry) Finalization if not required (If Subject choice not submited). These student can be directly finalized in <b>Add/Drop Basic</b> form</p>",
        add_drop_basic: "<p><b><u>Hints:</u></b><br> 1. Add/Drop Basic will be used only for First Semester Students <br> 2. If Student LateralEntry in Student master is 'Y' then Third Semester Students will be consider <br> 3. If Student Registration Confirmation is not done then All Subjects (Include Drop subjects or which Load Distribution is assigned) Will be Added automatically <br> 4. Batch Wise Registration Process Date must be define if Collect Late Registration Fine </p>",
        suppl_subj_entry: "<p><b><u>Hints:</u></b><br> 1. Once the fee gets finalized, You can't add more Subjects...! </p>",
        summer_subj_entry: "<p><b><u>Hints:</u></b><br> 1. Only those Enrollment No. will be shown in which atleast one Subject is Offered in Offer Subect Tagging...<br> 2. Only those Subject will be shown which Offer Subect Tagging is defined. <br>3. Once the fee gets finalized, You can't add more Subjects...!</p>"
    }


    $('[name=duallistbox]').bootstrapDualListbox({
        // default text
        filterTextClear: 'show all',
        filterPlaceHolder: 'Filter',
        moveSelectedLabel: 'Move selected',
        moveAllLabel: 'Move all',
        removeSelectedLabel: 'Remove selected',
        removeAllLabel: 'Remove all',
        // true/false (forced true on androids, see the comment later)
        moveOnSelect: true,
        // 'all' / 'moved' / false                                           
        preserveSelectionOnMove: false,
        // 'string', false                                     
        selectedListLabel: false,
        // 'string', false
        nonSelectedListLabel: false,
        // 'string_of_postfix' / false                                                      
        helperSelectNamePostfix: '_helper',
        // minimal height in pixels
        selectorMinimalHeight: 100,
        // whether to show filter inputs
        showFilterInputs: true,
        // string, filter the non selected options                                                 
        nonSelectedFilter: '',
        // string, filter the selected options                                              
        selectedFilter: '',
        // text when all options are visible / false for no info text                      
        infoText: 'Showing all {0}',
        // when not all of the options are visible due to the filter                                         
        infoTextFiltered: '<span class="badge badge-warning">Filtered</span> {0} from {1}',
        // when there are no options present in the list
        infoTextEmpty: 'Empty list',
        // sort by input order
        sortByInputOrder: false,
        // filter by selector's values, boolean
        filterOnValues: false,
        // boolean, allows user to unbind default event behaviour and run their own instead                                                   
        eventMoveOverride: false,
        // boolean, allows user to unbind default event behaviour and run their own instead                                                
        eventMoveAllOverride: false,
        // boolean, allows user to unbind default event behaviour and run their own instead
        eventRemoveOverride: false,
        // boolean, allows user to unbind default event behaviour and run their own instead                                              
        eventRemoveAllOverride: false,
        // sets the button style class for all the buttons
        btnClass: 'btn-outline-secondary',
        // string, sets the text for the "Move" button                                            
        btnMoveText: '&gt;',
        // string, sets the text for the "Remove" button                                                        
        btnRemoveText: '&lt;',
        // string, sets the text for the "Move All" button
        btnMoveAllText: '&gt;&gt;',
        // string, sets the text for the "Remove All" button
        btnRemoveAllText: '&lt;&lt;'

    });


</script>