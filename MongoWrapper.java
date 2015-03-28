import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.*;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;

//This class is responsible for interacting with MongoDB. For example, add a document to a MongoDB collection. 

public class MongoWrapper {

	public static DBCollection col = null;
	public static MongoClient mongoClient = null;

	private static void createDBInstance() {
		try {
			if (col == null) {
				if (mongoClient == null) {
					mongoClient = new MongoClient("localhost", 27017);
				}
				DB db = mongoClient.getDB("twitter");
				col = db.getCollection("twitterCollection");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addToDB(BasicDBObject obj) {
		createDBInstance();
		col.insert(obj);
	}

	protected void finalize() throws Throwable {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

}
