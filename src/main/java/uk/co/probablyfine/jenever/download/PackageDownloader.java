package uk.co.probablyfine.jenever.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.probablyfine.jenever.util.JeneverOptions;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

public class PackageDownloader {
	
	private PomParser parser;
	private JeneverOptions options;
	private final Logger log = LoggerFactory.getLogger(PackageDownloader.class);
	
	@Inject
	public PackageDownloader(JeneverOptions jo, PomParser parser) {
		this.options = jo;
		this.parser = parser;
	}
	
	public void process(String[] optionValues) {
		for(String packageString : optionValues) {
			 try {
				log.info("Processing: {}",packageString);
				downloadPackage(new Package().setSignature(packageString));
				log.info("");
			} catch (IOException e) {
				log.info("Could not download package {}, exiting...",packageString);
				System.exit(1);
			}
		}
	}
	
	public void downloadPackage(Package p) throws IOException {
		
		if (p.version == null) {
			p.version = parser.mostRecentVersion(p);
		}
				
		final List<Package> deps;
		try {
			deps = parser.getDependencies(p);
		} catch (RuntimeException e) {
			log.error("Error: Unable to resolve package data from {}, aborting.",p.toString());
			return;
		}
		
		if (deps.size() > 0) {
			log.info("{} has {} dependencies, resolving...",p.toString(),deps.size());
			for(Package dependency : deps) {
				downloadPackage(dependency);
			}
		}
		
		log.info("Downloading {} ...",p.toString());
		
		String downloadUrl = getDownloadUrl(p);
		
		URL jarRequest = new URL(downloadUrl);

		ReadableByteChannel rbc = Channels.newChannel(jarRequest.openStream());

		FileOutputStream fos = new FileOutputStream(options.jenEnv+File.separator+p.artifactId+"-"+p.version+".jar");

		fos.getChannel().transferFrom(rbc, 0, 1 << 24);

		fos.close();
		
		log.info("Download complete.");

		
	}

	private String getDownloadUrl(Package p) {
		
		return Joiner.on("/").join(new String[] { 
				options.BASE_URL,
				p.groupId.replaceAll("\\.", "/"),
				p.artifactId,
				p.version,
				p.artifactId+"-"+p.version+".jar"
		});
		
		
	}
	
}