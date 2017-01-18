package com.jmeter.ext.functions;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
 * Provides a function to return a random Date chosen from a range.
 * The following parameters can be specified in this function 
 * (all params are not mandatory):
 * 
 *  <li>startDate</li> - Begging of the Time Range
 *  <li>endDate</li> - Ending of Time Range
 *  <li>pattern</li> - Pattern which will be used to parse data
 *  <li>variableName</li> - JMeter variable name.
 *  A reference name for reusing the value computed by this function.
 * 
 * 
 * @author vfedak
 * @since 1.0
 */
public class RandomDateGenerator extends AbstractFunction  {	
	
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";


	private static final Logger LOGGER = LoggerFactory.getLogger(RandomDateGenerator.class);	
	
    
	private static final List<String> DESCRIPTION =  ImmutableList.of("Generates rendom date in specified period", 
			"Name of variable in which to store the result (optional)");
    private static final String KEY = "__randomDate";
    
    private static final String DEFAULT_MIN_DATE = "1970-01-01";
    private static final String DEFAULT_MAX_DATE = "2020-01-01";    

    private static final int PATTERN_PARAM = 2;
    private static final int MAX_PARAMS_COUNT = 4;
    
    private Object[] values;    
    
	@Override
	public List<String> getArgumentDesc() {
		return DESCRIPTION;
	}
	
	@Override
	public String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {
        JMeterVariables vars = getVariables();        
        DateTimeFormatter formatter  = getFormatter();
        
        LocalDateTime dMin = parseDate(formatter, DEFAULT_MIN_DATE, 0);
        LocalDateTime dMax = parseDate(formatter, DEFAULT_MAX_DATE, 1);	
        
        String res = generateDateInRange(dMin, dMax, formatter);

        populateJMeterVar(vars, res);

        return res;
	}

	private void populateJMeterVar(JMeterVariables vars, String res) {
		if (vars != null && values.length == MAX_PARAMS_COUNT) {
            String varName = ((CompoundVariable) values[MAX_PARAMS_COUNT -1]).execute().trim();
            vars.put(varName, res);
        }
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
	
	private DateTimeFormatter getFormatter(){
	
		String pattern = DEFAULT_DATE_PATTERN; 
		
		//If there is defined pattern 
		if(values.length > PATTERN_PARAM){
			pattern = ((CompoundVariable) values[PATTERN_PARAM]).execute().trim();			
		}
		return new DateTimeFormatterBuilder().appendPattern(pattern)
	            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
	            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
	            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
	            .toFormatter();
	}

	private LocalDateTime parseDate(DateTimeFormatter formatter, String date, int datePosition) {
		if (values.length > datePosition) {
			date = ((CompoundVariable) values[datePosition]).execute().trim();
			try {
				return LocalDateTime.parse(date, formatter);
			} catch (DateTimeParseException e) {
				LOGGER.error("Can't parse specified date: " + e.getMessage());
			}
		}
		return null;
	}

	private String generateDateInRange(LocalDateTime dMin, LocalDateTime dMax, DateTimeFormatter formatter) {
		long millis = dMin.until(dMax, ChronoUnit.MILLIS);
		return dMin.plus(ThreadLocalRandom.current().nextLong(millis), ChronoUnit.MILLIS).format(formatter);
	}
}
