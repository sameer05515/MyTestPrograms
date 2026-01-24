package com.example.vocabkhajana.controller;

import com.example.vocabkhajana.model.WordPage;
import com.example.vocabkhajana.service.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping({"/", "/words"})
    public String words(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", required = false) Integer size,
            Model model) {

        int pageSize = (size != null && size > 0) ? size : wordService.getDefaultPageSize();
        WordPage wordPage = wordService.getPage(page, pageSize);

        int totalPages = (int) wordPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.range(0, totalPages)
                .boxed()
                .toList();

        boolean hasPrevious = wordPage.hasPrevious();
        boolean hasNext = wordPage.hasNext();
        int previousPage = hasPrevious ? wordPage.getPageNumber() - 1 : wordPage.getPageNumber();
        int nextPage = hasNext ? wordPage.getPageNumber() + 1 : wordPage.getPageNumber();

        model.addAttribute("wordPage", wordPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);

        return "words";
    }
}

