package com.ohgiraffers.section01;

import java.util.List;
import java.util.Map;

public interface DynamicSqlMapper {

    List<MenuDTO>  selectMenuByPrice(Map<String, Integer> map);

    List<MenuDTO> searchMenu(SearchCriteria searchCriteria);

}
