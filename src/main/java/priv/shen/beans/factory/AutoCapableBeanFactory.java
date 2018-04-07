package priv.shen.beans.factory;

import priv.shen.beans.beanDefinition.BeanDefinition;
import priv.shen.beans.beanDefinition.BeanReference;
import priv.shen.beans.beanDefinition.PropertyValue;

import java.lang.reflect.Field;

/**
 * 自动装配的bean工厂 具体实现了创建bean的方法
 */
public class AutoCapableBeanFactory extends AbstractBeanFactory {

    //将bean的属性用反射注入到bean实例中
    protected void applyPropertyValues(Object bean,BeanDefinition beanDefinition)throws Exception{
        for (PropertyValue propertyValue:
             beanDefinition.getPropertyValues().getPropertyValueList()) {
            Field beanField = bean.getClass().getDeclaredField(propertyValue.getName());
            //允许通过反射访问属性
            beanField.setAccessible(true);
            Object value=propertyValue.getValue();
            //如果是引用的bean则要先实例化bean
            if (value instanceof BeanReference){
               BeanReference beanReference=(BeanReference)value;
               value=getBean(beanReference.getName());
            }
            //将属性注入到bean实例中
            beanField.set(bean,value);
        }
    }
}
