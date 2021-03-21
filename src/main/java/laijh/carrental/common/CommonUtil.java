package laijh.carrental.common;

import lombok.experimental.UtilityClass;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author laijh25319
 * @date 2021/3/21 1:46
 */
@UtilityClass
public class CommonUtil {

    /**
     * 将 bean 对象变成 map，bean内部属性为key，属性值为value。
     * ignoreValueNull：
     * true-忽略value为null，value为null不会写入map。
     * false-value为null会写入map中。
     */
    public Map<String, Object> bean2map(Object bean, boolean ignoreNullValue) {

        if (bean == null) {
            return new HashMap<>(0);
        }

        BeanMap beanMap = BeanMap.create(bean);

        Set<?> set = beanMap.entrySet();

        Map<String, Object> map = new HashMap<>(Math.min(set.size() * 2, 128));

        for (Object item : set) {

            Object key = ((Map.Entry<?, ?>) item).getKey();
            Object value = ((Map.Entry<?, ?>) item).getValue();

            if (key != null) {
                if (!ignoreNullValue) {
                    map.put(key.toString(), value);
                } else {
                    if (value != null) {
                        map.put(key.toString(), value);
                    }
                }
            }
        }

        return map;
    }

}
