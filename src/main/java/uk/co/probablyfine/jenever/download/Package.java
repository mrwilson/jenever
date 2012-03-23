package uk.co.probablyfine.jenever.download;

public class Package {
	public String artifactId;
	public String groupId;
	public String version;
	
	public Package() {
	}
	
	public Package setSignature(String string) {
		String[] splitted = string.split(":");
		
		if (splitted.length >= 2) {
			this.groupId = splitted[0];
			this.artifactId = splitted[1];
		} else {
			this.artifactId = splitted[0];
		}
		
		if (splitted.length == 3) {
			this.version = splitted[2];
		} else {
			this.version = null;
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		String name = artifactId;
		
		if (groupId != null) {
			name = groupId+"."+name;
		}
		
		if (version != null) {
			name = name+"-"+version;
		}
		return name;
	}
}