package hello.ria.model;

/**
 * Created by bohdan on 09.02.16.
 */
public enum SelectorProps {

    MARK_MODEL,
    PRICE,
    RUN,
    FUEL,
    ENG_VOLUME,
    CITY,
    TRANSMISSION,
    COMFORT,
    SAFETY,
    DESCRIPTION,
    STATE,
    SELLER,
    PHONE_NUMBER,
    ADS_COUNTS,
    ADS_DATE,
    ADS_ID;

    public String getKey() {
        return "selector." + this.toString().toLowerCase();
    }
}
