

//variables for testing incoming sms
var smsTextBoxIncoming;
var charCounterIncoming;
var creditCounterIncoming;
var charCounterIncomingContainer;

//variables for testing outgoing sms
var smsTextBoxOutgoing;
var charCounterOutgoing;
var creditCounterOutgoing;
var charCounterOutgoingContainer;

var SMS_CHARACTER_LIMIT = 160;

function initialiseIncoming(smsTextBoxIncomingID, charCounterIncomingID, creditCounterIncomingID, charCounterIncomingContainerID) {
    // Declare variables
    smsTextBoxIncoming = document.getElementById(smsTextBoxIncomingID);
    charCounterIncoming = document.getElementById(charCounterIncomingID);
    creditCounterIncoming = document.getElementById(creditCounterIncomingID);
    charCounterIncomingContainer = document.getElementById(charCounterIncomingContainerID);

    // Initialize elements
    smsTextBoxIncoming.value = "";
    charCounterIncoming.innerHTML = smsTextBoxIncoming.value.length;
    creditCounterIncoming.innerHTML = 0;

    // Set the colour of the character counter	
    charCounterIncomingContainer.style.color = "#008c00";
}

function counterIncoming() {

    var characterCount = smsTextBoxIncoming.value.length;
    var creditCount = 1;

    if (characterCount === 0) {
        // Reset the colour of the character counter
        charCounterIncomingContainer.style.color = "#008c00";

        creditCount = 0; // Set sms credits to 0 since there are no characters

        charCounterIncoming.innerHTML = characterCount;
        creditCounterIncoming.innerHTML = creditCount;

    } else if (characterCount > 0 && characterCount <= SMS_CHARACTER_LIMIT) {
        // Reset the colour of the character counter
        charCounterIncomingContainer.style.color = "#008c00";

        charCounterIncoming.innerHTML = characterCount;
        creditCounterIncoming.innerHTML = creditCount;

    } else if (characterCount > SMS_CHARACTER_LIMIT) {
        // Indicate 160 character limit of an SMS is exceeded by changing colour 
        charCounterIncomingContainer.style.color = "red";

        // Calculate and display the SMS credits to be used
        if (Math.floor(characterCount / SMS_CHARACTER_LIMIT) > 0) {
            if (characterCount % SMS_CHARACTER_LIMIT != 0) {
                creditCount = creditCount + Math.floor(characterCount / SMS_CHARACTER_LIMIT);
            } else {
                creditCount = Math.floor(characterCount / SMS_CHARACTER_LIMIT);
            }

            creditCounterIncoming.innerHTML = creditCount;
        }

        charCounterIncoming.innerHTML = characterCount;

    } else {
        charCounterIncoming.innerHTML = characterCount;
    }




}

function initialiseOutgoing(smsTextBoxOutgoingID, charCounterOutgoingID, creditCounterOutgoingID, charCounterOutgoingContainerID) {
    // Declare variables
    smsTextBoxOutgoing = document.getElementById(smsTextBoxOutgoingID);
    charCounterOutgoing = document.getElementById(charCounterOutgoingID);
    creditCounterOutgoing = document.getElementById(creditCounterOutgoingID);
    charCounterOutgoingContainer = document.getElementById(charCounterOutgoingContainerID);

    // Initialize elements
    smsTextBoxOutgoing.value = "";
    charCounterOutgoing.innerHTML = smsTextBoxOutgoing.value.length;
    creditCounterOutgoing.innerHTML = 0;

    // Set the colour of the character counter	
    charCounterOutgoingContainer.style.color = "#008c00";
}

function counterOutgoing() {

    var characterCount = smsTextBoxOutgoing.value.length;
    var creditCount = 1;

    if (characterCount === 0) {
        // Reset the colour of the character counter
        charCounterOutgoingContainer.style.color = "#008c00";

        creditCount = 0; // Set sms credits to 0 since there are no characters

        charCounterOutgoing.innerHTML = characterCount;
        creditCounterOutgoing.innerHTML = creditCount;

    } else if (characterCount > 0 && characterCount <= SMS_CHARACTER_LIMIT) {
        // Reset the colour of the character counter
        charCounterOutgoingContainer.style.color = "#008c00";

        charCounterOutgoing.innerHTML = characterCount;
        creditCounterOutgoing.innerHTML = creditCount;

    } else if (characterCount > SMS_CHARACTER_LIMIT) {
        // Indicate 160 character limit of an SMS is exceeded by changing colour 
        charCounterOutgoingContainer.style.color = "red";

        // Calculate and display the SMS credits to be used
        if (Math.floor(characterCount / SMS_CHARACTER_LIMIT) > 0) {
            if (characterCount % SMS_CHARACTER_LIMIT != 0) {
                creditCount = creditCount + Math.floor(characterCount / SMS_CHARACTER_LIMIT);
            } else {
                creditCount = Math.floor(characterCount / SMS_CHARACTER_LIMIT);
            }

            creditCounterOutgoing.innerHTML = creditCount;
        }

        charCounterOutgoing.innerHTML = characterCount;

    } else {
        charCounterOutgoing.innerHTML = characterCount;
    }



}