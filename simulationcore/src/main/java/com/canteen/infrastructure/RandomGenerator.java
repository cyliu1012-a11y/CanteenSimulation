package com.canteen.infrastructure;
import java.util.Random;

/**
 * 随机数生成器封装
 * 提供各种分布的随机数生成功能
 */
public class RandomGenerator {
    /** 随机数生成器 */
    private final Random random;
    /** 随机种子 */
    private long seed;
    /**
     * 默认构造函数
     * 使用系统时间座位种子
     */
    public RandomGenerator(){
        this(System.currentTimeMillis());
    }
    /**
     * 带种子的构造函数
     * @param seed 随机种子
     */
    public RandomGenerator(long seed){
        this.seed=seed;
        this.random=new Random(seed);
    }
    /**
     * 获取当前随机种子
     * @return 随机种子
     */
    public long getSeed(){
        return seed;
    }
    /**
     * 重置随机种子
     * @param newSeed 新种子
     */
    public void reseed(long newSeed){
        this.seed=newSeed;
        this.random.setSeed(newSeed);
    }
    /**
     * 生成[0,1)均匀分布的随机数
     * @return 随机数
     */
    public double nextDouble(){
        return random.nextDouble();
    }
    /**
     * 生成[0,1)均匀分布的随机数(指定精度)
     * @return 随机数
     */
    public float nextFloat(){
        return random.nextFloat();
    }
    /**
     * 生成整数随机数（在指定范围内，包含下限，不包含上限）
     * @param min 最小值（包含）
     * @param max 最大值（不包含）
     * @return 随机整数
     */
    public int nextInt(int min,int max){
        if (min>=max){
            throw new IllegalArgumentException("min必须小于max");
        }
        return min+random.nextInt(max-min);
    }
    /**
     * 生成随机数（0到bound-1)
     * @param bound 上限（不包含）
     * @return 随机整数
     */
    public int nextInt(int bound){
        return random.nextInt(bound);
    }
    /**
     * 生成布尔值
     * @return 随机布尔值
     */
    public boolean nextBoolean(){
        return random.nextBoolean();
    }
    /**
     * 生成指数分布的随机数（用于到达间隔和服务时间）
     * @param lambda 速率参数（平均值的倒数）
     * @return 指数分布随机数
     */
    public double nextExponential(double lambda){
        if (lambda<=0){
            throw new IllegalArgumentException("lambda必须大于0");
        }
        return -Math.log(1-nextDouble())/lambda;
    }
    /**
     * 生成均匀分布的随机数
     * @param min 最小值
     * @param max 最大值
     * @return 均匀分布随机数
     */
    public double nextUniform(double min,double max){
        if (min>=max){
            throw new IllegalArgumentException("min必须小于max");
        }
        return min+(max-min)*nextDouble();
    }
    /**
     * 生成正态分布的随机数
     * @param mean 均值
     * @param stdDev 标准差
     * @return 正态分布随机数
     */
    public double nextGaussian(double mean,double stdDev){
        if (stdDev<0){
            throw new IllegalArgumentException("标准差不能为负数");
        }
        return mean+stdDev*random.nextGaussian();
    }
    /**
     * 生成泊松分布的随机数
     * @param lambda 平均达到率
     * @return 泊松分布随机数
     */
    public int nextPoisson(double lambda){
        if (lambda<=0){
            return 0;
        }
        double L=Math.exp(-lambda);
        double p=1.0;
        int k=0;
        do{
            k++;
            p*=nextDouble();
        }while (p>L);
        return k-1;
    }
    /**
     * 根据概率返回true
     * @param probability 概率（0-1之间）
     * @return 是否命中
     */
    public boolean nextProbability(double probability){
        if (probability<0||probability>1){
            throw new IllegalArgumentException("概率必须在0-1之间");
        }
        return nextDouble()<probability;
    }
    /**
     * 从数组中随机选择一个元素
     * @param array 数组
     * @param <T> 元素类型
     * @return 随机选择的元素
     */
    public <T> T nextFromArray(T[] array){
        if (array==null||array.length==0){
            return null;
        }
        return array[nextInt(array.length)];
    }

}
