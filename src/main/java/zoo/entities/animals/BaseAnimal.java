package zoo.entities.animals;

public abstract class BaseAnimal implements Animal {

    private String name;
    private String kind;
    private double kg;
    private double price;

    public BaseAnimal(String name, String kind, double kg, double price) {
        setName(name);
        setKind(kind);
        setKg(kg);
        setPrice(price);
    }

    private void setName(String name) {
        if (name.trim().isEmpty()) throw new NullPointerException("Animal name cannot be null or empty.");
        this.name = name;
    }

    private void setKind(String kind) {
        if (kind.trim().isEmpty()) throw new NullPointerException("Animal kind cannot be null or empty.");
        this.kind = kind;
    }

    private void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Animal price cannot be below or equal to 0.");
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getKg() {
        return this.kg;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    protected void setKg(double kg) {
        this.kg = kg;
    }
}
