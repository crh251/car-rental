package laijh.carrental.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author laijh25319
 * @date 2021/3/20 17:14
 */
public class CarRentalConst {

    @AllArgsConstructor
    @Getter
    public enum CarRentalStatus {

        /**
         * 1-已预订
         */
        NORMAL(1),
        /**
         * 2-取消
         */
        CANCEL(2);

        final private int val;
    }

}
