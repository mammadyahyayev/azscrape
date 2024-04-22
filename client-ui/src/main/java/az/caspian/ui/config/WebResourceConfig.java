package az.caspian.ui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebResourceConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(
        "/templates/**",
        "/static/img/**",
        "/static/css/**",
        "/static/js/**"
      )
      .addResourceLocations(
        "classpath:/static/css/",
        "classpath:/static/js/",
        "classpath:/static/img/",
        "classPath:/templates/"
      );
  }
}
