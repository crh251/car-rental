package laijh.carrental.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import laijh.carrental.dto.CarRentalInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author laijh25319
 * @date 2021/3/20 16:55
 */
@Mapper
public interface CarRentalMapper extends BaseMapper<CarRentalInfo> {

}
