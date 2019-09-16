package it.lefosse.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.lefosse.object.DistrictPop;



public class GestioneDb {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "dstech";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		return conn;
	}

	
	public static boolean checkCity(String citta) throws ClassNotFoundException, SQLException {
		String query="select city.Name from city;";
		PreparedStatement ps=getConnection().prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			String cittaCorrente=rs.getString(1);
			if(citta.equals(cittaCorrente)) {
				return true;
			}	
		}
		return false;
	}


	public static List<String> getCittaFromCode(String citta) throws ClassNotFoundException, SQLException {
		List<String> listaCities=new ArrayList<String>();
		String query="select name from world.city where CountryCode=(select CountryCode from world.city where Name=?);";
		PreparedStatement ps=getConnection().prepareStatement(query);
		ps.setString(1, citta);
		ResultSet result=ps.executeQuery();
		while(result.next()) {
			String city=result.getString(1);
			listaCities.add(city);
		}
		
		return listaCities;
	}

	public static int getId(String citta) throws ClassNotFoundException, SQLException {
		String query="select ID from city where Name=?;";
		PreparedStatement ps= getConnection().prepareStatement(query);
		ps.setString(1, citta);
		ResultSet result=ps.executeQuery();
		int id=0;
		while(result.next()) {
			id=result.getInt(1);
		}
		return id;
	}

	public static void deleteCity(String citta) throws ClassNotFoundException, SQLException {
		int id=GestioneDb.getId(citta);
		String query2="delete from world.city where ID=?;";
		PreparedStatement ps2= getConnection().prepareStatement(query2);
		ps2.setInt(1, id);
		ps2.executeUpdate();
	}


	public static void modifyCity(DistrictPop oggetto, List<String> codici, String citta) {
		
		
	}


	public static DistrictPop getDistrictAndPopulation(String citta) throws ClassNotFoundException, SQLException {
		String distretto=null;
		int popolazione=0;
		String query="select District, Population from world.city where name=?;";
		PreparedStatement ps= getConnection().prepareStatement(query);
		ps.setString(1,citta);
		ResultSet result=ps.executeQuery();
		while (result.next()) {
			distretto=result.getString(1);
			popolazione=result.getInt(2);
		}
		DistrictPop x= new DistrictPop(distretto,popolazione);
		return x;
	}

	public static List<String> getAllCodes() throws ClassNotFoundException, SQLException {
		List<String> codes=new ArrayList<String>();
		String query="select distinct CountryCode from world.city;";
		PreparedStatement ps= getConnection().prepareStatement(query);
		ResultSet result=ps.executeQuery();
		while (result.next()) {
			String code=result.getString(1);
			codes.add(code);
		}
		return codes;
		
		
		
	}


	public static void modificaNelDb(String citta, String district, String codice, String cittavecchia, int population) throws ClassNotFoundException, SQLException {
		
		int id=GestioneDb.getId(cittavecchia);
		String query="update world.city set Name=?,CountryCode=?,District=?,Population=? where ID=?;";
		PreparedStatement ps= getConnection().prepareStatement(query);
		ps.setString(1, citta);
		ps.setString(2, codice);
		ps.setString(3, district);
		ps.setInt(4, population);
		ps.setInt(5, id);
		ps.executeUpdate();
		
	}
	
}
