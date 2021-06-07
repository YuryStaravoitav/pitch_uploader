package us.ystar.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import us.ystar.demo.PitchPdfUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PitchViewController {


    @GetMapping("/view/slides")
    public String showViewPage(
            Model model,
            @ModelAttribute("filePath") final String filePath,
            @ModelAttribute("fileName") final String fileName,
            @ModelAttribute("images") final List<String> imageFileNames) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            return "redirect:/";
        }
        if (imageFileNames == null) {
            System.out.println("imagesFileNames");
        }
        model.addAttribute("filePath", filePath);
        model.addAttribute("images", imageFileNames);
        model.addAttribute("filePath", filePath);

        return "view/slides";
    }
}