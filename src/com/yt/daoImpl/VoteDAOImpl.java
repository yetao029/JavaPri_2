package com.yt.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yt.bean.Vote;
import com.yt.dao.VoteDAO;
import com.yt.util.DBConnection;
import com.yt.util.Page;

public class VoteDAOImpl implements VoteDAO {

	@Override
	public void addVote(Vote vote) {
		Connection conn = DBConnection.getConnection();
		String addVoteSQL = "insert into tb_vote (voteName,channelID) values (?,?) ";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(addVoteSQL);
			pstmt.setString(1, vote.getVoteName());
			pstmt.setInt(2, vote.getChannelID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public void updateVote(Vote vote) {
		Connection conn = DBConnection.getConnection();
		String updateVoteSQL = "update tb_vote set voteName = ? where voteID = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(updateVoteSQL);
			pstmt.setString(1, vote.getVoteName());
			pstmt.setInt(2, vote.getVoteID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public void deleteVote(int voteID) {
		Connection conn = DBConnection.getConnection();
		String deleteSQL = "delete from tb_vote where voteID=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, voteID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public Vote findVoteByID(int voteID) {
		Connection conn = DBConnection.getConnection();
		String findVoteByIDSQL = "select * from tb_vote where voteID = ？";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vote vote = null;
		try {
			pstmt = conn.prepareStatement(findVoteByIDSQL);
			pstmt.setInt(1, voteID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				vote = new Vote();
				vote.setVoteID(rs.getInt(1));
				vote.setVoteName(rs.getString(2));
				vote.setChannelID(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return vote;
	}

	@Override
	public Vote findVoteByName(String voteName) {
		Connection conn = DBConnection.getConnection();
		String querySQL = "select * from tb_vote where voteName = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vote vote = null;
		try {
			pstmt = conn.prepareStatement(querySQL);
			pstmt.setString(1, voteName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vote = new Vote();
				vote.setVoteID(rs.getInt(1));
				vote.setVoteName(rs.getString(2));
				vote.setChannelID(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return vote;
	}

	@Override
	public List<Vote> findVoteByChannel(Page page, int channelID) {
		Connection conn = DBConnection.getConnection();
		String findVoteByChannelSQL = "select * from tb_vote where channelID = ? limit ?,?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Vote> votes = new ArrayList<Vote>();
		try {
			pstmt = conn.prepareStatement(findVoteByChannelSQL);
			pstmt.setInt(1, channelID);
			pstmt.setInt(2, page.getBeginIndex());
			pstmt.setInt(3, page.getEveryPage());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Vote vote = new Vote();
				vote.setVoteID(rs.getInt(1));
				vote.setVoteName(rs.getString(2));
				vote.setChannelID(rs.getInt(3));
				votes.add(vote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return votes;
	}

	@Override
	public List<Vote> findAllVote(Page page) {
		Connection conn = DBConnection.getConnection();
		String findByIDSQL = "select * from tb_vote limit ?,?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Vote> votes = new ArrayList<Vote>();
		try {
			pstmt = conn.prepareStatement(findByIDSQL);
			pstmt.setInt(1, page.getBeginIndex());
			pstmt.setInt(2, page.getEveryPage());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Vote vote = new Vote();
				vote.setVoteID(rs.getInt(1));
				vote.setVoteName(rs.getString(2));
				vote.setChannelID(rs.getInt(3));
				votes.add(vote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return votes;
	}

	@Override
	public int findAllCount() {
		Connection conn = DBConnection.getConnection();
		String findSQL = "select count(*) from tb_vote";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findSQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return count;
	}

	@Override
	public int findCountByChannel(int channelID) {
		Connection conn = DBConnection.getConnection();
		String findSQL = "select count(*) from tb_vote where channelID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findSQL);
			pstmt.setInt(1, channelID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return count;
	}

}
