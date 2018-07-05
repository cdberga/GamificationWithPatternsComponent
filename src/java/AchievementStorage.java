import java.util.List;

public interface AchievementStorage {

    void addAchievement(String user, Achievement a);

    List<Achievement> getAchievements(String user);

    Achievement getAchievement(String user, String achievementName) throws Exception;
    
    void addObserver(AchievementObserver observer);
}
