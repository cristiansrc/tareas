/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coopeuch.tareas.repository.row.mapper;

import com.coopeuch.tareas.model.Tarea;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Cristhiam Reina
 */
public class TareaRowMapper implements RowMapper<Tarea> {

    @Override
    public Tarea mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Tarea tarea = new Tarea();
        
        tarea.setIdTarea(rs.getInt("id_tarea"));
        tarea.setDescripcion(rs.getString("descripcion"));
        tarea.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        tarea.setVigente(rs.getInt("vigente") == 1);
        
        return tarea;
    }
    
}
