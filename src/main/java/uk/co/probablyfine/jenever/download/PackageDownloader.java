package uk.co.probablyfine.jenever.download;

import java.util.List;

public class PackageDownloader {

	
	private XmlParser parser;

	public PackageDownloader(XmlParser parser) {
		this.parser = parser;
	}
	
	public void process(String[] optionValues) {
		
		for(String packageString : optionValues) {
			 downloadPackage(new Package(packageString));
		}
		
	}
	
	public void downloadPackage(Package p) {
		List<Package> deps = parser.getDependencies(p);
		
		if (deps.size() > 0) {
			for(Package dependency : deps) {
				downloadPackage(dependency);
			}
		}
		
	}
	
}