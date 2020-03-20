package com.generation.base;

import java.math.BigInteger;
import java.util.List;

import javax.swing.text.html.parser.Entity;

public interface IDAO
{
        static String read = "select * from tabella where id = ?;";
        static String upadate = "update tabella set ? where id = [id];";
        static String insert = "insert into tabella (chiavi) values (valori);";
        static String delete = "delete from tabella where id = [id]";

        public List<Entity> list();
        public Entity load(BigInteger id);
        public Entity load(Entity e);
        public boolean delete(BigInteger id);

}//CHIUSURA INTERFACE
