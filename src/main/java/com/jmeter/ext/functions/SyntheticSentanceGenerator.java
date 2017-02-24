package com.jmeter.ext.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

/**
 * Provides a function to return a random Sentence based on lengths oarams
 * The following parameters can be specified in this function 
 * (all params are not mandatory):
 * 
 *  <li>minLength</li> - minimum number of words in sentance
 *  <li>maxLength</li> - Max number of words
 *  <li>variableName</li> - JMeter variable name.
 *  A reference name for reusing the value computed by this function.
 * 
 * 
 * @author vfedak
 * @since 1.0
 */
public class SyntheticSentanceGenerator extends AbstractFunction {
	
	private static final int MAX_PARAMS_COUNT = 3;
	private static final int DEFAULT_SENTANCE_LENGTH = 10;
	private static final int DEFAULT_SENTANCE_MIN_LENGTH = 2;

	static String WORDS = "/words.txt";

	static List<String> dict = null;
    private Object[] values;

	private static final Logger LOGGER = LoggerFactory.getLogger(SyntheticSentanceGenerator.class);
	private static final List<String> DESCRIPTION =  ImmutableList.of("Generates dummy sentance based on sentance length parameters", 
			"Name of variable in which to store the result (optional)");
    private static final String KEY = "__randomSentance";

    Random tRandom = new Random();

	@Override
	public List<String> getArgumentDesc() {
		return DESCRIPTION;
	}
	
	@Override
	public String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {
		JMeterVariables vars = getVariables();
		
		int minValue = parseLength(0, DEFAULT_SENTANCE_MIN_LENGTH);
		int maxValue = parseLength(1, DEFAULT_SENTANCE_LENGTH);
		
		List<String> dict = null;
		try {
			dict = getDict();
		} catch (IOException e) {
			LOGGER.error("Can't read dict file" + e.getMessage());
			e.printStackTrace();
		}
		
		String res = genSetntance(dict, minValue, maxValue);		
		populateJMeterVar(vars, res);
		return res;

	}

	@Override
	public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
		checkParameterCount(parameters, 0, MAX_PARAMS_COUNT);
		
        values = parameters.toArray();
	}
	
	@Override
	public String getReferenceKey() {
        return KEY;
	}
	
	private int parseLength(int position, int dafault) {
		if (values != null && values.length > position) {
			String length = ((CompoundVariable) values[position]).execute().trim();
			try {
				return Integer.parseInt(length);
			} catch (DateTimeParseException e) {
				LOGGER.error("Can't parse specified date: " + e.getMessage());
			}
		}
		return dafault;
	}
	
	private void populateJMeterVar(JMeterVariables vars, String res) {
		if (vars != null && values.length == MAX_PARAMS_COUNT) {
            String varName = ((CompoundVariable) values[MAX_PARAMS_COUNT -1]).execute().trim();
            vars.put(varName, res);
        }
	}
	
	private static synchronized List<String> getDict() throws IOException{		
		if (dict == null) {
			   InputStream is = SyntheticSentanceGenerator.class.getResourceAsStream(WORDS);
			    try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
		            return buffer.lines().collect(Collectors.toList());
		        }
		}		
		return dict;
	}
	
	private String genSetntance(List<String> dict, int minValue, int maxValue) {
		StringBuffer builder = new StringBuffer();

		int sentlength;
		final int COMMA_FREQ = 5;
		final char[] punct = { '.', '.', '.', '?', '!' };

		// Read in the user input.
		sentlength = tRandom.nextInt(maxValue-minValue) + minValue;
		
		if(sentlength < minValue){
			sentlength = minValue;
		}
		
		for (int i = 0; i < sentlength; i++) {
			getRandomWord(dict, builder, COMMA_FREQ);
		}

		// Remove final space and trailing punctuation.
		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1);
		}

		if (builder.length() > 0) {

			if (builder.charAt(builder.length() - 1) == ',') {
				builder.setLength(builder.length() - 1);
			}
		}

		// Add punctuation to the end of the sentence.
		builder.append(punct[tRandom.nextInt(punct.length)]);

		// Uppercase the StringBuilder.
		builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));
		return builder.toString();
	}

	private void getRandomWord(List<String> dict, StringBuffer builder, final int COMMA_FREQ) {
		// Get random index.
		int index = tRandom.nextInt(dict.size());

		// Add a random word to the sentence.
		builder.append(dict.get(index));

		// Sometimes, add a comma.
		if (tRandom.nextInt(COMMA_FREQ) == 0)
			builder.append(",");

		// Add a space after each word.
		builder.append(" ");
	}
	
	
}
