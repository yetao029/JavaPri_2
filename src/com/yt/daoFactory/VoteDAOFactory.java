package com.yt.daoFactory;

import com.yt.dao.VoteDAO;
import com.yt.daoImpl.VoteDAOImpl;

public class VoteDAOFactory {
	public static VoteDAO getVoteDAOInstance() {
		return new VoteDAOImpl();
	}

}
