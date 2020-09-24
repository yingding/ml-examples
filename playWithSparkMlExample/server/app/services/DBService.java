package services;

import Models.MoodEntry;
import Models.MoodObject;
import com.mongodb.DBObject;
import com.mongodb.LazyDBObject;
import com.mongodb.MongoOptions;
import org.jongo.MongoCursor;
import org.jongo.ResultHandler;
// import play.api.Play;
import play.Logger;
import uk.co.panaxiom.playjongo.PlayJongo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @Author Yingding Wang
 * @lastModified 24.09.2020
 */
@Singleton
public class DBService {
    // Inject PlayJongo Class
    @Inject
    private PlayJongo jongo; // = Play.current().injector().instanceOf(PlayJongo.class);

    private static String MOODS = "moods"; // collection name
    // save a single mood entry

    public boolean saveMood(MoodEntry mood) {
        return jongo.getCollection(MOODS).save(mood).wasAcknowledged();
    }
    // fetch all moods
    public MongoCursor<MoodObject> findAllMoods() {
        logMongoClientInfo();
        // sort _id (timestamp) descending with -1
        return jongo.getCollection(MOODS).find().sort("{_id: -1}").map(moodResultHandler());
    }

    private void logMongoClientInfo() {
        MongoOptions mongoOptions = jongo.mongo().getMongoOptions();
        Logger.info(String.format("ConnectionsPerHost: %d, ConnectionTimeOutMS: %d", mongoOptions.connectionsPerHost, mongoOptions.getConnectTimeout()));
    }

    public long countAllMoods() {
        return jongo.getCollection(MOODS).count("");
    }

    private static ResultHandler<MoodObject> moodResultHandler() {
        return new ResultHandler<MoodObject>() {
            @Override
            public MoodObject map(DBObject result) {
                Object id = result.get("_id");
                long timestamp;
                try {
                    timestamp = (long) ((LazyDBObject) id).get("timestamp");
                } catch (ClassCastException e) {
                    timestamp = (long) id;
                }
                String mood = (String) result.get("mood");
                return new MoodObject(timestamp, mood);
            }
        };
    }
}
