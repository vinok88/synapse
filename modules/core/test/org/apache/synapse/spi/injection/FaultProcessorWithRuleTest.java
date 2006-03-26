/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/
package org.apache.synapse.spi.injection;

import junit.framework.TestCase;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import org.apache.axis2.transport.http.SimpleHTTPServer;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.synapse.util.Axis2EnvSetup;


public class FaultProcessorWithRuleTest extends TestCase {
    /**
     * this is an incomple test
     * what we should expect an error code
     * but what we receive is 200ok.
     */
    private SimpleHTTPServer synapseServer;
    private EndpointReference targetEpr = new EndpointReference(
            "http://127.0.0.1:5043/axis2/services/anonymous");

    public void setUp() throws Exception {
        ConfigurationContext context = ConfigurationContextFactory
                .createConfigurationContextFromFileSystem(
                        "target/synapse-repository-fault", null);
        synapseServer =
                new SimpleHTTPServer(context, 5043);
        synapseServer.start();
    }

    protected void tearDown() throws Exception {
        synapseServer.stop();
    }

    public void testFaultPrcessor() throws Exception {
        ServiceClient serviceClient = new ServiceClient(
                Axis2EnvSetup.createConfigurationContextFromFileSystem(
                        "target/synapse-repository-fault"), null);
        Options co = new Options();
        co.setTo(targetEpr);
        serviceClient.setOptions(co);
        serviceClient.fireAndForget(Axis2EnvSetup.payload());
    }

}
