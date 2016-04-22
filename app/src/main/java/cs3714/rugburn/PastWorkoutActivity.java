package cs3714.rugburn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import cs3714.rugburn.CustomObjects.User;

/**
 * Created by Jared on 4/22/2016.
 */
public class PastWorkoutActivity extends AppCompatActivity implements View.OnClickListener{

    User user;

    Button finish;
    TextView quote;
    ListView list;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }
        //TODO: get motivational quotes
        quote = (TextView)findViewById(R.id.motivational);
        list = (ListView)findViewById(R.id.listPrev);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == finish.getId()) {
            i = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("USER", user);
            i.putExtras(b);
            startActivity(i);
        }
    }
}
