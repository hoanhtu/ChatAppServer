import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ConnectMongo {

    static String connectionString = System.getProperty("mongodb.uri");
    static CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    static CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
    static MongoClientSettings clientSettings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .codecRegistry(codecRegistry)
            .build();

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {

            MongoDatabase db = mongoClient.getDatabase("player");
            MongoCollection<Messenger> messengerCollection = db.getCollection("Messenger", Messenger.class);
            Messenger messenger = new Messenger("Hello kitty", 23);
            messengerCollection.insertOne(messenger);
            System.out.println("complete");
        }
    }


}
