package org.lzz.chat.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
 
import javax.sql.DataSource;
 
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.alibaba.druid.pool.DruidDataSource; //这个包容易导入错误
import com.alibaba.druid.support.http.StatViewServlet; //这个包容易导入错误
import com.alibaba.druid.support.http.WebStatFilter; //这个包容易导入错误
 

@Configuration
public class DruidConfig {
 
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }
 
    // 配置Druid的监控
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
 
        Map<String, String> initParams = new HashMap<>();
        // 监控页面登录用户名
 
        initParams.put("loginUsername", "admin");
 
        // 监控页面登录用户密码
        initParams.put("loginPassword", "123456");
 
        // ip白名单（没有配置或者为空，则允许所有访问）
        initParams.put("allow", "");
 
        // ip黑名单（如果某个ip同时存在，deny优先于allow）
        initParams.put("deny", "");
        bean.setInitParameters(initParams);
        return bean;
    }
 
    // 2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
 
        Map<String, String> initParams = new HashMap<>();
 
        // 不拦截的静态资源
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(initParams);
 
        // 拦截所有的请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}