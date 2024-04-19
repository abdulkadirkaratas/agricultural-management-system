package AgriculturalSystem;

/**
 *
 * @author Abdulkadir Karataş
 */
public class Vegetable extends Crop implements Comparable<Vegetable> {

    private final String cultivatedRegion;
    private final CropKeeper keeper;

    public Vegetable(String name, int weight, String cultivatedRegion, CropKeeper keeper) {
        super(name, weight, null);
        this.cultivatedRegion = cultivatedRegion;
        this.keeper = keeper;
    }

    public String getCultivatedRegion() {
        return cultivatedRegion;
    }

    public CropKeeper getKeeper() {
        return keeper;
    }

    @Override
    public String consumeIt() {
        return "Vegetables are cooked.";
    }

    @Override
    public void storeIt() {
        if (this.keeper instanceof Supplier) {
            ((Supplier) keeper).addCrop(this);
        } else if (this.keeper instanceof Store) {
            try {
                throw new CanNotBeStoredException("Can not be stored.");
            } catch (CanNotBeStoredException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    @Override
    public int compareTo(Vegetable other) {
        if (this.getName().equals(other.getName())) {
            return 0;
        } else {
            return this.getWeight() - other.getWeight();
        }
    }

    @Override
    public String toString() {
        return "\nVegetable{" + "name: " + this.getName() + ", weight: " + this.getWeight()
                + ", cultivatedSeason: " + this.getCultivatedSeason() + ", cultivatedRegion: " 
                + cultivatedRegion + ", How is it consumed? " + this.consumeIt() + '}';
    }

}
