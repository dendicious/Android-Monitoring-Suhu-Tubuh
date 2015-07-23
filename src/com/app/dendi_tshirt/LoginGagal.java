package com.app.dendi_tshirt;


import com.app.graph.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LoginGagal extends Activity{
	
	Button btn_kembali;
	TextView tv_gagal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gagal);
		
		btn_kembali = (Button)findViewById(R.id.btn_kembali);
		tv_gagal = (TextView)findViewById(R.id.tv_gagals);
		
		Typeface roboto = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		
		tv_gagal.setTypeface(roboto);
		
		btn_kembali.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				Intent inten = new Intent(LoginGagal.this, Login.class);
				startActivity(inten);
 
			}
 
		});
	}

}
