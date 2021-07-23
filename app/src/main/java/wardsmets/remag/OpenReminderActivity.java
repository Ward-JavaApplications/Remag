package wardsmets.remag;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wardsmets.remag.ReminderContainers.ReminderContainer;

public class OpenReminderActivity extends AddReminderActivity{
    private String reminderName;
    private ReminderContainer reminderContainer;

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
    private ReminderContainer getReminderContainer(){
        reminderName = getIntent().getStringExtra("name");
        ArrayList<ReminderContainer> reminders =  MainActivity.preferenceManager.getReminderContainerArrayList();
        for(ReminderContainer container: reminders){
            if(container.getReminderName().equals(reminderName)) reminderContainer = container;
        }
        return reminderContainer;
    }

    @Override
    public void setTextViewName(TextView textView) {
        textView.setText(reminderName);
    }

    @Override
    public int setSpinnerPosition() {
        return getReminderContainer().getTypeOfContainer();
    }

    @Override
    public void setReminderContainerView(AddReminderActivity parent) {
        getReminderContainer().setReminderViewFlipperContent(parent);
    }
}
