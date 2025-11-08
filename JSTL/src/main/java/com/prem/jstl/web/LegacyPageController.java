package com.prem.jstl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legacy")
public class LegacyPageController {

    @GetMapping
    public String index() {
        return "legacy-list";
    }

    @GetMapping("/subscript")
    public String subscript() {
        return "legacy/Subscript";
    }

    @GetMapping("/test-page")
    public String testPage() {
        return "legacy/TestPage";
    }

    @GetMapping("/gg")
    public String ggPage() {
        return "legacy/gg";
    }

    @GetMapping("/letter")
    public String letterPage() {
        return "legacy/test1";
    }

    @GetMapping("/number-format")
    public String numberFormatLegacy() {
        return "legacy/numFormat-legacy";
    }

    @GetMapping("/colorbox")
    public String colorboxLegacy() {
        return "legacy/colorbox-legacy";
    }

    @GetMapping("/test/colorbox")
    public String colorboxTest() {
        return "legacy/test/colorbox";
    }

    @GetMapping("/test/colorbox-explorer")
    public String colorboxExplorer() {
        return "legacy/test/colorboxExplorer";
    }

    @GetMapping("/test/file-explorer")
    public String fileExplorer() {
        return "legacy/test/fileExplorer";
    }

    @GetMapping("/test/file-explorer-div")
    public String fileExplorerDiv() {
        return "legacy/test/fileExplorerWithDIV";
    }

    @GetMapping("/test/subscript")
    public String subscriptTest() {
        return "legacy/test/suscript";
    }

    @GetMapping("/test/subscript-div")
    public String subscriptDivTest() {
        return "legacy/test/suscriptWithDIV";
    }
}

