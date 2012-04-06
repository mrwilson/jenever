package uk.co.probablyfine.jenever.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

public class JeneverOptions {
	
	public final String jenHome = System.getenv("JEN_HOME") == null ? System.getenv("HOME")+File.separator+".jen" : System.getenv("JEN_HOME"); 
	
	public final String jenEnv = System.getenv("JEN_ENV") == null ? jenHome+File.separator+"default" : jenHome+File.separator+System.getenv("JEN_ENV"); 
	
	public final String BASE_URL="http://repo1.maven.org/maven2";
	
	@SuppressWarnings({ "serial", "static-access" })
	public final List<Option> options = new ArrayList<Option>() {{
		add(new Option("q", "quiet", false, "Decrease verbosity."));
		add(new Option("v", "verbose", false, "Increase verbosity."));
		add(OptionBuilder
				.withLongOpt("install")
				.withDescription("Install package")
				.withArgName("package")
				.hasArg()
				.create("i"));
		add(new Option("ls", "list", false, "List available environments."));
		add(new Option("k","init", false, "Initialise environment folders." ));
		add(OptionBuilder
				.withLongOpt("environment")
				.withDescription("Change environment")
				.withArgName("env")
				.hasArg()
				.create("e"));
	}};
	
}