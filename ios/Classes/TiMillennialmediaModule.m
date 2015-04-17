/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiMillennialmediaModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"

#import "MMSDK.h"

#define FIRE_EVENT_IF_LISTENER(name, obj) \
if ([self _hasListeners:name]) \
{ \
[self fireEvent:name withObject:obj]; \
} \

@implementation TiMillennialmediaModule

#pragma mark -
#pragma mark Internal

static TiMillennialmediaModule *sharedInstance;
+ (TiMillennialmediaModule *)sharedInstance
{
    return sharedInstance;
}

-(id)moduleGUID
{
	return @"72599f01-03dc-4c82-b474-6f18054d94a5";
}
-(NSString*)moduleId
{
	return @"ti.millennialmedia";
}

#pragma mark -
#pragma mark Lifecycle

-(void)startup
{
	[super startup];

    self.modelDelegate = self;
    sharedInstance = self;

    [MMSDK initialize]; // Initialize a Millennial Media session
    [self listenForEvents];
}

-(void)shutdown:(id)sender
{
	[super shutdown:sender];

    [self stopListeningForEvents];
}

#pragma mark -
#pragma mark Cleanup

-(void)dealloc
{
    RELEASE_TO_NIL(geolocation_);
    RELEASE_TO_NIL(demographics_);
    RELEASE_TO_NIL(keywords_);
    RELEASE_TO_NIL(customParameters_);

	[super dealloc];
}

#pragma mark -
#pragma mark Properties and Public API

-(NSString *)getVersion
{
    return [MMSDK version];
}

-(void)trackConversion:(id)args
{
    ENSURE_SINGLE_ARG(args, NSDictionary);
    id goalId = [args objectForKey:@"goalId"];
    ENSURE_STRING(goalId);

    [MMSDK trackConversionWithGoalId:goalId requestData:[self request]];
}

-(void)setLogLevel:(id)args
{
    // In the MM Android SDK, log levels are limits
    // Creating the same behavior here in the name of parity
    ENSURE_SINGLE_ARG(args, NSNumber);
    int newLevel = [TiUtils intValue:args];
    int level = 0; // Default to MMLOG_LEVEL_OFF
    switch (newLevel) {
        case MMLOG_LEVEL_OFF:
            level = MMLOG_LEVEL_OFF;
            break;
        case MMLOG_LEVEL_ERROR:
            level = MMLOG_LEVEL_ERROR;
            break;
        case MMLOG_LEVEL_INFO:
            level = MMLOG_LEVEL_ERROR | MMLOG_LEVEL_INFO;
            break;
        case MMLOG_LEVEL_DEBUG:
            level = MMLOG_LEVEL_ERROR | MMLOG_LEVEL_INFO | MMLOG_LEVEL_DEBUG;
            break;
        case MMLOG_LEVEL_FATAL:
            level = MMLOG_LEVEL_ERROR | MMLOG_LEVEL_INFO | MMLOG_LEVEL_DEBUG | MMLOG_LEVEL_FATAL;
            break;
        default:
            break;
    }
    [MMSDK setLogLevel:level];
}

-(void)trackEvent:(id)args
{
    ENSURE_SINGLE_ARG(args, NSDictionary);
    id eventId = [args objectForKey:@"eventId"];
    ENSURE_STRING(eventId);

    [MMSDK trackEventWithId:eventId];
}

#pragma mark -
#pragma mark Utils

-(MMRequest *)request
{
    // Doing memory profiling on this module will show that requests are not being released.
    // This is a know issue with this library and is reproducible using the MM example app.
    MMRequest *request = [[[MMRequest requestWithLocation:geolocation_] retain] autorelease];

    for (NSString *key in demographics_) {

        id prop = [demographics_ objectForKey:key];

        if ([key isEqualToString:@"education"]) {
            request.education = [TiUtils doubleValue:prop];
        } else if ([key isEqualToString:@"gender"]) {
            request.gender = [TiUtils doubleValue:prop];
        } else if ([key isEqualToString:@"ethnicity"]) {
            request.ethnicity = [TiUtils doubleValue:prop];
        } else if ([key isEqualToString:@"maritalStatus"]) {
            request.maritalStatus = [TiUtils doubleValue:prop];
        } else if ([key isEqualToString:@"age"]) {
            request.age = [TiUtils numberFromObject:prop];
        } else if ([key isEqualToString:@"zipCode"]) {
            request.zipCode = [TiUtils stringValue:prop];
        }
    }

    if (keywords_ != nil) {
        request.keywords = keywords_;
    }

    for (id key in customParameters_) {
        [request setValue:[TiUtils stringValue:[customParameters_ objectForKey:key]] forKey:[TiUtils stringValue:key]];
    }

    return request;
}

