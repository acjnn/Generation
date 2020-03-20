package com.generation.base;

import java.math.BigInteger;

public abstract class Entity implements Mapper
{
    public static final int IDMIN = 1;
    //LE POPRIETA' E LO STATO DELL'OGGETTO CI SONO SOLO NELLE CLASSI ASTRATTE NON NELLE INTERFACCE
    //ED E' QUESTA LA PRINCIPALE DIFFERENZA TRA CLASSI ASTRATTE E INTERFACCE

    protected BigInteger id;
    public Entity(int id)
    {

        this.id = BigInteger.valueOf(id);
    }
    public BigInteger getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = BigInteger.valueOf(id);
    }

    // public abstract String --> DOVRA' ESSERE IMPLEMENTATO DA FIGLI CONCRETI

}//CHIUSURA CLASS
