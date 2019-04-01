package Simulation;

import Data.Configuration.Settings;
import Data.Tiled.Layer.TiledObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class Visitor {
    private Point2D position;
    private Point2D speed;
    private double angle;
    private double speedMult;


    private BufferedImage[] tiles;
    private BufferedImage currentImage;

    private TiledObject target;
    private int personalSpace;
    private int uid;

    private Map map;
	private int animationtimer;
	private int animationindex;

    public Visitor(Point2D position) {
		this.uid = UUID.randomUUID().hashCode();
		this.animationindex = 0;
		this.animationtimer = 0;
        this.speed = new Point2D.Double(0,0);
        this.position = position;
        this.angle = 0;
        this.speedMult = (Math.random()*2 + 9)/10;
        this.personalSpace = 28;
        try {
            String link = Settings.rootPath + "\\res\\IMG\\Visitor.png";
            BufferedImage image = ImageIO.read(new File(link));
            tiles = new BufferedImage[24];
            //knip de afbeelding op in 24 stukjes van 32x32 pixels.
            for (int i = 0; i < 24; i++)
                this.tiles[i] = image.getSubimage(32 * (i % 8), 32 * (i / 8), 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.target = null;
    }

    public void update(ArrayList<Visitor> visitors, Map map) {
//        setNewPosition(visitors, map);
		this.map = map;
        updateSpeed();
        updateAngle();
        setCorrectSprite();
        updatePos(visitors);
    }

    public void draw(Graphics2D g, boolean debug) {
        AffineTransform tx = new AffineTransform();
        tx.translate(this.position.getX() - 16, this.position.getY() - 16);
        g.drawImage(this.currentImage, tx, null);
        if (debug){
			g.draw(new Ellipse2D.Double(this.position.getX() - 16, this.position.getY() - 16, 28, 28));
		}
    }

    private void updateAnimationIndex(){
    	if (this.speed.getY() + this.speed.getY() < 0.01){
    		animationindex = 0;
    		return;
		}
		animationtimer += 1;
		if (animationtimer > 8){
			animationtimer = 0;

			this.animationindex += 1;
			if (this.animationindex > 2){
				this.animationindex = 0;
			}
		}
	}

    //updates the info of this visitor
    private void setCorrectSprite() {
    	this.updateAnimationIndex();
        if ((this.angle <= (1 / 8f) * Math.PI && this.angle >= 0) || (this.angle >= (-1 / 8f) * Math.PI) && this.angle <= 0) {
            this.currentImage = this.tiles[8-animationindex];
        } else if (this.angle > (1 / 8f) * Math.PI && this.angle < (3 / 8f) * Math.PI) {
            this.currentImage = this.tiles[5-animationindex];
        } else if (this.angle > (3 / 8f) * Math.PI && this.angle < (5 / 8f) * Math.PI) {
            this.currentImage = this.tiles[2-animationindex];
        } else if (this.angle > (5 / 8) * Math.PI && this.angle < (7 / 8f) * Math.PI) {
            this.currentImage = this.tiles[23-animationindex];
        } else if ((this.angle > ((7 / 8f) * Math.PI) && this.angle < Math.PI) || (this.angle > -Math.PI && this.angle < (-7 / 8f) * Math.PI)) {
            this.currentImage = this.tiles[20-animationindex];
        } else if (this.angle > (-7 / 8f) * Math.PI && this.angle < (-5 / 8f) * Math.PI) {
            this.currentImage = this.tiles[17-animationindex];
        } else if (this.angle > (-5 / 8f) * Math.PI && this.angle < (-3 / 8f) * Math.PI) {
            this.currentImage = this.tiles[14-animationindex];
        } else if (this.angle > (-3 / 8f) * Math.PI && this.angle < (-1 / 8f) * Math.PI) {
            this.currentImage = this.tiles[11-animationindex];
        }
    }

    private void updatePos(ArrayList<Visitor> visitors) {
        Point2D newPosition = new Point2D.Double(this.position.getX() + this.speed.getX(),
                this.position.getY() + this.speed.getY());

		//this.position = newPosition;

        if (!hasCollision(visitors, newPosition)) {
            this.position = newPosition;
        } else {
            checkAlternativePath();
        }
		/**/
    }

    private void checkAlternativePath() {
		Point2D newPosition = new Point2D.Double(this.position.getX() + this.speed.getY(),
			this.position.getY() + this.speed.getX());
		if (!this.map.hasCollision(newPosition)){
			this.position = newPosition;
		}
    }

    private boolean hasCollision(ArrayList<Visitor> visitors, Point2D newPosition) {
        AtomicBoolean hasCollision = new AtomicBoolean(false);
        visitors.parallelStream().forEach( visitor -> {
            if (visitor.hasCollision(newPosition)) {
                hasCollision.set(true);
                if (this.uid == visitor.uid){
                	hasCollision.set(false);
				}
            }
        });
        return hasCollision.get();
    }

    private void updateSpeed() {
    	try {
			if (target != null) {
				int tileX = (int)(position.getX()/32);
				int tileY = (int)(position.getY()/32);
				javafx.geometry.Point2D tileFlow = this.target.getFlowLayer().getFlowMap()[tileX][tileY];

				Point2D newSpeed = new Point2D.Double((speed.getX() * speedMult + tileFlow.getX())/2, (speed.getY() * speedMult + tileFlow.getY())/2);
				this.speed = newSpeed;
			}
			else {
				this.speed = new Point2D.Double(0, 0);
			}
		}
		catch (Exception x) { }
    }

    private void updateAngle() {
        if (this.target != null) {
            Point2D diff = this.speed;
            double targetAngle = Math.atan2(diff.getY(), diff.getX());
            double angleDiff = targetAngle - this.angle;

            while (angleDiff > Math.PI)
                angleDiff -= 2 * Math.PI;
            while (angleDiff < -Math.PI)
                angleDiff += 2 * Math.PI;

            if (angleDiff < -0.1)
                this.angle -= 0.1;
            else if (angleDiff > 0.1)
                this.angle += 0.1;
            else
                this.angle = targetAngle;
        }
    }


    public boolean hasCollision(Point2D otherPosition) {
        return otherPosition.distance(this.position) < this.personalSpace;
    }

    public void setTarget(TiledObject target) {
        this.target = target;
    }

    public Point2D getPosition() {
        return position;
    }
}
