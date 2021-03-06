/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiUIView.h"
#import "MMAdView.h"
#import "TiMillennialmediaModule.h"

@interface TiMillennialmediaView : TiUIView {
    MMAdView *adView_;
}

-(void)createView;
-(int)adType;

@end
