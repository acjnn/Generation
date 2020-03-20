package com.generation.db;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements IDatabase
{	
	//Facade -> super interfaccia 
	//ha il compito di nascondere tutte le classi di difficile utilizzo, Connection, Statement, ResultSet
	private String percorso;
	private String user;
	private String pw;
	
	private Connection conn = null;
	
	private static IDatabase db = null;
	
	public synchronized static IDatabase getInstance()
	{
		if(db == null)
			db = new Database();
		return db;
	}
	
	private Database() {}
	
	public void setPercorso(String percorso) {
		this.percorso = percorso;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public void apriConn()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(percorso, user, pw);
		}
		catch(ClassNotFoundException c)
		{
			System.err.println("NON CHIAMARMI SE NON HAI CONTROLLATO IN BUILD PATH SE HAI IL CONNETTORE");
		}
		catch(SQLException s)
		{
			System.err.println("Verifica il nome del database, hai messo quello giusto?");
		}
		return ;
	}
	
	public Connection getConn()
	{
		return conn;
	}
	
	//una mappa sarà trasformata in un oggetto, quando leggiamo da db carichiamo tutte le righe delle tabelle
	//quindi avremo bisogno di una lista di mappe, corrispondenti a tutti i nostri oggetti

	/**
	 * è un metodo che permette di trasformare tutte le righe della tabella in una
	 * lista, composta da mappe. Ogni mappa corrisponde a una riga della tabella
	 * dove le chiavi corrispondo al nome della colonna, e il valore corrisponde
	 * all'incrocio della riga per la colonna -> colonna id, riga 1
	 * mappa { id=1....}
	 */
	public List<Map<String,String>> rows(String query)
	{
		if(conn == null)
			apriConn();
		//creiamo la lista di risposta
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		//dichiaro s come strumento che eseguirà la query, uno Statement
		Statement s = null;
		try
		{
			//usando la connessione conn, creo lo Statement s
			s =  conn.createStatement();
			//s esegue la query che arriva dal parametro, otteniamo una risposta
			//del tipo ResultSet, che corrisponde all'intera tabella
			//righe e metadati compresi
			ResultSet rs = s.executeQuery(query);
			//ciclare tutte le righe della tabella
			while(rs.next())
			{
				//se trovo anche solo una riga, per me quella riga deve diventare una mappa
				//creiamo una mappa che avrà come chiave una String, e come valore una String
				//perché anche se il valore di una colonna è di tipo numerico, una stringa può
				//contenere anche altri caratteri diversi dalle lettere, quindi anche numeri e 
				//caratteri speciali
				Map<String,String> mappa = new HashMap<String,String>();
				//in SQL la prima colonna ha come indice il numero 1
				//quindi non parto a contare da 0, perché andrei prima 
				//della prima colonna, quindi avrei un errore
				//voglio scorrere le colonne, quindi una delle parti dei metadati
				//quindi dobbiamo usare rs.getMetaData(), che ci fornisce il numero
				//delle colonne con il metodo .getColumnCount()
				//così scorriamo tutte le colonne dalla prima all'ultima compresa
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				{
					/* Ogni mappa corrisponde a una riga della tabella
					 * dove le chiavi corrispondo al nome della colonna, 
					 * e il valore corrisponde al valore della colonna di riferimento
					 */
					mappa.put(	rs.getMetaData().getColumnLabel(i), //il nome della colonna lo ricavo in base al numero
															//della colonna che sto analizzando, i:
														//il nome della colonna lo ricavo da getColumnLabel -> prende anche gli alias
								rs.getString(i));		//getString(i), prende il valore della riga alla colonna i-esima
				}
				// finito il ciclo ho riempito tutta la mappa con tutti i dati presi da una riga della tabella
				// mettendo come chiave i nomi delle colonne e il valore il dato vero e proprio
				// quindi carichiamo la mappa nella lista di mappe
				res.add(mappa);
			}
		}
		catch(Exception e)
		{
			System.err.println("Attento alla query");
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				s.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public BigInteger update(String sql, String... params)
	{
		if(conn == null)
			apriConn();
		//Nonostante quei puntini params è visto come un vettore
		//La dicitura ... si legge varargs
		//Esempio sql : insert into artisti (nome, nazionalita) values (?,?)
		//Esempio params:  ["Pippo","Italia"]
		BigInteger ris = null;
		try
		{
			PreparedStatement p = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for(int i=0; i<params.length; i++)
				p.setString(i+1, params[i]);
			p.executeUpdate();
			ResultSet rs = p.getGeneratedKeys();
			while(rs.next())
				ris = BigInteger.valueOf(rs.getInt(1));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ris;
	}
	
	public BigInteger update(String query, Map<String,String> modifiche)
	{
		String sostituta = "";
		String chiavi = "";
		String valori = "";
		for(String k : modifiche.keySet())
		{
			sostituta += k + "='"+ modifiche.get(k)+"',";
			chiavi += k+ ",";
			valori += "'"+modifiche.get(k)+"',";
		}
		sostituta = sostituta.substring(0, sostituta.length()-1);
		chiavi = chiavi.substring(0, chiavi.length()-1);
		valori = valori.substring(0, valori.length()-1);
		//query update tabella set ? where id = num
		query = query.replace("chiavi", chiavi);
		query = query.replace("valori", valori);
		
		query = query.replace("?",sostituta);
		System.out.println(query);
		//query update tabella set chiave='valore',chiave='valore' where id = num
		return update(query);
	}
	
	
}
