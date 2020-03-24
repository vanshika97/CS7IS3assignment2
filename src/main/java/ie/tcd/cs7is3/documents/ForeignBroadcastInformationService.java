package ie.tcd.cs7is3.documents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//File Parser
public class ForeignBroadcastInformationService {
    private static List<Document> forBroadcastDocList = new ArrayList<>();

    public static List<Document> loadForBroadcastDocs(String pathToForBroadcast) throws IOException {
        File[] directories = new File(pathToForBroadcast).listFiles(File::isDirectory);
        System.out.println(directories);
        String docno,text,title;
        for (File directory : directories) {
            File[] files = directory.listFiles();
            for (File file : files) {
                org.jsoup.nodes.Document d = Jsoup.parse(file, null, "");
                Elements documents = d.select("DOC");

                for (Element document : documents) {
                    docno = document.select("DOCNO").text();
                    text = document.select("TEXT").text();
                    title = document.select("TI").text();

                    addForBroadcastDoc(docno, text, title);
                }
            }
        }
        return forBroadcastDocList;
    }

    private static void addForBroadcastDoc(String docno, String text, String title) {
        Document doc = new Document();
        doc.add(new TextField("docno", docno, Field.Store.YES));
        doc.add(new TextField("headline", title, Field.Store.YES));
        doc.add(new TextField("text", text, Field.Store.YES));

        forBroadcastDocList.add(doc);
    }




}
