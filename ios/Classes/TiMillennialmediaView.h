/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiUIView.h"
#import "TiMillennialmediaModule.h"
#import <MillennialMedia/MMAdView.h>

@interface TiMillennialmediaView : TiUIView {
    MMAdView *adView_;
}

-(void)createView;
-(int)adType;

@end
