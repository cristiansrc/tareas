/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coopeuch.tareas.repository;

import com.coopeuch.tareas.model.Tarea;
import com.coopeuch.tareas.repository.row.mapper.TareaRowMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Cristhiam Reina
 */

@Repository
public class TareaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void insert(Tarea tarea){
        String sql = "INSERT INTO tarea (descripcion, fecha_creacion, vigente) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, tarea.getDescripcion(), 
                tarea.getFechaCreacion(), (tarea.getVigente()) ? 1 : 0);
    }
    
    public void update(Tarea tarea){
        String sql = "\n" +
            "UPDATE tarea\n" +
            "SET descripcion = ?, fecha_creacion = ?, vigente = ?\n" +
            "WHERE id_tarea = ?";
        
        jdbcTemplate.update(sql, tarea.getDescripcion(), 
                tarea.getFechaCreacion(), tarea.getVigente() ? 1 : 0, 
                tarea.getIdTarea());
        
    }
    
    public void delete(Integer id){
        String sql = "DELETE FROM tarea WHERE id_tarea = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public List<Tarea> select(){
        String sql = "SELECT * from tarea order by id_tarea desc";
        return jdbcTemplate.query(sql, new TareaRowMapper());
    }
    
    public Tarea selectOne(Integer id){
        String sql = "SELECT * FROM tarea WHERE id_tarea = ?";
        List<Tarea> tareas = jdbcTemplate.query(sql, new TareaRowMapper(), id);
        return (tareas != null && tareas.size() > 0) ? tareas.get(0) : new Tarea();
    }
    
}
