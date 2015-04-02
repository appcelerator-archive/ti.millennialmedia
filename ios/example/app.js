/**
 * This example demonstrates a couple of different ads that you can display from Millennial Media.
 */
var IPAD = (Ti.Platform.osname === 'ipad');
var IOS = (Ti.Platform.osname === 'iphone' || Ti.Platform.osname === 'ipad');
var MillennialMedia = require('ti.millennialmedia');

// Set the log level on the module
MillennialMedia.setLogLevel(MillennialMedia.LOG_LEVEL_VERBOSE);

MillennialMedia.geolocation = {
    latitude:41.866, 'longitude':-88.107
};

// Set demographics using module constants
MillennialMedia.demographics = {
    age: '23',
    gender: MillennialMedia.GENDER_MALE,
    education: MillennialMedia.EDUCATION_SOME_COLLEGE,
    ethnicity: MillennialMedia.ETHNICITY_HISPANIC,
    maritalStatus: MillennialMedia.MARITAL_DIVORCED,
    zipCode: '60187'
};

MillennialMedia.custom = {
    something: "CUSTOM1",
    'somethingelse' : "CUSTOM2"
};

MillennialMedia.keywords = ['KEYWORD1', 'KEYWORD2'];

var win = Ti.UI.createWindow({
    backgroundColor:'white'
});

/**
 * Here we demonstrate the different events available
 */
function curryEventHandler(type) {
    return function () {
        Ti.API.info(type + ' fired!');
    }
}

// 'applicationWillTerminateFromAd', 'modalWillAppear', and 'modalWillDismiss' are
// iOS only, but it does not hurt to add their listeners on Android.
var events = ['adRequestComplete', 'applicationWillTerminateFromAd', 'adWasTapped',
    'modalWillAppear', 'modalDidAppear', 'modalWillDismiss', 'modalDidDismiss'];
for (var k in events) {
    MillennialMedia.addEventListener(events[k], curryEventHandler(events[k]));
}

MillennialMedia.addEventListener('adRequestComplete', function (e) {
    if (!e.success) {
        alert('Request Error: ' + e.error);
    }
});

/**
 * To keep this demo easy to understand, I have split the different types of ads in to their
 * own JavaScript files. Look at the following files to find out more:
 */
Ti.include(
    'top.js',
    'bottom.js',
    'rectangle.js',
    'interstitial.js'
);

/**
 * That's it! Take a look at the docs if you want more information on particular properties.
 */
win.open();
