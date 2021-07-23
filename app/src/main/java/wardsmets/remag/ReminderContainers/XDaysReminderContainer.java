package wardsmets.remag.ReminderContainers;

import android.widget.EditText;

import wardsmets.remag.AddReminderActivity;
import wardsmets.remag.ReminderContainers.ReminderContainer;

public class XDaysReminderContainer extends ReminderContainer {
    private int everyXdays;

    public XDaysReminderContainer(String reminderName,int everyXdays) {
        super(reminderName);
        this.everyXdays = everyXdays;
    }

    public XDaysReminderContainer(String[] times, String reminderName,int everyXdays) {
        super(times, reminderName);
        this.everyXdays = everyXdays;
    }

    public int getEveryXdays() {
        return everyXdays;
    }

    @Override
    public int getTypeOfContainer() {
        return 1;
    }

    @Override
    public void setReminderViewFlipperContent(AddReminderActivity parent) {
        EditText editText = parent.getDaysText();
        editText.setText(String.valueOf(everyXdays));
    }
}
