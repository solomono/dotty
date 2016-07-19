package test

import scala.annotation.Idempotent

object CommonSubexpression {

  @Idempotent def sum(i1: Int, i2: Int) = i1 + i2
  @Idempotent def sum2[A: Numeric, B: Numeric](i1: A, i2: B) =
    implicitly[Numeric[A]].toInt(i1) + implicitly[Numeric[B]].toInt(i2)

  def method1: Int = {
    val a = 1
    val c = sum(sum(1, a), 3)
    val d = sum(sum(1, a), 4)
    assert(c == 5)
    assert(d == 6)
    d - c
  }

  def method2: Int = {
    val a = 1
    val c = sum(sum(sum(1, a), 2), 3)
    val d = sum(sum(sum(1, a), 2), 4)
    assert(c == 7)
    assert(d == 8)
    d - c
  }

  def method3: Int = {
    val a = 1
    val c = sum(sum(sum(1, a), 2), 1)
    val d = sum(sum(sum(1, a), 2), 2)
    assert(c == 5)
    assert(d == 6)
    d - c
  }

  def method4: Int = {
    val a = 1
    val b = sum(1, a)
    val c = sum(sum(sum(1, a), 2), 1)
    val d = sum(sum(sum(1, a), 2), 2)
    assert(b == 2)
    assert(c == 5)
    assert(d == 6)
    d - c
  }

  def method5: Int = {
    val a = 1
    val b = sum(1, a)
    val c = sum(sum(1, a), 2)
    val d = sum(sum(sum(1, a), 2), 2)
    assert(b == 2)
    assert(c == 4)
    assert(d == 6)
    d - c
  }

  def method6: Int = {
    val a = 1
    val c = 3 + sum(1, a)
    val d = sum(1, a) + 4
    assert(c == 5)
    assert(d == 6)
    d - c
  }

  def method7: Int = {
    val a = (1,1)
    val c = 3 + a._1
    val d = a._1 + 3
    assert(c == 4)
    assert(d == 4)
    d - c
  }

  def method8: Boolean = {
    // SHOULD DO NOTHING
    for (x <- List(1, 2, 3); y = x * x; z = x * y; u <- 0 to y) yield x * y * z * u
    true
  }

  def method9: Int = {
    val a = 1
    val c = 3 + sum2(a, a)
    val d = sum2(a, a) + 3
    assert(c == 5)
    assert(d == 5)
    d - c
  }

  // Method equals are optimized
  case class Foo(a: Int)
  case class Bar(f: Foo)

  def method10: Boolean = {
    val a1 = Bar(Foo(1))
    val a2 = Bar(Foo(1))
    a1.equals(a2)
  }

  def method11: Boolean = {
    class A
    class B extends A
    class C extends A
    val a: A = new B
    val b = a.isInstanceOf[B]
    val c = a.isInstanceOf[B]
    b && c
  }

  def method12: Int = {
    val a = Bar(Foo(1))
    val c = 3 + sum2(a.f.a, a.f.a)
    val d = sum2(a.f.a, a.f.a) + 3
    assert(c == 5)
    assert(d == 5)
    d - c
  }

  def method13: Int = {
    class A(val a: Int) {
      @Idempotent def sum[N: Numeric](b: N) =
        a + implicitly[Numeric[N]].toInt(b)
    }

    val a = new A(1)
    val b = a.sum(2)
    val c = a.sum(2) + 1
    assert(b == 3)
    assert(c == 4)
    c - b
  }

  def method14: Int = {
    class A(val a: Int) {
      @Idempotent def convert[N: Numeric]: N =
        implicitly[Numeric[N]].fromInt(a)
    }

    val a = new A(1)
    val b = a.convert[Int]
    val c = a.convert[Int]
    assert(b == 1)
    assert(c == 1)
    c - b
  }

  def method15: Int = {
    val a = 1
    val c = () => {
      val b = sum(sum(1, a), 3)
      b + 1 - 1
    }
    val c2 = c()
    val d = sum(sum(1, a), 4)
    assert(c2 == 5)
    assert(d == 6)
    d - c2
  }

  def method16: Int = {
    val a = 1
    val c = {
      val e = () => {
        val b = sum(sum(1, a), 3)
        b + 1 - 1
      }
      e()
    }
    val d = sum(sum(1, a), 4)
    assert(c == 5)
    assert(d == 6)
    d - c
  }

  def main(args: Array[String]): Unit = {
    println("executing")
    assert(method1 == 1)
    assert(method2 == 1)
    assert(method3 == 1)
    assert(method4 == 1)
    assert(method5 == 2)
    assert(method6 == 1)
    assert(method7 == 0)
    assert(method8)
    assert(method9 == 0)
    assert(method10)
    assert(method11)
    assert(method12 == 0)
    assert(method13 == 1)
    assert(method14 == 0)
    assert(method15 == 1)
    //assert(method16 == 1)
  }

}
