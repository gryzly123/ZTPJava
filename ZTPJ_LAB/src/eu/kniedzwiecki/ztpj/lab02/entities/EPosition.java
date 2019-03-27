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
	
	public static String toString(EPosition position) throws Exception
	{
		switch(position)
		{
			case BaseWorker: return "Pracownik";
			case Director:   return "Dyrektor";
			case Salesman:   return "Handlowiec";
		}
		throw new Exception("invalid position enum value");
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
	
	private static Map<Integer, String> sqlPositions;
	public static EPosition getPositionFromId(int id) throws SQLException, Exception
	{
		if(sqlPositions == null) sqlPositions = new HashMap<>();
		if(!sqlPositions.containsKey(id))
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			PreparedStatement getPositions = c.prepareStatement("SELECT * FROM positions");
			ResultSet result = getPositions.executeQuery();
			while(result.next())
			{
				sqlPositions.put(result.getInt("id"), result.getString("name"));
			}
		}
		
		if(!sqlPositions.containsKey(id)) throw new SQLException("Position not found!");
		return fromString(sqlPositions.get(id));
	}
	
	
}
