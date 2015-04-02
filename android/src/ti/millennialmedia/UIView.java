/**
 * Appcelerator Titanium Mobile Modules
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package ti.millennialmedia;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMException;
import com.millennialmedia.android.MMInterstitial;
import com.millennialmedia.android.RequestListener;

public class UIView extends TiUIView implements RequestListener {

	private static AtomicInteger idGenerator;

	private ViewProxy proxy;
	private Activity activity;
	private MMAdView adView;
	private MMInterstitial adInter;

	private String apid;
	private Boolean autoLoad;
	private int adType;

	public UIView(ViewProxy _proxy, Activity _activity) {
		super(_proxy);
		proxy = _proxy;
		activity = _activity;

		if (idGenerator == null) {
			idGenerator = new AtomicInteger(5000);
		}
	}

	@Override
	public void processProperties(KrollDict args) {
		super.processProperties(args);

		apid = args.getString("apid");
		autoLoad = args.optBoolean("autoLoad", true);
		adType = args.optInt("adType", MillennialmediaModule.TiMMBanner);

		if (adType == MillennialmediaModule.TiMMBanner) {
			adView = new MMAdView(activity);
			adView.setApid(apid);

			// (Highly Recommended) Set the id to preserve your ad on configuration changes. Save Battery!
			// Each MMAdView you give requires a unique id.
			if (adView.getId() == View.NO_ID) {
				adView.setId(idGenerator.incrementAndGet());
			}

			adView.setIgnoresDensityScaling(args.optBoolean("ignoreDensityScaling", true));

			adView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
			adView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

			adView.setMMRequest(MillennialmediaModule.getInstance().getRequest());
			adView.setListener(this);

			setNativeView(adView);

			if (autoLoad) {
				adView.getAd();
			}
		} else if (adType == MillennialmediaModule.TiMMInterstitial) {
			adInter = new MMInterstitial(activity);
			adInter.setApid(apid);
			adInter.fetch(MillennialmediaModule.getInstance().getRequest(), this);
		} else {
			Util.e("Invalid type `" + adType + "` passed to `adType`.");
		}
	}

	/**
	 * Event Utils
	 */
	private void fireRequestCompleteEvent(Boolean success, String error)
	{
		MillennialmediaModule module = MillennialmediaModule.getInstance();
		if (module.hasListeners("adRequestComplete")) {
			HashMap<String, Object> event = new HashMap<String, Object>();
			event.put("success",success);
			event.put("adView",proxy);
			event.put("error", error);
			event.put("apid",apid);
			event.put("adType",adType);

			module.fireEvent("adRequestComplete", event);
		}
	}

	private void fireEventFromModule(String name)
	{
		MillennialmediaModule module = MillennialmediaModule.getInstance();
		if (module.hasListeners(name)) {
			HashMap<String, Object> event = new HashMap<String, Object>();
			event.put("adType",adType);
			event.put("apid",apid);

			module.fireEvent(name, event);
		}
	}

	/**
	 * Millennial Media Ad View Listener Integration
	 */

	// No applicationWillTerminateFromAd event in the MM Android SDK

	@Override
	public void onSingleTap(MMAd mmAd)
	{
		fireEventFromModule("adWasTapped");
	}

	@Override
	public void MMAdRequestIsCaching(MMAd mmAd)
	{
		// Event not available in the MM iOS SDK
		// Not documenting this event
		fireEventFromModule("isCaching");
	}

	@Override
	public void requestCompleted(MMAd mmAd)
	{
		fireRequestCompleteEvent(true, null);

		if (autoLoad && adInter != null) {
			adInter.display();
		}
	}

	@Override
	public void requestFailed(MMAd mmAd, MMException exception)
	{
		fireRequestCompleteEvent(false, exception.getMessage());
	}

	@Override
	public void MMAdOverlayLaunched(MMAd mmAd)
	{
		fireEventFromModule("modalDidAppear");
	}

	@Override
	public void MMAdOverlayClosed(MMAd mmAd)
	{
		fireEventFromModule("modalDidDismiss");
	}

	/**
	 * Public API will be called by the proxy
	 */

	public void refresh() {
		if (adType != MillennialmediaModule.TiMMBanner) {
			Util.w("The `refresh` method is only for Banner ads.");
			return;
		}

		if (adView != null) {
			adView.getAd();
		}
	}

	public void display() {
		if (adType != MillennialmediaModule.TiMMInterstitial) {
			Util.w("The `display` method is only for Interstitial ads.");
			return;
		}

		if (adInter != null) {
			adInter.display();
		}
	}

	public Boolean isAdAvailable() {
		if (adType != MillennialmediaModule.TiMMInterstitial) {
			Util.w("The `isAdAvailable` method is only for Interstitial ads.");
			return false;
		}

		if (adInter == null) {
			return false;
		}

		return adInter.isAdAvailable();
	}
}
