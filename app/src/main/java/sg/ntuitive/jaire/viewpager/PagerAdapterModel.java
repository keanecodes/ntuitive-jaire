package sg.ntuitive.jaire.viewpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import sg.ntuitive.jaire.main.WorkoutActivity;
import sg.ntuitive.jaire.R;

public class PagerAdapterModel extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItemModel> mData;
    private float mBaseElevation;
    private Context context;

    public PagerAdapterModel(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.context = context;
    }

    public void addCardItem(CardItemModel item) {
        mViews.add(null);
        mData.add(item);
    }


    @Override
    public float getBaseElevation() { return mBaseElevation; }

    @Override
    public CardView getCardViewAt(int position) { return mViews.get(position); }

    @Override
    public int getCount() { return mData.size(); }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) { return view == object; }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_model, container, false);

        bind(mData.get(position), view);
        container.addView(view, 0);
        CardView cardView = (CardView) view.findViewById(R.id.cardViewModel);

        if (mBaseElevation == 0) mBaseElevation = cardView.getCardElevation();

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
        mViews.set(position, null);
    }

    private void bind(CardItemModel item, View view) {
        // ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView modelName = view.findViewById(R.id.modelName);
        TextView modelType = view.findViewById(R.id.modelType);
        TextView level = view.findViewById(R.id.level);
        TextView session = view.findViewById(R.id.session);
        RadarChart radarChart =  view.findViewById(R.id.chartModel);
        Button btnWorkout = view.findViewById(R.id.btn_workout);

        //imageView.setImageResource(item.getImage());
        modelName.setText(item.getModelName());
        modelType.setText(item.getModelType());
        level.setText(item.getLevel());
        session.setText(item.getSession());

        /****************** Radar Chart ***********************/
        XAxis x = radarChart.getXAxis();
        YAxis y = radarChart.getYAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(new String[] {"Deltoid: Lat Up", "Rear Up", "Rear Down", "Lat Down", "Front Down", "Front Up"}));
        x.setTextColor(ContextCompat.getColor(context, R.color.JlightGrey));
        x.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        y.setAxisMaximum(5);
        y.setAxisMinimum(0);
        y.setLabelCount(6, true);
        y.setTextColor(ContextCompat.getColor(context, R.color.JlighterGrey));

        radarChart.getLegend().setEnabled(false);
        radarChart.setWebColor(ContextCompat.getColor(context, R.color.JlightGrey));
        radarChart.setWebColorInner(ContextCompat.getColor(context, R.color.JlightGrey));
        radarChart.getDescription().setEnabled(false);
        radarChart.setTouchEnabled(false);
        radarChart.setData(item.getRadarData());
        radarChart.invalidate();

        /****************** Workout Btn ***********************/
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(context , WorkoutActivity.class);
                 context.startActivity(i);
            }
        });

    }
}
