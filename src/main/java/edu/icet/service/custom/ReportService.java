package edu.icet.service.custom;

import edu.icet.service.SuperService;
import edu.icet.util.ReportType;

public interface ReportService extends SuperService {
    boolean generateReport(ReportType reportType);
}
