package ie.tcd.cs7is3;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.MultiSimilarity;
import org.apache.lucene.search.similarities.Similarity;

import ie.tcd.cs7is3.documents.Topic;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        // Different Similarity Algorithms to Test
        Similarity bm25Similairty= new BM25Similarity();
        Similarity booleanSimilarity = new BooleanSimilarity();
        Similarity tfidfSimilarity = new ClassicSimilarity();
        Similarity multiSimilarity =  new MultiSimilarity(new Similarity[] { new ClassicSimilarity(), new BM25Similarity() });
        

        //Different Analyzers to Test
        CustomAnalyser custom = new CustomAnalyser();
        Analyzer standard = new StandardAnalyzer();
        Analyzer english = new EnglishAnalyzer(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);

        String PathToIndex = "src/Index";
        File directory = new File(PathToIndex);
        String[] delFiles;
        if(directory.isDirectory()){
            System.out.println("Deleting Index");
            delFiles = directory.list();
            for (int i=0; i<delFiles.length; i++) {
                File my = new File(directory, delFiles[i]);
                my.delete();
            }
            directory.delete();
        }

        String FinalQueries = "src/final_queries";
        File directory_final_Queries = new File(FinalQueries);
        String[] delFiles_fq;
        if(directory_final_Queries.isDirectory()){
            System.out.println("Deleting Final Queris");
            delFiles_fq = directory_final_Queries.list();
            for (int i=0; i<delFiles_fq.length; i++) {
                File my = new File(directory_final_Queries, delFiles_fq[i]);
                my.delete();
            }
            directory_final_Queries.delete();
        }

        File search_results = new File("src/search_results.txt");
        if(search_results.exists()){
            System.out.println("Deleting Search Results");
            search_results.delete();
        }

        File tops = new File("src/topic/tops.json");
        if(tops.exists()){
            System.out.println("Deleting Topic JSON File");
            tops.delete();
        }

        Topic.main(null);
        Indexer.buildDocsIndex(english, multiSimilarity);
        Searcher.main(english, multiSimilarity);
    }
}
