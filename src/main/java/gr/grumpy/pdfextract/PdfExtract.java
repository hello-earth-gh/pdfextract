package gr.grumpy.pdfextract;

import gr.grumpy.pdfextract.service.impl.PdfExtractCLIServiceImpl;
import gr.grumpy.pdfextract.service.impl.PdfExtractServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PdfExtract {
   public static void main(String[] args) {
      ApplicationContext ctx = new AnnotationConfigApplicationContext(PdfExtractCLIServiceImpl.class, PdfExtractServiceImpl.class);
      PdfExtractCLIServiceImpl bean = ctx.getBean(PdfExtractCLIServiceImpl.class);
      bean.doWork(args);
   }
}
