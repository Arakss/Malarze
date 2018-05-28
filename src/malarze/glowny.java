/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package malarze;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import malarze.model.malarz;
import malarze.wyglad.FXMLDocumentController;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Kamil
 */
public class glowny extends Application {
    
    private final ObservableList<malarz> danemalarzy = FXCollections.observableArrayList();
    public glowny() throws IOException, InterruptedException {
        Connection connect = Jsoup.connect("http://www.pinakoteka.zascianek.pl/Artists.htm");
        Document document = connect.get();
        Elements ahref = document.select("a[href]");
        ahref.remove(ahref.last());
        ahref.remove(ahref.last());
        ahref.remove(56);
        ahref.remove(104);
        for(Element elem: ahref) {
            danemalarzy.add(new malarz(elem.attr("href"),elem.text()));
        }
    }
    public ObservableList<malarz> getdanemalarzy() {
        return danemalarzy;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("wyglad/FXMLDocument.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        FXMLDocumentController controller = loader.getController();
        controller.Setglowny(this);
        
        Scene scene = new Scene(root);
        stage.setTitle("Polscy Malarze");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }  
}
