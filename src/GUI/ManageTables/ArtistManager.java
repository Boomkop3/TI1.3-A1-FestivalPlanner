package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import Data.Genre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ArtistManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private FestivalDay festivalDay;
    private TableView tableView = new TableView<Artist>();

    public ArtistManager(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
        this.scene = new Scene(this.borderPane = new BorderPane());
        this.stage = new Stage();
        HBox top = new HBox();
        Label title = new Label("Artist List");
        top.getChildren().add(title);
        top.setAlignment(Pos.CENTER);
        title.setFont(new Font("Arial", 40));
        this.borderPane.setTop(top);
        this.borderPane.setCenter(this.tableView);
        InitializeScene();
    }

    private void InitializeScene() {
        //adding the columns
        tableView.setEditable(true);
        TableColumn<Artist, String> name = new TableColumn("name");
        TableColumn<Artist, Genre> genre = new TableColumn("Genre");
        TableColumn<Artist, String> artistType = new TableColumn("artistType");
        TableColumn<Artist, String> filePathProfilePic = new TableColumn("ProfilePicture");
        TableColumn<Artist, String> countryOfOrigin = new TableColumn("country");
        TableColumn<Artist, String> extraInfo = new TableColumn<>("extraInformation");


        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setMinWidth(100);

        name.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newName = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            this.festivalDay.getArtist(artist).setName(newName);
        });
        // genre
        ObservableList<Genre> genreList = FXCollections.observableArrayList(Genre.values());

        genre.setCellValueFactory(param -> {
            Artist artist = param.getValue();

            String genderCode = artist.getGenre().toString();
            Genre gernre = Genre.getByCode(genderCode);
            return new SimpleObjectProperty<>(gernre);
        });

        genre.setCellFactory(ComboBoxTableCell.forTableColumn(genreList));

        genre.setOnEditCommit((TableColumn.CellEditEvent<Artist, Genre> event) -> {
            TablePosition<Artist, Genre> pos = event.getTablePosition();

            Genre newGenre = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            artist.setGenre(newGenre);
        });


        genre.setMinWidth(150);

        // end genre


        artistType.setCellValueFactory(new PropertyValueFactory<>("artistType"));
        artistType.setCellFactory(TextFieldTableCell.forTableColumn());
        artistType.setMinWidth(100);

        artistType.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newArtistType = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            this.festivalDay.getArtist(artist).setArtistType(newArtistType);
        });

        countryOfOrigin.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryOfOrigin.setCellFactory(TextFieldTableCell.<Artist> forTableColumn());
        countryOfOrigin.setMinWidth(100);

        countryOfOrigin.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newCountry = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            this.festivalDay.getArtist(artist).setCountry(newCountry);
        });

        extraInfo.setCellValueFactory(new PropertyValueFactory<>("extraInformation"));
        extraInfo.setCellFactory(TextFieldTableCell.<Artist> forTableColumn());
        extraInfo.setMinWidth(300);

        extraInfo.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newInfo = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            this.festivalDay.getArtist(artist).setExtraInformation(newInfo);
        });


        //button
        filePathProfilePic.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Artist, String>, TableCell<Artist, String>> cellFactory
                =
                new Callback<TableColumn<Artist, String>, TableCell<Artist, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Artist, String> param) {
                        final TableCell<Artist, String> cell = new TableCell<Artist, String>() {

                            final Button button = new Button("Chose Picture");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Artist artist = getTableView().getItems().get(getIndex());
                                        FileChooser fileChooser = new FileChooser();
                                        artist.setFilePathProfilePicture(fileChooser.showOpenDialog(new Stage()).getPath());
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        filePathProfilePic.setCellFactory(cellFactory);
        //end button

        this.tableView.getColumns().addAll(name, genre, artistType, filePathProfilePic, countryOfOrigin, extraInfo);

        this.tableView.setItems(FXCollections.observableList(this.festivalDay.getArtists()));

        this.stage.setResizable(true);
        this.stage.setTitle("Artist List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }


}