package uk.co.probablyfine.jenever.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;

public class JeneverOptions {

	public static List<Option> options = new ArrayList<Option>() {{
		
		add(new Option("q", "quiet", false, "Decrease verbosity."));
		
		
	}};
	
}
