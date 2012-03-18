package uk.co.probablyfine.jenever.util;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.download.PackageDownloader;

import com.google.inject.Inject;

public class JeneverOptionsHandler {

	private final Logger log = LoggerFactory.getLogger(JeneverOptionsHandler.class);
	private PackageDownloader jd;
	private JeneverOptions options;
	
	@Inject
	public JeneverOptionsHandler(JeneverOptions jo, PackageDownloader jd) {
		this.options = jo;
		this.jd = jd;
	}
	
	@SuppressWarnings("static-access")
	public void handle(CommandLine parser) {

		//Handle listing files
		if (parser.hasOption("ls")) {
			File jenHome = new File(options.jenHome);

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
			System.out.println(parser.getOptionValue("i"));
			jd.process(parser.getOptionValues("i"));
		}
		
	}

	public void checkParamsSet() {
		if (!new File(options.jenHome).exists()) {
			System.out.println(String.format("Cannot find JEN_HOME directory at %s, creating...",options.jenHome));
			try {
				File file = new File(options.jenHome);
				file.mkdir();
			} catch (Exception e) {
				System.out.println(String.format("Error: could not create file at , exiting.",options.jenHome));
				System.out.println(e);
				System.exit(-1);
			}
			
			System.out.println("Successfully created JEN_HOME folder.");
			try {
				File file = new File(options.jenEnv);
				file.mkdir();
			} catch (Exception e) {
				System.out.println(String.format("Error: could not create file at , exiting.",options.jenEnv));
				System.out.println(e);
				System.exit(-1);
			}
			
			System.out.println("Successfully created JEN_HOME/default.");
			
			
		}
		
		
		
	}
	
}
