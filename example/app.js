/**
 * This example demonstrates a couple of different ads that you can display from Millennial Media.
 * We'll look at five different types of ads.
 */
Titanium.MillennialMedia = Ti.MillennialMedia = require("ti.millennialmedia");
Ti.MillennialMedia.apid = '54128';

var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});

/**
 * You can launch full screen ads when your app launches. After it is done, the user will be able to
 * see the rest of your app.
 */
win.add(Ti.MillennialMedia.createView({
    top: 0, right: 0, bottom: 0, left: 0,
    type: Ti.MillennialMedia.TYPE_LAUNCH
}));

/**
 * Next, an ad at the top of your app.
 */
win.add(Ti.MillennialMedia.createView({
    top: 0, left: 0,
    width: 320, height: 53,
    type: Ti.MillennialMedia.TYPE_TOP
}));

/**
 * Now, let's place an ad arbitrarily in the middle of our app. We'll also show off a couple additional properties that
 * control how often the ad refreshes, if the accelerometer can be used, and we'll track a goal for the ad.
 */
var middle = Ti.MillennialMedia.createView({
    top: 54, left: 0,
    width: 320, height: 53,
    type: Ti.MillennialMedia.TYPE_RECTANGLE,
    autoRefresh: true,
    refreshDuration: 60,
    accelerometerEnabled: false
});
win.add(middle);
bottom.trackGoal('My Ad Goal');

/**
 * You can also use ads interstitially -- that is, between views.
 */
var launchFullScreenTransition = Ti.UI.createButton({
    title: 'Launch Full Screen Transition',
    top: 220, left: 20,
    width: 300, height: 35
});
launchFullScreenTransition.addEventListener('click', function() {
    win.add(Ti.MillennialMedia.createView({
        top: 0, right: 0, bottom: 0, left: 0,
        type: Ti.MillennialMedia.TYPE_TRANSITION
    }));
});
win.add(launchFullScreenTransition);

/**
 * Finally, we'll create an ad at the bottom of our app. This ad will demonstrate the different
 * events that are available, and it will demonstrate goal tracking and manual refreshing.
 */
var bottom = Ti.MillennialMedia.createView({
    bottom: 0, left: 0,
    width: 320, height: 53,
    type: Ti.MillennialMedia.TYPE_BOTTOM,
    autoRefresh: false,
    autoLoad: false
});
function curryEventHandler(type) {
    return function() {
        Ti.API.info(type + ' fired!');
    }
}
var events = ['success', 'fail', 'refresh', 'tapped', 'isCaching', 'willTerminate',
    'modalWillAppear', 'modalDidAppear', 'modalWasDismissed'];
for (var k in events) {
    bottom.addEventListener(events[k], curryEventHandler(events[k]));
}
bottom.refresh();
win.add(bottom);

/**
 * That's it! Take a look at the docs if you want more information on particular properties.
 */
win.open();