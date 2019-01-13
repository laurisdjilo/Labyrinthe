package sample;

/**
 * Created by Lauris on 10/11/2017.
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Control {

    public final int DIMIMAGES=10;

    @FXML
    private ScrollPane labyrinthe;

    @FXML
    private TextArea textArea;

    @FXML
    private Button resoudre;

    @FXML
    private Button afficher;

    @FXML
    private Label error;

    public String textMaze;
    String[] listLine;
    String[][] listChar;
    GridPane maze;

    @FXML
    void afficherMaze(ActionEvent event){
        textMaze=textArea.getText();
        listLine=textMaze.split("\n");
        try{
            syntaxLabyrinthe();
        } catch (NotPossibleException e) {
            error.setText(e.pb);
            error.setTextFill(Color.RED);
        }
        listChar = new String[listLine.length][];
        for (int i=0;i<listLine.length;i++){
            listChar[i]=listLine[i].split("");
        }
        createAndFillGrid();
        error.setText("");
        resoudre.setDisable(false);
    }

    @FXML
    void solve(ActionEvent event){
        resoudre.setDisable(true);
        afficher.setDisable(true);
        AStar aStar=new AStar();
        aStar.initializeStates(listChar);
        aStar.setEdges(listChar);
        try {
            aStar.aStar();
            remplirChemin(aStar.chemin);
            error.setText("Chemin trouvé");
            error.setTextFill(Color.GREEN);
        } catch (NotPossibleException e) {
            error.setText(e.pb);
            error.setTextFill(Color.RED);
        }
        afficher.setDisable(false);

    }

    private void remplirChemin(ArrayList<State> chemin) {

        new Thread(new Runnable() {
            int i;
            @Override
            public void run() {
                try {
                    for (i=chemin.size()-2;i>=1;i--){
                        Platform.runLater(new Runnable() { // Run from JavaFX GUI
                            @Override
                            public void run() {
                                Rectangle reflexion=new Rectangle();
                                reflexion.setHeight(DIMIMAGES);
                                reflexion.setWidth(DIMIMAGES);
                                reflexion.setFill(Color.GREEN);
                                maze.add(reflexion,(int)chemin.get(i).getY(),(int)chemin.get(i).getX());
                            }
                        });
                        Thread.sleep(40);
                    }
                    Platform.runLater(new Runnable() {
                        public void run() {
                            Rectangle found = new Rectangle();
                            found.setHeight(DIMIMAGES);
                            found.setWidth(DIMIMAGES);
                            found.setFill(Color.GREEN);
                            maze.add(found, (int) chemin.get(0).getY(), (int) chemin.get(0).getX());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void syntaxLabyrinthe() throws NotPossibleException {
        int longueur=listLine[0].length();
        for(int i=1;i<listLine.length;i++){
            if(listLine[i].length()!=longueur)
                throw new NotPossibleException("labyrinthe mal formé");
        }
    }

    void createAndFillGrid(){
        maze=new GridPane();
        maze.setAlignment(Pos.CENTER);
        maze.setHgap(2);
        maze.setVgap(2);
        for (int i=0;i<listChar.length;i++){
            for (int j=0;j<listChar[0].length;j++){
                switch (listChar[i][j]){
                    case "i":
                        Rectangle start=new Rectangle();
                        start.setHeight(DIMIMAGES);
                        start.setWidth(DIMIMAGES);
                        start.setFill(Color.GREEN);
                        maze.add(start,j,i);
                        break;
                    case "e":
                        Rectangle stateimg=new Rectangle();
                        stateimg.setHeight(DIMIMAGES);
                        stateimg.setWidth(DIMIMAGES);
                        stateimg.setFill(Color.WHITE);
                        maze.add(stateimg,j,i);
                        break;
                    case "$":
                        Rectangle end=new Rectangle();
                        end.setHeight(DIMIMAGES);
                        end.setWidth(DIMIMAGES);
                        end.setFill(Color.BLUE);
                        maze.add(end,j,i);
                        break;
                    default:
                        Rectangle brique=new Rectangle();
                        brique.setHeight(DIMIMAGES);
                        brique.setWidth(DIMIMAGES);
                        brique.setFill(Color.DARKGRAY);
                        maze.add(brique,j,i);
                        break;
                }
            }
        }
        labyrinthe.setContent(maze);
    }
}
