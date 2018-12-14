package com.test.util;

import android.graphics.Bitmap;

import com.socsi.exception.SDKException;
import com.socsi.smartposapi.printer.Align;
import com.socsi.smartposapi.printer.FontLattice;
import com.socsi.smartposapi.printer.PrintRespCode;
import com.socsi.smartposapi.printer.Printer2;
import com.socsi.smartposapi.printer.TextEntity;
import com.test.ui.activities.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by CuncheW1 on 2017/8/10.
 */

public class PrinterUtil {
    public static void printStringArray(String title, ArrayList<String> contentArray) {
        if (contentArray != null && contentArray.size() != 0) {
            Printer2 printer = Printer2.getInstance();
            if (checkPrinterStatus()) {
                TextEntity entity = new TextEntity(title+"\n", FontLattice.THIRTY_TWO, true, Align.CENTER);
                printer.appendTextEntity2(entity);
                printer.setSystemPrintGrayLevel(7);
                Iterator<String> iterator = contentArray.iterator();
                while (iterator.hasNext()) {
                    String content = iterator.next();
                    entity = new TextEntity(content, FontLattice.SIXTEEN, true, Align.LEFT);
                    printer.appendTextEntity2(entity);
                }
                printer.startPrint();
            }
        } else {
            DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.print_no_content), null);
        }
    }

    public static boolean checkPrinterStatus() {
        Printer2 printer = Printer2.getInstance();
        try {
            PrintRespCode respCode = printer.getPrinterStatus();
            if (respCode == PrintRespCode.Print_Success) {
                return true;
            } else if (respCode == PrintRespCode.Printer_PaperLack || respCode == PrintRespCode.Printer_Busy) {
                DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.print_no_paper), null);
            } else {
                DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.print_error), null);
            }
        } catch (SDKException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void printBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Printer2 printer = Printer2.getInstance();
            if (checkPrinterStatus()) {
                printer.appendImage(bitmap, Align.CENTER);
                printer.setSystemPrintGrayLevel(7);
                printer.startPrint();
                printer.takePaper(1, 3*24);
            }
        } else {
            DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.print_no_content), null);
        }
    }

    public static void printString(String title, String printContent) {
        if (printContent != null && printContent.length() != 0) {
            Printer2 printer = Printer2.getInstance();
            if (checkPrinterStatus()) {
                TextEntity entity = new TextEntity(title+"\n", FontLattice.THIRTY_TWO, true, Align.CENTER);
                printer.appendTextEntity2(entity);
                printer.setSystemPrintGrayLevel(7);
                entity = new TextEntity(printContent + "\n\n\n", FontLattice.TWENTY_FOUR, true, Align.LEFT);
                printer.appendTextEntity2(entity);
                printer.startPrint();
            }
        } else {
            DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.print_no_content), null);
        }
    }
}
