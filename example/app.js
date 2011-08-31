/**
 * This example demonstrates a couple of different ads that you can display from Millennial Media.
 * We'll look at five different types of ads.
 */
Titanium.MillennialMedia = Ti.MillennialMedia = require("ti.millennialmedia");
Ti.MillennialMedia.apid = '54128';
Ti.MillennialMedia.demographics = {
    age: '23',
    gender: 'male',
    zip: '60187',
    lat: '41.866', long: '-88.107'
};

var activeDemo = 1;
var demos = ['launch', 'top', 'rectangle', 'transition', 'bottom'];

var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});

if (demos[activeDemo] == 'launch') {
    /**
     * You can show a full screen ad when your app launches. After it is done, the user will be able to
     * see the rest of your app.
     */
    var launch = Ti.MillennialMedia.createView({
        type: Ti.MillennialMedia.TYPE_LAUNCH,
        autoRefresh: false
    });
    launch.addEventListener('fail', function() {
        Ti.API.info('fail fired!');
        win.remove(launch);
    });
    launch.addEventListener('modalWasDismissed', function() {
        Ti.API.info('modalWasDismissed fired!');
        win.remove(launch);
    });
    win.add(launch);
    launch.refresh();
}
else if (demos[activeDemo] == 'top') {
    /**
     * Next, an ad at the top of your app.
     */
    win.add(Ti.MillennialMedia.createView({
        top: 0, left: 0,
        width: 320, height: 53,
        type: Ti.MillennialMedia.TYPE_TOP
    }));
}
else if (demos[activeDemo] == 'rectangle') {
    /**
     * Now, let's place an ad arbitrarily in the middle of our app.
     */
    win.add(Ti.MillennialMedia.createView({
        top: 54, left: 0, right: 0,
        height: 150,
        type: Ti.MillennialMedia.TYPE_RECTANGLE,
        refreshDuration: 60
    }));

}
else if (demos[activeDemo] == 'transition') {
    /**
     * You can also use ads interstitially -- that is, between views.
     */
    var launchFullScreenTransition = Ti.UI.createButton({
        title: 'Launch Full Screen Transition',
        top: 220, left: 20, right: 20,
        height: 35
    });
    launchFullScreenTransition.addEventListener('click', function() {
        win.add(Ti.MillennialMedia.createView({
            type: Ti.MillennialMedia.TYPE_TRANSITION,
            autoRefresh: false
        }));
    });
    win.add(launchFullScreenTransition);
}
else if (demos[activeDemo] == 'bottom') {
    /**
     * Finally, we'll create an ad at the bottom of our app. This ad will demonstrate the different
     * events that are available.
     */
    var bottom = Ti.MillennialMedia.createView({
        bottom: 0, left: 0,
        width: 320, height: 53,
        type: Ti.MillennialMedia.TYPE_BOTTOM
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
    win.add(bottom);
}

/**
 * That's it! Take a look at the docs if you want more information on particular properties.
 */
win.open();