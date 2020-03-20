package com.generation.base;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public interface Mapper //usa Java Reflection
{
	default public Map<String,String> toMap()
	{
		//mappa.put("surname","Lepri");
		Map<String,String> mappa = new HashMap<String,String>();
		
		for(Method m : this.getClass().getMethods())
		{
			if(m.getName().equalsIgnoreCase("getClass"))
				continue; // prosegui nell'eseguire il ciclo dall'inizio
			
			if(m.getName().startsWith("get")) //m.getName() getSurname
			{
				// mappa { name = gloria, surname = lepri}
				String nomeProprieta = m.getName().replace("get", "").toLowerCase();
									
				try
				{							//prendi il metodo m e invocalo su this -> this.getSurname()
					mappa.put(nomeProprieta, m.invoke(this).toString());
				}
				catch(Exception e)
				{
					//abbiamo usato un po' troppo le nostre capacità di programmatori
				}
			}
			
		}
		return mappa;
	}
	/**
	 * Vogliamo utilizzare i metodi set per attribuire i valori alle proprietà dei nostri oggetti
	 * da mappa a stato dell'oggetto
	 * @param mappa
	 */
	default public void fromMap(Map<String,String> mappa)
	{
		// mappa = {id = 1, name = Gloria, surname = Lepri, dob = 1990-07-12, conoscenze = Boh}
		
		for(String k : mappa.keySet())
		{
			for(Method m : this.getClass().getMethods())
			{										//setName(String name) -> m.getParameterCount() -> 1
													//setId(java.math.BigInteger id)
				if(m.getName().startsWith("set") && m.getParameterCount()==1)
				{
					String nomeMetodo = m.getName().replace("set","").toLowerCase();
					if(k.equalsIgnoreCase(nomeMetodo))
					{
									//m = setId(java.math.BigInteger id)
									//m.getParameters() = [java.math.BigInteger id]
									//m.getParameters()[0] = java.math.BigInteger id
									//m.getParameters()[0].getType() = java.math.BigInteger
									//m.getParameters()[0].getType().getSimpleName() = BigInteger
						// ciò che c'è a destra deve essere uguale a ciò che c'è a sinistra
						 
						String tipo = m.getParameters()[0].getType().getSimpleName();
						try 
						{
							switch(tipo)
							{
								case "String":
									m.invoke(this,mappa.get(k)); //this.m(mappa.get(k))
								break;
								case "int":
									m.invoke(this,Integer.parseInt(mappa.get(k)));
								break;
								case "double":
									m.invoke(this,Double.parseDouble(mappa.get(k)));
								break;
								case "BigInteger":
									m.invoke(this,BigInteger.valueOf(Integer.parseInt(mappa.get(k))));
								break;
							}
						}
						catch(Exception e)
						{
							//abbiamo usato un po' troppo le nostre capacità di programmatori
						}
					}
				}
			}
		}
	}
}
