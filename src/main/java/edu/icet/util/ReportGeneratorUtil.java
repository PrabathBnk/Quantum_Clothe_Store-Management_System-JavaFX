package edu.icet.util;

import edu.icet.db.DBConnection;
import net.sf.jasperreports.engine.*;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportGeneratorUtil {

    private ReportGeneratorUtil() {}

    public static boolean generateInventoryReport(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportID", generateReportID("src/main/resources/generated_reports/inventory_reports", "IR"));
        return generateReport("inventory_report", parameters);
    }

    public static boolean generateEmployeeReport(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportID", generateReportID("src/main/resources/generated_reports/employee_reports", "ER"));
        return generateReport("employee_report", parameters);
    }

    public static boolean generateSupplierReport(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportID", generateReportID("src/main/resources/generated_reports/supplier_reports", "SR"));
        return generateReport("supplier_report", parameters);
    }

    private static boolean generateReport(String reportName, Map<String, Object> parameters) {
        try {
            JasperCompileManager.compileReportToFile("src/main/resources/report/"+ reportName + ".jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/report/"+ reportName + ".jasper", parameters, DBConnection.getInstance().getConnection());
            String destinationFilePath = "src/main/resources/generated_reports/"+ reportName + "s/" + reportName + "-" + parameters.get("reportID") + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, destinationFilePath);
            PdfViewer.getInstance().viewPdf(destinationFilePath);
            return true;
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateReportID(String directory, String initialText) {
        return initialText + String.format("%03d", Objects.requireNonNull(new File(directory).list()).length+1);
    }
}
