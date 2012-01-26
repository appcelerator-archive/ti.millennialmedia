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

-(void)createView {
    bool autoLoad = [TiUtils boolValue:[self.proxy valueForKey:@"autoLoad"] def:YES];
    int type = [TiUtils intValue:[self.proxy valueForKey:@"type"] def:MMBannerAdTop];
    if (type == MMFullScreenAdLaunch || type == MMFullScreenAdTransition) {
        adView = [[MMAdView interstitialWithType:type
                                            apid:[TiUtils stringValue:[self.proxy valueForKey:@"apid"]]
                                       delegate:self
                                         loadAd:autoLoad] retain];
        adView.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
        [self addSubview:adView];
    }
}
-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds {
    int type = [TiUtils intValue:[self.proxy valueForKey:@"type"] def:MMBannerAdTop];
    if (type == MMBannerAdTop || type == MMBannerAdBottom || type == MMBannerAdRectangle) {
        if (adView != nil) {
            [adView removeFromSuperview];
            RELEASE_TO_NIL(adView);
        }
        bool autoLoad = [TiUtils boolValue:[self.proxy valueForKey:@"autoLoad"] def:YES];
        bool autoRefresh = [TiUtils boolValue:[self.proxy valueForKey:@"autoRefresh"] def:YES];
        adView = [[MMAdView adWithFrame:bounds
                                  type:type
                                  apid:[TiUtils stringValue:[self.proxy valueForKey:@"apid"]]
                              delegate:self
                                loadAd:autoLoad
                             startTimer:autoRefresh] retain];
        adView.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
        [self addSubview:adView];
    }
}
-(void)dealloc {
    if (adView != nil) {
        [adView removeFromSuperview];
        RELEASE_TO_NIL(adView);
    }
    [super dealloc];
}


#pragma mark -
#pragma mark Additional Settings
- (NSDictionary *)requestData {
    return [TiMillennialmediaModule retrieveDemographics];
}
- (NSInteger)adRefreshDuration {
    return [TiUtils intValue:[self.proxy valueForKey:@"refreshDuration"] def:60];
}
- (BOOL)accelerometerEnabled {
    return [TiUtils boolValue:[self.proxy valueForKey:@"accelerometerEnabled"] def:YES];
}

#pragma mark-
#pragma mark Public API
-(void)refresh:(id)args {
    [adView refreshAd];
}

#pragma mark -
#pragma mark Events
- (void)adRequestSucceeded:(MMAdView *) adView {
    [self.proxy fireEvent:@"success" withObject:nil];
}

- (void)adRequestFailed:(MMAdView *) adView {
    [self.proxy fireEvent:@"fail" withObject:nil];
}

- (void)adDidRefresh:(MMAdView *) adView {
    [self.proxy fireEvent:@"refresh" withObject:nil];
}

- (void)adWasTapped:(MMAdView *) adView {
    [self.proxy fireEvent:@"tapped" withObject:nil];
}

- (void)adRequestIsCaching:(MMAdView *) adView {
    [self.proxy fireEvent:@"isCaching" withObject:nil];
}

- (void)adRequestFinishedCaching:(MMAdView *) adView successful: (BOOL) didSucceed {
    [self.proxy fireEvent:@"cached" withObject:nil];
}

- (void)applicationWillTerminateFromAd {
    [self.proxy fireEvent:@"willTerminate" withObject:nil];
}

- (void)adModalWillAppear {
    [self.proxy fireEvent:@"modalWillAppear" withObject:nil];
}

- (void)adModalDidAppear {
    [self.proxy fireEvent:@"modalDidAppear" withObject:nil];
}

- (void)adModalWasDismissed {
    [self.proxy fireEvent:@"modalWasDismissed" withObject:nil];
}

@end
