package dcc.evaluation.computation.database;

import java.util.List;

import org.junit.Test;

import dcc.evaluation.database.dao.ProjectDao;
import dcc.evaluation.database.entity.Project;

public class ProjectTest {


	@Test
	public void testGetList(){
		ProjectDao dao = new ProjectDao();
		List<Project> projects = dao.getList();

		for(Project project: projects){
			System.out.println("id:"+ project.getId());
			System.out.println("name:"+ project.getName());
			System.out.println("createDate:"+ project.getCreate_date().toString());
			System.out.println("lastModifiedDate:"+ project.getLast_modified_date());
			System.out.println("creator:"+ project.getCreator());
			System.out.println("description:"+ project.getDescription());
			System.out.println("");
		}

	}

	@Test
	public void testFindById(){
		ProjectDao dao = new ProjectDao();
		Project project = dao.findById(2);

		if(project==null){
			System.err.println("not exist");
			return ;
		}

		System.out.println("id:"+ project.getId());
		System.out.println("name:"+ project.getName());
		System.out.println("createDate:"+ project.getCreate_date().toString());
		System.out.println("lastModifiedDate:"+ project.getLast_modified_date());
		System.out.println("creator:"+ project.getCreator());
		System.out.println("description:"+ project.getDescription());

	}

}
