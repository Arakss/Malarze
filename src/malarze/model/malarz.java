/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package malarze.model;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 *
 * @author Kamil
 */
public class malarz {
    private final StringProperty ImieNazwisko;
    private final ObservableList<StringProperty> Obraz = FXCollections.observableArrayList();
    private final ObservableList<StringProperty> Tytul = FXCollections.observableArrayList();
       
    public malarz() throws IOException, InterruptedException  {
        this(null,null);
    }
    public malarz(String NazwaPliku,String ImieNazwisko) throws IOException, InterruptedException {
        this.ImieNazwisko = new SimpleStringProperty(ImieNazwisko);
        Connection connect = Jsoup.connect("http://www.pinakoteka.zascianek.pl/"+NazwaPliku);
        Document document = connect.get();
        Elements images = document.select("a[href^=Images]");
        for(Element elem: images) {
            this.Obraz.add(new SimpleStringProperty("http://www.pinakoteka.zascianek.pl/"+NazwaPliku.substring(0,NazwaPliku.indexOf('/') + 1)+elem.attr("href")));
        }
        Elements href = document.select("tr");
        href.remove(href.last());
        for(Element elem: href) {
            this.Tytul.add(new SimpleStringProperty(elem.text().substring(0,elem.text().length())));
        }
//        Elements podstrony = document.select("a[href^="+NazwaPliku.substring(0,NazwaPliku.indexOf('/'))+"]");
//        podstrony.remove(podstrony.first());
//        podstrony.remove(podstrony.first());
//        for(Element elem: podstrony) {
//            Connection connectx = Jsoup.connect("http://www.pinakoteka.zascianek.pl/"+NazwaPliku.substring(0,NazwaPliku.indexOf('/')+ 1)+elem.attr("href"));
//            Document documentx = connectx.get();
//            Elements imagesx = documentx.select("a[href^=Images]");
//            for(Element elemx: imagesx) {
//                System.out.println("http://www.pinakoteka.zascianek.pl/"+NazwaPliku.substring(0,NazwaPliku.indexOf('/') + 1)+elemx.attr("href"));
//                this.Obraz.add(new SimpleStringProperty("http://www.pinakoteka.zascianek.pl/"+NazwaPliku.substring(0,NazwaPliku.indexOf('/') + 1)+elemx.attr("href")));
//            }  
//            Elements hrefx = documentx.select("tr");
//            hrefx.remove(hrefx.last());
//            for(Element elemy: hrefx) {
//                this.Tytul.add(new SimpleStringProperty(elemy.text()));
//                System.out.println(elemy.text());
//        }
//            Thread.sleep(100); 
//      }
    }
    
    public String getImieNazwisko() {
        return ImieNazwisko.get();
    }
    public StringProperty getObraz(int n) {
        return Obraz.get(n);
    }
    public StringProperty getTytul(int n) {
        return Tytul.get(n);
    }
    public int getRozmiar() {
        return Obraz.size();
    }
}
