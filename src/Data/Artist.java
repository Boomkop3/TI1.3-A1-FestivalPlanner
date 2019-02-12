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
}
