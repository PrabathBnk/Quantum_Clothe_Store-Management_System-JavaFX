package edu.icet.util;

import com.dlsc.pdfviewfx.PDFView;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PdfViewer {

    private static PdfViewer instance;

    private PdfViewer(){}

    public static PdfViewer getInstance() {
        return null==instance ? instance=new PdfViewer(): instance;
    }

    public void viewPdf(String filePath){
        PDFView pdfView = new PDFView();

        try {
            pdfView.load(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        VBox.setVgrow(pdfView, Priority.ALWAYS);
        VBox vBox = new VBox(pdfView);
        vBox.setFillWidth(true);

        Scene scene = new Scene(vBox);
        Stage stage = new Stage();
        stage.setTitle("PDF Viewer");
        stage.setWidth(1000);
        stage.setHeight(900);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
