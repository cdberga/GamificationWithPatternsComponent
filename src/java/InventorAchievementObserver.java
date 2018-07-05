
public class InventorAchievementObserver implements AchievementObserver {

    private static final String CREATION = "CREATION";

    @Override
    public void achievementUpdate(String user, Achievement a){
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	if (isAPoint(a) && isCreation(a) && isSuperior100(a)) {
	    if (!storage.getAchievements(user).contains(Badge.INVENTOR)) {
		storage.addAchievement(user, Badge.INVENTOR);
	    }
	}
    }

    private boolean isAPoint(Achievement a) {
	return a instanceof Points;
    }

    private boolean isSuperior100(Achievement a) {
	return ((Points) a).getQuantity() >= 100;
    }

    private boolean isCreation(Achievement a) {
	return ((Points) a).getName().equals(CREATION);
    }
}
