import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class JavaFXGUI
{
    private static TextField uniName;
    private static TextField uniRank; // Initialising the variables for University properties
    private static TextField uniPlace;
    private static ListView<University> uniListView;
    private static ArrayList<University> uniArrList = new ArrayList<University>();
    private static University currentlySelectedUniversity = null;
    
    public static void main(String args[]){
        launchFX();
    }

    public static void displayCloseDialog(WindowEvent we){
        System.out.println("Close button clicked");

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            terminate();
        }
        else{
            we.consume();
        }
    }

    private static void terminate(){
        System.out.println("Goodbye");
        System.exit(0);
    }

    private static void launchFX(){
        // Initialising the JavaFX program
        new JFXPanel();
        // Runs initialisation on the JavaFX thread
        Platform.runLater(() -> initialiseGUI());
    }

    private static void initialiseGUI(){
        Stage stage = new Stage(); // Initialise stage
        stage.setOnCloseRequest((WindowEvent we) -> displayCloseDialog(we));
        stage.setTitle("University Printing --- Finn");
        stage.setResizable(false); // Remains the same size
        Pane rootPane = new Pane();   // Iniatialise new pane
        stage.setScene(new Scene(rootPane));
        stage.setWidth(768); // stage width and height is equal, 768
        stage.setHeight(768);
        stage.show();

        Image image = new Image("sam.jpg");     // Find the image, initialising as an object
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setLayoutX(20);    // setting layout position of image
        iv1.setLayoutY(250);
        iv1.setFitWidth(420);
        iv1.setPreserveRatio(true);
        rootPane.getChildren().add(iv1);    // add image to pane

        uniListView = new ListView<University>();
        uniListView.setLayoutX(450);
        uniListView.setLayoutY(50);
        uniListView.setOnMouseClicked((MouseEvent me) -> {
            currentlySelectedUniversity = uniListView.getSelectionModel().getSelectedItem();
        });
        rootPane.getChildren().add(uniListView);
        updateListView();

        Label label = new Label("Enter details of University");
        label.setLayoutX(100);
        label.setLayoutY(20);
        rootPane.getChildren().add(label);

        uniRank = new TextField();
        uniRank.setLayoutX(100);
        uniRank.setLayoutY(50);
        uniRank.setPrefWidth(300);
        uniRank.setPromptText("University Rank");
        rootPane.getChildren().add(uniRank);

        uniName = new TextField();  // sets uniName to new text field
        uniName.setLayoutX(100); 
        uniName.setLayoutY(80);     // sets layout position of text field
        uniName.setPrefWidth(300);
        uniName.setPromptText("University Name");
        rootPane.getChildren().add(uniName);

        uniPlace = new TextField();
        uniPlace.setLayoutX(100);
        uniPlace.setLayoutY(110);
        uniPlace.setPrefWidth(300);
        uniPlace.setPromptText("University Country");
        rootPane.getChildren().add(uniPlace);

        Button btnPrint = new Button();
        btnPrint.setText("Add");
        btnPrint.setLayoutX(100);
        btnPrint.setLayoutY(150);
        btnPrint.setOnAction((ActionEvent ae) -> addNewItem());
        rootPane.getChildren().add(btnPrint);

        Button btnDelete = new Button();
        btnDelete.setText("Delete");
        btnDelete.setLayoutX(100);
        btnDelete.setLayoutY(190);
        btnDelete.setOnAction((ActionEvent ae) -> deleteItem());
        rootPane.getChildren().add(btnDelete);

    }

    private static void deleteItem() {
        uniArrList.remove(currentlySelectedUniversity);
        updateListView();
    }
   
    private static void updateListView(){
        uniListView.getItems().clear();

        for(University uni : uniArrList) {
            uniListView.getItems().add(uni);
        }
    }

    private static void addNewItem(){
        String uniN = uniName.getText();
        int uniR = Integer.parseInt(uniRank.getText());
        String uniP = uniPlace.getText();

        System.out.println(uniN);
        System.out.println(uniR);
        System.out.println(uniP);

        uniArrList.add(new University(uniR, uniN, uniP));

        updateListView();
    }
}
