package com.app.dendi_tshirt;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.graph.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.widget.TextView;

public class SuhuRealtime extends Activity {

	TextView tv_suhu_kiri;
	TextView tv_suhu_kanan;
	TextView tv_nama;
	TextView tv_reload;

	String username;
	String nama;

	String suhu_kiri;
	String suhu_kanan;
	String sukses;
	String waktu;

	double dbl_suhu_kiri;
	double dbl_suhu_kanan;

	String url_suhu_terakhir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.suhu_realtime);
		

		tv_suhu_kiri = (TextView) findViewById(R.id.tv_suhu_kiri);
		tv_suhu_kanan = (TextView) findViewById(R.id.tv_suhu_kanan);
		tv_nama = (TextView) findViewById(R.id.tv_nama);
		tv_reload = (TextView) findViewById(R.id.tv_reload);

		Intent in = getIntent();
		username = in.getStringExtra("username");
		nama = in.getStringExtra("nama");

		tv_nama.setText("Suhu Tubuh " + nama);

		Typeface roboto = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");

		tv_suhu_kiri.setTypeface(roboto);
		tv_suhu_kanan.setTypeface(roboto);
		tv_nama.setTypeface(roboto);
		tv_reload.setTypeface(roboto);
		
		url_suhu_terakhir = "http://dendikim.16mb.com/"+username+"/suhu_terakhir.php";

//		(new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				while (!Thread.interrupted())
//					try {
//						Thread.sleep(10000);
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
								tv_suhu_kiri.setText(getSuhuKiri());
								tv_suhu_kanan.setText(getSuhuKanan());
								tv_reload.setText(getWaktu());

								dbl_suhu_kiri = Double.parseDouble(suhu_kiri);
								dbl_suhu_kanan = Double.parseDouble(suhu_kanan);

								if (dbl_suhu_kiri < 30)
									tv_suhu_kiri.setTextColor(Color.BLUE);
								else if (dbl_suhu_kiri >= 30
										&& 37 > dbl_suhu_kiri)
									tv_suhu_kiri.setTextColor(Color.YELLOW);
								else
									tv_suhu_kiri.setTextColor(Color.RED);

								if (dbl_suhu_kanan < 30)
									tv_suhu_kanan.setTextColor(Color.CYAN);
								else if (dbl_suhu_kanan >= 30
										&& 37 > dbl_suhu_kanan)
									tv_suhu_kanan.setTextColor(Color.YELLOW);
								else
									tv_suhu_kanan.setTextColor(Color.RED);
//							}
//						});
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//			}
//		})).start();
	}

	public String getSuhuKiri() {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		try {

			JSONParser jParser = new JSONParser();

			JSONObject json = jParser.getJSONFromUrl(url_suhu_terakhir);

			sukses = json.getString("sukses");
			JSONArray cl = json.getJSONArray("hasil");

			if (sukses.equals("1")) {

				JSONObject c = cl.getJSONObject(0);
				suhu_kiri = c.getString("suhu_kiri").toString().trim();

			} else {
				suhu_kiri = "";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suhu_kiri;
	}

	public String getSuhuKanan() {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		try {

			JSONParser jParser = new JSONParser();

			JSONObject json = jParser.getJSONFromUrl(url_suhu_terakhir);

			sukses = json.getString("sukses");
			JSONArray cl = json.getJSONArray("hasil");

			if (sukses.equals("1")) {

				JSONObject c = cl.getJSONObject(0);
				suhu_kanan = c.getString("suhu_kanan").toString().trim();

			} else {
				suhu_kanan = "";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suhu_kanan;
	}
	
	public String getWaktu() {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		try {

			JSONParser jParser = new JSONParser();

			JSONObject json = jParser.getJSONFromUrl(url_suhu_terakhir);

			sukses = json.getString("sukses");
			JSONArray cl = json.getJSONArray("hasil");

			if (sukses.equals("1")) {

				JSONObject c = cl.getJSONObject(0);
				waktu = c.getString("waktu").toString().trim();

			} else {
				waktu = "";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return waktu;
	}

}
