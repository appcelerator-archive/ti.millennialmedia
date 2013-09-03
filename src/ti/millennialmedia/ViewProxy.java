/**
 * Appcelerator Titanium Mobile Modules
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package ti.millennialmedia;

import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.TiMessenger;
import org.appcelerator.kroll.common.AsyncResult;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

@Kroll.proxy(creatableInModule = MillennialmediaModule.class)
public class ViewProxy extends TiViewProxy {
	
	private UIView view;

	public ViewProxy() {
		super();
	}

	@Override
	public TiUIView createView(Activity activity) {
		view = new UIView(this, activity);
		return view;
	}
	
    private static final int MSG_REFRESH = 50000;
    private static final int MSG_DISPLAY = 60000;
    private static final int MSG_ISADAVAILABLE = 70000;

	private final Handler handler = new Handler(TiMessenger.getMainMessenger().getLooper(), new Handler.Callback ()
	{
    	public boolean handleMessage(Message msg)
        {
            switch (msg.what) {
                case MSG_REFRESH: {
                    handleRefresh();
                    ((AsyncResult) msg.obj).setResult(null);
                    return true;
                }
                case MSG_DISPLAY: {
                    handleDisplay();
                    ((AsyncResult) msg.obj).setResult(null);
                    return true;
                }
                case MSG_ISADAVAILABLE: {
                    ((AsyncResult) msg.obj).setResult(handleIsAdAvailable());
                    return true;
                }
            }
            return false;
        }
	});

	private void handleRefresh()
	{
        view.refresh();
	}
	
	private void handleDisplay()
	{
		view.display();
	}
	
	private Boolean handleIsAdAvailable()
	{
		return view.isAdAvailable();
	}

	/**
	 * Public API
	 */
	
	@Kroll.method
	public void refresh()
	{
	    if (view != null) {
	    	if (!TiApplication.isUIThread()) {
	            TiMessenger.sendBlockingMainMessage(handler.obtainMessage(MSG_REFRESH));
        	} else {
        	    handleRefresh();
        	}
        }
	}
	
	@Kroll.method
	public void display()
	{
	    if (view != null) {
	    	if (!TiApplication.isUIThread()) {
	            TiMessenger.sendBlockingMainMessage(handler.obtainMessage(MSG_DISPLAY));
        	} else {
        		handleDisplay();
        	}
        }
	}
	
	@Kroll.method
	public Boolean isAdAvailable()
	{
		if (view != null) {
	    	if (!TiApplication.isUIThread()) {
	    		Object result = TiMessenger.sendBlockingMainMessage(handler.obtainMessage(MSG_ISADAVAILABLE));
	            if (result instanceof Boolean) {
	            	return (Boolean) result;
	            } else {
	            	return false;
	            }
        	} else {
        		return handleIsAdAvailable();
        	}
        } else {
        	return false;
        }
	}
}
