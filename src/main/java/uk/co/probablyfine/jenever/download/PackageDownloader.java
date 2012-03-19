package uk.co.probablyfine.jenever.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import uk.co.probablyfine.jenever.util.JeneverOptions;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

public class PackageDownloader {
	
	private PomParser parser;
	private JeneverOptions options;

	@Inject
	public PackageDownloader(JeneverOptions jo, PomParser parser) {
		this.options = jo;
		this.parser = parser;
	}
	
	public void process(String[] optionValues) {
		for(String packageString : optionValues) {
			 try {
				downloadPackage(new Package(packageString));
			} catch (IOException e) {
				System.out.println("Could not download package "+packageString+", exiting...");
				System.exit(1);
			}
		}
		
	}
	
	public void downloadPackage(Package p) throws IOException {
		List<Package> deps = parser.getDependencies(p);
		
		if (deps.size() > 0) {
			System.out.println(p.toString() + " has " + deps.size() + " dependencies, resolving...");
			for(Package dependency : deps) {
				downloadPackage(dependency);
			}
		}
		
		System.out.println("Downloading "+p.toString()+"...");
		
		String downloadUrl = Joiner.on("/").join(new String[] { 
				options.BASE_URL,
				p.groupId.replaceAll("\\.", "/"),
				p.artifactId,
				p.version,
				p.artifactId+"-"+p.version+".jar"
		});
		
		URL jarRequest = new URL(downloadUrl);

		ReadableByteChannel rbc = Channels.newChannel(jarRequest.openStream());

		FileOutputStream fos = new FileOutputStream(options.jenEnv+File.separator+p.artifactId+"-"+p.version+".jar");

		fos.getChannel().transferFrom(rbc, 0, 1 << 24);

		fos.close();
		
		System.out.println("Download complete.");

		
	}
	
}