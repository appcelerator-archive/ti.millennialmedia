# Ti.MillennialMedia Module

## Description

Mobile Advertising with Millennial Media. Partner with the largest and highest quality mobile advertising network to
build brand awareness, target mobile users, and acquire new customers.

Note: you will need to have an account with Millennial Media. You can sign up for one at the following URL: [https://developer.millennialmedia.com](https://developer.millennialmedia.com)

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document for instructions on getting
started with using this module in your application.
	
## Accessing the Module

Use `require` to access this module from JavaScript:

	var MillennialMedia = require('ti.millennialmedia');

The `MillennialMedia` variable is a reference to the Module object.

## Properties

### version [string] (read-only)
The version of the MillennialMedia library.

### demographics [object]
Demographic information about the current user, such as their age gender and location. Any ads you display will use this information. This is a dictionary with any of the following properties:

* education [int]: Set using module constants `EDUCATION_HIGH_SCHOOL`, `EDUCATION_SOME_COLLEGE`, `EDUCATION_BACHELORS`, `EDUCATION_MASTERS`, `EDUCATION_DOCTORATE`, or `EDUCATION_UNKNOWN`.
* gender [int]: Set using module constants `GENDER_MALE`, `GENDER_FEMALE`, or `GENDER_UNKNOWN`.
* ethnicity [int]: Set using module constants `ETHNICITY_ASIAN`, `ETHNICITY_BLACK`, `ETHNICITY_HISPANIC`, `ETHNICITY_INDIAN`, `ETHNICITY_MIDDLE_EASTERN`, `ETHNICITY_NATIVE_AMERICAN`, `ETHNICITY_PACIFIC_ISLANDER`, `ETHNICITY_WHITE`, or `ETHNICITY_OTHER`.
* maritalStatus [int]: Set using module constants `MARITAL_SINGLE`, `MARITAL_MARRIED`, `MARITAL_DIVORCED`, or `MARITAL_ENGAGED`.
* orientation [int]: Set using module constants `SEXUAL_ORIENTATION_STRAIGHT`, `SEXUAL_ORIENTATION_GAY`, `SEXUAL_ORIENTATION_BISEXUAL`, or `SEXUAL_ORIENTATION_UNKNOWN`.
* age [string]: in years (e.g. 18) or an age
* zipCode [string]: 5 digit US zip code

#### Example

	MillennialMedia.demographics = {
	    age: '23',
	    gender: MillennialMedia.GENDER_MALE,
	    education: MillennialMedia.EDUCATION_SOME_COLLEGE,
	    ethnicity: MillennialMedia.ETHNICITY_HISPANIC,
	    maritalStatus: MillennialMedia.MARITAL_DIVORCED,
	    orientation: MillennialMedia.SEXUAL_ORIENTATION_STRAIGHT,
	    zipCode: '60187'
	};

### keywords [string[]]
Set an array of keywords to include any additional information about the user or their preferences. Any ads you display will use this information.

#### Example

	MillennialMedia.keywords = ['KEYWORD1', 'KEYWORD2'];

### geolocation [object]
Set the user's last known location. Any ads you display will use this information. This dictionary should have the following properties.

*  latitude [number]: a valid latitude
*  longitude [number]: a valid longitude

#### Example

	MillennialMedia.geolocation = {
	    latitude: 41.866, 
	    longitude: -88.107
	};


### custom [object]
Sets additional custom parameters for the ad request. Any ads you display will use this information. Values must be strings or numbers. For example, you might use a number value for the key "children" or "income". You could use string values such as "conservative" or "liberal" for the key "politics".

#### Example

	MillennialMedia.customParameters = {
	    something: "CUSTOM1",
	    somethingelse: "CUSTOM2"
	};

## Methods

### setLogLevel(int)
Used to set the log level of the MillennialMedia library. See constants for available log levels.

#### Example

	MillennialMedia.setLogLevel(MillennialMedia.LOG_LEVEL_VERBOSE);

### [Ti.MillennialMedia.View][] createView({...})
Creates a [Ti.MillennialMedia.View][], which will display an ad for you. Takes a dictionary of the properties available on the view as an argument.

#### Example

	var rectangle = MillennialMedia.createView({
	    apid: '54195',
	    top: 100,
	    // For a rectangle size ad the view should be 300 x 250
	    width: 300,
	    height: 250,
	    adType: MillennialMedia.TYPE_BANNER
	});

### void trackConversion({...})
Reports a goal conversion to the Millennial Conversion tracking server.

* goalId [string]: The Millennial Media Goal ID for tracking conversions for your application.

#### Example

	MillennialMedia.trackConversion({
		goalId: 'SOME GOAL ID'
	});

### void trackEvent({...})
Reports an event conversion.

* eventId [string]: The Millennial Media event ID for tracking events in your application.

#### Example

	MillennialMedia.trackEvent({
		eventId: 'SOME EVENT ID'
	});

## Events

### adRequestComplete
Occurs whenever a request for an ad completes. It could be a success or a failure. A failure could occur due to the apid being incorrectly set, invalid,
network connectivity failing, or some other unforeseen reason (such as the cubs losing). 

The event object has the following properties:

* success [boolean]: Will be true if the request was successful or false if it was not.
* error [string]: Description of the error if one occurred. Success will be false if there is an error.
* adView [[Ti.MillennialMedia.View][]]: The ad view that fired the event
* apid [string]: The apid of the view that made the request.
* adType [int]: The adType of the view that made the request. See the `adType` property of [Ti.MillennialMedia.View][].

### applicationWillTerminateFromAd
Occurs when launching an external application, causing the current application to either terminate or enter the background. This event will be fired when the application will exit to launch another application such as the App Store, iTunes, or Safari.

__iOS only.__

No event object is passed with this event.

### adWasTapped
Notification posted when an ad is tapped.

The event object has the following properties:

* apid [string]: The apid of the view that made the request.
* adType [int]: The adType of the view that made the request. See the `adType` property of [Ti.MillennialMedia.View][].

### modalWillAppear
Event fired when a modal view is about to be made visible. 

__iOS only.__

The event object has the following properties:

* apid [string]: The apid of the view that made the request.
* adType [int]: The adType of the view that made the request. See the `adType` property of [Ti.MillennialMedia.View][].

### modalDidAppear
Event fired when a modal view has been fully transitioned onto the screen.

The event object has the following properties:

* apid [string]: The apid of the view that made the request.
* adType [int]: The adType of the view that made the request. See the `adType` property of [Ti.MillennialMedia.View][].

### modalWillDismiss
Event fired when a modal view is dismissed, covered, or otherwise hidden.

__iOS only.__

The event object has the following properties:

* apid [string]: The apid of the view that made the request.
* adType [int]: The adType of the view that made the request. See the `adType` property of [Ti.MillennialMedia.View][].

### modalDidDismiss
Event fired after a modal view was dismissed, covered, or otherwise hidden.

The event object has the following properties:

* apid [string]: The apid of the view that made the request.
* adType [int]: The adType of the view that made the request. See the `adType` property of [Ti.MillennialMedia.View][].


## Constants

### TYPE_BANNER
Used to set `adType` when creating a [Ti.MillennialMedia.View][]. Use to create both Banner and Rectangle ads.

### TYPE_INTERSTITIAL
Used to set `adType` when creating a [Ti.MillennialMedia.View][]. 

### LOG_LEVEL_INFO
Used to set the `logLevel` of the MillennialMedia library.

### LOG_LEVEL_DEBUG
Used to set the `logLevel` of the MillennialMedia library.

### LOG_LEVEL_ERROR
Used to set the `logLevel` of the MillennialMedia library.

### LOG_LEVEL_VERBOSE
Used to set the `logLevel` of the MillennialMedia library.

### GENDER_MALE
Used to set `gender` in the `demographics` object.

### GENDER_FEMALE
Used to set `gender` in the `demographics` object.

### GENDER_OTHER
Used to set `gender` in the `demographics` object.

### ETHNICITY_ASIAN
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_BLACK
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_HISPANIC
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_INDIAN
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_MIDDLE_EASTERN
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_NATIVE_AMERICAN
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_PACIFIC_ISLANDER
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_WHITE
Used to set `ethnicity` in the `demographics` object.

### ETHNICITY_OTHER
Used to set `ethnicity` in the `demographics` object.

### EDUCATION_HIGH_SCHOOL
Used to set `education` in the `demographics` object.

### EDUCATION_IN_COLLEGE
Used to set `education` in the `demographics` object.

### EDUCATION_SOME_COLLEGE
Used to set `education` in the `demographics` object.

### EDUCATION_ASSOCIATES
Used to set `education` in the `demographics` object.

### EDUCATION_BACHELORS
Used to set `education` in the `demographics` object.

### EDUCATION_MASTERS
Used to set `education` in the `demographics` object.

### EDUCATION_DOCTORATE
Used to set `education` in the `demographics` object.

### EDUCATION_OTHER
Used to set `education` in the `demographics` object.

### MARITAL_SINGLE
Used to set `maritalStatus` in the `demographics` object.

### MARITAL_RELATIONSHIP
Used to set `maritalStatus` in the `demographics` object.

### MARITAL_MARRIED
Used to set `maritalStatus` in the `demographics` object.

### MARITAL_DIVORCED
Used to set `maritalStatus` in the `demographics` object.

### MARITAL_ENGAGED
Used to set `maritalStatus` in the `demographics` object.

### MARITAL_OTHER
Used to set `maritalStatus` in the `demographics` object.

### SEXUAL_ORIENTATION_STRAIGHT
Used to set `orientation` in the `demographics` object.

### SEXUAL_ORIENTATION_GAY
Used to set `orientation` in the `demographics` object.

### SEXUAL_ORIENTATION_BISEXUAL
Used to set `orientation` in the `demographics` object.

### SEXUAL_ORIENTATION_OTHER
Used to set `orientation` in the `demographics` object.


## Usage
See example.

## Author
Dawson Toth and Jon Alter

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support
Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=Android%20MillennialMedia%20Module).

## License
Copyright(c) 2010-2013 by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Ti.MillennialMedia.View]: view.html