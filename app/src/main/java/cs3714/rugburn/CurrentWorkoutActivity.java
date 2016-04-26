package cs3714.rugburn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cs3714.rugburn.CustomObjects.User;
import cs3714.rugburn.CustomObjects.Workout;
import cs3714.rugburn.CustomObjects.WorkoutListAdapter;

/**
 * Created by Jared on 4/22/2016.
 */
public class CurrentWorkoutActivity extends AppCompatActivity implements View.OnClickListener{

    User user;
    Workout workout;
    WorkoutListAdapter mAdapter;
    ArrayList<String> motivationalQuotes;

    Button finish,add;
    ListView list;
    TextView date_text;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_workout);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Date date = new Date();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        if (user.getCurrentWorkout() != null &&
                !user.getCurrentWorkout().getFinished()) {
            //don't have to make a new workout object because the current workout was never finished
            workout = user.getCurrentWorkout();
        } else {
            workout = new Workout(date);
            user.addWorkout(workout);
        }

        finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        date_text = (TextView) findViewById(R.id.date);
        date_text.setText(date.toString().substring(0, 10));

        if (workout.getExercises().size() == 0) {
            add.setText("Start Workout");
        } else {
            add.setText("Add Exercise");
            System.out.println("WORKOUT IS: " + workout.getExercises().get(0).getName());
        }

        motivationalQuotes = new ArrayList<String>(){{
            add("You Can Do It!");
            add("Never Give up!");
            add("Get Big!");
            add("No Pain, No Gain!");
            add("Push Yourself!");

        }};
        Toast.makeText(this, getRandomQuote(),
                Toast.LENGTH_SHORT).show();

        list = (ListView) findViewById(R.id.list);
        mAdapter = new WorkoutListAdapter(this, user.getCurrentWorkout().getExercises());
        list.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == finish.getId()) {
            workout.setFinished(true);
            Toast.makeText(this, "Nice Workout!",
                    Toast.LENGTH_SHORT).show();
            i = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        } else if (v.getId() == add.getId()) {

            //TODO won't go straight to exercise activity -- needs pop up dialog w/ qr option
            try {
                //QR intent
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
                startActivityForResult(intent, 0);

            } catch (Exception e) {
                //This will send user to android market (to download QR app)
                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);
            }



//            i = new Intent(this, ExerciseActivity.class);
//            Bundle b = new Bundle();
//            b.putParcelable("USER", user);
//            i.putExtras(b);
//            startActivity(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                //RESULT from QR code is a string
                String contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(this.getBaseContext(),contents,
                        Toast.LENGTH_SHORT).show();
                i = new Intent(this, ExerciseActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("USER", user);
                i.putExtra("WORKOUT",contents);
                i.putExtras(b);
                startActivity(i);

            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }


    public String getRandomQuote() {
        Random r = new Random();
        return motivationalQuotes.get(r.nextInt(5));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundleForBack = new Bundle();
                bundleForBack.putParcelable("USER", user);
                intent.putExtras(bundleForBack);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
