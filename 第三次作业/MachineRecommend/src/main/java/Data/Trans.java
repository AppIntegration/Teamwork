package Data;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SK on 2017/7/10.
 */
public class Trans {
    public static void main(String[] args) {
        DBUtil mongo = new DBUtil();
        DbStore store= new DbStore();

        List<String> id=new ArrayList<>();
        List<String> cname=new ArrayList<>();
        List<String> ename=new ArrayList<>();

        MongoDatabase db=mongo.getDb();
        MongoCollection<Document> docs= db.getCollection("douban");
        FindIterable<Document> documents = docs.find();
        for (Document document : documents) {

            System.out.println(document.get("title"));

//            if(!document.get("year").equals("2016") || !document.get("subtype").equals("movie")){
//                continue;
//            }

            id.add((String) document.get("id"));
            cname.add((String) document.get("title"));
            ename.add((String) document.get("original_title"));
        }

        store.store(id,cname,ename);

    }
}
