/**
 * Appcelerator Titanium Mobile Modules
 * Copyright (c) 2010-2015 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package ti.millennialmedia;

import java.util.HashMap;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.common.Log;

/**
 * Holds various static methods that we will use throughout the module.
 *
 * @author Dawson Toth, Appcelerator Inc.
 */
public final class Util {

	public static final String LCAT = "MillennialmediaModule";

	/**
	 * Prevents instantiation.
	 */
	private Util() {
	}

	public static void checkRequired(String key, KrollDict args) {
		if (!args.containsKey(key)) {
			throw new IllegalArgumentException("`"+key+"` required");
		}
	}

	public static KrollDict krollDictFromHashMap(Object hm) {
		if (!(hm instanceof HashMap)) {
			throw new IllegalArgumentException("argument must be a <Dictionary> of key-value pairs");
		}

		return new KrollDict((HashMap) hm);
	}

	public static String join(String[] arr) {
		if(arr.length < 1) return "";

		StringBuilder builder = new StringBuilder();
		int length = arr.length;
		int index = 0;
		for(String s : arr) {
		    builder.append(s);
		    if (++index < length) {
		    	builder.append(",");
		    }
		}
		return builder.toString();
	}

	/*
	 * These 8 methods are useful for logging purposes -- they make what we do
	 * in this module a tiny bit easier.
	 */
	public static void d(String msg) {
		Log.d(LCAT, msg);
	}

	public static void d(String msg, Throwable e) {
		Log.d(LCAT, msg, e);
	}

	public static void i(String msg) {
		Log.i(LCAT, msg);
	}

	public static void i(String msg, Throwable e) {
		Log.i(LCAT, msg, e);
	}

	public static void w(String msg) {
		Log.w(LCAT, msg);
	}

	public static void w(String msg, Throwable e) {
		Log.w(LCAT, msg, e);
	}

	public static void e(String msg) {
		Log.e(LCAT, msg);
	}

	public static void e(String msg, Throwable e) {
		Log.e(LCAT, msg, e);
	}
}
