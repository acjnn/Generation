package com.generation.base;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IDatabase
{
    public List<Map<String,String>>rows(String query);
    //DATO UNO STRING SQL RESTITUISCE UNA RIGA DEL DATABASE
    default public Map<String,String>row(String sql)
    {
        try
        {
            return rows(sql).get(0);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public BigInteger update(String sql, String...mappa);
    public BigInteger update(String sql, Map<String,String>mappa);
}//CHIUSURA INTERFACE
