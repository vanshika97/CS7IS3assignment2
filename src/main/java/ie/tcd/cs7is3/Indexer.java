package ie.tcd.cs7is3;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.similarities.Similarity;

import ie.tcd.cs7is3.documents.*;


public class Indexer {

    private final static Path currentRelativePath = Paths.get("").toAbsolutePath();

    private final static String absPathToFedRegister = String.format("%s/Collection/fr94",currentRelativePath);
    private final static String absPathToForBroadcast = String.format("%s/Collection/fbis",currentRelativePath);
    private final static String absPathToLATimes = String.format("%s/Collection/latimes",currentRelativePath);
    private final static String absPathToFinTimes = String.format("%s/Collection/ft",currentRelativePath);

    public static void buildDocsIndex(Analyzer analyzer, Similarity similarity) throws IOException {
        System.out.print("Indexing Federal Register collection");
        FederalRegister.loadFedRegisterDocs(absPathToFedRegister, analyzer, similarity);
        System.out.println("..Done");
        System.out.print("Indexing Foreign Broadcast Information Service collection");
        ForeignBroadcastInformationService.loadForBroadcastDocs(absPathToForBroadcast, analyzer, similarity);
        System.out.println("..Done");
        System.out.print("Indexing LA Times collection");
        LATimesParser.loadLaTimesDocs(absPathToLATimes, analyzer, similarity);
        System.out.println("..Done");
        System.out.print("Indexing Financial Times collection");
        FinancialTimes.loadFinTimesDocs(absPathToFinTimes, analyzer, similarity);
        System.out.println("..Done");
    }



}