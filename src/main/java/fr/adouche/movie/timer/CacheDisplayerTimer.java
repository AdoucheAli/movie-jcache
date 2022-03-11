package fr.adouche.movie.timer;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.Cache;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import fr.adouche.movie.cache.utils.CacheUtils;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
@Startup
public class CacheDisplayerTimer {

    private static final Logger LOGGER = getLogger(CacheDisplayerTimer.class);

    @Inject
    CacheUtils cacheUtils;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize() {
        //timerService.createTimer(TimeUnit.SECONDS.toMillis(20), TimeUnit.MINUTES.toMillis(1), "Delay 20 seconds then every 1 minute timer");

        ScheduleExpression expression = new ScheduleExpression();
        expression.minute("*/1").hour("*");

        TimerConfig config = new TimerConfig();
        config.setPersistent(false);

        timerService.createCalendarTimer(expression, config);

    }

    @Timeout
    public void execute() {
        cacheUtils.cacheNames()
                .stream()
                .map(cacheUtils::getCache)
                .map(Optional::get)
                .forEach(this::displayCache);
    }

    private void displayCache(Cache<Object, Object> cache) {
        LOGGER.info("------------ cache {} -----------------", cache.getName());
        cache.forEach(entry -> LOGGER.info("{}={}", entry.getKey(), entry.getValue()));
    }
}
