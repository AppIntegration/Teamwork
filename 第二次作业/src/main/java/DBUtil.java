import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

/**
 * Created by kylin on 08/06/2017.
 * All rights reserved.
 */
public class DBUtil {

    private MongoClient mongoClient;

    private MongoDatabase db;

    private String dbName = "douban";

    private String collectionName = "movie";

    public DBUtil() {
        // Connect to a MongoDB instance running on the localhost on the default port 27017:
        mongoClient = new MongoClient();

        // Once successfully connected, access the test database:
        db = mongoClient.getDatabase(dbName);
    }

    public boolean saveListOfMovie(String json) {
        Document document = Document.parse(json);
        List<Document> movieList = (List<Document>) document.get("subjects");
        db.getCollection(collectionName).insertMany(movieList);
        return true;
    }
}
