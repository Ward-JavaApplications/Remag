package wardsmets.remag;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import wardsmets.remag.Notifications.MyReceiver;
import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.ReminderContainers.XDaysReminderContainer;
import wardsmets.remag.Views.MyRecycleViewAdapterAddReminder;
import wardsmets.remag.Views.MyRecycleViewAdapterReminders;
import wardsmets.remag.tests.TestActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loadReminders();
        calculateNextReminder();
    }

    public void loadAddReminder(View view){
        Intent intent = new Intent(this,AddReminderActivity.class);
        startActivity(intent);
        finish();
    }

    public void reloadView(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void calculateNextReminder(){
        try {
            for(ReminderContainer container:MainActivity.preferenceManager.getReminderContainerArrayList()){
                Intent notifyIntent = new Intent(this, MyReceiver.class);
                notifyIntent.putExtra("title",container.getReminderName());
                if(container.getTypeOfContainer() == 0){
                    //CustomDayReminder

                }
                else if(container.getTypeOfContainer() == 1){
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), MainActivity.requestCodeAlarmManager, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Date date = new Date(System.currentTimeMillis());
                    date.setHours(Integer.parseInt(container.getTimes()[0].substring(0,2)));
                    date.setMinutes(Integer.parseInt(container.getTimes()[0].substring(3,5)));
                    date.setSeconds(0);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, date.getTime(),AlarmManager.INTERVAL_DAY*((XDaysReminderContainer)container).getEveryXdays(),pendingIntent);
                    Log.v("markel",date.toString());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void loadReminders(){
        try {
            ArrayList<ReminderContainer> arrayList = MainActivity.preferenceManager.getReminderContainerArrayList();
            ArrayList<String> names = new ArrayList<>(arrayList.size());
            for (ReminderContainer reminderContainer : arrayList) {
                names.add(reminderContainer.getReminderName());
            }
            String[] namesArray = names.toArray(new String[0]);

            //RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recycler_view_time_of_day);
            MyRecycleViewAdapterReminders adapter = new MyRecycleViewAdapterReminders(namesArray, getApplicationContext(),this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void settingsClicked(View view){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }


}
