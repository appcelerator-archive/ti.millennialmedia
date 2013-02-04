/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.millennialmedia;

import java.util.Hashtable;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMAdView.MMAdListener;
import com.millennialmedia.android.MMAdViewSDK;

public class UIView extends TiUIView {

	private Activity _activity;
	private MMAdView _adView;

	public UIView(ViewProxy proxy, Activity activity) {
		super(proxy);
		_activity = activity;
	}

	@Override
	public void processProperties(KrollDict args) {
		super.processProperties(args);

		Boolean autoLoad = args.optBoolean("autoLoad", true);
		Boolean autoRefresh = args.optBoolean("autoRefresh", true);
		int refreshDuration = args.optInt("refreshDuration", 60);
		String apid = args.getString("apid");
		String type = args.optString("type", MMAdView.BANNER_AD_TOP);

		_adView = new MMAdView(_activity, apid, type,
				autoLoad ? (autoRefresh ? refreshDuration : 0)
						: MMAdView.REFRESH_INTERVAL_OFF);
		_adView.setIgnoresDensityScaling(args.optBoolean(
				"ignoreDensityScaling", true));
		_adView.setId(MMAdViewSDK.DEFAULT_VIEWID);

		Hashtable<String, String> demographics = Constants
				.getDemographicsAsHashTable();
		if (demographics != null) {
			_adView.setMetaValues(demographics);
		}

		_adView.setListener(new UIViewListener());
		setNativeView(_adView);

		if (!type.equals(MMAdView.BANNER_AD_TOP) && !type.equals(MMAdView.BANNER_AD_BOTTOM) && !type.equals(MMAdView.BANNER_AD_RECTANGLE)) {
			if (autoLoad) {
				_adView.callForAd();
			}
		}
	}

	public void refresh() {
		if (_adView != null) {
			_adView.callForAd();
		}
	}

	private final class UIViewListener implements MMAdListener {
		@Override
		public void MMAdReturned(MMAdView arg) {
			proxy.fireEvent("success", null);
		}

		@Override
		public void MMAdRequestIsCaching(MMAdView arg) {
			proxy.fireEvent("isCaching", null);

		}

		@Override
		public void MMAdOverlayLaunched(MMAdView arg) {
			proxy.fireEvent("modalDidAppear", null);

		}

		@Override
		public void MMAdFailed(MMAdView arg) {
			proxy.fireEvent("fail", null);

		}

		@Override
		public void MMAdClickedToOverlay(MMAdView arg) {
			proxy.fireEvent("modalWillAppear", null);

		}

		@Override
		public void MMAdClickedToNewBrowser(MMAdView arg) {
			proxy.fireEvent("willTerminate", null);

		}

		@Override
		public void MMAdCachingCompleted(MMAdView arg0, boolean arg1) {
			proxy.fireEvent("cached", null);
		}
	}

}
