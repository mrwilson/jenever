package uk.co.probablyfine.jenever.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.download.PackageDownloader;
import uk.co.probablyfine.jenever.fs.FileSystem;

import ch.qos.logback.classic.Level;

import com.google.common.io.Files;
import com.google.inject.Inject;

public class JeneverOptionsHandler {

	private final Logger log = LoggerFactory.getLogger(JeneverOptionsHandler.class);
	private PackageDownloader jd;
	private JeneverOptions options;
	private FileSystem fs;
	
	@Inject
	public JeneverOptionsHandler(JeneverOptions jo, PackageDownloader jd, FileSystem fs) {
		this.options = jo;
		this.jd = jd;
		this.fs = fs;
	}
	
	@SuppressWarnings("static-access")
	public void handle(CommandLine parser) {

		//Handle listing files
		if (parser.hasOption("ls")) {
			final File jenHome = new File(options.jenHome);
			if (jenHome.listFiles().length == 0) {
				log.info("No jen environments found.");
				return;
			}
			for (File f : jenHome.listFiles()) {
				log.info("{}",f.getName());
			}
		}
		
		//Increase verbosity
		if (parser.hasOption("v")) {
			ch.qos.logback.classic.Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(this.log.ROOT_LOGGER_NAME);
			log.setLevel(Level.DEBUG);
		}
		//Decrease verbosity
		if (parser.hasOption("q")) {
			ch.qos.logback.classic.Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(this.log.ROOT_LOGGER_NAME);
			log.setLevel(Level.ERROR);
		}
		
		//Install packages
		if (parser.hasOption("i")) {
			jd.process(parser.getOptionValues("i"));
		}
		
		//Change environment
		if (parser.hasOption("e")) {
			log.debug("Attempting to change environment to {}",parser.getOptionValues("e"));
			writeConfig(parser.getOptionValues("e"));
			log.info("Succesfully changed environment to {}",parser.getOptionValue("e"));
		}
		
		if (parser.hasOption("m")) {
			fs.makeLibs("foo");
		}
		
	}

	public void checkParamsSet() {
		if (!new File(options.jenHome).exists()) {
			log.info("Cannot find JEN_HOME directory at {} - creating...", options.jenHome);
			try {
				log.debug("Creating file ... ");
				final File file = new File(options.jenHome);
				file.mkdir();
				log.info("Successfully created environment directory at {}",options.jenEnv);
			} catch (Exception e) {
				log.error("Error: could not create file at {}, exiting.",options.jenEnv);
				log.error("{}",e);
				System.exit(-1);
			}
		}
		
		if (!new File(options.jenEnv).exists()) {
			log.info("Cannot find current environment directory at {} - creating...", options.jenEnv);
			try {
				log.debug("Creating file ... ");
				final File file = new File(options.jenEnv);
				file.mkdir();
				log.info("Successfully created environment directory at {}",options.jenEnv);
			} catch (Exception e) {
				log.error("Error: could not create file at {}, exiting.",options.jenEnv);
				log.error("{}",e);
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
			log.error("Could not write to config file - {}",e);
		}
		
		
	}
	
}