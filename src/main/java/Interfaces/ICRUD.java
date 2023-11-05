package Interfaces;

public interface ICRUD {
    public abstract void add(Object obj);

    public abstract void update(String ID);

    public abstract void delete(String ID);
}
