//Once the document is ready
$(function() {
    // jQuery plugin to prevent double submission of forms
    jQuery.fn.preventDoubleSubmission = function() {
        $(this).bind('submit', function(e) {

            if ($(this).data('submitted') === true) {
                // Previously submitted - don't submit again
                e.preventDefault();
            } else {
                // Mark this so that the next submit can be ignored
                $(this).data('submitted', true);
            }
        });
        // Keep chainability
        return $(this);
    };
    $('form').preventDoubleSubmission();
});

function submitForm() {
    document.getElementById("newMeetResult").submit();
}




function validateFields( fields )
{
    let isOK = true;
    let errorMsg = "";
    fields.forEach( element => {
        let el = document.getElementById( element.id );
        if( !el || !el.value )
        {
            errorMsg += element.msg + "\n";
            isOK = false;
        }
    });
    if( errorMsg)
        alert( errorMsg );
    return isOK;
}

function validateListField( fields )
{
    let isOK = true;
    let errorMsg = "";
    fields.forEach( element => {
        let el = document.getElementById( element.id );
        if( !el || el.selectedIndex === "0")
        {
            errorMsg += element.msg + "\n";
            isOK = false;
        }
    });
    if( errorMsg)
        alert( errorMsg );
    return isOK;
}


function validatePowerlifting() {
    let isOK = validateFields([
        { id: 'meetResults.fullName',   msg: 'Event Name must have a value'}
    ]);
	 let isOK2 = validateListField([
        { id: 'meetResults.equipmentType',   msg: 'Equipment Type must have a value'},
        { id: 'meetResults.division',   msg: 'Division must have a value'}
    ]);
    if( !isOK || !isOk2 ) return false;

}


function validateOlympic() {
    let isOK = validateFields([
        { id: 'meetResults.fullName',   msg: 'Event Name must have a value'}
    ]);
	 let isOK2 = validateListField([
        { id: 'meetResults.division',   msg: 'Division must have a value'}
    ]);
    if( !isOK || !isOk2 ) return false;

}


