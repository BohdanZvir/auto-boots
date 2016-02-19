package hello.ria.model;

/**
 * Created by bohdan on 19.02.16.
 */
public enum RequestOptions {

    DAMAGE;
    
    public String getKey() {
        return "request.options." + this.toString().toLowerCase();
    }
}
