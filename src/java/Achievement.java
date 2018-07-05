
public abstract class Achievement {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public abstract void add(Achievement a);
    
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	if (name == null) {
	    if (((Achievement) obj).getName() != null)
		return false;
	} else if (!name.equals(((Achievement) obj).getName()))
	    return false;
	return true;
    }
}
