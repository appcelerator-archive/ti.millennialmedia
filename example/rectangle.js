/**
 * Now, let's place an ad arbitrarily in the middle of our app.
 */
win.add(MillennialMedia.createView({
    apid: '<INSERT YOUR APID HERE>',
    top: 100,
    // For a rectangle size ad the view should be 300 x 250
    width: 300,
    height: 250,
    adType: MillennialMedia.TYPE_BANNER
}));