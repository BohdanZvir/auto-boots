package hello.ria.communicator;

import java.net.URI;

/**
 * Created by Bohdan on 04/01/2016.
 */
public interface UrlBuilder {

    String getCategories();

    String getMarks(int category);

    String getModels(int category, int mark);

    String getAvarege(int category, int mark, int model);

    String getOptions(int category);
}
