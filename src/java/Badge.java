
public class Badge extends Achievement {

    private static final String PART_OF_COMMUNITY_STR = "PART OF THE COMMUNITY";
    private static final String INVENTOR_STR = "INVENTOR";

    public static final Achievement PART_OF_COMMUNITY = new Badge(PART_OF_COMMUNITY_STR);
    public static final Achievement INVENTOR = new Badge(INVENTOR_STR);

    public Badge(String name) {
	this.name = name;
    }

    @Override
    public void add(Achievement a) {
    }

}
