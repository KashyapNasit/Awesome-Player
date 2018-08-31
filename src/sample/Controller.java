package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.naming.Binding;
import java.io.File;

public class Controller {

    private MediaPlayer mediaPlayer;
    private String filePath;

    @FXML
    Slider volumeSlider;

    @FXML
    Slider timeSlider;
    @FXML
    private MediaView mediaView = new MediaView();

    @FXML
    private void open(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Choose file with extension *.mp3, *.wav, *.mpa, *.ogg", "*.mp3");
                fileChooser.setSelectedExtensionFilter(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        if(filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            DoubleProperty width =  mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));

            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue()/100);
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    timeSlider.setValue(newValue.toSeconds());
                }
            });

            timeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                }
            });

            timeSlider.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                }
            });

        }
    }

    @FXML
    private void play(){
        mediaPlayer.play();
    }

    @FXML
    private void pause(){
        mediaPlayer.pause();
    }

    @FXML
    private void stop(){
        mediaPlayer.stop();
    }
}
