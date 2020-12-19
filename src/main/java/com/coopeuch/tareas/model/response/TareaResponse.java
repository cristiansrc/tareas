/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coopeuch.tareas.model.response;

import com.coopeuch.tareas.model.Tarea;

/**
 *
 * @author Cristhiam Reina
 */
public class TareaResponse extends Response {
    private Tarea tarea = new Tarea();

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
    
    
}
