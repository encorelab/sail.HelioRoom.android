/**
 *
 */
package org.encorelab.sail.helioroom;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author armin - largely copied from tebemis
 *
 */
public class XmppService extends Service {
	private final IBinder localBinder = new LocalBinder();
	private XMPPThread nt = null;

	@Override
	public void onCreate() {
		super.onCreate();
		nt = new XMPPThread("test2",
				"a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", "textNotifier",
				"s3@conference.proto.encorelab.org", "Heliotest1");
		nt.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		nt.disconnect();
		nt.interrupt();
	}

	public IBinder onBind(Intent i) {
		return localBinder;
	}

	public class LocalBinder extends Binder {
		XmppService getService() {
			return XmppService.this;
		}
	}

	public void sendMessage(String dest, String message) {
		nt.sendTo(dest, message);
	}
	
	public void sendGroupChat(String message) {
		nt.sendGroupChat(message);
	}

	public void addObserver(Hypothesis hypo) {
		nt.addObserver(hypo);
	}
}
