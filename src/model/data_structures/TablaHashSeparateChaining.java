package model.data_structures;

import model.logic.Video;

public class TablaHashSeparateChaining <K extends Comparable<K>,V extends Comparable<V>> implements ITablaSimbolos <K,V>
{
	private ILista <ILista<NodoTS<K, V>>> listaNodos;
	private int tamanioActual;
	private int tamanioTabla;
	
	public TablaHashSeparateChaining(int pTamanioIncial, double pFactorCarga) 
	{
		// Cuando llegue al factor de carga se tiene que hacer rehash
		listaNodos = new ArregloDinamico<> (pTamanioIncial);
		tamanioActual = 0;
		tamanioTabla = pTamanioIncial;
		for (int i = 0 ; i < tamanioTabla; i++)
		{
			listaNodos.addLast(null);
		}
	}
	@Override
	public ILista<K> keySet() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILista<V> valueSet() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(K key) 
	{
		V resp = null;
		int posicion = hash(key);
		ILista<NodoTS<K, V>> lista = listaNodos.getElement(posicion);
		if(lista != null )
		{
			for( int i = 0; i <= lista.size()-1 && resp == null; i ++)
			{
				if(lista.getElement(i).getKey().compareTo(key) == 0)
				{
					resp = lista.getElement(i).getValue();
				}
			}
		}
		return resp;
	}

	@Override
	public int size() 
	{
		return tamanioTabla;
	}

	@Override
	public void put(K key, V val)
	{
		int posicion = hash(key);
		System.out.println(posicion);
		ILista<NodoTS<K, V>> lista = listaNodos.getElement(posicion);
		if(lista != null && !contains(key))
		{
			lista.addLast(new NodoTS<K, V>(key, val));
		}
		else
		{
			listaNodos.changeInfo(posicion, new ArregloDinamico<NodoTS<K, V>>(10)); 
			//DEPENDE DE FACTOR DE CARGA
			listaNodos.getElement(posicion).addLast(new NodoTS<K, V>(key, val));
		}
		tamanioActual ++;
	}

	@Override
	public void remove(K key) 
	{
		int posicion = hash(key);
		ILista<NodoTS<K, V>> lista = listaNodos.getElement(posicion);
		if(lista != null)
		{
			for( int i = 1; i <= lista.size(); i ++)
			{
				if(lista.getElement(i).getKey().compareTo(key) == 0)
				{
					lista.changeInfo(i, null);
				}
			}
		}
		tamanioActual --;
	}

	@Override
	public boolean contains(K key) 
	{
		boolean resp = false;
		int posicion = hash(key);
		ILista<NodoTS<K, V>> lista = listaNodos.getElement(posicion);
		if(lista != null )
		{
			for( int i = 0; i <= lista.size()-1; i ++)
			{
				if(lista.getElement(i).getKey().compareTo(key) == 0)
				{
					resp = true;
				}
			}
		}
		return resp;
	}

	@Override
	public boolean isEmpty() 
	{
		return listaNodos.size() == 0;
	}

	@Override
	public int hash(K key) 
	{
		int p = nextPrime(tamanioTabla);
		int a = (int) (Math.random()* (p-1));
		int b = (int) (Math.random()* (p-1));
		return Math.abs((key.hashCode()*a + b) % p) % tamanioTabla;
	}
	
	// Function that returns true if n
    // is prime else returns false
    static boolean isPrime(int n)
    {
        // Corner cases
        if (n <= 1) return false;
        if (n <= 3) return true;
        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
            return false;
        return true;
    }

    // Function to return the smallest
    // prime number greater than N
    static int nextPrime(int N)
    {
        // Base case
        if (N <= 1)
            return 2;
        int prime = N;
        boolean found = false;
        // Loop continuously until isPrime returns
        // true for a number greater than n
        while (!found)
        {
            prime++;
            if (isPrime(prime))
                found = true;
        }
        return prime;

    }

    
	public ILista<Video> getLista(K key) 
	{
		ILista<V> resp = null;
		int posicion = hash(key);
		ILista<NodoTS<K, V>> lista = listaNodos.getElement(posicion);
		if(lista != null )
		{
			resp = (ILista<V>) lista;
		}
		return resp;
	}

}