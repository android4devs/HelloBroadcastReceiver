package pl.froger.hello.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String ACTION_NEW_MSG = "pl.froger.hello.broadcastreceiver.NEW_MSG";
	public static final String MSG_FIELD = "message";

	private TextView tvMessage;
	private MyReceiver myReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		initService();
		initReceiver();
	}

	private void initService() {
		Intent intent = new Intent(this, MainService.class);
		startService(intent);
	}

	private void initReceiver() {
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter(ACTION_NEW_MSG);
		registerReceiver(myReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		finishService();
		finishReceiver();
		super.onDestroy();
	}

	private void finishService() {
		Intent intent = new Intent(this, MainService.class);
		stopService(intent);
	}

	private void finishReceiver() {
		unregisterReceiver(myReceiver);
	}

	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_NEW_MSG)) {
				String message = intent.getStringExtra(MSG_FIELD);
				tvMessage.setText(message);
			}
		}
	}
}