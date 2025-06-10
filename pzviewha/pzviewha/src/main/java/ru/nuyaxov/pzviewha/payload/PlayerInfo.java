package ru.nuyaxov.pzviewha.payload;


/**
 * DTO object for getting data about Sandbox and Server settings from PZ Server via API
 *
 */
public class PlayerInfo {

    private String playerName; //Name of player
    private String role; //Role of player: none, admin //TODO: дописать все
    private String faction; //Player's faction

    private float x; // current X coordinate of player
    private float y; // current Y coordinate of player
    private float z; // current Z coordinate of player (height on map)

    /**
     * Public constructor with all args
     * */
    public PlayerInfo(String playerName, String role, String faction, float x, float y, float z) {
        this.playerName = playerName;
        this.role = role;
        this.faction = faction;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Public constructor with no args
     * */
    PlayerInfo() {
        this.playerName = "No Player";
        this.role = "role";
        this.faction = "faction";
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }


    /**
     * Return player name
     *
     * @return {@link String} player name
     * */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Set player name
     *
     * @param playerName {@link String} name of player
     * */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Return player role
     *
     * @return {@link String} player role
     * */
    public String getRole() {
        return role;
    }

    /**
     * Set player role
     *
     * @param role {@link String} role of player
     * */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Return player faction
     *
     * @return {@link String} player faction
     * */
    public String getFaction() {
        return faction;
    }

    /**
     * Set player faction
     *
     * @param faction {@link String} faction of player
     * */
    public void setFaction(String faction) {
        this.faction = faction;
    }

    /**
     * Return player x coordinate
     *
     * @return {@link Float} player x coordinate
     * */
    public float getX() {
        return x;
    }

    /**
     * Set player x coordinate
     *
     * @param x {@link Float} player x coordinate
     * */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Return player y coordinate
     *
     * @return {@link Float} player y coordinate
     * */
    public float getY() {
        return y;
    }

    /**
     * Set player y coordinate
     *
     * @param y {@link Float} player y coordinate
     * */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Return player z coordinate
     *
     * @return {@link Float} player z coordinate
     * */
    public float getZ() {
        return z;
    }

    /**
     * Set player z coordinate
     *
     * @param z {@link Float} player z coordinate
     * */
    public void setZ(float z) {
        this.z = z;
    }
}

