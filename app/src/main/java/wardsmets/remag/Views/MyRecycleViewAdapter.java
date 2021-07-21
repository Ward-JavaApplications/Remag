package wardsmets.remag.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import wardsmets.remag.R;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    String[] times;
    Context context;

    public MyRecycleViewAdapter(String[] times, Context context) {
        this.times = times;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_reminder_add_time_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.editText.setText(times[position]);
    }

    @Override
    public int getItemCount() {
        return times.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText editText;

        public MyViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.editText_selectTime);
        }
    }
}
