/**
 * Appcelerator Titanium Mobile Modules
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package ti.millennialmedia;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;

import com.millennialmedia.android.MMRequest;
import com.millennialmedia.android.MMSDK;

import android.app.Activity;
import android.location.Location;

@Kroll.module(name = "Millennialmedia", id = "ti.millennialmedia")
public class MillennialmediaModule extends KrollModule {
	private static final String LCAT = "MillennialmediaModule";
	private static final String ModuleName = "Millennialmedia";

	public static final int TiMMBanner = 0;
	public static final int TiMMInterstitial = 1;
	
	private Activity _activity;
	
	private KrollDict _demographics;
	private String[] _keywords;
	private Location _geolocation;
	private KrollDict _customParameters;
	
	public MillennialmediaModule() {
		super(ModuleName);
	}
	
	public static MillennialmediaModule getInstance()
	{
		TiApplication appContext = TiApplication.getInstance();
		MillennialmediaModule mmModule = (MillennialmediaModule)appContext.getModuleByName(ModuleName);
	
		if (mmModule == null) {
			Log.w(LCAT,"Millennial Media module not currently loaded");
		}
		return mmModule;
	}
	
	@Override
	public void onStart(Activity activity) 
	{
		_activity = activity;	
		MMSDK.initialize(activity);
				
		super.onStart(activity);
	}
	
	public MMRequest getRequest()
	{
		MMRequest request = new MMRequest();
		
		// Set props on request here
		MMRequest.setUserLocation(_geolocation);
		
		if (_demographics !=  null) {
			for (String key : _demographics.keySet()) {
				String val = _demographics.getString(key);
				
				if (key.equals("education")) {
					request.setEducation(val);
				} else if (key.equals("gender")) {
					request.setGender(val);
				} else if (key.equals("ethnicity")) {
					request.setEthnicity(val);
				} else if (key.equals("maritalStatus")) {
					request.setMarital(val);
				} else if (key.equals("orientation")) {
					request.setOrientation(val);
				} else if (key.equals("age")) {
					request.setAge(val);
				} else if (key.equals("zipCode")) {
					 request.setZip(val);
				}
			}
		}
		
		// Keywords on iOS takes an array of strings and joins them with commas.
		// Doing the same thing here for the sake of parity.
		if (_keywords != null && _keywords.length > 0) {
			request.setKeywords(Util.join(_keywords));
		}
		
		if (_customParameters != null) {
			for (String key : _customParameters.keySet()) {
				String val = _customParameters.getString(key);
				request.put(key, val);
			}
		}
		
		return request;
	}
	
	/**
	 * Public API
	 */
	
	@Kroll.getProperty  @Kroll.method
	public String getVersion()
	{
		return MMSDK.VERSION;
	}
	
	@Kroll.method
	public void resetCache()
	{
		// No equivalent in the MM iOS SDK
		// Not documenting
		MMSDK.resetCache(_activity);
	}
	
	@Kroll.setProperty @Kroll.method
	public void setLogLevel(int level)
	{
		MMSDK.setLogLevel(level);
	}
	
	@Kroll.method
	public void trackEvent(Object hm)
	{
		KrollDict args = Util.krollDictFromHashMap(hm);
		Util.checkRequired("eventId", args);
		String eventId = args.getString("eventId");
		
		MMSDK.trackEvent(_activity, eventId);
	}
	
	@Kroll.method
	public void trackConversion(Object hm)
	{
		KrollDict args = Util.krollDictFromHashMap(hm);
		Util.checkRequired("goalId", args);
		String goalId = args.getString("goalId");
		
		MMSDK.trackConversion(_activity, goalId);
	}
	
	@Kroll.setProperty @Kroll.method
	public void setDemographics(@Kroll.argument(optional=true) Object hm) {
		if (hm == null) {
			_demographics = null;
			return;
		}
		
		KrollDict args = Util.krollDictFromHashMap(hm);
		_demographics = args;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setKeywords(@Kroll.argument(optional=true) String[] arg) {
		// arg doesn't need to be checked for type here because an 
		// exception will be thrown if any other type is passed to the method
		_keywords = arg;
	}
	
	@Kroll.setProperty @Kroll.method
	public void setGeolocation(@Kroll.argument(optional=true) Object hm) {
		if (hm == null) {
			_geolocation = null;
			return;
		}
		
		KrollDict args = Util.krollDictFromHashMap(hm);
		Util.checkRequired("latitude", args);
		Util.checkRequired("longitude", args);
		double latitude = args.getDouble("latitude");
		double longitude = args.getDouble("longitude");
		
		_geolocation = new Location("MillennialmediaModule");
		_geolocation.setLatitude(latitude);
		_geolocation.setLongitude(longitude);
	}
	
	@Kroll.setProperty @Kroll.method
	public void setCustom(@Kroll.argument(optional=true) Object hm) {
		if (hm == null) {
			_customParameters = null;
			return;
		}
		_customParameters = Util.krollDictFromHashMap(hm);
	}
	
	/**
	 * Constants
	 */
	
	@Kroll.constant public static final int TYPE_BANNER = TiMMBanner;
	@Kroll.constant public static final int TYPE_INTERSTITIAL = TiMMInterstitial;
	
	// Log Level
	@Kroll.constant public static final int LOG_LEVEL_ERROR = MMSDK.LOG_LEVEL_ERROR;
	@Kroll.constant public static final int LOG_LEVEL_INFO = MMSDK.LOG_LEVEL_INFO;
	@Kroll.constant public static final int LOG_LEVEL_DEBUG = MMSDK.LOG_LEVEL_DEBUG;
	@Kroll.constant public static final int LOG_LEVEL_VERBOSE = MMSDK.LOG_LEVEL_VERBOSE;
	
	// Gender
	@Kroll.constant public static final String GENDER_MALE = MMRequest.GENDER_MALE;
	@Kroll.constant public static final String GENDER_FEMALE = MMRequest.GENDER_FEMALE;
	@Kroll.constant public static final String GENDER_OTHER = MMRequest.GENDER_OTHER;
	
	// Ethnicity
	@Kroll.constant public static final String ETHNICITY_ASIAN = MMRequest.ETHNICITY_ASIAN;
	@Kroll.constant public static final String ETHNICITY_BLACK = MMRequest.ETHNICITY_BLACK;
	@Kroll.constant public static final String ETHNICITY_HISPANIC = MMRequest.ETHNICITY_HISPANIC;
	@Kroll.constant public static final String ETHNICITY_INDIAN = MMRequest.ETHNICITY_INDIAN;
	@Kroll.constant public static final String ETHNICITY_MIDDLE_EASTERN = MMRequest.ETHNICITY_MIDDLE_EASTERN;
	@Kroll.constant public static final String ETHNICITY_NATIVE_AMERICAN = MMRequest.ETHNICITY_NATIVE_AMERICAN;
	@Kroll.constant public static final String ETHNICITY_PACIFIC_ISLANDER = MMRequest.ETHNICITY_PACIFIC_ISLANDER;
	@Kroll.constant public static final String ETHNICITY_WHITE = MMRequest.ETHNICITY_WHITE;
	@Kroll.constant public static final String ETHNICITY_OTHER = MMRequest.ETHNICITY_OTHER;

	// Education
	@Kroll.constant public static final String EDUCATION_HIGH_SCHOOL = MMRequest.EDUCATION_HIGH_SCHOOL;
	@Kroll.constant public static final String EDUCATION_IN_COLLEGE = MMRequest.EDUCATION_IN_COLLEGE;
	@Kroll.constant public static final String EDUCATION_SOME_COLLEGE = MMRequest.EDUCATION_SOME_COLLEGE;
	@Kroll.constant public static final String EDUCATION_ASSOCIATES = MMRequest.EDUCATION_ASSOCIATES;
	@Kroll.constant public static final String EDUCATION_BACHELORS = MMRequest.EDUCATION_BACHELORS;
	@Kroll.constant public static final String EDUCATION_MASTERS = MMRequest.EDUCATION_MASTERS;
	@Kroll.constant public static final String EDUCATION_DOCTORATE = MMRequest.EDUCATION_DOCTORATE;
	@Kroll.constant public static final String EDUCATION_OTHER = MMRequest.EDUCATION_OTHER;

	// Marital Status
	@Kroll.constant public static final String MARITAL_SINGLE = MMRequest.MARITAL_SINGLE;
	@Kroll.constant public static final String MARITAL_RELATIONSHIP = MMRequest.MARITAL_RELATIONSHIP;
	@Kroll.constant public static final String MARITAL_MARRIED = MMRequest.MARITAL_MARRIED;
	@Kroll.constant public static final String MARITAL_DIVORCED = MMRequest.MARITAL_DIVORCED;
	@Kroll.constant public static final String MARITAL_ENGAGED = MMRequest.MARITAL_ENGAGED;
	@Kroll.constant public static final String MARITAL_OTHER = MMRequest.MARITAL_OTHER;

	// Sexual Orientation
	@Kroll.constant public static final String SEXUAL_ORIENTATION_STRAIGHT = MMRequest.ORIENTATION_STRAIGHT;
	@Kroll.constant public static final String SEXUAL_ORIENTATION_GAY = MMRequest.ORIENTATION_GAY;
	@Kroll.constant public static final String SEXUAL_ORIENTATION_BISEXUAL = MMRequest.ORIENTATION_BISEXUAL;
	@Kroll.constant public static final String SEXUAL_ORIENTATION_OTHER = MMRequest.ORIENTATION_OTHER;
}
