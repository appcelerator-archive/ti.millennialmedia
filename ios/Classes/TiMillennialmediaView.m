/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiMillennialmediaView.h"
#import "TiUtils.h"
#import "TiMillennialmediaModule.h"

#import "MMInterstitial.h"

@implementation TiMillennialmediaView

#pragma mark -
#pragma mark Lifecycle and View Management

- (id)init
{
    self = [super init];

    if (self) {
        adView_ = nil;
    }

    return self;
}

-(void)dealloc
{
    if (adView_ != nil) {
        [adView_ removeFromSuperview];
        RELEASE_TO_NIL(adView_);
    }
    [super dealloc];
}

-(void)createView
{
    if ([self adType] == TiMMInterstitial) {
        BOOL autoLoad = [TiUtils boolValue:[self.proxy valueForKey:@"autoLoad"] def:YES];
        MMRequest *request = [[TiMillennialmediaModule sharedInstance] request];

        [MMInterstitial fetchWithRequest:request
                                    apid:[TiUtils stringValue:[self.proxy valueForKey:@"apid"]]
                            onCompletion:^(BOOL success, NSError *error) {
                                [self fireRequestCompleteEvent:success error:error];

                                if (success && autoLoad) {
                                        [self displayAd];
                                }
                            }];
    }
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    int type = [self adType];

    if (adView_ == nil && type == TiMMBanner) {
        // Create a 320 x 50 frame to display iPhone Banner ads, a 728 x 90 frame to display iPad Banner ads,
        // or a 300 x 250 frame to display Rectangle ads
        adView_ = [[MMAdView alloc] initWithFrame:bounds apid:[TiUtils stringValue:[self.proxy valueForKey:@"apid"]]
                              rootViewController:[[TiApp app] controller]];
        adView_.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
        [self addSubview:adView_];

        BOOL autoLoad = [TiUtils boolValue:[self.proxy valueForKey:@"autoLoad"] def:YES];
        if (autoLoad) {
            [self getAd];
        }
    }
}

#pragma mark -
#pragma mark Utils

-(void)displayAd
{
    [MMInterstitial displayForApid:[TiUtils stringValue:[self.proxy valueForKey:@"apid"]]
                fromViewController:[[TiApp app] controller]
                   withOrientation:0
                      onCompletion:^(BOOL success, NSError *error) {
                          if (!success) {
                              NSLog(@"[ERROR] `displayAd` failed with error: %@", error);
                          }
                      }];
}

-(void)getAd
{
    if (adView_ != nil) {
        MMRequest *request = [[TiMillennialmediaModule sharedInstance] request];

        [adView_ getAdWithRequest:request
                    onCompletion:^(BOOL success, NSError *error) {
                        [self fireRequestCompleteEvent:success error:error];
                    }];
    }
}

-(void)fireRequestCompleteEvent:(BOOL)success error:(NSError *)error
{
    if ([[TiMillennialmediaModule sharedInstance] _hasListeners:@"adRequestComplete"]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMBOOL(success), @"success",
                               self.proxy, @"adView",
                               (error == nil) ? [NSNull null] : [error localizedDescription], @"error",
                               [self apid], @"apid",
                               [NSNumber numberWithInt:[self adType]], @"adType",
                               nil];
        [[TiMillennialmediaModule sharedInstance] fireEvent:@"adRequestComplete" withObject:event];
    }
}

// Internal getters
-(NSString *)apid
{
    return [TiUtils stringValue:[self.proxy valueForKey:@"apid"]];
}

-(int)adType
{
    return [TiUtils intValue:[self.proxy valueForKey:@"adType"] def:TiMMBanner];
}

#pragma mark -
#pragma mark Public API

-(void)display:(id)args
{
    if ([self adType] != TiMMInterstitial) {
        NSLog(@"[WARN] The `display` method is only for Interstitial ads.");
        return;
    }

    [self displayAd];
}

-(void)refresh:(id)args
{
    int type = [self adType];
    if (type != TiMMBanner) {
        NSLog(@"[WARN] The `refresh` method is only for Banner ads.");
        return;
    }

    [self getAd];
}

@end
