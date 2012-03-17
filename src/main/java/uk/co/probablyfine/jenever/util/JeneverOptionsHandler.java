package uk.co.probablyfine.jenever.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.download.PackageDownloader;

import com.google.inject.Inject;


public class JeneverOptionsHandler {

	private final Logger log = LoggerFactory.getLogger(JeneverOptionsHandler.class);
	private PackageDownloader jd;
	
	@Inject
	public JeneverOptionsHandler(PackageDownloader jd) {
		this.jd = jd;
	}
	
	@SuppressWarnings("static-access")
	public void handle(CommandLine parser) {

		//Handle listing files
		if (parser.hasOption("ls")) {
			File jenHome = new File(JeneverOptions.jenHome);

			if (jenHome.listFiles().length == 0) {
				System.out.println("No jen packages found.");
				return;
			}


			for (File f : jenHome.listFiles()) {
					System.out.println(f.getName());
			}
		}
		
		//Increase verbosity
		if (parser.hasOption("v")) {
			org.apache.log4j.Logger log = (org.apache.log4j.Logger) LoggerFactory.getLogger(this.log.ROOT_LOGGER_NAME);
			log.setLevel(Level.DEBUG);
		}
		//Increase verbosity
		if (parser.hasOption("q")) {
			org.apache.log4j.Logger log = (org.apache.log4j.Logger) LoggerFactory.getLogger(this.log.ROOT_LOGGER_NAME);
			log.setLevel(Level.ERROR);
		}
		
		//Install packages
		if (parser.hasOption("i")) {
			jd.process(parser.getOptionValues("i"));
		}
		
	}

	public void checkParamsSet() {
		if (!new File(JeneverOptions.jenHome).exists()) {
			System.out.println(String.format("Cannot find JEN_HOME directory at %s, creating...",JeneverOptions.jenHome));
			try {
				File file = new File(JeneverOptions.jenHome);
				file.mkdir();
	
			} catch (Exception e) {
				System.out.println(String.format("Error: could not create file at , exiting.",JeneverOptions.jenHome));
				System.out.println(e);
				System.exit(-1);
			}
			
			System.out.println("Successfully created JEN_HOME folder.");
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
