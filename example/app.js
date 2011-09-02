/**
 * This example demonstrates a couple of different ads that you can display from Millennial Media.
 * We'll look at five different types of ads.
 */
Titanium.MillennialMedia = Ti.MillennialMedia = require("ti.millennialmedia");
Ti.MillennialMedia.apid = '54131';
Ti.MillennialMedia.demographics = {
    age: '23',
    gender: 'male',
    zip: '60187',
    lat: '41.866', 'long': '-88.107'
};

var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});
/**
 * To keep this demo easy to understand, I have split the different types of ads in to their
 * own JavaScript files. Look at the following files to find out more:
 */
Ti.include(
    'launch.js',
    'top.js',
    'bottom.js',
    'rectangle.js',
    'transition.js'
);

/**
 * That's it! Take a look at the docs if you want more information on particular properties.
 */
win.open();