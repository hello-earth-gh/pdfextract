package gr.grumpy.pdfextract.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PdfExtractServiceImplTest {

   // replace system.output so that program writes to a buffer we can check later
   private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
   private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
   private final PrintStream originalOut = System.out;
   private final PrintStream originalErr = System.err;

   public PdfExtractServiceImplTest() {
   }

   @BeforeAll
   public void setUpStreams() {
      System.setOut(new PrintStream(outContent));
      System.setErr(new PrintStream(errContent));
   }

   @AfterAll
   public void restoreStreams() {
      System.setOut(originalOut);
      System.setErr(originalErr);
   }

   @org.junit.jupiter.api.Test
   public void testPrintSignatureExpiryDates_no_signatures() throws Exception {
      System.out.println("printSignatureExpiryDates_no_signatures");
      File file = File.createTempFile("temp", ".pdf");
      try (InputStream is = PdfExtractServiceImplTest.class.getResourceAsStream("/test_not_signed.pdf")) {
	 FileOutputStream os = new FileOutputStream(file);
	 is.transferTo(os);
	 String format = "dd-MM-yyyy";
	 PdfExtractServiceImpl instance = new PdfExtractServiceImpl();
	 instance.printSignatureExpiryDates(file, format);

	 assertTrue(outContent.toString().contains("no signatures present!"));
	 outContent.flush();
      }
   }

   @org.junit.jupiter.api.Test
   public void testPrintSignatureExpiryDates_signature() throws Exception {
      System.out.println("printSignatureExpiryDates_signature");
      File file = File.createTempFile("temp", ".pdf");
      try (InputStream is = PdfExtractServiceImplTest.class.getResourceAsStream("/test_signed.pdf")) {
	 FileOutputStream os = new FileOutputStream(file);
	 is.transferTo(os);
	 String format = "dd-MM-yyyy";
	 PdfExtractServiceImpl instance = new PdfExtractServiceImpl();
	 instance.printSignatureExpiryDates(file, format);

	 assertTrue(outContent.toString().contains("14-07-2025"));
      }
   }
}
