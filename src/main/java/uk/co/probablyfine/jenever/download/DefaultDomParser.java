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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Joiner;
import com.google.inject.Inject;


public class DefaultDomParser implements XmlParser {

	private final String BASE_URL="http://repo1.maven.org/maven2";
	
	@Inject
	public DefaultDomParser() {}
	
	public List<Package> getDependencies(Package p) {
		
		String pom = p.artifactId+"-"+p.version+".pom";
		String path = p.groupId.replaceAll("\\.", "/");
		
		String url = Joiner.on("/").join(new String[] { BASE_URL, path , p.artifactId, p.version, pom });
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		System.out.println(links);
	
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

}
