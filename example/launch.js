/**
 * You can show a full screen ad when your app launches. After it is done, the user will be able to
 * see the rest of your app.
 */
var launch = MillennialMedia.createView({
    type: MillennialMedia.TYPE_LAUNCH
});
launch.addEventListener('fail', function () {
    Ti.API.info('fail fired!');
    win.remove(launch);
});
launch.addEventListener('modalWasDismissed', function () {
    Ti.API.info('modalWasDismissed fired!');
    win.remove(launch);
});
win.add(launch);