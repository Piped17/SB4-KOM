package dk.sdu.piped17.cbse.common.events;

import dk.sdu.piped17.cbse.common.data.Entity;

import java.io.Serializable;

/**
 *
 * @author Mads
 */
public class Event implements Serializable{
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
