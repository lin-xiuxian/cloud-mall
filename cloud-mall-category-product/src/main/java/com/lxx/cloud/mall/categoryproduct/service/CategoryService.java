package com.lxx.cloud.mall.categoryproduct.service;



import com.github.pagehelper.PageInfo;
import com.lxx.cloud.mall.categoryproduct.model.pojo.Category;
import com.lxx.cloud.mall.categoryproduct.model.request.AddCategoryReq;
import com.lxx.cloud.mall.categoryproduct.model.vo.CategoryVO;

import java.util.List;

/**
 * @author 林修贤
 * @date 2023/2/12
 * @description 分类目录 Service 接口
 */
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
