package wardsmets.remag.Views;

import android.content.Context;
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
    ArrayList<TextView> textViews = new ArrayList<TextView>();
    int image = R.drawable.ic_baseline_delete_24;

    public MyRecycleViewAdapterAddReminder(String[] times, Context context, AddReminderActivity parent) {
        this.times = times;
        this.context = context;
        this.parent = parent;
    }


    public ArrayList<TextView> getTextViews() {
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
                parent.reloadTimes(textViews);
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
            editText = itemView.findViewById(R.id.editText_add_reminder_add_time_row_editTime);
            textViews.add(editText);
            imageButton = itemView.findViewById(R.id.button_add_time_delete_time);
        }
    }
}
