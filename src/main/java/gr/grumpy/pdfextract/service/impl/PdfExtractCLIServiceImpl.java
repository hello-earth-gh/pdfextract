package gr.grumpy.pdfextract.service.impl;

import gr.grumpy.pdfextract.exception.PdfExtractException;
import gr.grumpy.pdfextract.service.PdfExtractService;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PdfExtractCLIServiceImpl {

   @Autowired
   PdfExtractService service;

   public void doWork(String[] args) {

      Options options = new Options();
      options.addOption("file", true, "PDF file to work with");
      options.addOption("folder", true, "Directory that contains PDF files to work with");
      options.addOption("m", "mode", true, "when mode=s, extract signature");
      options.addOption("f", "field", true, "when field=ed, extract expiration date");
      options.addOption("fnt", true, "Java-style output format modifier for the output field");

      if (args.length <= 0) {
	 HelpFormatter formatter = new HelpFormatter();
	 formatter.printHelp("pdfextract", options);
	 return;
      }

      CommandLineParser parser = new DefaultParser();
      try {
	 CommandLine cmd = parser.parse(options, args);
	 String file = cmd.getOptionValue("file");
	 String folder = cmd.getOptionValue("folder");
	 String mode = cmd.getOptionValue("m", "s");
	 String field = cmd.getOptionValue("f", "ed");
	 String format = cmd.getOptionValue("fmt", "dd-MM-yyyy");

	 if (!"s".equals(mode) || !"ed".equals(field)) {
	    throw new RuntimeException("Not implemented yet, please run command without arguments to see what is");
	 }

	 if (!StringUtils.isEmpty(file)) {
	    workWithFile(file, format);
	 } else if (!StringUtils.isEmpty(folder)) {
	    workWithFolder(folder, format);
	 }

      } catch (Exception ex) {
	 log.error("Something failed. Reason: " + ex.getMessage(), ex);
      }

   }

   private void workWithFile(String file, String format) throws PdfExtractException {
      File f = new File(file);
      if (!f.exists()) {
	 throw new RuntimeException("file does not exist");
      }

      if (f.getName().toUpperCase().endsWith(".PDF")) {
	 service.printSignatureExpiryDates(f, format);
      }
   }

  private void workWithFolder(String folder, String format) throws PdfExtractException {
      File f = new File(folder);
      if (!f.exists()) {
	 throw new RuntimeException("folder does not exist");
      }

      for (final File fileEntry : f.listFiles()) {
	 if (fileEntry.isDirectory()) {
	    workWithFolder(fileEntry.getAbsolutePath(), format);
	 } else {
	    workWithFile(fileEntry.getAbsolutePath(), format);
	 }
      }
   }
}
