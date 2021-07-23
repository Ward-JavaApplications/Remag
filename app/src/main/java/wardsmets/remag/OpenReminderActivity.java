package wardsmets.remag;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wardsmets.remag.ReminderContainers.ReminderContainer;

public class OpenReminderActivity extends AddReminderActivity{
    private String reminderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reminderName = getIntent().getStringExtra("name");
    }

    @Override
    public String[] getTimes(){
        reminderName = getIntent().getStringExtra("name");
        ArrayList<ReminderContainer> reminders =  MainActivity.preferenceManager.getReminderContainerArrayList();
        for(ReminderContainer container: reminders){
            if(container.getReminderName().equals(reminderName)) return container.getTimes();
        }
        Toast.makeText(getApplicationContext(),"no times set yet",Toast.LENGTH_SHORT).show();
        return new String[0];
    }

    @Override
    public void setTextViewName(TextView textView) {
        textView.setText(reminderName);
    }
}
