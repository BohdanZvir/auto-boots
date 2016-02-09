package hello.ria.model;

/**
 * Created by bohdan on 09.02.16.
 */
public enum SelectorProps {

    TRANSMISSION,
    MARK_MODEL,
    FUEL,
    ENG_VOLUME,
    COMFORT,
    SAFETY,
    STATE,
    DESCRIPTION,
    PRICE,
    RUN,
    SELLER,
    PHONE_NUMBER,
    CITY,
    ADS_COUNTS,
    ADS_DATE,
    ADS_ID;

    public String getKey() {
        return "selector." + this.toString().toLowerCase();
    }
}
