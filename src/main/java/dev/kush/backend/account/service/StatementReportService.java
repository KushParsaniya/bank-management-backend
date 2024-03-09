package dev.kush.backend.account.service;

import net.sf.jasperreports.engine.*;

import java.io.*;

public interface StatementReportService {
    String generateReport(Long accountId) throws FileNotFoundException, JRException;
}
