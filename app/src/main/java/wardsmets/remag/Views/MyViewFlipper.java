package wardsmets.remag.Views;

import android.util.Log;
import android.widget.ViewFlipper;

public class MyViewFlipper {
    ViewFlipper viewFlipper;
    int currentView = 0;
    public MyViewFlipper(ViewFlipper viewFlipper){
        this.viewFlipper = viewFlipper;
        currentView = 0;
    }
    public void setView(int position){
        //Log.v("markel", "Setting view to position " + String.valueOf(position));
        while(position!= currentView){
            if(position>currentView){
                viewFlipper.showNext();
                currentView ++;
            }
            else{
                viewFlipper.showPrevious();
                currentView --;
            }
        }
    }


}
