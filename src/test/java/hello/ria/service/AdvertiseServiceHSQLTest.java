package hello.ria.service;

import hello.ria.dao.Advertise;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by bohdan on 13.02.16.
 */

public class AdvertiseServiceHSQLTest extends AbstractBaseTest {

    @Autowired
    private AdvertiseService advertiseService;

    @Test
    public void save() throws Exception {
        Advertise ad1 = new Advertise();
        ad1.setName("first");
        int save = advertiseService.save(ad1);
        assertTrue(save == 1);
        System.out.println(advertiseService.listAdvertises());
        int delete = advertiseService.delete(ad1);
        assertTrue(delete == 1);
    }

    @Test
    public void listAdvertises() throws Exception {
        List<Advertise> advertises = advertiseService.listAdvertises();
        assertTrue(advertises.isEmpty());
    }
}