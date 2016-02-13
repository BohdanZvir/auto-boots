package hello.ria.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * Created by bohdan on 09.01.16.
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Statistic {

    private int total;
    private double arithmeticMean;
    private int[] prices;
    private Map<String, Integer> percentiles;
    private int[] classifieds;

    public Map<Integer, Integer> buildMap() {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < prices.length; i++ ){
            map.put(prices[i],classifieds[i]);
        }
        return map;
    }
}
