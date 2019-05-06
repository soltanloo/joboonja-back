package DataLayer.DataMappers.Project;

import DataLayer.DataMappers.IMapper;
import Models.Project;
import Models.User;

public interface IProjectMapper extends IMapper<Project, String> {
    String getCheckExistsStatement();
    String getFindAllStatement();
}
