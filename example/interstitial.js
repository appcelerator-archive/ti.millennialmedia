/**
 * You can add interstitial ads.
 */
var interstitialDemoButton = Ti.UI.createButton({
    title: 'Show Interstitial Ad',
    bottom: "20%", left: 20, right: 20,
    height: "10%"
});
interstitialDemoButton.addEventListener('click', function () {
    var view = MillennialMedia.createView({
        apid: '<INSERT YOUR APID HERE>',
        // It can be desirable to set the size of the view to 0 for interstitial ads to 
        // ensure that it does not block the UI while it is loading, before it is displayed. 
        top: 0, width: 0, height: 0,
        adType: MillennialMedia.TYPE_INTERSTITIAL
    });
    
    MillennialMedia.addEventListener('adRequestComplete', requestComplete);
    function requestComplete(e) {
        Ti.API.info('interstitial adRequestComplete');
        MillennialMedia.removeEventListener('adRequestComplete', requestComplete);
        if (!e.success) {
            win.remove(view);
        }
    }
    
    MillennialMedia.addEventListener('modalDidDismiss', modalDidDismiss);
    function modalDidDismiss() {
        Ti.API.info('interstitial modalDidDismiss');
        MillennialMedia.removeEventListener('modalDidDismiss', modalDidDismiss);
        win.remove(view);
        if (IOS) {
            Ti.UI.iPhone.setStatusBarHidden(false);
        }
    }
    
    if (IOS) {
        MillennialMedia.addEventListener('modalDidAppear', modalDidAppear);
        function modalDidAppear() {
            Ti.API.info('interstitial modalDidAppear');
            MillennialMedia.removeEventListener('modalDidAppear', modalDidAppear);
            // Some Interstitial ads want to use the full screen, hiding the status bar to allow this.
            Ti.UI.iPhone.setStatusBarHidden(true);
        }
    }
    
    win.add(view);
});
win.add(interstitialDemoButton);
