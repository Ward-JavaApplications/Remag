package wardsmets.remag;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import wardsmets.remag.ReminderContainers.ReminderContainer;

/**
 * Preference manager to manage the list of dates when we want to be reminded
 */
public class PreferenceManager {
    private final int preferenceMode = 0;
    private Context context;
    private final String preferenceValues = "Preferences_Reminder";
    private final String preferenceNames = "Preferences_Names";
    private static ArrayList<ReminderContainer> reminderContainerArrayList;

    public PreferenceManager(Context context) {
        this.context = context;
        if(reminderContainerArrayList == null) reminderContainerArrayList = new ArrayList<>();
        populatePreferences();

    }

    private void populatePreferences(){
        fetchPreference();

    }

    public void printTest(){

        fetchPreference();
        StringBuilder builder = new StringBuilder();
        for(ReminderContainer container: reminderContainerArrayList){
            for(String time: container.getTimes()){
                Log.v("markel", time);
                builder.append(time);
            }
        }
        String s = builder.toString();
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public void updateReminders(ReminderContainer container){
        syncReminders(container);
        pushPreferences();
    }

    public void deleteReminder(String name){
        Iterator<ReminderContainer> iterator = reminderContainerArrayList.iterator();
        while(iterator.hasNext()){
            ReminderContainer currentContainer = iterator.next();
            if(currentContainer.getReminderName().equals(name)) reminderContainerArrayList.remove(currentContainer);
        }
        pushPreferences();
    }
    public void deleteReminder(int position){
        reminderContainerArrayList.remove(position);
        pushPreferences();
    }


    public void syncReminders(ReminderContainer container){
        try {
            String name = container.getReminderName();
            String[] times = container.getTimes();
            boolean wasPresent = false;
            for(int i = 0; i<reminderContainerArrayList.size();i++) {
                if (reminderContainerArrayList.get(i).getReminderName().equals(name)) {
                    reminderContainerArrayList.set(i,container);
                    wasPresent = true;
                }
            }

            if(!wasPresent){
                reminderContainerArrayList.add(container);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearPreferences(SharedPreferences pref){
        pref.edit().clear().commit();
    }
    public void pushPreferences(){
        SharedPreferences preferences = context.getSharedPreferences(preferenceValues,preferenceMode);
        SharedPreferences preferencesNames = context.getSharedPreferences(preferenceNames,preferenceMode);
        clearPreferences(preferences);
        clearPreferences(preferencesNames);
        for(ReminderContainer container:reminderContainerArrayList){
            SharedPreferences.Editor editor = preferences.edit();
            for(int i = 0;i<container.getTimesSize();i++){
                editor.putString(container.getReminderName()+"_"+i,container.getTimes()[i]);
            }
            editor.commit();
            SharedPreferences.Editor editorName = preferencesNames.edit();
            editorName.putInt(container.getReminderName() + "_size",container.getTimesSize());
            editorName.commit();
        }
    }

    public void fetchPreference(){
        SharedPreferences preferencesName = context.getSharedPreferences(preferenceNames,preferenceMode);
        SharedPreferences preferencesDates = context.getSharedPreferences(preferenceValues,preferenceMode);
        Map<String, ?> map =  preferencesName.getAll();
        for(Map.Entry<String,?> entry: map.entrySet()){
            String arrayName = entry.getKey().substring(0,entry.getKey().length()-5);
            int arrayLength = preferencesName.getInt(entry.getKey(), 0);
            String[] reminders = new String[arrayLength];
            for(int i=0;i<arrayLength;i++){
                reminders[i] = preferencesDates.getString(arrayName + "_"+i,"");
            }
            syncReminders(new ReminderContainer(reminders, arrayName));
        }
    }
    public ArrayList<ReminderContainer> getReminderContainerArrayList(){
        fetchPreference();
        return reminderContainerArrayList;
    }
}
