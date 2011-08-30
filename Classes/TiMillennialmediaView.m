/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiMillennialmediaView.h"
#import "TiUtils.h"

@implementation TiMillennialmediaView

#pragma mark -
#pragma mark Lifecycle and View Management

-(void)createView
{
    adView = [MMAdView adWithFrame:CGRectMake(0,0,320,53)
                                        type:MMBannerAdTop
                                        apid:@"54128"
                                    delegate:self
                                      loadAd:YES
                                  startTimer:YES];
    [self addSubview:adView];
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    //[TiUtils setView:adView positionRect:bounds];
}

-(void)dealloc
{
    if (adView != nil) {
        [adView disableAdRefresh];
        [adView removeFromSuperview];
        RELEASE_TO_NIL(adView);
    }
    [super dealloc];
}

#pragma mark -
#pragma mark MMAdView Delegate

- (NSDictionary *)requestData {
    return [NSDictionary dictionaryWithObjectsAndKeys:
            @"21224", @"zip",
            @"35", @"age",
            @"male", @"gender",
            nil];
}

/**
 * Set the timer duration for the rotation of ads in seconds. Default: 60
 */
- (NSInteger)adRefreshDuration {
    return 30;
}

/**
 * Use this method to disable the accelerometer. Default: YES
 */	
- (BOOL)accelerometerEnabled {
    return YES;
}

/**
 * If the following methods are implemented, the delegate will be notified when
 * an ad request succeeds or fails. An ad request is considered to have failed
 * in any situation where no ad is recived.
 */
- (void)adRequestSucceeded:(MMAdView *) adView {
    NSLog(@"adRequestSucceeded");
}

- (void)adRequestFailed:(MMAdView *) adView {
    NSLog(@"adRequestFailed");
}

- (void)adDidRefresh:(MMAdView *) adView {
    NSLog(@"adDidRefresh");
}

- (void)adWasTapped:(MMAdView *) adView {
    NSLog(@"adWasTapped");
}

- (void)adRequestIsCaching:(MMAdView *) adView {
    NSLog(@"adRequestIsCaching");
}

- (void)applicationWillTerminateFromAd {
    NSLog(@"applicationWillTerminateFromAd");
}

- (void)adModalWillAppear {
    NSLog(@"adModalWillAppear");
}

- (void)adModalDidAppear {
    NSLog(@"adModalDidAppear");
}

- (void)adModalWasDismissed {
    NSLog(@"adModalWasDismissed");
}

@end
