package wardsmets.remag;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wardsmets.remag.RecyclerView.MyRecyclerViewAdapter;

public class AddReminderActivity extends AppCompatActivity{

    private ArrayList<String> times;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        times = new ArrayList<>();
        times.add("morgen");
        times.add("gister");
        setUpRecycleView();
    }

    private void setUpRecycleView(){
        try {
            RecyclerView recyclerView = findViewById(R.id.recycler_view_times);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            MyRecyclerViewAdapter recyclerViewAdapter = new MyRecyclerViewAdapter(times);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
