/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiUIView.h"
#import "MMAdView.h"
#import "TiMillennialmediaModule.h"

@interface TiMillennialmediaView : TiUIView<MMAdDelegate> {
    MMAdView *adView;
}

-(void)createView;

-(void)refresh:(id)args;

@end
