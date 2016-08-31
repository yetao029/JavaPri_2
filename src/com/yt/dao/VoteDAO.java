package com.yt.dao;

import java.util.List;

import com.yt.bean.Vote;
import com.yt.util.Page;

public interface VoteDAO {
	public void addVote(Vote vote);// 添加投票

	public void updateVote(Vote vote);// 更新投票信息

	public void deleteVote(int voteID);// 删除投票

	public Vote findVoteByID(int voteID);// 根据ID查询投票

	public Vote findVoteByName(String voteName);// 根基名称查询投票

	public List<Vote> findVoteByChannel(Page page, int channelID);// 查询频道下所有投票

	public List<Vote> findAllVote(Page page);// 查询所有投票

	public int findAllCount();// 查询投票总记录数

	public int findCountByChannel(int channelID);// 查询频道下投票记录数

}
