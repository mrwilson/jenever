package uk.co.probablyfine.jenever;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Joiner;

import uk.co.probablyfine.jenever.download.Package;

public class PackageObjectTest {

	@Test
	public void testCanonicalName() {
		String[] params = new String[]{"foo.bar","baz","1.0"};
		
		Package p = new Package().setSignature(Joiner.on(":").join(Arrays.asList(params)));
		assertEquals(p.groupId,params[0]);
		assertEquals(p.artifactId,params[1]);
		assertEquals(p.version, params[2]);
		assertEquals(p.toString(),"foo.bar.baz-1.0");
	}
	
	@Test	
	public void testNoVersion() {
		String[] params = new String[]{"foo.bar","baz"};
		
		Package p = new Package().setSignature(Joiner.on(":").join(Arrays.asList(params)));
		assertEquals(p.groupId,params[0]);
		assertEquals(p.artifactId,params[1]);
		assertEquals(p.version, null);
		assertEquals(p.toString(),"foo.bar.baz");
	}
	
	@Test	
	public void testJustArtifact() {
		String[] params = new String[]{"baz"};
		
		Package p = new Package().setSignature(Joiner.on(":").join(Arrays.asList(params)));
		assertEquals(p.groupId,null);
		assertEquals(p.artifactId,params[0]);
		assertEquals(p.version, null);
		assertEquals(p.toString(),"baz");
	}
	
}
