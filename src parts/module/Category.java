package client.module;

public enum Category {
    COMBAT("COMBAT", 1),
    MOVEMENT("MOVEMENT", 2),
    RENDER("RENDER", 3),
    PLAYER("PLAYER", 4),
    MISC("HUD", 5);

    private String name;
    private int id;

    private Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }
}