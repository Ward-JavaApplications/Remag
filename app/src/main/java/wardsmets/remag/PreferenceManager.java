package wardsmets.remag;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import wardsmets.remag.ReminderContainers.CustomDayReminderContainer;
import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.ReminderContainers.XDaysReminderContainer;

/**
 * Preference manager to manage the list of dates when we want to be reminded
 */
public class PreferenceManager {
    private final int preferenceMode = 0;
    private Context context;
    private final String preferenceValues = "Preferences_Reminder";
    private final String preferenceNames = "Preferences_Names";
    private final String preferenceTypes = "Preferences_types";
    private static ArrayList<ReminderContainer> reminderContainerArrayList;

    public PreferenceManager(Context context) {
        this.context = context;
        if(reminderContainerArrayList == null) reminderContainerArrayList = new ArrayList<>();
        populatePreferences();

    }

    private void populatePreferences(){
        fetchPreference();

    }

//    public void printTest(){
//
//        fetchPreference();
//        StringBuilder builder = new StringBuilder();
//        for(ReminderContainer container: reminderContainerArrayList){
//            for(String time: container.getTimes()){
//                Log.v("markel", time);
//                builder.append(time);
//            }
//        }
//        String s = builder.toString();
//        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
//    }

    public void updateReminders(ReminderContainer container){
        if(container == null) return;
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
        //Log.v("markel", "pushing");
        SharedPreferences preferences = context.getSharedPreferences(preferenceValues,preferenceMode);
        SharedPreferences preferencesNames = context.getSharedPreferences(preferenceNames,preferenceMode);
        SharedPreferences preferencesTypes = context.getSharedPreferences(preferenceTypes,preferenceMode);
        clearPreferences(preferences);
        clearPreferences(preferencesNames);
        clearPreferences(preferencesTypes);
        for(ReminderContainer container:reminderContainerArrayList){
            SharedPreferences.Editor editor = preferences.edit();
            for(int i = 0;i<container.getTimesSize();i++){
                editor.putString(container.getReminderName()+"_"+i,container.getTimes()[i]);
                //Log.v("markel", container.getReminderName()+"_"+i + " and this is the value " + container.getTimes()[i]);
            }
            editor.commit();
            SharedPreferences.Editor editorName = preferencesNames.edit();
            editorName.putInt(container.getReminderName() + "_size",container.getTimesSize());
            editorName.commit();
            SharedPreferences.Editor editorTypes = preferencesTypes.edit();
            if(container.getTypeOfContainer() == 1){
                editorTypes.putInt(container.getReminderName() + "_1",((XDaysReminderContainer)container).getEveryXdays());
                //Log.v("markel",container.getReminderName() + "_1 and we want a reminder every " +  String.valueOf(((XDaysReminderContainer)container).getEveryXdays()) + " days");
                editorTypes.commit();
            }
            else if(container.getTypeOfContainer() == 0){
                editorTypes.putString(container.getReminderName() + "_2",((CustomDayReminderContainer)container).getDays());
                //Log.v("markel", container.getReminderName() + "_2 with as custom days: " + ((CustomDayReminderContainer)container).getDays());
                editorTypes.commit();
            }
        }
    }

    public void fetchPreference(){
        try {
            //Log.v("markel", "fetching");
            SharedPreferences preferencesName = context.getSharedPreferences(preferenceNames, preferenceMode);
            SharedPreferences preferencesDates = context.getSharedPreferences(preferenceValues, preferenceMode);
            SharedPreferences preferencesTypes = context.getSharedPreferences(preferenceTypes, preferenceMode);
            Map<String, ?> mapTypes = preferencesTypes.getAll();
            Map<String, ?> map = preferencesName.getAll();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                String arrayName = entry.getKey().substring(0, entry.getKey().length() - 5);
                int arrayLength = preferencesName.getInt(entry.getKey(), 0);
                String[] reminders = new String[arrayLength];
                for (int i = 0; i < arrayLength; i++) {
                    reminders[i] = preferencesDates.getString(arrayName + "_" + i, "");
                    //Log.v("markel", "this is one of the times " + reminders[i]);
                }
                for (Map.Entry<String, ?> entryType : mapTypes.entrySet()) {
                    String arrayNameType = entryType.getKey().substring(0, entryType.getKey().length() - 2);
                    //Log.v("markel", arrayNameType + " this is the arrayNameType");
                    String type = entryType.getKey().substring(entryType.getKey().length()-1,entryType.getKey().length());
                    //Log.v("markel", " this is the type " + type);
                    int typeInt = Integer.parseInt(type);
                    if (arrayName.equals(arrayNameType)) {
                        if(typeInt == 1){
                            int daysInBetween = preferencesTypes.getInt(entryType.getKey(), 0);
                            //Log.v("markel", "These are the days in between " + String.valueOf(daysInBetween));
                            syncReminders(new XDaysReminderContainer(reminders,arrayName,daysInBetween));
                        }
                        else if(typeInt == 2){
                            String customDays = preferencesTypes.getString(entryType.getKey(), "");
                            //Log.v("markel", "These are the the customdays " + customDays);
                            syncReminders(new CustomDayReminderContainer(reminders,arrayName,customDays));
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<ReminderContainer> getReminderContainerArrayList(){
        fetchPreference();
        return reminderContainerArrayList;
    }
}
