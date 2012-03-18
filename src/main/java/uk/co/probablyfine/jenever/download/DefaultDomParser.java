package uk.co.probablyfine.jenever.download;

import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.google.common.base.Joiner;

public class DefaultDomParser implements XmlParser {

	private final String BASE_URL="http://repo1.maven.org/maven2";
	
	public List<Package> getDependencies(Package p) {
		
		String pom = p.artifactId+"-"+p.version+".pom";
		String path = p.groupId.replaceAll(".", "/") + p.artifactId;
		
		String url = Joiner.on("/").join(new String[] { BASE_URL, path , pom });
		
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
		
		
		
	}

}
