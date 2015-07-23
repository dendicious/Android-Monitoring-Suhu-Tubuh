package com.app.dendi_tshirt;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.graph.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity{
	
	EditText txt_username;
	EditText txt_password;
	TextView tv_login;
	
	Button btn_login ;
	private ProgressDialog LoginDialog;
	JSONParser jsonParser = new JSONParser();
	JSONObject json = null;
	JSONArray akunObj = null;
	
	String st_username;
	String st_password;
	
	String url_login = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		txt_username = (EditText) findViewById(R.id.username);
		txt_password = (EditText) findViewById(R.id.password);
		tv_login = (TextView) findViewById(R.id.tv_login);

		btn_login = (Button) findViewById(R.id.btn_login);
		
		Typeface roboto = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		
		txt_username.setTypeface(roboto);
		txt_password.setTypeface(roboto);
		btn_login.setTypeface(roboto);
		tv_login.setTypeface(roboto);
		
		btn_login.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				if(validation(txt_username) && validation(txt_password))
					new LoginActions().execute();
			}
		});
		
	}
	
	public boolean validation(EditText editText) {
		if(editText.getText().toString().trim().length() == 0){
			editText.setError("Harus diisi");
			return false;
		}
		else{
			editText.setError(null);
			return true;
			
		}
	}
	
	/**
	 * Background Async Task to Get complete product details
	 * */
	class LoginActions extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			LoginDialog = new ProgressDialog(Login.this);
			LoginDialog.setMessage("Proses login. Tunggu bentar...");
			LoginDialog.setIndeterminate(false);
			LoginDialog.setCancelable(true);
			LoginDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {
			
			
					int success;
					if (android.os.Build.VERSION.SDK_INT > 9) {
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
								.permitAll().build();
						StrictMode.setThreadPolicy(policy);
					}
					try {
//						List<NameValuePair> parameter = new ArrayList<NameValuePair>();
//						parameter.add(new BasicNameValuePair("username", txt_username.getText().toString()));
//						parameter.add(new BasicNameValuePair("password", txt_password.getText().toString()));
			
						// getting product details by making HTTP request
						// Note that product details url will use GET request
						
						JSONObject json = jsonParser.getJSONFromUrl("http://dendikim.16mb.com/auth/login.php?username="+txt_username.getText().toString()+"&password="+txt_password.getText().toString());
						
							
						// check your log for json response
						Log.d("Detail Akun", json.toString());
						
						success = json.getInt("sukses");
						
						JSONArray akunObj = json
								.getJSONArray("hasil"); // JSON Array
						
						// get first product object from JSON Array
						JSONObject detail_akun = akunObj.getJSONObject(0);
						if (success == 1) {
							// successfully received product details
								String nama_user = detail_akun.getString("nama");
							
								Intent i = new Intent(Login.this, MainActivity.class);
								i.putExtra("nama", nama_user);
								i.putExtra("username", txt_username.getText().toString());
								startActivity(i);						 	
						}else
						{
							Intent i = new Intent(Login.this, LoginGagal.class);
							startActivity(i);
						}
					} catch (JSONException e) {
						Intent i = new Intent(Login.this, LoginGagal.class);
						startActivity(i);
					}
				
			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			LoginDialog.dismiss();
		}
	}

}
