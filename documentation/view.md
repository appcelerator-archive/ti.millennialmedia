# Ti.MillennialMedia.View

## Description

Displays an ad to the user.

WARNING: You must set the "apid" property of Ti.MillennialMedia before you can display any ads to the user!

## Properties

### type [int]
Controls the type of ad that will be displayed to the user. Look at the constants available on [Ti.MillennialMedia][] to
see what you can set here.

### autoRefresh [bool, defaults to true]
Whether or not the ad should auto refresh. Use the "refreshDuration" property to control how often this happens.

### refreshDuration [int, defaults to 60]
The number of seconds to wait before auto refreshing the ad. If "autoRefresh" is false, this property will not do
anything.

### autoLoad [bool, defaults to true]
Whether or not the ad should load as soon as it is added to the view hierarchy. Note that if you set this to false, no
ad will be displayed until you call the "refresh" method.

### accelerometerEnabled [bool, defaults to true]
Whether or not ads can utilize the accelerometer.

### Additional Properties
A _Ti.MillennialMedia.View_ inherits from [Ti.UI.View][], so any properties that you can set on a view can also be
utilized here.

## Methods

### refresh()
Shows a new ad to the user.

### trackGoal(string)
Tracks a goal for your ad campaign. Takes a single string argument.

## Events

### success
Occurs whenever an ad is displayed to the user.

### fail
Occurs whenever an ad cannot be served to the user, whether this is from your apid being incorrectly set, invalid,
network connectivity failing, or some other unforeseen reason (such as the cubs losing).

### refresh
Occurs when the ad is refreshed.

### tapped
Occurs when the user taps on an ad.

### isCaching
Occurs when an ad is being cached.

### willTerminate
Occurs when your app is going to terminate, or background, because an external app (such as the store or the browser)
due to the user interacting with an ad.

### modalWillAppear
Occurs just before a modal ad is shown to the user.

### modalDidAppear
Occurs after a modal ad is shown to the user.

### modalWasDismissed
Occurs after the user dismisses a modal ad.

[Ti.MillennialMedia]: index.html
[Ti.UI.View]: http://developer.appcelerator.com/apidoc/mobile/latest/Titanium.UI.View-object