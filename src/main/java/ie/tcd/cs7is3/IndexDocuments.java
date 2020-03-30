package ie.tcd.cs7is3;

import ie.tcd.cs7is3.documents.FederalRegister;
import ie.tcd.cs7is3.documents.FinancialTimes;
import ie.tcd.cs7is3.documents.ForeignBroadcastInformationService;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexDocuments {

    private final static Path currentRelativePath = Paths.get("").toAbsolutePath();

    private final static String absPathToIndex = String.format("%s/News_Index", currentRelativePath);
    private final static String absPathToFedRegister = String.format("%s/Collection/fr94",currentRelativePath);
    private final static String absPathToForBroadcast = String.format("%s/Collection/fbis",currentRelativePath);
    private final static String absPathToFinTimes = String.format("%s/Collection/ft",currentRelativePath);
    //... add the other paths

    public static void buildDocsIndex(Similarity similarity, Analyzer analyzer) throws IOException {
//        System.out.println(currentRelativePath);
        IndexWriter indexWriter;
        IndexWriterConfig indexWriterConfig  = new IndexWriterConfig(analyzer);
        indexWriterConfig
                .setSimilarity(similarity)
                .setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND).setRAMBufferSizeMB(1024);
        Directory directory = FSDirectory.open(Paths.get(absPathToIndex));

        List<Document> fedRegisterDocs = FederalRegister.loadFedRegisterDocs(absPathToFedRegister);
        List<Document> forBroadcastDocs = ForeignBroadcastInformationService.loadForBroadcastDocs(absPathToForBroadcast);
        List<Document> finTimesDocs = FinancialTimes.loadFinTimesDocs(absPathToFinTimes);

        try {
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            indexWriter.deleteAll();


            System.out.print("Indexing Federal Register Document Collection ");
            for (Document doc : fedRegisterDocs) {
            	indexWriter.addDocument(doc);
            }
            System.out.println("...Done");
            System.out.print("Indexing Foreign Broadcast Information Service Document Collection ");
            for (Document doc : forBroadcastDocs) {
            	indexWriter.addDocument(doc);
            }
//            indexWriter.addDocuments(forBroadcastDocs);
            System.out.println("...Done");
            System.out.print("Indexing Financial Times Document Collection ");
            for (Document doc : finTimesDocs) {
            	indexWriter.addDocument(doc);
//            	indexWriter.optimize();
//            	indexWriter.commit();
//            	indexWriter.close(); 
            }
//            indexWriter.addDocuments(finTimesDocs);
            System.out.println("...Done");
            //... create same classes for other docs in documents package

            indexWriter.close();

        } catch ( IOException e) {
            System.out.println("ERROR: An error occurred when trying to instantiate a new IndexWriter");
            System.out.println(String.format("ERROR MESSAGE: %s", e.getMessage()));
        }

    }

}
