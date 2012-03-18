package uk.co.probablyfine.jenever;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.util.JeneverOptions;
import uk.co.probablyfine.jenever.util.JeneverOptionsHandler;

import com.google.inject.Inject;

public class Jenever {

	private final Logger log = LoggerFactory.getLogger(Jenever.class);
	private JeneverOptionsHandler handler;
	private JeneverOptions jo;
	
	@Inject
	public Jenever(JeneverOptions jo, JeneverOptionsHandler joh) {
		this.jo = jo;
		this.handler = joh;
	}
	
	public void parseArgs(String[] args) {
		Options options = new Options();
		for (Option o : jo.options) {
			options.addOption(o);
		}
		
		CommandLine parser; 
		try {
			parser = new GnuParser().parse(options, args);
		} catch (ParseException e) {
			log.info("Unable to parse options.");
			return;
		}
		
		if (parser.hasOption("help") || parser.getOptions().length == 0) {
			new HelpFormatter().printHelp("jen <args>", "jenever - A package manager for java", options, "");
			System.exit(0);
		} else {
			handler.checkParamsSet();
			handler.handle(parser);
		}
	}
	
	
}
