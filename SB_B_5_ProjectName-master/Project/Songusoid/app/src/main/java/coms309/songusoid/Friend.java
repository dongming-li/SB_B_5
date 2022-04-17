package coms309.songusoid;

import java.io.Serializable;

/**
 * Created by Garrett PC on 11/4/2017.
 */

public class Friend implements Serializable{

    String name;

    public Friend(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
