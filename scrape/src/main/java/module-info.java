module azscrape.scrape {
  requires azscrape.core;

  requires org.seleniumhq.selenium.api;
  requires org.apache.logging.log4j;
  requires org.seleniumhq.selenium.remote_driver;
  requires org.seleniumhq.selenium.chrome_driver;
  requires io.github.bonigarcia.webdrivermanager;
  requires dev.failsafe.core;
  requires org.jetbrains.annotations;
  requires org.slf4j;
}
