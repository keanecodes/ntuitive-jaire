package sg.ntuitive.jaire.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import sg.ntuitive.jaire.R;

public class WorkoutActivity extends AppCompatActivity {

    private Context context;

    private TextView feed;
    private RadarData data;
    private CountDownTimer timer;
    private Random r = new Random();

    float[] modelForm = {2.5f, 2.6f, 2.1f, 2.8f, 2.3f, 2.7f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        final Button pauseBtn = findViewById(R.id.btn_workout_pause);
        final Button doneBtn = findViewById(R.id.btn_workout_done);
        feed = findViewById(R.id.label_workout_feed);
        final RadarChart radarChart = findViewById(R.id.chartWorkout);
        initRadarChart(radarChart);

        context = getApplicationContext();

        /****************** Radar Data ***********************/
        data = new RadarData();

        ArrayList<RadarEntry> user = new ArrayList<>();
        ArrayList<RadarEntry> upper = new ArrayList<>();
        ArrayList<RadarEntry> model = new ArrayList<>();
        ArrayList<RadarEntry> lower = new ArrayList<>();

        for(int i = 0; i < modelForm.length; i++) {
            upper.add(new RadarEntry(5));
            model.add(new RadarEntry(modelForm[i] + 1));
            lower.add(new RadarEntry(modelForm[i] - 1));
            user.add(new RadarEntry(0));
        }

        final RadarDataSet userSet = initDataset("user", new RadarDataSet(user, "You"));

        data.addDataSet(initDataset("limit", new RadarDataSet(upper, "Upper Limit")));
        data.addDataSet(initDataset("model", new RadarDataSet(model, "Model Range")));
        data.addDataSet(initDataset("limit", new RadarDataSet(lower, "Lower Limit")));
        data.addDataSet(userSet);

        radarChart.setData(data);

        /****************** Data Stream ***********************/

        timer = new CountDownTimer(3000, 20) {

            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                try{
                    //Toast.makeText(getApplicationContext(), "boopboop", Toast.LENGTH_SHORT).show();
                    userSet.clear();
                    randomDataset(userSet);
                    radarChart.notifyDataSetChanged();
                    radarChart.invalidate();
                    timer.start();
                } catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();

        /****************** Buttons ***********************/
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(context , WorkoutPauseActivity.class));
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(context , WorkoutSummaryActivity.class));
            }
        });
    }

    private RadarDataSet randomDataset(RadarDataSet set) {
        int initScore = 6;
        String msg = "";
        for (int i = 0; i < 6; i++) {
            float rdm = 2f + r.nextFloat() * (2f - 0.3f);
            if (rdm < (modelForm[i] - 1) || rdm > (modelForm[i] + 1)) initScore--;
            set.addEntry(new RadarEntry(rdm));
        }

        switch(initScore) {
            case 6: msg = "PERFECT"; break;
            case 5:
            case 4:
                msg = "GREAT"; break;
            case 3:
                msg = "COOL"; break;
            case 2:
            case 1:
                msg = "BAD"; break;
            default:
                msg = "";
        }

        feed.setText(msg);

        return set;
    }

    private RadarDataSet initDataset(String type, RadarDataSet set) {
        set.setDrawFilled(true);
        set.setDrawValues(false);

        switch (type.toLowerCase()) {
            case "user":
                set.setFillAlpha(150);
                set.setColor(ContextCompat.getColor(this, R.color.Jyellow));
                set.setFillColor(ContextCompat.getColor(this, R.color.Jyellow));
                break;
            case "limit":
                set.setFillAlpha(255);
                set.setColor(ActivityCompat.getColor(this, R.color.JlightGrey));
                set.setFillColor(ActivityCompat.getColor(this, R.color.JlightGrey));
                break;
            case "model":
                set.setFillAlpha(255);
                set.setColor(ActivityCompat.getColor(this, R.color.JdarkerGrey));
                set.setFillColor(ActivityCompat.getColor(this, R.color.JdarkerGrey));
                break;
            default:
        }

        return set;
    }

    private RadarChart initRadarChart(RadarChart radarChart) {
        XAxis x = radarChart.getXAxis();
        YAxis y = radarChart.getYAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(new String[] {"Lat Up", "Rear Up", "Rear Down", "Lat Down", "Front Down", "Front Up"}));
        x.setTextColor(ActivityCompat.getColor(this, R.color.JlightGrey));
        x.setTextSize(14);
        x.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        y.setAxisMaximum(5);
        y.setAxisMinimum(0);
        y.setLabelCount(6, true);
        y.setTextColor(ActivityCompat.getColor(this, R.color.JlighterGrey));

        radarChart.getLegend().setEnabled(false);
        radarChart.setWebColor(ActivityCompat.getColor(this, R.color.JlightGrey));
        radarChart.setWebColorInner(ActivityCompat.getColor(this, R.color.JlightGrey));
        radarChart.getDescription().setEnabled(false);
        radarChart.setTouchEnabled(false);

        return radarChart;
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.start();
    }
}