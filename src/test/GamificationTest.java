import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamificationTest {

    private ForumService fs;

    public GamificationTest() {
	super();
    }

    @Before
    public void startUp() {
	MockAchievementStorage mockStorage = MockAchievementStorage.getInstance();
	PartOfCommunityAchievementObserver pocObserver = new PartOfCommunityAchievementObserver();
	InventorAchievementObserver inventorObserver = new InventorAchievementObserver();

	mockStorage.addObserver(pocObserver);
	mockStorage.addObserver(inventorObserver);
	AchievementStorageFactory.setAchievementStorage(mockStorage);

	fs = new ForumServiceGamificationProxy(AchievementStorageFactory.getAchievementStorage());
    }
    
    @After
    public void tearDown(){
	MockAchievementStorage mockStorage = MockAchievementStorage.getInstance();
	mockStorage.cleanAchievements();
	mockStorage.cleanObservers();
    }

    @Test
    public void whenAddTopic() throws Exception {
	fs.addTopic("Joao", "Dúvida Java");

	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Points p = (Points)storage.getAchievement("Joao", "CREATION");
	Badge b = (Badge)storage.getAchievement("Joao", "I CAN TALK");

	assertEquals(5, p.getQuantity().intValue());
	assertEquals("CREATION", p.getName());
	assertEquals("I CAN TALK", b.getName());
    }

    @Test
    public void whenAddComment() throws Exception {
	fs.addComment("Maria", "Java", "Num entendi nada");
	
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Points p = (Points)storage.getAchievement("Maria", "PARTICIPATION");
	Badge b = (Badge)storage.getAchievement("Maria", "LET ME ADD");

	assertEquals(3, p.getQuantity().intValue());
	assertEquals("PARTICIPATION", p.getName());
	assertEquals("LET ME ADD", b.getName());
    }

    @Test
    public void whenLikeTopic() throws Exception {
	fs.likeTopic("Antonio", "Ruby", "Joao");

	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Points p = (Points)storage.getAchievement("Antonio", "CREATION");

	assertEquals(1, p.getQuantity().intValue());
	assertEquals("CREATION", p.getName());
    }

    @Test
    public void whenLikeComment() throws Exception{
	fs.likeComment("Andre", "Java", "Num Entendi nada", "Maria");

	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Points p = (Points)storage.getAchievement("Andre", "PARTICIPATION");

	assertEquals(1, p.getQuantity().intValue());
	assertEquals("PARTICIPATION", p.getName());
    }

    @Test
    public void whenTwiceTopicOnceBadge() throws Exception {
	fs.addTopic("Joao", "Dúvida Java");
	fs.addTopic("Joao", "Dúvida Ruby");
	
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Points p = (Points)storage.getAchievement("Joao", "CREATION");
	Badge b = (Badge)storage.getAchievement("Joao", "I CAN TALK");

	assertEquals(10, p.getQuantity().intValue());
	assertEquals("CREATION", p.getName());
	assertEquals("I CAN TALK", b.getName());
    }
    
    @Test
    public void whenMultipleActions() throws Exception{
	fs.addTopic("Joao", "Dúvida Java"); 						// 5, CREATION, I CAN TALK
	fs.addComment("Joao", "Dúvida Java", "Acabei de criar um topico"); 		// 3, PARTICIPATION, LET ME ADD
	fs.addComment("Maria", "Dúvida Java", "Bom topico");				// 3, PARTICIPATION, LET ME ADD
	fs.addTopic("Maria", "Um topico Ruby"); 					// 5, CREATION, I CAN TALK
	fs.addComment("Joao", "Um topico Ruby", "Vou comentar seu topico");		// 3, PARTICIPATION, LET ME ADD
	fs.likeTopic("Antonio", "Dúvida Java", "Joao");					// 1, CREATION
	fs.likeTopic("Antonio", "Um topico Ruby", "Maria");				// 1, CREATION
	fs.likeComment("Andre", "Dúvida Java", "Acabei de criar um topico", "Joao");	// 1, PARTICIPATION
	fs.likeComment("Antonio", "Dúvida Java", "Acabei de criar um topico", "Joao");	// 1, PARTICIPATION
	fs.likeComment("Antonio", "Dúvida Java", "Bom topico", "Maria");		// 1, PARTICIPATION
	
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();

	Points pJoaoCreation = (Points)storage.getAchievement("Joao", "CREATION");
	Points pJoaoPartic = (Points)storage.getAchievement("Joao", "PARTICIPATION");
	Badge bJoaoTalk = (Badge)storage.getAchievement("Joao", "I CAN TALK");
	Badge bJoaoAdd = (Badge)storage.getAchievement("Joao", "LET ME ADD");

	Points pMariaCreation = (Points)storage.getAchievement("Maria", "CREATION");
	Points pMariaPartic = (Points)storage.getAchievement("Maria", "PARTICIPATION");
	Badge bMariaTalk = (Badge)storage.getAchievement("Maria", "I CAN TALK");
	Badge bMariaAdd = (Badge)storage.getAchievement("Maria", "LET ME ADD");

	Points pAntonioCreation = (Points)storage.getAchievement("Antonio", "CREATION");
	Points pAntonioPartic = (Points)storage.getAchievement("Antonio", "PARTICIPATION");
	
	Points pAndrePartic = (Points)storage.getAchievement("Andre", "PARTICIPATION");
	
	assertEquals(5, pJoaoCreation.getQuantity().intValue());
	assertEquals(6, pJoaoPartic.getQuantity().intValue());
	assertNotNull(bJoaoTalk);
	assertNotNull(bJoaoAdd);
	
	assertEquals(5, pMariaCreation.getQuantity().intValue());
	assertEquals(3, pMariaPartic.getQuantity().intValue());
	assertNotNull(bMariaTalk);
	assertNotNull(bMariaAdd);
	
	assertEquals(2, pAntonioCreation.getQuantity().intValue());
	assertEquals(2, pAntonioPartic.getQuantity().intValue());
	
	assertEquals(1, pAndrePartic.getQuantity().intValue());
	
	assertNull((Badge)storage.getAchievement("Antonio", "I CAN TALK"));
	assertNull((Badge)storage.getAchievement("Antonio", "LET ME ADD"));
	assertNull((Badge)storage.getAchievement("Andre", "I CAN TALK"));
	assertNull((Badge)storage.getAchievement("Andre", "LET ME ADD"));
    }
    
    @Test(expected = Exception.class)
    public void whenLaunchError() throws Exception {
	fs.likeComment("Andre", "Java", "Num Entendi nada", "Maria");

	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	storage.getAchievement("Joao", "LET ME ADD");
    }

    @Test
    public void whenReachInventor() throws Exception {
	for (int i=20; i>0; i--) {
	    fs.addTopic("Joao", "Dúvida Java 1");
	}
	
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Badge b = (Badge)storage.getAchievement("Joao", "INVENTOR");
	
	assertNotNull(b);
    }

    @Test
    public void whenReachPartCommunity() throws Exception {
	for (int i=34; i>0; i--) {
	    fs.addComment("Maria", "Ruby", "Meu comentario");
	}
	
	AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	Badge b = (Badge)storage.getAchievement("Maria", "PART OF THE COMMUNITY");
	
	assertNotNull(b);
    }
}
