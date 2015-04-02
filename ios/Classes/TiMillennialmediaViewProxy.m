/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiMillennialmediaViewProxy.h"
#import "TiUtils.h"

#import <MillennialMedia/MMInterstitial.h>

@implementation TiMillennialmediaViewProxy

-(void)viewDidAttach
{
    [(TiMillennialmediaView*)[self view] createView];
}

#ifndef USE_VIEW_FOR_UI_METHOD
	#define USE_VIEW_FOR_UI_METHOD(methodname)\
	-(void)methodname:(id)args\
	{\
		[self makeViewPerformSelector:@selector(methodname:) withObject:args createIfNeeded:YES waitUntilDone:NO];\
	}
#endif

USE_VIEW_FOR_UI_METHOD(display);
USE_VIEW_FOR_UI_METHOD(refresh);

#pragma mark -
#pragma mark Public API

-(id)isAdAvailable:(id)args
{
    if ([(TiMillennialmediaView*)self.view adType] != TiMMInterstitial) {
        NSLog(@"[WARN] The `isAdAvailable` method is only for Interstitial ads.");
        return NUMBOOL(NO);
    }
    
    return NUMBOOL([MMInterstitial isAdAvailableForApid:[TiUtils stringValue:[self valueForKey:@"apid"]]]);
}

@end
