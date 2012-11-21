package org.projekt.multimediaplayer.dao;

import org.projekt.multimediaplayer.model.MultimediaFile;

public interface MultimediaFileDao
{
	void saveMultimediaFile(MultimediaFile multimediaFile);
	
	void updateMultimediaFile(MultimediaFile multimediaFile);
	
	void deleteMultimediaFile(MultimediaFile multimediaFile);
}
