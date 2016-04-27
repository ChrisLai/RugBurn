package cs3714.rugburn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import cs3714.rugburn.CustomObjects.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Button start, past, wod;
    TextView burn;
    Intent i;
    CommHandler commHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            System.out.println("BUNDLE BACK");
            //user = bundle.getParcelable("USER");
            user = new User();
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

        burn = (TextView)findViewById(R.id.feelBurn);

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        burn.postDelayed(new Runnable() {
                                public void run() {
                                    String first = "We Feel the ";
                                    String next = "<font color='#EE0000'>BURN</font>";
                                    burn.setText(Html.fromHtml(first + next));
                                }
                            },1000 );

        if (user.getCurrentWorkout() != null &&
                user.getCurrentWorkout().getExercises().size() > 0 &&
                !user.getCurrentWorkout().getFinished()) {
            start.setText("Continue Workout");
            System.out.println("SHOULD BE CONTINUE WORKOUT");
        } else {
            System.out.println(user.getCurrentWorkout());
        }

        commHandler = new CommHandler(this);
    }

    public void onWatchClick(){
        i = new Intent(this, CurrentWorkoutActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("USER", user);
        i.putExtras(b);
        startActivity(i);
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
