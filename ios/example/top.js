/**
 * Next, an ad at the top of your app.
 */
win.add(MillennialMedia.createView({
    apid: '<INSERT YOUR APID HERE>',
    top: 0,
    // For an iPad size banner the view should be 728 x 90
    // For an iPhone size banner the view should be 320 x 50
    width: IPAD ? 728 : 320,
    height: IPAD ? 90 : 50,
    adType: MillennialMedia.TYPE_BANNER
}));
