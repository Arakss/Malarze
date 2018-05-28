/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package malarze.wyglad;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import malarze.model.malarz;
import malarze.glowny;

/**
 *
 * @author Kamil
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ComboBox<malarz> lista;
    @FXML
    private ImageView obraz;
    @FXML
    private Label opis;
    @FXML
    private AnchorPane pane;
    
    private glowny podstawa;
    private int n;
    private double posX;
    private double posX2;
    private boolean przesuniecie=false;

    public void Setglowny(glowny g) {
        this.podstawa = g;
        lista.setItems(g.getdanemalarzy());
        obraz.fitWidthProperty().bind(pane.widthProperty());
        obraz.fitHeightProperty().bind(pane.heightProperty().subtract(110));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       lista.getSelectionModel().selectFirst();
       lista.setCellFactory(new Callback<ListView<malarz>, ListCell<malarz>>() {
       @Override
       public ListCell<malarz> call(ListView<malarz> param) {
  
            return new ListCell<malarz>(){
              @Override
                public void updateItem(malarz item, boolean empty){
                    super.updateItem(item, empty);
                    if(!empty) {
                        setText(item.getImieNazwisko());
                        setGraphic(null);
                    } 
                    else 
                    {
                        setText(null);
                    }
                }
           };
      }
      });
      lista.setButtonCell(
      new ListCell<malarz>() {
        @Override
        public void updateItem(malarz item, boolean empty) {
            super.updateItem(item, empty); 
            if(!empty) {
                setText(item.getImieNazwisko());
            } 
            else 
            {
                setText(null);
            }
        }
    });
    lista.valueProperty().addListener(new ChangeListener<malarz>() {
    @Override
    public void changed(ObservableValue ov, malarz oldValue, malarz newValue) {
        n=0;
        animacja();
        opis.setText(lista.getValue().getTytul(n).get());
        Image image = new Image(newValue.getObraz(0).get());
        obraz.setImage(image);
    }
    });
    }  
    @FXML
    private void poprzedni(ActionEvent event) throws InterruptedException {
        if(lista.getValue()!=null)
        {
            animacja();
            if(n==0)
            {
                n=lista.getValue().getRozmiar();
            }
            Image image = new Image(lista.getValue().getObraz(--n).get());
            obraz.setImage(image);
            opis.setText(lista.getValue().getTytul(n).get());
        }
    }
    @FXML
    private void nastepny(ActionEvent event) {
        if(lista.getValue()!=null)
        {
            animacja();
            if(n==lista.getValue().getRozmiar()-1)
            {
                n=-1;
            }
            Image image = new Image(lista.getValue().getObraz(++n).get());
            obraz.setImage(image);
            opis.setText(lista.getValue().getTytul(n).get());
        }
    }
    @FXML
    private void przesuniecie(MouseEvent event) {
        if(lista.getValue()!=null)
        {
            przesuniecie=true;
            posX2=event.getX();
            obraz.setCursor(Cursor.CLOSED_HAND);
        }
    }

    @FXML
    private void wcisniecie(MouseEvent event) {
        if(lista.getValue()!=null)
        {
            posX=event.getX();
        }
    }
    @FXML
    private void puszczenie(MouseEvent event) {
        if(lista.getValue()!=null)
        {
            if((przesuniecie==true) & Math.abs(posX-posX2)>200  ) {
                animacja();
                if(posX<posX2)
                {
                    if(n==lista.getValue().getRozmiar()-1)
                    {
                        n=-1;
                    }
                    Image image = new Image(lista.getValue().getObraz(++n).get());
                    obraz.setImage(image);
                    opis.setText(lista.getValue().getTytul(n).get());
                }
                else
                {
                    if(n==0)
                    {
                        n=lista.getValue().getRozmiar();
                    }
                    Image image = new Image(lista.getValue().getObraz(--n).get());
                    obraz.setImage(image);
                    opis.setText(lista.getValue().getTytul(n).get());
                }
            }
        }
        przesuniecie=false;
        obraz.setCursor(Cursor.OPEN_HAND);
    }
    private void animacja() {
        FadeTransition ft = new FadeTransition();
        ft.setNode(obraz);
        ft.setDuration(new Duration(3000));
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true); 
        ft.play();
    }
}