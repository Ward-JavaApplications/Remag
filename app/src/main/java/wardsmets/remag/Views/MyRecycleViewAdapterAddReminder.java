package wardsmets.remag.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wardsmets.remag.AddReminderActivity;
import wardsmets.remag.R;

public class MyRecycleViewAdapterAddReminder extends RecyclerView.Adapter<MyRecycleViewAdapterAddReminder.MyViewHolder> {

    String[] times;
    Context context;
    AddReminderActivity parent;
    ArrayList<String> textViews = new ArrayList<>();
    int image = R.drawable.ic_baseline_delete_24;

    public MyRecycleViewAdapterAddReminder(String[] times, Context context, AddReminderActivity parent) {
        this.times = times;
        this.context = context;
        this.parent = parent;
    }

    public ArrayList<String> getTextViews() {
        return textViews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_reminder_add_time_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleViewAdapterAddReminder.MyViewHolder holder, int position) {
        holder.editText.setText(times[position]);
        holder.imageButton.setImageResource(image);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViews.remove(position);
                parent.reloadTimes(textViews.toArray(new String[0]));
            }
        });
    }

    @Override
    public int getItemCount() {
        return times.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText editText;
        ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.editText_selectTime);
            String time = editText.getText().toString();
            if(time.equals("")) textViews.add("00:00");
            else textViews.add(time);
            imageButton = itemView.findViewById(R.id.button_add_time_delete_time);
        }
    }
}
