# Ti.MillennialMedia Module

## WARNING

Ads won't show up in the Android Emulator 2.3, and using the module may crash the app. This is due to a bug in the
Android emulator that has been fixed in later versions, and is not present in earlier versions. Real devices running
Android 2.3 will not have this problem.

## Description

Mobile Advertising with Millennial Media. Partner with the largest and highest quality mobile advertising network to
build brand awareness, target mobile users, and acquire new customers.

Note: you will need to have an account with Millennial Media. You can sign up for one at the following URL:
	https://developer.millennialmedia.com

WARNING: The first thing you should do is set the "apid" property of this module! You won't be able to display any ads
until after this property is set.

## Accessing the Ti.MillennialMedia Module

To access this module from JavaScript, you would do the following:

	var MillennialMedia = require('ti.millennialmedia');

## Properties

### apid [string]
Your APID from Millennial Media. This is shown on the "My Apps" > "Manage Apps" page on developer.millennialmedia.com.

### demographics [object]
Demographic information about the current user, such as their age gender and location. Any ads you display will use
this information. This is a dictionary with any of the following properties:

* age [string]: in years (e.g. 18) or an age
* gender [string]: "male", "female", or "unknown"
* zip [string]: 5 digit US zip code
* marital [string]: Marital status, options are: "single", "divorced", "engaged", "relationship", or "swinger"
* income [string]: e.g. 65000
* lat [string]: latitude
* long [string]: longitude
* ethnicity [string]: Options are: "hispanic", "africanamerican", "asian", "indian", "middleeastern", "nativeamerican", "pacificislander", "white", or "other"
* orientation [string]: Sexual orientation, options are: "straight", "gay", "bisexual", or "notsure"
* education [string]: Highest level of education, options are: "highschool", "somecollege", "associate", "bachelor", "masters", "phd", or "professional"
* politics [string]: Political orientation.
* keywords [string]: Any additional relevant description of the user or their preferences separated by commas. e.g. "soccer,scores,basketball"
* children [string]: Any boolean value e.g. "yes", "no", "true", "false".
* width [string]: The width of the ad if you have a limit on the size of ads you can display, e.g. 320. This should only be used if you have a specific limitation on the size of ads you can display. If not used, the ad returned will be optimized based on device specifications.
* height [string]: The height of the ad if you have a limit on the size of ads you can display, e.g. 53. This should only be used if you have a specific limitation on the size of ads you can display. If not used, the ad returned will be optimized based on device specifications.

## Methods

### trackGoal(string)
Tracks a goal for your ad campaign. Takes a single string argument.

### createView({...})
Creates a [Ti.MillennialMedia.View][], which will display an ad for you. Takes a dictionary of the properties available
on the view as an argument.

## Constants

### TYPE_TOP
Used for ads that are displayed at the top of your app. Use this constant on the "type"
property of [Ti.MillennialMedia.View][].

### TYPE_BOTTOM
Used for ads that are displayed at the bottom of your app. Use this constant on the "type"
property of [Ti.MillennialMedia.View][].

### TYPE_RECTANGLE
Used for ads that are displayed throughout your app. Use this constant on the "type"
property of [Ti.MillennialMedia.View][].

### TYPE_LAUNCH
Used for fullscreen ads displayed when your app launches. Use this constant on the "type"
property of [Ti.MillennialMedia.View][].

### TYPE_TRANSITION
Used for fullscreen ads that are meant to be stitched between transitions in your app
(IE moving from one screen to the next). Use this constant on the "type" property
of [Ti.MillennialMedia.View][].

## Usage
See example.

## Author
Dawson Toth

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support
Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=iOS%20MillennialMedia%20Module).

## License
Copyright(c) 2010-2011 by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Ti.MillennialMedia.View]: view.html