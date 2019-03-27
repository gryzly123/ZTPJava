package eu.kniedzwiecki.ztpj.lab02.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T>
{
	Optional<T> get(long id)  throws SQLException;
	List<T> getAll()  throws SQLException;
	void save(T t)  throws SQLException;
	void update(T t, String[] params)  throws SQLException;
	void delete(T t)  throws SQLException;
}
