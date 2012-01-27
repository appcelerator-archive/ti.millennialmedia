/**
 * We'll create an ad at the bottom of our app. This ad will demonstrate the different
 * events that are available.
 */
var bottom = MillennialMedia.createView({
    apid:'54197',
    bottom:0, left:0, right:0,
    height:53,
    type:MillennialMedia.TYPE_BOTTOM
});

function curryEventHandler(type) {
    return function () {
        Ti.API.info(type + ' fired!');
    }
}

var events = ['success', 'fail', 'refresh', 'tapped', 'isCaching', 'willTerminate',
    'modalWillAppear', 'modalDidAppear', 'modalWasDismissed'];
for (var k in events) {
    bottom.addEventListener(events[k], curryEventHandler(events[k]));
}
win.add(bottom);