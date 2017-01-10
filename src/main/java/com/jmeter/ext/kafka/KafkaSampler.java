package com.jmeter.ext.kafka;

import java.util.Properties;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * This is a simple Kafka producer that stores messages to Kafka 
 * using defined parameters
 * 
 * @author vfedak
 * @since 1.0
 *
 */
public class KafkaSampler extends AbstractJavaSamplerClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSampler.class);

	private static final String KAFKA_HOSTS = "kafka_brokers";
	private static final String TOPIC_NAME = "default_topic";
	private static final String KAFKA_KEY = "kafka_key";
	private static final String MESSAGE = "message";

	Producer<String, String> kafkaProducer;
	
	@Override
	public void setupTest(JavaSamplerContext javaSamplerContext) {
		Properties props = configureProducerProperties(javaSamplerContext);

        ProducerConfig config = new ProducerConfig(props);   	 
        kafkaProducer = new Producer<String, String>(config);
	}

	@Override
	public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
		SampleResult sampleResult = new SampleResult();
		sampleResult.setDataType(SampleResult.TEXT);
		JMeterVariables variables = JMeterContextService.getContext().getVariables();

		String topic = javaSamplerContext.getParameter(TOPIC_NAME);
		String key = javaSamplerContext.getParameter(KAFKA_KEY);
		String messageBodyKey = javaSamplerContext.getParameter(MESSAGE);
		String message = variables.get(messageBodyKey);
 
		try {
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, key, message);
			kafkaProducer.send(data);
			setSuccess(sampleResult);
		} catch (Exception e) {
			setFailed(sampleResult, e);
		}
		return sampleResult;
	}



	@Override
	public Arguments getDefaultParameters() {
		Arguments defaultParameters = new Arguments();
		defaultParameters.addArgument(KAFKA_HOSTS, KAFKA_HOSTS);
		defaultParameters.addArgument(TOPIC_NAME, TOPIC_NAME);
		defaultParameters.addArgument(KAFKA_KEY, KAFKA_KEY);
		defaultParameters.addArgument(MESSAGE, MESSAGE);
		return defaultParameters;
	}
	
	@Override
	public void teardownTest(JavaSamplerContext context) {
		kafkaProducer.close();
	}

	private Properties configureProducerProperties(JavaSamplerContext javaSamplerContext) {
		Properties props = new Properties();
		props.put("metadata.broker.list", javaSamplerContext.getParameter(KAFKA_HOSTS));
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
		return props;
	}
	
	private void setFailed(SampleResult result, Exception e) {
		LOGGER.error("Request was not successfully processed", e);

		result.sampleEnd();
		result.setSuccessful(false);
		result.setResponseCode(e.getMessage());
	}

	private void setSuccess(SampleResult result) {
		result.sampleEnd();
		result.setSuccessful(true);
		result.setResponseCodeOK();
	}

}
