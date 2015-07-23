package com.app.dendi_tshirt;

import java.util.Calendar;

import org.json.JSONArray;

import com.app.graph.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText txt_ip;
	TextView tv_nama;
	TextView selamat_datang;

	Button btn_realtime;
	Button btn_grafik;
	Button btn_tentang;
	Button btn_keluar;

	TextView credit;

	JSONParser jsonParser = new JSONParser();
	JSONArray suhuArray = null;
	StringBuilder build_tanggal;

	double double_suhu;
	boolean status = false;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 999;

	String suhu_kiri;
	String suhu_kanan;
	String sukses;
	String nama;
	String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		
		// pengenalan objek di file xml

		//txt_ip = (EditText) findViewById(R.id.txt_ip);
		
		Intent in = getIntent();
		nama = in.getStringExtra("nama");
		username = in.getStringExtra("username");
		
		
		
		tv_nama = (TextView) findViewById(R.id.tv_nama_user);
		selamat_datang = (TextView) findViewById(R.id.tv_selamat_datang);

		btn_realtime = (Button) findViewById(R.id.btn_realtime);
		btn_grafik = (Button) findViewById(R.id.btn_grafik);
		btn_tentang = (Button) findViewById(R.id.btn_tentang);
		btn_keluar = (Button) findViewById(R.id.btn_keluar);

		credit = (TextView) findViewById(R.id.credit);

		// mengubah font setiap objek
		Typeface roboto = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		
		tv_nama.setText(nama);

		selamat_datang.setTypeface(roboto);
		tv_nama.setTypeface(roboto);
		btn_grafik.setTypeface(roboto);
		btn_realtime.setTypeface(roboto);
		btn_tentang.setTypeface(roboto);
		btn_keluar.setTypeface(roboto);
		credit.setTypeface(roboto);

		// aksi tombol

		btn_grafik.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				showDialog(DATE_DIALOG_ID);

			}

		});

		btn_realtime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(MainActivity.this, SuhuRealtime.class);
				//i.putExtra("ip", txt_ip.getText().toString().trim());
				i.putExtra("nama", nama);
				i.putExtra("username", username);
				startActivity(i);

			}

		});

		btn_tentang.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(MainActivity.this,
						TentangPengembang.class);
				i.putExtra("nama", nama);
				i.putExtra("username", username);
				startActivity(i);
			}

		});

		btn_keluar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ExitConfirm();

			}

		});
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

	}

	@Override
	public Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			build_tanggal = new StringBuilder().append(year).append("-")
					.append(month + 1).append("-").append(day).append(" ");

			Intent i = new Intent(MainActivity.this, GrafikHarian.class);
			//i.putExtra("ip", txt_ip.getText().toString().trim());
			i.putExtra("nama", nama);
			i.putExtra("username", username);
			i.putExtra("tanggal", build_tanggal.toString().trim());
			startActivity(i);
		}
	};

	public void ExitConfirm() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Apakah anda yakin ? ")
				.setCancelable(false)
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						Intent i = new Intent(MainActivity.this, Login.class);
						startActivity(i);
					}
				})
				.setNegativeButton("Batal",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
