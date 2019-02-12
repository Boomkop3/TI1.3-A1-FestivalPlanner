package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Artist implements Serializable {
    private String name; //Full name
    private Enum<Genre> genre;
    private String artistType;
    private String filePathProfilePicture;
    private String extraInformation; //Arbitrary text
    private String country;      //Country
    private List<Performance> performances;
    private FestivalDay festivalDay;

    public Artist(String name, Enum<Genre> genre, FestivalDay festivalDay, String artistType, String filePathProfilePicture, String extraInformation, String country) {
        this.name = name;
        this.genre = genre;
        this.artistType = artistType;
        this.filePathProfilePicture = filePathProfilePicture;
        this.extraInformation = extraInformation;
        this.country = country;
        this.performances = new ArrayList<Performance>();
        this.festivalDay = festivalDay;
    }

    public Artist(String name, Enum<Genre> genre, FestivalDay festivalDay, String artistType, String filePathProfilePicture, String country) {
        this(name, genre, festivalDay, artistType, filePathProfilePicture, "No extra information", country);
    }

    public void addPerformance(Performance performance){
        if(!this.performances.contains(performance)){
            this.performances.add(performance);
        } else {
            System.out.println("Performance allready in Artist's list!");
        }
    }

    public void removePerformance(Performance performance){
        if(this.performances.contains(performance)){
            this.performances.remove(performance);
        } else {
            System.out.println("Artist's performance list does not contain the given performance");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Enum<Genre> genre) {
        this.genre = genre;
    }

    public String getArtistType() {
        return artistType;
    }

    public void setArtistType(String artistType) {
        this.artistType = artistType;
    }

    public String getFilePathProfilePicture() {
        return filePathProfilePicture;
    }

    public void setFilePathProfilePicture(String filePathProfilePicture) {
        this.filePathProfilePicture = filePathProfilePicture;
    }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public FestivalDay getFestivalDay() {
        return festivalDay;
    }

    public void setFestivalDay(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
    }
}
