/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coopeuch.tareas.rest;

import com.coopeuch.tareas.model.Tarea;
import com.coopeuch.tareas.model.response.Response;
import com.coopeuch.tareas.model.response.TareasResponse;
import com.coopeuch.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Cristhiam Reina
 */

@RestController
@RequestMapping("/")
public class TareasRest {
    
    @Autowired
    private TareaRepository repository;
    
    @PostMapping("/tarea")
    public ResponseEntity<Response> tareaSave(@RequestBody Tarea tarea){
        Response response = new Response();
        response.setHttpStatus(HttpStatus.OK);
        repository.insert(tarea);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @PutMapping("/tarea")
    public ResponseEntity<Response> tareaUpdate(@RequestBody Tarea tarea){
        Response response = new Response();
        response.setHttpStatus(HttpStatus.OK);
        repository.update(tarea);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @DeleteMapping("/tarea/{id}")
    public ResponseEntity<Response> tareaDelete(@PathVariable("id") Integer id){
        Response response = new Response();
        response.setHttpStatus(HttpStatus.OK);
        repository.delete(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @GetMapping("/tarea")
    public ResponseEntity<TareasResponse> tareaGet(){
        TareasResponse tareasResponse = new TareasResponse();
        tareasResponse.setHttpStatus(HttpStatus.OK);
        tareasResponse.setTareas(repository.select());
        return new ResponseEntity<>(tareasResponse, tareasResponse.getHttpStatus());
    }
    
}
