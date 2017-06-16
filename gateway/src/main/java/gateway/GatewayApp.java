package gateway;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;

/**
 * @author chanwook
 */
@EnableZuulProxy
@SpringBootApplication
public class GatewayApp {

    private static final Logger logger = LoggerFactory.getLogger(GatewayApp.class);

    public static void main(String[] args) {

        final ConfigurableApplicationContext context = SpringApplication.run(GatewayApp.class, args);

        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        String scriptRoot = context.getEnvironment().getProperty("zuul.filter.root", "");

        logger.info("Groovy filter root path : {}", scriptRoot);

        if (scriptRoot.length() > 0) scriptRoot = scriptRoot + File.separator;
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(5,
                    scriptRoot + "pre",
                    scriptRoot + "route",
                    scriptRoot + "post");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SimpleFilter simpleFilter() {
        return new SimpleFilter();
    }
}
