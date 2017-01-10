package com.jmeter.ext.message;

import java.beans.PropertyDescriptor;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.gui.TextAreaEditor;

/**
 * Bean that represents two properties:
 * <li>Text Area where user can defined auto-generated message</li>
 * <li>JMeter Variable name where generated message will be stored</li>
 * 
 * @author vfedak
 * @since 1.0
 *
 */
public class GeneratedMessageBeanInfo extends BeanInfoSupport {

  private static final String VARIABLE = "variable";
  private static final String MESSAGE_BODY = "messageBody";

  public GeneratedMessageBeanInfo() {
    super(GeneratedMessage.class);

    createPropertyGroup("message_generator", new String[] {
        VARIABLE, MESSAGE_BODY
    });

    PropertyDescriptor p = property(VARIABLE);    
    p = property(VARIABLE);
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "");
    p.setValue(NOT_EXPRESSION, Boolean.TRUE);
    
    p = property(MESSAGE_BODY);
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "");
    p.setValue(NOT_EXPRESSION, Boolean.TRUE);
    p.setPropertyEditorClass(TextAreaEditor.class);    

  }

}
