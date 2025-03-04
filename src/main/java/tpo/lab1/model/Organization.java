package tpo.lab1.model;

public abstract class Organization {
    protected String name;
    protected String symbol;

    public Organization(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
}
