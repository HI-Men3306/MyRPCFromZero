package version5.service;

import version5.common.blog;

public interface blogService {
    blog selectBlogById(Integer id);
    Integer insertBlog(blog Blog);
}
