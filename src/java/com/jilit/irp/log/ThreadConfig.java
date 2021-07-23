
package com.jilit.irp.log;

/**
 *
 * @author Ashutosh1.kumar
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import org.springframework.scheduling.annotation.EnableAsync;
 
@Configuration
@EnableAsync
public class ThreadConfig {
    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(25);
        executor.setThreadNamePrefix("CLXLogManager");
        executor.initialize();
        return executor;
    }
}