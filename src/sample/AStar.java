package sample;

import java.util.ArrayList;

/**
 * Created by Lauris on 10/11/2017.
 */
public class AStar {

    public State firstState;
    public ArrayList<State> allStates,finalStates,OPEN,CLOSED,chemin;


    public void aStar() throws NotPossibleException{
        State minFStateOpen=new State();
        OPEN=new ArrayList<State>();
        CLOSED=new ArrayList<State>();
        OPEN.add(firstState);
        while (true){
            if (OPEN.isEmpty()){
                throw new NotPossibleException("chemin non existant");
            }
            else{
                minFStateOpen=OPEN.remove(minFOpen());
                CLOSED.add(minFStateOpen);
                if(minFStateOpen.isFinalState()){
                    chemin=new ArrayList<State>();
                    State temp=minFStateOpen;
                    while(!temp.equals(firstState)){
                        chemin.add(temp);
                        temp=temp.getFather();
                    }
                    chemin.add(temp);
                    return;
                }
                else{
                    if (minFStateOpen.getListFils().isEmpty()){
                        //ici on va rentrer au début de la boucle
                    }
                    else{
                        ArrayList<State> listeEnfants=minFStateOpen.getListFils();
                        //on calcul f pour chacun des successeurs de minFStateOpen
                        for(int i=0;i<listeEnfants.size();i++){
                            double newCoutG=minFStateOpen.getCoutg()+1.0;
                            double newCoutF=listeEnfants.get(i).getHeurisitc()+newCoutG;
                            if(!contains(OPEN,listeEnfants.get(i))&&!contains(CLOSED,listeEnfants.get(i))){
                                listeEnfants.get(i).setCoutg(newCoutG);
                                listeEnfants.get(i).setCoutf(newCoutF);
                                listeEnfants.get(i).setFather(minFStateOpen);
                                OPEN.add(listeEnfants.get(i));
                            }
                            else if (OPEN.contains(listeEnfants.get(i))){
                                if (listeEnfants.get(i).getCoutf()>newCoutF){
                                    listeEnfants.get(i).setCoutg(newCoutG);
                                    listeEnfants.get(i).setCoutf(newCoutF);
                                }
                            }
                            else{//donc il est dans CLOSED
                                if (listeEnfants.get(i).getCoutf()>newCoutF){
                                    listeEnfants.get(i).setCoutg(newCoutG);
                                    listeEnfants.get(i).setCoutf(newCoutF);
                                    OPEN.add(listeEnfants.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * cette méthode permet de savoir si un état est dans une liste ou pas
     * @param listStates
     * @param state
     * @return
     */
    public boolean contains(ArrayList<State> listStates,State state){
        for (State temp:listStates) {
            if (temp.equals(state))
                return true;
        }
        return false;
    }

    /**
     * cette méthode renvoi l'élément de la liste OPEN qui a le plus petit f
     */
    public int minFOpen(){
        int minState;
        minState=0;
        for (int k=1;k<OPEN.size();k++) {
            if (OPEN.get(k).getCoutf()<OPEN.get(minState).getCoutf())
                minState++;
        }
        return minState;
    }

    /**
     * cette méthode permet de remplir la liste allStates d'initialiser l'état initial et la liste finalstates des états finaux;
     * ceci sans toute fois remplir la liste des fils pour chaque etat
     *
     *
     * les caractères m seront considéres comme les murs, le caracartère i comme état initial,
     * le caractère $ comme état final et le caractère e
     * comme état simple
     * @param listChar
     */
    public void initializeStates(String[][] listChar){
        finalStates=new ArrayList<State>();
        allStates=new ArrayList<State>();
        int nbreColonnes=listChar[0].length;
        int nbreLignes=listChar.length;

        /**cette boucle est pour remplir la liste finalstates
         * on remplit d'abord finalstates par ce que tous les états finaux ont pour h 0. et le h de tous les autres états se calcul
         * à partir des coordonnées des états finaux
         */
        State temp;
        for(int i=0;i<nbreLignes;i++){
            for(int j=0;j<nbreColonnes;j++){
                switch (listChar[i][j]){
                    case "$":
                        temp=new State(i,j,0,true);
                        //temp.setListFils(chercherFils(i,j,listChar));
                        finalStates.add(temp);
                        allStates.add(temp);
                        break;
                }

            }
        }
        for(int i=0;i<nbreLignes;i++){
            for(int j=0;j<nbreColonnes;j++){
                switch (listChar[i][j]){
                    case "i":
                        //on cherche d'abord la liste de ses enfants
                        firstState=new State(i,j,calculHeuristic(i,j,finalStates),0,calculHeuristic(i,j,finalStates),false);
                        //firstState.setListFils(chercherFils(i,j,listChar));
                        allStates.add(firstState);
                        break;
                    case "e":
                        //la il s'agit d'un état quelconque
                        temp=new State(i,j,calculHeuristic(i,j,finalStates),false);
                        //temp.setListFils(chercherFils(i,j,listChar));
                        allStates.add(temp);
                        break;
                }

            }
        }
    }


    /**
     * cette méthode permet de remplir la liste listFils pour tous les états de allstates
     */
    public void setEdges(String[][] listChar) {

        ArrayList<State> tempListFils;

        for (State state:allStates) {

            tempListFils=new ArrayList<State>();
            //System.out.print(state.toString()+"  ->");
            int i=(int)state.getX();
            int j=(int)state.getY();
            if(i==0){
                //System.out.println("ici i vaut 0");
                if(j==0){
                    if (j<(listChar[0].length-1)&&(contains(allStates,new State(i,j+1)))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j+1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (i<(listChar.length-1)&&(contains(allStates,new State(i+1,j)))){
                        for (State temp:allStates) {
                            if(temp.getX()==i+1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
                else if(j!=0&&j==listChar[0].length-1){
                    if (contains(allStates,new State(i,j-1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j-1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (i<(listChar.length-1)&&(contains(allStates,new State(i+1,j)))){
                        for (State temp:allStates) {
                            if(temp.getX()==i+1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
                else{
                    //System.out.println("ici i vaut 0 et j vaut une valeur ente 0 et la longueur de la première ligne");
                    if (contains(allStates,new State(i,j-1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j-1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (i<(listChar.length-1)&&(contains(allStates,new State(i+1,j)))){
                        for (State temp:allStates) {
                            if(temp.getX()==i+1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if(contains(allStates,new State(i,j+1))){
                        //System.out.println("je look pour l'état final");
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j+1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
            }
            else if(i!=0&&i==listChar.length-1){
                if(j==0){
                    if (j<(listChar[0].length-1)&&(contains(allStates,new State(i,j+1)))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j+1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i-1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i-1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
                else if(j!=0&&j==listChar[0].length-1){
                    if (contains(allStates,new State(i,j-1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j-1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i-1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i-1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
                else{
                    if (contains(allStates,new State(i,j+1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j+1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i-1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i-1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i,j-1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j-1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
            }
            else{
                if(j==0){
                    if (contains(allStates,new State(i-1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i-1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (j<(listChar[0].length-1)&&(contains(allStates,new State(i,j+1)))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j+1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i+1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i+1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
                else if(j!=0&&j==listChar[0].length-1){
                    if (contains(allStates,new State(i-1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i-1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i,j-1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j-1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i+1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i+1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
                else{
                    if (contains(allStates,new State(i,j-1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j-1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i-1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i-1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i,j+1))){
                        for (State temp:allStates) {
                            if(temp.getX()==i&&temp.getY()==j+1){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                    if (contains(allStates,new State(i+1,j))){
                        for (State temp:allStates) {
                            if(temp.getX()==i+1&&temp.getY()==j){
                                tempListFils.add(temp);
                                 
                            }
                        }
                    }
                }
            }
            state.setListFils(tempListFils);
            //System.out.println("");
        }
    }

    /**
     * cette méthode calcule l'heuristique d'un état
     *
     */
    public double calculHeuristic(double x,double y,ArrayList<State> finalStates){
        ArrayList listHeuristic=new ArrayList(finalStates.size());
        for (State temp:finalStates) {
            listHeuristic.add(Math.abs(x-temp.getX())+Math.abs(y-temp.getY()));
        }
        double min= (double) listHeuristic.get(0);
        int i;
        for (i=1;i<listHeuristic.size();i++){
            if((double)listHeuristic.get(i)<min)
                min=(double)listHeuristic.get(i);
        }
        return min;
    }

}
