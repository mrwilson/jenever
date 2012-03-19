package uk.co.probablyfine.jenever;

import uk.co.probablyfine.jenever.guice.JeneverModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class JeneverMain {

	public static void main(String args[]) {
		
		Injector injector = Guice.createInjector(new JeneverModule());
		injector.getInstance(Jenever.class).parseArgs(args);
		
	}
	
}