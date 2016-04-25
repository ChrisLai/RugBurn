package cs3714.rugburn.CustomObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jared on 4/22/2016.
 */
public class User implements Parcelable{

    ArrayList<Workout> workouts;
    ArrayList<String> motivationalQuotes;

    public User() {

        workouts = new ArrayList<Workout>();

    }

    public void addWorkout(Workout w) {
        workouts.add(w);
    }

    public Workout getCurrentWorkout() {
        if (workouts.size() > 0) {
            return workouts.get(workouts.size() - 1); //gets the most recent workout
        } else {
            return null; //bc there's no current workout if workout list is empty
        }
    }

    public Workout getLastWorkout() {
        if (workouts.size() > 1) {
            return workouts.get(workouts.size() - 2); //gets the last workout
        } else {
            return null; //bc there's no past workout if list is smaller than 2
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * determines what to write out for use as a parcel. Required parcelable
     * method
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        // need to be serialized b/c they're custom classes
        out.writeSerializable(workouts);
    }

    /**
     * determine what to write in when used as a parcel. Required parcelable
     * method
     */
    @SuppressWarnings("unchecked")
    private User(Parcel in) {
        // need to be serialized b/c they're custom classes
        workouts = (ArrayList<Workout>) in.readSerializable();
    }


    /**
     * Creator for the parcelable object. Required parcelable method
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
