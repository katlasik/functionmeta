package io.functionmeta

import org.scalatest._
import shapeless.test.illTyped

class FunctionMetaTest extends WordSpec with Matchers {

  "arguments" should {
    "return list of arguments" in {

      case class Foo(bar: Double)

      def method(a: List[String], b: Int, c: Foo): Unit = {
        arguments shouldBe List(List("hello", "yesterday"), 1100, Foo(12.3))
        ()
      }

      method(List("hello", "yesterday"), 1100, Foo(12.3))
    }

    "return list of arguments when assigned to val" in {

      case class Foo(bar: Double)

      def method(a: List[String], b: Int, c: Foo): Unit = {
        val args = arguments
        args shouldBe List(List("hello", "yesterday"), 1100, Foo(12.3))
        ()
      }

      method(List("hello", "yesterday"), 1100, Foo(12.3))
    }

    "return list of vararg arguments" in {

      case class Foo(bar: Double)

      def method(a: String*): Unit = {
        arguments shouldBe List(List("hello", "yesterday"))
        ()
      }

      method("hello", "yesterday")
    }
    "return list of vararg arguments when assigned to val" in {

      case class Foo(bar: Double)

      def method(a: String*): Unit = {
        val args = arguments
        args shouldBe List(List("hello", "yesterday"))
        ()
      }

      method("hello", "yesterday")
    }


    "not compile outside function" in {

      illTyped(
        """
          case class Test() {
            arguments
          }
        """)

      illTyped(
        """
          arguments
        """)
    }
  }

  "argumentsMap" should {
    "return map of argument names and values" in {

      case class Foo(bar: Double)

      def method(a: List[String], b: Int, c: Foo): Unit = {
        argumentsMap shouldBe Map("a" -> List("hello", "yesterday"), "b" -> 1100, "c" -> Foo(12.3))
        ()
      }

      method(List("hello", "yesterday"), 1100, Foo(12.3))
    }
    "return map of argument names and values when assigned to val" in {

      case class Foo(bar: Double)

      def method(a: List[String], b: Int, c: Foo): Unit = {
        val am = argumentsMap
        am shouldBe Map("a" -> List("hello", "yesterday"), "b" -> 1100, "c" -> Foo(12.3))
        ()
      }

      method(List("hello", "yesterday"), 1100, Foo(12.3))
    }
    "not compile outside function" in {

      illTyped(
        """
          argumentsMap
        """)

      illTyped(
        """
           case class Test() {
             argumentsMap
          }
        """)
    }
  }

  "functionName" should {
    "return name of the function" in {

      case class Foo(bar: Double)

      def method(a: List[String], b: Int, c: Foo): Unit = {
        functionName shouldBe "method"
        ()
      }

      method(List("hello", "yesterday"), 1100, Foo(12.3))
    }
    "return name of the function when assigned to a val" in {
      def method(): Unit = {
        val funcName = functionName

        funcName shouldBe "method"
        ()
      }
      method()
    }
    "not compile outside function" in {

      illTyped(
        """
           functionName
        """)

      illTyped(
        """
           class Test() {
              functionName
           }
        """)
    }
  }

}
