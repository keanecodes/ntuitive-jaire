package sg.ntuitive.jaire.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

import sg.ntuitive.jaire.R;
import sg.ntuitive.jaire.viewpager.CardItemModel;
import sg.ntuitive.jaire.viewpager.PagerAdapterModel;
import sg.ntuitive.jaire.viewpager.ShadowTransformer;

public class ModelActivity extends AppCompatActivity {


    RadarChart radarChart;
    String[] chartLabels = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        /****************** Back Button ***********************/
        ImageButton back = findViewById(R.id.previousActivity);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /****************** Tab Layout ***********************/
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Basic"));
        tabLayout.addTab(tabLayout.newTab().setText("Premium"));

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_ic_text_linear, null);
        tabOne.setText(" Premium");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_closed_outline, 0, 0, 0);
        tabOne.setPadding(50, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabOne);

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        ViewGroup vgTab = (ViewGroup) vg.getChildAt(1);
        vgTab.setEnabled(false);

        /****************** ViewPager ***********************/
        ViewPager modelPager = findViewById(R.id.modelPager);

        PagerAdapterModel mCardAdapter = new PagerAdapterModel(this);
        mCardAdapter.addCardItem(new CardItemModel(
                "Terry Watson",
                "Personal Trainer",
                "2/5",
                "9,432",
                new RadarData(initDataset(new RadarDataSet(new ArrayList<RadarEntry> (Arrays.asList(
                        new RadarEntry(2.5f),
                        new RadarEntry(2.6f),
                        new RadarEntry(2.1f),
                        new RadarEntry(2.3f),
                        new RadarEntry(2.8f),
                        new RadarEntry(2.7f)
                )), null)))
            )
        );

        mCardAdapter.addCardItem(new CardItemModel(
                "Alan Turing",
                "Average Joe",
                "1/5",
                "3,157",
                new RadarData(initDataset(new RadarDataSet(new ArrayList<RadarEntry> (Arrays.asList(
                        new RadarEntry(1.1f),
                        new RadarEntry(1.2f),
                        new RadarEntry(0.9f),
                        new RadarEntry(1.4f),
                        new RadarEntry(1.3f),
                        new RadarEntry(1.1f)
                )), null)))
            )
        );

        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(modelPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        modelPager.setAdapter(mCardAdapter);
        modelPager.setPageTransformer(false, mCardShadowTransformer);
        modelPager.setOffscreenPageLimit(3);
    }

    private RadarDataSet initDataset(RadarDataSet set) {
        set.setDrawFilled(true);
        set.setColor(ContextCompat.getColor(this, R.color.Jred));
        set.setFillColor(ContextCompat.getColor(this, R.color.Jred));
        set.setFillAlpha(255);
        set.setDrawValues(false);

        return set;
    }
}