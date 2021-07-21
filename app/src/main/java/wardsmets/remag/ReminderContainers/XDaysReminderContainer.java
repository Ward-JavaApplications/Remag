package wardsmets.remag.ReminderContainers;

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
}
