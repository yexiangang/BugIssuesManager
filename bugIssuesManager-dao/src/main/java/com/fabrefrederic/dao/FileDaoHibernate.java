package com.fabrefrederic.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabrefrederic.business.File;
import com.fabrefrederic.business.File_;
import com.fabrefrederic.dao.interfaces.FileDao;

@Component("fileDaoHibernate")
public class FileDaoHibernate extends DaoHibernate<File> implements FileDao {
    private static final Logger LOGGER = Logger.getLogger(FileDaoHibernate.class);
    private static final long serialVersionUID = 1L;

    @Transactional(noRollbackFor = NoResultException.class)
    @Override
    public File findByName(String name) {
        File fileResult = null;

        if (StringUtils.isBlank(name)) {
            LOGGER.error("The file name cannot be null or empty");
            throw new IllegalArgumentException("The file name cannot be null or empty");
        }

        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<File> criteriaQuery = builder.createQuery(File.class);

        final Root<File> root = criteriaQuery.from(File.class);
        final ParameterExpression<String> paramExpression = builder.parameter(String.class);
        criteriaQuery.select(root).where(builder.equal(root.get(File_.name), paramExpression));

        final TypedQuery<File> typedQuery = getEntityManager().createQuery(criteriaQuery);
        typedQuery.setParameter(paramExpression, name);

        try {
            fileResult = typedQuery.getSingleResult();
        } catch (final NoResultException noResultException) {
            LOGGER.info("No file found with the name : " + name);
            LOGGER.debug(noResultException);
            throw noResultException;
        } catch (final Exception exception) {
            LOGGER.error(exception);
            throw exception;
        }

        if (LOGGER.isDebugEnabled()) {
            if (fileResult != null) {
                LOGGER.debug("A file has been found");
            }
        }
        return fileResult;
    }
}