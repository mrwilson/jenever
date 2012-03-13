package uk.co.probablyfine.jenever;

import java.io.File;

import org.apache.commons.cli.CommandLine;

public class JeneverOptionsHandler {

	public static void handle(CommandLine parser) {
		
		if (parser.hasOption("ls")) {
			
			if (System.getenv("JEN_HOME") == null) {
				System.out.println("JEN_HOME is not set, exiting.");
				return;
			}
						
			File jenHome = new File(System.getenv("JEN_HOME"));
			
			if (jenHome.exists()) {
				for (File f : jenHome.listFiles()) {
					System.out.println(f.getName());
				}
			} else {
				System.out.println(String.format("Cannot find directory %s",System.getenv("JEN_HOME")));
			}
			
			
			
		}
		
	}

}
