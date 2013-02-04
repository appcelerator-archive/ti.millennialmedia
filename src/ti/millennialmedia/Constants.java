/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.millennialmedia;

import java.util.Hashtable;
import java.util.HashMap;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.common.TiConfig;

public class Constants {
	public static final String LCAT = "MillennialmediaModule";
	public static final boolean DBG = TiConfig.LOGD;

	private static String _apid;

	public static void setApid(String val) {
		_apid = val;
	}

	public static String getApid() {
		return _apid;
	}

	private static KrollDict _demographics;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setDemographics(HashMap val) {
		_demographics = new KrollDict(val);
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