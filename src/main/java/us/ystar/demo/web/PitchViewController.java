package us.ystar.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import us.ystar.demo.PitchPdfUtils;

import java.io.IOException;
import java.util.List;

@Controller
public class PitchViewController {

    @Autowired
    private PitchPdfUtils pitchPdfUtils;

    @GetMapping("/view/slides")
    public String showViewPage(
            Model model,
            @ModelAttribute("filePath") final String filePath,
            @ModelAttribute("fileName") final String fileName) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("filePath", filePath);
        List<String> imageFileNames = pitchPdfUtils.convertPdfToImage(filePath, fileName);
        model.addAttribute("images", imageFileNames);
        model.addAttribute("filePath", filePath);
        return "view/slides";
    }
}