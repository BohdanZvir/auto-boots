package hello.ria.service;

import hello.ria.dao.Advertise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bohdan on 13.02.16.
 */
@Repository
public class AdvertiseServiceHSQL implements AdvertiseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Advertise advertise) {
        String sql = "INSERT INTO advertise(name) VALUES(?)";
        return jdbcTemplate.update(sql, advertise.getName());
    }

    public List<Advertise> listAdvertises() {
        return jdbcTemplate.query("SELECT * FROM advertise", new RowMapper<Advertise>() {

            public Advertise mapRow(ResultSet rs, int arg1) throws SQLException {
                Advertise ad = new Advertise();
                ad.setName(rs.getString("name"));
                return ad;
            }

        });
    }

    public int delete(Advertise advertise) {
        String sql = "DELETE FROM advertise WHERE name=?";
        return jdbcTemplate.update(sql,advertise.getName());
    }
}
