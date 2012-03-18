package uk.co.probablyfine.jenever.download;

public class Package {
	public String artifactId;
	public String groupId;
	public String version;
	
	public Package() {
		//Constructor magic here
	}
	
	public Package(String string) {
		String[] splitted = string.split(":");
		this.groupId = splitted[0];
		this.artifactId = splitted[1];
		
		if (splitted.length == 3) {
			this.version = splitted[2];
		} else {
			this.version = null;
		}
	}
	
	@Override
	public String toString() {
		String name = groupId+"."+artifactId;
		if (version != null) {
			name = name+"-"+version;
		}
		return name;
	}
}