package org.sus.object;

public class FortnitePlayer {

    private String id;
    private String name;
    private String platform;

    public FortnitePlayer(String id, String name, String platform) {
        this.id = id;
        this.name = name;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