-(NSDictionary *)dictFromMMNotification:(NSNotification *)notification
{
    NSString *typeString = [[notification userInfo] objectForKey:MillennialMediaAdTypeKey];
    NSNumber *type;

    // The notification returns the adType as a string.
    // Converting it to match the module constants.
    if ([typeString isEqualToString:@"MillennialMediaAdTypeInterstitial"]) {
        type = [NSNumber numberWithInt:TiMMInterstitial];
    } else {
        type = [NSNumber numberWithInt:TiMMBanner];
    }

    NSDictionary *dict = [NSDictionary dictionaryWithObjectsAndKeys:
                           type, @"adType",
                           [[notification userInfo] objectForKey:MillennialMediaAPIDKey], @"apid",
                           nil];
    return dict;
}

#pragma mark -
#pragma mark Proxy Events

-(void)propertyChanged:(NSString*)key oldValue:(id)oldValue newValue:(id)newValue proxy:(TiProxy*)proxy
{
    if ([oldValue isEqual:newValue]) {
		// Value didn't really change
		return;
	}

    // Validating and saving property values
    if ([key isEqualToString:@"demographics"]) {
        // validate and save location
        ENSURE_TYPE_OR_NIL(newValue, NSDictionary);
        if (demographics_ != nil) {
            RELEASE_TO_NIL(demographics_);
        }
        demographics_ = [newValue retain];
    } else if ([key isEqualToString:@"keywords"]) {
        ENSURE_TYPE_OR_NIL(newValue, NSArray);
        if (keywords_ != nil) {
            RELEASE_TO_NIL(keywords_);
        }
        keywords_ = [newValue retain];
    } else if ([key isEqualToString:@"geolocation"]) {
        ENSURE_TYPE_OR_NIL(newValue, NSDictionary);
        if (newValue != nil) {
            id latitude = [newValue objectForKey:@"latitude"];
            id longitude = [newValue objectForKey:@"longitude"];
            ENSURE_TYPE(latitude, NSNumber);
            ENSURE_TYPE(longitude, NSNumber);
            double lat = [TiUtils doubleValue:latitude];
            double lng = [TiUtils doubleValue:longitude];

            if (geolocation_ != nil) {
                RELEASE_TO_NIL(geolocation_);
            }

            if (CLLocationCoordinate2DIsValid(CLLocationCoordinate2DMake(lat, lng))) {
                geolocation_ = [[CLLocation alloc] initWithLatitude:lat longitude:lng];
            } else {
                NSLog(@"[WARN] geolocation coordinates %f,%f are not invalid", lat, lng);
            }
        } else {
            RELEASE_TO_NIL(geolocation_);
        }
    } else if ([key isEqualToString:@"custom"]) {
        ENSURE_TYPE_OR_NIL(newValue, NSDictionary);
        if (customParameters_ != nil) {
            RELEASE_TO_NIL(customParameters_);
        }
        customParameters_ = [newValue retain];
    }
}

#pragma mark -
#pragma mark Millennial Media Notification Methods

-(void)listenForEvents
{
    // Notification will fire when an ad causes the application to terminate or enter the background
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(applicationWillTerminateFromAd:)
                                                 name:MillennialMediaAdWillTerminateApplication
                                               object:nil];

    // Notification will fire when an ad is tapped.
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(adWasTapped:)
                                                 name:MillennialMediaAdWasTapped
                                               object:nil];

    // Notification will fire when an ad modal will appear.
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(adModalWillAppear:)
                                                 name:MillennialMediaAdModalWillAppear
                                               object:nil];

    // Notification will fire when an ad modal did appear.
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(adModalDidAppear:)
                                                 name:MillennialMediaAdModalDidAppear
                                               object:nil];

    // Notification will fire when an ad modal will dismiss.
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(adModalWillDismiss:)
                                                 name:MillennialMediaAdModalWillDismiss
                                               object:nil];

    // Notification will fire when an ad modal did dismiss.
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(adModalDidDismiss:)
                                                 name:MillennialMediaAdModalDidDismiss
                                               object:nil];
}

-(void)stopListeningForEvents
{
    // Remove notification observers
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:MillennialMediaAdWillTerminateApplication
                                                  object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:MillennialMediaAdWasTapped
                                                  object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:MillennialMediaAdModalWillAppear
                                                  object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:MillennialMediaAdModalDidAppear
                                                  object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:MillennialMediaAdModalWillDismiss
                                                  object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                    name:MillennialMediaAdModalDidDismiss
                                                  object:nil];
}

-(void)applicationWillTerminateFromAd:(NSNotification *)notification
{
    // No User Info is passed for this notification
    FIRE_EVENT_IF_LISTENER(@"applicationWillTerminateFromAd", nil);
}

