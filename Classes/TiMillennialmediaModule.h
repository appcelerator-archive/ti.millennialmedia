/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiModule.h"
#import "MMAdView.h"

@interface TiMillennialmediaModule : TiModule 
{
}

+(NSString*)retrieveAPID;
+(NSDictionary*)retrieveDemographics;

-(void)trackGoal:(NSString*)goal;

@property (readonly, nonatomic) NSNumber* TYPE_TOP;
@property (readonly, nonatomic) NSNumber* TYPE_BOTTOM;
@property (readonly, nonatomic) NSNumber* TYPE_RECTANGLE;
@property (readonly, nonatomic) NSNumber* TYPE_LAUNCH;
@property (readonly, nonatomic) NSNumber* TYPE_TRANSITION;

@end
