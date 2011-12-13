/**
 * Ti.MillennialMedia Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
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

	private UIView _view;

	public ViewProxy() {
		super();
	}

	@Override
	public TiUIView createView(Activity activity) {
		_view = new UIView(this, activity);
		return _view;
	}

	private Handler handler = new Handler(TiMessenger.getMainMessenger().getLooper(), this);
    private static final int MSG_REFRESH = 50000;

	public boolean handleMessage(Message msg)
	{
	    switch (msg.what) {
	        case MSG_REFRESH: {
	            AsyncResult result = (AsyncResult) msg.obj;
                handleRefresh();
	            result.setResult(null);
	            return true;
	        }
	    }
	    return false;
	}

	private void handleRefresh()
	{
        _view.refresh();
	}

	@Kroll.method
	public void refresh() {
	    if (_view != null) {
	    	if (!TiApplication.isUIThread()) {
	            TiMessenger.sendBlockingMainMessage(handler.obtainMessage(MSG_REFRESH));
        	} else {
        	    handleRefresh();
        	}
        }
	}
}
