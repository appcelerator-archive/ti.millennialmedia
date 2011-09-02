/**
 * Now, let's place an ad arbitrarily in the middle of our app.
 */
win.add(Ti.MillennialMedia.createView({
    top: 54, left: 0, right: 0,
    height: 150,
    type: Ti.MillennialMedia.TYPE_RECTANGLE,
    refreshDuration: 60
}));