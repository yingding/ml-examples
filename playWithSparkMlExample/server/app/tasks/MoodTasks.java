package tasks;

import play.Logger;
import services.DBService;

// JSR330 DI
// https://github.com/google/guice/wiki/JSR330
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wang on 5/9/17.
 */
@Singleton
public class MoodTasks {
    private static final String TAG = MoodTasks.class.getSimpleName();

    @Inject
    private DBService dbService;

    public void countAll() {
        Logger.info("[{}] current total moods is: {}", TAG, this.dbService.countAllMoods());
    }
}
