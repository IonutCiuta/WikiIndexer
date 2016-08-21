package com.endava.wikiexplorer.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by aciurea on 8/21/2016.
 */
@Controller
public class FileController {

    @RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }
}
