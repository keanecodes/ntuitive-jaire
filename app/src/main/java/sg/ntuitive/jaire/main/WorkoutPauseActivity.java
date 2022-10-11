package sg.ntuitive.jaire.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import sg.ntuitive.jaire.R;

public class WorkoutPauseActivity extends AppCompatActivity {

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_pause);

        Button resumeBtn = findViewById(R.id.btn_workout_resume);

        context = getApplicationContext();

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WorkoutActivity.class));
            }
        });
    }
}
