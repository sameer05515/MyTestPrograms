package com.p.topic.mgmt.service;

import com.p.topic.mgmt.pojo.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> find(int page,int size);

    /**
     * Gets the Topic sessions grid.
     *
     * @param accountId the account id
     * @param publishAccStatus the publish acc status
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the Topic sessions grid
     * @throws Exception the  Topic exception
     */
    List<Topic> getAll();

    /**
     * Gets the Topic sessions grid.
     *
     * @param accountId the account id
     * @param publishAccStatus the publish acc status
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the Topic sessions grid
     * @throws Exception the  Topic exception
     */
    int create(Topic lob);

    /**
     * Gets the Topic sessions grid.
     *
     * @param accountId        the account id
     * @param publishAccStatus the publish acc status
     * @param dateFrom         the date from
     * @param dateTo           the date to
     * @return the Topic sessions grid
     * @throws Exception the  Topic exception
     */
    Topic update(Topic lob);

    List<Topic> updateAll(List<Topic> lob);

    /**
     * Gets the Topic sessions grid.
     *
     * @param accountId the account id
     * @param publishAccStatus the publish acc status
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the Topic sessions grid
     * @throws Exception the  Topic exception
     */
    Topic get(Integer id);

    /**
     * Gets the Topic sessions grid.
     *
     * @param accountId the account id
     * @param publishAccStatus the publish acc status
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the Topic sessions grid
     * @throws Exception the  Topic exception
     */
    Topic get(String uniqueStrid);
}
