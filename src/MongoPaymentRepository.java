import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoPaymentRepository implements PaymentRepository {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoPaymentRepository() {
        client = new MongoClient("localhost", 27017);
        database = client.getDatabase("ecommerce");
        collection = database.getCollection("ecommerce");
        System.out.println("Connected to MongoDB");
        System.out.println("Connected to MongoDB, db = " + database.getName());
        System.out.println("Collection namespace = " + collection.getNamespace());
    }

    @Override
    public void create(Payment p) {
        Document doc = new Document()
                .append("id", p.getId())
                .append("user_id", p.getUserId())
                .append("amount", p.getAmount())
                .append("provider", p.getProvider())
                .append("status", p.getStatus());
        collection.insertOne(doc);
        System.out.println("Total docs after insert = " + collection.countDocuments());
    }

    @Override
    public Payment read(int id) {
        Document filter = new Document("id", id);
        Document doc = collection.find(filter).first();
        if (doc == null) {
            return null;
        }
        return new Payment(
                doc.getInteger("id"),
                doc.getInteger("user_id"),
                doc.getDouble("amount"),
                doc.getString("provider"),
                doc.getString("status")
        );
    }

    @Override
    public List<Payment> readAll() {
        List<Payment> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            Payment p = new Payment(
                    doc.getInteger("id"),
                    doc.getInteger("user_id"),
                    doc.getDouble("amount"),
                    doc.getString("provider"),
                    doc.getString("status")
            );
            list.add(p);
        }
        return list;
    }

    @Override
    public void update(Payment p) {
        Document filter = new Document("id", p.getId());
        Document newData = new Document()
                .append("user_id", p.getUserId())
                .append("amount", p.getAmount())
                .append("provider", p.getProvider())
                .append("status", p.getStatus());
        Document update = new Document("$set", newData);
        collection.updateOne(filter, update);
    }

    @Override
    public void delete(int id) {
        Document filter = new Document("id", id);
        collection.deleteOne(filter);
    }
}
