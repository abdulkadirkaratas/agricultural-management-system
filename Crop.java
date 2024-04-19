package AgriculturalSystem;

/**
 *
 * @author Abdulkadir Karataş
 */
public abstract class Crop {

    private final String name;
    private int weight;
    private final String cultivatedSeason;

    protected Crop(String name, int weight, String cultivatedSeason) {
        this.name = name;
        this.weight = weight;
        this.cultivatedSeason = cultivatedSeason;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCultivatedSeason() {
        return cultivatedSeason;
    }

    public abstract String consumeIt();

    public abstract void storeIt();

    @Override
    public abstract String toString();

}
