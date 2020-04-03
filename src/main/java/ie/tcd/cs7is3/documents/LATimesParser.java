package ie.tcd.cs7is3.documents;
import java.io.File;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class LATimesParser {
    private final static String absPathToIndex = "src/Index";
    public static void loadLaTimesDocs(String pathToLATimesRegister, Analyzer analyzer, Similarity similarity) throws IOException {

        File folder = new File(pathToLATimesRegister);
        File[] listOfFiles = folder.listFiles();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND).setSimilarity(similarity);
        Directory drc = FSDirectory.open(Paths.get(absPathToIndex));
        String docno, headline, text;
        IndexWriter iw = new IndexWriter(drc, iwc);
        for (File file : listOfFiles) {

            Document laTimesContent = Jsoup.parse(file, null, "");

            Elements docs = laTimesContent.select("doc");

            for(Element doc: docs) {

                docno = doc.select("docno").text();
                headline = (doc.select("headline").select("P").text());
                text = (doc.select("text").select("P").text());
                org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
                document.add(new TextField("docnoo", docno, Field.Store.YES));
                document.add(new TextField("headline", headline, Field.Store.YES) );
                document.add(new TextField("text", text, Field.Store.YES) );
                iw.addDocument(document);
            }
        }
        iw.close();
        drc.close();
    }
}