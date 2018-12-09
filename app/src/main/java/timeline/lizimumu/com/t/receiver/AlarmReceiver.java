package timeline.lizimumu.com.t.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timeline.lizimumu.com.t.service.AlarmService;

/**
 * Alarm receiver
 * Created by nmisa on 24/11/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AlarmService.class));
    }
}
