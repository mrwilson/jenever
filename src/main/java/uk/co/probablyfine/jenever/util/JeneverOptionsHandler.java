package uk.co.probablyfine.jenever.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.Jenever;
import uk.co.probablyfine.jenever.download.PackageDownloader;

import com.google.common.io.Files;
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
				System.out.println("No jen environments found.");
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
		
		//Change environment
		if (parser.hasOption("e")) {
			writeConfig(parser.getOptionValues("e"));
		}
		
	}

	public void checkParamsSet() {
		if (!new File(options.jenHome).exists()) {
			System.out.println("Cannot find current environment directory at "+options.jenEnv+" - creating...");
			try {
				File file = new File(options.jenHome);
				file.mkdir();
				System.out.println("Successfully created JEN_ENV folder at "+options.jenEnv+".");
			} catch (Exception e) {
				System.out.println(String.format("Error: could not create file at , exiting.",options.jenHome));
				System.out.println(e);
				System.exit(-1);
			}
		}
		
		if (!new File(options.jenEnv).exists()) {
			System.out.println("Cannot find current environment directory at "+options.jenEnv+" - creating...");
			try {
				File file = new File(options.jenEnv);
				file.mkdir();
				System.out.println("Successfully created environment directory at "+options.jenEnv);
			} catch (Exception e) {
				System.out.println(String.format("Error: could not create file at , exiting.",options.jenEnv));
				System.out.println(e);
				System.exit(-1);
			}
			
		}
		
		writeConfig(new String[] { "default" });
	
	}
	
	public void writeConfig(String[] env) {
		String filename;
		String contents;
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			filename = "config.bat";
			contents = String.format("set JEN_ENV=%s", env[0]);
		} else if (System.getProperty("os.name").startsWith("Linux")) {
			filename = "config";
			contents = String.format("export JEN_ENV=%s", env[0]);
		} else {
			//Handle other os here
			return;
		}
		
		
		try {
			Files.write(contents.getBytes(), new File(options.jenHome+File.separator+filename));
		} catch (IOException e) {
			System.out.println("Could not write to file.");
			e.printStackTrace();
		}
		
		
	}
	
}