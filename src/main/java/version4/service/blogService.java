package version4.service;

import version4.common.Blog;
import version4.common.User;

public interface blogService {
    Blog getBlogById(Integer id);
    User selectUser(Integer id);
}
