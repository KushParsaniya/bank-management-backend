package dev.kush.backend.account.controller;

import dev.kush.backend.account.service.*;
import net.sf.jasperreports.engine.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
public class TransactionController {

    private final StatementReportService statementReportService;

    public TransactionController(StatementReportService statementReportService) {
        this.statementReportService = statementReportService;
    }

    @GetMapping("/transaction/report/{accountId}")
    public String generateStatement(@PathVariable Long accountId) throws JRException, FileNotFoundException {
            return statementReportService.generateReport(accountId);
    }
}
