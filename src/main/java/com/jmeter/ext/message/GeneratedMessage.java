package com.jmeter.ext.message;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

/**
 * POJO to store Randomly Generated Message
 * 
 * @author vfedak
 * @since 1.0
 *
 */
public class GeneratedMessage extends ConfigTestElement implements TestBean, LoopIterationListener {

	private static final long serialVersionUID = 1L;
	private String variable;
	private String messageBody;

	@Override
	public void iterationStart(LoopIterationEvent loopIterationEvent) {
		JMeterVariables variables = JMeterContextService.getContext().getVariables();
		variables.put(getVariable(), getMessageBody());
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variableName) {
		this.variable = variableName;
	}


	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

}
