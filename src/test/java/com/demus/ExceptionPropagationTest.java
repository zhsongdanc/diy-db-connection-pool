package com.demus;

import org.junit.Test;

/**
 * Java异常传播机制测试类
 * 演示在catch块中抛出更大范围异常时的行为
 * 
 * @author demus
 * @since 1.0
 */
public class ExceptionPropagationTest {

    /**
     * 测试异常传播机制
     * 演示：捕获具体异常后抛出更大范围的异常
     */
    @Test 
    public void testExceptionPropagation() {
        System.out.println("=== 开始测试异常传播机制 ===");
        
        try {
            // 第一层：调用可能抛出具体异常的方法
            methodThatThrowsSpecificException();
        } catch (Exception e) {
            // 第三层：捕获更大范围的异常
            System.out.println("第三层捕获到异常: " + e.getClass().getSimpleName());
            System.out.println("异常信息: " + e.getMessage());
            System.out.println("原始异常: " + e.getCause());
        }
        
        System.out.println("=== 测试完成 ===");
    }

    /**
     * 第一层方法：可能抛出具体的异常
     */
    private void methodThatThrowsSpecificException() throws Exception {
        try {
            // 模拟抛出具体的异常
            throw new IllegalArgumentException("这是一个具体的参数异常");
        } catch (IllegalArgumentException e) {
            // 第二层：捕获具体异常后抛出更大范围的异常
            System.out.println("第二层捕获到具体异常: " + e.getClass().getSimpleName());
            System.out.println("具体异常信息: " + e.getMessage());
            
            // 抛出更大范围的异常，并保留原始异常作为cause
            throw new RuntimeException("包装后的运行时异常", e);
        }
    }

    /**
     * 测试异常传播的另一种情况：不保留原始异常
     */
    @Test
    public void testExceptionPropagationWithoutCause() {
        System.out.println("\n=== 测试不保留原始异常的传播 ===");
        
        try {
            methodThatThrowsSpecificExceptionWithoutCause();
        } catch (Exception e) {
            System.out.println("捕获到异常: " + e.getClass().getSimpleName());
            System.out.println("异常信息: " + e.getMessage());
            System.out.println("原始异常: " + e.getCause());
        }
    }

    /**
     * 抛出异常但不保留原始异常
     */
    private void methodThatThrowsSpecificExceptionWithoutCause() throws Exception {
        try {
            throw new IllegalArgumentException("具体异常");
        } catch (IllegalArgumentException e) {
            System.out.println("捕获具体异常，但抛出新异常时不保留原始异常");
            // 不保留原始异常
            throw new RuntimeException("新的运行时异常");
        }
    }

    /**
     * 测试异常传播的第三种情况：抛出检查异常
     */
    @Test
    public void testCheckedExceptionPropagation() {
        System.out.println("\n=== 测试检查异常传播 ===");
        
        try {
            throw new IllegalArgumentException("具体异常");
        } catch (IllegalArgumentException e) {
            System.out.println("捕获具体异常，抛出检查异常");
            // 抛出检查异常
            throw new RuntimeException("检查异常", e);
        } catch (RuntimeException e) {
            System.out.println("捕获到RuntimeException: " + e.getClass().getSimpleName());
            System.out.println("异常信息: " + e.getMessage());
            System.out.println("原始异常: " + e.getCause());
        } catch (Exception e) {
            System.out.println("捕获到其他异常: " + e.getClass().getSimpleName());
            System.out.println("异常信息: " + e.getMessage());
        }
    }


} 