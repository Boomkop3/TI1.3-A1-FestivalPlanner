package GUI.ManageViewer;

import Data.Artist;
import Data.Performance;
import Data.Genre;
import Data.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TestManageView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Artist> artists = new ArrayList<>();
        List<Performance> performaces = new ArrayList<>();
        List<Stage> stages = new ArrayList<>();

        artists.add(new Artist("Lucas", Genre.COUNTRY, "Singer", "Cool", "Netherlands"));
        artists.add(new Artist("Jasper", Genre.ROCK_MUSIC, "Rocker", "Cool", "Duitsland"));
        artists.add(new Artist("Thijs", Genre.DISCO, "funker", "Cool", "Belgie"));
        artists.add(new Artist("Quincy", Genre.MUSIC_FOR_CHILDREN, "HardBass", "Cool", "Russiesch"));

        primaryStage.setScene(new ArtistManageView(artists).getScene());
        primaryStage.show();
    }
}