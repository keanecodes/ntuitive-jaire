package sg.ntuitive.jaire.viewpager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import sg.ntuitive.jaire.main.MainActivity;
import sg.ntuitive.jaire.main.ModelActivity;
import sg.ntuitive.jaire.R;

public class PagerAdapterExercise extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItemExercise> mData;
    private float mBaseElevation;
    private Context context;

    public PagerAdapterExercise(MainActivity context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.context = context;
    }

    public void addCardItem(CardItemExercise item) {
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
                .inflate(R.layout.card_exercise, container, false);

        bind(mData.get(position), view);
        container.addView(view, 0);
        CardView cardView = (CardView) view.findViewById(R.id.cardViewExercise);

        if (mBaseElevation == 0) mBaseElevation = cardView.getCardElevation();

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        view.setOnClickListener(new ViewPager.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , ModelActivity.class);
                context.startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
        mViews.set(position, null);
    }

    private void bind(CardItemExercise item, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView trackType = (TextView) view.findViewById(R.id.trackType);
        TextView exerciseName = (TextView) view.findViewById(R.id.exerciseName);
        TextView equipment = (TextView) view.findViewById(R.id.equipment);

        imageView.setImageResource(item.getImage());
        trackType.setText(item.getTrackType());
        exerciseName.setText(item.getExcerciseName());
        equipment.setText(item.getEquipment());
    }

}
