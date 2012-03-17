package uk.co.probablyfine.jenever.download;

import java.util.List;

public interface XmlParser {

	List<Package> getDependencies(Package p);

}
