package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import javax.naming.Binding;
import java.io.File;

public class Controller {

    private MediaPlayer mediaPlayer;
    private String filePath;

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
