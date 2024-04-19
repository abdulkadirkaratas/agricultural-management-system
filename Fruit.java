package AgriculturalSystem;

/**
 *
 * @author Abdulkadir Karataş
 */
public class Fruit extends Crop implements Comparable<Fruit> {

    private final String taste;
    private final double price;
    private final CropKeeper keeper;

    public Fruit(String name, int weight, String cultivatedSeason, String taste, double price, CropKeeper keeper) {
        super(name, weight, cultivatedSeason);
        this.taste = taste;
        this.price = price;
        this.keeper = keeper;
    }

    public String getTaste() {
        return taste;
    }

    public double getPrice() {
        return price;
    }

    public double totalPrice() {
        return (this.price * this.getWeight());
    }

    public CropKeeper getKeeper() {
        return keeper;
    }

    @Override
    public String consumeIt() {
        return "Fruits are consumed raw.";
    }

    @Override
    public void storeIt() {
        if (this.keeper instanceof Supplier) {
            ((Supplier) keeper).addCrop(this);
        } else if (this.keeper instanceof Store) {
            try {
                ((Store) keeper).importCrop(this);
            } catch (CapacityNotEnoughException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public int compareTo(Fruit other) {
        if (this.getName().equals(other.getName())) {
            return 0;
        } else {
            return this.getWeight() - other.getWeight();
        }
    }

    @Override
    public String toString() {
        return "\nFruit{" + "name: " + this.getName() + ", weight: " + this.getWeight()
                + ", cultivatedSeason: " + this.getCultivatedSeason() + ", taste: " + taste
                + ", price: " + price + ", How is it consumed? " + this.consumeIt() + '}';
    }

}