-(void)adWasTapped:(NSNotification *)notification
{
    FIRE_EVENT_IF_LISTENER(@"adWasTapped", [self dictFromMMNotification:notification]);
}

-(void)adModalWillAppear:(NSNotification *)notification
{
    FIRE_EVENT_IF_LISTENER(@"modalWillAppear", [self dictFromMMNotification:notification]);
}

-(void)adModalDidAppear:(NSNotification *)notification
{
    FIRE_EVENT_IF_LISTENER(@"modalDidAppear", [self dictFromMMNotification:notification]);
}

-(void)adModalWillDismiss:(NSNotification *)notification
{
    FIRE_EVENT_IF_LISTENER(@"modalWillDismiss", [self dictFromMMNotification:notification]);
}

-(void)adModalDidDismiss:(NSNotification *)notification
{
    FIRE_EVENT_IF_LISTENER(@"modalDidDismiss", [self dictFromMMNotification:notification]);
}

#pragma mark -
#pragma mark Constants

MAKE_SYSTEM_PROP(TYPE_BANNER, TiMMBanner);
MAKE_SYSTEM_PROP(TYPE_INTERSTITIAL, TiMMInterstitial);

// Log Level
MAKE_SYSTEM_PROP(LOG_LEVEL_OFF, MMLOG_LEVEL_OFF); // No equivalent on Android
MAKE_SYSTEM_PROP(LOG_LEVEL_ERROR, MMLOG_LEVEL_ERROR);
MAKE_SYSTEM_PROP(LOG_LEVEL_INFO, MMLOG_LEVEL_INFO);
MAKE_SYSTEM_PROP(LOG_LEVEL_DEBUG, MMLOG_LEVEL_DEBUG);
MAKE_SYSTEM_PROP(LOG_LEVEL_VERBOSE, MMLOG_LEVEL_FATAL); // VERBOSE to match Android

// Gender
MAKE_SYSTEM_PROP(GENDER_MALE, MMGenderMale);
MAKE_SYSTEM_PROP(GENDER_FEMALE, MMGenderFemale);
MAKE_SYSTEM_PROP(GENDER_UNKNOWN, MMGenderOther);

// Ethnicity
MAKE_SYSTEM_PROP(ETHNICITY_ASIAN, MMEthnicityAsian);
MAKE_SYSTEM_PROP(ETHNICITY_BLACK, MMEthnicityBlack);
MAKE_SYSTEM_PROP(ETHNICITY_HISPANIC, MMEthnicityHispanic);
MAKE_SYSTEM_PROP(ETHNICITY_INDIAN, MMEthnicityIndian);
MAKE_SYSTEM_PROP(ETHNICITY_MIDDLE_EASTERN, MMEthnicityMiddleEastern);
MAKE_SYSTEM_PROP(ETHNICITY_NATIVE_AMERICAN, MMEthnicityNativeAmerican);
MAKE_SYSTEM_PROP(ETHNICITY_PACIFIC_ISLANDER, MMEthnicityPacificIslander);
MAKE_SYSTEM_PROP(ETHNICITY_WHITE, MMEthnicityWhite);
MAKE_SYSTEM_PROP(ETHNICITY_OTHER, MMEthnicityOther);

// Education
MAKE_SYSTEM_PROP(EDUCATION_HIGH_SCHOOL, MMEducationHighSchool);
MAKE_SYSTEM_PROP(EDUCATION_IN_COLLEGE, MMEducationInCollege);
MAKE_SYSTEM_PROP(EDUCATION_SOME_COLLEGE, MMEducationSomeCollege);
MAKE_SYSTEM_PROP(EDUCATION_ASSOCIATES, MMEducationAssociates);
MAKE_SYSTEM_PROP(EDUCATION_BACHELORS, MMEducationBachelors);
MAKE_SYSTEM_PROP(EDUCATION_MASTERS, MMEducationMasters);
MAKE_SYSTEM_PROP(EDUCATION_DOCTORATE, MMEducationDoctorate);
MAKE_SYSTEM_PROP(EDUCATION_OTHER, MMEducationOther);

// Marital Status
MAKE_SYSTEM_PROP(MARITAL_SINGLE, MMMaritalSingle);
MAKE_SYSTEM_PROP(MARITAL_RELATIONSHIP, MMMaritalRelationship);
MAKE_SYSTEM_PROP(MARITAL_MARRIED, MMMaritalMarried);
MAKE_SYSTEM_PROP(MARITAL_DIVORCED, MMMaritalDivorced);
MAKE_SYSTEM_PROP(MARITAL_ENGAGED, MMMaritalEngaged);
MAKE_SYSTEM_PROP(MARITAL_OTHER, MMMaritalOther);

@end
