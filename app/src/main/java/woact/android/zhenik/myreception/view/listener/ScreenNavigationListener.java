package woact.android.zhenik.myreception.view.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.view.activity.information.InformationActivity;

public class ScreenNavigationListener implements View.OnClickListener {

    private Context context;
    public ScreenNavigationListener(Context context){
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.screen_navigation_information:
                Intent intent = new Intent(context, InformationActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}
