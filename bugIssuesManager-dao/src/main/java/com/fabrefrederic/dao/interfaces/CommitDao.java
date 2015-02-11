package com.fabrefrederic.dao.interfaces;

import javax.persistence.NoResultException;

import com.fabrefrederic.business.Commit;

public interface CommitDao extends GenericDao<Commit> {

    /**
     * Find the most recent commit saved in DB. <br />
     * If several commits have the same date, we return the first found.
     *
     * @return the commit, otherwise null
     */
    Commit findTheMostRecentCommit();

    /**
     * Find a commit by its number
     *
     * @param commitNumber commit number
     * @return the commit if found, otherwise null
     * @throws IllegalArgumentException if no commit number
     */
    Commit findByCommitNumber(String commitNumber) throws IllegalArgumentException, NoResultException;
}
