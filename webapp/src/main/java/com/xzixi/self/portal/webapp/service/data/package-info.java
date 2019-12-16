/**
 * 数据层，只允许业务层调用，访问数据库和缓存，将数据访问独立分出一层，原因如下：<br>
 * 1. 由于数据层的代码在编译期生成，如果和业务层不分开可能会有方法重名导致变异报错，增加调试成本<br>
 * 2. 数据层访问数据库的同时读写缓存，维护数据库和缓存的一致性，业务层直接调用即可，一般情况不需要再考虑一致问题<br>
 *
 * 传统的三层架构，业务层只需要通过持久层访问数据库，但是现在除了数据库还有缓存和全文搜索引擎，
 * 如果这些数据的一致性仍然放在业务层中和业务逻辑一起处理，会增加代码的复杂度和出错率，所以在业务层和持久层之间
 * 增加了一个数据层，业务层通过数据层访问数据，而不直接调用持久层，业务层只专注业务，代码逻辑也更加清晰
 *
 * @author 薛凌康
 */
package com.xzixi.self.portal.webapp.service.data;
