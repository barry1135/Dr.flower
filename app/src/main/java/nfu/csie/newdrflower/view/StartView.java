package nfu.csie.newdrflower.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import nfu.csie.newdrflower.R;


/**
 * Created by barry on 2017/5/8.
 */

public class StartView extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);

        ImageView EnterButton = (ImageView)findViewById(R.id.enter);
        ImageView ExitButton = (ImageView)findViewById(R.id.exit);
        ImageView DataButton = (ImageView)findViewById(R.id.data);

        EnterButton.setOnClickListener(EnterListener);
        ExitButton.setOnClickListener(ExitListener);
        DataButton.setOnClickListener(DataListener);


    }


    private ImageView.OnClickListener EnterListener = new ImageView.OnClickListener(){
        @Override
        public void onClick(View v){

        }
    };

    private ImageView.OnClickListener ExitListener = new ImageView.OnClickListener(){
       public void onClick(View v){
            finish();
      }
    };

    private ImageView.OnClickListener DataListener = new ImageView.OnClickListener(){
        public void onClick(View v){

        }
    };

}
