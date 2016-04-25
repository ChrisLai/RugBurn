package cs3714.rugburn.CustomObjects;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cs3714.rugburn.R;

/**
 * Adapter for our custom listView items.
 *
 * @author jared
 * @date 4/24/15
 */
public class WorkoutListAdapter extends BaseAdapter {

    ArrayList<Exercise> workout_list;
    Context context;
    LayoutInflater inflater;
    Exercise exercise;

    public WorkoutListAdapter(Context context, ArrayList<Exercise> list) {
        workout_list = list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return workout_list.size();
    }

    @Override
    public Exercise getItem(int position) {
        return workout_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.listitemexercise, parent,
                    false);

            holder.exercise_name = (TextView) convertView.findViewById(R.id.exercise_name);
            holder.sets_num = (TextView) convertView.findViewById(R.id.sets_num);
            holder.reps_num = (TextView) convertView.findViewById(R.id.reps_num);
            holder.weight_num = (TextView) convertView.findViewById(R.id.weight_num);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        exercise = workout_list.get(position);
        holder.exercise_name.setText(exercise.getName());
        holder.sets_num.setText(exercise.getSets() + "");
        holder.reps_num.setText(exercise.getReps() + "");
        holder.weight_num.setText(exercise.getWeight() + "");


        return convertView;
    }

    static class ViewHolder {
        TextView exercise_name;
        TextView sets_num;
        TextView reps_num;
        TextView weight_num;
        int position;
    }


}