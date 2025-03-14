package com.library.repository;
import com.library.model.Copy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CopyRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Copy addCopy(Copy copy) {
        String copySql = "INSERT INTO copy (book_id, status_copy_id) VALUES (?, ?)";
        KeyHolder copykeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(copySql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, copy.getBook_id());
            ps.setLong(2,copy.getStatus_copy_id());
            return ps;
        }, copykeyHolder);
        long copyId = copykeyHolder.getKey().longValue();
        // Asignar los IDs generados a los objetos
        copy.setId(copyId);
        return copy;
    }
    public Integer availableCopies(Long book_id) {
        String sql = "SELECT COUNT(id) FROM copy c WHERE c.book_id = ?";
        // Usamos queryForObject() para obtener un único valor como Integer
        return jdbcTemplate.queryForObject(sql, Integer.class, book_id);
    }

    public void changeStatus (Long copy_id,Long status_copy_id ){
        String changeStatusSql= "UPDATE copy SET status_copy_id= ?" +
                " WHERE  id = ?";
        jdbcTemplate.update(changeStatusSql, status_copy_id, copy_id);
    }

}
