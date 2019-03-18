package Simulation;

import Data.AgendaModule;
import Data.Performance;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private Map map;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;
    private AgendaModule agendaModule;
    private BorderPane mainPane;

    public static final String path = System.getProperty("user.dir");

    public Simulation(AgendaModule agendaModule) {
        this.agendaModule = agendaModule;

        try (

//                InputStream jsonMap = new FileInputStream("D:\\Avans TI\\Proftaken\\Festival Planner\\Simulation.Map Laden\\Tiled\\Festival_3_11_2019.json");

                InputStream jsonMap = new FileInputStream( path + "\\rec\\Tiled\\untitled.json");

                JsonReader jsonReader = Json.createReader(jsonMap)
        ) {
            JsonObject jsonArrayOfBands = jsonReader.readObject();
            this.map = new Map(jsonArrayOfBands);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainPane = new BorderPane();

        CheckBox collisionL = new CheckBox("show Collision");
        HBox top = new HBox();
        top.getChildren().addAll(collisionL);

        mainPane.setTop(top);
        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        Scene scene = new Scene(mainPane);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        visitors = new ArrayList<>();

        while(visitors.size() < 40) {
            double x = Math.random()*1920;
            double y = Math.random()*1080;
            boolean hasCollision = false;
            for(Visitor visitor : visitors)
                if(visitor.hasCollision(new Point2D.Double(x,y)))
                    hasCollision = true;
            if(!hasCollision)
                visitors.add(new Visitor(new Point2D.Double(x, y)));
        }


        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        this.map.drawCache();

        canvas.setOnMouseMoved(e -> {
            for(Visitor visitor : visitors)
                visitor.setTarget(new Point2D.Double(e.getX(), e.getY()));
        });

        draw(g2d);
    }

    public void update(double deltaTime) {
        updateVisitorPosition();

        for(Visitor visitor : visitors)
            visitor.update(visitors, map);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        this.map.draw(graphics);

        for(Visitor visitor : visitors)
            visitor.draw(graphics);
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public void updateVisitorPosition(){
        int amountOfVisitors = visitors.size();
        int totalPopularity = 0;
        List<Performance> performances = agendaModule.getFestivalDays().get(0).getPerformances();

        for(Performance performance : performances){
            totalPopularity += performance.getPopularity();
        }

        int positionedVisitors = 0;

        for(Performance performance : performances){
            double popularity = (double) performance.getPopularity() / totalPopularity * amountOfVisitors;

            for(int i = 0; i < popularity; i++){
                visitors.get(i + positionedVisitors).setTarget(performance.getPosition());
            }
            positionedVisitors = (int) popularity - 1;
        }
    }
}
