/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiModule.h"
#import "TiApp.h"
#import "MMAdView.h"

@interface TiMillennialmediaModule : TiModule <MMAdDelegate>
{
}

+(NSDictionary*)retrieveDemographics;

-(void)trackGoal:(NSString*)goal;

@property (readonly, nonatomic) NSNumber* TYPE_TOP;
@property (readonly, nonatomic) NSNumber* TYPE_BOTTOM;
@property (readonly, nonatomic) NSNumber* TYPE_RECTANGLE;
@property (readonly, nonatomic) NSNumber* TYPE_LAUNCH;
@property (readonly, nonatomic) NSNumber* TYPE_TRANSITION;

@end
