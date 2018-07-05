
public class AchievementStorageFactory {

    private static AchievementStorage achievementStorage;

    public static AchievementStorage getAchievementStorage() {
	return achievementStorage;
    }

    static void setAchievementStorage(AchievementStorage a) {
	achievementStorage = a;
    }
}
