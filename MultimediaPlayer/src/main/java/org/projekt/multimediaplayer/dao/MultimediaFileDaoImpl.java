package org.projekt.multimediaplayer.dao;

import org.projekt.multimediaplayer.model.MultimediaFile;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public final class MultimediaFileDaoImpl extends HibernateDaoSupport implements MultimediaFileDao
{

	public void saveMultimediaFile(MultimediaFile multimediaFile)
	{
		getHibernateTemplate().save(multimediaFile);
	}

	public void updateMultimediaFile(MultimediaFile multimediaFile)
	{
		getHibernateTemplate().update(multimediaFile);
	}

	public void deleteMultimediaFile(MultimediaFile multimediaFile)
	{
		getHibernateTemplate().delete(multimediaFile);
	}

}
