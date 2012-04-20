package uk.co.probablyfine.jenever.fs;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.util.JeneverOptions;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

public class FileSystem {

	private JeneverOptions options;
	private final Logger log = LoggerFactory.getLogger(FileSystem.class);
	
	@Inject
	public FileSystem(JeneverOptions options) {
		this.options = options;
	}
	
	public void makeLibs(String envName) {
		
		final File file = new File("lib");
				
		log.info("Creating lib folder at {}",file.getAbsolutePath());
		
		if (file.exists()) {
			log.info("File called 'lib' already exists, aborting");
			return;
		}
		
		file.mkdir();
		
		log.info("Successfully created folder, copying jars.");
		
		final File jarDir;
		
		if (null == envName) {
			jarDir = new File(options.jenEnv);
		} else {
			jarDir = new File(options.jenHome+File.separator+envName);
		}
		
		for (final File jar : jarDir.listFiles()) {
			
			if (!jar.getName().endsWith(".jar")) 
				continue;
			
			log.info("Copying {}",jar.getName());
			
			try {
				Files.copy(jar, new File(file.getAbsolutePath()+File.separator+jar.getName()));
			} catch (IOException e) {
				log.error("Error copying across {}, ignoring.",jar.getName());
			}
					
		}
		
		log.info("Lib folder generation complete");
		
	}
	
	public void makeManifest(String envName) {
		
		final File manifestFile = new File("libs.txt");
		
		log.info("Creating manifest file at {}",manifestFile.getAbsolutePath());
		
		if (manifestFile.exists()) {
			log.info("File called 'libs.txt' already exists, aborting");
			return;
		}
		
		try {
			manifestFile.createNewFile();
		} catch (IOException e) {
			log.error("Unable to create file, aborting");
			log.error("Exception: {}",e);
			return;
		}
		
		log.info("Successfully created file, inserting manifest.");
		
		final File jarDir;
		
		if (null == envName) {
			jarDir = new File(options.jenEnv);
		} else {
			jarDir = new File(options.jenHome+File.separator+envName);
		}
		
		Set<String> jarNames = new HashSet<String>();
		
		for (final File jar : jarDir.listFiles()) {
			
			if (!jar.getName().endsWith(".jar")) 
				continue;
			
			log.debug("Adding {} to manifest",jar.getName());
			
			jarNames.add(jar.getName());
					
		}
		
		try {
			log.debug("Writing to file.");
			Files.write(Joiner.on("\n").join(jarNames).getBytes(), manifestFile);
		} catch (IOException e) {
			log.error("Unable to write to file, aborting");
			log.error("Exception: {}",e);
		}
		
		log.info("Manifest generation complete");
		
	}
}
