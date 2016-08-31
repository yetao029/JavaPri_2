package com.yt.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yt.bean.VoteOption;
import com.yt.dao.VoteOptionDAO;
import com.yt.util.DBConnection;

public class VoteOptionDAOImpl implements VoteOptionDAO {

	@Override
	public void addVoteOption(VoteOption voteOption) {
		Connection conn = DBConnection.getConnection();
		String addSQL = "insert into " + "tb_voteoption(voteOptionName,voteID,ticketNum) values(?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(addSQL);
			pstmt.setString(1, voteOption.getVoteOptionName());
			pstmt.setInt(2, voteOption.getVoteID());
			pstmt.setInt(3, voteOption.getTicketNum());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}
	@Override
	public void updateVoteOption(VoteOption voteOption) {
		Connection conn = DBConnection.getConnection();
		String deleteSQL = "update tb_voteoption set voteOptionName = ? , ticketNum = ? where voteOptionID = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setString(1, voteOption.getVoteOptionName());
			pstmt.setInt(2, voteOption.getTicketNum());
			pstmt.setInt(3, voteOption.getVoteOptionID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
	}
	@Override
	public void deleteVoteOption(int voteOptionID) {
		Connection conn = DBConnection.getConnection();
		String deleteSQL = "delete from tb_voteoption where voteOptionID=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, voteOptionID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}
	@Override
	public VoteOption findVoteOptionByID(int voteOptionID) {
		Connection conn = DBConnection.getConnection();
		String findByIDSQL = "select * from tb_voteoption where voteOptionID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		VoteOption voteOption = null;
		try {
			pstmt = conn.prepareStatement(findByIDSQL);
			pstmt.setInt(1, voteOptionID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				voteOption = new VoteOption();
				voteOption.setVoteOptionID(rs.getInt(1));
				voteOption.setVoteID(rs.getInt(2));
				voteOption.setVoteOptionName(rs.getString(3));
				voteOption.setTicketNum(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return voteOption;
	}

	@Override
	public List<VoteOption> findVoteOptionByVote(int voteID) {
		Connection conn = DBConnection.getConnection();
		String findByIDSQL = "select * from tb_voteoption where voteID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VoteOption> voteOptions = new ArrayList<VoteOption>();
		try {
			pstmt = conn.prepareStatement(findByIDSQL);
			pstmt.setInt(1, voteID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				VoteOption voteOption = new VoteOption();
				voteOption.setVoteOptionID(rs.getInt(1));
				voteOption.setVoteID(rs.getInt(2));
				voteOption.setVoteOptionName(rs.getString(3));
				voteOption.setTicketNum(rs.getInt(4));
				voteOptions.add(voteOption);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return voteOptions;
	}

}
