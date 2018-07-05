public class PartOfCommunityAchievementObserver implements AchievementObserver {

    private static final String PARTICIPATION = "PARTICIPATION";

    @Override
    public void achievementUpdate(String user, Achievement a) {
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	if (isAPoint(a) && isParticipation(a) && isSuperior100(a)) {
	    storage.addAchievement(user, Badge.PART_OF_COMMUNITY);
	}
    }

    private boolean isAPoint(Achievement a) {
	return a instanceof Points;
    }

    private boolean isSuperior100(Achievement a) {
	return ((Points) a).getQuantity() >= 100;
    }

    private boolean isParticipation(Achievement a) {
	return ((Points) a).getName().equals(PARTICIPATION);
    }

}
