/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.millennialmedia;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;

import com.millennialmedia.android.MMAdView;

import java.util.HashMap;

@Kroll.module(name = "Millennialmedia", id = "ti.millennialmedia")
public class MillennialmediaModule extends KrollModule {
	public MillennialmediaModule() {
		super();
	}

	@Kroll.setProperty
	@SuppressWarnings("rawtypes")
	public void setDemographics(HashMap val) {
		Constants.setDemographics(val);
	}

	@Kroll.method
	public void trackGoal(String goal) {
		MMAdView.startConversionTrackerWithGoalId(TiApplication.getInstance().getApplicationContext(),
				goal);
	}

    @Kroll.constant public static final String TYPE_TOP = MMAdView.BANNER_AD_TOP;
    @Kroll.constant public static final String TYPE_BOTTOM = MMAdView.BANNER_AD_BOTTOM;
    @Kroll.constant public static final String TYPE_RECTANGLE = MMAdView.BANNER_AD_RECTANGLE;
    @Kroll.constant public static final String TYPE_LAUNCH = MMAdView.FULLSCREEN_AD_LAUNCH;
    @Kroll.constant public static final String TYPE_TRANSITION = MMAdView.FULLSCREEN_AD_TRANSITION;
}
