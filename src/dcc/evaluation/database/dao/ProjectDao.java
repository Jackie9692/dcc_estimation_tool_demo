package dcc.evaluation.database.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import dcc.evaluation.database.HibernateSessionFactory;
import dcc.evaluation.database.entity.Project;

public class ProjectDao {
	/**
	 * 查表，支持分页
	 * @return
	 */
	public List<Project> getList(){
		Session session = HibernateSessionFactory.getSession();
		if(session == null)
			return null;

		session.beginTransaction();

		String hql = "select p from Project as p where 1=1";
        Query query = session.createQuery(hql);

        @SuppressWarnings("unchecked")
		List<Project> projects = query.list();

        session.getTransaction().commit();
//        session.close();
		return projects;
	}

	/**
	 * 通过id查找
	 * @param id
	 * @return
	 */
	public Project findById(Integer id){
		Session session = HibernateSessionFactory.getSession();
		if(session == null)
			return null;
		session.beginTransaction();

		String hql = "select p from Project as p where p.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, id);

        @SuppressWarnings("unchecked")
		Project project = (Project) query.uniqueResult();

        session.getTransaction().commit();
		return project;
	}

	/**
	 * delete the project
	 * @param project
	 */
	public void delete(Project project){

	}

	/**
	 * update the project
	 * @param project
	 */
	public void update(Project project){

	}

	/**
	 * create the project
	 * @param project
	 */
	public void add(Project project){

	}

}
