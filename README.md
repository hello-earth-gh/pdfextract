This program was created as a simple tool to extract various things from PDFs, using LibreOffice OpenPDF library, which is a fork of iText 5

For the time being it just extracts the expiration date of signature(s) within the PDF(s). You can run it on single PDF file, or a folder that contains PDFs potentially. It will also go into subdirs.

Usage:
  - first install at least Java 19 and point JAVA_HOME and/or PATH to run Java from there. I do it using a setenv.bat script that I run from the same Command Prompt that I will run PdfExtract tool:
```bat
c:\> type d:\tools\setenv_java19.bat

set JAVA_HOME=d:\tools\java\jdk-19.0.1
set PATH=%JAVA_HOME%\bin;%PATH%
```
  - run PdfExtract e.g. like this:
```bash
java -jar pdfextract.jar -folder c:\temp\

#or

java -jar pdfextract.jar -file c:\temp\example.pdf
```

The output will be something like the following (where NAME and SURNAME are what is shown in the Subject field of the signing certificate):
```bash
test_libre_office_annotated.pdf | no signatures present!
test_libre_office_signed_annotated.pdf | NAME SURNAME | 14-07-2025
test_libre_office_signed_certified_annotated.pdf | NAME SURNAME | 14-07-2025
```