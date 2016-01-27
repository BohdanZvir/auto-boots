package hello.ria.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by bohdan on 04/01/2016.
 */
@Getter
@Setter
public @Data class Payload {

    public Payload() {
    }

    private String name;
    private int value;

    @Override
    public String toString() {
        return "{" + name + '\'' +
                ":" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
