package com.jmeter.ext.functions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.InvalidVariableException;
import org.junit.Test;

import static org.exparity.hamcrest.date.LocalDateMatchers.*;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Assert;

public class RandomDateGeneratorTest {
	
	private RandomDateGenerator dateGenerator = new RandomDateGenerator();
		
    @Test
    public void testExecuteWithDateRange() throws Exception {
        Collection<CompoundVariable> parameters = new ArrayList<>();
        parameters.add(new CompoundVariable("1970-01-01"));
        parameters.add(new CompoundVariable("2200-01-01"));
        
        dateGenerator.setParameters(parameters);
        String result = dateGenerator.execute(null, null);
        
        LocalDate ldtG = LocalDate.parse(result, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
 
        assertThat(ldtG, is(not(before(1970, Month.JANUARY, 1))));
        assertThat(ldtG, is(not(after(2200, Month.JANUARY, 1))));
    }

	@Test
	public void testExecuteWithDateRangeSlashPattern() throws InvalidVariableException {		
        Collection<CompoundVariable> parameters = new ArrayList<>();
        parameters.add(new CompoundVariable("1970/01/01"));
        parameters.add(new CompoundVariable("2200/01/01"));
        parameters.add(new CompoundVariable("yyyy/MM/dd"));
        
        dateGenerator.setParameters(parameters);

        String result = dateGenerator.execute(null, null);
        
        LocalDate ldtG = LocalDate.parse(result, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        assertThat(ldtG, is(not(before(1970, Month.JANUARY, 1))));
        assertThat(ldtG, is(not(after(2200, Month.JANUARY, 1))));
	}
	
	
	@Test
	public void testTime() throws InvalidVariableException {		
        Collection<CompoundVariable> parameters = new ArrayList<>();
        parameters.add(new CompoundVariable("1970/01/01 00:00:00"));
        parameters.add(new CompoundVariable("2200/01/01 00:00:00"));
        parameters.add(new CompoundVariable("yyyy/MM/dd HH:mm:ss"));
        
        dateGenerator.setParameters(parameters);

        String result = dateGenerator.execute(null, null);
        
        LocalDateTime ldtG = LocalDateTime.parse(result, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        assertThat(ldtG,  is(not(after(2200, Month.JANUARY, 1, 0, 0, 0))));
        assertThat(ldtG,  is(not(before(1970, Month.JANUARY, 1, 0, 0, 0))));
	}
	
	
	@Test
	public void testTimeOneDayRange() throws InvalidVariableException {		
        Collection<CompoundVariable> parameters = new ArrayList<>();
        parameters.add(new CompoundVariable("2017/01/01 00:00:00"));
        parameters.add(new CompoundVariable("2017/01/01 05:00:00"));
        parameters.add(new CompoundVariable("yyyy/MM/dd HH:mm:ss"));
        
        dateGenerator.setParameters(parameters);

        String result = dateGenerator.execute(null, null);
        
        LocalDateTime ldtG = LocalDateTime.parse(result, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
       
        assertThat(ldtG,  is(not(after(2017, Month.JANUARY, 1, 5, 0, 0))));
        assertThat(ldtG,  is(not(before(2017, Month.JANUARY, 1, 0, 0, 0))));
	}	

	
    /**
     * Test of getReferenceKey method
     */
    @Test
    public void testGetReferenceKey() {
        String expResult = "__randomDate";
        String result = dateGenerator.getReferenceKey();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getArgumentDesc method
     */
    @Test
    public void testGetArgumentDesc() {
        List<String> result = dateGenerator.getArgumentDesc();
        Assert.assertEquals(2, result.size());
    }
}
