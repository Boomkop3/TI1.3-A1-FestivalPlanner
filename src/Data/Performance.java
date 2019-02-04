package Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Performance {

    private List<Artist> artists;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int popularity;

    public Performance(LocalDateTime startTime, LocalDateTime endTime, int popularity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.popularity = popularity;
        this.artists = new ArrayList<>();
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public void removeArtist(Artist artist) {
        if (this.artists.contains(artist)) {
            this.artists.remove(artist);
        } else {
            System.out.println("Artist not found in list!");
        }
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
