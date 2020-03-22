package ie.tcd.cs7is3;

import ie.tcd.cs7is3.analyzers.CustomAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

import java.io.IOException;

public class Main {

    private static final int MAX_RETURN_RESULTS = 1000;


    public static void main(String[] args) throws IOException {

        // Different Similarity Algorithms to Test
        Similarity bm25Similairty= new BM25Similarity();
        Similarity booleanSimilarity = new BooleanSimilarity();
        Similarity tfidfSimilarity = new ClassicSimilarity();

        //Different Analyzers to Test
        Analyzer standard = new StandardAnalyzer();
        Analyzer custom = new CustomAnalyzer();


        //Index Documents
        IndexDocuments.buildDocsIndex(tfidfSimilarity,standard);

    }
}
