package com.generation.base;

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
	//FACADE : SUPER INTERFACCIA CHE HA IL COMPITO DI NASCONDERE TUTTE LE CLASSI DI DIFFICILE UTILIZZO:
	//CONNECTION, STATEMENT, RESULTSET
	private String percorso;
	private String user;
	private String pw;

	private Connection conn;

	public Database(String percorso, String user, String pw)
	{
		super();
		this.percorso = percorso;
		this.user = user;
		this.pw = pw;

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

	} //CHIUSURA COSTRUTTORE

	public Connection getConn()
	{
		return conn;
	}

	//UNA MAPPA SARA' TRASFORMATA IN UN OGGETTO
	//QUANDO LEGGIAMO DA DB CARICHIAMO TUTTE LE RIGHE DELLE TABELLE
	//QUINDI AVREMO BISOGNO DI UNA LISTA DI MAPPE CORRISPONDENTI A TUTTI I NOSTRI OGGETTI

	public List<Map<String,String>> rows(String query)  //E' UN METODO CHE PERMETTE DI TRASFORMARE TUTTE LE RIGHE DELLA TABELLA
														//IN UNA LISTA DI MAPPE. OGNI MAPPA CORRISPONDE A UNA RIGA DELLA TABELLA
														//CHIAVI(K) = NOME DELLA COLONNA
														//VALORE(V) = CELLA (INCROCIO RIGA*COLONNA)
	{
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();  //CREIAMO LA LISTA DI APPOGGIO, DI RISPOSTA

		Statement s = null; //DICHIARAZIONE DI STATEMENT, STRUMENTO CHE ESEGUE LA QUERY
		try
		{
			s = conn.createStatement(); //CREO LO STATEMENT INRENDO LA CONN (CONNESSIONE)
			//s ESEGUE LA QUERY CHE ARRIVA DAL PARAMETRO
			ResultSet rs = s.executeQuery(query);  //ResultSet CI DA' LA RISPOSTA CHE CORRISPONDE ALL'INTERA TABELLA
												   //RIGHE E METADATI COMPRESI

			while(rs.next())  //CICLIAMO TUTTE LE RIGHE DELLA TABELLA. SE TROVO ANCHE SOLO UNA RIGA, PER ME QUELLA RIGA DEVE DIVENTARE UNA MAPPA.
			{
				Map<String,String>mappa = new HashMap<String,String>(); //CREIAMO UNA MAPPA CHE AVRA' COME CHIAVE UNA STRING
																		//E COME VALORE UNA STRING, PERCHE' ANCHE SE IL VALORE DI UNA COLONNA
																		//E' DI TIPO NUMERICO, UNA STRINGA  PUO' CONTENERE ANHE ALTRO CARATTERI
																		//DIVERSI DALLE LETTERE, QUINDI ANCHE NUMERI E CARATTERI SPECIALI.

				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)//CICLO LE COLONNE. PARTO DA 1 (PERCHE' IN SQL LA COLONNA HA COME INIDICE 1)
																			//SCORRENDO LE COLONNE, SCORRO I METADATI, QUINDI USIAMO rs.getMetaData()
																			//E USIAMO .getCoulumnCount() CHE CI FORNISCE IL NUMERO DELLE COLONNE
				{
					mappa.put(rs.getMetaData().getColumnLabel(i), rs.getString(i));//IL NOME DELLA COLONNA LO RICAVO IN BASE AL NUMERO
																					//DELLA COLONNA CHE STO ANALIZZANDO, i:
																					//getColumnLabel(i) -> PRENDE IL NOME DELLA COLONNA iESIMA(E GLI EVENTUALI ALIAS)
																					//getString(i);-> PRENDE IL VALORE DELLA RIGA ALLA COLONNA iESIMA.

				}//CHIUSURA CICLO FOR

			res.add(mappa); //CARICHIAMO LA MAPPA NELLA LISTA MAPPE


			}//CHIUSURA WHILE
		}//CHIUSURA TRY
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
		}//CHISURA FINALLY
		return res;
	}//CHIUSURA METODO

	public BigInteger update(String sql, String...params) //params E' UN VETTORE. LA DICITURA ... SI LEGGE "VARARGS"
	{
		BigInteger ris = null;
		try
		{
			PreparedStatement p = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for(int i = 0; i<params.length; i++)
				p.setString(i+1,  params[i]);
			p.executeUpdate();
			ResultSet rs = p.getGeneratedKeys();
			while(rs.next())
			ris = BigInteger.valueOf(rs.getInt(1));

		}//CHIUSURA TRY
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
			sostituta += k + "=' "+ modifiche.get(k)+"',";
			chiavi += k + ",";
			valori += "'" + modifiche.get(k)+"',";
		}//CHIUSURA FOR
		sostituta = sostituta.substring(0,sostituta.length()-1);
		chiavi = chiavi.substring(0,valori.length()-1);
		//QUERY UPDATE TABELLA SET ? WHERE ID = NUM
		query = query.replace("chiavi", chiavi);
		query = query.replace("valori", valori);
		query = query.replace("?", sostituta);

		//QUERY UPDATE TABELLA SET CHIAVE='VALORE',CHIAVE='VALORE' WHERE ID = NUM
				return update(query);
	}
}//CHIUSURA CLASS
