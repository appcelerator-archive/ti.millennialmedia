/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.millennialmedia;

import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMAdViewSDK;

public class UIView extends TiUIView {
	public UIView(ViewProxy proxy, Activity activity) {
		super(proxy);

		MMAdView adView = new MMAdView(activity, "54131",
				MMAdView.BANNER_AD_TOP, 30);
		adView.setId(MMAdViewSDK.DEFAULT_VIEWID);
		setNativeView(adView);
	}
}
