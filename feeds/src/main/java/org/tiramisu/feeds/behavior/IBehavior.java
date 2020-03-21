package org.tiramisu.feeds.behavior;

/**
 * 目的：业务逻辑彼此隔离，只依赖生命周期，方便解耦、复用 <br>
 * 注意，behavior 应该尽可能不保存状态（不出现成员变量），这样逻辑更干净
 */
interface IBehavior {
}
