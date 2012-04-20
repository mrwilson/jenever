package uk.co.probablyfine.jenever.download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.co.probablyfine.jenever.util.JeneverOptions;

import com.google.common.base.Joiner;
import com.google.inject.Inject;


public class DefaultDomParser implements PomParser {

	private JeneverOptions options;
	private Logger log = LoggerFactory.getLogger(DefaultDomParser.class);
	
	@Inject
	public DefaultDomParser(JeneverOptions jo) {
		this.options = jo;
	}
	
	public List<Package> getDependencies(Package p) {
		
		final String pom = p.artifactId+"-"+p.version+".pom";
		final String path = p.groupId.replaceAll("\\.", "/");
		
		final String url = Joiner.on("/").join(new String[] {  options.BASE_URL,
				path,
				p.artifactId, 
				p.version, 
				pom });
		
		Document doc;
		try {
			log.debug("Attempting to parse xml file at {}",url);
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			log.error("File does not exist at {}",url);
			return null;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList links;
		try {
			links = (NodeList) xpath.evaluate("//dependencies/dependency[not(scope)]", doc,
			    XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		List<Package> deps = new ArrayList<Package>();
		
		for (int i = 0; i < links.getLength(); i++) {
			Package pa = nodeToPackage(links.item(i));
			if (pa != null) {
				deps.add(pa);
			}
		}
	
		return deps;
	}

	private Package nodeToPackage(Node item) {
		
		Package p = new Package();
		
		NodeList params = item.getChildNodes();
		for (int i = 0; i < params.getLength(); i++) {
			
			Node current = params.item(i);
			if (current instanceof Element) {
				
				if (current.getNodeName().equals("groupId")) {
					p.groupId = current.getFirstChild().getNodeValue();
				}
				
				if (current.getNodeName().equals("artifactId")) {
					p.artifactId = current.getFirstChild().getNodeValue();
				}
				
				if (current.getNodeName().equals("version")) {
					p.version = current.getFirstChild().getNodeValue();
				}
				
			}
			
		}

		if (p.groupId.contains("${") || p.artifactId.contains("${") || p.version.contains("${")) {
			return null;
		}
		
		return p;
	}

	public String mostRecentVersion(Package p) {
		String path = p.groupId.replaceAll("\\.", "/");
		
		String url = Joiner.on("/").join(new String[] {  
				options.BASE_URL, 
				path , 
				p.artifactId, 
				"maven-metadata.xml" });
		
		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			log.error("File does not exist at {}",url);
			return null;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList links;
		try {
			links = (NodeList) xpath.evaluate("//versioning/release", doc,
			    XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return links.item(links.getLength()-1).getChildNodes().item(0).getNodeValue();
			
		}
	}


