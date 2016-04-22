package cs3714.rugburn.CustomObjects;

/**
 * Created by Jared on 4/22/2016.
 */
public class Exercise {

    String name;
    int sets, reps, weight, picture;

    public Exercise(String n) {
        name = n;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int s) {
        sets = s;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int r) {
        reps = r;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int w) {
        weight = w;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int p) {
        picture = p;
    }


}
