package uk.co.probablyfine.jenever.guice;

import uk.co.probablyfine.jenever.Jenever;
import uk.co.probablyfine.jenever.download.DefaultDomParser;
import uk.co.probablyfine.jenever.download.PackageDownloader;
import uk.co.probablyfine.jenever.download.XmlParser;
import uk.co.probablyfine.jenever.util.JeneverOptionsHandler;

import com.google.inject.AbstractModule;

public class JeneverModule extends AbstractModule{

	@Override
	protected void configure() {
		
		bind(Jenever.class);
		bind(JeneverOptionsHandler.class);
		bind(PackageDownloader.class);
		bind(XmlParser.class).to(DefaultDomParser.class);
		
	}

}
