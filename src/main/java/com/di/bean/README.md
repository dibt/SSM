# Bean的初始化和销毁
1. Java配置方式：  
使用@Bean的initMethod 和 destoryMethod(相当于xml配置 init-method 和 destory-method)  
init-method : 构造函数执行完之后执行  
destroyMethod : 在Bean销毁之前执行
2. 注解方式：  
利用JSR-250的@PostConstruct 和 @PreDestory  
@PostConstruct : 构造函数执行完之后执行  
@PreDestory : 在Bean销毁之前执行  
3. BeanFactory实例化Bean的过程：  
    * 第一步：调用Bean的默认构造方法（当然也可以是指定的其它构造方法），生成bean实例：bean1。  
    * 第二步：检查Bean配置文件中是否注入了Bean的属性值，如果有注入，则在bean1实例的基础上对其属性进行注入，把原来的bean1给覆盖掉形成新的bean实例：bean2。
    * 第三步：检查Bean是否实现了InitializingBean接口，如果实现了此接口，则调用afterPropertiesSet()方法对bean2进行相应操作后，把bean2覆盖形成新的bean实例：bean3。
    * 第四步：检查Bean配置文件中是否指定了init-method此属性，如果已指定，则调用此属性对应方法并对bean3进行相应操作后，最终把bean3覆盖形成新的实例：bean4。  
4. init-method方法，初始化bean的时候执行，可以针对某个具体的bean进行配置。  
afterPropertiesSet方法，初始化bean的时候执行，可以针对某个具体的bean进行配置。afterPropertiesSet 必须实现 InitializingBean接口  
BeanPostProcessor接口针对所有Spring上下文中所有的bean，对所有的bean进行一个初始化之前和之后的代理。  
BeanPostProcessor接口中有两个方法：   
postProcessBeforeInitialization:在bean初始化之后执行  
postProcessAfterInitialization:在bean初始化之后执行  
5. Spring装配Bean的过程  
    1. 调用构造函数实例化;
    2. 配置文件是否注入对象属性值;
    3. 如果实现了BeanNameAware接口,调用setBeanName设置Bean的ID或者Name;
    4. 如果实现BeanFactoryAware接口,调用setBeanFactory 设置BeanFactory;
    5. 如果实现ApplicationContextAware,调用setApplicationContext设置ApplicationContext
    6. 调用BeanPostProcessor的预先初始化方法postProcessBeforeInitialization();
    7. 调用InitializingBean的afterPropertiesSet()方法;
    8. 调用定制init-method方法；
    9. 调用BeanPostProcessor的后初始化方法postProcessAfterInitialization;  
6. Spring容器关闭过程
    1. 调用DisposableBean接口的destroy();
    2. 调用定制的destroy-method方法;





未完待续，持续更新
