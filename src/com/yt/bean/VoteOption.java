package com.yt.bean;

public class VoteOption {
	private int voteOptionID;// 投票选项ID
	private int voteID;// 所属投票ID
	private String voteOptionName;// 投票选项名称
	private int ticketNum;// 票数

	public int getVoteOptionID() {
		return voteOptionID;
	}

	public void setVoteOptionID(int voteOptionID) {
		this.voteOptionID = voteOptionID;
	}

	public int getVoteID() {
		return voteID;
	}

	public void setVoteID(int voteID) {
		this.voteID = voteID;
	}

	public String getVoteOptionName() {
		return voteOptionName;
	}

	public void setVoteOptionName(String voteOptionName) {
		this.voteOptionName = voteOptionName;
	}

	public int getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}

}
