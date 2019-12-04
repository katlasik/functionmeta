package io

import scala.annotation.compileTimeOnly
import scala.reflect.macros.blackbox

object functionmeta {

  /**
  *
    * Returns name of the host function.
    * For example:
    *<pre>
    *{@code
    *import io.functionmeta._
    *
    *def func() {
    *  println("Name of the function is " + functionName)
    *}
    *}
    *</pre>
    *Calling method <i>func</i> will print <i>Name of the function is func</i>.
    *
    * @return Name of host function.
    */
  def functionName: String = macro Impls.functionNameImpl

  /**
    *
    * Returns all values of arguments of host function as <i>List[Any]</i>.
    * For example:
    *<pre>
    *{@code
    *import io.functionmeta._
    *
    *def func(i: String, j: Int) {
    *  println(arguments)
    *}
    *}
    *</pre>
    *Calling method <i>func("1", 2)</i> will print <i>List("1",1)</i>.
    *
    * @return List of arguments of host function.
    */
  def arguments: List[Any] = macro Impls.argumentsImpl

  /**
    *
    * Returns map of names and values of arguments of host function as <i>Map[String, Any]</i>.
    * For example:
    *<pre>
    *{@code
    *import io.functionmeta._
    *
    *def func(i: String, j: Int) {
    *  println(arguments)
    *}
    *}
    *</pre>
    *Calling method <i>func("1", 2)</i> will print <i>Map(i -> "1", j -> 2)</i>.
    *
    * @return Map of names and values of arguments of host function.
    */
  def argumentsMap: Map[String, Any] = macro Impls.argumentsMapImpl

  private object Impls {

    @compileTimeOnly("Enable macro paradise plugin to expand macro annotations or add scalac flag -Ymacro-annotations.")
    def functionNameImpl(c: blackbox.Context): c.Expr[String] = {
      val owner = c.internal.enclosingOwner

      if (owner.isMethod) {
        c.Expr(c.parse(s""""${owner.name.toString}""""))
      } else {
        c.abort(c.enclosingPosition, "functionName can be used only inside function.")
      }
    }

    @compileTimeOnly("Enable macro paradise plugin to expand macro annotations or add scalac flag -Ymacro-annotations.")
    def argumentsImpl(c: blackbox.Context): c.Expr[List[Any]] = {

      val owner = c.internal.enclosingOwner

      if (owner.isMethod || owner.isConstructor) {
        c.Expr(
          c.parse(
            s"List(${owner.asMethod.paramLists.head.map(_.name).mkString(", ")})"
          )
        )
      } else {
        c.abort(c.enclosingPosition, "arguments can be used only inside function.")
      }

    }

    @compileTimeOnly("Enable macro paradise plugin to expand macro annotations or add scalac flag -Ymacro-annotations.")
    def argumentsMapImpl(c: blackbox.Context): c.Expr[Map[String, Any]] = {

      val owner = c.internal.enclosingOwner

      if (owner.isMethod || owner.isConstructor) {
        c.Expr(
          c.parse(
            s"""Map(${owner.asMethod.paramLists.head.map(s => s""""${s.name}" -> ${s.name}""").mkString(", ")})"""
          )
        )
      } else {
        c.abort(c.enclosingPosition, "argumentsMap can be used only inside function.")
      }

    }

  }

}
