package wardsmets.remag.Views;

import android.widget.ViewFlipper;

public class MyViewFlipper {
    ViewFlipper viewFlipper;
    int currentView = 0;
    public MyViewFlipper(ViewFlipper viewFlipper){
        this.viewFlipper = viewFlipper;
        currentView = 0;
    }
    public void setView(int position){
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
