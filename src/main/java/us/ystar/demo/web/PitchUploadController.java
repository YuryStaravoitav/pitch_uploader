package us.ystar.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import us.ystar.demo.services.FileService;

import java.util.Arrays;
import java.util.List;

@Controller
public class PitchUploadController extends BasePitchController {

    @Autowired
    private FileService fileService;

    private static final List<String> contentTypes = Arrays.asList("application/x-pdf", "application/pdf");
    //, "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.ms-powerpoint");

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        String fileContentType = file.getContentType();
        if (!contentTypes.contains(fileContentType)) {
            model.addAttribute("message", String.format("We don't support %s file type", fileContentType));
            return "index";
        }
        filePath = fileService.uploadFile(file);
        redirectAttributes.addFlashAttribute("message", String.format("You successfully uploaded the %s file", file.getOriginalFilename()));
        redirectAttributes.addFlashAttribute("filePath", filePath);
        redirectAttributes.addFlashAttribute("fileName", file.getOriginalFilename());
        return "redirect:/view/slides";
    }
}