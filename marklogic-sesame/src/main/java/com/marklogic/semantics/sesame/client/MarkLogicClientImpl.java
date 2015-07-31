/*
 * Copyright 2015 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * A library that enables access to a MarkLogic-backed triple-store via the
 * Sesame API.
 */
package com.marklogic.semantics.sesame.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.Transaction;
import com.marklogic.client.impl.SPARQLBindingsImpl;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.query.RawCombinedQueryDefinition;
import com.marklogic.client.semantics.*;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.Binding;
import org.openrdf.repository.sparql.query.SPARQLQueryBindingSet;
import org.openrdf.rio.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 *
 * @author James Fuller
 */
public class MarkLogicClientImpl {

    protected final Logger logger = LoggerFactory.getLogger(MarkLogicClientImpl.class);

    public static final String DEFAULT_GRAPH_URI = "http://marklogic.com/semantics#default-graph";

    private String host;

    private int port;

    private String user;

    private String password;

    private String auth;

    protected static DatabaseClientFactory.Authentication authType = DatabaseClientFactory.Authentication.valueOf(
            "DIGEST"
    );

    private SPARQLRuleset rulesets;
    private RawCombinedQueryDefinition constrainingQueryDef;

    static public SPARQLQueryManager sparqlManager;
    static public GraphManager graphManager;

    protected DatabaseClient databaseClient;

    // constructor
    public MarkLogicClientImpl(String host, int port, String user, String password, String auth) {
        this.databaseClient = DatabaseClientFactory.newClient(host, port, user, password, DatabaseClientFactory.Authentication.valueOf(auth));
    }

