/**
 * You can also use ads to transition between views.
 */
var transitionDemoButton = Ti.UI.createButton({
    title: 'Launch Full Screen Transition',
    top: 220, left: 20, right: 20,
    height: 35
});
transitionDemoButton.addEventListener('click', function () {
    var transition = MillennialMedia.createView({
        type: MillennialMedia.TYPE_TRANSITION
    });
    transition.addEventListener('fail', function () {
        Ti.API.info('fail fired!');
        win.remove(transition);
    });
    transition.addEventListener('modalWasDismissed', function () {
        Ti.API.info('modalWasDismissed fired!');
        win.remove(transition);
    });
    win.add(transition);
});
win.add(transitionDemoButton);