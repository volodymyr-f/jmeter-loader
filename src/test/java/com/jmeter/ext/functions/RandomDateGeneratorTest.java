package com.jmeter.ext.functions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.InvalidVariableException;
import org.junit.Test;
import org.junit.Assert;

public class RandomDateGeneratorTest {
	
	private RandomDateGenerator dateGenerator = new RandomDateGenerator();
		
    /**
     * Test of execute method, of class UpperCase.
     */
    @Test
    public void testExecuteWithDateRange() throws Exception {
        Collection<CompoundVariable> parameters = new ArrayList<>();
        parameters.add(new CompoundVariable("1970-01-01"));
        parameters.add(new CompoundVariable("2200-01-01"));
        
        dateGenerator.setParameters(parameters);
        String result = dateGenerator.execute(null, null);
        
        LocalDate ldtG = LocalDate.parse(result, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate ldtS = LocalDate.of(1970,1,1);
        LocalDate ldtE = LocalDate.of(2200,1,1);
        
        Assert.assertTrue(ldtG.isAfter(ldtS) || ldtG.isEqual(ldtS));
        Assert.assertTrue(ldtG.isBefore(ldtE) || ldtG.isEqual(ldtE));
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
        LocalDate ldtS = LocalDate.of(1970,1,1);
        LocalDate ldtE = LocalDate.of(2200,1,1);
        
        Assert.assertTrue(ldtG.isAfter(ldtS) || ldtG.isEqual(ldtS));
        Assert.assertTrue(ldtG.isBefore(ldtE) || ldtG.isEqual(ldtE));

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
        LocalDateTime ldtS = LocalDateTime.of(1970,1,1,0,0);
        LocalDateTime ldtE = LocalDateTime.of(2200,1,1,0,0);
        
        Assert.assertTrue(ldtG.isAfter(ldtS) || ldtG.isEqual(ldtS));
        Assert.assertTrue(ldtG.isBefore(ldtE) || ldtG.isEqual(ldtE));


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
        LocalDateTime ldtS = LocalDateTime.of(2017,1,1,0,0);
        LocalDateTime ldtE = LocalDateTime.of(2017,1,1,5,0);
        
        Assert.assertTrue(ldtG.isAfter(ldtS) || ldtG.isEqual(ldtS));
        Assert.assertTrue(ldtG.isBefore(ldtE) || ldtG.isEqual(ldtE));

	}
	


}
