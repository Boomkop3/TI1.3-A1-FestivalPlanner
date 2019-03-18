package Data.Tiled.Layer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.awt.geom.Point2D;

public class TiledObject {
	private int height;
	private int width;
	private int x;
	private int y;
	private String name;
	private double rotation;
	private boolean visible;
	private Point2D[] polygon;
	public TiledObject(JsonObject jsonSource){
		this.height = jsonSource.getInt("height");
		this.width = jsonSource.getInt("width");
		this.x = jsonSource.getInt("x");
		this.y = jsonSource.getInt("y");
		this.rotation = jsonSource.getInt("rotation");
		this.name = jsonSource.getString("name");
		this.visible = jsonSource.getBoolean("visible");
		try {
			JsonArray jsonPolygon = jsonSource.getJsonArray("polygon");
			this.polygon = new Point2D[jsonPolygon.size()];
			for (int i = 0; i < jsonPolygon.size(); i++) {
				JsonValue jsonPoint = jsonPolygon.get(i);
				this.polygon[i] = new Point2D.Double(
					((JsonObject) jsonPoint).getInt("x"),
					((JsonObject) jsonPoint).getInt("y")
				);
			}
		}
		catch (Exception ex){}
	}
}