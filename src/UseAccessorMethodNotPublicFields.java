/**
 * 有时我们会写一些没有功能只用来集中数据的类，比如{@link point.Point1}。这种类的数据可被直接访问，
 * 因而不具备封装(encapsulation)的优势。除非改变API，否则无法改变其数据表示法，无法对其强加约束条件，
 * 数据被访问时也没办法提供附加操作。坚持完全面向对象程序设计的程序员表示，必须将成员变量设置为private型，
 * 并通过附加getter&setter方法来替代这种类{@link point.Point2}
 *
 * 涉及public型类的时候，这样做没问题：当类需要被设计为可在包外部访问时，应提供访问方法，
 * 以保证更改类内部结构时的灵活性。如果public型类对外暴露了其数据字段，那就甭想改其内部结构了，
 * 因为对这个类的访问语句可能非常多、分布得又非常乱。
 *
 * 但是，如果这个类是package-private型类或者是private型嵌套类，那么用{@link point.Point1}
 * 中的方式访问数据实际上并没有本质上的错误(假设这些数据确实能够描述该类提供的抽象)。
 * 无论在类的定义中还是使用时，这种代码无疑会更简洁。虽然客户端代码跟类内部的数据紧密耦合，
 * 但这种代码仅限于该类所在的包。如果需要更改类内部的数据结构，也不用担心package外的代码受到影响；
 * 而private型嵌套类中，需要修改的范围更是缩小到了顶层类中。
 *
 * Java平台库中有几个类违反了public型类不应直接公开组成成分的建议。典型的有{@link java.awt.Point}和
 * {@link java.awt.Dimension}。千万不要效仿这些设计。正如(item 67:Optimize judiciously)所述，
 * {@link java.awt.Dimension}的内部设计导致直到现在，这个类仍然存在严重的性能问题。
 *
 * 虽然将public型类中的数据域设置为public型一般来说是不明智的，但如果该字段是不可变的，则危害较小。
 * 因为我们无法在不改变API的前提下修改其内部结构，数据被读取时也虽然不能够同时采取辅助措施，
 * 但可以对其强加约束条件(因为final型变量只能被赋值一次且无法被任意修改)。比如下面的例子
 * {@link Time}
 *
 * 但这也仅仅是说“危害较小”，而不是“没有危害”。当我们设计时，还是应该记得，
 * 别把public型类中的数据域设置成public型暴露在外。
 *
 * @author LightDance
 */
public class UseAccessorMethodNotPublicFields {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;

    public static final class Time{

        public final int hour;
        public final int minute;

        public Time(int hour, int minute) {
            if (hour < 0 || hour >= HOURS_PER_DAY) {
                throw new IllegalArgumentException("Hour: " + hour);
            }
            if (minute < 0 || minute >= MINUTES_PER_HOUR) {
                throw new IllegalArgumentException("Min: " + minute);
            }
            this.hour = hour;
            this.minute = minute;
        }

        @Override
        public String toString() {
            return this.hour +":"+this.minute;
        }
    }
    public static void main(String[] args) {
        Time t1 = new Time(15 , 50);
        System.out.println(t1);
        //IllegalArgumentException: Hour: 25
        Time t2 = new Time(25 , 50);
        System.out.println(t2);

    }
}
