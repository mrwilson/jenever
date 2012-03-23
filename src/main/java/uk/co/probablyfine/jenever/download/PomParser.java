package uk.co.probablyfine.jenever.download;

import java.util.List;

public interface PomParser {

	List<Package> getDependencies(Package p);

	String mostRecentVersion(Package p);

}
