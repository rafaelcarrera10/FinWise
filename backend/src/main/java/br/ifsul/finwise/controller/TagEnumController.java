package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.TagEnum;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagEnumController {

    @GetMapping
    public TagEnum[] listar() {
        return TagEnum.values();
    }
}
