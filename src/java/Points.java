
public class Points extends Achievement {
    public Points(Integer quantity) {
	super();
	this.quantity = quantity;
    }

    private Integer quantity;

    public Integer getQuantity() {
	return quantity;
    }

    @Override
    public void add(Achievement a) {
	if (this.equals(a)) {
	    Points p = (Points) a;
	    this.quantity += p.quantity;
	}
    }

}
