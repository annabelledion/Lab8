import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {
    public static void main(String[] args) {launch(args);}

    public static ArrayList<ImageView> liste = new ArrayList<>();

    public static GridPane puzzle = new GridPane();

    public static Image tabImage[] = new Image[9];

    @Override
    public void start(Stage primaryStage) throws Exception {

        //fenêtre
        primaryStage.setHeight(300);
        primaryStage.setWidth(600);
        primaryStage.setTitle("Casse-tête");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);

        //menu
        BorderPane app = new BorderPane();
        Scene scene = new Scene(app);

        //images
        Image image1 = new Image("mario0.jpg");
        Image image2 = new Image("mario1.jpg");
        Image image3 = new Image("mario2.jpg");
        Image image4 = new Image("mario3.jpg");
        Image image5 = new Image("mario4.jpg");
        Image image6 = new Image("mario5.jpg");
        Image image7 = new Image("mario6.jpg");
        Image image8 = new Image("mario7.jpg");
        Image image9 = new Image("mario8.jpg");

        for (int i = 0; i < tabImage.length; i++){
            tabImage[0] = image1;
            tabImage[1] = image2;
            tabImage[2] = image3;
            tabImage[3] = image4;
            tabImage[4] = image5;
            tabImage[5] = image6;
            tabImage[6] = image7;
            tabImage[7] = image8;
            tabImage[8] = image9;
        }

        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        ImageView imageView4 = new ImageView(image4);
        ImageView imageView5 = new ImageView(image5);
        ImageView imageView6 = new ImageView(image6);
        ImageView imageView7 = new ImageView(image7);
        ImageView imageView8 = new ImageView(image8);
        ImageView imageView9 = new ImageView(image9);

        liste.add(imageView1);
        liste.add(imageView2);
        liste.add(imageView3);
        liste.add(imageView4);
        liste.add(imageView5);
        liste.add(imageView6);
        liste.add(imageView7);
        liste.add(imageView8);
        liste.add(imageView9);
        Collections.shuffle(liste);

        //casse-tête
        puzzle.add(liste.get(0), 0,0);
        puzzle.add(liste.get(1), 1,0);
        puzzle.add(liste.get(2), 2,0);
        puzzle.add(liste.get(3), 0,1);
        puzzle.add(liste.get(4), 1,1);
        puzzle.add(liste.get(5), 2,1);
        puzzle.add(liste.get(6), 0,2);
        puzzle.add(liste.get(7), 1,2);
        puzzle.add(liste.get(8), 2,2);

        HBox puzzle2 = new HBox(puzzle);
        puzzle2.setAlignment(Pos.CENTER);
        app.setCenter(puzzle2);

        //actions
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown() == true && event.getCode() == KeyCode.M)
            {
                Collections.shuffle(liste);
                puzzle.setConstraints(liste.get(0), 0,0);
                puzzle.setConstraints(liste.get(1), 0,1);
                puzzle.setConstraints(liste.get(2), 0,2);
                puzzle.setConstraints(liste.get(3), 1,0);
                puzzle.setConstraints(liste.get(4), 1,1);
                puzzle.setConstraints(liste.get(5), 1,2);
                puzzle.setConstraints(liste.get(6), 2,0);
                puzzle.setConstraints(liste.get(7), 2,1);
                puzzle.setConstraints(liste.get(8), 2,2);

            }
        });

        for (int i = 0; i < liste.size(); i++){

            final ImageView source = liste.get(i);
            final int position = i;

            source.setOnDragDetected(event -> {
                Dragboard dragboard = source.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(source.getImage());
                dragboard.setContent(content);
            });

            source.setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.MOVE);

            });

            source.setOnDragDropped(event -> {
                Image target = ((ImageView)event.getGestureSource()).getImage();
                ((ImageView)event.getGestureSource()).setImage(source.getImage());
                source.setImage(target);
                event.setDropCompleted(true);
            });

            source.setOnDragDone(event -> {

                boolean same = true;

                for (int j = 0; j < liste.size(); j++){

                   try {
                       if (tabImage[j] != liste.get(j).getImage()){
                           same = false;
                       }
                   }
                   catch (NullPointerException e){

                   }
                }

                if (same){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Félicitation");
                    alert.setHeaderText("Vous venez de compléter le casse-tête!!");
                    alert.setContentText("Voulez-vous recommencer?");

                    ButtonType result = alert.showAndWait().get();

                    if (result == ButtonType.OK){
                       rejouer();
                    }
                    else if (result == ButtonType.CANCEL){
                        alert.close();
                        event.consume();
                        primaryStage.close();
                    }

                }
            });

        }
        //show
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void rejouer(){
        puzzle.getChildren().clear();
        Collections.shuffle(liste);

        puzzle.add(liste.get(0), 0,0);
        puzzle.add(liste.get(1), 1,0);
        puzzle.add(liste.get(2), 2,0);
        puzzle.add(liste.get(3), 0,1);
        puzzle.add(liste.get(4), 1,1);
        puzzle.add(liste.get(5), 2,1);
        puzzle.add(liste.get(6), 0,2);
        puzzle.add(liste.get(7), 1,2);
        puzzle.add(liste.get(8), 2,2);

    }
}
