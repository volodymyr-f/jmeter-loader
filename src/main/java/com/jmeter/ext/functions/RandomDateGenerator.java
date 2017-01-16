package com.jmeter.ext.functions;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedList;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomDateGenerator.class);	
	
    
	private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__randomDate";
    
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_MIN_DATE = "1970-01-01";
    private static final String DEFAULT_MAX_DATE = "2020-01-01";
    
    private String pattern = DEFAULT_PATTERN;
    private static final int PATTERN_PARAM = 2;
    private static final int MAX_PARAMS_COUNT = 4;
    
    DateTimeFormatter formatter = null;
    
    static {
        desc.add("Generates rendom date in specified period");
        desc.add("Name of variable in which to store the result (optional)");
    }
    
    private Object[] values;
    
    private LocalDateTime dMin = null;
    private LocalDateTime dMax = null;
    
    /**
     * No-arg constructor.
     */
    public RandomDateGenerator(){
    	
    }
    
	@Override
	public synchronized List<String> getArgumentDesc() {
		return desc;
	}
	
	@Override
	public synchronized String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {
        JMeterVariables vars = getVariables();        
        setDateRange();
        
        String res = generateDateInRange().format(formatter);

        if (vars != null && values.length == MAX_PARAMS_COUNT) {
            String varName = ((CompoundVariable) values[MAX_PARAMS_COUNT -1]).execute().trim();
            vars.put(varName, res);
        }

        return res;
	}
	
	@Override
	public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
		checkParameterCount(parameters, 0, MAX_PARAMS_COUNT);
        values = parameters.toArray();
	}
	
	@Override
	public synchronized String getReferenceKey() {
        return KEY;
	}
	
	private void setDateRange(){
		if (values == null){
			return;
		}
		String minDate = DEFAULT_MIN_DATE;
		String maxDate = DEFAULT_MAX_DATE;
		
		//If there is defined pattern 
		if(values.length > PATTERN_PARAM){
			pattern = ((CompoundVariable) values[PATTERN_PARAM]).execute().trim();			
		}
		formatter = new DateTimeFormatterBuilder().appendPattern(pattern)
	            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
	            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
	            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
	            .toFormatter(); 
		
		setMinDate(minDate, formatter);
		setMaxDate(maxDate, formatter);		
	}

	private void setMaxDate(String maxDate, DateTimeFormatter formatter) {
		int maxDatePosition = 1;
		if (values.length > maxDatePosition){
			maxDate = ((CompoundVariable) values[maxDatePosition]).execute().trim();					
		}
		dMax =  parseDate(maxDate, formatter);
	}
	
	private void setMinDate(String minDate, DateTimeFormatter formatter) {
		int minDatePosition = 0;
		if (values.length > minDatePosition){
			minDate = ((CompoundVariable) values[minDatePosition]).execute().trim();					
		}
		dMin =  parseDate(minDate, formatter);
	}

	private LocalDateTime parseDate(String stringDate, DateTimeFormatter formatter) {		
		try {
			return  LocalDateTime.parse(stringDate, formatter);
		} catch (DateTimeParseException e) {
			LOGGER.error("Can't parse specified date: "+ e.getMessage());
		}
		return null;
	}
	
	
	public LocalDateTime generateDateInRange() {
		long millis = dMin.until( dMax, ChronoUnit.MILLIS);	
		return dMin.plus(ThreadLocalRandom.current().nextLong(millis), ChronoUnit.MILLIS);		
	}
}
