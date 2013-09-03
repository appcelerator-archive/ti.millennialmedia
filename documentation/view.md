# Ti.MillennialMedia.View

## Description

Displays an ad to the user.

__WARNING:__ You must set the "apid" property of Ti.MillennialMedia.View before you can display any ads to the user!

## View Size

For banner ads, the size of the view controls what size of ad is returned. Below are the available banner sizes.

#### iOS:
* Banner iPhone: 320 x 50
* Banner iPad: 728 x 90
* Rectangle: 300 x 250

#### Android:
* Banner Phone: 320 x 50
* Banner Medium: 480 x 60
* Banner Tablet: 728 x 90
* Rectangle: 300 x 250

The view should be set to one of these dimentions durring creation.

__NOTE:__ View size has no affect on interstitial ads.

## Properties

### apid [string] (required)
Your APID from Millennial Media for this ad. This is shown on the on [http://developer.millennialmedia.com](http://developer.millennialmedia.com).

### adType [int] (defaults to TYPE_BANNER)
Controls the type of ad that will be displayed to the user. Available ad types are `TYPE_BANNER` and `TYPE_INTERSTITIAL`. See available constants at [Ti.MillennialMedia][].

WARNING: when using interstitial type ads, you do not need to size them; they'll take over the full view of your app while they are displayed. It can be desirable to set the size of the view to 0 x 0 for interstitial ads, to ensure that the view does not block the UI while the ad is loading, before it is displayed.  It is very important that you listen for "adRequestComplete" (with a failure) and "modalDidDismiss" to remove the ad from the window after the user is done with it. See the example app.

Also note that "interstitial" ads may not display immediately. If the server responds with a cached ad that the client does not
have yet.

### autoLoad [boolean] (defaults to true)
Whether or not the ad should load and display as soon as it is added to the view hierarchy. Note that if you set this to false, no ad will be displayed until you call the `refresh` method for __banner__ ads or `display` for __interstitial__ ads. Interstitial ads will still make a request for an ad when the view is added to a parent, but will not auto display it if autoLoad is false.

### ignoreDensityScaling [boolean] (defaults to true)
Controls whether density scaling is used for the ad view.

__Android only.__

### Additional Properties
A _Ti.MillennialMedia.View_ inherits from [Ti.UI.View][], so any properties that you can set on a view can also be utilized here.

## Methods

### void display()
Will display an Interstitial ad if one is available. Check if an ad is available using `isAdAvailable`.

For __interstitial__ ads only.

### void isAdAvailable()

Check if an ad has been downloaded for this view.

For __interstitial__ ads only.

### void refresh()
Shows a new ad to the user.

For __banner__ ads only.

[Ti.MillennialMedia]: index.html
[Ti.UI.View]: http://developer.appcelerator.com/apidoc/mobile/latest/Titanium.UI.View-object