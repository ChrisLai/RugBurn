package cs3714.rugburn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs3714.rugburn.CustomObjects.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Button start, past, wod;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            System.out.println("BUNDLE BACK");
            user = bundle.getParcelable("USER");
        } else {
            System.out.println("NO BUNDLE");
            user = new User();
        }

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);
        past = (Button) findViewById(R.id.previous);
        past.setOnClickListener(this);
        wod = (Button) findViewById(R.id.wodButton);
        wod.setOnClickListener(this);

        if (user.getCurrentWorkout() != null &&
                !user.getCurrentWorkout().getFinished()) {
            start.setText("Continue Workout");
            System.out.println("SHOULD BE CONTINUE WORKOUT");
        } else {
            System.out.println(user.getCurrentWorkout());
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == start.getId()) {
            i = new Intent(this, CurrentWorkoutActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        } else if (v.getId() == past.getId()) {
            i = new Intent(this, PastWorkoutActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        } else if (v.getId() == wod.getId()) {
            i = new Intent(this, WorkoutOfDayActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        }
    }
}
