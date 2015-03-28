import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

// The structure of a tweet

public class Tweet {
	private BasicDBObject obj;
	private String userName;
	private Date time;
	private long Id;
	private String text;
	private double lng;
	private double lat;
	
	public Tweet(String userName, Date time, long Id, String text, double lng, double lat) {
		this.userName = userName;
		this.time = time;
		this.Id = Id;
		this.text = text;
		this.lng = lng;
		this.lat = lat;
		obj = new BasicDBObject("userName", this.userName)
				.append("time", this.time)
				.append("Id", this.Id)
				.append("text", this.text)
				.append("location", new BasicDBObject("lng", this.lng).append("lat", this.lat));
	}
	
	public Tweet(DBObject obj) {
		if(obj != null) {
			BasicDBObject temp = (BasicDBObject)obj;
			this.userName = temp.getString("userName");
			this.time = temp.getDate("time");
			this.Id = temp.getLong("Id");
			this.text = temp.getString("text");
			//this.lng = ((BasicDBObject)obj.get("location")).getDouble("lng");
			//this.lat = ((BasicDBObject)obj.get("location")).getDouble("lat");			
		}
	}
	
	public BasicDBObject getDBObject() {
		return obj;
	}
	
	public double getLng() {
		return lng;
	}
	
	public double getLat() {
		return lat;
	}
	
}
