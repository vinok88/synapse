/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.synapse.deployers;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.config.xml.endpoints.EndpointFactory;
import org.apache.synapse.endpoints.Endpoint;

/**
 *  Handles the <code>Endpoint</code> deployment and undeployment tasks
 *
 * @see org.apache.synapse.deployers.AbstractSynapseArtifactDeployer
 */
public class EndpointDeployer extends AbstractSynapseArtifactDeployer {

    private static Log log = LogFactory.getLog(EndpointDeployer.class);

    @Override
    public String deploySynapseArtifact(OMElement artifactConfig, String fileName) {

        if (log.isDebugEnabled()) {
            log.debug("Endpoint Deployment from file : " + fileName + " : Started");
        }

        try {
            Endpoint ep = EndpointFactory.getEndpointFromElement(artifactConfig, false);
            if (ep != null) {
                ep.setFileName(fileName);
                if (log.isDebugEnabled()) {
                    log.debug("Endpoint named '" + ep.getName()
                            + "' has been built from the file " + fileName);
                }
                ep.init(getSynapseEnvironment());
                if (log.isDebugEnabled()) {
                    log.debug("Initialized the endpoint : " + ep.getName());
                }
                getSynapseConfiguration().addEndpoint(ep.getName(), ep);
                if (log.isDebugEnabled()) {
                    log.debug("Endpoint Deployment from file : " + fileName + " : Completed");
                }
                log.info("Endpoint named '" + ep.getName()
                        + "' has been deployed from file : " + fileName);
                return ep.getName();
            } else {
                log.error("Endpoint Deployment Failed. The artifact described in the file "
                        + fileName + " is not an Endpoint");
            }
        } catch (Exception e) {
            log.error("Endpoint Deployment from the file : " + fileName + " : Failed.", e);
        }

        return null;
    }

    @Override
    public String updateSynapseArtifact(OMElement artifactConfig, String fileName,
                                        String existingArtifactName) {

        if (log.isDebugEnabled()) {
            log.debug("Endpoint Update from file : " + fileName + " : Started");
        }

        try {
            Endpoint ep = EndpointFactory.getEndpointFromElement(artifactConfig, false);
            if (ep != null) {
                ep.setFileName(fileName);
                if (log.isDebugEnabled()) {
                    log.debug("Endpoint named '" + ep.getName()
                            + "' has been built from the file " + fileName);
                }
                ep.init(getSynapseEnvironment());
                if (log.isDebugEnabled()) {
                    log.debug("Initialized the endpoint : " + ep.getName());
                }
                Endpoint existingEp
                        = getSynapseConfiguration().getDefinedEndpoints().get(existingArtifactName);
                getSynapseConfiguration().removeEndpoint(existingArtifactName);
                if (!existingArtifactName.equals(ep.getName())) {
                    log.info("Endpoint named " + existingArtifactName + " has been Undeployed");
                }
                getSynapseConfiguration().addEndpoint(ep.getName(), ep);
                existingEp.destroy();
                if (log.isDebugEnabled()) {
                    log.debug("Endpoint " + (existingArtifactName.equals(ep.getName()) ?
                            "update" : "deployment") + " from file : " + fileName + " : Completed");
                }
                log.info("Endpoint named '" + ep.getName()
                        + "' has been " + (existingArtifactName.equals(ep.getName()) ?
                            "update" : "deployed") + " from file : " + fileName);
                return ep.getName();
            } else {
                log.error("Endpoint Update Failed. The artifact described in the file "
                        + fileName + " is not an Endpoint");
            }
        } catch (Exception e) {
            log.error("Endpoint Update from the file : " + fileName + " : Failed.", e);
        }

        return null;
    }

    @Override
    public void undeploySynapseArtifact(String artifactName) {

        if (log.isDebugEnabled()) {
            log.debug("Endpoint Undeployment of the endpoint named : "
                    + artifactName + " : Started");
        }
        
        try {
            Endpoint ep = getSynapseConfiguration().getDefinedEndpoints().get(artifactName);
            if (ep != null) {
                getSynapseConfiguration().removeEndpoint(artifactName);
                if (log.isDebugEnabled()) {
                    log.debug("Destroying the endpoint named : " + artifactName);
                }
                ep.destroy();
                if (log.isDebugEnabled()) {
                    log.debug("Endpoint Undeployment of the endpoint named : "
                            + artifactName + " : Completed");
                }
                log.info("Endpoint named '" + ep.getName() + "' has been undeployed");
            } else {
                log.error("Couldn't find the endpoint named : " + artifactName);
            }
        } catch (Exception e) {
            log.error("Endpoint Undeployement of endpoint named : " + artifactName + " : Failed");
        }
    }
}