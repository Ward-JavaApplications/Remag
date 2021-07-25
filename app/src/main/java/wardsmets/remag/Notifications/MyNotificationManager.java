package wardsmets.remag.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import wardsmets.remag.MainActivity;
import wardsmets.remag.ReminderContainers.ReminderContainer;

public class MyNotificationManager {
    private Context context;
    public MyNotificationManager(Context context){
        this.context = context;
        scheduleNextReminder();
    }



    private void scheduleNextReminder(){
        Date currentDate = new Date(System.currentTimeMillis());
        String previousTime = currentDate.getHours() + ":" + currentDate.getMinutes();
        try {
            ArrayList<ReminderContainer> reminders = MainActivity.preferenceManager.getReminderContainerArrayList();
            TreeMap<String,String> allTimes = new TreeMap<>();
            for(ReminderContainer container: reminders){
                for(String time: container.getTimes()){
                    allTimes.put(time,container.getReminderName());
                }
            }
//            for(Map.Entry<String,String> entry: allTimes.entrySet()){
//                Log.v("markel","This is the key " + entry.getKey() + " and this is the value " + entry.getValue());
//            }
            if(previousTime ==null){
                Map.Entry<String, String> firstEntry = allTimes.firstEntry();
                programNextReminder(firstEntry.getKey(), firstEntry.getValue());
                return;
            }
            else{
                for(Map.Entry<String,String> entry: allTimes.entrySet()){
                    if(timeIsLater(previousTime,entry.getKey())){
                        programNextReminder(entry.getKey(),entry.getValue());
                        return;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    private boolean timeIsLater(String previousTime,String currentTime){
        int previousHour = Integer.parseInt(previousTime.substring(0,2));
        int currentHour = Integer.parseInt(currentTime.substring(0,2));
        if(previousHour<currentHour) return true;
        else if(previousHour == currentHour){
            int previousMinutes = Integer.parseInt(previousTime.substring(3,5));
            int currentMinutes = Integer.parseInt(currentTime.substring(3,5));
            if(previousMinutes<=currentMinutes) return true;
            else return false;
        }
        else return false;
    }

    private void programNextReminder(String time,String reminderName){
        Intent notifyIntent = new Intent(context,MyReceiver.class);
        notifyIntent.putExtra("title",reminderName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,MainActivity.requestCodeAlarmManager,
                notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Date date = new Date(System.currentTimeMillis());
        date.setHours(Integer.parseInt(time.substring(0,2)));
        date.setMinutes(Integer.parseInt(time.substring(3,5)));

        Log.v("markel","The next reminder should be set at " + date.toString());
        Toast.makeText(context,"The next reminder should be set at " + date.toString(),Toast.LENGTH_LONG).show();

        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,date.getTime(),pendingIntent);
    }
}
