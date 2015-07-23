package com.app.dendi_tshirt;

import com.app.graph.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
public class SplashScreen extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
         Handler handler=new Handler();
            handler.postDelayed(new Runnable()
            {              
                @Override
                public void run()
                {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);                
                }
            }, 3000);
    }

}
