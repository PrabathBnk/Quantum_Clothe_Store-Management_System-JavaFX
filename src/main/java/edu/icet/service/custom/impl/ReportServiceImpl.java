package edu.icet.service.custom.impl;

import edu.icet.service.custom.ReportService;
import edu.icet.util.ReportGeneratorUtil;
import edu.icet.util.ReportType;

public class ReportServiceImpl implements ReportService {
    @Override
    public boolean generateReport(ReportType reportType) {
        switch (reportType) {
            case INVENTORY_REPORT: return ReportGeneratorUtil.generateInventoryReport();
            case EMPLOYEE_REPORT: return ReportGeneratorUtil.generateEmployeeReport();
            case SUPPLIER_REPORT: return ReportGeneratorUtil.generateSupplierReport();
            default: return false;
        }
    }
}
