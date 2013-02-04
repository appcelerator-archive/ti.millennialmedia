/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiMillennialmediaModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"

@implementation TiMillennialmediaModule

#pragma mark -
#pragma mark Internal

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
}

-(void)shutdown:(id)sender
{
	[super shutdown:sender];
}

#pragma mark -
#pragma mark Cleanup 

-(void)dealloc
{
	[super dealloc];
}

#pragma mark -
#pragma mark Internal Memory Management

-(void)didReceiveMemoryWarning:(NSNotification*)notification
{
	[super didReceiveMemoryWarning:notification];
}

#pragma mark -
#pragma mark Properties and Public API

static NSDictionary* demographics;
+(NSDictionary*)retrieveDemographics
{
    return demographics;
}
-(void)setDemographics:(id)arg
{
    ENSURE_SINGLE_ARG(arg, NSDictionary);
    if (demographics) {
        [demographics release];
    }
    demographics = [arg retain];
}

-(void)trackGoal:(NSString*)goal {
    [MMAdView startSynchronousConversionTrackerWithGoalId:goal];
}

#pragma mark -
#pragma mark Constants

MAKE_SYSTEM_PROP(TYPE_TOP, MMBannerAdTop);
MAKE_SYSTEM_PROP(TYPE_BOTTOM, MMBannerAdBottom);
MAKE_SYSTEM_PROP(TYPE_RECTANGLE, MMBannerAdRectangle);
MAKE_SYSTEM_PROP(TYPE_LAUNCH, MMFullScreenAdLaunch);
MAKE_SYSTEM_PROP(TYPE_TRANSITION, MMFullScreenAdTransition);

@end
