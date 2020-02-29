package org.dbpedia.databus.void_test_mod;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.util.FileManager;

/**
 * Hello world!
 *
 */
public class CountTriples {
	public static void main(String[] args) throws Exception {

		FileManager.get().addLocatorClassLoader(CountTriples.class.getClassLoader());

		fetchFileUrlFromDatabus();

	}

	public static void fetchFileUrlFromDatabus() {
		String queryString = " PREFIX dataid: <http://dataid.dbpedia.org/ns/core#>\r\n"
						   + "PREFIX dct: <http://purl.org/dc/terms/>\r\n"
						   + "PREFIX dcat:  <http://www.w3.org/ns/dcat#>\r\n"
						   + "select distinct ?file where {"
						   + "?dataset dataid:artifact <https://databus.dbpedia.org/denis/ontology/dbo-snapshots> .\r\n"
						   + "?dataset dcat:distribution ?distribution .\r\n"
						   + "?distribution dcat:downloadURL ?file ;\r\n"
						   + "dct:hasVersion ?latestVersion ;\r\n" 
						   + "dcat:mediaType <http://dataid.dbpedia.org/ns/mt#TextTurtle> . \r\n"
						   + "{\r\n"
						   + "SELECT (?version as ?latestVersion) WHERE { \r\n"
						   + "?dataset dataid:artifact <https://databus.dbpedia.org/denis/ontology/dbo-snapshots> . \r\n"
						   + "?dataset dct:hasVersion ?version . \r\n"
						   + "} ORDER BY DESC (?version) LIMIT 1 \r\n" 
						   + "} \r\n" 
						   + "}";

		QueryExecution qexec = QueryExecutionFactory.sparqlService("https://databus.dbpedia.org/repo/sparql",
				queryString);

		try {
			ResultSet results = qexec.execSelect();

			ResultSetFormatter.out(System.out, results);

		} finally {
			qexec.close();
		}

	}
}
