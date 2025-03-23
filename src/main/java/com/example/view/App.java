package com.example.view;
import com.example.Service.ServiceForCalculations;
import com.example.models.Celestials.Planet;
import com.example.models.Rocket;
import com.example.parsers.ParsePlanetFile;
import com.example.parsers.ParseRocketFile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Duration;


public class App extends Application{
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private Map<String, Planet> planetData = new HashMap<String, Planet>();
    private List<Planet> planets = new ArrayList<Planet>();
    private Rocket rocket = new Rocket(0,0);
    private ParsePlanetFile parserForPlanets = new ParsePlanetFile();
    private ParseRocketFile parserForRocket = new ParseRocketFile();
    private ServiceForCalculations service = new ServiceForCalculations();
    public void loadData(String planetPath, String solarSystemPath, String rocketPath) throws Exception{
        parserForPlanets.parseFilePlanet(planetPath, planetData);
        parserForPlanets.parseFileSolarSystem(solarSystemPath, planetData, planets);
        parserForRocket.parseRocketFile(rocketPath, rocket);
    }
    private void applyTypewriterEffect(Text text, String stringToType){
        Timeline timeline = new Timeline();
        for(int i = 0; i < stringToType.length(); i++){
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(25*i), e -> text.setText(stringToType.substring(0, index+1)));
            timeline.getKeyFrames().add(keyFrame);
        }
            timeline.play();
    }
    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        this.primaryStage = primaryStage;
        String musicFile = getClass().getResource("/music/backgroundSound.mp3").toExternalForm();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        primaryStage.setTitle("Rocket project");
        Scene sceneInputFiles = createSceneInputFiles(primaryStage);
        primaryStage.setScene(sceneInputFiles);
        primaryStage.show();
    }
    public Scene createSceneStage3(Stage stage){
        VBox root = new VBox(20);
        HBox aiImageAndText = aiImageAndTextModular("\"Stage 3 initiates a simulated spaceflight between two planets, assuming perfect alignment and no external interference. Enter your departure and destination planets, then confirm to begin the calculation.\"");
        root.getChildren().add(aiImageAndText);
        TextField departurePlanet = new TextField();
        departurePlanet.setPromptText("Enter departure planet..");
        TextField destinationPlanet = new TextField();
        destinationPlanet.setPromptText("Enter destination planet..");
        Button submitButton  = new Button("Submit");
        Text informationText = new Text();
        informationText.setFont(Font.font("Arial", 16));
        Button goBackButton = new Button("Go back");
        submitButton.setOnAction(e -> {
            Planet departPlanet = planetData.get(departurePlanet.getText());
            Planet destPlanet = planetData.get(destinationPlanet.getText());
            BigDecimal cruisingSpeed = service.getCruisingSpeed(departPlanet, destPlanet);

            BigDecimal timeToCruisingVelocity = rocket.getTimeToReachEscapeVelocity(cruisingSpeed);
            BigDecimal distanceAtCruisingVelocity = service.getDistanceFromPlanetAtAccOrDeacc(departPlanet, destPlanet, rocket);
            BigDecimal journeyTimeAtCruisingSpeed = service.getJourneyTimeAtCruisingVelocity(departPlanet, destPlanet, rocket, cruisingSpeed);
            BigDecimal distanceBeforeDeceleration = service.getDistanceFromPlanetAtAccOrDeacc(destPlanet, departPlanet, rocket);
            BigDecimal decelerationTime = rocket.getTimeToReachEscapeVelocity(cruisingSpeed);
            BigDecimal totalTravelTime = service.getTotalJourneyTime(departPlanet, destPlanet, rocket,  cruisingSpeed);
            String formattedJourneyTime = service.toStringGetTime(service.getTimeinDaysMinutesSeconds(totalTravelTime));

            informationText.setText(
                    "Time to reach cruising velocity (the highest escape velocity between the two planets): " + timeToCruisingVelocity.setScale(0, RoundingMode.HALF_UP) + " seconds\n" +
                            "Distance from departure planet when we reach cruising velocity: " + distanceAtCruisingVelocity.divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP) + " km\n" +
                            "Time spent cruising at nominal velocity: " + journeyTimeAtCruisingSpeed.setScale(0, RoundingMode.HALF_UP) + " seconds\n" +
                            "Distance from destination planet where deceleration begins: " + distanceBeforeDeceleration.divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP) + " km\n" +
                            "Time to decelerate to zero: " + decelerationTime.setScale(0, RoundingMode.HALF_UP) + " seconds\n" +
                            "Total travel time in seconds: " + totalTravelTime.setScale(0, RoundingMode.HALF_UP) + " seconds\n" +
                            "Total travel time formatted " + formattedJourneyTime
            );
        });
        goBackButton.setOnAction(e ->{
            primaryStage.setScene(createSceneTestSelection(primaryStage));
        });
        root.getChildren().addAll(departurePlanet, destinationPlanet, submitButton, informationText, goBackButton);

        return new Scene(root, 1000, 800);


    }
    public Scene createSceneStage2(Stage stage){
        VBox root = new VBox(20);
        HBox aiImageAndText = aiImageAndTextModular("\"Test 2 consists of testing our spaceship's capability to reach escape velocity. We want our ship to be able to compute the time to reach this speed and the distance we travelled to reach this velocity.\"");
        root.getChildren().add(aiImageAndText);
        HBox buttons =  new HBox(20);
        Button buttonMercury = new Button("Mercury");
        Button buttonVenus = new Button("Venus");
        Button buttonEarth = new Button("Earth");
        Button buttonMars = new Button("Mars");
        Button buttonJupiter = new Button("Jupiter");
        Button buttonSaturn = new Button("Saturn");
        Button buttonUranus = new Button("Uranus");
        Button buttonNeptune = new Button("Neptune");
        Button buttonPluto = new Button("Pluto");
        Text informationText = new Text();
        Button buttonGoBack = new Button("Go back");
        Button[] planetButtons = {buttonMercury, buttonVenus, buttonEarth, buttonMars,
                buttonJupiter, buttonSaturn, buttonUranus, buttonNeptune, buttonPluto};
        informationText.setFont(Font.font("Arial", 24));
        buttonGoBack.setOnAction(e -> {
            primaryStage.setScene(createSceneTestSelection(primaryStage));
        });
        for (Button button : planetButtons) {
            button.setOnAction(e -> {
                String planetName = button.getText();
                informationText.setText("Time to reach escape velocity from " + planetName + " with our rocket: "
                        + rocket.getTimeToReachEscapeVelocity(planetData.get(planetName).getEscapeVelocity()).setScale(0, RoundingMode.HALF_UP)+ " seconds" + "\n"
                        + "The distance travelled to reach escape velocity: "
                        + rocket.getDistanceToReachEscapeVelocity(planetData.get(planetName).getEscapeVelocity())
                        .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP) + " km");
            });
        }
        buttons.getChildren().addAll(planetButtons);
        root.getChildren().addAll(buttons, informationText, buttonGoBack);
        return new Scene(root, 1000, 800);
    }
    public Scene createSceneStage1(Stage stage){
        VBox root = new VBox(20);
        HBox aiImageAndText = aiImageAndTextModular("\"Welcome, Captain. For our first test, we will verify the ship's ability to accurately calculate the escape velocity of the selected planet. Please, choose the planet from the list, and the ship will handle the computations with precision.\"");
        root.getChildren().add(aiImageAndText);
        HBox buttons =  new HBox(20);
        Button buttonMercury = new Button("Mercury");
        Button buttonVenus = new Button("Venus");
        Button buttonEarth = new Button("Earth");
        Button buttonMars = new Button("Mars");
        Button buttonJupiter = new Button("Jupiter");
        Button buttonSaturn = new Button("Saturn");
        Button buttonUranus = new Button("Uranus");
        Button buttonNeptune = new Button("Neptune");
        Button buttonPluto = new Button("Pluto");
        Text escapeVelocityText = new Text();
        escapeVelocityText.setFont(Font.font("Arial", 40));
        buttons.getChildren().addAll(buttonMercury, buttonVenus, buttonEarth, buttonMars, buttonJupiter, buttonSaturn, buttonUranus, buttonNeptune, buttonPluto);
        Button buttonGoBack = new Button("Go back");
        buttonGoBack.setOnAction(e -> {
            primaryStage.setScene(createSceneTestSelection(primaryStage));
        });
        root.getChildren().addAll(buttons, escapeVelocityText, buttonGoBack);

        buttonMercury.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf( "Escape Velocity of Mercury: " + planetData.get(buttonMercury.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)))
                    .setScale(1, BigDecimal.ROUND_HALF_UP)) + " km/s");
        });
        buttonVenus.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Venus: " + planetData.get(buttonVenus.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)))
                    .setScale(1, BigDecimal.ROUND_HALF_UP)) + " km/s");
        });
        buttonEarth.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Earth: " +planetData.get(buttonEarth.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP))+ " km/s");
        });
        buttonMars.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Mars: " +planetData.get(buttonMars.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP)) + " km/s");
        });
        buttonJupiter.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Jupiter: " + planetData.get(buttonJupiter.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP)) + " km/s");
        });
        buttonSaturn.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Saturn: " +planetData.get(buttonSaturn.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP)) + " km/s");
        });
        buttonUranus.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Uranus: " +planetData.get(buttonUranus.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP)) + " km/s");
        });
        buttonNeptune.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Neptune: " +planetData.get(buttonNeptune.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP))+ " km/s");
        });
        buttonPluto.setOnAction(e -> {
            escapeVelocityText.setText(String.valueOf("Escape Velocity of Mercury: " +planetData.get(buttonPluto.getText()).getEscapeVelocity()
                    .divide(BigDecimal.valueOf(Math.pow(10,3)), MathContext.DECIMAL128)
                    .setScale(1, BigDecimal.ROUND_HALF_UP))+ " km/s");
        });
        return new Scene(root, 1000, 800);
    }
    public HBox aiImageAndTextModular(String textToDisplay){
        HBox aiImageAndText = new HBox(20);

        aiImageAndText.setPadding(new Insets(20));
        Image aiFace = new Image(getClass().getResource("/aiFace.png").toExternalForm());
        ImageView aiView = new ImageView(aiFace);
        Text text = new Text();
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Orbitron-VariableFont_wght.ttf"), 18);
        applyTypewriterEffect(text, textToDisplay);

        aiView.setFitHeight(200);
        aiView.setPreserveRatio(true);
        text.setWrappingWidth(700);
        text.setFont(font);
        aiImageAndText.getChildren().addAll(aiView, text);
        return aiImageAndText;
    }
    public Scene createSceneTestSelection(Stage stage){
        VBox root = new VBox(20);
        HBox aiImageAndText = aiImageAndTextModular("\"Thank you for providing the necessary data, Captain. Before we embark on a journey to distant planets, we must ensure the Horizon is operating within optimal parameters. There are five critical stages to test, ensuring our safety and success. However, should you feel adventurous, you can bypass the diagnostics and take off into the unknown without waiting for the green light. The choice is yours, Captain\"");
        root.getChildren().add(aiImageAndText);
        HBox buttons =  new HBox(20);

        Button stage1Button =  new Button("Stage 1");
        stage1Button.setPrefSize(100, 50);
        Button stage2Button = new Button("Stage 2");
        stage2Button.setPrefSize(100, 50);
        Button stage3Button = new Button("Stage 3");
        stage3Button.setPrefSize(100, 50);
        Button stage4Button = new Button("Stage 4");
        stage4Button.setPrefSize(100, 50);
        Button stage5Button = new Button("Stage 5");
        stage5Button.setPrefSize(100, 50);
        Button launchButton = new Button("LAUNCH");
        launchButton.setPrefSize(100, 50);
        stage1Button.setOnAction(e -> {
            primaryStage.setScene(createSceneStage1(primaryStage));
        });
        stage2Button.setOnAction(e ->{
            primaryStage.setScene(createSceneStage2(primaryStage));
        });
        stage3Button.setOnAction(e ->{
            primaryStage.setScene(createSceneStage3(primaryStage));
        });
        buttons.getChildren().addAll(stage1Button,stage2Button,stage3Button,stage4Button,stage5Button, launchButton);
        root.getChildren().add(buttons);
        return new Scene(root, 1000, 800);
    }
    public Scene createSceneInputFiles(Stage stage) throws URISyntaxException {
        VBox rootfirst = new VBox(20);
        HBox aiImageAndText = aiImageAndTextModular("\"Greetings, Captain. I am Orion, the Artificial Intelligence designed to guide you aboard the prototype ship, Horizon. Together, we will embark on a voyage to distant planets. As we prepare for launch, I will assist you in navigating crucial systems, starting with configuring the file paths required for our journey. Don’t worry—default paths have already been set, but feel free to input your custom data whenever you're ready. Let’s get this ship prepared to journey through the stars.\"");

        TextField planetDataFileInput = new TextField();
        planetDataFileInput.setText("src/main/java/com/example/data/input/Planetary_Data.txt");
        planetDataFileInput.setPromptText("File path for planet data:");
        TextField solarSystemDataFileInput = new TextField();
        solarSystemDataFileInput.setText("src/main/java/com/example/data/input/Solar_System_Data.txt");
        solarSystemDataFileInput.setPromptText("File path for solar system data:");
        TextField rocketDataFileInput = new TextField();
        rocketDataFileInput.setText("src/main/java/com/example/data/input/Rocket_Data.txt");
        rocketDataFileInput.setPromptText("File path for rocket data:");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e ->{
            try{
            String planetPath = planetDataFileInput.getText();
            String solarSystemPath = solarSystemDataFileInput.getText();
            String  rocketPath = rocketDataFileInput.getText();
            loadData(planetPath, solarSystemPath, rocketPath);
            Scene testScene = createSceneTestSelection(primaryStage);
            primaryStage.setScene(testScene);

            }
            catch(Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        });

        Label planetDataLabel = new Label("File path for planet data:");
        Label solarSystemDataLabel = new Label("File path for solar system data:");
        Label rocketDataLabel = new Label("File path for rocket data:");
        rootfirst.getChildren().add(aiImageAndText);
        rootfirst.getChildren().addAll(planetDataLabel, planetDataFileInput, solarSystemDataLabel, solarSystemDataFileInput, rocketDataLabel, rocketDataFileInput, submitButton );

        return new Scene(rootfirst, 1000, 800);
    }
}
