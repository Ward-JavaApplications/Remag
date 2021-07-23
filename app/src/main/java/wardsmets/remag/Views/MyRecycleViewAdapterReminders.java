package wardsmets.remag.Views;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import wardsmets.remag.AddReminderActivity;
import wardsmets.remag.MainActivity;
import wardsmets.remag.MainMenuActivity;
import wardsmets.remag.OpenReminderActivity;
import wardsmets.remag.R;
import wardsmets.remag.ReminderSettingsActivity;


public class MyRecycleViewAdapterReminders extends RecyclerView.Adapter<MyRecycleViewAdapterReminders.MyViewHolderReminders> {

    String[] reminderNames;
    int imageDelete = R.drawable.ic_baseline_delete_24;
    int imageSongSettings = R.drawable.ic_baseline_settings_applications_24;
    Context context;
    MainMenuActivity parent;
    public MyRecycleViewAdapterReminders(String[] reminderNames, Context context, MainMenuActivity parent){
        this.reminderNames =reminderNames;
        this.context = context;
        this.parent = parent;
    }

    @Override
    public MyViewHolderReminders onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_reminder_row,parent,false);
        return new MyViewHolderReminders(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleViewAdapterReminders.MyViewHolderReminders holder, int position) {
        holder.textView.setText(reminderNames[position]);
        holder.deleteButton.setImageResource(imageDelete);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.preferenceManager.deleteReminder(position);
                parent.reloadView();
            }
        });
        holder.settingsButton.setImageResource(imageSongSettings);
        holder.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(parent, ReminderSettingsActivity.class);
                    intent.putExtra("name", reminderNames[position]);
                    parent.startActivity(intent);
                    parent.finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent, OpenReminderActivity.class);
                intent.putExtra("name",reminderNames[position]);
                parent.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderNames.length;
    }

    public class MyViewHolderReminders extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageButton settingsButton;
        private ImageButton deleteButton;
        private ConstraintLayout mainLayout;
        public MyViewHolderReminders(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_reminder_name);
            settingsButton = itemView.findViewById(R.id.imageButton_reminderSettings);
            deleteButton = itemView.findViewById(R.id.imageButton_deleteReminder);
            mainLayout = itemView.findViewById(R.id.row_reminders_constraint_layout);
        }
    }
}
