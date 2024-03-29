package ie.tcd.cs7is3.documents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FinancialTimes {

    private final static String absPathToIndex = "src/Index";

    public static void loadFinTimesDocs(String pathToFinTimes, Analyzer analyzer, Similarity similarity) throws IOException {
    	
        File[] directories = new File(pathToFinTimes).listFiles(File::isDirectory);
        String docno,text,title;
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND).setSimilarity(similarity);
        Directory drc = FSDirectory.open(Paths.get(absPathToIndex));
        IndexWriter iw = new IndexWriter(drc, iwc);
        for (File directory : directories) {
            File[] files = directory.listFiles();
            for (File file : files) {
                org.jsoup.nodes.Document d = Jsoup.parse(file, null, "");
                Elements documents = d.select("doc");

                for (Element document : documents) {
                    docno = document.select("docno").text();
                    text = document.select("text").text().replaceAll("[^a-zA-Z ]", "".toLowerCase());
                    title = document.select("headline").text().replaceAll("[^a-zA-Z ]", "".toLowerCase());
                    Document doc = new Document();
                    doc.add(new TextField("docnoo", docno, Field.Store.YES));
                    doc.add(new TextField("headline", title, Field.Store.YES));
                    doc.add(new TextField("text", text, Field.Store.YES));
                    iw.addDocument(doc);
                }
            }
        }
        iw.close();
        drc.close();

    }
}