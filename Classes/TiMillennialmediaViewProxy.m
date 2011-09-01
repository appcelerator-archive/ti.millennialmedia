/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiMillennialmediaViewProxy.h"
#import "TiUtils.h"

@implementation TiMillennialmediaViewProxy



-(void)viewDidAttach
{
    [(TiMillennialmediaView*)[self view] createView];
}

USE_VIEW_FOR_UI_METHOD(refresh);

@end
