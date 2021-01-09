package com.groupe5.observerpattern;
/**
 * Pattern observé
 * @author plel
 * @author duhayona
 */
public interface Observer {
	public void update(Observed observed);
	public void update(Observed observed, Object data);
}
