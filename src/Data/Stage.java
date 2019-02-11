package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Stage {
    private String name;
    private List<Performance> performances;

    public Stage(String name) {
        this.name = name;
        this.performances = new ArrayList<>();
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void addPerformance(Performance performance) {
        if (!this.performances.contains(performance)) {
            this.performances.add(performance);
        } else {
            System.out.println("Performance allready in Stage's list");
        }
    }

    public void removePerformance(Performance performance) {
        if (this.performances.contains(performance)) {
            this.performances.remove(performance);
        } else {
            System.out.println("Performance not found Stage's list in list!");
        }
    }
}
