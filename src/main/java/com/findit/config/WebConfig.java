package com.findit.config;
import org.springframework.context.annotation.Configuration;import org.springframework.web.servlet.config.annotation.*;import java.nio.file.Paths;
@Configuration public class WebConfig implements WebMvcConfigurer {@org.springframework.beans.factory.annotation.Value("${findit.upload-dir}") String dir; public void addResourceHandlers(ResourceHandlerRegistry r){String path=Paths.get(dir).toAbsolutePath().normalize().toUri().toString();r.addResourceHandler("/uploads/**").addResourceLocations(path);}}
