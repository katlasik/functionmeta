## FunctionMeta
[![Build Status](https://travis-ci.com/katlasik/functionmeta.svg?branch=master)](https://travis-ci.com/katlasik/functionmeta)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.katlasik/functionmeta_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.katlasik/functionmeta_2.13)
[![javadoc](https://javadoc.io/badge2/com.github.katlasik/functionmeta_2.13/javadoc.svg)](https://javadoc.io/doc/com.github.katlasik/functionmeta_2.13) 

*FunctionMeta* is library providing useful macros that can be used
to retrieve metadata of the function.

### Installation

To use *functionmeta* in your project add dependecy to your **build.sbt**:

```
libraryDependencies += "com.github.katlasik" %% "functionmeta" % "0.4.0" % "provided"
```

### Examples

#### functionName

You can use `functionName` to retrieve the name of the function.
For example:

```scala
import io.functionmeta._

def func() {
   println(s"Name of the function is $functionName")
}
```

Executing function `func` will print:

> Name of the function is func

#### arguments

You can use `arguments` to retrieve values of all parameters passed to function as
`List[Any]`. For example, if we use `arguments` inside function `func`:

```scala
import io.functionmeta._

def func(s: String, i: Int, d: Double, b: Boolean) {
  println(s"Values ${arguments.mkString(", ")} were passed to function")
}
```

and then we execute `func` with parameters like:

```scala
func("str", 10, 5.5, true)
```

then the following will be printed to console:

> Values str, 10, 5.5, true were passed to function

#### argumentsMap

Finally, you can use `argumentsMap` to retrieve names of parameters with values as `Map[String, Any]`.
For example, if we use `argumentsMap` inside function `func` like:

```scala
import io.functionmeta._

def func(s: String, i: Int, d: Double, b: Boolean) {
  argumentsMap.foreach {
    case (k,v) => println(s"$k: $v")
  }
}
```

then when we execute it with parameters like:

```scala
func("str", 10, 5.5, true)
```

then the following lines will be printed to console:

> s: str

> i: 10

> d: 5.5

> b: true
