package gr.grumpy.pdfextract.service;

import gr.grumpy.pdfextract.exception.PdfExtractException;
import java.io.File;

public interface PdfExtractService {

   void printSignatureExpiryDates(File file, String format) throws PdfExtractException;
}
