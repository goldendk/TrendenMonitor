package org.goldenworkshop.trenden.model;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class StandardTrendenJob implements TrendenJob {
    private static Logger logger = LogManager.getLogger(StandardTrendenJob.class);
    private TrendenJobResult result = new TrendenJobResult();
    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try{
            executeJob();
            stopWatch.stop();
            result.setMessage("OK");
            result.setSuccess(true);
        }
        catch (Exception e){
            logger.error("Job failed", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        result.setDuration((int) stopWatch.getTime());
        logger.info("Executed job: " + result);
    }

    @Override
    public TrendenJobResult getResult() {
        return result;
    }

    protected abstract void executeJob();


}
