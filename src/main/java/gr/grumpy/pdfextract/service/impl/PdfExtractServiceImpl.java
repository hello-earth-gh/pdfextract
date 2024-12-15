package gr.grumpy.pdfextract.service.impl;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.PdfReader;
import gr.grumpy.pdfextract.exception.PdfExtractException;
import gr.grumpy.pdfextract.service.PdfExtractService;
import gr.grumpy.pdfextract.util.PdfExtractUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PdfExtractServiceImpl
	implements PdfExtractService {

   public void printSignatureExpiryDates(File file, String format) throws PdfExtractException {
      log.debug("printSignatureExpiryDates for " + file.getAbsolutePath());

      PdfPKCS7.loadCacertsKeyStore();

      try {
	 InputStream is = new FileInputStream(file);

	 PdfReader reader = new PdfReader(is);
	 AcroFields fields = reader.getAcroFields();

	 List<String> signatures = fields.getSignedFieldNames();
	 if (signatures != null && signatures.isEmpty()) {
	    System.out.println(file.getName() + " | no signatures present! ");
	    return;
	 }

	 for (String signature : signatures) {

	    PdfPKCS7 pk = fields.verifySignature(signature);

	    X509Certificate certificate = pk.getSigningCertificate();
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    String dt = sdf.format(certificate.getNotAfter());
	    String finalOutput = file.getName() + " | " + PdfExtractUtils.extractCommonName(certificate) + " | " + dt;
	    System.out.println(finalOutput);
	 }

      } catch (Exception e) {
	 throw new PdfExtractException(e.getMessage(), e);
      }
   }
}
