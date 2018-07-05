
public class ForumServiceGamificationProxy implements ForumService{

    private AchievementStorage storage;
    public ForumServiceGamificationProxy(AchievementStorage storage) {
	this.storage = storage;
    }

    @Override
    public void addTopic(String user, String topic){
	Points p = new Points(5);
	p.setName("CREATION");
	
	Badge b = new Badge("I CAN TALK");
	storage.addAchievement(user, p);
	storage.addAchievement(user, b);
    }

    @Override
    public void addComment(String user, String topic, String comment) {
	Points p = new Points(3);
	p.setName("PARTICIPATION");
	
	Badge b = new Badge("LET ME ADD");

	storage.addAchievement(user, p);
	storage.addAchievement(user, b);
    }

    @Override
    public void likeTopic(String user, String topic, String topicUser) {
	Points p = new Points(1);
	p.setName("CREATION");

	storage.addAchievement(user, p);
    }

    @Override
    public void likeComment(String user, String topic, String comment, String commentUser) {
	Points p = new Points(1);
	p.setName("PARTICIPATION");

	storage.addAchievement(user, p);
    }

}
