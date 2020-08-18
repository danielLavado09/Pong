package model;

import java.io.Serializable;

public class Player implements Serializable {

    private String nick;
    private long score;

    /**
     * Está clase está destinada para la manipulación de los datos de los jugadores.
     * @param nick El "nick" de cada Player.
     */

    public Player(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}