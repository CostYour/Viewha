package ru.nuyaxov.pzviewha.payload;


//TODO: что блять за объект: DTO, или POJO у меня получился
/**
 * DTO object for getting data about SafeHouses from PZ Server via API
 *
 */
public class SafehouseInfo {

    private String owner; //Owner of Safehouse
    private String title; //Name of Safehouse
    private float x; // current X coordinate of Safehouse
    private float y; // current Y coordinate of Safehouse

    /**
     * Public constructor with all args
     * */
    public SafehouseInfo(String owner, String title, float x, float y) {
        this.owner = owner;
        this.title = title;
        this.x = x;
        this.y = y;
    }

    /**
     * Public constructor with no args
     * */
    public SafehouseInfo() {
        this.owner = "No owner";
        this.title = "no title";
        this.x = 666.0f;
        this.y = 777.0f;
    }

    /**
     * Return owner's name of Safehouse
     *
     * @return {@link String} owner's name
     * */
    public String getOwner() {
        return owner;
    }

    /**
     * Set player name
     *
     * @param owner {@link String} owner's name of Safehouse
     * */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Return name of Safehouse
     *
     * @return {@link String} name of Safehouse
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Set name of Safehouse
     *
     * @param title {@link String}  name of Safehouse
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return x coordinate of Safehouse
     *
     * @return {@link Float} x coordinate of Safehouse
     * */
    public float getX() {
        return x;
    }

    /**
     * Set x coordinate of Safehouse
     *
     * @param x {@link Float} x coordinate of Safehouse
     * */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Return y coordinate of Safehouse
     *
     * @return {@link Float} y coordinate of Safehouse
     * */
    public float getY() {
        return y;
    }

    /**
     * Set y coordinate of Safehouse
     *
     * @param y {@link Float} y coordinate of Safehouse
     * */
    public void setY(float y) {
        this.y = y;
    }
}

