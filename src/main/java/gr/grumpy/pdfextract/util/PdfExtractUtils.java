package gr.grumpy.pdfextract.util;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;


public class PdfExtractUtils {
    public static final String extractCommonName(X509Certificate cert) throws CertificateEncodingException {
      X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();
      RDN cn = x500name.getRDNs(BCStyle.CN)[0];

      return IETFUtils.valueToString(cn.getFirst().getValue());
    }
}
