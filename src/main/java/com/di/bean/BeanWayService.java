package com.di.bean;

import org.springframework.beans.factory.InitializingBean;

//使用@Bean形式的Bean
public class BeanWayService implements InitializingBean {
    private int id;
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BeanWayService(){
        System.out.println("**********第一步：调用Bean的默认构造方法**********");
        this.id = 1;
        this.name = "第一步";
        System.out.println("bean1的名称"+this);
        System.out.println("*********第二步：检查Bean配置文件中是否注入了Bean的属性值**********");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bean2的名称"+this);
        System.out.println("**********第三步：检查Bean是否实现了InitializingBean接口**********");
        this.id = 3;
        this.name = "第三步";
        System.out.println("bean3的值：" + this);
    }


    public void init(){
        System.out.println("**********第四步：检查Bean配置文件中是否指定了init-method此属性**********");
        this.id = 4;
        this.name = "第四步";
        System.out.println("bean4的值：" + this);
    }

    public void destory(){
        System.out.println("在Bean销毁之前执行");
    }

    @Override
    public String toString() {
        return "BeanWayService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
