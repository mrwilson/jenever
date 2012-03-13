package uk.co.probablyfine.jenever.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;


public class JeneverOptionsHandler {

	private final Logger log = LoggerFactory.getLogger(JeneverOptionsHandler.class);
	
	@Inject
	public JeneverOptionsHandler() {}
	
	@SuppressWarnings("static-access")
	public void handle(CommandLine parser) {
		
		//Handle listing files
		if (parser.hasOption("ls")) {
			File jenHome = new File(JeneverOptions.jenHome);
			if (jenHome.exists()) {
				for (File f : jenHome.listFiles()) {
					System.out.println(f.getName());
				}
			} else {
				System.out.println(String.format("Cannot find directory %s",System.getenv("JEN_HOME")));
				return;
			}
		}
		
		//Increase verbosity
		if (parser.hasOption("v")) {
			org.apache.log4j.Logger log = (org.apache.log4j.Logger) LoggerFactory.getLogger(this.log.ROOT_LOGGER_NAME);
			log.setLevel(Level.DEBUG);
		}
		//Increase verbosity
		if (parser.hasOption("v")) {
			org.apache.log4j.Logger log = (org.apache.log4j.Logger) LoggerFactory.getLogger(this.log.ROOT_LOGGER_NAME);
			log.setLevel(Level.ERROR);
		}
		
		
	}

	public List<Option> getOptions() {
		return JeneverOptions.options;
	}
	
	public String getJenHome() {
		return JeneverOptions.jenHome;
	}
	
	

}

class JeneverOptions {

	public static String jenHome = System.getenv("JEN_HOME") == null ? System.getenv("HOME")+File.pathSeparator+".jen" : System.getenv("JEN_HOME"); 
	
	@SuppressWarnings("serial")
	public static List<Option> options = new ArrayList<Option>() {{
		
		add(new Option("q", "quiet", false, "Decrease verbosity."));
		add(new Option("v", "verbose", false, "Increase verbosity."));
		add(new Option("i", "install", false, "Install package."));
		add(new Option("ls", "list", false, "List available environments."));
		
	}};
	
}
