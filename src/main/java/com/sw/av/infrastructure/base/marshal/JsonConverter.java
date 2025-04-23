/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sw.av.infrastructure.base.marshal;

/**
 *
 * @author externoit08
 */
import java.io.IOException;

public interface JsonConverter {

    public Object fromJson(String string, Class<?> type) throws IOException;

    public String toJson(Object o) throws IOException;

    public String toJson(Object o, Class<?> type) throws IOException;
    
    //public Object fromJsonArray(Object o,Class<?> ofType) throws IOException;
}
