package model.data_structures;

import java.awt.Color;

public class NodoRBT <K extends Comparable<K>, V extends Comparable<V>> implements Comparable<NodoRBT<K,V>>
{
	K llave;
	V valor;
	ArregloDinamico<V> valores;
	NodoRBT derecho, izquierdo;

	public Color color;

	public NodoRBT(K llave, V valor)
	{
		this.llave = llave;
		this.valor = valor;
		this.valores = new ArregloDinamico<>(10);
		this.valores.addLast(valor);
		color = Color.RED;
	}

	public int compareTo(NodoRBT<K, V> otro)
	{
		return this.llave.compareTo( otro.llave ); 
	}

	public NodoRBT<K, V> put(K llave, V val)
	{
		int comparacion = llave.compareTo(this.llave);
		if(comparacion == 0)
		{
			this.valores.addLast(val);
		}
		else 
		{
			if(comparacion < 0)
			{
				if(izquierdo != null)
				{
					izquierdo = ((NodoRBT<K, V>)izquierdo).put(llave, val);
				}
				else
				{
					izquierdo = new NodoRBT(llave, val);
				}
			}
			else
			{
				if(derecho != null)
				{
					derecho = ((NodoRBT<K, V>)derecho).put(llave, val);
				}
				else
				{
					derecho = new NodoRBT(llave, val);
				}
			}
		}

		NodoRBT<K, V> raiz = evaluarYcorregir();
		raiz = raiz.evaluarYcorregir();
		return raiz;
	}

	private NodoRBT<K, V> evaluarYcorregir()
	{
		NodoRBT<K, V> raizSubArbol = this;
		NodoRBT<K, V> der = (NodoRBT<K, V>) this.derecho;
		NodoRBT<K, V> izq = (NodoRBT<K, V>) this.izquierdo;
		if(der != null && der.color == Color.RED)
		{
			if(izq != null && izq.color == Color.RED)
			{
				der.color = Color.BLACK;
				izq.color = Color.BLACK;
				this.color = Color.RED;
			}
			else
			{
				raizSubArbol = rotarIzquierda();
			}
		}

		if(izq != null && izq.color == Color.RED && izq.izquierdo != null && izq.izquierdo.color == Color.RED)//Falta
		{
			raizSubArbol = rotarDerecha();
		}
		return raizSubArbol;
	}

	private NodoRBT<K, V> rotarDerecha() 
	{

		NodoRBT<K, V> raizSubArbol = (NodoRBT<K, V>) this.izquierdo;

		Color act = this.color;
		this.color = raizSubArbol.color;
		raizSubArbol.color = act;

		this.izquierdo = raizSubArbol.derecho;
		raizSubArbol.derecho = this;

		return raizSubArbol;
	}

	private NodoRBT<K, V> rotarIzquierda() 
	{
		NodoRBT<K, V> raizSubArbol = (NodoRBT<K, V>) this.derecho;

		Color act = this.color;
		this.color = raizSubArbol.color;
		raizSubArbol.color = act;

		this.derecho = raizSubArbol.izquierdo;
		raizSubArbol.izquierdo = this;

		return raizSubArbol;
	}

	public void cambiarColor(Color pColor) 
	{
		this.color = pColor;
	}

	public ArregloDinamico<V> get(K key) 
	{
		ArregloDinamico<V> val = null;
		int comparacion = key.compareTo(this.llave);
		if(comparacion == 0)
		{
			val = this.valores;
		}
		else 
		{
			if(comparacion < 0)
			{
				if(izquierdo != null)
				{
					val = (ArregloDinamico<V>) izquierdo.get(key);
				}
			}
			else
			{
				if(derecho != null)
				{
					val = (ArregloDinamico<V>) derecho.get(key);
				}
			}
		}
		return val;
	}

	public int size(int actual) 
	{
		actual++;
		NodoRBT<K, V> der = (NodoRBT<K, V>) this.derecho;
		NodoRBT<K, V> izq = (NodoRBT<K, V>) this.izquierdo;

		if(der != null)
		{
			der.size(actual);
		}
		if(izq != null)
		{
			izq.size(actual);
		}
		return actual;
	}

	public int getHeight(K key, int contador) 
	{
		int resp = 0;
		contador++;
		NodoRBT<K, V> der = (NodoRBT<K, V>) this.derecho;
		NodoRBT<K, V> izq = (NodoRBT<K, V>) this.izquierdo;

		if(this.llave.compareTo(key)==0)
		{
			resp = contador;
		}
		else
		{
			if(der != null)
			{
				resp = der.getHeight(key, contador);
			}
			if(izq != null)
			{
				resp = izq.getHeight(key, contador);
			}
		}
		return resp;
	}

	public int height(int actual) 
	{
		int mayor = 1;
		actual++;
		NodoRBT<K, V> der = (NodoRBT<K, V>) this.derecho;
		NodoRBT<K, V> izq = (NodoRBT<K, V>) this.izquierdo;

		if(der != null)
		{
			actual = der.height(actual);
		}
		if(izq != null)
		{
			actual = izq.height(actual);
		}
		return mayor;
	}

	public K min() 
	{
		K resp = this.llave;
		if(this.izquierdo != null)
			resp = (K) izquierdo.min();
		return resp;
	}	
	public K max() 
	{
		K resp = this.llave;
		if(this.derecho != null)
			resp = (K) derecho.min();
		return resp;
	}	
}
