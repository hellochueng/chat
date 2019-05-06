package org.lzz.chat;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.fs.FsShell;

@SpringBootApplication
public class app extends SpringBootServletInitializer implements CommandLineRunner {

//    @Autowired
//    private FsShell shell;

    public static void main(String[] args) {

        SpringApplication.run(app.class, args);
    }

//    @Bean
//    public HighLightJestSearchResultMapper highLightJestSearchResultMapper(){
//        return new HighLightJestSearchResultMapper();
//    }

    @Override
    public void run(String... args) throws Exception {

//        System.setProperty("hadoop.home.dir", "F:\\hadoop-common-2.6.0");
//        for (FileStatus s : shell.lsr("/tmp")) {
//            System.out.println(s.getOwner());
//            System.out.println("> " + s.getPath());
//        }
    }
}
