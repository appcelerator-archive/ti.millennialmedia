/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.millennialmedia;

import java.util.Hashtable;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.util.TiConfig;

public class Constants {
	public static final String LCAT = "BrightcoveModule";
	public static final boolean DBG = TiConfig.LOGD;

	private static String _apid;

	public static void setApid(String val) {
		_apid = val;
	}

	public static String getApid() {
		return _apid;
	}

	private static KrollDict _demographics;

	public static void setDemographics(KrollDict val) {
		_demographics = val;
	}

	public static Hashtable<String, String> getDemographicsAsHashTable() {
		if (_demographics == null)
			return null;

		Hashtable<String, String> retVal = new Hashtable<String, String>();
		for (String key : _demographics.keySet()) {
			retVal.put(key, _demographics.getString(key));
		}

		return retVal;
	}
}