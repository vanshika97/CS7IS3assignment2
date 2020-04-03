package ie.tcd.cs7is3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Searcher {

    private static final int HITS_PER_PAGE = 1000;
    private static final String[] allElements = {"date", "dateline", "in", "profile", "cn", "docno", "co", "pe",
            "text", "page", "tp", "pub", "headline", "byline",
            "usdept", "agency", "usbureau", "doctitle", "summary", "supplem", "other",
            "ht", "au", "f",
            "paragraph"};

    /**
     * Create query from topic
     * @param top the topic JSON to create query from
     * @return the query string created from the topic JSON
     */
    private static String createQuery(JSONObject top) {

        // Concatenate the four elements
        String queryStr = (String) top.get("num") + " " + (String) top.get("title") + " "
                + (String) top.get("desc") + " " + (String) top.get("narr");

        return queryStr;
    }

    public static void main(Analyzer analyzer, Similarity similarity) throws IOException, ParseException {
    	
        // Parse command line arguments
        String indexDirStr = "src/Index/";
        String parsedTopicFileStr = "src/topic/tops.json";

        // Create output directory if it does not exists
        // Create a directory to store final queries
        File finalQueriesDir = new File("src/final_queries/");
        if (!finalQueriesDir.exists())
            finalQueriesDir.mkdir();

        // Delete previous final queries files
        System.out.println("Deleting previous final queries files, if they exist...");

        System.out.println("Done!\n");

        List<String> resFileContent = new ArrayList<String>();
        List<String> queryFileContent = new ArrayList<String>();

        String[] elements = allElements;

        // Check if all paths are valid and exist
        String ftIndexDirStr = indexDirStr;
        // Parse into JSON
        JSONParser jsonParser = new JSONParser();
        JSONArray tops = null;
        try {

            System.out.print("Reading " + parsedTopicFileStr + "...");
            tops = (JSONArray) jsonParser.parse(new FileReader(parsedTopicFileStr));
        } catch (org.json.simple.parser.ParseException e) {

            System.out.println("Unable to parse " + parsedTopicFileStr + ". Please ensure the format is correct.");
            System.out.println("Exiting application.");
            System.exit(1);
        }
        System.out.println("done.\n");

        // Get index from disk
        Directory directory = FSDirectory.open(Paths.get(ftIndexDirStr));
        DirectoryReader ireader = DirectoryReader.open(directory);

        // Create an index searcher
        IndexSearcher isearcher = new IndexSearcher(ireader);
        isearcher.setSimilarity(similarity);

        System.out.print("Searching index using "+analyzer.getClass().getSimpleName()+" and " +
        similarity.getClass().getSimpleName() +", with " + Integer.toString(HITS_PER_PAGE) + " hits per page..");
        // Loop through all queries and retrieve documents
        for (Object obj : tops) {

            JSONObject top = (JSONObject) obj;

            String queryStr = createQuery(top);
            queryFileContent.add(queryStr);

            queryStr = queryStr.replace("/", "\\/");
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(elements, analyzer);
            Query query = queryParser.parse(queryStr);

            // Search
            TopDocs topDocs = isearcher.search(query, HITS_PER_PAGE);
            ScoreDoc[] hits = topDocs.scoreDocs;

            // Retrieve results
            for (int j = 0; j < hits.length; j++) {

                int docId = hits[j].doc;
                Document doc = isearcher.doc(docId);
                resFileContent.add(
                        (String) top.get("num") + " 0 " + doc.get("docnoo").toUpperCase() + " 0 " + hits[j].score + " STANDARD");
            }
        }
        System.out.println(".done!\n");

        System.out.print("Writing queries to file...");
        Files.write(Paths.get("src/final_queries/queries.txt"), queryFileContent, Charset.forName("UTF-8"),
                StandardOpenOption.CREATE_NEW);
        System.out.println(" src/search_results.txt to be used in TREC Eval.");

        System.out.print("Writing results to file...");
        Files.write(Paths.get("src/search_results.txt"), resFileContent, Charset.forName("UTF-8"),
                StandardOpenOption.CREATE_NEW);
        System.out.println(" src/search_results.txt to be used in TREC Eval.");
    }
}