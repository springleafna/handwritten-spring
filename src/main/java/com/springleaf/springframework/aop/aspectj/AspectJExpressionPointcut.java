package com.springleaf.springframework.aop.aspectj;

import com.springleaf.springframework.aop.ClassFilter;
import com.springleaf.springframework.aop.MethodMatcher;
import com.springleaf.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    /**
     * 存储所有支持的切点表达式类型
     * 当前只支持 execution 表达式（最常用的切点表达式）
     * execution(* com.example.service.*.*(..)) 这种形式
     */
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    /**
     * 表达式解析
     * @param expression 切点表达式 例如：expression = "execution(* com.example.service.UserService.*(..))"
     */
    public AspectJExpressionPointcut(String expression) {
        // 创建切点解析器，支持指定的原语类型
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        // 解析切点表达式 解析后存储在 pointcutExpression 中，用于后续匹配
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        /*
          作用 ：快速判断某个类是否可能包含匹配的连接点
          优化性能 ：避免对不相关的类进行详细的方法匹配
          预筛选 ：如果类都不匹配，就不用检查具体方法了
         */
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        /*
          作用 ：精确判断某个方法是否匹配切点表达式
          matchesMethodExecution() 返回匹配结果对象
          alwaysMatches() 表示是否总是匹配
         */
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
