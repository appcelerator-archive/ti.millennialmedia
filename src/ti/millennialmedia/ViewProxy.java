/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.millennialmedia;

import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.kroll.annotations.Kroll;

import android.app.Activity;

@Kroll.proxy(creatableInModule = MillennialmediaModule.class)
public class ViewProxy extends TiViewProxy {

	public ViewProxy(TiContext tiContext) {
		super(tiContext);
	}

	@Override
	public TiUIView createView(Activity activity) {
		return new UIView(this, activity);
	}
	
}
