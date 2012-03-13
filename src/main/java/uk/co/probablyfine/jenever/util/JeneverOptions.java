package uk.co.probablyfine.jenever.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;

public class JeneverOptions {

	public static List<Option> options = new ArrayList<Option>() {{
		
		add(new Option("q", "quiet", false, "Decrease verbosity."));
		add(new Option("v", "verbose", false, "Increase verbosity."));
		add(new Option("mk", "make", false, "Make a virtual environment at the path."));
		add(new Option("ls", "list", false, "List available environments."));
				
		
	}};
	
}
