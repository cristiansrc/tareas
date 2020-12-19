/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coopeuch.tareas.model.response;

import com.coopeuch.tareas.model.Tarea;
import java.util.List;

/**
 *
 * @author Cristhiam Reina
 */
public class TareasResponse extends Response {
    private List<Tarea> tareas;

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    
}
