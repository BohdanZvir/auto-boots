package hello.ria.service;

import hello.ria.dao.Advertise;

import java.util.List;

/**
 * Created by bohdan on 13.02.16.
 */
public interface AdvertiseService {

    int save(Advertise advertise);
    List<Advertise> listAdvertises();
    int delete(Advertise advertise);
}
