package us.ystar.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PitchPdfUtils {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private final int MAX_THREAD_COUNT = 15;

    String PATH = "/src/main/resources/uploads";

    public List<String> convertPdfToImage(String filePath, String pdfFilename) throws IOException, InterruptedException {
        List<String> listOfFiles = new ArrayList<>();
        File f = new File("");

        PDDocument document = PDDocument.load(new File(filePath));
        String uploadDir = f.getAbsolutePath() + PATH;
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            // new Thread(new PitchPdfUtilsProcessor(filePath, pdfFilename, uploadDir, page)).start();
            executor.execute(new PitchPdfUtilsProcessor(filePath, pdfFilename, uploadDir, page));
            String fileName = pdfFilename + "-" + (page + 1) + ".png";
            listOfFiles.add(fileName);
        }
        executor.shutdown();

        boolean finished = executor.awaitTermination(10, TimeUnit.MINUTES);
        while (!executor.isShutdown()) {
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time for " + pdfFilename + " is: " + (endTime - startTime));
        }
        document.close();
        return listOfFiles;
    }

}

class PitchPdfUtilsProcessor implements Runnable {

    private String filePath;
    private String pdfFilename;
    private String uploadDir;
    private int page;
    private volatile boolean running = true;

    public PitchPdfUtilsProcessor(String filePath, String pdfFileName, String uploadDir, int page) {
        this.filePath = filePath;
        this.pdfFilename = pdfFileName;
        this.uploadDir = uploadDir;
        this.page = page;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println(System.currentTimeMillis() + " Start process for page number: " + page);
            try {
                processPdfPageToImage();
                running = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() + " Stop process for page number: " + page);
    }

    private void processPdfPageToImage() throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        try {


            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            String fileName = pdfFilename + "-" + (page + 1) + ".png";

            String savedFilePath = uploadDir + File.separator + fileName;
            // suffix in filename will be used as the file format
            ImageIOUtil.writeImage(bim, savedFilePath, 300);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                document.close();
                running = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
