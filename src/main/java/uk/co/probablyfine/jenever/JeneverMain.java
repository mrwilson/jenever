package uk.co.probablyfine.jenever;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import uk.co.probablyfine.jenever.util.JeneverOptions;

public class JeneverMain {

	public static void main(String[] args) {
		
		Options options = new Options();
		for (Option o : JeneverOptions.options) {
			options.addOption(o);
		}
		
		CommandLine parser; 
		try {
			parser = new GnuParser().parse(options, args);
		} catch (ParseException e) {
			System.out.println("Unable to parse options.");
			return;
		}
		
		if (parser.hasOption("help") || parser.getOptions().length == 0) {
			new HelpFormatter().printHelp("jen <args>", "jenever - A virtual environment manager for java", options, "");
			System.exit(0);
		} else {
			JeneverOptionsHandler.handle(parser);
		}
	}
	
	
}
