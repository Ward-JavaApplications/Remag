package wardsmets.remag.ReminderContainers;

import wardsmets.remag.ReminderContainers.ReminderContainer;

public class CustomDayReminderContainer extends ReminderContainer {
    private String[] days;
    public CustomDayReminderContainer(String reminderName,String[] days) {
        super(reminderName);
        this.days = days;
    }

    public CustomDayReminderContainer(String[] times, String reminderName,String[] days) {
        super(times, reminderName);
        this.days = days;
    }
}
