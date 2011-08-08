package pl.froger.hello.broadcastreceiver;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainService extends Service {
	private int counter;
	private Timer timer;
	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			counter++;
			sendMessageToActivity();
		}
	};
	
	private void sendMessageToActivity() {
		Intent intent = new Intent(MainActivity.ACTION_NEW_MSG);
		intent.putExtra(MainActivity.MSG_FIELD, counter + ". message from Service");
		sendBroadcast(intent);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		counter = 0;
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 4000);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		timerTask.cancel();
		timer.purge();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}