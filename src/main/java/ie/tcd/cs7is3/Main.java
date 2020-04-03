package ie.tcd.cs7is3;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import ie.tcd.cs7is3.documents.Topic;
public class Main {

    public static void main(String[] args) throws Exception {

        // Different Similarity Algorithms to Test
        Similarity bm25Similairty= new BM25Similarity();
        Similarity booleanSimilarity = new BooleanSimilarity();
        Similarity tfidfSimilarity = new ClassicSimilarity();

        //Different Analyzers to Test
        Analyzer standard = new StandardAnalyzer();
        Analyzer english = new EnglishAnalyzer();

        Topic.main(null);
        Indexer.buildDocsIndex(english, bm25Similairty);
        Searcher.main(english, bm25Similairty);
    }
}
