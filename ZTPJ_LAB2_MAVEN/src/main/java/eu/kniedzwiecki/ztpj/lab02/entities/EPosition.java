package eu.kniedzwiecki.ztpj.lab02.entities;

import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public enum EPosition
{
	BaseWorker,
	Director,
	Salesman;
	
	public static String toString(EPosition position)
	{
		switch(position)
		{
			case BaseWorker: return "Pracownik";
			case Director:   return "Dyrektor";
			case Salesman:   return "Handlowiec";
		}
		return "internal_error";
		//throw new Exception("invalid position enum value");
	}
	
		
	public static EPosition fromString(String position) throws Exception
	{
		switch(position)
		{
			case "Pracownik":  return EPosition.BaseWorker;
			case "Dyrektor":   return EPosition.Director;
			case "Handlowiec": return EPosition.Salesman;
		}
		throw new Exception("invalid position string value");
	}
	
	private static Map<Integer, String> sqlPositionsId;
	private static Map<String, Integer> sqlPositionsStr;
	public static EPosition getPositionFromId(int id) throws SQLException, Exception
	{
		if(sqlPositionsId == null) sqlPositionsId = new HashMap<>();
		if(sqlPositionsStr == null) sqlPositionsStr = new HashMap<>();
		if(!sqlPositionsId.containsKey(id))
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			PreparedStatement getPositions = c.prepareStatement("SELECT * FROM positions");
			ResultSet result = getPositions.executeQuery();
			while(result.next())
			{
				sqlPositionsId.put(result.getInt("id"), result.getString("name"));
				sqlPositionsStr.put(result.getString("name"), result.getInt("id"));
			}
			result.close();
		}
		
		if(!sqlPositionsId.containsKey(id)) throw new SQLException("Position not found!");
		return fromString(sqlPositionsId.get(id));
	}
	
	public static int getIdFromPosition(EPosition pos) throws SQLException, Exception
	{
		if(sqlPositionsId == null) sqlPositionsId = new HashMap<>();
		if(sqlPositionsStr == null) sqlPositionsStr = new HashMap<>();
		if(!sqlPositionsStr.containsKey(toString(pos)))
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			PreparedStatement getPositions = c.prepareStatement("SELECT * FROM positions");
			ResultSet result = getPositions.executeQuery();
			while(result.next())
			{
				sqlPositionsId.put(result.getInt("id"), result.getString("name"));
				sqlPositionsStr.put(result.getString("name"), result.getInt("id"));
			}
			result.close();
		}
		
		if(!sqlPositionsStr.containsKey(toString(pos))) throw new SQLException("Position not found!");
		return sqlPositionsStr.get(toString(pos));
	}
	
	public static Worker createWorkerFromPosition(EPosition position)
	{
		switch(position)
		{
			case BaseWorker: return new Worker();
			case Director:   return new Director();
			case Salesman:   return new Salesman();
			default: return null;
		}
	}
}
