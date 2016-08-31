package com.yt.daoFactory;

import com.yt.dao.VoteOptionDAO;
import com.yt.daoImpl.VoteOptionDAOImpl;

public class VoteOptionDAOFactory {
	public static VoteOptionDAO getVoteOptionDAOInstance(){
		return new VoteOptionDAOImpl();
	}

}
