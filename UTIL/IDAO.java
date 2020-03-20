package com.generation.dao;

import java.math.BigInteger;
import java.util.List;

import com.generation.base.Entity;

public interface IDAO 
{
	static String read = "select * from tabella where id = ?;";
	static String update = "update tabella set ? where id = [id];";
	static String insert = "insert into tabella (chiavi) values (valori);";
	static String delete = "delete from tabella where id = [id]";
	
	public List<Entity> list();
	public List<Entity> list(String filtro);
	public Entity load(BigInteger id);
	public Entity load(Entity e);
	public boolean delete(BigInteger id);
}
