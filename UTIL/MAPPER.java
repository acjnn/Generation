package com.generation.base;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;



public interface Mapper //USA JAVA REFLECTION
{
	default public Map<String,String>toMap()
	{
		//MAPPA.PUT("SURNAME","LEPRI");
		Map<String,String> mappa = new HashMap<String,String>();
		for(Method m : this.getClass().getMethods())
		{
			if(m.getName().equalsIgnoreCase("getClass"))
				continue;  //PROSEGUI NELL'ESEGUIRE IL CICLO DALL'INIZIO
			if(m.getName().startsWith("get")) //m.getName() getSurname
			{
				//MAPPA { NAME = GLORIA, SURNAME = LEPRI}
				String nomeProprieta = m.getName().replace("get", "").toLowerCase();

				try
				{
					//PRENDI IL METODO M E INVOCALO SU this.getSurname();
					mappa.put(nomeProprieta, m.invoke(this).toString());
				}
				catch(Exception e)
				{
					//ABBIAMO USATO UN PO' TROPPO LE NOSTRE CAPACITA' DI PROGRAMMATORI
				}

			}

		}//CHIUSURA FOR

		return mappa;
	}
	//VOGLIAMO UTILIZZARE I METODI SET PER ATTRIBUIRE I VALORI ALLE PROPRIETA' DEI NOSTRI OGGETTI DA MAPPA A STATO DELL'OGGETTO
	default public void fromMap(Map<String,String> mappa)
	{
		//mappa = {id = 1, name = Gloria, surname = Lepri, dob = 1990-07-12, conoscenze = Boh}
		for(String k : mappa.keySet())
		{
			for(Method m : this.getClass().getMethods())
			{
				//SetName(String name)  -->m.getParameterCount() --> 1

				//SetId(java.matgh.BigInteger id)
				if(m.getName().startsWith("set") && m.getParameterCount()==1)
				{
					String nomeMetodo = m.getName().replace("set", "").toLowerCase();
					if(k.equalsIgnoreCase(nomeMetodo))
					{
						String tipo = m.getParameters()[0].getType().getSimpleName();
						try
						{
							switch(tipo)
							{
							case "String":
								m.invoke(this,mappa.get(k));
								break;
							case "int":
								m.invoke(this,Integer.parseInt(mappa.get(k)));
								break;
							case "double":
								m.invoke(this,Double.parseDouble(mappa.get(k)));
								break;
							case"BigInteger":
							m.invoke(this,BigInteger.valueOf(Integer.parseInt(mappa.get(k))));
							break;

							}//CHIUSURA SWITCH
						} //CHIUSURA TRY
						catch(Exception e)
						{
							//ABBIAMO USATO UN PO' TROPPO LE NOSTRE CAPACITA' DI PROGRAMMATORI
						}

					} //CHIUSURA 2° IF
				} //CHIUSURA 1° IF
			}
		}
	}
}//CHIUSURA INTERFACE
