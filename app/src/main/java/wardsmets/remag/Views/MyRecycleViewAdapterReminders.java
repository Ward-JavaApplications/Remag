package wardsmets.remag.Views;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import wardsmets.remag.R;


public class MyRecycleViewAdapterReminders extends RecyclerView.Adapter<MyRecycleViewAdapterReminders.MyViewHolderReminders> {

    String[] reminderNames;
    Context context;
    public MyRecycleViewAdapterReminders(String[] reminderNames,Context context){
        this.reminderNames =reminderNames;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return reminderNames.length;
    }

    public class MyViewHolderReminders extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageButton settingsButton;
        private ImageButton deleteButton;
        public MyViewHolderReminders(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_reminder_name);
            settingsButton = itemView.findViewById(R.id.imageButton_reminderSettings);
            deleteButton = itemView.findViewById(R.id.imageButton_deleteReminder);
        }
    }
}
