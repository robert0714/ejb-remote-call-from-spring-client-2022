/*
 * JBoss, Home of Professional Open Source
 * Copyright 2021, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.as.quickstarts.ejb.client;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * REST endpoints that serves as a simple API to invoke the remote EJBs.
 */
@Controller
public class RestEndpoints {

    @Autowired
    private RemoteBeanCaller remoteCaller;
 

    @ResponseBody
    @GetMapping(path = "/client/remote-outbound-stateless",produces =MediaType.APPLICATION_JSON_VALUE) 
    public List<String> toStatelessRemoteOutbound() throws Exception {
        return remoteCaller.remoteOutboundStatelessBeanCall();
    }
 
    @ResponseBody
    @GetMapping(path = "/client/remote-outbound-notx-stateless",produces =MediaType.APPLICATION_JSON_VALUE) 
    public List<String> toStatelessRemoteOutboundNoTx() throws Exception {
        return remoteCaller.remoteOutboundStatelessNoTxBeanCall();
    }
 
    @ResponseBody
    @GetMapping(path = "/client/direct-stateless",produces =MediaType.APPLICATION_JSON_VALUE) 
    
    public List<String> toStatelessDirect() throws Exception {
        return remoteCaller.directLookupStatelessBeanOverEjbRemotingCall();
    }
 
    @ResponseBody
    @GetMapping(path = "/client/direct-stateless-http",produces =MediaType.APPLICATION_JSON_VALUE) 
    public List<String> toStatelessDirectHttp() throws Exception {
        return remoteCaller.directLookupStatelessBeanOverHttpCall();
    }
 
    @ResponseBody
    @GetMapping(path = "/client/remote-outbound-notx-stateful",produces =MediaType.APPLICATION_JSON_VALUE) 
    public List<String> toStatefulRemoteOutboundNoTx() throws Exception {
        return remoteCaller.remoteOutboundStatefulNoTxBeanCall();
    }
 
    @ResponseBody
    @GetMapping(path = "/client/remote-outbound-fail-stateless",produces =MediaType.APPLICATION_JSON_VALUE) 
    public String toFailStatelessRemoteOutbound() throws Exception {
        return remoteCaller.remoteOutboundStatelessBeanFail();
    }
  
}
