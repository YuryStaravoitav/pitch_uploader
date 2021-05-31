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

@Component
public class PitchPdfUtils {

    String PATH = "/src/main/resources/uploads";

    public List<String> convertPdfToImage(String filePath, String pdfFilename) throws IOException {
        List<String> listOfFiles = new ArrayList<>();
        File f = new File("");

        PDDocument document = PDDocument.load(new File(filePath));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        String uploadDir = f.getAbsolutePath() + PATH;
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

            String fileName = pdfFilename + "-" + (page + 1) + ".png";
            String savedFilePath = uploadDir + File.separator + fileName;
            // suffix in filename will be used as the file format
            ImageIOUtil.writeImage(bim, savedFilePath, 300);
            listOfFiles.add(fileName);
        }
        document.close();
        return listOfFiles;
    }
}
