/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage;

import gorovojimage.filters.FurierFilter;
import gorovojimage.filters.GreyFilter;
import gorovojimage.filters.LinearFilter;
import gorovojimage.filters.NoiseFilter;
import gorovojimage.filters.WaveGenerator;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author aleksandr-gorovoi
 */
public class Gorovojimage extends Application {
    
    private static final int PICTURE_WIDTH=600;
    private static final int PICTURE_HEIGHT=500;
    private static final int TOOLBAR_HEIGHT=120;
    private static final int MAIN_WIDTH=PICTURE_WIDTH;
    private static final int MAIN_HEIGHT=PICTURE_HEIGHT+TOOLBAR_HEIGHT;
    
    
    ImageView sourceView = new ImageView();
    ImageView resultView = new ImageView();
    
    
    @Override
    public void start(Stage stage) {
        //Temporal
        File file = new File("dog1.jpeg");
        Image sourceImage = new Image(file.toURI().toString());
        sourceView.setImage(sourceImage);
        Image resultImage = new Image(file.toURI().toString());
        resultView.setImage(resultImage);
        //Set scroll panes
        VBox sourceBox = new VBox();
        sourceBox.getChildren().add(sourceView);
        ScrollPane sourceScroll = new ScrollPane(); 
        sourceScroll.setVmax(PICTURE_HEIGHT);
        sourceScroll.setHmax(PICTURE_WIDTH/2);
        sourceScroll.setPrefSize(PICTURE_WIDTH/2, PICTURE_HEIGHT);
        sourceScroll.setLayoutX(0);
        sourceScroll.setLayoutY(0);
        sourceScroll.setContent(sourceBox);
        //
        VBox resultBox = new VBox();
        resultBox.getChildren().add(resultView);
        ScrollPane resultScroll = new ScrollPane(); 
        resultScroll.setVmax(PICTURE_HEIGHT);
        resultScroll.setHmax(PICTURE_WIDTH/2);
        resultScroll.setPrefSize(PICTURE_WIDTH/2, PICTURE_HEIGHT);
        resultScroll.setLayoutX(PICTURE_WIDTH/2);
        resultScroll.setLayoutY(0);
        resultScroll.setContent(resultBox);
        //Images
        HBox imageBox = new HBox();
        imageBox.getChildren().addAll(sourceScroll, resultScroll);
        //Toolbar
        HBox toolBar = new HBox();
        toolBar.setPrefHeight(TOOLBAR_HEIGHT);
        prepareToolbar(toolBar, stage);
        //
        VBox commonBox = new VBox();
        commonBox.getChildren().addAll(toolBar, imageBox);
        Scene scene = new Scene(commonBox, MAIN_WIDTH, MAIN_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Image transformation");
        stage.setResizable(false);
        stage.setMaximized(false);
        //
        stage.show();
    }    

    public void prepareToolbar(HBox toolbar, Stage stage){
        //File open
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", 
                        "*.png", "*.jpg", "*.gif", "*.jpeg"),
                new ExtensionFilter("All Files", "*.*")
        );
        Button chooseButton = new Button();
        chooseButton.setText("Choose image");
        chooseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                File selected = fileChooser.showOpenDialog(stage);
                if (selected != null){
                    Image newImage = new Image(selected.toURI().toString());
                    sourceView.setImage(newImage);
                    resultView.setImage(newImage);
                }
            }            
        });
        //Grey button
        Button greyButton = new Button();
        greyButton.setText("Gray image");
        greyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Image greyed = new GreyFilter()
                        .filterImage(sourceView.getImage());
                resultView.setImage(greyed);
            }
        });
        //Set dest to source
        Button setButton = new Button();
        setButton.setText("Set dest as source");
        setButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                sourceView.setImage(resultView.getImage());
            }            
        });     
        //Noize filter
        Button addNoiseButton = new Button();
        addNoiseButton.setText("Add noize");
        addNoiseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Image noised = new NoiseFilter()
                        .filterImage(sourceView.getImage());
                resultView.setImage(noised);
            }
        });
        //Furier filter
        Button furierButton = new Button();
        furierButton.setText("Furier filter");
        furierButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Image filtered = new FurierFilter()
                        .filterImage(sourceView.getImage());
                resultView.setImage(filtered);
            }
        });
        //Wave generation
        Button waveButton = new Button();
        waveButton.setText("Source as wave");
        waveButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Image wave = new WaveGenerator(600, 0)
                        .filterImage(sourceView.getImage());
                sourceView.setImage(wave);
            }
        });
        //Linear button
        Button linearButton = new Button();
        linearButton.setText("Linear filter");
        linearButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Image wave = new LinearFilter()
                        .filterImage(sourceView.getImage());
                resultView.setImage(wave);
            }
        });
        //
        toolbar.getChildren().addAll(chooseButton, greyButton, setButton,
                addNoiseButton, furierButton, waveButton, linearButton);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
