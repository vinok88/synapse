package org.apache.synapse.sample;

import org.apache.synapse.mediators.Mediator;
import org.apache.synapse.SynapseConstants;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.AxisFault;

/**
 * Created by IntelliJ IDEA.
 * User: saminda
 * Date: Oct 18, 2005
 * Time: 1:05:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleDummyMediator implements Mediator {

    public boolean mediate(MessageContext messageContext) throws AxisFault {

        System.out.println(
                "you are in the dummy mediator..logging facility ... now\n");
        Boolean retbool = (Boolean) messageContext
                .getProperty(SynapseConstants.VALUE_FALSE);
        return  false;
    }
}
