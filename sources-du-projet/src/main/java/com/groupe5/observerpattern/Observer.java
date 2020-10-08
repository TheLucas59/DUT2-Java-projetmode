package com.groupe5.observerpattern;

public interface Observer {
	public void update(Observed observed);
	public void update(Observed observed, Object data);;
}
