import java.util.List;

public class MemoryAchievementStorage implements AchievementStorage {

    public MemoryAchievementStorage() {
	super();
    }

    @Override
    public void addAchievement(String user, Achievement a){
    }

    @Override
    public List<Achievement> getAchievements(String user) {
	return null;
    }

    @Override
    public Achievement getAchievement(String user, String achievementName) throws Exception {
	return null;
    }

    @Override
    public void addObserver(AchievementObserver observer) {
    }
}
