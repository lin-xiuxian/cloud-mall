package com.lxx.cloud.mall.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.lxx.cloud.mall.categoryproduct.model.pojo.Product;
import com.lxx.cloud.mall.categoryproduct.model.request.AddProductReq;
import com.lxx.cloud.mall.categoryproduct.model.request.ProductListReq;

/**
 * @author 林修贤
 * @date 2023/2/14
 * @description 商品service
 */
public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);

    void updateStock(Integer productId, Integer stock);
}
