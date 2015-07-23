package com.app.dendi_tshirt;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.graph.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.view.Window;
import android.widget.LinearLayout;

public class GrafikHarian extends Activity {

	private static String url_grafik_hari_ini = "";

	JSONParser jParser;
	JSONObject json = null;
	JSONObject obj_kiri = null;
	JSONObject obj_kanan = null;
	JSONArray json_kiri = null;
	JSONArray json_kanan = null;

	String sukses;
	String suhu_kiri;
	String suhu_kanan;
	String username;
	String nama;
	String tanggal;

	int size = 0;
	double dbl_suhu_kiri = 0;
	double dbl_suhu_kanan = 0;

	private GraphView graphView;
	LinearLayout layout;
	
	Date dateObj;
	SimpleDateFormat curFormater;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grafik_harian);
		

		Intent in = getIntent();
		username = in.getStringExtra("username");
		nama = in.getStringExtra("nama");
		tanggal = in.getStringExtra("tanggal");
		
		url_grafik_hari_ini = "http://dendikim.16mb.com/"+username+"/grafik_hari_ini.php?tanggal="+tanggal.toString().trim();
 
		graphView = new LineGraphView(this, "Grafik " + nama + " tanggal " + tanggal.toString().trim());
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		try {
			jParser = new JSONParser();
			json = jParser.getJSONFromUrl(url_grafik_hari_ini);
			json_kiri = json.getJSONArray("suhu_kiri");
			json_kanan = json.getJSONArray("suhu_kanan");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		size = json_kiri.length();

		GraphView.GraphViewData[] arr_kiri = new GraphView.GraphViewData[size];
		GraphView.GraphViewData[] arr_kanan = new GraphView.GraphViewData[size];
		GraphView.GraphViewData gvd_kiri = null;
		GraphView.GraphViewData gvd_kanan = null;
		for (int i = 0; i < size; i++) {
			try {
				obj_kiri = json_kiri.getJSONObject(i);
				obj_kanan = json_kanan.getJSONObject(i);
				suhu_kiri = obj_kiri.getString("suhu_kiri");
				suhu_kanan = obj_kanan.getString("suhu_kanan");

				gvd_kiri = new GraphView.GraphViewData(i,
						Double.parseDouble(suhu_kiri));
				gvd_kanan = new GraphView.GraphViewData(i,
						Double.parseDouble(suhu_kanan));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arr_kiri[i] = gvd_kiri;
			arr_kanan[i] = gvd_kanan;
		}
		graphView.addSeries(new GraphViewSeries("Kiri",
				new GraphViewSeriesStyle(Color.RED, 3), arr_kiri));
		graphView.addSeries(new GraphViewSeries("Kanan",
				new GraphViewSeriesStyle(Color.BLUE, 3), arr_kanan));

		graphView.setViewPort(2, 40);
		graphView.setScalable(true);

		graphView.getGraphViewStyle().setNumHorizontalLabels(20);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);

		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.setGraphViewStyle(new GraphViewStyle(Color.DKGRAY,
				Color.DKGRAY, Color.LTGRAY));

		//graphView.redrawAll();

		layout = (LinearLayout) findViewById(R.id.grafik);
		layout.addView(graphView);

	}
}
