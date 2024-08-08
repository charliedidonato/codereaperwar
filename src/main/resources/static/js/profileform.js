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
    document.getElementById("newProfileMulti").submit();
}

function checkUserName()
{
    fetch( `/atlas/userReg/ajax/userNameExists?userName=${document.querySelector('#user\\.username').value}`, {
        method: 'POST',
    })
    .then( response => response.text())
    .then( data => document.querySelector('#userNameExists').innerHTML ='<span id="userNameExists">' + data + '</span>' );
}

function checkEmail()
{
    fetch( `/atlas/userReg/ajax/emailExists?email=${document.querySelector('#email\\.email').value}`, {
        method: 'POST',
    })
    .then( response => response.text())
    .then( data => document.querySelector('#emailExists').innerHTML ='<span id="emailExists">Exists ?' + data + '</span>' );
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

function validate1() {
    return validateFields( [
        { id: 'email.email',   msg: 'Email must have a value'},
    ]);
}

function validate2() {
    let isOK = validateFields([
        { id: 'person.firstName',   msg: 'First Name must have a value'},
        { id: 'person.lastName',    msg: 'Last Name must have a value'},
        { id: 'person.birthYear',   msg: 'Please select a Birth Year.'},
        { id: 'user.username',      msg: 'User Name must have a value'},
        { id: 'user.password',      msg: 'Password must have a value'},
        { id: 'confirmPassword',   msg: 'Comfirm Password must have a value'}
    ]);

    if( !isOK ) return false;


	var username = document.getElementById('user.username').value;
	var usernamePattern = new RegExp("^[a-zA-Z0-9]{3,20}$");
	var res = usernamePattern.test(username);
	if (!res) {
		var msg = "Usernames must be at least 3 characters up to 20 characters long.\n"+
		    "The only valid characters for user name are a-z, A-Z and 0-9";
        alert(msg);
        return false;
    }



    if ( document.getElementById('user.password').value != document.getElementById('confirmPassword').value) {
	    alert('Password and Confirm password do NOT match');
	    return(false);
	}

	var password = document.getElementById('user.password').value;
	var passwordPattern = new RegExp("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
	var res = passwordPattern.test(password);
	if (!res) {
		var msg = "Password must be at least 6 characters long up to 20 characters.\n"+
		    "Passwords must contain at least 1 upper and lower case letter as well as 1 digit." +
		    "The only valid characters for password are a-z, A-Z and 0-9";
        alert(msg);
        return false;
    }



	return (isOK);
}

function validate3() {
    let isOK = validateFields([
        { id: 'lifter.experienceLifting',   msg: 'Lifter Experience must have a value'},
        { id: 'lifter.federationCode',    msg: 'Please select a Federation.'},
        { id: 'person.weight',   msg: 'Lifter Weight must have a value'},
        { id: 'person.height',      msg: 'Lifter Height must have a value'},
        { id: 'lifter.bio',      msg: 'Lifter Bio must have a value'},
        { id: 'user.password',      msg: 'Password must have a value'}
    ]);
    if( !isOK ) return false;


	var nationality = document.getElementById("person.nationality");
    if (nationality.selectedIndex=="0") {
        alert('Please select a Country.');
        return false;
    }

    var heightUnits = document.getElementById("person.heightUnitsCode");
    if (heightUnits.selectedIndex=="0") {
        alert('Please select a Lifter Height Units Code.');
        return false;
    }

    var weightUnits = document.getElementById("person.weightUnitsCode");
    if (weightUnits.selectedIndex=="0") {
        alert('Please select a Lifter Weight Units Code.');
        return false;
    }

    if( document.getElementById("pictureExtList[0].pictureFile").files.length === 0 ){
        alert('Please Select one primary picture for your Profile');
        return false;
    }

    if( !document.getElementById('person.photoAgreement').checked )
    {
        alert( 'Please agree to the terms and conditions')
        return false;
    }

    return true;
}

function validateEditProfile() {
    let isOK = validateFields([
        { id: 'person.firstName',   msg: 'First Name must have a value'},
	    { id: 'person.lastName',    msg: 'Last Name must have a value'},
        { id: 'person.birthYear',   msg: 'Please select a Birth Year.'},
        { id: 'lifter.experienceLifting',   msg: 'Lifter Experience must have a value'},
        { id: 'lifter.federationCode',    msg: 'Please select a Federation.'},
        { id: 'person.weight',   msg: 'Lifter Weight must have a value'},
        { id: 'person.height',      msg: 'Lifter Height must have a value'},
        { id: 'lifter.bio',      msg: 'Lifter Bio must have a value'},
        { id: 'person.heightUnitsCode',      msg: 'Please select a Lifter Height Units Code.'},
        { id: 'person.weightUnitsCode',      msg: 'Please select a Lifter Weight Units Code.'},
        { id: 'person.nationality',      msg: 'Please select a Country.'},
        { id: 'email.email',   msg: 'Email must have a value'}
    ]);
    if( !isOK ) return false;

     var radioGroup = document.getElementById( "primaryPicture" );

	 var isChecked = false;
	 if (radioGroup.length == null) {
	     if (radioGroup.value > 0) {
	    	    isChecked = true;
	     }
	 }

	    if (!isChecked) {
            alert('Select a Primary Picture for your Profile.');
            return false;

	    }

    return true;
}
