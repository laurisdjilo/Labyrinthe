package sample;

import java.util.ArrayList;

/**
 * Created by Lauris on 07/11/2017.
 */
public class State {
    private double x;
    private double y;
    private double heurisitc;
    private double coutg;
    private double coutf;
    ArrayList<State> listFils;
    private boolean finalState;
    private State father;

    public State(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public State() {

    }

    //ce constructeur est pour l'état initial car celui ci pour le construire faut avoir tous ces paramètres
    public State(double x,double y,double heurisitc, double coutg, double coutf,boolean finalState) {
        this.x=x;
        this.y=y;
        this.heurisitc = heurisitc;
        this.coutg = coutg;
        this.coutf = coutf;
        //this.listFils = listFils;
        this.finalState=finalState;
    }

    /*ce constructeur est pour tout état quelconque distinct de l'initial car lors de la création d'un état on a juste besoin
    de connaitre son heuristique est ses successeurs*/
    public State(double x,double y,double heurisitc,boolean finalState) {
        this.x=x;
        this.y=y;
        this.heurisitc = heurisitc;
        //this.listFils = listFils;
        this.finalState=finalState;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public State getFather() {
        return father;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }

    public double getCoutf() {
        return coutf;
    }

    public void setCoutf(double coutf) {
        this.coutf = coutf;
    }

    public double getHeurisitc() {
        return heurisitc;
    }

    public void setHeurisitc(double heurisitc) {
        this.heurisitc = heurisitc;
    }

    public double getCoutg() {
        return coutg;
    }

    public void setCoutg(double coutg) {
        this.coutg = coutg;
    }

    public ArrayList<State> getListFils() {
        return listFils;
    }

    public void setListFils(ArrayList<State> listFils) {
        this.listFils = listFils;
    }

    public boolean equals(State state){
        if(this.x==state.getX()&&this.y==state.getY())
            return true;
        else
            return false;
    }

    public String toString(){
        return "("+this.x+","+this.y+","+this.finalState+","+this.heurisitc+")";
    }
}
