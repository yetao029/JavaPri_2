package com.yt.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.yt.bean.Vote;
import com.yt.bean.VoteOption;
import com.yt.bean.VoteResult;
import com.yt.dao.VoteDAO;
import com.yt.dao.VoteOptionDAO;
import com.yt.daoFactory.VoteDAOFactory;
import com.yt.daoFactory.VoteOptionDAOFactory;
import com.yt.util.Page;
import com.yt.util.PageUtil;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/vote")
@Component
public class VoteAction extends ActionSupport {
	private int voteID;
	private int channelID;
	private String voteName;
	private String[] voteOptions;
	private int voteOptionID;
	private String otherOption;
	private int currentPage;
	@SuppressWarnings("unused")
	private JFreeChart chart;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getVoteID() {
		return voteID;
	}

	public void setVoteID(int voteID) {
		this.voteID = voteID;
	}

	public int getChannelID() {
		return channelID;
	}

	public void setChannelID(int channelID) {
		this.channelID = channelID;
	}

	public String getVoteName() {
		return voteName;
	}

	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}

	public String[] getVoteOptions() {
		return voteOptions;
	}

	public void setVoteOptions(String[] voteOptions) {
		this.voteOptions = voteOptions;
	}

	public int getVoteOptionID() {
		return voteOptionID;
	}

	public void setVoteOptionID(int voteOptionID) {
		this.voteOptionID = voteOptionID;
	}

	public String getOtherOption() {
		return otherOption;
	}

	public void setOtherOption(String otherOption) {
		this.otherOption = otherOption;
	}

	public JFreeChart getChart() {
		VoteDAO voteDAO = VoteDAOFactory.getVoteDAOInstance();
		VoteOptionDAO voteOptionDAO = VoteOptionDAOFactory.getVoteOptionDAOInstance();
		Vote vote = voteDAO.findVoteByID(voteID);
		String voteName = vote.getVoteName();
		List<VoteOption> voteOptions = voteOptionDAO.findVoteOptionByVote(voteID);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
		for (VoteOption voteOption : voteOptions) {
			dcd.setValue(voteOption.getTicketNum(), "", voteOption.getVoteOptionName());
		}
		JFreeChart chart = ChartFactory.createBarChart3D(voteName, "投票选项", "投票数", dcd, PlotOrientation.VERTICAL, false,
				true, false);

		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	@Action(value = "addVote", results = { @Result(name = "success", location = "/admin/addVote.jsp") })
	public String addVote() throws Exception {
		VoteDAO voteDAO = VoteDAOFactory.getVoteDAOInstance();
		VoteOptionDAO voteOptionDAO = VoteOptionDAOFactory.getVoteOptionDAOInstance();
		Vote vote = new Vote();
		vote.setChannelID(channelID);
		vote.setVoteName(voteName);
		voteDAO.addVote(vote);
		int voteID = voteDAO.findVoteByName(voteName).getVoteID();
		for (String voteOptionName : voteOptions) {
			VoteOption vp = new VoteOption();
			vp.setVoteID(voteID);
			vp.setVoteOptionName(voteOptionName);
			voteOptionDAO.addVoteOption(vp);
		}
		return "success";
	}

	@Action(value = "deleteVote", results = { @Result(name = "success", type = "chain", location = "showVote") })
	public String deleteVote() throws Exception {
		VoteDAO voteDAO = VoteDAOFactory.getVoteDAOInstance();
		VoteOptionDAO voteOptionDAO = VoteOptionDAOFactory.getVoteOptionDAOInstance();
		List<VoteOption> voteOptions = voteOptionDAO.findVoteOptionByVote(voteID);
		for (VoteOption voteOption : voteOptions) {
			voteOptionDAO.deleteVoteOption(voteOption.getVoteOptionID());
		}
		voteDAO.deleteVote(voteID);
		return "success";
	}

	@Action(value = "doVote", results = { @Result(name = "success", type = "chain", location = "voteResult"),
			@Result(name = "input", type = "chain", location = "showVoteByChannel") })
	public String doVote() throws Exception {
		VoteOptionDAO voteOptionDAO = VoteOptionDAOFactory.getVoteOptionDAOInstance();
		Cookie cookies[] = ServletActionContext.getRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getValue().equals(Integer.toString(voteID))) {
				this.addActionError("您今天已经投过票了，请明天再来！");
				return "input";
			}
		}
		if (voteOptionID == 0) {
			VoteOption voteOption = new VoteOption();
			voteOption.setVoteID(voteID);
			voteOption.setVoteOptionName(otherOption);
			voteOption.setTicketNum(1);
			voteOptionDAO.addVoteOption(voteOption);
		} else {
			VoteOption voteOption = voteOptionDAO.findVoteOptionByID(voteOptionID);
			int ticketNum = voteOption.getTicketNum();
			voteOption.setTicketNum(ticketNum + 1);
			voteOptionDAO.updateVoteOption(voteOption);
			Cookie cookie = new Cookie("hasVote" + voteID, Integer.toString(voteID));
			ServletActionContext.getResponse().addCookie(cookie);
		}
		return "success";
	}

	@Action(value = "showVote", results = { @Result(name = "success", location = "/admin/showVote.jsp") })
	public String showVote() throws Exception {
		VoteDAO voteDAO = VoteDAOFactory.getVoteDAOInstance();
		VoteOptionDAO voteOptionDAO = VoteOptionDAOFactory.getVoteOptionDAOInstance();
		int totalCount = voteDAO.findAllCount();
		Page page = PageUtil.createPage(10, totalCount, currentPage);
		List<Vote> votes = voteDAO.findAllVote(page);
		List<VoteResult> voteResultList = new ArrayList<VoteResult>();
		for (Vote vote : votes) {
			List<VoteOption> voteOptions = voteOptionDAO.findVoteOptionByVote(vote.getVoteID());
			VoteResult voteResult = new VoteResult();
			voteResult.setVote(vote);
			voteResult.setVoteOptions(voteOptions);
			voteResultList.add(voteResult);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("voteResultList", voteResultList);
		request.setAttribute("page", page);
		return "success";
	}

	@Action(value = "showVoteByChannel", results = { @Result(name = "success", location = "index.jsp"),
			@Result(name = "input", location = "index.jsp") })
	public String showVoteByChannel() throws Exception {
		VoteDAO voteDAO = VoteDAOFactory.getVoteDAOInstance();
		VoteOptionDAO voteOptionDAO = VoteOptionDAOFactory.getVoteOptionDAOInstance();
		int totalCount = voteDAO.findCountByChannel(channelID);
		Page page = PageUtil.createPage(3, totalCount, currentPage);
		List<Vote> votes = voteDAO.findVoteByChannel(page, channelID);
		List<VoteResult> voteResultList = new ArrayList<VoteResult>();
		for (Vote vote : votes) {
			List<VoteOption> voteOptions = voteOptionDAO.findVoteOptionByVote(vote.getVoteID());
			VoteResult voteResult = new VoteResult();
			voteResult.setVote(vote);
			voteResult.setVoteOptions(voteOptions);
			voteResultList.add(voteResult);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("voteResultList", voteResultList);
		request.setAttribute("page", page);
		return "success";
	}

	@Action(value = "voteResult", results = { @Result(name = "success", params = { "width", "400", "height", "300" }) })
	public String voteResult() throws Exception {
		return "success";
	}

}
