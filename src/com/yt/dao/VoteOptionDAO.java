package com.yt.dao;

import java.util.List;

import com.yt.bean.VoteOption;

public interface VoteOptionDAO {
	public void addVoteOption(VoteOption voteOption);// 新增投票选项

	public void updateVoteOption(VoteOption voteOption);// 更新投票信息

	public void deleteVoteOption(int voteOptionID);// 删除投票选项

	public VoteOption findVoteOptionByID(int voteOptionID);// 根据ID查询投票选项

	public List<VoteOption> findVoteOptionByVote(int voteID);// 根据投票查询投票选项

}
