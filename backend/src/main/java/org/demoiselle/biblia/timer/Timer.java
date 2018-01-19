package org.demoiselle.biblia.timer;

import java.util.List;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.inject.Inject;
import org.demoiselle.biblia.dao.VersiculoDAO;
import org.demoiselle.biblia.entity.Versiculo;
import org.demoiselle.jee.core.lifecycle.annotation.Startup;

/**
 *
 * @author SERPRO
 */
@Stateless
public class Timer {

    private static final Logger LOG = getLogger(Timer.class.getName());

    @Inject
    private VersiculoDAO dao;

    /**
     *
     */
    @Transactional
    @Schedule(second = "0", minute = "0", hour = "*/1", persistent = false)
    public void atSchedule1h() {
    }

    /**
     *
     */
    @Transactional
    @Schedule(second = "0", minute = "*/1", hour = "*", persistent = false)
    public void atSchedule1m() {
        //LOG.info("atSchedule1m");
    }

    /**
     *
     */
    @Startup
    public void atNow() {
        dao.reindex();
    }

    /**
     *
     */
    @Transactional
    @Schedule(second = "0", minute = "0", hour = "9", persistent = false)
    public void atScheduleOneInDay() {

        // LOG.info("atScheduleOneInDay");
    }

}
