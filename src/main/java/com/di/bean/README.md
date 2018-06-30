# Bean的初始化和销毁
1. Java配置方式：  
使用@Bean的initMethod 和 destoryMethod(相当于xml配置 init-method 和 destory-method)  
init-method : 构造函数执行完之后执行  
destroyMethod : 在Bean销毁之前执行
2. 注解方式：  
利用JSR-250的@PostConstruct 和 @PreDestory  
@PostConstruct : 构造函数执行完之后执行  
@PreDestory : 在Bean销毁之前执行


未完待续，持续更新
