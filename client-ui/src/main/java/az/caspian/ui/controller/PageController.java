package az.caspian.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/components")
  public String componentsPage() {
    return "components";
  }

  @GetMapping("/forms")
  public String formsPage() {
    return "forms";
  }

  @GetMapping("/table")
  public String tablesPage() {
    return "tables";
  }

  @GetMapping("/icons")
  public String iconsPage() {
    return "icons";
  }
}
