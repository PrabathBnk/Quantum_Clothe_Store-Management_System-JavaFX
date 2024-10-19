package edu.icet.controller;

import com.lowagie.text.pdf.PdfReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PdfViewerFormController implements Initializable {

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File pdfFile = new File("src/main/resources/generated_reports/inventory_reports/inventory_report-IR001.pdf");

    }
}
