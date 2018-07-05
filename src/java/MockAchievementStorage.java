import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockAchievementStorage extends MemoryAchievementStorage{

    private Map<String, Map<String, Achievement>> achievementsMap;
    private List<AchievementObserver> observerList;
    private static MockAchievementStorage instance;

    private MockAchievementStorage() {
	super();
	this.achievementsMap = new HashMap<>();
	this.observerList = new ArrayList<>();
    }

    public static MockAchievementStorage getInstance() {
	if (instance == null) {
	    instance = new MockAchievementStorage();
	}
	return instance;
    }

    @Override
    public void addAchievement(String user, Achievement a){
	
	if (achievementsMap.containsKey(user)) {
	    if(achievementsMap.get(user).containsKey(a.getName())){
		Achievement item = achievementsMap.get(user).get(a.getName());
		item.add(a);
	    }
	    else{
		achievementsMap.get(user).put(a.getName(), a);
	    }
	}
	else{
	    Map<String, Achievement> itemMap = new HashMap<>();
	    itemMap.put(a.getName(), a);
	    achievementsMap.put(user, itemMap);
	}

	for (AchievementObserver achievementObserver : observerList) {
	    achievementObserver.achievementUpdate(user, achievementsMap.get(user).get(a.getName()));
	}
    }

    @Override
    public List<Achievement> getAchievements(String user) {
	return achievementsMap.get(user).values().stream().collect(Collectors.toList());
    }

    @Override
    public Achievement getAchievement(String user, String achievementName) throws Exception {
	if (achievementsMap.containsKey(user)) {
	    Map<String, Achievement> mapa = achievementsMap.get(user);
	    return mapa.get(achievementName);
	}
	throw new Exception("Achievement does not exist in this user");
    }

    @Override
    public void addObserver(AchievementObserver observer) {
	observerList.add(observer);
    }

    public void cleanObservers() {
	observerList.clear();
    }

    public void cleanAchievements() {
	achievementsMap.clear();
    }
}
