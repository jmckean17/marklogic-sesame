package com.marklogic.semantics.sesame;

import info.aduna.iteration.Iteration;
import org.openrdf.IsolationLevel;
import org.openrdf.model.*;
import org.openrdf.query.*;
import org.openrdf.repository.*;
import org.openrdf.rio.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Created by jfuller on 6/24/15.
 */
public class MarkLogicRepositoryConnection implements RepositoryConnection {
    public MarkLogicRepositoryConnection(Repository markLogicSesameRepository, Object p1) {

    }

    @Override
    public Repository getRepository() {
        return null;
    }

    @Override
    public void setParserConfig(ParserConfig config) {

    }

    @Override
    public ParserConfig getParserConfig() {
        return null;
    }

    @Override
    public ValueFactory getValueFactory() {
        return null;
    }

    @Override
    public boolean isOpen() throws RepositoryException {
        return false;
    }

    @Override
    public void close() throws RepositoryException {

    }

    @Override
    public Query prepareQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public Query prepareQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public TupleQuery prepareTupleQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public TupleQuery prepareTupleQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public GraphQuery prepareGraphQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public GraphQuery prepareGraphQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public BooleanQuery prepareBooleanQuery(QueryLanguage ql, String query) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public BooleanQuery prepareBooleanQuery(QueryLanguage ql, String query, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public Update prepareUpdate(QueryLanguage ql, String update) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public Update prepareUpdate(QueryLanguage ql, String update, String baseURI) throws RepositoryException, MalformedQueryException {
        return null;
    }

    @Override
    public RepositoryResult<Resource> getContextIDs() throws RepositoryException {
        return null;
    }

    @Override
    public RepositoryResult<Statement> getStatements(Resource subj, URI pred, Value obj, boolean includeInferred, Resource... contexts) throws RepositoryException {
        return null;
    }

    @Override
    public boolean hasStatement(Resource subj, URI pred, Value obj, boolean includeInferred, Resource... contexts) throws RepositoryException {
        return false;
    }

    @Override
    public boolean hasStatement(Statement st, boolean includeInferred, Resource... contexts) throws RepositoryException {
        return false;
    }

    @Override
    public void exportStatements(Resource subj, URI pred, Value obj, boolean includeInferred, RDFHandler handler, Resource... contexts) throws RepositoryException, RDFHandlerException {

    }

    @Override
    public void export(RDFHandler handler, Resource... contexts) throws RepositoryException, RDFHandlerException {

    }

    @Override
    public long size(Resource... contexts) throws RepositoryException {
        return 0;
    }

    @Override
    public boolean isEmpty() throws RepositoryException {
        return false;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws RepositoryException {

    }

    @Override
    public boolean isAutoCommit() throws RepositoryException {
        return false;
    }

    @Override
    public boolean isActive() throws UnknownTransactionStateException, RepositoryException {
        return false;
    }

    @Override
    public void setIsolationLevel(IsolationLevel level) throws IllegalStateException {

    }

    @Override
    public IsolationLevel getIsolationLevel() {
        return null;
    }

    @Override
    public void begin() throws RepositoryException {

    }

    @Override
    public void begin(IsolationLevel level) throws RepositoryException {

    }

    @Override
    public void commit() throws RepositoryException {

    }

    @Override
    public void rollback() throws RepositoryException {

    }

    @Override
    public void add(InputStream in, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(Reader reader, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(URL url, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(File file, String baseURI, RDFFormat dataFormat, Resource... contexts) throws IOException, RDFParseException, RepositoryException {

    }

    @Override
    public void add(Resource subject, URI predicate, Value object, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void add(Statement st, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void add(Iterable<? extends Statement> statements, Resource... contexts) throws RepositoryException {

    }

    @Override
    public <E extends Exception> void add(Iteration<? extends Statement, E> statements, Resource... contexts) throws RepositoryException, E {

    }

    @Override
    public void remove(Resource subject, URI predicate, Value object, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void remove(Statement st, Resource... contexts) throws RepositoryException {

    }

    @Override
    public void remove(Iterable<? extends Statement> statements, Resource... contexts) throws RepositoryException {

    }

    @Override
    public <E extends Exception> void remove(Iteration<? extends Statement, E> statements, Resource... contexts) throws RepositoryException, E {

    }

    @Override
    public void clear(Resource... contexts) throws RepositoryException {

    }

    @Override
    public RepositoryResult<Namespace> getNamespaces() throws RepositoryException {
        return null;
    }

    @Override
    public String getNamespace(String prefix) throws RepositoryException {
        return null;
    }

    @Override
    public void setNamespace(String prefix, String name) throws RepositoryException {

    }

    @Override
    public void removeNamespace(String prefix) throws RepositoryException {

    }

    @Override
    public void clearNamespaces() throws RepositoryException {

    }
}
