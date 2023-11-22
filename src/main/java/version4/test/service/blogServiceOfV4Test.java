package version4.test.service;

import version4.test.common.blogOfV4Test;

public interface blogServiceOfV4Test {
    Integer insertBlog(blogOfV4Test blog);
    blogOfV4Test selectBlogById(Integer id);
}
