package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    //This method displays all the images in the user home page after successful login
    @RequestMapping("images")
    public String getUserImages(Model model) {
        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "images";
    }

    //This method is called when the details of the specific image with corresponding title are to be displayed
    //The logic is to get the image from the databse with corresponding title. After getting the image from the database the details are shown
    //But since the images are not stored in the database, therefore, we have hard-coded two images here
    //If the title of the image is 'Dr. Strange', an image object is created with all the corresponding details
    //If the title of the image is 'SpiderMan', an image object is created with all the corresponding details
    //The image object is added to the model and 'images/image.html' file is returned
    @RequestMapping("/images/{title}")
    public String showImage(@PathVariable("title") String title, Model model) {
        Date date = new Date();
        Image image = null;
        if (title.equals("Dr. Strange")) {
            image = new Image(1, "Dr. Strange", "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", "Dr. Strange has a time stone", date);
        } else if (title.equals("SpiderMan")) {
            image = new Image(2, "SpiderMan", "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", "Spider man dies in Infinity War", date);
        }

        model.addAttribute("image", image);
        return "images/image";
    }

    //This controller method is called when the request pattern is of type 'images/upload'
    //The method returns 'images/upload.html' file
    @RequestMapping("/images/upload")
    public String newImage() {
        return "images/upload";
    }

    //This controller method is called when the request pattern is of type 'images/upload' and also the incoming request is of POST type
    //The method receives all the details of the image to be stored in the database, and now the image will be sent to the business logic to be persisted in the database
    //After you get the imageFile, set the user of the image by getting the logged in user from the Http Session with 'loggeduser' as the key
    //Convert the image to Base64 format and store it as a string in the 'imageFile' attribute
    //Set the date on which the image is posted
    //After storing the image, this method directs to the logged in user homepage displaying all the images
    @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
    public String createImage(@RequestParam("file") MultipartFile file, Image newImage, HttpSession session,Model model) throws IOException {
        //Complete the method
    	
    	User user = (User) session.getAttribute("loggeduser");
    	newImage.setUser(user);
    	
    	String imageFile=convertUploadedFileToBase64(file);
    	newImage.setImageFile(imageFile);
    	newImage.setDate(new Date());
    	
    	imageService.uploadImage(newImage);
    	imageService.getAllImages();
    	List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        
    	return "index";
    	
    	
    }


    //This method converts the image to Base64 format
    private String convertUploadedFileToBase64(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }
}