    // host
    public String getHost() {
        return this.host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    // port
    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    // user
    public String getUser() {
        return this.user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    // password
    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }

    // auth
    public String getAuth() {
        return auth;
    }
    public void setAuth(String auth) {
        this.auth = auth;
        this.authType = DatabaseClientFactory.Authentication.valueOf(
                auth
        );
    }

    // auth type
    public void setAuthType(DatabaseClientFactory.Authentication authType) {
        MarkLogicClientImpl.authType = authType;
    }
    public DatabaseClientFactory.Authentication getAuthType() {
        return authType;
    }

    //
    public DatabaseClient getDatabaseClient() {
        return this.databaseClient;
    }

    // performSPARQLQuery
    public InputStream performSPARQLQuery(String queryString, SPARQLQueryBindingSet bindings, long start, long pageLength, Transaction tx, boolean includeInferred, String baseURI) throws JsonProcessingException {
        return performSPARQLQuery(queryString, bindings, new InputStreamHandle(), start, pageLength, tx, includeInferred, baseURI);
    }
    public InputStream performSPARQLQuery(String queryString, SPARQLQueryBindingSet bindings, InputStreamHandle handle, long start, long pageLength, Transaction tx, boolean includeInferred, String baseURI) throws JsonProcessingException {
        sparqlManager = getDatabaseClient().newSPARQLQueryManager();
        StringBuilder sb = new StringBuilder();
        if(baseURI != null) sb.append("BASE <"+baseURI+">\n");
        sb.append(queryString);
        SPARQLQueryDefinition qdef = sparqlManager.newQueryDefinition(sb.toString());
        if(rulesets instanceof SPARQLRuleset){qdef.setRulesets(rulesets);};
        if(constrainingQueryDef instanceof RawCombinedQueryDefinition){qdef.setConstrainingQueryDefinintion(constrainingQueryDef);};
        qdef.setIncludeDefaultRulesets(includeInferred);
        qdef.setBindings(getSPARQLBindings(bindings));
        sparqlManager.executeSelect(qdef, handle, start, pageLength, tx);
        return handle.get();
    }

    // performGraphQuery
    public InputStream performGraphQuery(String queryString, SPARQLQueryBindingSet bindings, Transaction tx, boolean includeInferred, String baseURI) throws JsonProcessingException {
        return performGraphQuery(queryString, bindings, new InputStreamHandle(), tx, includeInferred, baseURI);
    }
    public InputStream performGraphQuery(String queryString, SPARQLQueryBindingSet bindings, InputStreamHandle handle, Transaction tx, boolean includeInferred, String baseURI) throws JsonProcessingException {
        sparqlManager = getDatabaseClient().newSPARQLQueryManager();
        StringBuilder sb = new StringBuilder();
        if(baseURI != null) sb.append("BASE <"+baseURI+">\n");
        sb.append(queryString);
        SPARQLQueryDefinition qdef = sparqlManager.newQueryDefinition(sb.toString());
        if(rulesets instanceof SPARQLRuleset){qdef.setRulesets(rulesets);};
        if(constrainingQueryDef instanceof RawCombinedQueryDefinition){qdef.setConstrainingQueryDefinintion(constrainingQueryDef);};
        qdef.setIncludeDefaultRulesets(includeInferred);
        qdef.setBindings(getSPARQLBindings(bindings));
        sparqlManager.executeDescribe(qdef, handle, tx);
        return handle.get();
    }

    // performBooleanQuery
    public boolean performBooleanQuery(String queryString, SPARQLQueryBindingSet bindings, Transaction tx, boolean includeInferred, String baseURI) {
        sparqlManager = getDatabaseClient().newSPARQLQueryManager();
        StringBuilder sb = new StringBuilder();
        if(baseURI != null) sb.append("BASE <"+baseURI+">\n");
        sb.append(queryString);
        SPARQLQueryDefinition qdef = sparqlManager.newQueryDefinition(sb.toString());
        qdef.setIncludeDefaultRulesets(includeInferred);
        if (rulesets instanceof SPARQLRuleset){qdef.setRulesets(rulesets);};
        if(constrainingQueryDef instanceof RawCombinedQueryDefinition){
            logger.debug("set constraining query");

            qdef.setConstrainingQueryDefinintion(constrainingQueryDef);};
        qdef.setBindings(getSPARQLBindings(bindings));
        return sparqlManager.executeAsk(qdef, tx);
    }

    // performUpdateQuery
    public void performUpdateQuery(String queryString, SPARQLQueryBindingSet bindings, Transaction tx, boolean includeInferred, String baseURI) {
        sparqlManager = getDatabaseClient().newSPARQLQueryManager();
        StringBuilder sb = new StringBuilder();
        if(baseURI != null) sb.append("BASE <"+baseURI+">\n");
        sb.append(queryString);
        SPARQLQueryDefinition qdef = sparqlManager.newQueryDefinition(sb.toString());
        if(rulesets instanceof SPARQLRuleset){qdef.setRulesets(rulesets);};
        if(constrainingQueryDef instanceof RawCombinedQueryDefinition){
            qdef.setConstrainingQueryDefinintion(constrainingQueryDef);};
        qdef.setIncludeDefaultRulesets(includeInferred);
        qdef.setBindings(getSPARQLBindings(bindings));
        sparqlManager.executeUpdate(qdef, tx);
    }

    // performAdd
    public void performAdd(File file, String baseURI, RDFFormat dataFormat,Transaction tx,Resource... contexts){
        graphManager = getDatabaseClient().newGraphManager();
        graphManager.setDefaultMimetype(dataFormat.getDefaultMIMEType());
        if(dataFormat.equals(RDFFormat.NQUADS)||dataFormat.equals(RDFFormat.TRIG)){
            //TBD- tx ?
            graphManager.mergeGraphs(new FileHandle(file));
        }else{
            //TBD- must be more efficient
            if(contexts.length != 0){
                for(Resource context: contexts) {
                    graphManager.merge(context.toString(), new FileHandle(file), tx);
                }
            }else{
                graphManager.merge(null, new FileHandle(file), tx);
            }
        }
    }
    public void performAdd(InputStream in, String baseURI, RDFFormat dataFormat,Transaction tx,Resource... contexts) {
        graphManager = getDatabaseClient().newGraphManager();
        graphManager.setDefaultMimetype(dataFormat.getDefaultMIMEType());
        if(dataFormat.equals(RDFFormat.NQUADS)||dataFormat.equals(RDFFormat.TRIG)){
            //TBD- tx ?
            graphManager.mergeGraphs(new InputStreamHandle(in));
        }else{
            //TBD- must be more efficient
            if(contexts.length !=0) {
                graphManager.merge(contexts[0].stringValue(), new InputStreamHandle(in), tx);
            }else{
                graphManager.merge(null, new InputStreamHandle(in), tx);
            }
        }
    }

    public void performAdd(String baseURI,Resource subject,URI predicate, Value object,Transaction tx,Resource... contexts) {
        sparqlManager = getDatabaseClient().newSPARQLQueryManager();
        StringBuilder sb = new StringBuilder();
        if(baseURI != null) sb.append("BASE <"+baseURI+">\n");
        sb.append("INSERT DATA { ");
        for (int i = 0; i < contexts.length; i++)
        {
            sb.append("GRAPH <"+ contexts[i].stringValue()+"> {?s ?p ?o .} ");
        }
        sb.append("}");
        SPARQLQueryDefinition qdef = sparqlManager.newQueryDefinition(sb.toString());
        qdef.withBinding("s", subject.stringValue());
        qdef.withBinding("p", predicate.stringValue());
        qdef.withBinding("o", object.toString());
        sparqlManager.executeUpdate(qdef, tx);
    }

    // performRemove
    public void performRemove(String baseURI,Resource subject,URI predicate, Value object,Transaction tx,Resource... contexts) {
        sparqlManager = getDatabaseClient().newSPARQLQueryManager();
        StringBuilder sb = new StringBuilder();
        if(baseURI != null) sb.append("BASE <"+baseURI+">\n");
        sb.append("DELETE WHERE { ");
        for (int i = 0; i < contexts.length; i++)
        {
            sb.append("GRAPH <"+ contexts[i].stringValue()+"> {?s ?p ?o .} ");
        }
        sb.append("}");
        SPARQLQueryDefinition qdef = sparqlManager.newQueryDefinition(sb.toString());
        qdef.withBinding("s", subject.stringValue());
        qdef.withBinding("p", predicate.stringValue());
        qdef.withBinding("o", object.toString());
        sparqlManager.executeUpdate(qdef, tx);
    }

    // performClear
    public void performClear(Transaction tx, Resource... contexts){
        graphManager = getDatabaseClient().newGraphManager();
        for(Resource context : contexts) {
            graphManager.delete(context.stringValue(), tx);
        }
    }
    public void performClearAll(Transaction tx){
        graphManager = getDatabaseClient().newGraphManager();
        graphManager.deleteGraphs();
    }

    // rulesets
    public SPARQLRuleset getRulesets(){
        return this.rulesets;
    }
    public void setRulesets(Object rulesets){
        this.rulesets=(SPARQLRuleset) rulesets;
    }


    // constraining query
    public void setConstrainingQueryDefinition(Object constrainingQueryDefinition){
        logger.debug("constraining query is set");
        this.constrainingQueryDef = constrainingQueryDef;
    }
    public RawCombinedQueryDefinition getConstrainingQueryDefinition(){
        return this.constrainingQueryDef;
    }

    // getSPARQLBindings
    protected SPARQLBindings getSPARQLBindings(SPARQLQueryBindingSet bindings){
        SPARQLBindings sps = new SPARQLBindingsImpl();
        for (Binding binding : bindings) {
            sps.bind(binding.getName(), binding.getValue().stringValue());
            logger.debug("binding:" + binding.getName() + "=" + binding.getValue());
        }
        return sps;
    }
}