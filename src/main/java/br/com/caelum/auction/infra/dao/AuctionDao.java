package br.com.caelum.auction.infra.dao;

import br.com.caelum.auction.domain.Bid;
import br.com.caelum.auction.domain.Auction;
import br.com.caelum.auction.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuctionDao {

	private Connection connection;

	public AuctionDao() {
		try {
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/mocks", "root", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Calendar data(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public void save(Auction auction) {
		try {
			String sql = "INSERT INTO AUCTION (DESCRIPTION, DATE, CLOSED) VALUES (?,?,?);";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, auction.getDescription());
			ps.setDate(2, new java.sql.Date(auction.getDate().getTimeInMillis()));
			ps.setBoolean(3, auction.isClosed());
			
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            auction.setId(generatedKeys.getInt(1));
	        }
			
			for(Bid bid : auction.getBids()) {
				sql = "INSERT INTO AUCTION (AUCTION_ID, USER_ID, AMOUNT) VALUES (?,?,?);";
				PreparedStatement ps2 = connection.prepareStatement(sql);
				ps2.setInt(1, auction.getId());
				ps2.setInt(2, bid.getUser().getId());
				ps2.setDouble(3, bid.getAmount());
				
				ps2.execute();
				ps2.close();
				
			}
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Auction> closeds() {
		return byClosed(true);
	}
	
	public List<Auction> actuals() {
		return byClosed(false);
	}
	
	private List<Auction> byClosed(boolean status) {
		try {
			String sql = "SELECT * FROM AUCTION WHERE CLOSED = " + status + ";";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<Auction> auctions = new ArrayList<Auction>();
			while(rs.next()) {
				Auction auction = new Auction(rs.getString("description"), data(rs.getDate("date")));
				auction.setId(rs.getInt("id"));
				if(rs.getBoolean("closed")) auction.close();
				
				String sql2 = "SELECT AMOUNT, ANME, U.ID AS USER_ID, L.ID AS BID_ID FROM AUCTION L INNER JOIN USER U ON U.ID = L.USER_ID WHERE AUCTION_ID = " + rs.getInt("id");
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					User user = new User(rs2.getInt("id"), rs2.getString("name"));
					Bid bid = new Bid(user, rs2.getDouble("amount"));
					
					auction.take(bid);
				}
				rs2.close();
				ps2.close();
				
				auctions.add(auction);
				
			}
			rs.close();
			ps.close();
			
			return auctions;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Auction auction) {
		
		try {
			String sql = "UPDATE AUCTION SET DESCRIPTION=?, DATE=?, CLOSED=? WHERE ID = ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, auction.getDescription());
			ps.setDate(2, new java.sql.Date(auction.getDate().getTimeInMillis()));
			ps.setBoolean(3, auction.isClosed());
			ps.setInt(4, auction.getId());

			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int x() { return 10; }
}
