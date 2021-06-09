package actors.Dao;

import java.util.List;

public interface daoInterface<T> {
	public T add(T t);
	
	public T getId(Integer id);
	
	public List<T> getAll();
	
	public boolean update(T t);
	
	public boolean delete(T t);
	
}
