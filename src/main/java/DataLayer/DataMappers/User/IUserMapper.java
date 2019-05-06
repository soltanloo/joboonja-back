package DataLayer.DataMappers.User;

import DataLayer.DataMappers.IMapper;
import Models.User;

import java.util.List;

public interface IUserMapper extends IMapper<User, Integer> {
    String getCheckExistsStatement();
    String getFindAllStatement();
}
