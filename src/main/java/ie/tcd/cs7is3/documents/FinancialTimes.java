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

public class FinancialTimes {

    private static List<Document> finTimesDocList = new ArrayList<>();

    public static List<Document> loadFinTimesDocs(String pathToFinTimes) throws IOException {
        File[] directories = new File(pathToFinTimes).listFiles(File::isDirectory);
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
                    title = document.select("HEADLINE").text();

                    addFinTimesDoc(docno, text, title);
                }
            }
        }
        return finTimesDocList;
    }

    private static void addFinTimesDoc(String docno, String text, String title) {
        Document doc = new Document();
        doc.add(new TextField("docno", docno, Field.Store.YES));
        doc.add(new TextField("headline", title, Field.Store.YES));
        doc.add(new TextField("text", text, Field.Store.YES));

        finTimesDocList.add(doc);
    }




}
