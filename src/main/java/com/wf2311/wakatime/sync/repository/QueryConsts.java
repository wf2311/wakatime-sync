package com.wf2311.wakatime.sync.repository;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface QueryConsts {
    String START_TIME_BETWEEN_QUERY = "{'startTime':{ $gte: ?0, $lte: ?1}}";
    String START_TIME_BETWEEN_AND_PROJECT_QUERY = "{'startTime':{ $gte: ?0, $lte: ?1},'project':?2}";
    String DAY_BETWEEN_QUERY = "{'day':{ $gte: ?0, $lte: ?1}}";
}
