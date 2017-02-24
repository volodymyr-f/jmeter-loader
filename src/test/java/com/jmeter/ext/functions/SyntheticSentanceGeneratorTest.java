package com.jmeter.ext.functions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.InvalidVariableException;
import org.junit.Test;


public class SyntheticSentanceGeneratorTest {

	private SyntheticSentanceGenerator sentenceGenerator = new SyntheticSentanceGenerator();	
	
    @Test
    public void testExecuteWithDefaultParams() throws Exception {
    	assertThat(sentenceGenerator.execute(null, null).split(" ").length, greaterThan(1));
    	assertThat(sentenceGenerator.execute(null, null).split(" ").length, lessThan(10));
    }
    
	@Test
	public void testTime() throws InvalidVariableException {
        Collection<CompoundVariable> parameters = new ArrayList<>();
        parameters.add(new CompoundVariable("1"));
        parameters.add(new CompoundVariable("5"));
        sentenceGenerator.setParameters(parameters);
                
    	assertThat(sentenceGenerator.execute(null, null).split(" ").length, greaterThan(0));
    	assertThat(sentenceGenerator.execute(null, null).split(" ").length, lessThan(5));
	}
}
