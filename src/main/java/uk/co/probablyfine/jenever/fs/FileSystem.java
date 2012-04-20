package uk.co.probablyfine.jenever.fs;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import uk.co.probablyfine.jenever.util.JeneverOptions;

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
		
		if( null == envName) {
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
}
