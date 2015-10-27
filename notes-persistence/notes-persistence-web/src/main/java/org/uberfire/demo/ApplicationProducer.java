/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uberfire.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.uberfire.cloud.event.EventDispatcher;
import org.uberfire.cloud.event.EventDispatcherImpl;

import org.uberfire.cloud.rpc.LocalExecution;
import org.uberfire.cloud.rpc.LocalExecutionImpl;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ApplicationProducer {
    
    @Produces
    public LocalExecution getLocalExecution(){
        return new LocalExecutionImpl(null, null);
    }
    
    @Produces
    public EventDispatcher getEventDispatcher(){
        return new EventDispatcherImpl(null, null, null);
    }
}
